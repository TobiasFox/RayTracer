/**
 * 
 */

package raytracer.imageCreator.shape.withMaterial.material;

import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.withMaterial.Hit;
import raytracer.imageCreator.shape.withMaterial.texture.Texture;

/**
 * @author Tobias Fox
 *
 */
public class PerfectDiffuseMaterial implements Material
{
	
	private static final Vec3 EMISSION = new Vec3( 0, 0, 0 );
	private double accuracy;
	private Texture albedo;
	
	public PerfectDiffuseMaterial( Texture albedo )
	{
		this( albedo, 1 );
	}
	
	public PerfectDiffuseMaterial( Texture albedo, double accuracy )
	{
		this.albedo = albedo;
		this.accuracy = accuracy;
	}
	
	@Override
	public Vec3 getEmission( Ray ray, Hit hit )
	{
		return EMISSION;
	}
	
	@Override
	public boolean isScattered()
	{
		return true;
	}
	
	@Override
	public Ray getScatteredRay( Hit hit, Ray ray )
	{
		Vec3 direction = hit.surfaceNormal.add( MaterialCalculationHelper.getRandomPoint( accuracy ) ).normalize();
		return new Ray( hit.position, direction, 0.0001, Double.POSITIVE_INFINITY );
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
	public Vec3 getAlbedo( Hit hit )
	{
		return albedo.sample( hit.textureCoordinate );
	}
	
	@Override
	public boolean isSpecular()
	{
		return false;
	}
	
}
