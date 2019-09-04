/**
 * 
 */

package raytracer.imageCreator.shape.withMaterial.material;

import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.withMaterial.Hit;

/**
 * @author Tobias Fox
 *
 */
public interface Material
{
	
	public Ray getScatteredRay( Hit hit, Ray ray );
	
	public Vec3 getAlbedo( Hit hit );
	
	public boolean isScattered();
	
	public Vec3 getEmission( Ray ray, Hit hit );
	
	public boolean isSpecular();
	
}
