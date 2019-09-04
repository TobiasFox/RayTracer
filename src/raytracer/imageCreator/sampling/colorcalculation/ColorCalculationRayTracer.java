/**
 * 
 */

package raytracer.imageCreator.sampling.colorcalculation;

import raytracer.imageCreator.exceptions.FeatureNotUsingException;
import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.PinholeCamera;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.Shape;
import raytracer.imageCreator.shape.withColor.Hit;

/**
 * @author Tobias Fox
 *
 */
class ColorCalculationRayTracer extends ColorCalculation
{
	
	private double angle = Math.PI / 2;
	
	protected Shape scene;
	
	protected PinholeCamera camera;
	
	public ColorCalculationRayTracer( int width, int height, double alpha )
	{
		super( width, height );
		this.angle = alpha;
		camera = new PinholeCamera( width, height, alpha );
	}
	
	@Override
	public Vec3 pixelColor( double xs, double ys )
	{
		Ray ray = camera.generateRay( xs, ys, 0, Double.POSITIVE_INFINITY );
		
		Hit h = (Hit) scene.calculateHit( ray );
		if ( h != null )
		{
			return h.color;
		}
		return defaultColor;
	}
	@Override
	public void setScene( Shape scene ) throws FeatureNotUsingException
	{
		this.scene = scene;
	}
	
	@Override
	public Shape getScene()
	{
		return scene;
	}
}