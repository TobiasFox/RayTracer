/**
 * 
 */

package raytracer.b03;

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
import raytracer.imageCreator.shape.withMaterial.Quader;
import raytracer.imageCreator.shape.withMaterial.group.Group;
import raytracer.imageCreator.shape.withMaterial.light.Light;
import raytracer.imageCreator.shape.withMaterial.material.BackgroundInterpolation;
import raytracer.imageCreator.shape.withMaterial.material.Material;
import raytracer.imageCreator.shape.withMaterial.material.PerfectDiffuseMaterial;
import raytracer.imageCreator.shape.withMaterial.platon.Oktaeder;
import raytracer.imageCreator.shape.withMaterial.platon.Tetraeder;
import raytracer.imageCreator.shape.withMaterial.primitives.Rectangle;
import raytracer.imageCreator.shape.withMaterial.primitives.Sphere;
import raytracer.imageCreator.shape.withMaterial.texture.ConstantTexture;

/**
 * @author Tobias Fox
 *
 */
public class Main_b03
{
	
	private String name = "doc/test/b_0";
	private static final int WIDTH = 480;
	private static final int HEIGHT = 270;
	private static final int ABTASTRATE = 13;
	private static final int DEPTH = 5;
	
	private Main_b03()
	{
		ImageCreator creator = new ImageCreator( WIDTH, HEIGHT, ABTASTRATE, ImageQualities.Bit16,
				PixelColorCalculations.MonteCarloRaytrace );
		
		try
		{
			creator.setWorld( new World( createScene(), createBackground(), createLightSources() ) );
			creator.setRecursiveDepth( DEPTH );
			creator.setTransformation( setTransformation( 4 ) );
		} catch( FeatureNotUsingException e1 )
		{
			System.out.println( "error with scene or recursive" );
			e1.printStackTrace();
		}
		
		for ( int i = 0; i < 3; i++ )
		{
			switch ( i )
			{
				case 0:
					creator.startWithThreadPool( true );
					break;
				
				case 1:
					creator.startWithThreads();
					break;
				
				case 2:
					creator.start();
					break;
				
				default:
					break;
			}
			try
			{
				creator.savePicture( name + i + ".png" );
			} catch( IOException e )
			{
				System.out.println( "couldnt save picture4" );
				e.printStackTrace();
			}
		}
	}
	
	private Mat4 setTransformation( int i )
	{
		switch ( i )
		{
			case 1:
				// hinten, umgedreht, links oben
				return Mat4.translate( -12, 8, 0 ).mult( Mat4.rotateY( -50 ) ).mult( Mat4.rotateX( -10 ) )
						.mult( Mat4.translate( 0, 6, 13 ) ).mult( Mat4.rotateY( 180 ) )
						.mult( Mat4.translate( 0, 20, 60 ) ).mult( Mat4.rotateY( 30 ) )
						.mult( Mat4.translate( 10, 0, 0 ) ).mult( Mat4.rotateX( -60 ) )
						.mult( Mat4.translate( 0, 0, -2 ) ).mult( Mat4.translate( 3, 0, 30 ) )
						.mult( Mat4.translate( 0, -5, -5 ) ).mult( Mat4.rotateX( 5 ) );
			case 2:
				// vorne, unten
				return Mat4.translate( 1, 4, 25 ).mult( Mat4.rotateX( 10 ) );
			case 3:
				// vorne, rechts mitte, nach unten links
				return Mat4.translate( 15, 25, 30 ).mult( Mat4.rotateX( -25 ) ).mult( Mat4.rotateY( 20 ) );
			case 4:
				// hinten unten nach oben
				return Mat4.translate( 0, .5, -39 ).mult( Mat4.rotateY( 180 ) ).mult( Mat4.rotateX( 33 ) );
			default:
				return Mat4.identity;
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
		Material[] mat = {
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 1, 0, 0 ) ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 1, 1, 0 ) ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 1, 0 ) ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 1, 1 ) ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 0, 1 ) ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 0, 1 ) ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 204, 102 ).toRGB() ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 153, 51, 0 ).toRGB() ) )
		};
		
		Group ebene = new Group( Mat4.translate( -250, 0, 250 ).mult( Mat4.rotateX( -90 ) ),
				new Rectangle( 500, 500, new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( .5, .5, .5 ) ) ),
						new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 1, 0, 0 ) ) ) ) );
		
		Material[] cubeMat = {
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 255, 0, 128 ).toRGB() ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 255, 0, 0 ).toRGB() ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 255, 153, 0 ).toRGB() ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 0, 204, 102 ).toRGB() ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 204, 51, 255 ).toRGB() ) ),
				new PerfectDiffuseMaterial( new ConstantTexture( new Vec3( 255, 64, 64 ).toRGB() ) )
		};
		Oktaeder okta = new Oktaeder( 20, mat );
		Group oktaGroup = new Group( Mat4.translate( 0, 17, 0 ), okta );
		
		Tetraeder tetra = new Tetraeder( 30, mat[4], mat[0], mat[5], mat[2] );
		
		Group cubeS1 = new Group( Mat4.translate( .5, 0, -.5 ), new Sphere( new Vec3( 0, 0, 0 ), 1, mat[4] ) );
		Group cubeS2 = new Group( Mat4.translate( 3.5, 0, -.5 ), new Sphere( new Vec3( 0, 0, 0 ), 1, mat[7] ) );
		Group cubeS3 = new Group( Mat4.translate( 3.5, 0, -3.5 ), new Sphere( new Vec3( 0, 0, 0 ), 1, mat[1] ) );
		Group cubeS4 = new Group( Mat4.translate( .5, 0, -3.5 ), new Sphere( new Vec3( 0, 0, 0 ), 1, mat[6] ) );
		
		Group cubeS5 = new Group( Mat4.translate( ( Math.sqrt( 2 ) * 4 ) / 2., 3, -( ( Math.sqrt( 2 ) * 4 ) / 2. ) ),
				new Sphere( new Vec3( 0, 0, 0 ), 1, mat[0] ) );
		Group sphereForm = new Group( Mat4.translate( 0, 5, 0 ), cubeS1, cubeS2, cubeS3, cubeS4, cubeS5 );
		
		Quader cube = new Quader( new Vec3( 4, 4, 4 ), cubeMat );
		
		Group cubeWithSpheres = new Group( cube, sphereForm );
		Group c1 = new Group( Mat4.translate( 0, 18, 0 ).mult( Mat4.rotateX( 180. ).mult( Mat4.rotateY( -90 ) ) ),
				cubeWithSpheres );
		Group c2 = new Group( Mat4.translate( 0, 12, -12.5 ).mult( Mat4.rotateX( 90 ).mult( Mat4.rotateY( -90 ) ) ),
				cubeWithSpheres );
		Group c4 = new Group( Mat4.rotateX( -90 ).mult( Mat4.translate( 0, -6.8, 11.85 ) ), cubeWithSpheres );
		
		Group c = new Group( c1, c2, c4, cubeWithSpheres );
		Group cc = new Group( Mat4.translate( 0, 18, 0 ), c );
		Group cc2 = new Group( Mat4.translate( 0, 8, 14 ), c );
		Group cc3 = new Group( Mat4.translate( 0, 8, -14 ), c );
		Group gebilde = new Group( c, cc, cc2, cc3 );
		Group gebilde2 = new Group( Mat4.rotateY( 90 ).mult( Mat4.translate( 26, 0, -22 ) ), c, cc, cc2, cc3 );
		Group gebilde3 = new Group( Mat4.translate( 53, 0, 0 ), gebilde2 );
		Group tetraGroup2 = new Group( Mat4.translate( -40, 0, -8 ), tetra );
		Group oktaGroup2 = new Group( Mat4.translate( 10, 0, 8 ), oktaGroup );
		
		return new Group( gebilde, gebilde2, gebilde3, tetraGroup2, oktaGroup2, ebene );
	}
	
	public static void main( String[] a )
	{
		new Main_b03();
	}
}
