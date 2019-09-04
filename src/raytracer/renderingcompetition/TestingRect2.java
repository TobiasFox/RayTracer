
package raytracer.renderingcompetition;

import java.io.IOException;

import raytracer.imageCreator.ImageCreator;
import raytracer.imageCreator.World;
import raytracer.imageCreator.enums.ImageQualities;
import raytracer.imageCreator.enums.PixelColorCalculations;
import raytracer.imageCreator.exceptions.FeatureNotUsingException;
import raytracer.imageCreator.general.Mat4;
import raytracer.imageCreator.shape.withMaterial.group.Group;
import raytracer.imageCreator.threads.time.TimeFormatter;
import raytracer.imageCreator.threads.time.TimeObject;

public class TestingRect2
{
	
	private static final String NAME = "doc/test/rect2Test2/c0";
	private static final int WIDTH = 480;
	private static final int HEIGHT = 270;
	private static final int ABTASTRATE = 6;
	private static final int DEPTH = 4;
	private RenderingSceneCreation render;
	private int specialSpheresTurn;
	private Group[] specialSpheres;
	
	public TestingRect2()
	{
		render = new RenderingSceneCreation();
		World world = render.createWorld();
		specialSpheres = render.getSpecialSpheres();
		start( world );
	}
	
	private void start( World world )
	{
		ImageCreator creator = new ImageCreator( WIDTH, HEIGHT, ABTASTRATE, ImageQualities.Bit16,
				PixelColorCalculations.MonteCarloRaytrace );
		
		try
		{
			creator.setRecursiveDepth( DEPTH );
			creator.setWorld( world );
		} catch( FeatureNotUsingException e1 )
		{
			e1.printStackTrace();
		}
		long currentTime = System.currentTimeMillis();
		for ( int i = 0; i < 55; i++ )
		{
			try
			{
				int stepX = ( i * 25 );
				creator.resetTransformation();
				creator.setTransformation( Mat4.translate( stepX + 1725, 0, 160 ).mult( Mat4.rotateX( 34 ) ) );
				
				if ( stepX > 300 )
				{
					double angle = 30 * specialSpheresTurn++;
					specialSpheres[0]
							.setTransformation( Mat4.translate( 2275, 120, 50 ).mult( Mat4.rotateY( 90 + angle ) ) );
					specialSpheres[1].setTransformation(
							Mat4.translate( 2420, 275, 50 ).mult( Mat4.rotateX( angle ) ).mult( Mat4.rotateX( 90 ) ) );
					specialSpheres[2]
							.setTransformation( Mat4.translate( 2650, 80, 50 ).mult( Mat4.rotateY( 90 - angle ) ) );
				}
				
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
		
	}
	public static void main( String[] args )
	{
		// new TestingRect2();
	}
}
