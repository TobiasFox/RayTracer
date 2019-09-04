/**
 * 
 */

package raytracer.imageCreator.sampling;

import java.awt.Color;
import java.awt.image.BufferedImage;
import raytracer.imageCreator.general.Spectrum;
import raytracer.imageCreator.general.Vec3;

/**
 * @author Tobias Fox
 *
 */
class GammaCorrection
{
	
	private double gamma;
	
	/**
	 * default constructor with gamma: 2.2
	 */
	public GammaCorrection()
	{
		this( 2.2 );
	}
	
	public GammaCorrection( double gamma )
	{
		this.gamma = gamma;
	}
	
	public int correctWithIntensity( double intensity )
	{
		return (int) Math.floor( Math.pow( intensity, 1 / gamma ) * Spectrum.DEFAULT );
	}
	
	public double correctWithIntensityPercentage( double intensity )
	{
		return Math.pow( intensity, 1 / gamma );
	}
	
	public int correct( double colorValue )
	{
		return (int) Math.floor( Math.pow( colorValue / Spectrum.DEFAULT, 1 / gamma ) * Spectrum.DEFAULT );
	}
	
	public Color correct( Color color )
	{
		int red = correct( color.getRed() );
		int green = correct( color.getGreen() );
		int blue = correct( color.getBlue() );
		return new Color( red, green, blue );
	}
	
	public Vec3 correct( Vec3 color )
	{
		double red = correctWithIntensityPercentage( color.x );
		double green = correctWithIntensityPercentage( color.y );
		double blue = correctWithIntensityPercentage( color.z );
		return new Vec3( red, green, blue );
	}
	
	public double calcIntensity( double actual )
	{
		return actual / 255;
	}
	
	public double calcIntensity( double actual, Spectrum spectrum )
	{
		return actual / spectrum.value();
	}
	
	public double getGamma()
	{
		return gamma;
	}
	
	public void setGamma( double gamma )
	{
		this.gamma = gamma;
	}
	
	public static BufferedImage gammaCorrection( BufferedImage original, double gamma )
	{
		
		int alpha, red, green, blue;
		int newPixel;
		double spectrum = 255.0;
		
		double gamma_new = 1 / gamma;
		
		BufferedImage gamma_cor = new BufferedImage( original.getWidth(), original.getHeight(), original.getType() );
		
		for ( int i = 0; i < original.getWidth(); i++ )
		{
			for ( int j = 0; j < original.getHeight(); j++ )
			{
				
				// Get pixels by R, G, B
				Color color = new Color( original.getRGB( i, j ) );
				alpha = color.getAlpha();
				red = color.getRed();
				green = color.getGreen();
				blue = color.getBlue();
				
				
				red = (int) Math.floor( spectrum * ( Math.pow( red / spectrum, gamma_new ) ) );
				green = (int) Math.floor( spectrum * ( Math.pow( green / spectrum, gamma_new ) ) );
				blue = (int) Math.floor( spectrum * ( Math.pow( blue / spectrum, gamma_new ) ) );
				
				// Return back to original format
				newPixel = colorToRGB( alpha, red, green, blue );
				
				// Write pixels into image
				gamma_cor.setRGB( i, j, newPixel );
				
			}
			
		}
		
		return gamma_cor;
	}
	
	// Convert R, G, B, Alpha to standard 8 bit
	private static int colorToRGB( int alpha, int red, int green, int blue )
	{
		
		int newPixel = 0;
		newPixel += alpha;
		newPixel = newPixel << 8;
		newPixel += red;
		newPixel = newPixel << 8;
		newPixel += green;
		newPixel = newPixel << 8;
		newPixel += blue;
		
		return newPixel;
	}
}