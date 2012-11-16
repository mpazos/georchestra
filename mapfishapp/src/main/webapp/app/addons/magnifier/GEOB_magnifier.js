Ext.namespace("GEOR");


// TODO: change "module pattern" -> "class"

GEOR.magnifier = (function () {

    /*
     * Private
     */

    /**
     * Property: map
     * {OpenLayers.Map} The map instance.
     */
    var map = null;

    var magnifierControl = null;

    var createControl = function(options) {
        magnifierControl = new OpenLayers.Control.Magnifier(options);
        map.addControl(magnifierControl);
        magnifierControl.update();
    };

    return {
        /*
         * Public
         */

        init: function(menuitem, m, o) {
            map = m;
            
            var options = {
                mode: o.mode,
                baseLayerConfig: {}
            };
            if (o.wmsurl && o.layer) {
                options.baseLayerConfig = {
                    wmsurl: o.wmsurl,
                    layer: o.layer,
                    format: (o.format) ? o.format : "image/jpeg"
                }
            }
            options.baseLayerConfig.buffer = o.buffer;

            createControl(options);
            
            menuitem.on("checkchange", function(item, checked) {
                // TODO: not recreate the control each time, just deactivate it
                if (checked) {
                    createControl(options);
                } else {
                    map.removeControl(magnifierControl);
                    magnifierControl.destroy();
                }
            });
            
        }
    }
})();