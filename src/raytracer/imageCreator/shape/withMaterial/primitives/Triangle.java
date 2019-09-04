
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
public class Triangle implements Shape, IShapeMaterialMulti
{
	
	private final Vec3 point1;
	private final Vec3 point2;
	private final Vec3 point3;
	private final Vec3 edge1;
	private final Vec3 edge2;
	private Vec3 normal;
	private static final double EPSILON = 0.000001;
	private BoundingBox boundingBox;
	private Material[] materials = new Material[2];
	
	private static enum minMax
	{
		min, max
	};
	
	public Triangle( Vec3 point1, Vec3 point2, Vec3 point3, Material materialFront, Material materialBack )
	{
		this.point1 = point1;
		this.point2 = point2;
		this.point3 = point3;
		materials[0] = materialFront;
		materials[1] = materialBack;
		edge1 = point2.sub( point1 );
		edge2 = point3.sub( point1 );
		normal = edge1.crossProduct( edge2 ).normalize();
		
		double minX = calcCoord( minMax.min, point1.x, point2.x, point3.x );
		double minY = calcCoord( minMax.min, point1.y, point2.y, point3.y );
		double minZ = calcCoord( minMax.min, point1.z, point2.z, point3.z );
		double maxX = calcCoord( minMax.max, point1.x, point2.x, point3.x );
		double maxY = calcCoord( minMax.max, point1.y, point2.y, point3.y );
		double maxZ = calcCoord( minMax.max, point1.z, point2.z, point3.z );
		boundingBox = new BoundingBox( new Vec3( minX, minY, minZ ), new Vec3( maxX, maxY, maxZ ) );
	}
	
	public Triangle( Vec3 point1, Vec3 point2, Vec3 point3, Material material )
	{
		this( point1, point2, point3, material, material );
	}
	
	@Override
	public Hit calculateHit( Ray ray )
	{
		Vec3 p;
		Vec3 q;
		Vec3 distance;
		double determinant;
		double inverseDeterminant;
		double u;
		double v;
		double t;
		
		// Begin calculating determinant - also used to calculate u parameter
		p = ray.direction.crossProduct( edge2 );
		// if determinant is near zero, ray lies in plane of triangle or ray is
		// parallel to plane of triangle
		determinant = edge1.scalarProduct( p );
		// NOT CULLING
		if ( ( determinant > -EPSILON ) && ( determinant < EPSILON ) )
		{
			return null;
		}
		inverseDeterminant = 1. / determinant;
		
		// calculate distance from V1 to ray origin
		distance = ray.origin.sub( point1 );
		
		// Calculate u parameter and test bound
		u = distance.scalarProduct( p ) * inverseDeterminant;
		// The intersection lies outside of the triangle
		if ( ( u < 0. ) || ( u > 1. ) )
		{
			return null;
		}
		
		// Prepare to test v parameter
		q = distance.crossProduct( edge1 );
		
		// Calculate V parameter and test bound
		v = ray.direction.scalarProduct( q ) * inverseDeterminant;
		
		// The intersection lies outside of the triangle
		if ( ( v < 0. ) || ( ( u + v ) > 1. ) )
		{
			return null;
		}
		
		t = edge2.scalarProduct( q ) * inverseDeterminant;
		
		if ( t > EPSILON )
		{ // ray intersection
			if ( !ray.isInsideOfBounds( t ) )
			{
				return null;
			}
			Vec3 position = ray.pointAt( t );
			boolean fromBack;
			if ( ray.origin.z < 0 )
			{
				fromBack = ray.origin.z > position.z ? true : false;
			}
			else
			{
				fromBack = ray.origin.z < position.z ? true : false;
			}
			return new Hit( t, position, fromBack ? normal.scale( -1 ) : normal, fromBack ? materials[1] : materials[0],
					null );
		}
		
		// No hit, no win
		return null;
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
	
	@Override
	public BoundingBox getBoundingBox()
	{
		return boundingBox;
	}
	
	private double calcCoord( minMax min, double coord1, double coord2, double coord3 )
	{
		switch ( min )
		{
			case min:
				return Math.min( coord1, Math.min( coord2, coord3 ) );
			
			case max:
				return Math.max( coord1, Math.max( coord2, coord3 ) );
			
			default:
				return 0;
		}
	}
	
}
