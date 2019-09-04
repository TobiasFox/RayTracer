/**
 * 
 */

package raytracer.imageCreator.shape.withColor;

import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.BoundingBox;
import raytracer.imageCreator.shape.Shape;

/**
 * @author Tobias Fox
 */
public class Sphere implements Shape
{
	
	public final Vec3 center;
	public final double radius;
	public final Vec3 color;
	
	public Sphere( Vec3 center, double radius, Vec3 color )
	{
		this.center = center;
		this.radius = radius;
		this.color = color;
	}
	
	@Override
	public Hit calculateHit( Ray ray )
	{
		double p = ray.direction.scale( 2 ).scalarProduct( ray.origin.sub( center ) );
		Vec3 tr = ray.origin.sub( center );
		double q = tr.scalarProduct( tr ) - radius * radius;
		double p2 = p / 2;
		
		double diskriminante = ( p2 * p2 ) - q;
		if ( diskriminante < 0 )
			return null;
		
		double k = -p2;
		double sqrt = Math.sqrt( diskriminante );
		double t1 = k + sqrt;
		double t2 = k - sqrt;
		double t = t1 <= t2 ? t1 : t2;
		Vec3 position = ray.origin.add( ray.direction.scale( t ) );
		if ( t < 0 )
			return null;
		return new Hit( t, position, color );
	}

	@Override
	public BoundingBox getBoundingBox()
	{
		return null;
	}
}