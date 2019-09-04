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
public class ReflectiveMaterial implements Material
{
	
	private double accuracy;
	private Texture emission;
	private Texture albedo;
	
	/**
	 * Default constructor. No rough ReflectiveMaterial, no coloremission
	 */
	public ReflectiveMaterial()
	{
		this( 0, new ConstantTexture( MaterialCalculationHelper.ZEROVEC ),
				new ConstantTexture( MaterialCalculationHelper.ONEVEC ) );
	}
	// public ReflectiveMaterial()
	// {
	// this( 0, new ConstantTexture( MaterialCalculationHelper.ZEROVEC ),
	// new ConstantTexture( MaterialCalculationHelper.ONEVEC ) );
	// }
	// new ConstantTexture( new Vec3( 243, 94, 19 ).toRGB() ) ) )
	
	/**
	 * constructor: No rough ReflectiveMaterial, specified color
	 * 
	 * material reflektiert und strahlt die angegebene farbe aus
	 * 
	 * @param emission
	 */
	public ReflectiveMaterial( Texture emission )
	{
		this( 0, emission, new ConstantTexture( MaterialCalculationHelper.ONEVEC ) );
	}
	
	/**
	 * Constructor with parameter how rough the reflective material is and the
	 * color of emission
	 * 
	 * @param accuracy
	 * @param emission
	 */
	public ReflectiveMaterial( double accuracy, Texture emission )
	{
		this( accuracy, emission, new ConstantTexture( MaterialCalculationHelper.ONEVEC ) );
	}
	
	/**
	 * Constructor with parameter no rough reflective material, the
	 * color of emission and the albedo factor
	 * 
	 * @param emission
	 * @param emission
	 */
	public ReflectiveMaterial( Texture emission, Texture albedo )
	{
		this( 0, emission, albedo );
	}
	
	/**
	 * Constructor with parameter how rough the reflective material is, the
	 * color of emission and the albedo factor
	 * 
	 * @param accuracy
	 * @param emission
	 * @param emission
	 */
	public ReflectiveMaterial( double accuracy, Texture emission, Texture albedo )
	{
		this.accuracy = accuracy;
		this.emission = emission;
		this.albedo = albedo;
	}
	
	@Override
	public Ray getScatteredRay( Hit hit, Ray ray )
	{
		Vec3 reflect = MaterialCalculationHelper.reflect( ray.direction, hit.surfaceNormal, accuracy );
		if ( reflect == null )
		{
			return null;
		}
		return new Ray( hit.position, reflect, 0.001, Double.POSITIVE_INFINITY );
	}
	
	@Override
	public Vec3 getAlbedo( Hit hit )
	{
		return albedo.sample( null );
	}
	
	@Override
	public boolean isScattered()
	{
		return true;
	}
	
	@Override
	public Vec3 getEmission( Ray ray, Hit hit )
	{
		return emission.sample( hit.textureCoordinate );
	}
	
	public double getAccuracy()
	{
		return accuracy;
	}
	
	public void setAccuracy( double accuracy )
	{
		this.accuracy = accuracy;
	}
	
	@Override
	public boolean isSpecular()
	{
		return true;
	}
	
}
