/**
 * 
 */

package raytracer.imageCreator.shape.withMaterial;

import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.BoundingBox;
import raytracer.imageCreator.shape.Shape;
import raytracer.imageCreator.shape.withMaterial.material.Material;

/**
 * @author Tobias Fox
 *
 */
public class Zylinder implements Shape
{
	
	public Vec3 center;
	public final double radius;
	public final double height;
	private Material materialBody;
	private Material materialTop;
	private Material materialBottom;
	private BoundingBox boundingBox;
	private static final Vec3 UPPERPLATE = new Vec3( 0, 1, 0 );
	private static final Vec3 LOWERPLATE = new Vec3( 0, -1, 0 );
	
	public Zylinder( double radius, double height, Material material )
	{
		this( new Vec3( 0, 0, 0 ), radius, height, material, material, material );
	}
	
	public Zylinder( double radius, double height, Material material, Material top, Material bottom )
	{
		this( new Vec3( 0, 0, 0 ), radius, height, material, top, bottom );
	}
	
	public Zylinder( Vec3 center, double radius, double height, Material material )
	{
		this( center, radius, height, material, material, material );
	}
	
	public Zylinder( Vec3 center, double radius, double height, Material material, Material top, Material bottom )
	{
		this.center = center;
		this.radius = radius;
		this.height = height;
		this.materialBody = material;
		this.materialTop = top;
		this.materialBottom = bottom;
		Vec3 min = new Vec3( -radius, -height, -radius );
		boundingBox = new BoundingBox( min, min.scale( -1 ) );
	}
	
	@Override
	public Hit calculateHit( Ray ray )
	{
		double x0 = ray.origin.x - center.x;
		double z0 = ray.origin.z - center.z;
		
		double a = ( ray.direction.x * ray.direction.x ) + ( ray.direction.z * ray.direction.z );
		double b = 2 * ( ( x0 * ray.direction.x ) + ( z0 * ray.direction.z ) );
		double c = ( ( x0 * x0 ) + ( z0 * z0 ) ) - ( radius * radius );
		
		double diskriminante = ( b * b ) - ( 4 * a * c );
		if ( diskriminante < 0 )
		{
			return null;
		}
		
		double sqrt = Math.sqrt( diskriminante );
		double t0 = ( -b + sqrt ) / ( 2 * a );
		double t1 = ( -b - sqrt ) / ( 2 * a );
		
		if ( t0 > t1 )
		{
			double tmp = t0;
			t0 = t1;
			t1 = tmp;
		}
		
		double y0 = ( ray.origin.y - center.y ) + ( t0 * ray.direction.y );
		double y1 = ( ray.origin.y - center.y ) + ( t1 * ray.direction.y );
		
		if ( noHit( y0, y1 ) )
		{
			return null;
		}
		else if ( ( y0 > height ) && ( y1 < height ) )
		{
			// hit upper plate
			double th = t0 + ( ( ( t1 - t0 ) * ( y0 - height ) ) / ( y0 - y1 ) );
			if ( ( th <= 0 ) || !ray.isInsideOfBounds( th ) )
			{
				return null;
			}
			
			Vec3 position = ray.pointAt( th );
			return new Hit( th, position, UPPERPLATE, materialTop, null );
		}
		else if ( ( y0 < -height ) && ( y1 > -height ) )
		{
			// hit lower plate
			double th = t0 + ( ( ( t1 - t0 ) * ( y0 + height ) ) / ( y0 - y1 ) );
			if ( ( th <= 0 ) || !ray.isInsideOfBounds( th ) )
			{
				return null;
			}
			
			Vec3 position = ray.pointAt( th );
			return new Hit( th, position, LOWERPLATE, materialBottom, null );
		}
		else
		{
			// hit body
			if ( ( t0 <= 0 ) || !ray.isInsideOfBounds( t0 ) )
			{
				return null;
			}
			
			Vec3 position = ray.pointAt( t0 );
			Vec3 normal = new Vec3( position.x, 0, position.z );
			return new Hit( t0, position, normal.normalize(), materialBody, null );
		}
	}
	
	private boolean noHit( double y0, double y1 )
	{
		return ( ( y0 > height ) && ( y1 > height ) ) || ( ( y0 < -height ) && ( y1 < -height ) );
	}
	
	public Material getMaterial()
	{
		return materialBody;
	}
	
	public void setMaterial( Material materialBody )
	{
		this.materialBody = materialBody;
	}
	
	public Material getMaterialTop()
	{
		return materialTop;
	}
	
	public void setMaterialTop( Material materialTop )
	{
		this.materialTop = materialTop;
	}
	
	public Material getMaterialBottom()
	{
		return materialBottom;
	}
	
	public void setMaterialBottom( Material materialBottom )
	{
		this.materialBottom = materialBottom;
	}
	
	@Override
	public BoundingBox getBoundingBox()
	{
		return boundingBox;
	}
	
}