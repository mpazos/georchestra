/**
 * 
 */
package org.georchestra.mapfishapp.ws.upload;

import java.io.File;
import java.io.IOException;

import org.geotools.data.FileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.opengis.feature.simple.SimpleFeatureType;

/**
 * This implementation is a façade to the Geotools store implementations.
 * 
 * 
 * @author Mauricio Pazos
 *
 */
class GeotoolsFeatureReader implements FeatureFileReaderImplementor {

	private static final FileFormat[] formats = new FileFormat[] {
			FileFormat.shp, 
			FileFormat.mif, 
			FileFormat.gpx, 
			FileFormat.gml,
			FileFormat.kml };

	
	public GeotoolsFeatureReader() {
	}

	/* (non-Javadoc)
	 * @see org.georchestra.mapfishapp.ws.upload.FeatureFileReaderImplementor#getFeatureCollection()
	 */
	@Override
	public SimpleFeatureCollection getFeatureCollection(final File basedir, final FileFormat fileFormat) throws IOException {
		
		assert basedir != null && fileFormat != null;

		// TODO Auto-generated method stub
		switch(fileFormat){
		case shp:
			return readShpFile(basedir);
			
		default:
			throw new IOException("Unsuported format: " + fileFormat.toString());
		}
		
	}

	/**
	 * Reads the features from Shape file.
	 * 
	 * @param basedir
	 * @return {@link SimpleFeatureCollection}
	 * 
	 * @throws IOException
	 */
	private SimpleFeatureCollection readShpFile(final File basedir) throws IOException {
		
		ShapefileDataStoreFactory shpStoreFactory = new ShapefileDataStoreFactory();
		
		FileDataStore store = shpStoreFactory.createDataStore(basedir.toURI().toURL());

		SimpleFeatureType schema = store.getSchema();
		SimpleFeatureSource featureSource = store.getFeatureSource(schema.getTypeName());
		
		SimpleFeatureCollection features = featureSource.getFeatures();
		
		return features;
	}

	@Override
	public FileFormat[] getFormats() {
		// TODO Auto-generated method stub
		return formats;
	}

}
