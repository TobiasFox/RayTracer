
package raytracer.imageCreator.shape.withMaterial.material;

import raytracer.imageCreator.ImageCreator;
import raytracer.imageCreator.general.Random;
import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.withMaterial.Hit;

/**
 * @author Tobias Fox
 *
 */
public class GlassMaterial implements Material
{
	
	private Density density;
	private double accuracy;
	
	public GlassMaterial( Density density )
	{
		this( density, 0 );
	}
	
	public GlassMaterial( Density density, double accuracy )
	{
		this.density = density;
		this.accuracy = accuracy;
	}
	
	@Override
	public Ray getScatteredRay( Hit hit, Ray ray )
	{
		Vec3 normal = hit.surfaceNormal;
		double densityIndex = ImageCreator.DEFAULTDENSITY.value() / density.value();
		if ( normal.scalarProduct( ray.direction ) > 0 )
		{
			// ray is from outside
			normal = normal.scale( -1 );
			densityIndex = density.value() / ImageCreator.DEFAULTDENSITY.value();
		}
		
		double scalarProduct = normal.scalarProduct( ray.direction );
		if ( isRefract( densityIndex, scalarProduct ) )
		{
			// has to negate scalarProduct for this calculation
			double schlicks = schlick( ray.direction, normal, -scalarProduct );
			if ( schlicks < Random.random() )
			{
				return new Ray( hit.position, refract( ray.direction, normal, densityIndex, scalarProduct ), 0.001,
						Double.POSITIVE_INFINITY );
			}
		}
		Vec3 reflect = MaterialCalculationHelper.reflect( ray.direction, normal, accuracy );
		if ( reflect == null )
		{
			return null;
		}
		return new Ray( hit.position, reflect, 0.001, Double.POSITIVE_INFINITY );
	}
	
	private double schlick( Vec3 direction, Vec3 surfaceNormal, double scalarProduct )
	{
		double densityDevide = ( ImageCreator.DEFAULTDENSITY.value() - density.value() )
				/ ( ImageCreator.DEFAULTDENSITY.value() + density.value() );
		double r0 = densityDevide * densityDevide;
		double pow = Math.pow( 1 - scalarProduct, 5 );
		return r0 + ( ( 1 - r0 ) * pow );
	}
	
	private boolean isRefract( double densityIndex, double scalarProduct )
	{
		return calcDeterminant( densityIndex, scalarProduct ) >= 0;
	}
	
	private double calcDeterminant( double densityIndex, double scalarProduct )
	{
		return 1 - ( ( densityIndex * densityIndex ) * ( 1 - ( scalarProduct * scalarProduct ) ) );
	}
	
	private Vec3 refract( Vec3 direction, Vec3 normal, double densityIndex, double scalarProduct )
	{
		double det = calcDeterminant( densityIndex, scalarProduct );
		double skalar = ( densityIndex * scalarProduct ) - Math.sqrt( det );
		return direction.scale( densityIndex ).add( normal.scale( skalar ) );
	}
	
	@Override
	public Vec3 getAlbedo( Hit hit )
	{
		return MaterialCalculationHelper.ONEVEC;
	}
	
	@Override
	public boolean isScattered()
	{
		return true;
	}
	
	@Override
	public Vec3 getEmission( Ray ray, Hit hit )
	{
		return MaterialCalculationHelper.ZEROVEC;
	}
	
	public Density getDensity()
	{
		return density;
	}
	
	public void setDensity( Density density )
	{
		this.density = density;
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
