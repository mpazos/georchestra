PRG="$0"

while [ -h "$PRG" ]; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
        else
        PRG=`dirname "$PRG"`/"$link"
        fi
done

PRGDIR=`dirname "$PRG"`
PWD=`pwd`

isOk () {
  if [ ! $2 -eq 0 ]; then
      echo "[FAILURE] [deploy] Failed to execute '$1' correctly"
      echo "[FAILURE] [deploy] Failed to execute '$1' correctly" > /tmp/gc_deploy_failure
      exit $2;
 fi
}
function USAGE {
    echo "deploy.to.*.sh [-P <project_to_deploy>] [-m <deploy_mode>] [-D <property=value>] [-X] target"
    echo "  -P <project_to_deploy> "
    echo "      the modules (like mapfishapp) to deploy. There can be multiple -p parameters"
    echo "      'full' indicates all should be deployed"
    echo "  -m <deploy_mode> either upgrade or full.  All indicates setup databases, geoserver, etc..."
    echo "  -D <property=value> the java system property to set"
    echo "  -X maven debug mode"
}

MODE="upgrade"
PROFILES=""
SYSTEM_PROPS=""

while getopts ":D:P:m:Xh?" opt; do
    case $opt in
        P ) PROFILES="$PROFILES -P$OPTARG" ;;
        m ) MODE=$OPTARG ;;
        D ) SYSTEM_PROPS="$SYSTEM_PROPS -D$OPTARG" ;;
        X ) SYSTEM_PROPS="$SYSTEM_PROPS -X" ;;
        \?) USAGE; exit 1 ;;
    esac
done

if [ -z $PROFILES ] ; then
    PROFILES="-Pupgrade"
fi

if [[ $PROFILES =~ .*Pall.* ]] || [ $PROFILES == "-Pupgrade" ] ; then
    # do nothing.  we want to build all projects
    echo "building and deploying all projects"
else
    PROFILES="-P-all $PROFILES"
fi

shift $(($OPTIND -1))
TARGET=$1

if [ -z $TARGET ] ; then
    echo "a target parameter is required"
    USAGE
    exit 1;
fi



PROFILES="$PROFILES -Ppigma_$TARGET"

echo "[deploy] Deploy mode is $MODE"
echo "[deploy] Deploy profiles are $PROFILES"
echo "[deploy] Deploy target is $TARGET"

DEPLOY_CACHE=/var/cache/deploy

CHECKOUT_DIR=$DEPLOY_CACHE/checkout
if [ ! -d $CHECKOUT_DIR ] ; then
    mkdir -p $CHECKOUT_DIR
    svn checkout http://svn.georchestra.org/georchestra/trunk $CHECKOUT_DIR
    svn checkout https://project.camptocamp.com/svn/aquitaine_pigma/trunk/config-configuration-pigma/ $CHECKOUT_DIR/config/configurations/pigma
fi

cd $CHECKOUT_DIR
#svn revert -R .
#svn status | grep ^? | awk '{print $2}' | while read line; do rm -rf $line; done

svn up $CHECKOUT_DIR/config/configurations/pigma
svn up $CHECKOUT_DIR


ARCHIVE=$DEPLOY_CACHE/archive
#need to execute as deploy

set -x
MVN="/var/cache/deploy/checkout/build-tools/maven/bin/mvn"
sudo -u deploy $MVN clean $SYSTEM_PROPS
#isOk "clean georchestra" $?
sudo -u deploy $MVN install $PROFILES $SYSTEM_PROPS
isOk "build georchestra" $?
cd server-deploy
sudo -u deploy $MVN $PROFILES -Dnon-interactive=true $SYSTEM_PROPS
isOk "deploy" $?