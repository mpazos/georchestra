
Ext.namespace("GEOR");

GEOR.addonmodel = (function () {

    /*
     * Private
     */

    /**
     * Property: map
     * {OpenLayers.Map} The map instance.
     */
    var map = null;
	
	var title = "default title";

	var abstract = "default abstract";


    /**
     * Property: config
     *{Object} Hash of options,. */	
	
    var config = null;

    var tr = function (str) {
            return OpenLayers.i18n(str);
        };


    var getextent = function () {
            GEOR.util.infoDialog({
                title: tr("addonmodel.extent"),
                //"Etendue de la carte",
                msg: tr("addonmodel.extent") + " : " + map.getExtent() + '\n' + tr("addonmodel.size") + " : " + map.getSize()
            });
        };

    var showoptions = function () {
            var myoptions = "option 1 : " + config.option1 + ", option 2 : " + config.option2;
            GEOR.util.infoDialog({
                title: tr("addonmodel.options"),
                //"Options",
                msg: myoptions
            });
        };


    return {
        /*
         * Public
         */

        
        //                    GEOR[this.addonName].init(menuitem, this.map);


        /**
         * APIMethod: init
         */
        init: function (menuitem, m, config) {
            map = m;
            menuitem.addMenuItem({
                text: tr("addonmodel.extent"),
                //'Etendue de la carte',
                handler: function () {
                    getextent();
                }
            });
                
            menuitem.addMenuItem({
                text: tr("addonmodel.options"),
                //'Paramètres',
                handler: function () {
                    showoptions();
                }
            });
        }
    }
})();