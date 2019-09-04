
package raytracer.imageCreator.shape.withMaterial.material;

import raytracer.imageCreator.general.Random;
import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.withMaterial.Hit;

public class InterpolateMaterial implements Material
{
	
	private Material one;
	private Material two;
	private double chanceOfFirstMaterial;
	private double comparison;
	
	public InterpolateMaterial( Material one, Material two, double chanceOfFirstMaterial )
	{
		this.one = one;
		this.two = two;
		if ( chanceOfFirstMaterial < 0 )
		{
			this.chanceOfFirstMaterial = 0;
		}
		else if ( chanceOfFirstMaterial > 1 )
		{
			this.chanceOfFirstMaterial = 1;
		}
		else
		{
			this.chanceOfFirstMaterial = chanceOfFirstMaterial;
		}
	}
	
	@Override
	public Ray getScatteredRay( Hit hit, Ray ray )
	{
		if ( chanceOfFirstMaterial > comparison )
		{
			return one.getScatteredRay( hit, ray );
		}
		else
		{
			return two.getScatteredRay( hit, ray );
		}
	}
	
	@Override
	public Vec3 getAlbedo( Hit hit )
	{
		if ( chanceOfFirstMaterial > comparison )
		{
			return one.getAlbedo( hit );
		}
		else
		{
			return two.getAlbedo( hit );
		}
	}
	
	@Override
	public boolean isScattered()
	{
		if ( chanceOfFirstMaterial > comparison )
		{
			return one.isScattered();
		}
		else
		{
			return two.isScattered();
		}
	}
	
	@Override
	public Vec3 getEmission( Ray ray, Hit hit )
	{
		comparison = Random.random();
		if ( chanceOfFirstMaterial > comparison )
		{
			return one.getEmission( ray, hit );
		}
		else
		{
			return two.getEmission( ray, hit );
		}
	}
	
	@Override
	public boolean isSpecular()
	{
		if ( chanceOfFirstMaterial > comparison )
		{
			return one.isSpecular();
		}
		else
		{
			return two.isSpecular();
		}
	}
	
	public Material getOne()
	{
		return one;
	}
	
	public void setOne( Material one )
	{
		this.one = one;
	}
	
	public Material getTwo()
	{
		return two;
	}
	
	public void setTwo( Material two )
	{
		this.two = two;
	}
	
	public double getChanceOfFirstMaterial()
	{
		return chanceOfFirstMaterial;
	}
	
	public void setChanceOfFirstMaterial( double chanceOfFirstMaterial )
	{
		this.chanceOfFirstMaterial = chanceOfFirstMaterial;
	}
	
}
