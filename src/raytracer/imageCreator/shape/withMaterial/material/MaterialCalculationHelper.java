/**
 * 
 */

package raytracer.imageCreator.shape.withMaterial.material;

import raytracer.imageCreator.general.Random;
import raytracer.imageCreator.general.Vec3;

/**
 * @author Tobias Fox
 *
 */
class MaterialCalculationHelper
{
	
	public static final Vec3 ZEROVEC = new Vec3( 0, 0, 0 );
	public static final Vec3 ONEVEC = new Vec3( 1, 1, 1 );
	
	public static Vec3 getRandomPoint( double accuracy )
	{
		double x;
		double y;
		double z;
		Vec3 randomPosition;
		int count = 0;
		while ( count++ < 100 )
		{
			x = ( Random.random() * 2. * accuracy ) - accuracy;
			y = ( Random.random() * 2. * accuracy ) - accuracy;
			z = ( Random.random() * 2. * accuracy ) - accuracy;
			randomPosition = new Vec3( x, y, z );
			
			if ( randomPosition.scalarProduct( randomPosition ) <= ( accuracy * accuracy ) )
			{
				return randomPosition;
			}
		}
		throw new RuntimeException( "too many tries" );
	}
	
	public static Vec3 reflect( Vec3 direction, Vec3 normal, double accuracy )
	{
		Vec3 reflectDirection = direction.sub( normal.scale( 2 * direction.scalarProduct( normal ) ) );
		// std accuracy = 0, no rough surface -> perfect reflecting
		Vec3 randomPoint = ZEROVEC;
		if ( accuracy != 0 )
		{
			randomPoint = getRandomPoint( accuracy );
			reflectDirection = reflectDirection.add( randomPoint.scale( accuracy ) );
			if ( reflectDirection.scalarProduct( normal ) < 0 )
			{
				// absorb ray
				return null;
			}
		}
		return reflectDirection.normalize();
	}
}
