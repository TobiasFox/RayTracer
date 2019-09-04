
package raytracer.imageCreator.shape.withMaterial.texture;

import raytracer.imageCreator.general.Vec3;

/**
 * @author Tobias Fox
 *
 *         Interface for a texture of a shape
 */

public interface Texture
{
	
	Vec3 sample( Vec3 uv );
	
}
