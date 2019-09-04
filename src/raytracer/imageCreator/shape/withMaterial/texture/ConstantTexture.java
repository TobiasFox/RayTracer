/**
 * 
 */

package raytracer.imageCreator.shape.withMaterial.texture;

import raytracer.imageCreator.general.Vec3;


/**
 * @author Tobias Fox
 *
 */
public class ConstantTexture implements Texture
{
	
	private Vec3 color;
	
	public ConstantTexture( Vec3 color )
	{
		this.color = color;
	}
	
	@Override
	public Vec3 sample( Vec3 uv )
	{
		return color;
	}
	
	
	public Vec3 getColor()
	{
		return color;
	}
	
	
	public void setColor( Vec3 color )
	{
		this.color = color;
	}
	
	
}
