
package raytracer.imageCreator.shape.withMaterial;

import java.util.Arrays;
import raytracer.imageCreator.general.Mat4;
import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.shape.BoundingBox;
import raytracer.imageCreator.shape.Shape;
import raytracer.imageCreator.shape.withMaterial.group.AbstractGroup;
import raytracer.imageCreator.shape.withMaterial.group.Group;
import raytracer.imageCreator.shape.withMaterial.interfaces.IShapeMaterialMulti;
import raytracer.imageCreator.shape.withMaterial.material.Material;
import raytracer.imageCreator.shape.withMaterial.primitives.Rectangle;

public class Quader extends AbstractGroup implements IShapeMaterialMulti
{
	
	private final Vec3 dimension;
	private Rectangle[] surfaces = new Rectangle[6];
	private Group[] groups = new Group[6];
	private BoundingBox boundingBox = new BoundingBox( new Vec3( 0, 0, 0 ), new Vec3( 0, 0, 0 ) );
	
	public Quader( Vec3 dimension, Material material )
	{
		this.dimension = dimension;
		for ( int i = 0; i < surfaces.length; i++ )
		{
			if ( i < 2 )
			{
				surfaces[i] = new Rectangle( dimension.x, dimension.z, material );
				groups[i] = new Group( Mat4.rotateX( -90 ).mult( Mat4.translate( 0, 0, ( i * dimension.y ) ) ),
						surfaces[i] );
			}
			else
			{
				// vorne
				Mat4 m = Mat4.identity;
				double width = dimension.x;
				double height = dimension.y;
				switch ( i )
				{
					case 3:
						// rechts
						width = dimension.z;
						m = Mat4.translate( dimension.x, 0, 0 ).mult( Mat4.rotateY( 90 ) );
						break;
					case 4:
						// hinten
						m = Mat4.translate( 0, 0, -dimension.z );
						break;
					case 5:
						// links
						width = dimension.z;
						m = Mat4.translate( 0, 0, -dimension.z ).mult( Mat4.rotateY( -90 ) );
						break;
					default:
						break;
				}
				
				surfaces[i] = new Rectangle( width, height, material );
				groups[i] = new Group( m, surfaces[i] );
			}
		}
		Arrays.stream( groups ).forEach( shapes::add );
		
		for ( Shape shape : shapes )
		{
			boundingBox = boundingBox.addBoundingBox( shape.getBoundingBox() );
		}
	}
	
	public Quader( Vec3 dimension, Material[] materials )
	{
		this( dimension, materials[0] );
		setAllMaterials( materials );
	}
	
	@Override
	public Material[] getMaterials()
	{
		Material[] materials = new Material[6];
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
		if ( material.length != surfaces.length )
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
		return boundingBox;
	}
	
}
