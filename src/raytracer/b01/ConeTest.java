/**
 * 
 */

package raytracer.b01;

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
import raytracer.imageCreator.shape.withMaterial.Cone;
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
public class ConeTest
{
	
	private static final String NAME = "doc/test/a07-23sdfgtz2.png";
	private static final int WIDTH = 480;
	private static final int HEIGHT = 270;
	private static final int ABTASTRATE = 5;
	private static final int DEPTH = 5;
	
	private ConeTest()
	{
		ImageCreator creator = new ImageCreator( WIDTH, HEIGHT, ABTASTRATE, ImageQualities.Bit16,
				PixelColorCalculations.MonteCarloRaytrace );
		
		try
		{
			creator.setWorld( new World( createScene(), createBackground(), createLightSources() ) );
			creator.setRecursiveDepth( DEPTH );
			// creator.setTransformation( Mat4.translate( 0, 3, 3 ).mult( Mat4.rotateX( -50 ) ) );
			// creator.setTransformation( Mat4.translate( 5, 0, 0 ).mult( Mat4.rotateY( 90 ) ) );
			// creator.setTransformation( Mat4.translate( 0, -7.5, 7.5 ).mult( Mat4.rotateX( 45 ) ) );
			// creator.setTransformation( Mat4.translate( 0, -5, 5 ).mult( Mat4.rotateX( 35 ) ) );
			// creator.setTransformation( Mat4.translate( 0, 10, 5 ).mult( Mat4.rotateX( -35 ) ) );
			// creator.setTransformation( Mat4.translate( 0, 0, -10 ).mult( Mat4.rotateY( 180 ) ) );
			creator.setTransformation( Mat4.translate( 0, 0, 8 ) );
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
		Group c = new Group( Mat4.translate( 0, 6, 0 ),
				new Cone( 3, 4, new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 1, 0, 0 ) ) ),
						new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 0, 1 ) ) ) ) );
		
		Group s = new Group( Mat4.translate( 0, 0, -5 ),
				new Sphere( 1, new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 1, 0 ) ) ) ) );
		
		Vec3 center = new Vec3( 0, -501, 0 );
		Vec3 color = new Vec3( .85, .85, .85 );
		Material material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape earth = new Sphere( center, 500, material );
		return new Group( s, c, earth );
	}
	
	public static void main( String[] a )
	{
		new ConeTest();
	}
}
