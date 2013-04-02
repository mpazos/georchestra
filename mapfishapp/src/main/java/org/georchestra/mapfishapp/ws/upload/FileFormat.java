package org.georchestra.mapfishapp.ws.upload;

/**
 * 
 * File formats.
 *
 * @author Mauricio Pazos
 */

public enum FileFormat {
		tab {
			@Override
			public String getDriver() {
				return "MapInfo File";
			}

			@Override
			public String[] getFormatOptions() {
				return new String[] {};
			}

			@Override
			public String toString() {
				return "tab";
			}
		},
		mif {
			@Override
			public String getDriver() {
				return "MapInfo File";
			}

			@Override
			public String[] getFormatOptions() {
				return new String[] { "FORMAT=MIF" };
			}

			@Override
			public String toString() {
				return "mif";
			}
		},
		shp {
			@Override
			public String getDriver() {
				return "ESRI shapefile";
			}

			@Override
			public String toString() {
		
				return "shp";
			}
		},
		gml {
			@Override
			public String getDriver() {
				return "GML";
			}

			@Override
			public String toString() {
				// TODO Auto-generated method stub
				return "gml";
			}
		},
		kml {
			@Override
			public String getDriver() {
				return "KML";
			}

			@Override
			public String toString() {
		
				return "kml";
			}

		},
		gpx {
			@Override
			public String getDriver() {
				return "GPX";
			}

			@Override
			public String toString() {
		
				return "gpx";
			}

		};
		

		/**
		 * Returns the OGR driver for this format.
		 * 
		 * @return the driver
		 */
		public abstract String getDriver();

		/**
		 * @return Options for this format
		 */
		public String[] getFormatOptions() {
			return null; //default implementation
		}
		
		public abstract String toString();
		

		/**
		 * Returns the enumerated value associated to the extension file name
		 *  
		 * @param fileExtension
		 * @return FileFormat enumerated value or null if it doesn't exist.
		 */
		public static FileFormat getFileFormat(String fileExtension) {
			
			if("tab".equalsIgnoreCase(fileExtension))	return tab;
			if("mif".equalsIgnoreCase(fileExtension))	return mif;
			if("shp".equalsIgnoreCase(fileExtension))	return shp;
			if("gml".equalsIgnoreCase(fileExtension))	return gml;
			if("gpx".equalsIgnoreCase(fileExtension))	return gpx;
			if("kml".equalsIgnoreCase(fileExtension))	return kml;
			
			return null;
		}
		
		
		
		

}