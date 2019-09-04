
package raytracer.imageCreator.shape.withMaterial.platon;

import java.util.Arrays;
import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.shape.BoundingBox;
import raytracer.imageCreator.shape.withMaterial.group.AbstractGroup;
import raytracer.imageCreator.shape.withMaterial.interfaces.IShapeMaterialMulti;
import raytracer.imageCreator.shape.withMaterial.material.Material;
import raytracer.imageCreator.shape.withMaterial.primitives.Triangle;

/**
 * @author Tobias Fox
 *
 */
public class Oktaeder extends AbstractGroup implements IShapeMaterialMulti
{
	
	private double sideLength;
	private Triangle[] surfaces = new Triangle[8];
	private Material[] materials = new Material[8];
	
	public Oktaeder( double sidelength, Material material )
	{
		this( sidelength, new Material[] {
				material, material, material, material, material, material, material, material
		} );
	}
	
	public Oktaeder( double sideLength, Material[] material )
	{
		if ( material.length != materials.length )
		{
			throw new IllegalArgumentException();
		}
		materials = material;
		
		this.sideLength = sideLength;
		double hoehe = ( 1. / 2 ) * Math.sqrt( 3 ) * this.sideLength;
		double diagonale = Math.sqrt( 2 ) * sideLength;
		
		Vec3[] points = new Vec3[4];
		points[0] = new Vec3( 0, 0, 0 );
		points[1] = points[0].add( sideLength, 0, 0 );
		points[2] = points[0].add( sideLength, 0, -diagonale );
		points[3] = points[0].add( 0, 0, -sideLength );
		Vec3 center = points[0].add( diagonale / 2, hoehe, -diagonale / 2 );
		
		// zuerst unten
		for ( int i = 0; i < 4; i++ )
		{
			surfaces[i] = new Triangle( center, points[( i + 1 ) % 4], points[i], materials[i] );
		}
		
		center = center.add( 0, -2 * hoehe, 0 );
		for ( int i = 0; i < 4; i++ )
		{
			surfaces[i + 4] = new Triangle( center, points[i], points[( i + 1 ) % 4], materials[i + 4] );
		}
		
		// for ( Triangle triangle : surfaces )
		// {
		// shapes.add( triangle );
		// }
		Arrays.stream( surfaces ).forEach( shapes::add );
		
	}
	
	@Override
	public Material[] getMaterials()
	{
		return materials;
	}
	
	@Override
	public Material getMaterial( int index )
	{
		if ( ( index >= materials.length ) || ( index < 0 ) )
		{
			throw new IllegalArgumentException();
		}
		
		return materials[index];
	}
	
	@Override
	public void setAllMaterials( Material[] material )
	{
		if ( ( material.length != materials.length ) || ( material.length < 0 ) )
		{
			throw new IllegalArgumentException();
		}
		
		for ( int i = 0; i < material.length; i++ )
		{
			surfaces[i].setAllMaterials( new Material[] {
					material[i], material[i]
			} );
			materials[i] = material[i];
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
		materials[index] = material;
	}

	@Override
	public BoundingBox getBoundingBox()
	{
		return null;
	}
	
}
