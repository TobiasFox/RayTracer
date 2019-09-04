/**
 * 
 */

package raytracer.imageCreator.shape.withMaterial;

import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.BoundingBox;
import raytracer.imageCreator.shape.Shape;
import raytracer.imageCreator.shape.withMaterial.material.BackgroundMaterial;
import raytracer.imageCreator.shape.withMaterial.material.Material;
import raytracer.imageCreator.shape.withMaterial.texture.UVHelper;

/**
 * @author Tobias Fox
 *
 */
public class Background implements Shape
{
	
	private Material material;
	
	public Background()
	{
		material = new BackgroundMaterial();
	}
	public Background( Material material )
	{
		this.material = material;
	}
	
	@Override
	public Hit calculateHit( Ray ray )
	{
		Vec3 position = new Vec3( Double.POSITIVE_INFINITY * ray.direction.x,
				Double.POSITIVE_INFINITY * ray.direction.y, Double.POSITIVE_INFINITY * ray.direction.z );
		
		Vec3 textureCoordinate = UVHelper.calcUVBGTexture( ray.direction );
		textureCoordinate = new Vec3( textureCoordinate.x, textureCoordinate.y, textureCoordinate.z );
		
		return new Hit( Double.POSITIVE_INFINITY, position, new Vec3( 0, 0, 0 ), material, textureCoordinate );
	}
	
	@Override
	public BoundingBox getBoundingBox()
	{
		return null;
	}
	
	public Material getMaterial()
	{
		return material;
	}
	
	public void setMaterial( Material material )
	{
		this.material = material;
	}
	
}
