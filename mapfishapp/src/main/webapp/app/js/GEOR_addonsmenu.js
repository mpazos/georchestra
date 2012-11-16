/*
 * Copyright (C) GeoBretagne, Camptocamp
 *
 * This file is part of geOrchestra
 *
 * geOrchestra is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with geOrchestra.  If not, see <http://www.gnu.org/licenses/>.
 */

Ext.namespace("GEOR");

GEOR.addonloader = Ext.extend(Ext.util.Observable, {
    ptype: "geor_addon",
    initialized: false,
    
    constructor: function(config) {
        this.initialConfig = config || {};
        Ext.apply(this, config);
        this.addEvents(
            "initialized"
        );
        GEOR.addonloader.superclass.constructor.apply(this, arguments);
    },

    init: function(menuitem) {
        // register listener to load files on click and run them ...
        var eventName = menuitem.menu ? "mouseover" : "click";
        
        // TODO: think of "hover" if this is a menu
        menuitem.on(eventName, function() {
            alert("loading");
            if (!this.initialized) {
                this.initialized = true;
                // load CSS
                if (this.css) {
                    this.loadCssFiles(this.css);
                }
                // load JS
                Ext.Loader.load(this.js, function () {
                    this.fireEvent("initialized");
                    // do stuff
                    GEOR[this.addonName].init(menuitem, this.map, this.options); // FIXME: config object
                    
                }, this, true);
            }
        }, this);
    },
    
    /**
     * Method: loadCssFiles
     * this method loads dynamically the css files passed in parameter
     * this method is used because Ext.Loader does not works with css files
     * Parameter:
     * [filename - css files].
     */
    loadCssFiles: function (files) {
        Ext.each(files, function(file) {
            var css = document.createElement("link");
            css.setAttribute("rel", "stylesheet");
            css.setAttribute("type", "text/css");
            css.setAttribute("href", file);
            document.getElementsByTagName("head")[0].appendChild(css);
        });
    }
});
Ext.preg(GEOR.addonloader.prototype.ptype, GEOR.addonloader);



GEOR.addonsmenu = (function () {
    /*
     * Private
     */

    /**
     * Property: map
     * {OpenLayers.Map} The map instance.
     */
    var map = null;

     /**
     * Property: tr
     * {Function} an alias to OpenLayers.i18n
     */
    var tr = null;

    /**
     * Property: addons
     * Array of addon config objects
     */
    var addons = null;

    /**
     * Property: initializes
     * boolean.
     */
    var initialized = false;


    /**
     * Method: checkRoles
     * this method checks the addon permissions
     * Parameter: okRoles {addonItems.roles}.
     *
     *
    var checkRoles = function (okRoles) {
        // addon is available for everyone if okRoles is empty:
        var ok = (okRoles.length === 0);
        // else, check existence of required role to activate addon:
        for (var i = 0; i < okRoles.length; i += 1) {
            if (GEOR.config.ROLES.indexOf(okRoles[i]) >= 0) {
                ok = true;
                break;
            }
        }
        return ok;
    };


    var lazyLoad = function () {
        if (initialized === false) {
            var libs, i, j;
            libs = [];
            i = 0;
            j = 0;
            for (i = 0; i < addons.length; i += 1) {
                var files = addons[i].js;
                for (j = 0; j < files.length; j += 1) {
                    libs.push(files[j]);
                }
                if (addons[i].css) {
                    loadCssFiles(addons[i].css);
                }

            }
            Ext.Loader.load(libs, function (test) {
                var i = 0;
                var menuaddons = Ext.getCmp('menuaddons'), addon, name; // eviter le getCmp : lent ! (utiliser une reference interne au present module)
                for (i = 0; i < addons.length; i += 1) {
                    addon = addons[i];
                    name = addon.name;
                    var addonObject = GEOR[name]; // FIXME: the module pattern is probably not the best pattern for addons ! (cf inheritance)
                    if (addonObject && checkRoles(addon.roles ? addon.roles : [])) {
                        if (addon.group) {
                            var menuGroup = getGroupItem(menuaddons, addon.group);
                            menuaddons.menu.items.items[menuGroup].menu.addItem(
                                addonObject.create(map, addons[i])
                            );
                        } else {
                            menuaddons.menu.addItem(addonObject.create(map, addons[i]));
                        }
                    }
                }
                menuaddons.menu.remove(menuaddons.menu.items.items[0]);
            }, this, true);
            initialized = true;
        }
    };*/

    return {
        /*
         * Public
         */

        /**
         * APIMethod: create
         *
         * This API method returns items menu from each addon loaded
         * Parameters:
         * m - {OpenLayers.Map} The map instance.
         */

        create: function (m) {
            /*
            
            TO BE RESTORED LATER !!!!!!!!
            
            if (GEOR.config.ADDONS.length == 0) {
                return null;
            }
            */
            map = m;
            tr = OpenLayers.i18n;
            addons = GEOR.config.ADDONS;
            
            return {
                text: tr("Tools"),
                menu: [
                    // TODO: the content of this "items" should be got from GEOR.config.ADDONS
                    {
                        // TODO: handle restrictions to specific ROLES
                        text: "Loupe ortho",
                        checked: false,
                        qtip: "Afficher l'ortho dans une fenêtre déplaçable",
                        plugins: [{
                            // idée : un addon = un plugin, qui étend un plugin de base, celui-ci fournissant les fonctionnalités de base (chargement JS, etc)
                            ptype: 'geor_addon',
                            addonName: 'magnifier',
                            map: map,
                            js: [
                                // build into single file ?
                                "app/addons/magnifier/Magnifier.js", 
                                "app/addons/magnifier/GEOB_magnifier.js" // TODO: rename into GEOR_*
                            ],
                            css: ["app/addons/magnifier/magnifier.css"],
                            options: {
                                mode: "static",
                                layer: "satellite",
                                format: "image/jpeg",
                                buffer: 8,
                                wmsurl: "http://tile.geobretagne.fr/gwc02/service/wms"
                            }
                        }]
                    }, {
                        text: "addon model",				
                        iconCls: 'model-icon',
                        qtip: "test",
                        menu: new Ext.menu.Menu({ // TODO : ce sera du ressort de ce module de créer cette structure si menu = true est détecté dans la config
                            items: [{
                                text: "loading..."
                            }]
                        }), // sera rempli a posteriori par l'addon
                        plugins: [{
                            ptype: 'geor_addon',
                            addonName: 'addonmodel',
                            //menu: true, // fixme (à recuperer de menuitem.menu)
                            js: [
                                "app/addons/model/lang.js",
                                "app/addons/model/GEOB_addonmodel.js"
                            ],
                            css: ["app/addons/model/model.css"],
                            options: {
                                option1: "modele",
                                option2: 3
                            },
                            map: map
                        }]
                        
                    }
                ]
            };
        }
            
            /*
            var menuitems = new Ext.Action({
                text: tr("Tools"),
                id: 'menuaddons',
                handler: lazyLoad,
                menu: new Ext.menu.Menu({
                    items: [{
                        text: tr("loading") + "..."
                    }]
                })
            });

            Ext.iterate(groups, function(group) {
                menuitems.initialConfig.menu.addItem({
                    text: group,
                    iconCls: 'geor-save-map',
                    menu: new Ext.menu.Menu({
                        items: []
                    })
                });
            });
            */


    };
})();
