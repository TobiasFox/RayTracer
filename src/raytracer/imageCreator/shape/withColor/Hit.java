/**
 * 
 */
package raytracer.imageCreator.shape.withColor;

import raytracer.imageCreator.general.Vec3;

/**
 * @author Tobias Fox
 *
 */
public class Hit implements raytracer.imageCreator.shape.Hit
{
	public final double t;
	public final Vec3 position;
	public final Vec3 color;

	public Hit( double t, Vec3 position, Vec3 color )
	{
		this.t = t;
		this.position = position;
		this.color = color;
	}
}
