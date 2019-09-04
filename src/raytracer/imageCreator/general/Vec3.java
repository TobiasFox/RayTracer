/**
 * 
 */

package raytracer.imageCreator.general;

import java.awt.Color;

/**
 * @author Tobias Fox
 *
 */
public class Vec3
{
	
	public final double x;
	public final double y;
	public final double z;
	public final double length;
	
	public Vec3( double x, double y, double z )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		length = calcLength();
	}
	
	public Vec3( Color color )
	{
		this.x = color.getRed();
		this.z = color.getGreen();
		this.y = color.getBlue();
		length = calcLength();
	}
	
	public static Vec3 createVec( double x, double y, double z )
	{
		return new Vec3( x, y, z );
	}
	
	public Vec3 add( Vec3 v )
	{
		return new Vec3( x + v.x, y + v.y, z + v.z );
	}
	
	public Vec3 add( double x, double y, double z )
	{
		return new Vec3( this.x + x, this.y + y, this.z + z );
	}
	
	public Vec3 sub( double x, double y, double z )
	{
		return new Vec3( this.x - x, this.y - y, this.z - z );
	}
	
	public Vec3 sub( Vec3 v )
	{
		return new Vec3( x - v.x, y - v.y, z - v.z );
	}
	
	public Vec3 scale( double skalar )
	{
		return new Vec3( x * skalar, y * skalar, z * skalar );
	}
	
	public double scalarProduct( Vec3 v )
	{
		return ( x * v.x ) + ( y * v.y ) + ( z * v.z );
	}
	
	public double angleBetween( Vec3 v )
	{
		double skalarPro = scalarProduct( v );
		return skalarPro / ( length * v.length );
	}
	
	public Vec3 crossProduct( Vec3 v )
	{
		return new Vec3( ( y * v.z ) - ( z * v.y ), ( z * v.x ) - ( x * v.z ), ( x * v.y ) - ( y * v.x ) );
	}
	
	public double crossProductArea( Vec3 v )
	{
		return crossProduct( v ).length;
	}
	
	public Vec3 projection( Vec3 v )
	{
		double skalarPro = scalarProduct( v );
		skalarPro /= v.scalarProduct( v );
		return v.scale( skalarPro );
	}
	
	public Vec3 projection2( Vec3 v )
	{
		double a = length * v.normalize().length * angleBetween( v );
		return v.scale( a );
	}
	
	public Vec3 normalize()
	{
		return new Vec3( x / length, y / length, z / length );
	}
	
	public Vec3 scaleAll( Vec3 other )
	{
		return new Vec3( x * other.x, y * other.y, z * other.z );
	}
	
	public boolean isOrthogonal( Vec3 v )
	{
		double skalarPro = scalarProduct( v );
		return skalarPro == 0;
	}
	
	public Vec3 toRGB()
	{
		return new Vec3( x / 255, y / 255, z / 255 );
	}
	
	public double getValue( int coordinate )
	{
		switch ( coordinate )
		{
			case 0:
				return x;
			case 1:
				return y;
			case 2:
				return z;
			
			default:
				break;
		}
		throw new NullPointerException();
	}
	
	@Override
	public boolean equals( Object obj )
	{
		if ( obj != null )
		{
			if ( obj instanceof Vec3 )
			{
				Vec3 v = (Vec3) obj;
				double delta = 0.001;
				return ( Math.abs( x - v.x ) < delta ) && ( Math.abs( y - v.y ) < delta )
						&& ( Math.abs( z - v.z ) < delta );
			}
		}
		return false;
	}
	
	public Vec3 convertToSphereCoordinates()
	{
		double radius = Math.sqrt( ( x * x ) + ( y * y ) + ( z * z ) );
		double theta = radius < 0. ? 0 : Math.acos( z / radius );
		double phi = Math.atan2( y, x );
		return new Vec3( radius, theta, phi );
	}
	
	public Vec3 convertToCartesianCoordinates()
	{
		double a = x * Math.sin( y ) * Math.cos( z );
		double b = x * Math.sin( y ) * Math.sin( z );
		double c = x * Math.cos( y );
		return new Vec3( a, b, c );
	}
	
	@Override
	public String toString()
	{
		return "Vec3[ " + x + " " + y + " " + z + " ]";
	}
	
	private double calcLength()
	{
		return Math.sqrt( ( x * x ) + ( y * y ) + ( z * z ) );
	}
	
	public Vec3 reCalculateVec3WithLowerBound( int bound )
	{
		double xNew = Math.max( x, bound );
		double yNew = Math.max( y, bound );
		double zNew = Math.max( z, bound );
		return new Vec3( xNew, yNew, zNew );
	}
	
	// parametertransformation
	// schnitt mit gerade
	// schnitt mit ebene
	
}