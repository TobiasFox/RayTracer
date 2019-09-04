
package raytracer.imageCreator.shape.withMaterial.light;

import raytracer.imageCreator.World;
import raytracer.imageCreator.general.Vec3;

public class PointLight implements Light
{
	
	private final Vec3 position;
	private final Vec3 intensity;
	
	public PointLight( Vec3 position, Vec3 intensity )
	{
		this.position = position;
		this.intensity = intensity;
	}
	@Override
	public LightData sample( World world, Vec3 fromPoint )
	{
		LightData data = null;
		
		if ( world.isVisible( fromPoint, position ) )
		{
			Vec3 direction = position.sub( fromPoint );
			
			double squaredLength = direction.length * direction.length;
			Vec3 radiance = intensity.scale( 1 / squaredLength );
			data = new LightData( radiance, direction.normalize() );
		}
		return data;
	}
	
}
