/**
 * 
 */

package raytracer.imageCreator.shape.withMaterial.primitives;

import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.BoundingBox;
import raytracer.imageCreator.shape.Shape;
import raytracer.imageCreator.shape.withMaterial.Hit;
import raytracer.imageCreator.shape.withMaterial.interfaces.IShapeMaterialSingle;
import raytracer.imageCreator.shape.withMaterial.material.Material;
import raytracer.imageCreator.shape.withMaterial.texture.UVHelper;

/**
 * @author Tobias Fox
 *
 */
public class Sphere implements Shape, IShapeMaterialSingle
{
	
	public Vec3 center;
	private static final Vec3 ORIGIN = new Vec3( 0, 0, 0 );
	public final double radius;
	private Material material;
	private BoundingBox boundingBox;
	
	public Sphere( Vec3 center, double radius, Material material )
	{
		this.center = center;
		this.radius = radius;
		this.material = material;
		boundingBox = new BoundingBox( new Vec3( center.x - radius, center.y - radius, center.z - radius ),
				new Vec3( center.x + radius, center.y + radius, center.z + radius ) );
	}
	
	public Sphere( double radius, Material material )
	{
		this( ORIGIN, radius, material );
	}
	
	@Override
	public Hit calculateHit( Ray ray )
	{
		
		if ( !boundingBox.intersect( ray ) )
		{
			return null;
		}
		
		double p = ray.direction.scale( 2 ).scalarProduct( ray.origin.sub( center ) );
		double q = ray.origin.sub( center ).scalarProduct( ray.origin.sub( center ) ) - ( radius * radius );
		double pHalf = p / 2;
		
		double diskriminante = ( pHalf * pHalf ) - q;
		
		if ( diskriminante < 0 )
		{
			return null;
		}
		
		double t1 = -pHalf + Math.sqrt( diskriminante );
		double t2 = -pHalf - Math.sqrt( diskriminante );
		double t = t1 <= t2 ? t1 : t2;
		
		if ( ( t < 0 ) || !ray.isInsideOfBounds( t ) )
		{
			return null;
		}
		
		Vec3 position = ray.pointAt( t );
		Vec3 surfaceNormal = position.sub( center ).normalize();
		Vec3 textureCoordinate = UVHelper.calcUVSphereTexture( surfaceNormal );
		
		return new Hit( t, position, surfaceNormal, material, textureCoordinate );
	}
	
	@Override
	public Material getMaterial()
	{
		return material;
	}
	
	@Override
	public void setMaterial( Material material )
	{
		this.material = material;
	}
	
	@Override
	public BoundingBox getBoundingBox()
	{
		return boundingBox;
	}
}