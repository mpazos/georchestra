package org.georchestra.mapfishapp.ws.upload;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
	public SimpleFeatureCollection getFeatureCollection(final File basedir, final FileFormat fileFormat) throws IOException;

	/**
	 * @return List of available format
	 */
	public FileFormat[] getFormats();

}