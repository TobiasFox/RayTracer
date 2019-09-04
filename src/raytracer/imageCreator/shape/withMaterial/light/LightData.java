
package raytracer.imageCreator.shape.withMaterial.light;

import raytracer.imageCreator.general.Vec3;

public class LightData
{
	
	public final Vec3 radiance;
	public final Vec3 intensity;
	
	public LightData( Vec3 radiance, Vec3 intensity )
	{
		this.radiance = radiance;
		this.intensity = intensity;
	}
	
}
