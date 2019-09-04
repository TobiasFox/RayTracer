package raytracer.imageCreator.shape.withMaterial.group;

import java.util.ArrayList;

import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.Shape;
import raytracer.imageCreator.shape.withMaterial.Hit;

public abstract class AbstractGroup implements Shape
{
	protected ArrayList<Shape> shapes = new ArrayList<>();

	@Override
	public Hit calculateHit( Ray ray )
	{
		Hit hit = null;
		for ( Shape shape : shapes )
		{
			Hit hit2 = (Hit) shape.calculateHit(ray);
			if ( hit2 != null )
			{
				if ( hit != null )
				{
					if ( hit2.t < hit.t )
						hit = hit2;
				}
				else
					hit = hit2;
			}
		}
		return hit;
	}

}
