
package raytracer.renderingcompetition;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import raytracer.imageCreator.ImageCreator;
import raytracer.imageCreator.World;
import raytracer.imageCreator.enums.ImageQualities;
import raytracer.imageCreator.enums.PixelColorCalculations;
import raytracer.imageCreator.exceptions.FeatureNotUsingException;
import raytracer.imageCreator.general.Mat4;
import raytracer.imageCreator.shape.withMaterial.Background;
import raytracer.imageCreator.shape.withMaterial.HouseOfCircles;
import raytracer.imageCreator.shape.withMaterial.material.InterpolateMaterial;
import raytracer.imageCreator.threads.time.TimeFormatter;
import raytracer.imageCreator.threads.time.TimeObject;

public class RenderingTest4
{
	
	private static final String NAME = "doc/test/animation6_/c";
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int ABTASTRATE = 5;
	private static final int DEPTH = 4;
	private RenderingSceneCreation render;
	private HouseOfCircles houseGroup;
	private Background bg;
	private double houseTurn = 0;
	private double houseDegree = -3;
	private boolean upperEdge = true;
	
	public RenderingTest4() throws IOException
	{
		render = new RenderingSceneCreation();
		World world = render.createWorld();
		houseGroup = render.getHouseGroup();
		bg = world.getBackground();
		start( world, false );
	}
	
	/*
	 * langsamer durch die scene
	 * stehen bleiben beim house?
	 * später mit der rotation des hauses starten
	 * house dreht 90° weiter als gedacht?
	 * optional:
	 * ray edging isValid einbauen?
	 */
	private void start( World world, boolean shutdown ) throws IOException
	{
		ImageCreator creator = new ImageCreator( WIDTH, HEIGHT, ABTASTRATE, ImageQualities.Bit16,
				PixelColorCalculations.MonteCarloRaytrace );
		
		if ( shutdown )
		{
			try
			{
				System.setOut( new PrintStream( "result.txt" ) );
			} catch( FileNotFoundException exc )
			{
				exc.printStackTrace();
			}
		}
		
		try
		{
			creator.setRecursiveDepth( DEPTH );
			creator.setWorld( world );
		} catch( FeatureNotUsingException e1 )
		{
			e1.printStackTrace();
		}
		long currentTime = System.currentTimeMillis();
		for ( int i = 0; i < 411; i++ )
		{
			try
			{
				int stepX = ( i * 5 );
				creator.resetTransformation();
				creator.setTransformation( Mat4.translate( stepX, 0, 160 ).mult( Mat4.rotateX( 29.5 ) ) );
				
				turnHouse( stepX );
				changeBG( stepX );
				
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
		
		if ( shutdown )
		{
			System.out.println( "shutting down." );
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec( "shutdown -s -t 0" );
			System.exit( 0 );
		}
	}
	
	private void changeBG( int stepX )
	{
		if ( ( stepX >= 525 ) && ( stepX < 1300 ) )
		{
			double percent = ( stepX - 525 ) / 375.; // first startValue, second length of effect
			( (InterpolateMaterial) bg.getMaterial() ).setChanceOfFirstMaterial( 1 - percent );
		}
		if ( ( stepX >= 1300 ) && ( stepX < 1700 ) )
		{
			double percent = ( stepX - 1300 ) / 375.;
			( (InterpolateMaterial) bg.getMaterial() ).setChanceOfFirstMaterial( percent );
		}
	}
	
	private void turnHouse( int stepX )
	{
		if ( ( stepX >= 875 ) && upperEdge )
		{
			double angle = houseDegree * houseTurn++;
			if ( angle >= -360 )
			{
				houseGroup.setTransformation(
						Mat4.translate( 1200, 0, -70 ).mult( Mat4.rotateY( 90 + angle ) ).mult( Mat4.rotateY( 57 ) ) );
			}
			else
			{
				upperEdge = false;
			}
		}
	}
	
	public static void main( String[] args ) throws IOException
	{
		// new RenderingTest4();
	}
	
}
