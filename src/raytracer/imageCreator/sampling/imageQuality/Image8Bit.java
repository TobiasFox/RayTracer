/**
 * 
 */

package raytracer.imageCreator.sampling.imageQuality;

import java.awt.Color;
import java.awt.image.BufferedImage;
import raytracer.imageCreator.enums.ImageQualities;
import raytracer.imageCreator.general.Spectrum;
import raytracer.imageCreator.general.Vec3;

/**
 * @author Tobias Fox
 *
 */
class Image8Bit extends ImageQuality
{
	
	public Image8Bit( int width, int height )
	{
		Spectrum.DEFAULT = Spectrum.Bit8.value();
		bufImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
	}
	
	@Override
	public void setPixel( int x, int y, Color color )
	{
		bufImage.setRGB( x, y, color.getRGB() );
	}
	
	@Override
	public void setPixel( int x, int y, double... c )
	{
		bufImage.setRGB( x, y, new Color( (int) c[0], (int) c[1], (int) c[2] ).getRGB() );
	}
	
	@Override
	public void setPixel( int x, int y, Vec3 color )
	{
		// Vec3 col = color.scale(Spectrum.Bit8.value());
		Color c = new Color( (int) ( color.x * Spectrum.DEFAULT ), (int) ( color.y * Spectrum.DEFAULT ),
				(int) ( color.z * Spectrum.DEFAULT ) );
		setPixel( x, y, c );
	}
	
	@Override
	public ImageQualities getQuality()
	{
		return ImageQualities.Bit8;
	}
	
}
