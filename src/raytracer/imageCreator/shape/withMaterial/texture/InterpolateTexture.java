
package raytracer.imageCreator.shape.withMaterial.texture;

import raytracer.imageCreator.general.Vec3;

public class InterpolateTexture implements Texture
{
	
	private Vec3 colorOne;
	private Vec3 colorTwo;
	
	public InterpolateTexture( Vec3 colorOne, Vec3 colorTwo )
	{
		this.colorOne = colorOne;
		this.colorTwo = colorTwo;
	}
	
	@Override
	public Vec3 sample( Vec3 uv )
	{
		double t = uv.y + .5;
		return colorOne.scale( 1 - t ).add( colorTwo.scale( t ) );
	}
	
}
