
package raytracer.imageCreator.shape.withMaterial;

import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.shape.BoundingBox;
import raytracer.imageCreator.shape.withMaterial.group.AbstractGroup;
import raytracer.imageCreator.shape.withMaterial.interfaces.IShapeMaterialMulti;
import raytracer.imageCreator.shape.withMaterial.material.Material;
import raytracer.imageCreator.shape.withMaterial.primitives.Triangle;

public class ConvexPolygon extends AbstractGroup implements IShapeMaterialMulti
{
	
	private Triangle[] surfaces;
	
	public ConvexPolygon( Material material, Vec3... points )
	{
		if ( points.length > 2 )
		{
			surfaces = new Triangle[points.length - 2];
			for ( int i = 0; i < surfaces.length; i++ )
			{
				surfaces[i] = new Triangle( points[0], points[i + 1], points[i + 2], material );
			}
			shapes.stream().forEach( shapes::add );
			
			// for ( Triangle triangle : surfaces )
			// {
			// shapes.add(triangle);
			// }
		}
	}
	
	public ConvexPolygon( Material[] materials, Vec3... points )
	{
		this( materials[0], points );
		setAllMaterials( materials );
	}
	
	@Override
	public Material[] getMaterials()
	{
		Material[] materials = new Material[surfaces.length];
		for ( int i = 0; i < surfaces.length; i++ )
		{
			materials[i] = surfaces[i].getMaterial( 0 );
		}
		return materials;
	}
	
	@Override
	public Material getMaterial( int index )
	{
		if ( ( index >= surfaces.length ) || ( index < 0 ) )
		{
			throw new IllegalArgumentException();
		}
		
		return surfaces[index].getMaterial( 0 );
	}
	
	@Override
	public void setAllMaterials( Material[] material )
	{
		if ( ( material.length != surfaces.length ) || ( material.length < 0 ) )
		{
			throw new IllegalArgumentException();
		}
		
		for ( int i = 0; i < material.length; i++ )
		{
			surfaces[i].setAllMaterials( new Material[] {
					material[i], material[i]
			} );
		}
	}
	
	@Override
	public void setMaterial( int index, Material material )
	{
		if ( ( index >= surfaces.length ) || ( index < 0 ) )
		{
			throw new IllegalArgumentException();
		}
		surfaces[index].setAllMaterials( new Material[] {
				material, material
		} );
	}

	@Override
	public BoundingBox getBoundingBox()
	{
		return null;
	}
	
}
