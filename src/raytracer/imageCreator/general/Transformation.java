/**
 * 
 */

package raytracer.imageCreator.general;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.BoundingBox;
import raytracer.imageCreator.shape.withMaterial.Hit;

/**
 * @author Tobias Fox
 *
 */
public class Transformation
{
	
	private Mat4 transform;
	private Mat4 invertTransformation;
	
	public Transformation()
	{
		transform = Mat4.identity;
		invertTransformation = Mat4.identity;
	}
	
	public Transformation( Mat4 transformation )
	{
		set( transformation );
	}
	
	public Mat4 get()
	{
		return transform;
	}
	
	public Mat4 getInvert()
	{
		return invertTransformation;
	}
	
	public void set( Mat4 transformation )
	{
		this.transform = transformation;
		invertTransformation = transformation.invertFull();
	}
	
	public void add( Mat4 transformation )
	{
		this.transform = this.transform.mult( transformation );
		invertTransformation = this.invertTransformation.mult( transformation.invertFull() );
	}
	
	public void reset()
	{
		set( Mat4.identity );
	}
	
	public Ray transformRay( Ray ray )
	{
		return new Ray( transform.transformPoint( ray.origin ), transform.transformDirection( ray.direction ),
				ray.lowerBound, ray.upperBound );
	}
	
	public Ray transformRayWithInvert( Ray ray )
	{
		return new Ray( invertTransformation.transformPoint( ray.origin ),
				invertTransformation.transformDirection( ray.direction ), ray.lowerBound, ray.upperBound );
	}
	
	public Hit transformHit( Hit hit )
	{
		return new Hit( hit.t, transform.transformPoint( hit.position ), transform.transformNormal( hit.surfaceNormal ),
				hit.material, hit.textureCoordinate == null ? null : hit.textureCoordinate );
	}
	
	/**
	 * transforms each point of bounding box and creates a new bounding box, with the transformed min and max
	 * 
	 * @param boundingBox
	 * @return
	 */
	public BoundingBox transformBoundingBox( BoundingBox boundingBox )
	{
		// if ( transform.equals( Mat4.identity ) )
		// {
		// return boundingBox;
		// }
		
		Vec3 oldMin = boundingBox.min;
		Vec3 oldMax = boundingBox.max;
		/*
		 * follows the schema:
		 * lower:
		 * 14
		 * 23
		 * upper
		 * 58
		 * 67
		 * @formatter:off
		 */
		List<Vec3> points = Arrays.asList(
//				lower
				oldMin,
				new Vec3( oldMin.x, oldMin.y, oldMax.z ),
				new Vec3( oldMax.x, oldMin.y, oldMax.z ),
				new Vec3( oldMax.x, oldMin.y, oldMin.z ),
//				upper
				new Vec3( oldMin.x, oldMax.y, oldMin.z ),
				new Vec3( oldMin.x, oldMax.y, oldMax.z ),
				oldMax,
				new Vec3( oldMax.x, oldMax.y, oldMin.z )
		);
//		@formatter:on
		
		IntStream.range( 0, points.size() ).forEach( index ->
		{
			Vec3 vec = transform.transformPoint( points.get( index ) );
			Vec3 oldVec = points.get( index );
			
			// if oldVec contains infinity * 0, the result is NaN
			double d1 = checkIfNAN( oldVec.x, vec.x );
			double d2 = checkIfNAN( oldVec.y, vec.y );
			double d3 = checkIfNAN( oldVec.z, vec.z );
			
			points.set( index, new Vec3( d1, d2, d3 ) );
		} );
		
		double newMinX = points.stream().mapToDouble( vec -> vec.x ).min().getAsDouble();
		double newMinY = points.stream().mapToDouble( vec -> vec.y ).min().getAsDouble();
		double newMinZ = points.stream().mapToDouble( vec -> vec.z ).min().getAsDouble();
		
		double newMaxX = points.stream().mapToDouble( vec -> vec.x ).max().getAsDouble();
		double newMaxY = points.stream().mapToDouble( vec -> vec.y ).max().getAsDouble();
		double newMaxZ = points.stream().mapToDouble( vec -> vec.z ).max().getAsDouble();
		
		return new BoundingBox( new Vec3( newMinX, newMinY, newMinZ ), new Vec3( newMaxX, newMaxY, newMaxZ ) );
	}
	
	private double checkIfNAN( double index, double checkValue )
	{
		if ( Double.isNaN( checkValue ) )
		{
			return index;
		}
		return checkValue;
	}
	
	public Hit transformHitWithInvert( Hit hit )
	{
		return new Hit( hit.t, invertTransformation.transformPoint( hit.position ),
				invertTransformation.transformNormal( hit.surfaceNormal ), hit.material,
				hit.textureCoordinate == null ? null : hit.textureCoordinate );
	}
	
}