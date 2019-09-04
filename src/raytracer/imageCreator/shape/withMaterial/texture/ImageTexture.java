
package raytracer.imageCreator.shape.withMaterial.texture;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import raytracer.imageCreator.ImageCreator;
import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.threads.time.TimeFormatter;
import raytracer.imageCreator.threads.time.TimeObject;

public class ImageTexture
{
	
	private BufferedImage image;
	private int width;
	private int height;
	
	public ImageTexture( String filename ) throws IOException
	{
		this( filename, true );
	}
	
	public ImageTexture( String filename, boolean gammaCorrection ) throws IOException
	{
		image = ImageIO.read( new File( filename ) );
		width = image.getWidth();
		height = image.getHeight();
		
		if ( gammaCorrection )
		{
			System.out.printf( "%s Bild " + filename + " wird gammakorregiert.%n",
					TimeFormatter.getActualFormatTime() );
			long currentTime = System.currentTimeMillis();
			image = ImageCreator.gammacorrectBufferedImage( image, 1 / 2.2 );
			
			TimeObject time = new TimeObject( currentTime );
			System.out.printf( "%s Textur wurde erfolgreich gammakorregiert in %s: %.3f%n",
					TimeFormatter.getActualFormatTime(), time.getUnit(), time.getTime() );
		}
	}
	
	public Vec3 samplePoint( double u, double v )
	{
		int x = (int) Math.floor( clamp( u ) * ( width - 1 ) );
		int y = (int) Math.floor( clamp( v ) * ( height - 1 ) );
		
		Color rgb = new Color( image.getRGB( x, y ) );
		return new Vec3( rgb.getRed() / 255.0, rgb.getGreen() / 255.0, rgb.getBlue() / 255.0 );
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	private double clamp( double v )
	{
		return Math.abs( v % 1.0 );
	}
}
