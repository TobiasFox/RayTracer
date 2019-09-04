/**
 * 
 */

package raytracer.imageCreator.sampling.colorcalculation;

import raytracer.imageCreator.World;
import raytracer.imageCreator.general.Mat4;
import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.PinholeCamera;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.withMaterial.Hit;
import raytracer.imageCreator.shape.withMaterial.light.Light;
import raytracer.imageCreator.shape.withMaterial.light.LightData;
import raytracer.imageCreator.shape.withMaterial.material.Material;

/**
 * @author Tobias Fox
 *
 */
class ColorCalculationMonteCarloRay extends ColorCalculation
{
	
	private double angle;
	private int depth = 7;
	
	private World world;
	
	protected PinholeCamera camera;
	
	public ColorCalculationMonteCarloRay( int width, int height, double alpha )
	{
		super( width, height );
		this.angle = alpha;
		camera = new PinholeCamera( width, height, alpha );
	}
	
	@Override
	public Vec3 pixelColor( double xs, double ys )
	{
		return pixelColor( xs, ys, depth );
	}
	
	private Vec3 pixelColor( double xs, double ys, int depth )
	{
		Ray ray = camera.generateRay( xs, ys, 0.001, Double.POSITIVE_INFINITY );
		Vec3 color = radiance( ray, depth );
		return color.reCalculateVec3WithLowerBound( 0 );
	}
	
	private Vec3 radiance( Ray ray, int depth )
	{
		Hit hit = (Hit) world.calculateHit( ray );
		if ( ( hit == null ) || ( hit.material == null ) )
		{
			hit = world.getBackground().calculateHit( ray );
		}
		Material mat = hit.material;
		Vec3 emission = mat.getEmission( ray, hit );
		if ( mat.isScattered() && ( depth > 0 ) )
		{
			Ray scattered = mat.getScatteredRay( hit, ray );
			if ( scattered == null )
			{
				return emission;
			}
			
			Vec3 lightInfluens = calcLight( hit );
			return emission
					.add( ( radiance( scattered, depth - 1 ).add( lightInfluens ) ).scaleAll( mat.getAlbedo( hit ) ) );
		}
		return emission;
	}
	
	private Vec3 calcLight( Hit hit )
	{
		Vec3 lightInfluens = new Vec3( 0, 0, 0 );
		if ( !hit.material.isSpecular() )
		{
			for ( Light lightSource : world.getLightSources() )
			{
				LightData lData = lightSource.sample( world, hit.position );
				
				if ( lData != null )
				{
					Vec3 adding = lData.radiance.scale( hit.surfaceNormal.scalarProduct( lData.intensity ) );
					lightInfluens = lightInfluens.add( adding );
				}
			}
		}
		return lightInfluens;
	}
	
	@Override
	public World getWorld()
	{
		return world;
	}
	
	@Override
	public void setWorld( World scene )
	{
		this.world = scene;
	}
	
	@Override
	public int getRecursiveDepth()
	{
		return depth;
	}
	
	@Override
	public void setRecursiveDepth( int depth )
	{
		this.depth = depth;
	}
	
	@Override
	public void setTransformation( Mat4 transformation )
	{
		camera.setTransform( transformation );
	}
	
	@Override
	public void addTransformation( Mat4 transformation )
	{
		camera.addTransformation( transformation );
	}
	
	@Override
	public Mat4 getTransformation()
	{
		return camera.getTransformation();
	}
	
	@Override
	public void resetTransformation()
	{
		camera.resetTransform();
	}
	
}
