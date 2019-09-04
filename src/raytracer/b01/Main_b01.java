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
import raytracer.imageCreator.shape.withMaterial.Zylinder;
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
public class Main_b01
{
	
	private static final String NAME = "doc/test/b01test-1.png";
	private static final int WIDTH = 480;
	private static final int HEIGHT = 270;
	private static final int ABTASTRATE = 5;
	private static final int DEPTH = 5;
	
	private Main_b01()
	{
		ImageCreator creator = new ImageCreator( WIDTH, HEIGHT, ABTASTRATE, ImageQualities.Bit16,
				PixelColorCalculations.MonteCarloRaytrace );
		
		try
		{
			creator.setWorld( new World( createScene(), createBackground(), createLightSources() ) );
			creator.setRecursiveDepth( DEPTH );
			// creator.setTransformation( Mat4.translate( 0, 10, 0 ).mult( Mat4.rotateX( -90 ) ) );
			// creator.setTransformation( Mat4.translate( 0, 3, 0 ).mult( Mat4.rotateX( -90 ) ) );
			creator.setTransformation( Mat4.translate( .5, 0, 13 ) );
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
		Vec3 center = new Vec3( 0, -502.5, 0 );
		Vec3 color = new Vec3( .85, .85, .85 );
		Material material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape earth = new Sphere( center, 500, material );
		
		Shape cone = new Cone( 5, 5,
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 238, 118 ).toRGB() ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 25, 25, 112 ).toRGB() ) ) );
		Group c = new Group( Mat4.translate( 10, 7.5, 0 ), cone );
		Group c2 = new Group( Mat4.translate( -3, 1, 0 ),
				new Cone( 2, 3, new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 238, 118 ).toRGB() ) ),
						new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 25, 25, 112 ).toRGB() ) ) ) );
		Shape cone2 = new Cone( 1, 1,
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 238, 118 ).toRGB() ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 25, 25, 112 ).toRGB() ) ) );
		Group c3 = new Group( Mat4.translate( -2, 1, 0 ).mult( Mat4.rotateX( 180 ) ), cone2 );
		Group c4 = new Group( Mat4.translate( -.5, 1, 0 ).mult( Mat4.rotateX( 90 ) ), cone2 );
		Group c5 = new Group( Mat4.translate( 1.5, .2, 0 ).mult( Mat4.rotateX( -90 ) ), cone2 );
		Group cones = new Group( Mat4.translate( 4, 0, 0 ), c, c2, c3, c4, c5 );
		
		Shape z = new Zylinder( 2, 3, new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 1, 0, 0 ) ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 255, 140, 0 ).toRGB() ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 1, 0 ) ) ) );
		Group zy1 = new Group( Mat4.translate( -6, 1, 0 ), z );
		Group zy2 = new Group( Mat4.translate( 0, 1, 0 ).mult( Mat4.rotateX( -30 ) ), z );
		Group zy3 = new Group( Mat4.translate( 6, 1, 0 ).mult( Mat4.rotateX( 30 ) ), z );
		Group zylinders = new Group( Mat4.translate( -9, 0, 0 ), zy1, zy2, zy3 );
		
		return new Group( earth, cones, zylinders );
	}
	
	public static void main( String[] a )
	{
		new Main_b01();
	}
}
