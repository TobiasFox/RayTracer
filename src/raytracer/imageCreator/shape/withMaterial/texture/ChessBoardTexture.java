
package raytracer.imageCreator.shape.withMaterial.texture;

import raytracer.imageCreator.general.Mat4;
import raytracer.imageCreator.general.Vec3;

public class ChessBoardTexture implements Texture
{
	
	private static final Vec3 ONEVEC = new Vec3( 1, 1, 1 );
	private static final Vec3 ZEROVEC = new Vec3( 0, 0, 0 );
	double n = 2;
	private Mat4 matrix;
	
	public ChessBoardTexture( Mat4 matrix )
	{
		this.matrix = matrix;
	}
	
	public ChessBoardTexture()
	{
		this( Mat4.identity );
	}
	
	@Override
	public Vec3 sample( Vec3 uv )
	{
		// double u = fract( uv.x );
		// double v = fract( uv.y );
		Vec3 transformed = matrix.transformPoint( uv );
		int ui = (int) ( Math.floor( transformed.x * n ) );
		int vi = (int) ( Math.floor( transformed.y * n ) );
		if ( ( ( ui + vi ) % 2 ) == 0 )
		{
			return ONEVEC;
		}
		else
		{
			return ZEROVEC;
		}
	}
	
}
