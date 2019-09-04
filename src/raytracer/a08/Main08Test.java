/**
 * 
 */

package raytracer.a08;

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
import raytracer.imageCreator.shape.withMaterial.material.BackgroundInterpolation;
import raytracer.imageCreator.shape.withMaterial.material.BackgroundMaterial;
import raytracer.imageCreator.shape.withMaterial.material.Density;
import raytracer.imageCreator.shape.withMaterial.material.GlassMaterial;
import raytracer.imageCreator.shape.withMaterial.material.Material;
import raytracer.imageCreator.shape.withMaterial.material.PerfectDiffuseMaterial;
import raytracer.imageCreator.shape.withMaterial.primitives.Rectangle;
import raytracer.imageCreator.shape.withMaterial.primitives.Sphere;
import raytracer.imageCreator.shape.withMaterial.texture.ConstantTexture;

/**
 * @author Tobias Fox
 *
 */
public class Main08Test
{
	
	private String name = "doc/test/a08-";
	private static final int WIDTH = 480;
	private static final int HEIGHT = 270;
	private static final int ABTASTRATE = 6;
	private static final int DEPTH = 3;
	
	private Main08Test()
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
		try
		{
			creator.setTransformation( Mat4.translate( 0, 0, 5 ) );
		} catch( FeatureNotUsingException e1 )
		{
			e1.printStackTrace();
		}
		creator.startWithThreadPool( true );
		try
		{
			creator.savePicture( name + 4 + ".png" );
		} catch( IOException e )
		{
			System.out.println( "couldnt save picture" + 1 );
			e.printStackTrace();
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
	
	private Group createScene3()
	{
		Shape bg = new Background( new BackgroundMaterial() );
		Group s = new Group(
				new Sphere( 30, new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 1, 0, 0 ) ) ) ) );
		return new Group( s );
	}
	
	// rectange mit width/1
	private Group createScene()
	{
		Shape bg = new Background( new BackgroundMaterial() );
		Vec3 center = new Vec3( 0, -501, 0 );
		Vec3 color = new Vec3( .85, .85, .85 );
		Material material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape earth = new Sphere( center, 500, material );
		Group s = new Group( Mat4.translate( -1.5, .2, 3 ), new Sphere( 1.2, new GlassMaterial( Density.GLAS ) ) );
		// Group s2 = new Group(Mat4.translate(-1.5, .2, 3), new Sphere(-1.1,
		// new GlassMaterial(Density.GLAS)));
		// Group s = new Group( Mat4.translate( 0, 0, 3 ),
		// new Sphere( 1.2, new PerfectReflectiveMaterial( ) ) );
		Group groupS = new Group( s );
		Group t = new Group( Mat4.translate( 0, 0, -2 ),
				new Sphere( 2, new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 1, 0, 0 ) ) ) ) );
		Group t2 = new Group( Mat4.translate( -4, 0, 0 ), t );
		Group t3 = new Group( Mat4.translate( -9, 0, 0 ), t );
		Group t4 = new Group( Mat4.translate( 9, 0, 0 ), t );
		Group t5 = new Group( Mat4.translate( 0, 0, 10 ),
				new Sphere( 1, new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 0, 1 ) ) ) ) );
		return new Group( bg, earth, groupS, t, t3, t4, t5 );
	}
	
	private Group createScene2()
	{
		Shape bg = new Background( new BackgroundMaterial() );
		Rectangle r = new Rectangle( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 0, 1 ) ) ) );
		Group rt = new Group( Mat4.rotateX( -90 ).mult( Mat4.translate( -100000, 0, -1000 ) ), r );
		Sphere s = new Sphere( 1, new GlassMaterial( Density.GLAS ) );
		Group s2 = new Group( Mat4.translate( 0, 0, -5 ),
				new Sphere( 1, new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 1, 0, 0 ) ) ) ) );
		// Group s2 = new Group( new Sphere( -.7, new PerfectReflectiveMaterial() ) );
		// Group s2 = new Group( new Sphere( -.7, new GlassMaterial( Density.GLAS ) ) );
		return new Group( bg, rt, s, s2 );
	}
	
	public static void main( String[] a )
	{
		new Main08Test();
	}
}
