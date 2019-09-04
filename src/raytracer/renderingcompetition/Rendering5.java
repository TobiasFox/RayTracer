
package raytracer.renderingcompetition;

import java.io.IOException;

import raytracer.imageCreator.ImageCreator;
import raytracer.imageCreator.World;
import raytracer.imageCreator.enums.ImageQualities;
import raytracer.imageCreator.enums.PixelColorCalculations;
import raytracer.imageCreator.exceptions.FeatureNotUsingException;
import raytracer.imageCreator.general.Mat4;
import raytracer.imageCreator.shape.withMaterial.Background;
import raytracer.imageCreator.shape.withMaterial.HouseOfCircles;
import raytracer.imageCreator.shape.withMaterial.group.Group;
import raytracer.imageCreator.shape.withMaterial.material.InterpolateMaterial;
import raytracer.imageCreator.threads.time.TimeFormatter;
import raytracer.imageCreator.threads.time.TimeObject;

public class Rendering5
{
	
	private static final String NAME = "doc/test/testbox1/c0";
	private static final int WIDTH = 480;
	private static final int HEIGHT = 270;
	private static final int ABTASTRATE = 1;
	private static final int DEPTH = 2;
	
	private Background bg;
	
	// parameter for animation of houseGroup
	private HouseOfCircles houseGroup;
	private double houseTurn = 0;
	private double houseDegree = -4;
	
	// parameter for animation of specialSpheres
	private int specialSpheresTurn = 0;
	private Group[] specialSpheres;
	private double specialSphereDegree = 6;
	
	// parameter for camera moving
	private boolean movingForward = true;
	private double cameraX = 0;
	private double movingSpeed = 3;
	private boolean justOneTime = true;
	
	public Rendering5() throws IOException
	{
		RenderingSceneCreation render = new RenderingSceneCreation();
		World world = render.createWorld();
		houseGroup = render.getHouseGroup();
		bg = world.getBackground();
		specialSpheres = render.getSpecialSpheres();
		start( world );
	}
	
	private void start( World world ) throws IOException
	{
		ImageCreator creator = new ImageCreator( WIDTH, HEIGHT, ABTASTRATE, ImageQualities.Bit16,
				PixelColorCalculations.MonteCarloRaytrace );
		
		// try
		// {
		// System.setOut( new PrintStream( "result.txt" ) );
		// } catch( FileNotFoundException exc )
		// {
		// exc.printStackTrace();
		// }
		try
		{
			creator.setRecursiveDepth( DEPTH );
			creator.setWorld( world );
		} catch( FeatureNotUsingException e1 )
		{
			e1.printStackTrace();
		}
		long currentTime = System.currentTimeMillis();
		for ( int i = 0; i < 1200; i++ )
		{
			try
			{
				double stepX = i * movingSpeed;
				if ( justOneTime )
				{
					if ( stepX >= 1250 )
					{
						movingForward = false;
						justOneTime = false;
					}
				}
				if ( movingForward )
				{
					cameraX += movingSpeed;
					creator.resetTransformation();
					creator.setTransformation( Mat4.translate( cameraX, 0, 160 ).mult( Mat4.rotateX( 34 ) ) );
				}
				turnHouse();
				turnSpheresOnSecondPart();
				changeBG();
				// if ( ( i < 258 ) || ( i > 690 ) )
				// {
				// System.out.println( "skip picture " + NAME + i + ".png" );
				// continue;
				// }
				
				creator.startWithThreadPool();
				creator.savePicture( NAME + i + ".png" );
			} catch( IOException | FeatureNotUsingException e )
			{
				e.printStackTrace();
			}
		}
		creator.closeThreadPool();
		TimeObject time = new TimeObject( currentTime );
		System.out.printf( "%s Insgesamt benötigte %s: %.3f%n%n", TimeFormatter.getActualFormatTime(), time.getUnit(),
				time.getTime() );
		
		// System.out.println( "shutting down." );
		// Runtime runtime = Runtime.getRuntime();
		// Process proc = runtime.exec( "shutdown -s -t 0" );
		// System.exit( 0 );
	}
	
	private void turnSpheresOnSecondPart()
	{
		if ( cameraX > 1900 )
		{
			double angle = specialSphereDegree * specialSpheresTurn++;
			specialSpheres[0].setTransformation( Mat4.translate( 2275, 120, 50 ).mult( Mat4.rotateY( 90 + angle ) ) );
			specialSpheres[1].setTransformation(
					Mat4.translate( 2420, 275, 50 ).mult( Mat4.rotateX( angle ) ).mult( Mat4.rotateX( 90 ) ) );
			specialSpheres[2].setTransformation( Mat4.translate( 2650, 80, 50 ).mult( Mat4.rotateY( 90 - angle ) ) );
		}
	}
	
	private void changeBG()
	{
		if ( ( cameraX >= 775 ) && ( cameraX < 1250 ) )
		{
			double percent = ( cameraX - 775 ) / 450.; // first startValue, second length of effect
			( (InterpolateMaterial) bg.getMaterial() ).setChanceOfFirstMaterial( 1 - percent );
		}
		if ( ( cameraX >= 1300 ) && ( cameraX < 1800 ) && movingForward )
		{
			double percent = ( cameraX - 1300 ) / 475.;
			( (InterpolateMaterial) bg.getMaterial() ).setChanceOfFirstMaterial( percent );
		}
	}
	
	private void turnHouse()
	{
		if ( !movingForward )
		{
			double angle = houseDegree * houseTurn++;
			if ( angle >= -360 )
			{
				houseGroup.setTransformation(
						Mat4.translate( 1300, 0, -70 ).mult( Mat4.rotateY( 90 + angle ) ).mult( Mat4.rotateY( 57 ) ) );
			}
			else
			{
				movingForward = true;
			}
		}
	}
	
	public static void main( String[] args ) throws IOException
	{
		// new Rendering5();
	}
	
}
