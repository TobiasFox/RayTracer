/**
 * 
 */

package raytracer.imageCreator;

import java.io.IOException;
import raytracer.imageCreator.enums.ImageQualities;
import raytracer.imageCreator.enums.PixelColorCalculations;
import raytracer.imageCreator.exceptions.FeatureNotUsingException;
import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.shape.withMaterial.material.Density;

/**
 * @author Tobias Fox
 *
 */
public interface ImageCreatorInterface
{
	
	public void createRandomCircles( int numberOfCircles ) throws FeatureNotUsingException;
	
	public void start() throws FeatureNotUsingException;
	
	public void startWithThreads() throws FeatureNotUsingException;
	
	public void startWithThreadPool() throws FeatureNotUsingException;
	
	public void startWithThreadPool( boolean shutDownPool ) throws FeatureNotUsingException;
	
	public void savePicture( String filename ) throws IOException;
	
	public int getAbtastrate();
	
	public void setAbtastrate( int abtastrate );
	
	public void setGamma( double gamma );
	
	public double getGamma();
	
	public ImageQualities getImageQuality();
	
	public void setDefaultColor( Vec3 defaultColor ) throws FeatureNotUsingException;
	
	public void getDefaultColor() throws FeatureNotUsingException;
	
	public void setPixelColorCalculation( PixelColorCalculations variation );
	
	public PixelColorCalculations getPixelColorCalculation();
	
	public void setRecursiveDepth( int depth ) throws FeatureNotUsingException;
	
	public int getRecursiveDepth() throws FeatureNotUsingException;
	
	public void closeThreadPool();
	
	void setDefaultDensity( Density density );
	
	Density getDefaultDensity();
	
	World getWorld() throws FeatureNotUsingException;
	
	void setWorld( World world ) throws FeatureNotUsingException;
}
