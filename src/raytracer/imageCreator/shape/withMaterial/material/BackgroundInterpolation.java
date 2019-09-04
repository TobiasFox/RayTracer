
package raytracer.imageCreator.shape.withMaterial.material;

import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.withMaterial.Hit;
import raytracer.imageCreator.shape.withMaterial.texture.InterpolateTexture;
import raytracer.imageCreator.shape.withMaterial.texture.Texture;

public class BackgroundInterpolation implements Material
{
	
	private Texture emission;
	
	public BackgroundInterpolation( Vec3 colorOne, Vec3 colorTwo )
	{
		emission = new InterpolateTexture( colorOne, colorTwo );
	}
	
	public BackgroundInterpolation()
	{
		Vec3 colorOne = new Vec3( 230, 240, 255 ).toRGB();
		Vec3 colorTwo = new Vec3( 153, 194, 255 ).toRGB();
		emission = new InterpolateTexture( colorOne, colorTwo );
	}
	
	@Override
	public Vec3 getEmission( Ray ray, Hit hit )
	{
		return emission.sample( ray.direction );
	}
	
	@Override
	public boolean isScattered()
	{
		return false;
	}
	
	@Override
	public Ray getScatteredRay( Hit hit, Ray ray )
	{
		// wird nie ausgeführt
		return null;
	}
	
	@Override
	public Vec3 getAlbedo( Hit hit )
	{
		// wird nie ausgeführt
		return null;
	}
	
	@Override
	public boolean isSpecular()
	{
		return true;
	}
	
}
