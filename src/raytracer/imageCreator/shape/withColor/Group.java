/**
 * 
 */
package raytracer.imageCreator.shape.withColor;

import java.util.ArrayList;
import java.util.List;

import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.BoundingBox;
import raytracer.imageCreator.shape.Shape;

/**
 * @author Tobias Fox
 *
 */
public class Group implements Shape
{
	private List<Shape> shapes = new ArrayList<>();

	@Override
	public Hit calculateHit( Ray ray )
	{
		List<Hit> list = new ArrayList<>();
		for ( Shape shape : shapes )
		{
			Hit h = (Hit) shape.calculateHit(ray);
			if ( h != null )
				list.add(h);
		}
		list.sort(( hit1, hit2 ) ->
		{
			return Double.compare(hit1.t, hit2.t);
		});

		if ( list.isEmpty() )
			return null;
		return list.get(0);

	}

	public boolean addShape( Shape shape )
	{
		return shapes.add(shape);
	}

	public boolean addAllShape( Shape... shape )
	{
		for ( Shape sh : shape )
			if ( !addShape(sh) )
				return false;
		return true;
	}

	public boolean removeShape( Shape shape )
	{
		return shapes.remove(shape);
	}

	@Override
	public BoundingBox getBoundingBox()
	{
		return null;
	}
}
