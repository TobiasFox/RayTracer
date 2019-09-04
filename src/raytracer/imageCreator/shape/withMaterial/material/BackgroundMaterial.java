/**
 * 
 */

package raytracer.imageCreator.shape.withMaterial.material;

import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.withMaterial.Hit;
import raytracer.imageCreator.shape.withMaterial.texture.ConstantTexture;
import raytracer.imageCreator.shape.withMaterial.texture.Texture;

/**
 * @author Tobias Fox
 *
 */
public class BackgroundMaterial implements Material
{
	
	private int numberOfStripes = 35;
	private Texture emission;
	
	public BackgroundMaterial()
	{
		this.emission = new ConstantTexture( new Vec3( 217, 217, 217 ).toRGB() );
	}
	
	public BackgroundMaterial( Texture emission )
	{
		this.emission = emission;
		
	}
	
	@Override
	public Vec3 getEmission( Ray ray, Hit hit )
	{
		return emission.sample( hit.textureCoordinate );
		// Vec3 colorOne = new Vec3(50, 205, 50).toRGB();
		// Vec3 colorTwo = new Vec3(25, 25, 112).toRGB();
		// Vec3 colorOne = new Vec3( 230, 240, 255 ).toRGB();
		// Vec3 colorTwo = new Vec3( 153, 194, 255 ).toRGB();
		// return striped( ray.direction.y, colorOne, colorTwo );
		// return linInterpolation(ray.direction.y, colorOne, colorTwo);
		// return new Vec3(198, 226, 255).toRGB();
		// return new Vec3(.8, 0.95294117647, 1);
		// return new Vec3(77, 166, 255).toRGB();
	}
	
	private Vec3 linInterpolation( double yValue, Vec3 colorOne, Vec3 colorTwo )
	{
		double t = yValue + .5;
		return colorOne.scale( 1 - t ).add( colorTwo.scale( t ) );
	}
	
	private Vec3 striped( double yValue, Vec3 colorOne, Vec3 colorTwo )
	{
		double calcY = yValue * numberOfStripes;
		
		if ( ( ( (int) calcY ) % 2 ) == 0 )
		{
			if ( calcY < 0 )
			{
				return colorOne;
			}
			else
			{
				return colorTwo;
			}
		}
		else
		{
			if ( calcY < 0 )
			{
				return colorTwo;
			}
			else
			{
				return colorOne;
			}
		}
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
