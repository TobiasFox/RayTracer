/**
 * 
 */

package raytracer.imageCreator.shape.withMaterial;

import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.BoundingBox;
import raytracer.imageCreator.shape.Shape;
import raytracer.imageCreator.shape.withMaterial.interfaces.IShapeMaterialMulti;
import raytracer.imageCreator.shape.withMaterial.material.Material;

/**
 * @author Tobias Fox
 * 
 */
public class Cone implements Shape, IShapeMaterialMulti
{
	
	private static final Vec3 NORMALBOTTOM = new Vec3( 0, -1, 0 );
	private final double radius;
	private final double height;
	private Material materialBody;
	private Material materialBottom;
	private BoundingBox boundingBox;
	
	public Cone( double radius, double height, Material material )
	{
		this( radius, height, material, material );
	}
	
	public Cone( double radius, double height, Material material, Material bottom )
	{
		this.radius = radius;
		this.height = height;
		this.materialBody = material;
		this.materialBottom = bottom;
		boundingBox = new BoundingBox( new Vec3( -radius, -height, -radius ), new Vec3( radius, 0, radius ) );
	}
	
	@Override
	public Hit calculateHit( Ray ray )
	{
		double originx = ray.origin.x;
		double originy = ray.origin.z;
		
		// dependency between height and radius
		double percentHeight = radius / height;
		double originz = ray.origin.y * percentHeight;
		double directionZWithPercentHeight = ray.direction.y * percentHeight;
		
		double a = ( ( ray.direction.x * ray.direction.x ) + ( ray.direction.z * ray.direction.z ) )
				- ( directionZWithPercentHeight * directionZWithPercentHeight );
		double b = 2 * ( ( ( originx * ray.direction.x ) + ( originy * ray.direction.z ) )
				- ( originz * ray.direction.y * percentHeight ) );
		double c = ( ( originx * originx ) + ( originy * originy ) ) - ( originz * originz );
		
		double diskriminante = Math.pow( b, 2 ) - ( 4 * a * c );
		// keine lösung der gleichung
		if ( diskriminante < 0 )
		{
			return null;
		}
		
		// zwei ergebniss der quadratischen gleichung t0 und t1
		double sqrt = Math.sqrt( diskriminante );
		double t0 = ( -b + sqrt ) / ( 2 * a );
		double t1 = ( -b - sqrt ) / ( 2 * a );
		
		double result = checkBoundaries( t0, t1, ray );
		if ( Double.isInfinite( result ) )
		{
			// result is outside of boundaries
			return null;
		}
		
		// height restriction with value of y
		Vec3 position = ray.pointAt( result );
		if ( ( position.y > 0 ) || ( position.y < -height ) )
		{
			return null;
		}
		
		// hit with bottom plate
		double tPlate = ( -height - ray.origin.y ) / ray.direction.y;
		if ( ray.isInsideOfBounds( tPlate ) )
		{
			if ( !ray.isInsideOfBounds( tPlate ) )
			{
				return null;
			}
			Vec3 position3 = ray.pointAt( tPlate );
			double distance = ( position3.x * position3.x ) + ( position3.z * position3.z );
			if ( distance <= ( radius * radius ) )
			{
				if ( tPlate < result )
				{
					// hit is closer than body hit
					return new Hit( tPlate, ray.pointAt( tPlate ), NORMALBOTTOM, materialBottom, null );
				}
			}
		}
		
		// normal body hit
		if ( !ray.isInsideOfBounds( result ) )
		{
			return null;
		}
		Vec3 hitPoint = ray.pointAt( result );
		Vec3 normale = calcNormale( hitPoint );
		return new Hit( result, hitPoint, normale.normalize(), materialBody, null );
	}
	
	private Vec3 calcNormale( Vec3 hitPoint )
	{
		// this calculation is for the height of the cone between 0 and height, but the cone lies between -height and 0,
		// so there is some transforming
		Vec3 newHitPoint = new Vec3( hitPoint.x, height + hitPoint.y, hitPoint.z );
		Vec3 center = new Vec3( 0, newHitPoint.y, 0 );
		
		// finding the angle between the normal and the line which contains center and hitPoint
		double length = center.sub( newHitPoint ).length;
		double alpha = Math.atan( ( height - center.y ) / length );
		
		// calculate the difference on y axis between the normalPoint and center
		double heightDifference = Math.tan( Math.toRadians( 90 ) - alpha ) * length;
		
		// converting back to cone corrdinates
		double centerDifferenceWithConeHeight = center.y - heightDifference - height;
		Vec3 point = new Vec3( 0, centerDifferenceWithConeHeight, 0 );
		return hitPoint.sub( point );
	}
	
	/*
	 * checks the boundaries from both hit points and return the smallest non-negative value.
	 * if both are outside of the boundaries or not visible on the cone, Double.NEGATIVE_INFINITY is returned
	 */
	private double checkBoundaries( double t0, double t1, Ray ray )
	{
		if ( !ray.isInsideOfBounds( t0 ) && !ray.isInsideOfBounds( t1 ) )
		{
			return Double.NEGATIVE_INFINITY;
		}
		else if ( ray.isInsideOfBounds( t0 ) && ray.isInsideOfBounds( t1 ) )
		{
			Vec3 position = ray.pointAt( t0 );
			Vec3 position2 = ray.pointAt( t1 );
			
			boolean positionisVisible = ( position.y <= 0 ) && ( position.y >= -height );
			boolean position2isVisible = ( position2.y <= 0 ) && ( position2.y >= -height );
			if ( positionisVisible && position2isVisible )
			{
				return t1 < t0 ? t1 : t0;
			}
			else if ( positionisVisible )
			{
				return t0;
			}
			else if ( position2isVisible )
			{
				return t1;
			}
			else
			{
				return Double.NEGATIVE_INFINITY;
			}
			
		}
		else if ( ray.isInsideOfBounds( t1 ) )
		{
			return t1;
		}
		else
		{
			return t0;
		}
	}
	
	@Override
	public Material[] getMaterials()
	{
		return null;
	}
	
	@Override
	public Material getMaterial( int index )
	{
		return null;
	}
	
	@Override
	public void setAllMaterials( Material[] material )
	{
		
	}
	
	@Override
	public void setMaterial( int index, Material material )
	{
		
	}
	
	@Override
	public BoundingBox getBoundingBox()
	{
		return boundingBox;
	}
	
}