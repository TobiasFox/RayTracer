/**
 * 
 */

package raytracer.a05;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import raytracer.imageCreator.ImageCreator;
import raytracer.imageCreator.World;
import raytracer.imageCreator.enums.ImageQualities;
import raytracer.imageCreator.enums.PixelColorCalculations;
import raytracer.imageCreator.exceptions.FeatureNotUsingException;
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
public class Main05
{
	
	private static final String NAME = "doc/a05/a05_.png";
	private static final int WIDTH = 480;
	private static final int HEIGHT = 270;
	private static final int ABTASTRATE = 1;
	private static final int DEPTH = 1;
	
	private Main05()
	{
		ImageCreator creator = new ImageCreator( WIDTH, HEIGHT, ABTASTRATE, ImageQualities.Bit16,
				PixelColorCalculations.MonteCarloRaytrace );
		
		try
		{
			creator.setWorld( new World( createScene(), createBackground(), createLightSources() ) );
			creator.setRecursiveDepth( DEPTH );
			creator.startWithThreads();
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
		Vec3 center = new Vec3( 0, 0, -5 );
		Vec3 color = new Vec3( 1, 0, 0 );
		PerfectDiffuseMaterial material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape sphere = new Sphere( center, 1, material );
		
		center = new Vec3( 0, -501, 0 );
		color = new Vec3( .85, .85, .85 );
		material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape earth = new Sphere( center, 500, material );
		
		center = new Vec3( 1, -.6, -1.5 );
		color = new Vec3( 255, 51, 204 ).toRGB();
		material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape sphere2 = new Sphere( center, .35, material );
		
		center = new Vec3( -2, -.5, -1.5 );
		color = new Vec3( 0, 1, .5 );
		material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape sphere6 = new Sphere( center, .35, material );
		
		center = new Vec3( -.5, -.85, -2 );
		color = new Vec3( 255, 102, 0 ).toRGB();
		material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape sphere10 = new Sphere( center, .1, material );
		
		center = new Vec3( -10, 1, -10 );
		color = new Vec3( 0, 1, 0 );
		material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape sphere8 = new Sphere( center, 2, material );
		
		center = new Vec3( 5, 0, -5 );
		color = new Vec3( 1, 1, 0 );
		material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape sphere7 = new Sphere( center, 1, material );
		
		center = new Vec3( 5, 1.2, -4.2 );
		color = new Vec3( 1, 0, 0 );
		material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape sphere9 = new Sphere( center, .5, material );
		
		Group group = new Group();
		group.addAllShape( sphere, sphere7, sphere8, sphere6, sphere2, sphere9, sphere10, earth );
		
		return group;
	}
	
	public static void main( String[] a )
	{
		new Main05();
	}
}
