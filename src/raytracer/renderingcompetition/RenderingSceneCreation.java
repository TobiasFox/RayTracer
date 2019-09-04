
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
import raytracer.imageCreator.shape.withMaterial.HouseOfCircles;
import raytracer.imageCreator.shape.withMaterial.Zylinder;
import raytracer.imageCreator.shape.withMaterial.group.Group;
import raytracer.imageCreator.shape.withMaterial.light.Light;
import raytracer.imageCreator.shape.withMaterial.light.PointLight;
import raytracer.imageCreator.shape.withMaterial.material.BackgroundInterpolation;
import raytracer.imageCreator.shape.withMaterial.material.BackgroundMaterial;
import raytracer.imageCreator.shape.withMaterial.material.Density;
import raytracer.imageCreator.shape.withMaterial.material.GlassMaterial;
import raytracer.imageCreator.shape.withMaterial.material.InterpolateMaterial;
import raytracer.imageCreator.shape.withMaterial.material.Material;
import raytracer.imageCreator.shape.withMaterial.material.PerfectDiffuseMaterial;
import raytracer.imageCreator.shape.withMaterial.material.ReflectiveMaterial;
import raytracer.imageCreator.shape.withMaterial.primitives.Rectangle2;
import raytracer.imageCreator.shape.withMaterial.primitives.Sphere;
import raytracer.imageCreator.shape.withMaterial.texture.ConstantTexture;
import raytracer.imageCreator.shape.withMaterial.texture.PictureTexture;

public class RenderingSceneCreation
{
	
	// 3,449
	// 43,339
	private static final String NAME = "doc/test/c01.png";
	private static final int WIDTH = 480;
	private static final int HEIGHT = 270;
	private static final int ABTASTRATE = 2;
	private static final int DEPTH = 2;
	private static final String path = "src/raytracer/textures/";
	private HouseOfCircles house1;
	private Group[] specialSpheres = new Group[3];
	private ArrayList<Light> lightSources = new ArrayList<>();
	
	public RenderingSceneCreation()
	{
	}
	
	RenderingSceneCreation( boolean b )
	{
		ImageCreator creator = new ImageCreator( WIDTH, HEIGHT, ABTASTRATE, ImageQualities.Bit16,
				PixelColorCalculations.MonteCarloRaytrace );
		
		try
		{
			creator.setWorld( new World( createScene(), createBackground(), createLightSources() ) );
			// optimale position bis jetzt
			// creator.setTransformation( Mat4.translate( 0, 0, 160 ).mult( Mat4.rotateX( 29.5 ) ) );
			// creator.setTransformation(
			// Mat4.translate( 400, 200, 160 ).mult( Mat4.rotateX( 29.5 ).mult( Mat4.rotateY( 90 ) ) ) );
			
			// creator.setTransformation( Mat4.translate( 50, 0, 180 ).mult( Mat4.rotateX( 26 ) ) );
			
			// creator.setTransformation( Mat4.translate( 150, 0, 280 ).mult( Mat4.rotateX( 26 ) ) );
			
			// creator.setTransformation( Mat4.translate( 1000, 0, 160 ).mult( Mat4.rotateX( 26 ) ) );
			// creator.setTransformation( Mat4.translate( 2300, 0, 250 ).mult( Mat4.rotateX( 26 ) ) );
			// creator.setTransformation( Mat4.translate( 2000, 0, 160 ).mult( Mat4.rotateX( 26 ) ) );
			creator.setTransformation( Mat4.translate( 600, 0, 200 ) );
			// creator.setTransformation( Mat4.translate( 0, 0, 400 ) );
			
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
			Runtime runtime = Runtime.getRuntime();
			long memory = runtime.totalMemory() - runtime.freeMemory();
			System.out.println( "Used memory is megabytes: " + bytesToMegabytes( memory ) );
			
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
		Runtime runtime = Runtime.getRuntime();
		long memory = runtime.totalMemory() - runtime.freeMemory();
		System.out.println( "Used memory is megabytes: " + bytesToMegabytes( memory ) );
		runtime.gc();
		memory = runtime.totalMemory() - runtime.freeMemory();
		System.out.println( "Used memory is megabytes: " + bytesToMegabytes( memory ) );
	}
	
	private static final long MEGABYTE = 1024L * 1024L;
	
	public static long bytesToMegabytes( long bytes )
	{
		return bytes / MEGABYTE;
	}
	
	public World createWorld()
	{
		return new World( createScene(), createBackground(), createLightSources() );
	}
	
	private Background createBackground()
	{
		// try
		// {
		// return new Background(
		// new BackgroundMaterial( new TransformationTexture( new PictureTexture( path + "sky.jpg", false ),
		// Mat4.rotateZ( 180 ).mult( Mat4.translate( 200, 200, 200 ) ) ) ) );
		// } catch( IOException exc )
		// {
		// exc.printStackTrace();
		// }
		Vec3 colorOne = new Vec3( 10, 20, 50 ).toRGB();
		Vec3 colorTwo = new Vec3( 15, 27, 30 ).toRGB();
		// Background bg = new Background( new BackgroundInterpolation( colorOne, colorTwo ) );
		Background bg = null;
		try
		{
			bg = new Background( new InterpolateMaterial( new BackgroundInterpolation( colorOne, colorTwo ),
					new BackgroundMaterial( new PictureTexture( path + "sky2.jpg" ) ), 1 ) );
		} catch( IOException exc )
		{
			exc.printStackTrace();
		}
		return bg;
		// return new Background();
	}
	
	private List<Light> createLightSources()
	{
		lightSources.add( new PointLight( new Vec3( 1250, -200, 200 ), new Vec3( 100000, 100000, 100000 ) ) );
		
		// lightSources.add( new PointLight( new Vec3( 0, -200, 700 ), new Vec3( 0, 30000, 45000 ) ) );
		
		// lightSources.add( new PointLight( new Vec3( 1250, -200, 0 ), new Vec3( 100000, 100000, 100000 ) ) );
		// lightSources.add( new PointLight( new Vec3( 1250, 0, -100 ), new Vec3( 0, 25000, 37500 ) ) );
		// lightSources.add( new PointLight( new Vec3( 1250, 0, -100 ), new Vec3( 0, 12500, 18750 ) ) );
		// lightSources.add( new PointLight( new Vec3( 1250, 0, -500 ), new Vec3( 0, 12500, 18750 ) ) );
		return lightSources;
	}
	
	private Group createScene()
	{
		
		Group rec1 = null;
		try
		{
			rec1 = new Group( Mat4.translate( 0, 300, 0 ), new Rectangle2( 1600, 1200,
					new PerfectDiffuseMaterial( new PictureTexture( path + "texture1.jpg", false ) ) ) );
			// rec1 = new Group( Mat4.rotateZ( 90 ).mult( Mat4.rotateY( 10 ) ), new Rectangle2( 1600, 1200,
			// new PerfectDiffuseMaterial( new PictureTexture( path + "texture1.jpg", false ) ) ) );
			
		} catch( IOException exc )
		{
			exc.printStackTrace();
		}
		
		Group firstSceneCut = createFirstScene();
		createHouse();
		
		Group rec2 = new Group( Mat4.translate( 2600, 0, 0 ), rec1 );
		Group secondSceneCut = createSecondScene();
		
		return new Group( rec1, firstSceneCut, house1, rec2, secondSceneCut );
	}
	
	private Group createSecondScene()
	{
		Group specialSphereGroup = createSphereGroup( new Vec3( 239, 74, 37 ).toRGB(), 35, Mat4.identity,
				Mat4.rotateZ( 35 ), Mat4.rotateZ( 70 ), Mat4.rotateZ( 105 ), Mat4.rotateZ( 140 ) );
		
		Group greenSphere = createSphereGroup( new Vec3( 57, 255, 20 ).toRGB(), 35, Mat4.identity );
		
		// 1.viertel
		Group sphereA = new Group( Mat4.translate( 1900, 70, 50 ).mult( Mat4.rotateX( 90 ) ), greenSphere );
		Group sphereB = new Group( Mat4.translate( 2000, 350, 50 ).mult( Mat4.rotateX( 90 ) ), greenSphere );
		Group sphereC = new Group( Mat4.translate( 2100, 150, 50 ).mult( Mat4.rotateX( 90 ) ), greenSphere );
		
		// 2.viertel
		// Group sphereD = new Group( Mat4.translate( 2275, 275, 50 ).mult( Mat4.rotateX( 90 ) ), greenSphere );
		Group sphereE = new Group( Mat4.translate( 2550, 450, 50 ).mult( Mat4.rotateX( 90 ) ), greenSphere );
		specialSpheres[0] = new Group( Mat4.translate( 2275, 120, 50 ).mult( Mat4.rotateX( 90 ) ), specialSphereGroup );
		specialSpheres[1] = new Group( Mat4.translate( 2420, 275, 50 ).mult( Mat4.rotateX( 90 ) ), specialSphereGroup );
		
		// 3.viertel
		specialSpheres[2] = new Group( Mat4.translate( 2650, 80, 50 ).mult( Mat4.rotateX( 90 ) ), specialSphereGroup );
		Group sphereF = new Group( Mat4.translate( 2850, 500, 50 ).mult( Mat4.rotateX( 90 ) ), greenSphere );
		Group sphereG = new Group( Mat4.translate( 2825, 150, 50 ).mult( Mat4.rotateX( 90 ) ), greenSphere );
		Group sphereH = new Group( Mat4.translate( 3000, 250, 50 ).mult( Mat4.rotateX( 90 ) ), greenSphere );
		
		// 4.viertel
		Group sphereI = new Group( Mat4.translate( 3200, 400, 50 ).mult( Mat4.rotateX( 90 ) ), greenSphere );
		Group sphereJ = new Group( Mat4.translate( 3300, 120, 50 ).mult( Mat4.rotateX( 90 ) ), greenSphere );
		
		// materials
		Vec3 turquoise = new Vec3( 0, 1, 1 );
		Material blueLightningReflectiveMaterial = new ReflectiveMaterial( new ConstantTexture( turquoise ) );
		
		// zylinder objects
		Zylinder zy1 = new Zylinder( 4, 75, blueLightningReflectiveMaterial );
		Zylinder zy2 = new Zylinder( 4, 150, blueLightningReflectiveMaterial );
		
		// groups with zylinders
		Group zylinder1 = new Group( Mat4.translate( 2000, 120, 50 ).mult( Mat4.rotateZ( -67.5 ) ), zy1 );
		
		Group zylinder2 = new Group( Mat4.translate( 2260, 212.5, 50 ).mult( Mat4.rotateZ( -67.5 ) ), zy2 );
		
		Group zylinder3 = new Group( Mat4.translate( 2210, 312.5, 50 ).mult( Mat4.rotateZ( -102 ) ),
				new Zylinder( 4, 180, blueLightningReflectiveMaterial ) );
		
		Group zylinder4 = new Group( Mat4.translate( 2347.5, 197.5, 50 ).mult( Mat4.rotateZ( -50 ) ), zy1 );
		
		Group zylinder5 = new Group( Mat4.translate( 2737.5, 260, 50 ).mult( Mat4.rotateZ( -72.75 ) ),
				new Zylinder( 4, 500, new ReflectiveMaterial( new ConstantTexture( new Vec3( 1, 0, 0 ) ) ) ) );
		
		Group zylinder6 = new Group( Mat4.translate( 2462.5, 100, 50 ).mult( Mat4.rotateZ( -98 ) ), zy2 );
		
		Group zylinder7 = new Group( Mat4.translate( 2535, 177.5, 50 ).mult( Mat4.rotateZ( 47 ) ),
				new Zylinder( 4, 125, blueLightningReflectiveMaterial ) );
		
		Group zylinder8 = new Group( Mat4.translate( 2600, 265, 50 ).mult( Mat4.rotateZ( 16 ) ), new Zylinder( 4, 150,
				new ReflectiveMaterial( new ConstantTexture( new Vec3( 233, 254, 20 ).toRGB() ) ) ) );
		
		Group zylinder9 = new Group( Mat4.translate( 2485, 362.5, 50 ).mult( Mat4.rotateZ( -36 ) ), zy1 );
		
		Group zylinder10 = new Group( Mat4.translate( 2700, 475, 50 ).mult( Mat4.rotateZ( -83 ) ), zy2 );
		
		Group zylinder11 = new Group( Mat4.translate( 2750, 290, 50 ).mult( Mat4.rotateZ( -25 ) ),
				new Zylinder( 4, 210, blueLightningReflectiveMaterial ) );
		
		Group zylinder12 = new Group( Mat4.translate( 3025, 450, 50 ).mult( Mat4.rotateZ( -107 ) ), zy2 );
		
		Group zylinder13 = new Group( Mat4.translate( 2912.5, 200, 50 ).mult( Mat4.rotateZ( -59 ) ), zy1 );
		
		Group zylinder14 = new Group( Mat4.translate( 3100, 325, 50 ).mult( Mat4.rotateZ( -55 ) ),
				new Zylinder( 4, 100, blueLightningReflectiveMaterial ) );
		
		Group zylinder15 = new Group( Mat4.translate( 3250, 260, 50 ).mult( Mat4.rotateZ( 21 ) ), zy2 );
		
		return new Group( sphereA, sphereB, sphereC, sphereE, specialSpheres[0], specialSpheres[1], specialSpheres[2],
				sphereF, sphereG, sphereH, sphereI, sphereJ, zylinder1, zylinder2, zylinder3, zylinder4, zylinder5,
				zylinder6, zylinder7, zylinder8, zylinder9, zylinder10, zylinder11, zylinder12, zylinder13, zylinder14,
				zylinder15 );
	}
	
	private Group createFirstScene()
	{
		Group sphereGroup1 = createSphereGroup( new Vec3( 0, 1, 1 ), 35, Mat4.identity );
		Group sphereGroup2 = new Group( Mat4.translate( -70, 80, 50 ).mult( Mat4.rotateX( -127 ) ), sphereGroup1 );
		Group sphereGroup3 = new Group( Mat4.translate( 160, 80, 50 ).mult( Mat4.rotateX( -127 ) ), sphereGroup1 );
		Group sphereGroup4 = new Group( Mat4.translate( 110, 230, 30 ).mult( Mat4.rotateX( -110 ) ), sphereGroup1 );
		Group sphereGroup6 = new Group( Mat4.translate( 350, 100, 30 ).mult( Mat4.rotateX( -110 ) ), sphereGroup1 );
		Group sphereGroup7 = new Group( Mat4.translate( 200, 400, 30 ).mult( Mat4.rotateX( -110 ) ), sphereGroup1 );
		Group sphereGroup8 = new Group( Mat4.translate( 600, 400, 30 ).mult( Mat4.rotateX( -110 ) ), sphereGroup1 );
		
		Group zylinder1 = new Group( Mat4.rotateZ( 90 ).mult( Mat4.translate( 80, -30, 50 ) ),
				new Zylinder( 4, 100, new ReflectiveMaterial( new ConstantTexture( new Vec3( 0, 1, 1 ) ) ) ) );
		
		Group zylinder2 = new Group( Mat4.rotateZ( 90 ).mult( Mat4.translate( 80, -30, 48 ) ),
				new Zylinder( 4, 60, new ReflectiveMaterial( new ConstantTexture( new Vec3( 0, 1, 1 ) ) ) ) );
		
		Group zylinder3 = new Group( Mat4.rotateZ( 105 ).mult( Mat4.translate( 230, 180, 45 ) ),
				new Zylinder( 4, 200, new ReflectiveMaterial( new ConstantTexture( new Vec3( 0, 1, 1 ) ) ) ) );
		
		Group zylinder4 = new Group( Mat4.translate( 135, 250, 35 ).mult( Mat4.rotateZ( -20 ) ),
				new Zylinder( 4, 130, new ReflectiveMaterial( new ConstantTexture( new Vec3( 0, 1, 1 ) ) ) ) );
		
		Group zylinder5 = new Group( Mat4.translate( 420, 350, 28 ).mult( Mat4.rotateZ( -70 ) ),
				new Zylinder( 4, 160, new ReflectiveMaterial( new ConstantTexture( new Vec3( 0, 1, 1 ) ) ) ) );
		
		Group zylinder6 = new Group( Mat4.translate( 310, 185, 45 ).mult( Mat4.rotateZ( 14 ) ),
				new Zylinder( 4, 80, new ReflectiveMaterial( new ConstantTexture( new Vec3( 0, 1, 1 ) ) ) ) );
		
		Group zy2 = new Group( Mat4.translate( 270, 0, 15 ).mult( Mat4.rotateY( -10 ) ), zylinder1 );
		
		Group zy3 = new Group( Mat4.translate( 215, 135, 5 ).mult( Mat4.rotateZ( 100 ).mult( Mat4.rotateY( 10 ) ) ),
				zylinder2 );
		
		Group sphereGroup5 = new Group(
				Mat4.translate( 270, 285, 35 ).mult( Mat4.rotateX( -110 ).mult( Mat4.rotateZ( -10 ) ) ), sphereGroup1 );
		
		Group triangleStructure = new Group( Mat4.rotateY( 10 ).mult( Mat4.rotateZ( 35 ) ), sphereGroup2, sphereGroup3,
				sphereGroup4, zylinder1, zy2, zy3 );
		
		return new Group( triangleStructure, sphereGroup5, sphereGroup6, sphereGroup7, sphereGroup8, zylinder3,
				zylinder4, zylinder5, zylinder6 );
	}
	
	private void createHouse()
	{
		Vec3 dimension = new Vec3( 112.5, 150, 50 );
		Vec3 start = new Vec3( 0, 0, 0 );
		HouseOfCircles house1 = new HouseOfCircles( dimension, 4, 37.5, start, 3.5, new Vec3( 0, 1, 0 ),
				new Vec3( 0, 0, 1 ) );
		house1.setLimitationLeft( .1 );
		house1.setLimitationRight( .9 );
		house1.createRadiantHouse();
		house1.setTransformation(
				Mat4.translate( 1300, 0, -70 ).mult( Mat4.rotateY( 90 ) ).mult( Mat4.rotateY( 57 ) ) );
		this.house1 = house1;
		// house1.setTransformation(
		// Mat4.translate( 1200, 0, -50 ).mult( Mat4.rotateY( 90 ) ).mult( Mat4.rotateY( 57 ) ) );
	}
	
	private Group createSphereGroup( Vec3 color, double radius, Mat4 transformation, Mat4... mat4s )
	{
		// List<Group> smallGroups = new ArrayList<>();
		Group[] smallGroups = new Group[72];
		
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
			// smallGroups.add( new Group( Mat4.translate( x, 0, z ),
			// new Sphere( smallRadi, new ReflectiveMaterial( new ConstantTexture( color ) ) ) ) );
			smallGroups[(int) ( i / 5. )] = new Group( Mat4.translate( x, 0, z ),
					new Sphere( smallRadi, new ReflectiveMaterial( new ConstantTexture( color ) ) ) );
		}
		// Group sphereRing = new Group( transformation, smallGroups.stream().toArray( Group[]::new ) );
		Group sphereRing = new Group( transformation, smallGroups );
		
		Group result = new Group( sphereRing,
				new Sphere( radius - .1,
						new ReflectiveMaterial( new ConstantTexture( new Vec3( 0, 0, 0 ) ),
								new ConstantTexture( new Vec3( .5, .5, .5 ) ) ) ),
				new Sphere( radius, new GlassMaterial( Density.GLAS ) ) );
		// new Sphere( radius,
		// new ReflectiveMaterial( new ConstantTexture( new Vec3( 0, 0, 0 ) ),
		// new ConstantTexture( new Vec3( .6, .6, .6 ) ) ) )
		
		Arrays.asList( mat4s ).forEach( mat -> result.addShape( new Group( mat, sphereRing ) ) );
		
		return result;
	}
	
	private Group createSphereGroup( Vec3 color, double radius, Mat4 transformation )
	{
		return createSphereGroup( color, radius, transformation, new Mat4[0] );
	}
	
	public static void main( String[] args )
	{
		new RenderingSceneCreation( true );
	}
	
	public HouseOfCircles getHouseGroup()
	{
		return house1;
	}
	
	public Group[] getSpecialSpheres()
	{
		return specialSpheres;
	}
	
	public ArrayList<Light> getLightSources()
	{
		return lightSources;
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
	 * grass: http://www.allcgtextures.com/d/3335-1/Grass004.JPG
	 * sky2.jpg http://montco.happeningmag.com/wp-content/uploads/2013/08/night-sky-hd-wallpaper-2.jpg
	 */
	
}
