/**
 * 
 */
package org.georchestra.mapfishapp.ws.upload;

import java.io.File;
import java.io.IOException;

import org.geotools.data.simple.SimpleFeatureCollection;

/**
 * Defines the abstract interface (Bridge Pattern). This class is responsible of create the implementation OGR or Geotools for the 
 * feature reader. Thus the client don't need to create a specific reader implementation. 
 * 
 * @author Mauricio Pazos
 */
class FeatureFileReader {

	private FeatureFileReaderImplementor readerImpl; 
	
	/**
	 * Creates a reader
	 * 
	 * @param basedir file to read
	 * @param fileFormat the format
	 */
	public FeatureFileReader(final File basedir, final FileFormat fileFormat) {
		assert  basedir != null && fileFormat != null;

		this.readerImpl = createImplementationStrategy(basedir, fileFormat);
	}
	

	/**
	 * Returns the feature collection contained by the file
	 * @return
	 * @throws IOException
	 */
	public SimpleFeatureCollection getFeatureCollection() throws IOException {
		
		return this.readerImpl.getFeatureCollection();
	}
	
	/**
	 * Selects which of implementation must be created.
	 */
	private FeatureFileReaderImplementor createImplementationStrategy(final File basedir, final FileFormat fileFormat){

		
		FeatureFileReaderImplementor implementor = null; 
		if( isOgrAvailable() ){
			
			implementor = new OGRFeatureReader(basedir, fileFormat);
			
		} else { // by default the geotools implementation is created
			
			implementor = new GeotoolsFeatureReader(basedir, fileFormat);
		}
		return implementor;
	}


	/**
	 * Decides what is the implementation must be instantiate.
	 *  
	 * @return
	 */
	private boolean isOgrAvailable() {
		// TODO Auto-generated method stub
		return true;
	}

}
