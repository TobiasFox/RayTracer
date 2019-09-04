
package raytracer.renderingcompetition;

import java.io.IOException;
import java.util.ArrayList;

import raytracer.imageCreator.ImageCreator;
import raytracer.imageCreator.World;
import raytracer.imageCreator.enums.ImageQualities;
import raytracer.imageCreator.enums.PixelColorCalculations;
import raytracer.imageCreator.exceptions.FeatureNotUsingException;
import raytracer.imageCreator.general.Mat4;
import raytracer.imageCreator.shape.withMaterial.Background;
import raytracer.imageCreator.shape.withMaterial.HouseOfCircles;
import raytracer.imageCreator.shape.withMaterial.group.Group;
import raytracer.imageCreator.shape.withMaterial.light.Light;
import raytracer.imageCreator.shape.withMaterial.material.InterpolateMaterial;
import raytracer.imageCreator.threads.time.TimeFormatter;
import raytracer.imageCreator.threads.time.TimeObject;

public class RenderingTest3
{
	
	private static final String NAME = "doc/test/bgTest/c0";
	private static final int WIDTH = 480;
	private static final int HEIGHT = 270;
	private static final int ABTASTRATE = 1;
	private static final int DEPTH = 2;
	private HouseOfCircles houseGroup;
	private Background bg;
	
	// parameter for animation of houseGroup
	private double houseTurn = 0;
	// private double houseDegree = -4;
	private double houseDegree = -30;
	private boolean upperEdgeForHouseTurning = true;
	
	// parameter for animation of specialSpheres
	private int specialSpheresTurn = 0;
	private Group[] specialSpheres;
	private double specialSphereDegree = 6;
	private ArrayList<Light> lightSources;
	
	// parameter for camera moving
	private boolean movingForward = true;
	private double cameraX = 725;
	private double movingSpeed = 25;
	private boolean justOneTime = true;
	
	public RenderingTest3()
	{
		RenderingSceneCreation render = new RenderingSceneCreation();
		World world = render.createWorld();
		lightSources = render.getLightSources();
		houseGroup = render.getHouseGroup();
		bg = world.getBackground();
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
		for ( int i = 0; i < 69; i++ )
		{
			try
			{
				double stepX = i * movingSpeed;
				if ( justOneTime )
				{
					if ( cameraX >= 1250 )
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
				turnHouse2( stepX );
				changeBG2( stepX );
				turnSpheresOnSecondPart( stepX );
				changeLightSources( stepX );
				
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
	
	private void changeLightSources( double stepX )
	{
		// if ( stepX > 525 )
		// {
		// // lightSources.add( new PointLight( new Vec3( 1250, -200, 200 ), new Vec3( 100000, 100000, 100000 ) ) );
		// }
		// if ( stepX > 1425 )
		// {
		// // lightSources.clear();
		// }
	}
	
	private void turnSpheresOnSecondPart( double stepX )
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
	
	private void changeBG2( double stepX )
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
	
	private void changeBG( double stepX )
	{
		if ( ( stepX >= 825 ) && ( stepX < 1300 ) )
		{
			double percent = ( stepX - 825 ) / 375.; // first startValue, second length of effect
			( (InterpolateMaterial) bg.getMaterial() ).setChanceOfFirstMaterial( 1 - percent );
		}
		if ( ( stepX >= 1300 ) && ( stepX < 1725 ) )
		{
			double percent = ( stepX - 1300 ) / 375.;
			( (InterpolateMaterial) bg.getMaterial() ).setChanceOfFirstMaterial( percent );
		}
	}
	
	private void turnHouse2( double stepX )
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
				upperEdgeForHouseTurning = false;
				movingForward = true;
			}
		}
	}
	
	private void turnHouse( double stepX )
	{
		if ( ( stepX >= 1050 ) && upperEdgeForHouseTurning )
		{
			double angle = houseDegree * houseTurn++;
			if ( angle >= -360 )
			{
				houseGroup.setTransformation(
						Mat4.translate( 1200, 0, -70 ).mult( Mat4.rotateY( 90 + angle ) ).mult( Mat4.rotateY( 57 ) ) );
			}
			else
			{
				upperEdgeForHouseTurning = false;
			}
		}
	}
	
	public static void main( String[] args )
	{
		// new RenderingTest3();
	}
	
}
