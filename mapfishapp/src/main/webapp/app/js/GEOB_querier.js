/*
 * Copyright (C) 2009  Camptocamp
 *
 * This file is part of GeoBretagne
 *
 * MapFish Client is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GeoBretagne is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GeoBretagne.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Notes about the -AT-requires and -AT-include annotations below:
 *
 * - -AT-requires OpenLayers/Filter/Comparison.js is required because
 *   Styler/widgets/form/ComparisonComboBox.js, which is
 *   included by Styler/widgets/FilterPanel.js, requires it
 * - -AT-requires OpenLayers/Filter/Spatial.js is required because
 *   Styler/widgets/form/SpatialComboBox.js, which is
 *   included by Styler/widgets/SpatialFilterPanel.js, requires it
 * - -AT-include OpenLayers/Filter/Logical.js is required because
 *   Styler/widgets/FilterBuilder.js uses OpenLayers.Filter.Logical
 */

/*
 * @requires OpenLayers/Filter/Comparison.js
 * @requires OpenLayers/Filter/Spatial.js
 * @include OpenLayers/Filter/Logical.js
 * @include OpenLayers/Format/Filter.js
 * @include OpenLayers/Format/XML.js
 * @include OpenLayers/Control/ModifyFeature.js
 * @include OpenLayers/Control/DrawFeature.js
 * @include OpenLayers/Handler/Point.js
 * @include OpenLayers/Handler/Path.js
 * @include OpenLayers/Handler/Polygon.js
 * @include GeoExt/data/LayerRecord.js
 * @include GeoExt/data/AttributeStore.js
 * @include Styler/widgets/FilterBuilder.js
 * @include GEOB_waiter.js
 * @include GEOB_ows.js
 * @include GEOB_util.js
 */

Ext.namespace("GEOB");

GEOB.querier = (function() {

    /*
     * Private
     */
    var observable = new Ext.util.Observable();
    observable.addEvents(
        /**
         * Event: ready
         * Fires when the filterbuilder panel is ready 
         *
         * Listener arguments:
         * filterBuilderCfg - {Object} Config object for a panel 
         *  with a filterbuilder
         */
        "ready",
        /**
         * Event: showrequest
         * Fires when the filterbuilder panel is already built 
         * and we just need to display it.
         */
        "showrequest",
        /**
         * Event: searchresults
         * Fires when we've received a response from server 
         *
         * Listener arguments:
         * options - {Object} A hash containing response, model and format
         */
        "searchresults",
        /**
         * Event: search
         * Fires when the user presses the search button
         *
         * Listener arguments:
         * panelCfg - {Object} Config object for a panel 
         */
        "search"
    );
    
    /**
     * Property: layerRecord
     * {GeoExt.data.LayerRecord} the WMS layer layerRecord
     */
    var layerRecord = null;
    
    /**
     * Property: record
     * {Ext.data.Record} The matching record for a WFS layer
     *      Fields: "owsType" (should be "WFS"), "owsURL" & "typeName"
     */
    var record = null;
    
    /**
     * Property: attStore
     * {GeoExt.data.AttributeStore} 
     */
    var attStore = null;
    
    /**
     * Property: geometryName
     * {String} The geometry column name
     */
    var geometryName = null;
    
    /**
     * Property: map
     * {OpenLayers.Map} The map instance.
     */
    var map = null;
    
    /**
     * Property: styleMap
     * {OpenLayers.StyleMap} StyleMap used for vectors
     */
    var styleMap = null;
        
    /**
     * Property: cp
     * {Ext.state.CookieProvider} the cookie provider
     */  
    var cp = null;
    
    /**
     * Method: checkFilter
     * Checks that a filter is not missing items.
     *
     * Parameters:
     * filter - {OpenLayers.Filter} the filter
     *
     * Returns:
     * {Boolean} Filter is correct ?
     */
    var checkFilter = function(filter) {
        var filters = filter.filters || [filter];
        for (var i=0, l=filters.length; i<l; i++) {
            var f = filters[i];
            if (f.CLASS_NAME == 'OpenLayers.Filter.Logical') {
                if (!checkFilter(f)) {
                    return false;
                }
            } else if (!(f.value && f.type && 
                (f.property || f.CLASS_NAME == "OpenLayers.Filter.Spatial"))) {
                GEOB.util.infoDialog({
                    msg: "Vous devez remplir les champs des filtres marqués en rouge."
                });
                return false;
            }
        }
        return true;
    };

    /**
     * Method: search
     * Gets the Filter Encoding string and sends the getFeature request
     */
    var search = function() {
        // we quickly check if nothing lacks in filter
        var filter = this.ownerCt.getFilter();
        if (!checkFilter(filter)) {
            return;
        }
    
        observable.fireEvent("search", {
            html: '<div>Recherche en cours...</div>'
        });
        
        // we deactivate draw controls before the request is done.
        this.ownerCt.deactivateControls();
        
        // we need to pass the geometry name at protocol creation, 
        // so that the format has the correct geometryName too.
        GEOB.ows.WFSProtocol(record, map, {geometryName: geometryName}).read({
            maxFeatures: GEOB.config.MAX_FEATURES,
            filter: filter,
            callback: function(response) {
                // Houston, we've got a pb ...
                
                // When using WFS 1.0, getFeature requests are honored with geodata in the original data's SRS 
                // ... which might not be EPSG:2154 !
                // When using GeoServer, an additional parameter can be used (srsName) to ask for feature reprojection, 
                // which is not part of the WFS 1.0 spec
                // When using MapServer, it seems that we have no way to do the same.
                
                // So, basically, we have two solutions :
                // - stick to WFS 1.1 spec, which leaves WFS servers with old MapServers unqueryable. 
                //   We don't want that.
                // - stick to WFS 1.0 spec and add the srsName parameter (in case we're in front of a GeoServer). 
                // In that case, WFSes made with MapServer will return geometries in the original data SRS ... 
                // which could be parsed, and reprojected on the client side using proj4js when user agrees to do so
                
                if (!response.success()) {
                    return;
                }
                
                var model =  (attStore.getCount() > 0) ? new GEOB.FeatureDataModel({
                    attributeStore: attStore
                }) : null;

                observable.fireEvent("searchresults", {
                    features: response.features,
                    model: model
                });
            },
            scope: this
        });
    };
    
    /**
     * Method: buildPanel
     *
     * Parameters:
     * describelayer - {Array} Array containing layer description objects, 
     * such as: {layerName: ..., owsType: ..., owsURL: ..., typeName: ...}
     */
    var buildPanel = function(store, records, options) {

        var r = GEOB.ows.getWfsInfo(records);
        if (!r) {
            GEOB.util.errorDialog({
                msg: "Impossible d'obtenir "+
                    " l'adresse de la couche WFS."
            });
            return;
        }
        
        record = r;
        GEOB.waiter.show();
        
        attStore = GEOB.ows.WFSDescribeFeatureType(record, {
            extractFeatureNS: true,
            success: function() {
                // we get the geometry column name, and remove the corresponding record from store
                var idx = attStore.find('type', GEOB.ows.matchGeomProperty);
                if (idx > -1) { 
                    // we have a geometry
                    var r = attStore.getAt(idx);
                    geometryName = r.get('name');
                    attStore.remove(r);
                } else {
                    GEOB.util.errorDialog({
                        msg: "La couche ne possède pas de colonne géométrique."
                    });
                    return;
                }
                
                observable.fireEvent("ready", {
                    xtype: 'gx_filterbuilder',
                    title: "Requêteur sur "+
                        GEOB.util.shortenLayerName(layerRecord),
                    defaultBuilderType: Styler.FilterBuilder.ALL_OF,
                    filterPanelOptions: {
                        attributesComboConfig: {
                            displayField: "name",
                            listWidth: 165,
                            tpl: GEOB.util.getAttributesComboTpl()
                        }
                    },
                    allowGroups: false,
                    noConditionOnInit: true,
                    deactivable: true,
                    cookieProvider: cp,
                    autoScroll: true,
                    buttons: [{
                        text: 'Recherche',
                        handler: search
                    }],
                    map: map,
                    attributes: attStore,
                    allowSpatial: true,
                    vectorLayer: new OpenLayers.Layer.Vector('filter_builder',{
                        displayInLayerSwitcher: false,
                        styleMap: styleMap
                    })
                });
                
            },
            failure: function() {
                GEOB.util.errorDialog({
                    msg: "Impossible d'obtenir "+
                        "les caractéristiques de la couche demandée"
                });
            },
            scope: this
        });
    };

    return {
    
        /*
         * Observable object
         */
        events: observable,
        
        /**
         * APIMethod: init
         * Initialize this module 
         *
         * Parameters:
         * m - {OpenLayers.Map} The map instance.
         */
        init: function(m) { 
            map = m;
            
            // Cookie storage !
            cp = new Ext.state.CookieProvider({
                expires: new Date(new Date().getTime()+(1000*60*60*24*30)) // 30 days
            });
            Ext.state.Manager.setProvider(cp);
            
            var style = OpenLayers.Util.extend({}, 
                        OpenLayers.Feature.Vector.style['default']);
            
            styleMap = new OpenLayers.StyleMap({
                "default": new OpenLayers.Style(
                    OpenLayers.Util.extend(style, {
                        strokeWidth: 2,
                        strokeColor: "#ee5400",
                        fillOpacity: 0
                    })
                )
            });
            
        },
        
        /*
         * Method: init
         *
         * Parameters:
         * r - {GeoExt.data.LayerRecord}
         */
        create: function(r) {
            
            // if we're already configured with the same layer,
            // do nothing, just ask the layout to switch panels
            if (r == layerRecord) {
                this.events.fireEvent("showrequest"); 
            } else {
                // build the querier panel
                GEOB.waiter.show();
                GEOB.ows.WMSDescribeLayer(r, {
                    success: function(store, records, options) {
                        layerRecord = r;
                        buildPanel(store, records, options);
                    },
                    storeOptions: {
                        fields: [
                            {name: "owsType", type: "string"},
                            {name: "owsURL", type: "string"},
                            {name: "typeName", type: "string"},
                            // and we need to add a special featureNS field
                            // which will be filled by WFSDescribeFeatureType:
                            {name: "featureNS", type: "string"}
                        ]
                    },
                    scope: this
                });
            }
        }
    };
})();