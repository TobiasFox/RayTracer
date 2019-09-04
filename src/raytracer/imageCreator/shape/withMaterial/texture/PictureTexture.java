
package raytracer.imageCreator.shape.withMaterial.texture;

import java.io.IOException;
import raytracer.imageCreator.general.Vec3;

public class PictureTexture implements Texture
{
	
	private String fileName;
	private ImageTexture imgTexture;
	
	public PictureTexture( String fileName ) throws IOException
	{
		this( fileName, true );
	}
	
	public PictureTexture( String fileName, boolean gammaCorrection ) throws IOException
	{
		changeFile( fileName, gammaCorrection );
	}
	
	@Override
	public Vec3 sample( Vec3 uv )
	{
		return imgTexture.samplePoint( uv.x, uv.y );
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public void changeFile( String fileName, boolean gammaCorrection ) throws IOException
	{
		this.fileName = fileName;
		initPicture( gammaCorrection );
	}
	
	private void initPicture( boolean gammaCorrection ) throws IOException
	{
		imgTexture = new ImageTexture( fileName, gammaCorrection );
	}
	
}
