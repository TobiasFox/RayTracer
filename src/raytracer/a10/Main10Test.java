/**
 * 
 */

package raytracer.a10;

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
import raytracer.imageCreator.shape.withMaterial.group.Group;
import raytracer.imageCreator.shape.withMaterial.light.Light;
import raytracer.imageCreator.shape.withMaterial.light.PointLight;
import raytracer.imageCreator.shape.withMaterial.material.BackgroundInterpolation;
import raytracer.imageCreator.shape.withMaterial.material.PerfectDiffuseMaterial;
import raytracer.imageCreator.shape.withMaterial.primitives.Sphere;
import raytracer.imageCreator.shape.withMaterial.texture.ConstantTexture;

/**
 * @author Tobias Fox
 *
 */
public class Main10Test
{
	
	private String name = "doc/test/a10-";
	private static final int WIDTH = 500;
	private static final int HEIGHT = 250;
	private static final int ABTASTRATE = 20;
	private static final int DEPTH = 15;
	
	private Main10Test()
	{
		ImageCreator creator = new ImageCreator( WIDTH, HEIGHT, ABTASTRATE, ImageQualities.Bit16,
				PixelColorCalculations.MonteCarloRaytrace );
		try
		{
			creator.setWorld( createWorld() );
			creator.setRecursiveDepth( DEPTH );
		} catch( FeatureNotUsingException e1 )
		{
			System.out.println( "error with scene or recursive" );
			e1.printStackTrace();
		}
		try
		{
			creator.setTransformation( Mat4.translate( .5, 13, 7 ).mult( Mat4.rotateX( -90 ) ) );
			
		} catch( FeatureNotUsingException e1 )
		{
			e1.printStackTrace();
		}
		creator.startWithThreadPool( true );
		try
		{
			creator.savePicture( name + 2 + ".png" );
		} catch( IOException e )
		{
			System.out.println( "couldnt save picture" );
			e.printStackTrace();
		}
	}
	
	private World createWorld()
	{
		Group scene = createScene();
		List<Light> lightsources = createLightSources();
		return new World( scene, createBackground(), lightsources );
	}
	
	private List<Light> createLightSources()
	{
		ArrayList<Light> lightSources = new ArrayList<>();
		// Vec3 intensity = new Vec3( .5, .5, .5 );
		Vec3 intensity = new Vec3( 40, 40, 40 );
		// lightSources.add( new PointLight( new Vec3( 5, 5, 0 ), intensity ) );
		lightSources.add( new PointLight( new Vec3( 0, 5, 10 ), new Vec3( 0, 500, 750 ) ) );
		// lightSources.add( new PointLight( new Vec3( -5, 5, -3 ), new Vec3( 0, 0, 100 ) ) );
		return lightSources;
	}
	private Background createBackground()
	{
		Vec3 colorOne = new Vec3( 230, 240, 255 ).toRGB();
		Vec3 colorTwo = new Vec3( 153, 194, 255 ).toRGB();
		Background bg = new Background( new BackgroundInterpolation( colorOne, colorTwo ) );
		return bg;
	}
	
	private Group createScene()
	{
		Vec3 center = new Vec3( 0, -501, 0 );
		// Vec3 color = new Vec3( .5, .5, .5 );
		Vec3 color = new Vec3( 130, 60, 10 ).toRGB();
		PerfectDiffuseMaterial material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape earth = new Sphere( center, 500, material );
		
		Group s2 = new Group( Mat4.translate( 2, 0, -1 ),
				new Sphere( 1, new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 1, 0 ) ) ) ) );
		
		Sphere s1 = new Sphere( 1, new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 1, 0, 0 ) ) ) );
		
		return new Group( earth, s1, s2 );
	}
	
	public static void main( String[] args )
	{
		new Main10Test();
	}
}
