/**
 * 
 */

package raytracer.imageCreator.sampling.pinholeCamera;

import raytracer.imageCreator.general.Vec3;

/**
 * @author Tobias Fox
 *
 */
public class Ray
{
	
	public final Vec3 origin;
	public final Vec3 direction;
	public final double upperBound;
	public final double lowerBound;
	
	public Ray( Vec3 origin, Vec3 direction, double lowerBound, double upperBound )
	{
		this.origin = origin;
		this.direction = direction.normalize();
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	
	public Vec3 pointAt( double t )
	{
		if ( isInsideOfBounds( t ) )
		{
			if ( t == 0 )
			{
				return origin;
			}
			else if ( t == 1 )
			{
				return origin.add( direction );
			}
		}
		return origin.add( direction.scale( t ) );
	}
	
	public boolean isInsideOfBounds( double t )
	{
		return ( t >= lowerBound ) && ( t <= upperBound );
	}
}
