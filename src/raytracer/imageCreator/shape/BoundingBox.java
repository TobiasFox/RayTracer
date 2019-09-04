
package raytracer.imageCreator.shape;

import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;

public class BoundingBox
{
	
	public final Vec3 min;
	public final Vec3 max;
	
	public BoundingBox( Vec3 min, Vec3 max )
	{
		this.min = min;
		this.max = max;
	}
	
	public BoundingBox addBoundingBox( BoundingBox other )
	{
		Vec3 newMin = new Vec3( Math.min( min.x, other.min.x ), Math.min( min.y, other.min.y ),
				Math.min( min.z, other.min.z ) );
		Vec3 newMax = new Vec3( Math.max( max.x, other.max.x ), Math.max( max.y, other.max.y ),
				Math.max( max.z, other.max.z ) );
		return new BoundingBox( newMin, newMax );
	}
	
	// public boolean intersect( Ray ray )
	// {
	// double tmin = -INFINITY, tmax = INFINITY;
	//
	// if ( ray.n.x != 0.0 )
	// {
	// double tx1 = ( min.x -ray.direction.x ) / r.n.x;
	// double tx2 = ( max.x -ray.direction.x ) / r.n.x;
	//
	// tmin = Math.max( tmin, Math.min( tx1, tx2 ) );
	// tmax = Math.min( tmax, Math.max( tx1, tx2 ) );
	// }
	//
	// if ( ray.n.y != 0.0 )
	// {
	// double ty1 = ( min.y - r.x0.y ) / r.n.y;
	// double ty2 = ( max.y - r.x0.y ) / r.n.y;
	//
	// tmin = Math.max( tmin, Math.min( ty1, ty2 ) );
	// tmax = Math.min( tmax, Math.max( ty1, ty2 ) );
	// }
	//
	// return tmax >= tmin;
	// }
	
	// public boolean intersection( Ray ray )
	// {
	// double tx1 = ( min.x - ray.origin.x ) * r.n_inv.x;
	// double tx2 = ( max.x - ray.origin.x ) * r.n_inv.x;
	//
	// double tmin = Math.min( tx1, tx2 );
	// double tmax = Math.max( tx1, tx2 );
	//
	// double ty1 = ( min.y - ray.origin.y ) * r.n_inv.y;
	// double ty2 = ( max.y - ray.origin.y ) * r.n_inv.y;
	//
	// tmin = Math.max( tmin, Math.min( ty1, ty2 ) );
	// tmax = Math.min( tmax, Math.max( ty1, ty2 ) );
	//
	// return tmax >= tmin;
	// }
	
	public boolean intersect( final Ray ray )
	{
		double tx1 = ( min.x - ray.origin.x ) / ray.direction.x;
		double tx2 = ( max.x - ray.origin.x ) / ray.direction.x;
		
		double tmin = Math.min( tx1, tx2 );
		double tmax = Math.max( tx1, tx2 );
		
		double ty1 = ( min.y - ray.origin.y ) / ray.direction.y;
		double ty2 = ( max.y - ray.origin.y ) / ray.direction.y;
		
		tmin = Math.max( tmin, Math.min( ty1, ty2 ) );
		tmax = Math.min( tmax, Math.max( ty1, ty2 ) );
		
		double tz1 = ( min.z - ray.origin.z ) / ray.direction.z;
		double tz2 = ( max.z - ray.origin.z ) / ray.direction.z;
		
		tmin = Math.max( tmin, Math.min( tz1, tz2 ) );
		tmax = Math.min( tmax, Math.max( tz1, tz2 ) );
		
		if ( tmin >= 0 )
		{
			getClass();
		}
		
		// return ( tmax >= Math.max( 0.0, tmin ) ) && ray.contains( tmin );
		return tmax >= Math.max( tmin, 0.0 );
	}
}
