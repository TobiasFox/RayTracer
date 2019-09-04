/**
 * 
 */

package raytracer.imageCreator.shape.withMaterial.primitives;

import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.BoundingBox;
import raytracer.imageCreator.shape.Shape;
import raytracer.imageCreator.shape.withMaterial.Hit;
import raytracer.imageCreator.shape.withMaterial.interfaces.IShapeMaterialMulti;
import raytracer.imageCreator.shape.withMaterial.material.Material;

/**
 * @author Tobias Fox
 *
 */
public class Rectangle implements Shape, IShapeMaterialMulti
{
	
	private static final Vec3 NORMAL = new Vec3( 0, 0, 1 );
	private double width;
	private double height;
	private Material[] materials = new Material[2];
	private BoundingBox boundingBox;
	
	public Rectangle( double width, double height, Material material )
	{
		this( width, height, material, material );
	}
	
	public Rectangle( double width, double height, Material materialFront, Material materialBack )
	{
		this.width = width;
		this.height = height;
		materials[0] = materialFront;
		materials[1] = materialBack;
		boundingBox = new BoundingBox( new Vec3( 0, 0, 0 ), new Vec3( width, height, 0 ) );
	}
	
	@Override
	public Hit calculateHit( Ray ray )
	{
		
		if ( !boundingBox.intersect( ray ) )
		{
			return null;
		}
		
		// calculation in xy-Ebene, z=0
		double a = ray.origin.scale( -1 ).scalarProduct( NORMAL );
		double b = ray.direction.scalarProduct( NORMAL );
		if ( b == 0. )
		{
			return null;
		}
		
		double t = a / b;
		if ( ( t <= 0 ) || !ray.isInsideOfBounds( t ) )
		{
			return null;
		}
		
		Vec3 position = ray.pointAt( t );
		
		if ( outsideOfDimensionsLeftLowerEdge( position ) )
		{
			return null;
		}
		
		boolean fromBack;
		if ( ray.origin.z < 0 )
		{
			fromBack = ray.origin.z > position.z ? true : false;
		}
		else
		{
			fromBack = ray.origin.z < position.z ? true : false;
		}
		// calculate uv from position for texture
		
		// position = new Vec3( position.x, -position.y, position.z );
		Vec3 textureCoords;
		textureCoords = new Vec3( ( position.x / width ), ( position.y / height ) - 1, 0 );
		
		return new Hit( t, position, fromBack ? NORMAL.scale( -1 ) : NORMAL, fromBack ? materials[1] : materials[0],
				textureCoords );
	}
	
	private boolean outsideOfDimensionsLeftUpperEdge( Vec3 position )
	{
		return ( position.x > width ) || ( position.x < 0 ) || ( position.y > 0 ) || ( position.y < -height );
	}
	
	private boolean outsideOfDimensionsLeftLowerEdge( Vec3 position )
	{
		return ( position.x > width ) || ( position.x < 0 ) || ( position.y > height ) || ( position.y < 0 );
	}
	
	private boolean outsideOfDimensionsCenter( Vec3 position )
	{
		return ( position.x > ( width / 2.0 ) ) || ( position.x < -( width / 2.0 ) )
				|| ( position.y > ( height / 2.0 ) ) || ( position.y < -( height / 2.0 ) );
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
		
		for ( int i = 0; i < materials.length; i++ )
		{
			materials[i] = material[i];
		}
	}
	
	@Override
	public void setMaterial( int index, Material material )
	{
		if ( ( index >= materials.length ) || ( index < 0 ) )
		{
			throw new IllegalArgumentException();
		}
		materials[index] = material;
	}
	
	private void testRange( Vec3 textureCoords )
	{
		if ( ( textureCoords.y > 1 ) || ( textureCoords.y < 0 ) || ( textureCoords.x > 1 ) || ( textureCoords.x < 0 ) )
		{
			toString();
		}
	}
	
	@Override
	public BoundingBox getBoundingBox()
	{
		return boundingBox;
	}
	
}