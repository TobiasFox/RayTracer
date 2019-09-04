
package raytracer.renderingcompetition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import raytracer.imageCreator.ImageCreator;
import raytracer.imageCreator.World;
import raytracer.imageCreator.enums.ImageQualities;
import raytracer.imageCreator.enums.PixelColorCalculations;
import raytracer.imageCreator.exceptions.FeatureNotUsingException;
import raytracer.imageCreator.general.Mat4;
import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.shape.withMaterial.Background;
import raytracer.imageCreator.shape.withMaterial.Zylinder;
import raytracer.imageCreator.shape.withMaterial.group.Group;
import raytracer.imageCreator.shape.withMaterial.light.Light;
import raytracer.imageCreator.shape.withMaterial.material.BackgroundInterpolation;
import raytracer.imageCreator.shape.withMaterial.material.Density;
import raytracer.imageCreator.shape.withMaterial.material.GlassMaterial;
import raytracer.imageCreator.shape.withMaterial.material.PerfectDiffuseMaterial;
import raytracer.imageCreator.shape.withMaterial.material.ReflectiveMaterial;
import raytracer.imageCreator.shape.withMaterial.primitives.Rectangle2;
import raytracer.imageCreator.shape.withMaterial.primitives.Sphere;
import raytracer.imageCreator.shape.withMaterial.texture.ConstantTexture;
import raytracer.imageCreator.shape.withMaterial.texture.PictureTexture;

public class RenderingTest1
{
	
	private static final String NAME = "doc/test/c01.png";
	private static final int WIDTH = 480;
	private static final int HEIGHT = 270;
	private static final int ABTASTRATE = 4;
	private static final int DEPTH = 16;
	private static final String path = "src/raytracer/textures/";
	
	private RenderingTest1()
	{
		ImageCreator creator = new ImageCreator( WIDTH, HEIGHT, ABTASTRATE, ImageQualities.Bit16,
				PixelColorCalculations.MonteCarloRaytrace );
		
		try
		{
			creator.setWorld( new World( createScene(), createBackground(), createLightSources() ) );
			// optimale position bis jetzt
			creator.setTransformation( Mat4.translate( 0, 0, 160 ).mult( Mat4.rotateX( 29.5 ) ) );
			
			// direkt auf dem boden
			// creator.setTransformation( Mat4.translate( 0, 0, 3 ).mult( Mat4.rotateX( 90 ) ) );
			// für ersten zy
			// creator.setTransformation(
			// Mat4.translate( 0, 20, 20 ).mult( Mat4.rotateX( 90 ).mult( Mat4.rotateY( 30 ) ) ) );
			// für zweiten zy
			// creator.setTransformation(
			// Mat4.translate( 200, 100, 20 ).mult( Mat4.rotateX( 90 ).mult( Mat4.rotateY( 30 ) ) ) );
			// für zy3
			// creator.setTransformation(
			// Mat4.translate( 0, 110, 20 ).mult( Mat4.rotateX( 90 ).mult( Mat4.rotateY( 10 ) ) ) );
			
			// creator.setTransformation( Mat4.translate( 0, 4, 0 ).mult( Mat4.rotateX( -90 ) ) );
			
			creator.setRecursiveDepth( DEPTH );
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
		Vec3 colorOne = new Vec3( 10, 20, 50 ).toRGB();
		Vec3 colorTwo = new Vec3( 15, 27, 30 ).toRGB();
		Background bg = new Background( new BackgroundInterpolation( colorOne, colorTwo ) );
		return bg;
		// return new Background();
	}
	
	private List<Light> createLightSources()
	{
		ArrayList<Light> lightSources = new ArrayList<>();
		return lightSources;
	}
	
	private Group createScene()
	{
		
		Group rec1 = null;
		try
		{
			rec1 = new Group( new Rectangle2( 1600, 1200,
					new PerfectDiffuseMaterial( new PictureTexture( path + "texture1.jpg", false ) ) ) );
			// rec1 = new Group( Mat4.rotateZ( 90 ).mult( Mat4.rotateY( 10 ) ), new Rectangle2( 1600, 1200,
			// new PerfectDiffuseMaterial( new PictureTexture( path + "texture1.jpg", false ) ) ) );
			
		} catch( IOException exc )
		{
			exc.printStackTrace();
		}
		
		// new ConstantTexture( new Vec3( 243, 94, 19 ).toRGB() ) ) )
		
		Group sphereGroup1 = createSphereGroup( 35, Mat4.identity );
		Group sphereGroup2 = new Group( Mat4.translate( -70, 80, 35 ).mult( Mat4.rotateX( -90 ) ), sphereGroup1 );
		Group sphereGroup3 = new Group( Mat4.translate( 160, 80, 35 ).mult( Mat4.rotateX( -127 ) ), sphereGroup1 );
		Group sphereGroup4 = new Group( Mat4.translate( 110, 230, 35 ).mult( Mat4.rotateX( -127 ) ), sphereGroup1 );
		
		Group sphereGroup5 = new Group(
				Mat4.translate( 270, 285, 35 ).mult( Mat4.rotateX( -110 ).mult( Mat4.rotateZ( -10 ) ) ), sphereGroup1 );
		
		Group rect = new Group( sphereGroup2, sphereGroup3, sphereGroup4 );
		
		return new Group( rec1, rect, sphereGroup5 );
	}
	
	private Group createScene2()
	{
		
		Group rec1 = null;
		try
		{
			rec1 = new Group( new Rectangle2( 1600, 1200,
					new PerfectDiffuseMaterial( new PictureTexture( path + "texture1.jpg", false ) ) ) );
			// rec1 = new Group( Mat4.rotateZ( 90 ).mult( Mat4.rotateY( 10 ) ), new Rectangle2( 1600, 1200,
			// new PerfectDiffuseMaterial( new PictureTexture( path + "texture1.jpg", false ) ) ) );
			
		} catch( IOException exc )
		{
			exc.printStackTrace();
		}
		
		// new ConstantTexture( new Vec3( 243, 94, 19 ).toRGB() ) ) )
		
		Group sphereGroup1 = createSphereGroup( 35, Mat4.identity );
		Group sphereGroup2 = new Group( Mat4.translate( -70, 80, 50 ).mult( Mat4.rotateX( -127 ) ), sphereGroup1 );
		Group sphereGroup3 = new Group( Mat4.translate( 160, 80, 50 ).mult( Mat4.rotateX( -127 ) ), sphereGroup1 );
		Group sphereGroup4 = new Group( Mat4.translate( 110, 230, 30 ).mult( Mat4.rotateX( -110 ) ), sphereGroup1 );
		
		Group zylinder1 = new Group( Mat4.rotateZ( 90 ).mult( Mat4.translate( 80, -30, 50 ) ),
				new Zylinder( 4, 100, new ReflectiveMaterial( new ConstantTexture( new Vec3( 0, 1, 1 ) ) ) ) );
		
		Group zylinder2 = new Group( Mat4.rotateZ( 90 ).mult( Mat4.translate( 80, -30, 50 ) ),
				new Zylinder( 4, 60, new ReflectiveMaterial( new ConstantTexture( new Vec3( 0, 1, 1 ) ) ) ) );
		
		Group zy2 = new Group( Mat4.translate( 270, 0, 13 ).mult( Mat4.rotateY( -10 ) ), zylinder1 );
		
		Group zy3 = new Group( Mat4.translate( 215, 135, 5 ).mult( Mat4.rotateZ( 100 ).mult( Mat4.rotateY( 10 ) ) ),
				zylinder2 );
		
		Group sphereGroup5 = new Group(
				Mat4.translate( 270, 285, 35 ).mult( Mat4.rotateX( -110 ).mult( Mat4.rotateZ( -10 ) ) ), sphereGroup1 );
		
		Group rect = new Group( Mat4.rotateY( 10 ).mult( Mat4.rotateZ( 35 ) ), sphereGroup2, sphereGroup3, sphereGroup4,
				zylinder1, zy2, zy3 );
		
		return new Group( rec1, rect, sphereGroup5 );
	}
	
	private Group createSphereGroup( double radius, Mat4 transformation, Mat4... mat4s )
	{
		List<Group> smallGroups = new ArrayList<>();
		
		double x;
		double z;
		double radian;
		double radi2 = radius + ( radius * 0.01 );
		double smallRadi = radius * .09;
		for ( double i = 0; i < 360.; i += 5 )
		{
			radian = Math.toRadians( i );
			x = radi2 * Math.cos( radian );
			z = radi2 * Math.sin( radian );
			smallGroups.add( new Group( Mat4.translate( x, 0, z ),
					new Sphere( smallRadi, new ReflectiveMaterial( new ConstantTexture( new Vec3( 0, 1, 1 ) ) ) ) ) );
		}
		Group sphereRing = new Group( transformation, smallGroups.stream().toArray( Group[]::new ) );
		
		Group result = new Group( sphereRing,
				new Sphere( radius - 1,
						new ReflectiveMaterial( new ConstantTexture( new Vec3( 0, 0, 0 ) ),
								new ConstantTexture( new Vec3( .5, .5, .5 ) ) ) ),
				new Sphere( radius, new GlassMaterial( Density.GLAS ) ) );
		// new Sphere( radius,
		// new ReflectiveMaterial( new ConstantTexture( new Vec3( 0, 0, 0 ) ),
		// new ConstantTexture( new Vec3( .6, .6, .6 ) ) ) )
		
		Arrays.asList( mat4s ).forEach( mat -> result.addShape( new Group( mat, sphereRing ) ) );
		
		return result;
	}
	private Group createSphereGroup( double radius, Mat4 transformation )
	{
		return createSphereGroup( radius, transformation, new Mat4[0] );
	}
	
	public static void main( String[] args )
	{
		// new RenderingTest1();
	}
	
	/*
	 * texture1: http://hamtana.com/images/img5/black-wood-floor-texture-design-inspiration-2.jpg
	 * texture2: http://www.holzkellner.de/typo3temp/_processed_/csm_Gartenholz_TS2015_MS_28_4a90ac129a.jpg
	 * texture3: http://hamtana.com/dark-stone-floor-texture-inspiration-decorating-3-5852-design.html
	 * texture4: http://www.parkett-wohnwelt.de/gfx/items2013/medium/000/005/465/1138089.jpg
	 * texture5: http://more-sky.com/data/out/12/IMG_512254.jpg
	 * texture6:
	 * http://2.bp.blogspot.com/-hxfNeT48rAI/Tfy2SOUlIYI/AAAAAAAACJE/kg1nN2skTlw/s1600/Cool+Texture+Wallpaper+-+www.
	 * OriginalWalls.Blogspot.Com++%25282%2529.jpg
	 * texture7: http://www.johnsusek.com/projects/textures/lawdogs/hatch_01_dark.jpg
	 */
	
}
