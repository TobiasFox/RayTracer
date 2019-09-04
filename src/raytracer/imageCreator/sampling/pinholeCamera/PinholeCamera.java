/**
 * 
 */

package raytracer.imageCreator.sampling.pinholeCamera;

import raytracer.imageCreator.general.Mat4;
import raytracer.imageCreator.general.Transformation;
import raytracer.imageCreator.general.Vec3;

/**
 * @author Tobias Fox
 *
 */
public class PinholeCamera
{
	
	private double alpha;
	private double width;
	private double height;
	private Vec3 origin = new Vec3( 0, 0, 0 );
	private Transformation transformation;
	
	public PinholeCamera( double width, double height, double alpha )
	{
		this( width, height, alpha, Mat4.identity );
	}
	
	public PinholeCamera( double width, double height, double alpha, Mat4 transformation )
	{
		this.width = width;
		this.height = height;
		this.alpha = alpha;
		this.transformation = new Transformation( transformation );
	}
	
	public Ray generateRay( double x, double y, double lowerBound, double upperBound )
	{
		double x1 = x - ( width / 2 );
		double y1 = ( height / 2 ) - y;
		double z1 = -( ( height / 2 ) / Math.tan( alpha / 2 ) );
		Vec3 direction = new Vec3( x1, y1, z1 ).normalize();
		return new Ray( origin, transformation.get().transformDirection( direction ), lowerBound, upperBound );
	}
	
	public Mat4 getTransformation()
	{
		return transformation.get();
	}
	
	public void setTransform( Mat4 transformation )
	{
		this.transformation.set( transformation );
		origin = this.transformation.get().transformPoint( origin );
	}
	
	public void addTransformation( Mat4 transformation )
	{
		this.transformation.add( transformation );
		origin = this.transformation.get().transformPoint( origin );
	}
	
	public void resetTransform()
	{
		if ( transformation.get().equals( Mat4.identity ) )
		{
			return;
		}
		origin = transformation.getInvert().transformPoint( origin );
		transformation.reset();
	}
	
}
