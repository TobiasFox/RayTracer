
package raytracer.imageCreator.shape.withMaterial.light;

import raytracer.imageCreator.World;
import raytracer.imageCreator.general.Vec3;

public interface Light
{
	
	LightData sample( World world, Vec3 fromPoint );
	
}
