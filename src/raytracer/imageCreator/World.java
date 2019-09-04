
package raytracer.imageCreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.Hit;
import raytracer.imageCreator.shape.Shape;
import raytracer.imageCreator.shape.withMaterial.Background;
import raytracer.imageCreator.shape.withMaterial.group.Group;
import raytracer.imageCreator.shape.withMaterial.light.Light;

public class World
{
	
	private Background background;
	
	private Shape world;
	private List<Light> lightSources;
	
	public World( Group world, Background background, List<Light> lightSources )
	{
		this.world = world;
		this.background = background;
		this.lightSources = lightSources;
	}
	
	public World( Shape world, Background background, Light... lightSources )
	{
		this.world = world;
		this.background = background;
		this.lightSources = new ArrayList<>();
		Arrays.asList( lightSources ).forEach( this.lightSources::add );
	}
	
	public Shape getWorld()
	{
		return world;
	}
	
	public void setWorld( Shape world )
	{
		this.world = world;
	}
	
	public List<Light> getLightSources()
	{
		return lightSources;
	}
	
	public void setLightSources( List<Light> lightSources )
	{
		this.lightSources = lightSources;
	}
	
	public void addLightSources( Light lightSource )
	{
		lightSources.add( lightSource );
	}
	
	public void remove( Light lightSource )
	{
		lightSources.remove( lightSource );
	}
	
	public boolean isVisible( Vec3 from, Vec3 position )
	{
		Vec3 direction = position.sub( from );
		Ray ray = new Ray( from, direction, 0.001, direction.length );
		raytracer.imageCreator.shape.withMaterial.Hit hit = (raytracer.imageCreator.shape.withMaterial.Hit) world
				.calculateHit( ray );
		if ( hit == null )
		{
			return true;
		}
		return direction.length < hit.t;
	}
	
	public Hit calculateHit( Ray ray )
	{
		return world.calculateHit( ray );
	}
	
	public Background getBackground()
	{
		return background;
	}
	
	public void setBackground( Background background )
	{
		this.background = background;
	}
	
}
