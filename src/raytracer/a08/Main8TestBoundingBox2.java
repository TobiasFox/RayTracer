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
import raytracer.imageCreator.shape.withMaterial.Background;
import raytracer.imageCreator.shape.withMaterial.Lamp;
import raytracer.imageCreator.shape.withMaterial.Quader;
import raytracer.imageCreator.shape.withMaterial.Zylinder;
import raytracer.imageCreator.shape.withMaterial.group.Group;
import raytracer.imageCreator.shape.withMaterial.light.Light;
import raytracer.imageCreator.shape.withMaterial.material.BackgroundMaterial;
import raytracer.imageCreator.shape.withMaterial.material.Density;
import raytracer.imageCreator.shape.withMaterial.material.GlassMaterial;
import raytracer.imageCreator.shape.withMaterial.material.PerfectDiffuseMaterial;
import raytracer.imageCreator.shape.withMaterial.material.ReflectiveMaterial;
import raytracer.imageCreator.shape.withMaterial.primitives.Rectangle;
import raytracer.imageCreator.shape.withMaterial.primitives.Sphere;
import raytracer.imageCreator.shape.withMaterial.texture.ConstantTexture;

/**
 * @author Tobias Fox
 *
 */
public class Main8TestBoundingBox2
{
	
	private String name = "doc/test/a08bound-";
	private static final int WIDTH = 480;
	private static final int HEIGHT = 270;
	private static final int ABTASTRATE = 20;
	private static final int DEPTH = 10;
	
	private Main8TestBoundingBox2()
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
		
		for ( int i = 1; i < 4; i++ )
		{
			try
			{
				if ( i != 1 )
				{
					creator.resetTransformation();
				}
				creator.setTransformation( setTransformation( i ) );
				creator.startWithThreadPool();
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
				// in front of glassphere
				return Mat4.translate( -2.5, 3, 9 );
			case 2:
				// top of reflective sphere, look down
				return Mat4.translate( -2.5, 3, 9 )
						.mult( Mat4.translate( 0, 2.5, -17 ).mult( Mat4.rotateY( -180 ).mult( Mat4.rotateX( -30 ) ) ) );
			case 3:
				// side of scene
				return Mat4.translate( -2.5, 3, 9 ).mult( Mat4.translate( -10, 0, 0 ).mult( Mat4.rotateY( -90 ) ) );
			default:
				return Mat4.identity;
		}
	}
	
	private World createWorld()
	{
		Group scene = createScene();
		List<Light> lightsources = createLightSources();
		Background background = setBackground();
		return new World( scene, background, lightsources );
	}
	
	private Background setBackground()
	{
		Background bg = new Background( new BackgroundMaterial() );
		return bg;
	}
	
	private List<Light> createLightSources()
	{
		ArrayList<Light> lightSources = new ArrayList<>();
		// lightSources.add( new PointLight( new Vec3( 5, 5, -10 ), new Vec3( 1, 1, 1 ) ) );
		// lightSources.add( new PointLight( new Vec3( -5, 5, -10 ), new Vec3( 1, 0, 0 ) ) );
		return lightSources;
	}
	
	private Group createScene()
	{
		Rectangle rectDown = new Rectangle( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( .8, .8, .8 ) ) ) );
		Rectangle rectUp = new Rectangle( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 1, 1, 1 ) ) ) );
		Group planeDown = new Group( Mat4.translate( -10000, 0, 10000 ).mult( Mat4.rotateX( -90 ) ), rectDown );
		Group planeUp = new Group( Mat4.translate( -10000, 13, 10000 ).mult( Mat4.rotateX( -90 ) ), rectUp );
		Group planeRight = new Group( Mat4.rotateY( -90 ).mult( Mat4.translate( -10000, 0, -15 ) ),
				new Rectangle( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, new ReflectiveMaterial() ) );
		Group planeLeft = new Group( Mat4.rotateY( -90 ).mult( Mat4.translate( -10000, 0, 15 ) ),
				new Rectangle( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, new ReflectiveMaterial() ) );
		
		Group planes = new Group( planeDown, planeUp, planeRight, planeLeft );
		
		Vec3 colorFront = new Vec3( .95, .95, .95 );
		Group lamp1 = new Group( Mat4.translate( 0, 0, 0 ),
				new Lamp( colorFront, new ReflectiveMaterial( new ConstantTexture( new Vec3( 1, 1, 0 ) ),
						new ConstantTexture( new Vec3( 1, 1, 0 ) ) ), new Vec3( 0, 0, 1 ) ) );
		
		// Vec3 colorVec = new Vec3( .85, .85, .85 );
		// Group earth = new Group( Mat4.translate( 0, -501, 0 ),
		// new Sphere( 497, new PerfectDiffuseMaterial( colorVec ) ) );
		
		Group lamp2 = new Group( Mat4.translate( -10, 0, 0 ),
				new Lamp( colorFront, new ReflectiveMaterial( new ConstantTexture( new Vec3( 0, 1, 0 ) ),
						new ConstantTexture( new Vec3( 0, 1, 0 ) ) ), new Vec3( 0, 0, 1 ) ) );
		
		Group lamp3 = new Group( Mat4.translate( -10, 0, -10 ),
				new Lamp( colorFront, new ReflectiveMaterial( new ConstantTexture( new Vec3( 0, 1, 1 ) ),
						new ConstantTexture( new Vec3( 0, 1, 1 ) ) ), new Vec3( 0, 0, 1 ) ) );
		
		Group lamp4 = new Group( Mat4.translate( 0, 0, -10 ),
				new Lamp( colorFront, new ReflectiveMaterial( new ConstantTexture( new Vec3( 1, 1, 1 ) ),
						new ConstantTexture( new Vec3( 1, 1, 1 ) ) ), new Vec3( 0, 0, 1 ) ) );
		
		// Group lamp5 = new Group( Mat4.translate( 0, 0, 5 ), new Lamp( new Vec3( 1, 1, 1 ),
		// new PerfectReflectiveMaterial( new Vec3( 1, 1, 1 ), new Vec3( 1, 1, 1 ) ), new Vec3( 0, 0, 1 ) ) );
		
		Quader q = new Quader( new Vec3( 2, 2, 2 ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 1, 1 ) ) ) );
		Group s = new Group( Mat4.translate( 1, 3.5, -1 ), new Sphere( 1.5, new ReflectiveMaterial() ) );
		Group quaderGroup = new Group( Mat4.translate( -4, 0, -5 ), q, s );
		
		Sphere glasSphere = new Sphere( 1.5, new GlassMaterial( Density.GLAS ) );
		double height = 1;
		Zylinder zy = new Zylinder( new Vec3( 0, 0, 0 ), 0.4, height,
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 205, 193, 197 ).toRGB() ) ) );
		Group zyGr = new Group( Mat4.translate( 0, -( height + glasSphere.radius ), 0 ), zy );
		Group glasGroup = new Group( Mat4.translate( -2.5, 3, 6 ), glasSphere, zyGr );
		
		Group lamps = new Group( Mat4.translate( 0, 6, 0 ), lamp1, lamp2, lamp3, lamp4 );
		
		Group lampWithQuader1 = new Group( lamps, quaderGroup );
		Group lampWithQuader2 = new Group( Mat4.translate( 0, 0, 27 ), lampWithQuader1 );
		return new Group( planes, glasGroup, lampWithQuader1, lampWithQuader2 );
	}
	
	public static void main( String[] a )
	{
		new Main8TestBoundingBox2();
	}
}
