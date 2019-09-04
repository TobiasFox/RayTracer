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
 *
 */
public class Background implements Shape
{
	
	private int numberOfStripes = 35;
	
	@Override
	public Hit calculateHit( Ray ray )
	{
		Vec3 position = new Vec3( Double.POSITIVE_INFINITY * ray.direction.x,
				Double.POSITIVE_INFINITY * ray.direction.y, Double.POSITIVE_INFINITY * ray.direction.z );
		
		double calcY = ray.direction.y * numberOfStripes;
		// calcColor(calcY);
		Vec3 color = test( ray.direction );
		
		return new Hit( Double.POSITIVE_INFINITY, position, color );
	}
	
	private Vec3 test( Vec3 direction )
	{
		Vec3 one = new Vec3( 50, 205, 50 ).toRGB();
		Vec3 two = new Vec3( 25, 25, 112 ).toRGB();
		
		Vec3 a = direction.add( new Vec3( .5, .5, .5 ) );
		Vec3 richtung = new Vec3( 0, 1, 0 );
		// Vec3 end = direction.ad d(new Vec3(.5, .5, .5));
		
		double t = direction.y + .5;
		
		return one.scale( 1 - t ).add( two.scale( t ) );
		
		// return two.scale(-1 - (direction.y) / -2).add(one.scale((direction.y)
		// - 1 /
		// -2));
		
		// double b = one.scale(1 - a.x).add(two.scale(a.x));
		// double c = one.scale(1 - a.y).add(two.scale(a.y));
		// double d = one.scale(1 - a.z).add(two.scale(a.z));
		// double b = richtung.x * ((1 - a.x) * one.x + a.x * two.x);
		// double c = richtung.y * ((1 - a.y) * one.y + a.y * two.y);
		// double d = richtung.z * ((1 - a.z) * one.z + a.z * two.z);
		
		// double c = (1 - a.y) * one.y + a.y * two.y;
		// double d = (1 - a.z) * one.z + a.z * two.z;
		// return new Vec3(b, c, d);
	}
	
	private Vec3 calcColor( double calcY )
	{
		Vec3 one = new Vec3( 50, 205, 50 ).toRGB();
		Vec3 two = new Vec3( 25, 25, 112 ).toRGB();
		
		if ( ( ( (int) calcY ) % 2 ) == 0 )
		{
			if ( calcY < 0 )
			{
				return one;
			}
			else
			{
				return two;
			}
		}
		else
		{
			if ( calcY < 0 )
			{
				return two;
			}
			else
			{
				return one;
			}
		}
		
		// return new Vec3(colorPercentage(50), colorPercentage(205),
	}
	
	public static void main( String[] a )
	{
		Shape bg = new Background();
		bg.calculateHit( new Ray( new Vec3( 0, 0, 0 ), new Vec3( 0, 1, 0 ), 0, Double.POSITIVE_INFINITY ) );
	}
	
	@Override
	public BoundingBox getBoundingBox()
	{
		return null;
	}
	
}
