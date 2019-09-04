/**
 * 
 */

package raytracer.b04;

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
import raytracer.imageCreator.shape.withMaterial.material.PerfectDiffuseMaterial;
import raytracer.imageCreator.shape.withMaterial.primitives.Sphere;
import raytracer.imageCreator.shape.withMaterial.texture.ConstantTexture;

/**
 * @author Tobias Fox
 *
 */
public class Main_b04
{
	
	private static final String NAME = "doc/test/a07-232.png";
	private static final int WIDTH = 480;
	private static final int HEIGHT = 270;
	private static final int ABTASTRATE = 3;
	private static final int DEPTH = 7;
	
	private Main_b04()
	{
		ImageCreator creator = new ImageCreator( WIDTH, HEIGHT, ABTASTRATE, ImageQualities.Bit16,
				PixelColorCalculations.MonteCarloRaytrace );
		
		try
		{
			creator.setWorld( new World( createScene(), createBackground(), createLightSources() ) );
			creator.setRecursiveDepth( DEPTH );
			// creator.setTransformation(Mat4.rotateY(90));
			creator.setTransformation( Mat4.translate( 0, 20, 0 ).mult( Mat4.rotateX( -90 ) ) );
			creator.startWithThreadPool( true );
		} catch( FeatureNotUsingException e1 )
		{
			e1.printStackTrace();
		}
		
		try
		{
			creator.savePicture( NAME );
		} catch( IOException e )
		{
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
	
	private Group createScene()
	{
		// vorne
		Vec3 center = new Vec3( 0, 2, 0 );
		Vec3 color = new Vec3( 1, 0, 0 );
		PerfectDiffuseMaterial material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape sphere = new Sphere( center, 2, material );
		// hinten
		color = new Vec3( 0, 0, 1 );
		material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape sphere2 = new Sphere( center, 2, material );
		
		// rechts
		color = new Vec3( 0, 1, 0 );
		material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape sphere3 = new Sphere( center, 2, material );
		
		// links
		color = new Vec3( 0, 1, 1 );
		material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape sphere4 = new Sphere( center, 2, material );
		
		center = new Vec3( 0, -501, 0 );
		color = new Vec3( .85, .85, .85 );
		material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape earth = new Sphere( center, 500, material );
		
		Group g1 = new Group( Mat4.translate( 0, 0, -10 ), sphere );
		Group g2 = new Group( Mat4.translate( 0, 0, 10 ), sphere2 );
		Group g3 = new Group( Mat4.translate( 10, 0, 0 ), sphere3 );
		Group g4 = new Group( Mat4.translate( -10, 0, 0 ), sphere4 );
		
		return new Group( earth, g1, g2, g3, g4 );
	}
	
	public static void main( String[] a )
	{
		new Main_b04();
	}
}
