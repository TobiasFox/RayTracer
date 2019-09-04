
package raytracer.imageCreator.shape.withMaterial.texture;

import raytracer.imageCreator.general.Vec3;

public class UVHelper
{
	
	public static Vec3 calcUVSphereTexture( Vec3 vec )
	{
		double inclination = Math.acos( vec.y );
		double azimuth = Math.atan2( vec.x, vec.z );
		double u = ( azimuth + Math.PI ) / ( 2 * Math.PI );
		double v = inclination / Math.PI;
		
		return new Vec3( u, v, 0 );
	}
	public static Vec3 calcUVBGTexture( Vec3 vec )
	{
		double inclination = Math.acos( vec.y );
		double azimuth = Math.atan2( vec.x, vec.z );
		double u = ( azimuth - ( 2 * Math.PI ) ) / ( 2 * Math.PI ); // ( azimuth + Math.PI ) / ( 2 * Math.PI );
		double v = inclination / Math.PI;
		
		return new Vec3( u, v, 0 );
	}
}