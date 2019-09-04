/**
 * 
 */

package raytracer.imageCreator.sampling.colorcalculation;

import raytracer.imageCreator.World;
import raytracer.imageCreator.exceptions.FeatureNotUsingException;
import raytracer.imageCreator.general.Mat4;
import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.shape.Shape;

/**
 * @author Tobias Fox
 *
 */
public abstract class ColorCalculation
{
	
	protected int width;
	protected int height;
	private String featureNotActivated = "This feature is not activated.";
	
	protected Vec3 defaultColor = new Vec3( 0, 0, 0 );
	
	public ColorCalculation( int width, int height )
	{
		this.width = width;
		this.height = height;
	}
	
	public abstract Vec3 pixelColor( double xs, double ys );
	
	public void createRandomCircles( int numberOfCircles ) throws FeatureNotUsingException
	{
		throw new FeatureNotUsingException();
	}
	
	public World getWorld() throws FeatureNotUsingException
	{
		throw new FeatureNotUsingException( featureNotActivated );
	}
	
	public void setWorld( World scene ) throws FeatureNotUsingException
	{
		throw new FeatureNotUsingException( featureNotActivated );
	}
	
	public Vec3 getDefaultColor() throws FeatureNotUsingException
	{
		throw new FeatureNotUsingException( featureNotActivated );
	}
	
	public void setDefaultColor( Vec3 defaultColor ) throws FeatureNotUsingException
	{
		throw new FeatureNotUsingException( featureNotActivated );
	}
	
	public int getRecursiveDepth() throws FeatureNotUsingException
	{
		throw new FeatureNotUsingException( featureNotActivated );
	}
	
	public void setRecursiveDepth( int depth ) throws FeatureNotUsingException
	{
		throw new FeatureNotUsingException( featureNotActivated );
	}
	
	public void setTransformation( Mat4 transformation ) throws FeatureNotUsingException
	{
		throw new FeatureNotUsingException( featureNotActivated );
	}
	
	public void addTransformation( Mat4 transformation ) throws FeatureNotUsingException
	{
		throw new FeatureNotUsingException( featureNotActivated );
	}
	
	public Mat4 getTransformation() throws FeatureNotUsingException
	{
		throw new FeatureNotUsingException( featureNotActivated );
	}
	
	public void resetTransformation() throws FeatureNotUsingException
	{
		throw new FeatureNotUsingException( featureNotActivated );
	}
	
	public void setScene( Shape scene ) throws FeatureNotUsingException
	{
		throw new FeatureNotUsingException( featureNotActivated );
	}
	
	public Shape getScene() throws FeatureNotUsingException
	{
		throw new FeatureNotUsingException( featureNotActivated );
	}
}
