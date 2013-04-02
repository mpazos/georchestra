package org.georchestra.mapfishapp.ws.upload;

import java.io.IOException;

import org.geotools.data.simple.SimpleFeatureCollection;
/**
 * 
 * Feature file reader interface.
 * 
 * <p>
 * The implementations of this interface provides the access to the feature which are stored in specific file formats. 
 * </p>
 * 
 * @author Mauricio Pazos
 *
 */
interface FeatureFileReaderImplementor {

	/**
	 * Returns the set of features maintained in the geofile.
	 * 
	 * @return {@link SimpleFeatureCollection}
	 * @throws IOException
	 */
	public abstract SimpleFeatureCollection getFeatureCollection()
			throws IOException;

}