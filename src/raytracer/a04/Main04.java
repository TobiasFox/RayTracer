/**
 * 
 */

package raytracer.a04;

import java.io.IOException;

import raytracer.imageCreator.ImageCreator;
import raytracer.imageCreator.enums.ImageQualities;
import raytracer.imageCreator.enums.PixelColorCalculations;
import raytracer.imageCreator.exceptions.FeatureNotUsingException;
import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.shape.Shape;
import raytracer.imageCreator.shape.withColor.Background;
import raytracer.imageCreator.shape.withColor.Group;
import raytracer.imageCreator.shape.withColor.Sphere;

/**
 * @author Tobias Fox
 *
 */
public class Main04
{
	
	private static final String name = "doc/a04-21.png";
	private static final int width = 480;
	private static final int height = 270;
	private static final int abtastRate = 2;
	
	private Main04()
	{
		ImageCreator creator = new ImageCreator( width, height, abtastRate, ImageQualities.Bit16,
				PixelColorCalculations.RayTrace );
		
		try
		{
			creator.setScene( createScene() );
			creator.start();
		} catch( FeatureNotUsingException e1 )
		{
			e1.printStackTrace();
		}
		
		try
		{
			creator.savePicture( name );
		} catch( IOException e )
		{
			e.printStackTrace();
		}
	}
	
	private Shape createScene()
	{
		Shape bg = new Background();
		
		Vec3 center = new Vec3( 0, 0, -50 );
		Vec3 color = new Vec3( .3, .63, .153 );
		Shape sphere = new Sphere( center, 15, color );
		
		center = new Vec3( 50, 0, -50 );
		color = new Vec3( .015, .561318664, .5165651 );
		Shape sphere2 = new Sphere( center, 15, color );
		
		center = new Vec3( 0, 50, -50 );
		color = new Vec3( .751235131, .321351, .3216515 );
		Shape sphere3 = new Sphere( center, 15, color );
		
		center = new Vec3( -40, 10, -52 );
		color = new Vec3( 1., .65481, 1. );
		Shape sphere4 = new Sphere( center, 15, color ); // radius auf 30-->
		// überlappung
		center = new Vec3( 2, 2, -10 );
		color = new Vec3( .0, .0, 1. );
		Shape sphere5 = new Sphere( center, 5, color );
		
		center = new Vec3( 10, 15, -8 );
		color = new Vec3( .984651, .333333, .65313135 );
		Shape sphere6 = new Sphere( center, 15, color );
		
		center = new Vec3( 30, -30, -33 );
		color = new Vec3( 1., .0001, 0 );
		Shape sphere7 = new Sphere( center, 15, color );
		
		Shape group = new Group();
		( (Group) group ).addAllShape( bg, sphere, sphere2, sphere3, sphere4, sphere5, sphere6, sphere7 );
		
		return group;
	}
	
	public static void main( String[] a )
	{
		new Main04();
	}
}
