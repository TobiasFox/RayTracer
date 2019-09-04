
/**
 * 
 */
package raytracer.a02;

import java.io.IOException;

import raytracer.imageCreator.ImageCreator;
import raytracer.imageCreator.enums.ImageQualities;
import raytracer.imageCreator.enums.PixelColorCalculations;
import raytracer.imageCreator.exceptions.FeatureNotUsingException;

/**
 * @author Tobias Fox
 *
 */
public class Main02
{
	private static final String NAME = "doc/a02-4.png";
	private static final int WIDTH = 480;
	private static final int HEIGHT = 270;
	private static final int ABTASTRATE = 5;
	private static final int NUMBEROFCIRCLES = 250;

	/**
	 * @param args
	 */

	private Main02()
	{
		ImageCreator imageCreator = new ImageCreator(WIDTH, HEIGHT, ABTASTRATE, ImageQualities.Bit8,
				PixelColorCalculations.Normal);

		try
		{
			imageCreator.createRandomCircles(NUMBEROFCIRCLES);
			imageCreator.start();
		} catch( FeatureNotUsingException e1 )
		{
			e1.printStackTrace();
		}

		try
		{
			imageCreator.savePicture(NAME);
		} catch( IOException e )
		{
			System.out.println("Something went wrong: " + e);
		}
	}

	public static void main( String[] args )
	{
		new Main02();
	}
}
