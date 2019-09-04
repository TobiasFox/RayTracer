/**
 * 
 */

package raytracer.imageCreator.shape;

import raytracer.imageCreator.sampling.pinholeCamera.Ray;

/**
 * @author Tobias Fox
 *
 */
public interface Shape
{
	
	public Hit calculateHit( Ray ray );
	
	public BoundingBox getBoundingBox();
	
}
