/**
 * 
 */

package raytracer.imageCreator.shape.withMaterial;

import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.shape.withMaterial.material.Material;

/**
 * @author Tobias Fox
 *
 */
public class Hit implements raytracer.imageCreator.shape.Hit
{
	
	public final double t;
	public final Vec3 position;
	public final Vec3 surfaceNormal;
	public final Material material;
	public final Vec3 textureCoordinate;
	
	public Hit( double t, Vec3 position, Vec3 surfaceNormal, Material material, Vec3 textureCoordinate )
	{
		this.t = t;
		this.position = position;
		this.material = material;
		this.surfaceNormal = surfaceNormal;
		this.textureCoordinate = textureCoordinate;
	}
	
}
