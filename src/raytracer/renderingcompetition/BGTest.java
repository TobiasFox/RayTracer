
package raytracer.renderingcompetition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import raytracer.imageCreator.ImageCreator;
import raytracer.imageCreator.World;
import raytracer.imageCreator.enums.ImageQualities;
import raytracer.imageCreator.enums.PixelColorCalculations;
import raytracer.imageCreator.exceptions.FeatureNotUsingException;
import raytracer.imageCreator.shape.withMaterial.Background;
import raytracer.imageCreator.shape.withMaterial.group.Group;
import raytracer.imageCreator.shape.withMaterial.light.Light;
import raytracer.imageCreator.shape.withMaterial.material.BackgroundMaterial;
import raytracer.imageCreator.shape.withMaterial.texture.PictureTexture;

public class BGTest
{
	
	private static final String NAME = "doc/test/bgTest.png";
	private static final int WIDTH = 480;
	private static final int HEIGHT = 270;
	private static final int ABTASTRATE = 1;
	private static final int DEPTH = 2;
	private static final String path = "src/raytracer/textures/";
	
	public BGTest()
	{
		ImageCreator creator = new ImageCreator( WIDTH, HEIGHT, ABTASTRATE, ImageQualities.Bit16,
				PixelColorCalculations.MonteCarloRaytrace );
		
		try
		{
			creator.setWorld( new World( createScene(), createBackground(), createLightSources() ) );
			// creator.setTransformation( Mat4.rotateY( 180 ) );
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
		try
		{
			return new Background( new BackgroundMaterial( new PictureTexture( path + "sky3.jpg" ) ) );
		} catch( IOException exc )
		{
			exc.printStackTrace();
		}
		return null;
	}
	public World createWorld()
	{
		return new World( createScene(), createBackground(), createLightSources() );
	}
	
	private List<Light> createLightSources()
	{
		ArrayList<Light> lightSources = new ArrayList<>();
		return lightSources;
	}
	
	private Group createScene()
	{
		return new Group();
	}
	public static void main( String[] args )
	{
		// new BGTest();
	}
}
