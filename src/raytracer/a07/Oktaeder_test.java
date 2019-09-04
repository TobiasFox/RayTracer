/**
 * 
 */

package raytracer.a07;

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
import raytracer.imageCreator.shape.withMaterial.material.Material;
import raytracer.imageCreator.shape.withMaterial.material.PerfectDiffuseMaterial;
import raytracer.imageCreator.shape.withMaterial.platon.Oktaeder;
import raytracer.imageCreator.shape.withMaterial.primitives.Rectangle;
import raytracer.imageCreator.shape.withMaterial.primitives.Sphere;
import raytracer.imageCreator.shape.withMaterial.texture.ConstantTexture;

/**
 * @author Tobias Fox
 *
 */
public class Oktaeder_test
{
	
	private static final String NAME = "doc/test/a07-232.png";
	private static final int WIDTH = 480;
	private static final int HEIGHT = 270;
	private static final int ABTASTRATE = 3;
	private static final int DEPTH = 3;
	
	private Oktaeder_test()
	{
		ImageCreator creator = new ImageCreator( WIDTH, HEIGHT, ABTASTRATE, ImageQualities.Bit16,
				PixelColorCalculations.MonteCarloRaytrace );
		
		try
		{
			creator.setWorld( new World( createScene(), createBackground(), createLightSources() ) );
			creator.setRecursiveDepth( DEPTH );
			
			// creator.setTransformation(Mat4.translate(0, 0, 10));
			
			// creator.setTransformation(Mat4.translate(3, 1, 8));
			
			// creator.setTransformation(Mat4.translate(3, -2,
			// -6).mult(Mat4.rotateY(180)).mult(Mat4.rotateX(20)));
			
			creator.setTransformation( Mat4.translate( 3, -2, -6 ).mult( Mat4.rotateY( 180 ) ) );
			
			// creator.setTransformation(Mat4.translate(-3, 0,
			// 2).mult(Mat4.rotateY(-120)).mult(Mat4.translate(-3, -2, 3))
			// .mult(Mat4.translate(-8, 12,
			// -4)).mult(Mat4.rotateY(-90)).mult(Mat4.rotateX(-80))
			// .mult(Mat4.translate(-3, 0, 0)));
			// creator.setTransformation(Mat4.translate(1, 1,
			// -7).mult(Mat4.rotateY(145)).mult(Mat4.rotateX(0)));
			
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
		Vec3 center = new Vec3( 0, 0, 0 );
		Vec3 color = new Vec3( 1, 0, 0 );
		PerfectDiffuseMaterial material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape sphere = new Sphere( center, 1, material );
		
		center = new Vec3( 0, 0, -5 );
		color = new Vec3( 1, 0, 0 );
		material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape sp34 = new Sphere( center, 1, material );
		
		center = new Vec3( 0, -501, 0 );
		color = new Vec3( .85, .85, .85 );
		material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		Shape earth = new Sphere( center, 500, material );
		
		color = new Vec3( 0, 0, 1 );
		material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		color = new Vec3( 1, 1, 0 );
		PerfectDiffuseMaterial materialBack = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
		PerfectDiffuseMaterial materialFront = new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 1, 1, 1 ) ) );
		Group r = new Group( Mat4.rotateY( 45 ), new Rectangle( 3, 5, material ) );
		// Group grr = new Group(Mat4.rotateY(180), r);
		Material[] m = new Material[6];
		Vec3[] test = {
				new Vec3( 1, 0, 1 ), new Vec3( 0, 1, 0 ), new Vec3( 1, 1, 0 ), new Vec3( 255, 153, 51 ).toRGB(),
				new Vec3( 0, 1, 1 ), new Vec3( 0, 0, 1 )
		};
		for ( int i = 0; i < m.length; i++ )
		{
			m[i] = new PerfectDiffuseMaterial( new ConstantTexture( test[i] ) );
		}
		
		// Group gr2 = new Group(Mat4.rotateY(0), new Cube(new Vec3(3, 1, 2),
		// m));
		// Group gr2 = new Group(new ConvexPolygon(materialFront, new Vec3(0, 0,
		// 0), new Vec3(2, 0, 0), new Vec3(3, 2, 0),
		// new Vec3(2, 4, 0)));
		// Shape group = new Group(bg, earth, grrrg);
		// ((Group) group).addAllShape(bg);
		
		// Shape t = new Triangle(new Vec3(0, 0, -1), new Vec3(2, 0, -1), new
		// Vec3(1, 2, 2), materialFront, materialBack);
		// Group2 gr2 = new Group2(Mat4.rotateX(-90).mult(Mat4.translate(0, 4,
		// 0)), new Rectangle(2, 2, materialFront));
		Material top = materialFront;
		Material bottom = materialBack;
		Material body = new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 1, 0, 0 ) ) );
		// Group gr2 = new Group(new Zylinder(new Vec3(0, 0, 0), 2, 3, top,
		// bottom, body));
		// Group gr2 = new Group(
		// new Tetraeder(6, materialFront, bottom, body, new
		// PerfectDiffuseMaterial(new Vec3(1, 0, 1))));
		Material[] mat = {
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 1, 0, 0 ) ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 1, 1, 0 ) ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 1, 0 ) ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 1, 1 ) ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 0, 1 ) ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 1, 0, 1 ) ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 204, 102 ).toRGB() ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 153, 51, 0 ).toRGB() ) )
		};
		
		return new Group( Mat4.rotateY( 90 ), new Oktaeder( 3, mat ) );
	}
	
	public static void main( String[] a )
	{
		new Oktaeder_test();
	}
}
