/**
 * 
 */

package raytracer.a06;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import raytracer.imageCreator.ImageCreator;
import raytracer.imageCreator.World;
import raytracer.imageCreator.enums.ImageQualities;
import raytracer.imageCreator.enums.PixelColorCalculations;
import raytracer.imageCreator.exceptions.FeatureNotUsingException;
import raytracer.imageCreator.general.Mat4;
import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.shape.Shape;
import raytracer.imageCreator.shape.withMaterial.Background;
import raytracer.imageCreator.shape.withMaterial.HouseOfCircles;
import raytracer.imageCreator.shape.withMaterial.group.Group;
import raytracer.imageCreator.shape.withMaterial.light.Light;
import raytracer.imageCreator.shape.withMaterial.material.BackgroundInterpolation;
import raytracer.imageCreator.shape.withMaterial.material.Material;
import raytracer.imageCreator.shape.withMaterial.material.PerfectDiffuseMaterial;
import raytracer.imageCreator.shape.withMaterial.primitives.Sphere;
import raytracer.imageCreator.shape.withMaterial.texture.ConstantTexture;

/**
 * @author Tobias Fox
 *
 */
public class Main06
{
	
	private String name = "doc/a06_-";
	private static final int WIDTH = 480;
	private static final int HEIGHT = 270;
	private static final int ABTASTRATE = 2;
	private static final int DEPTH = 1;
	
	private Main06()
	{
		ImageCreator creator = new ImageCreator( WIDTH, HEIGHT, ABTASTRATE, ImageQualities.Bit16,
				PixelColorCalculations.MonteCarloRaytrace );
		
		try
		{
			creator.setWorld( new World( createScene(), createBackground(), createLightSources() ) );
			creator.setRecursiveDepth( DEPTH );
		} catch( FeatureNotUsingException e1 )
		{
			System.out.println( "error with scene or recursive" );
			e1.printStackTrace();
		}
		
		for ( int i = 1; i < 5; i++ )
		{
			try
			{
				if ( i != 1 )
				{
					creator.resetTransformation();
				}
				
				creator.setTransformation( setTransformation( i ) );
				creator.startWithThreads();
				try
				{
					creator.savePicture( name + i + ".png" );
				} catch( IOException e )
				{
					System.out.println( "couldnt save picture" + i );
					e.printStackTrace();
				}
			} catch( FeatureNotUsingException e2 )
			{
				System.out.println( "error with transformation or threading" );
				e2.printStackTrace();
			}
		}
		creator.closeThreadPool();
	}
	
	private Mat4 setTransformation( int i )
	{
		switch ( i )
		{
			case 1:
				// links oben nach rechts unten
				return Mat4.translate( -13, 19, 11 ).mult( Mat4.rotateY( -30 ).mult( Mat4.rotateX( -25 ) ) );
			case 2:
				// mitte unten, nach links oben
				return Mat4.translate( 2, 0, 7 ).mult( Mat4.rotateX( 30 ) ).mult( Mat4.rotateY( 15 ) );
			case 3:
				// innerhalb des 4. nach oben
				return Mat4.translate( 12.5, 0, -3.75 ).mult( Mat4.rotateX( 90 ) );
			case 4:
				// in der dachebene des 4. hauses nach links unten
				return Mat4.translate( 15, 22, -7.4 ).mult( Mat4.rotateY( 28 ).mult( Mat4.rotateX( -33 ) ) );
			default:
				return Mat4.identity;
		}
	}
	
	private Background createBackground()
	{
		return new Background( new BackgroundInterpolation() );
	}
	
	private List<Light> createLightSources()
	{
		ArrayList<Light> lightSources = new ArrayList<>();
		return lightSources;
	}
	
	private Group createScene()
	{
		Vec3 center = new Vec3( 0, -501, 0 );
		Vec3 color = new Vec3( .85, .85, .85 );
		Material material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape earth = new Sphere( center, 500, material );
		
		Vec3 dimension = new Vec3( 10, 10, 7.5 );
		Vec3 start = new Vec3( 0, 0, 0 );
		Group group = new Group();
		group.addShape( earth );
		
		Vec3[] colorArray = {
				new Vec3( 1, 0, 0 ), new Vec3( 1, 1, 0 ), new Vec3( 0, 0, 1 ), new Vec3( 1, 1, 1 )
		};
		Vec3[] translations = {
				new Vec3( -17.5, 0, 0 ), new Vec3( -17.5, 0, -17.5 ), new Vec3( 7.5, 0, -17.5 ), new Vec3( 7.5, 0, 0 )
		};
		for ( int i = 0; i < 4; i++ )
		{
			HouseOfCircles house1 = new HouseOfCircles( dimension.add( 0, i * 5., 0 ), i + 1, 5, start, .75,
					colorArray[i], colorArray[( i + 1 ) % 4] ).createRadiantHouse();
			house1.addTransformation( Mat4.translate( translations[i] ) );
			group.addShape( house1 );
		}
		
		return group;
	}
	
	public static void main( String[] a )
	{
		new Main06();
	}
}
