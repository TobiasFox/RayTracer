
package raytracer.imageCreator.shape.withMaterial.texture;

import raytracer.imageCreator.general.Mat4;
import raytracer.imageCreator.general.Vec3;

public class TransformationTexture implements Texture
{
	
	private Texture texture;
	private Mat4 matrix;
	
	public TransformationTexture( Texture texture, Mat4 matrix )
	{
		this.texture = texture;
		this.matrix = matrix;
	}
	
	@Override
	public Vec3 sample( Vec3 uv )
	{
		Vec3 transformed = matrix.transformPoint( uv );
		double c = repeated( transformed.x );
		double d = repeated( transformed.y );
		
		if ( texture instanceof PictureTexture )
		{
			transformed = new Vec3( c, 1 - d, 0 );
		}
		else
		{
			transformed = new Vec3( c, d, 0 );
		}
		return texture.sample( transformed );
	}
	
	private double repeated( double x )
	{
		return Math.abs( x % 1.0 );
	}
	
}
