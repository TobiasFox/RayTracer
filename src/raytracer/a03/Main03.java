/**
 * 
 */

package raytracer.a03;

import java.io.IOException;

import raytracer.imageCreator.ImageCreator;
import raytracer.imageCreator.enums.ImageQualities;
import raytracer.imageCreator.enums.PixelColorCalculations;
import raytracer.imageCreator.exceptions.FeatureNotUsingException;
import raytracer.imageCreator.shape.withColor.Background;

/**
 * @author Tobias Fox
 *
 */
public class Main03
{
	
	private static final String name = "doc/test/a03-2.png";
	private static final int width = 480;
	private static final int height = 270;
	private static final int abtastRate = 10;
	
	/**
	 * @param args
	 */
	private Main03()
	{
		ImageCreator imageCreator = new ImageCreator( width, height, abtastRate, ImageQualities.Bit8,
				PixelColorCalculations.RayTrace );
		
		try
		{
			imageCreator.setScene( new Background() );
			imageCreator.start();
		} catch( FeatureNotUsingException e1 )
		{
			e1.printStackTrace();
		}
		
		try
		{
			imageCreator.savePicture( name );
		} catch( IOException e )
		{
			System.out.println( "Something went wrong: " + e );
		}
	}
	
	public static void main( String[] args )
	{
		new Main03();
	}
	
}
