/**
 * 
 */
package org.georchestra.mapfishapp.ws.upload;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geotools.data.ogr.OGRDataStore;
import org.geotools.data.ogr.jni.JniOGR;
import org.geotools.data.ogr.jni.JniOGRDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;

/**
 * OGR Feature Reader.
 * 
 * <p>
 * This class is responsible of retrieving the features stored in different file format.
 * </p>
 * <p>
 * The available file format are enumerated in the {@link FileFormat}.
 * </p>
 * 
 * 
 * @author Mauricio Pazos
 * 
 */
final class OGRFeatureReader implements FeatureFileReaderImplementor {
	
	private static final Log LOG = LogFactory.getLog(OGRFeatureReader.class.getPackage().getName());


	public OGRFeatureReader() {

	}

	/* (non-Javadoc)
	 * @see org.georchestra.mapfishapp.ws.upload.FeatureFileReader#getFeatureCollection()
	 */
	@Override
	public SimpleFeatureCollection getFeatureCollection(final File basedir, final FileFormat fileFormat) throws IOException {
		assert  basedir != null && fileFormat != null;

		try{
			String ogrName = basedir.getAbsolutePath();
			String ogrDriver = fileFormat.getDriver();
			
			// FIXME it is not used JniOGRDataStoreFactory jniFactory = JniOGRDataStoreFactory.class.newInstance();
			OGRDataStore store = new OGRDataStore(ogrName, ogrDriver, null,  new JniOGR() );
	        SimpleFeatureSource source = store.getFeatureSource(store.getTypeNames()[0]);

	        return source.getFeatures();
			
		} catch(IOException e ){
			LOG.error(e.getMessage());
			throw new IOException(e);
		}
	}

	@Override
	public FileFormat[] getFormats() {
		
		
		// TODO Auto-generated method stub
		
		return null;
	}

}
