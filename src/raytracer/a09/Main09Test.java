/**
 * 
 */

package raytracer.a09;

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
import raytracer.imageCreator.shape.withMaterial.Quader;
import raytracer.imageCreator.shape.withMaterial.group.Group;
import raytracer.imageCreator.shape.withMaterial.light.Light;
import raytracer.imageCreator.shape.withMaterial.material.BackgroundInterpolation;
import raytracer.imageCreator.shape.withMaterial.material.BackgroundMaterial;
import raytracer.imageCreator.shape.withMaterial.material.Density;
import raytracer.imageCreator.shape.withMaterial.material.GlassMaterial;
import raytracer.imageCreator.shape.withMaterial.material.Material;
import raytracer.imageCreator.shape.withMaterial.material.PerfectDiffuseMaterial;
import raytracer.imageCreator.shape.withMaterial.material.ReflectiveMaterial;
import raytracer.imageCreator.shape.withMaterial.primitives.Rectangle2;
import raytracer.imageCreator.shape.withMaterial.primitives.Sphere;
import raytracer.imageCreator.shape.withMaterial.texture.ConstantTexture;
import raytracer.imageCreator.shape.withMaterial.texture.PictureTexture;
import raytracer.imageCreator.shape.withMaterial.texture.Texture;

/**
 * @author Tobias Fox
 *
 */
public class Main09Test
{
	
	private String name = "doc/test/a09-";
	private static final int WIDTH = 500;
	private static final int HEIGHT = 250;
	private static final int ABTASTRATE = 2;
	private static final int DEPTH = 2;
	
	private Main09Test() throws IOException
	{
		ImageCreator creator = new ImageCreator( WIDTH, HEIGHT, ABTASTRATE, ImageQualities.Bit16,
				PixelColorCalculations.MonteCarloRaytrace );
		try
		{
			creator.setWorld( new World( createScene2(), createBackground(), createLightSources() ) );
			creator.setRecursiveDepth( DEPTH );
		} catch( FeatureNotUsingException e1 )
		{
			System.out.println( "error with scene or recursive" );
			e1.printStackTrace();
		}
		try
		{
			// creator.setTransformation(
			// Mat4.translate( 8, 5.2, -7 ).mult( Mat4.rotateY( -180 ).mult( Mat4.rotateX( -30 ) ) ) );
			//
			// von der anderen seite -->t1
			// creator.setTransformation(
			// Mat4.translate( 8, 6, -9 ).mult( Mat4.rotateY( -180 ).mult( Mat4.rotateX( -20 ) ) ) );
			
			// oben --> t2
			// creator.setTransformation( Mat4.translate( 8, 14, -2 ).mult( Mat4.rotateX( -90 ) ) );
			
			// creator.setTransformation( Mat4.translate( 8, 6, 5 ) );
			
			// -->t3
			creator.setTransformation( Mat4.translate( 14, 5.5, -2 ).mult( Mat4.rotateY( 90 ) ) );
			// creator.setTransformation( Mat4.translate( 8, 6, 5 ).mult( Mat4.rotateX( -30 ) ) );
			// creator.setTransformation( Mat4.translate( 0, 1, 0 ).mult( Mat4.rotateX( -90 ) ) );
			// creator.setTransformation( Mat4.translate( 8, 3, 8 ) );
			// creator.setTransformation( Mat4.translate( 0, 0, 8 ) );
			
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
	
	private Background createBackground()
	{
		return new Background( new BackgroundInterpolation() );
	}
	
	private List<Light> createLightSources()
	{
		ArrayList<Light> lightSources = new ArrayList<>();
		return lightSources;
	}
	
	private Group createScene2() throws IOException
	{
		Material wood = new PerfectDiffuseMaterial( new PictureTexture( "doc/test/wood.jpg", false ) );
		
		// Texture bsMat = new TransformationTexture( new PictureTexture( "doc/test/sky1.jpg", false ), Mat4.identity );
		Texture bsMat = new PictureTexture( "doc/test/sky.jpg", false );
		// Texture bsMat = new ConstantTexture( new Vec3( 217, 217, 217 ).toRGB() );
		
		Shape bg = new Background( new BackgroundMaterial( bsMat ) );
		
		Group chair = createChair( wood );
		Group table = createTable( new PerfectDiffuseMaterial( new PictureTexture( "doc/test/marmor.jpg", false ) ) );
		table.addTransformation( Mat4.translate( 6, 0, 0 ) );
		Group chair2 = createChair( wood );
		chair2.addTransformation( Mat4.rotateY( 180 ).mult( Mat4.translate( -17, 0, 3 ) ) );
		
		Group glass = new Group( Mat4.translate( 8.5, 4.52, -3.5 ),
				new Sphere( .7, new GlassMaterial( Density.GLAS ) ) );
		Group sp1 = new Group( Mat4.translate( 8.5, 4.52, -1.5 ),
				new Sphere( .7, new ReflectiveMaterial( new ConstantTexture( new Vec3( 1, 0, 0 ) ),
						new ConstantTexture( new Vec3( 0, 1, 0 ) ) ) ) );
		
		Group gr = new Group( Mat4.rotateX( 90 ), new Rectangle2( 80, 80, wood ) );
		
		Group sp2 = new Group( Mat4.translate( 1.5, 5.02, -2 ),
				new Sphere( 1, new ReflectiveMaterial( new PictureTexture( "doc/test/world.jpg", false ),
						new ConstantTexture( new Vec3( 1, 0, 0 ) ) ) ) );
		
		Group sp3 = new Group( Mat4.translate( 14, 5.02, -2 ), new Sphere( 1, new ReflectiveMaterial( 1,
				new PictureTexture( "doc/test/world.jpg", false ), new ConstantTexture( new Vec3( 0, 1, 0 ) ) ) ) );
		
		Group chairs = new Group( chair, chair2 );
		return new Group( bg, chairs, table, glass, gr, sp1, sp2, sp3 );
	}
	
	private Group createTable( Material material )
	{
		Quader quader = new Quader( new Vec3( 5, 1, 5 ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 160, 82, 45 ).toRGB() ) ) );
		Group gr = new Group( Mat4.translate( 0, 3, 0 ), quader );
		
		Vec3 legDimensions = new Vec3( .3, 3, .3 );
		Group tableLegs = createLegs( 5, 5, legDimensions, new Vec3( 139, 69, 19 ).toRGB() );
		
		Group materialTop = new Group( Mat4.translate( 2.5, 4.001, -2.5 ).mult( Mat4.rotateX( -90 ) ),
				new Rectangle2( 5, 5, material ) );
		
		return new Group( gr, tableLegs, materialTop );
	}
	
	private Group createChair( Material material )
	{
		Quader chair = new Quader( new Vec3( 3, 1, 3 ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 160, 82, 45 ).toRGB() ) ) );
		Group body = new Group( Mat4.translate( 0, 2.5, 0 ), chair );
		
		Vec3 legDimensions = new Vec3( .3, 2.5, .3 );
		Group chairLegs = createLegs( 3, 3, legDimensions, new Vec3( 139, 69, 19 ).toRGB() );
		
		Quader back = new Quader( new Vec3( .2, 5, 3 ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 139, 69, 19 ).toRGB() ) ) );
		Group backGr = new Group( Mat4.translate( 0, 3.5, 0 ), back );
		
		Group materialTop = new Group( Mat4.translate( 1.5, 3.5001, -1.5 ).mult( Mat4.rotateX( -90 ) ),
				new Rectangle2( 2.7, 3, material ) );
		
		Group backMat = new Group( Mat4.translate( .201, 6.001, -1.5 ).mult( Mat4.rotateY( -90 ) ),
				new Rectangle2( 3, 5, material ) );
		
		return new Group( body, chairLegs, backGr, materialTop, backMat );
	}
	
	private Group createLegs( double distanceWidth, double distanceHeight, Vec3 legDimensions, Vec3 color )
	{
		Quader tableLeg1 = new Quader( legDimensions, new PerfectDiffuseMaterial( new ConstantTexture( color ) ) );
		double distanceW = distanceWidth - legDimensions.x;
		double distanceH = distanceHeight - legDimensions.x;
		
		Group tableLeg2 = new Group( Mat4.translate( 0, 0, -distanceH ), tableLeg1 );
		Group tableLeg3 = new Group( Mat4.translate( distanceW, 0, 0 ), tableLeg1 );
		Group tableLeg4 = new Group( Mat4.translate( distanceW, 0, -distanceH ), tableLeg1 );
		return new Group( tableLeg1, tableLeg2, tableLeg3, tableLeg4 );
	}
	
	public static void main( String[] args ) throws IOException
	{
		new Main09Test();
	}
	
	private Shape createTestScene() throws IOException
	{
		Shape bg = new Background( new BackgroundMaterial() );
		// Group s = new Group( new Sphere( 1, new ReflectiveMaterial( new PictureTexture( "doc/test/world.jpg" ),
		// new ConstantTexture( new Vec3( 1, 0, 0 ) ) ) ) );
		// Group s = new Group( Mat4.translate( -9, -4.5, 0 ), new Rectangle( 18, 9, new PerfectDiffuseMaterial(
		// new PictureTexture( "doc/test/world.jpg", Mat4.scale( new Vec3( -1, 0, 0 ) ) ) ) ) );
		Group s = new Group( Mat4.rotateX( 270 ),
				new Rectangle2( 1, 1.5, new PerfectDiffuseMaterial( new PictureTexture( "doc/test/world.jpg", false ) ),
						new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 1, 0, 0 ) ) ) ) );
		// Group s = new Group( Mat4.rotateZ( 180 ), new Rectangle( 1, 1.5,
		// new PerfectDiffuseMaterial( new InterpolateTexture( new Vec3( 1, 0, 0 ), new Vec3( 0, 0, 1 ) ) ) ) );
		// Group s = new Group( new Sphere( 1,
		// new PerfectDiffuseMaterial( new InterpolateTexture( new Vec3( 1, 0, 0 ), new Vec3( 0, 0, 1 ) ) ) ) );
		// Group t = new Group( Mat4.translate( -2, 0, 0 ),
		// new Zylinder( 1., 2., new PerfectDiffuseMaterial( new PictureTexture( "doc/test/candle.jpg" ) ),
		// new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 1, 0, 0 ) ) ),
		// new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 0, 1 ) ) ) ) );
		return new Group( bg, s );
	}
	
	private Shape createTestScene1() throws IOException
	{
		Shape bg = new Background( new BackgroundMaterial( new PictureTexture( "doc/test/world.jpg", false ) ) );
		Group s = new Group( new Sphere( 1, new ReflectiveMaterial( new PictureTexture( "doc/test/world.jpg", false ),
				new ConstantTexture( new Vec3( 1, 0, 0 ) ) ) ) );
		// Group s = new Group( new Rectangle2( 18, 9, new PerfectDiffuseMaterial( new TransformationTexture(
		// new PictureTexture( "doc/test/world.jpg", false ), Mat4.rotateZ( 45 ) ) ) ) );
		
		// Group s =< new Group( Mat4.rotateX( 270 ),
		// new Rectangle2( 1, 1.5, new PerfectDiffuseMaterial( new PictureTexture( "doc/test/world.jpg", false ) ),
		// new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 1, 0, 0 ) ) ) ) );
		// Group s = new Group( Mat4.rotateZ( 180 ), new Rectangle( 1, 1.5,
		// new PerfectDiffuseMaterial( new InterpolateTexture( new Vec3( 1, 0, 0 ), new Vec3( 0, 0, 1 ) ) ) ) );
		
		// Group s = new Group( new Sphere( 1,
		// new PerfectDiffuseMaterial( new InterpolateTexture( new Vec3( 1, 0, 0 ), new Vec3( 0, 0, 1 ) ) ) ) );
		// Group t = new Group( Mat4.translate( -2, 0, 0 ),
		return new Group( bg, s );
	}
	
}
