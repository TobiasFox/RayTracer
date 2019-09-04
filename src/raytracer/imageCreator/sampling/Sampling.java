/**
 * 
 */

package raytracer.imageCreator.sampling;

import java.awt.image.BufferedImage;
import raytracer.imageCreator.World;
import raytracer.imageCreator.enums.PixelColorCalculations;
import raytracer.imageCreator.exceptions.FeatureNotUsingException;
import raytracer.imageCreator.general.Mat4;
import raytracer.imageCreator.general.Random;
import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.colorcalculation.ColorCalculation;
import raytracer.imageCreator.sampling.colorcalculation.ColorCalculationFactory;
import raytracer.imageCreator.shape.Shape;

/**
 * @author Tobias Fox
 *
 */
public class Sampling
{
	
	private int abtastRate = 4;
	private int abtastPunkte = abtastRate * abtastRate;
	private int width;
	private int height;
	private PixelColorCalculations variation;
	private GammaCorrection gamma = new GammaCorrection();
	
	private ColorCalculation colorCalculation;
	
	public Sampling( int width, int height, int abtastRate, PixelColorCalculations variation )
	{
		this.width = width;
		this.height = height;
		this.abtastRate = abtastRate;
		abtastPunkte = abtastRate * abtastRate;
		setPixelColorCalculation( variation );
	}
	
	public Sampling( Sampling other )
	{
		this( other.width, other.height, other.abtastPunkte, other.variation );
		try
		{
			setWorld( other.getWorld() );
			setDefaultColor( other.getDefaultColor() );
		} catch( FeatureNotUsingException e )
		{
			e.printStackTrace();
			throw new RuntimeException( "scene oder defaultColor konnte nicht gesetzt werden." );
		}
	}
	
	public Vec3 stratifiedSamplingGammaCorrected( int x, int y )
	{
		return gamma.correct( stratifiedSampling( x, y ) );
	}
	
	public Vec3 stratifiedSampling( int x, int y )
	{
		Vec3 sum = new Vec3( 0, 0, 0 );
		for ( int xi = 0; xi < abtastRate; xi++ )
		{
			for ( int yi = 0; yi < abtastRate; yi++ )
			{
				double xs = x + ( ( xi + Random.random() ) / abtastRate );
				double ys = y + ( ( yi + Random.random() ) / abtastRate );
				
				sum = sum.add( colorCalculation.pixelColor( xs, ys ) );
				if ( ( x > ( width / 2 ) ) && ( y > ( height * ( 3. / 4. ) ) ) )
				{
					toString();
				}
			}
		}
		return sum.scale( 1.0 / abtastPunkte );
	}
	
	public void setPixelColorCalculation( PixelColorCalculations variation )
	{
		this.variation = variation;
		colorCalculation = ColorCalculationFactory.create( width, height, ( Math.PI / 2 ), variation );
	}
	
	public PixelColorCalculations getPixelColorCalculation()
	{
		return variation;
	}
	
	public void setGamma( double gamma )
	{
		this.gamma.setGamma( gamma );
	}
	
	public double getGamma()
	{
		return gamma.getGamma();
	}
	
	public void createRandomCircles( int numberOfCircles ) throws FeatureNotUsingException
	{
		colorCalculation.createRandomCircles( numberOfCircles );
	}
	
	public World getWorld() throws FeatureNotUsingException
	{
		return colorCalculation.getWorld();
	}
	
	public void setWorld( World world ) throws FeatureNotUsingException
	{
		colorCalculation.setWorld( world );
	}
	
	public Vec3 getDefaultColor() throws FeatureNotUsingException
	{
		return colorCalculation.getDefaultColor();
	}
	
	public void setDefaultColor( Vec3 defaultColor ) throws FeatureNotUsingException
	{
		if ( defaultColor == null )
		{
			throw new NullPointerException();
		}
		colorCalculation.setDefaultColor( defaultColor );
	}
	
	public int getRecursiveDepth() throws FeatureNotUsingException
	{
		return colorCalculation.getRecursiveDepth();
	}
	
	public void setRecursiveDepth( int depth ) throws FeatureNotUsingException
	{
		if ( depth < 0 )
		{
			throw new IllegalArgumentException( "Rekursive must be positiv or zero" );
		}
		colorCalculation.setRecursiveDepth( depth );
	}
	
	public void setTransformation( Mat4 transformation ) throws FeatureNotUsingException
	{
		if ( transformation == null )
		{
			throw new NullPointerException();
		}
		colorCalculation.setTransformation( transformation );
	}
	
	public void addTransformation( Mat4 transformation ) throws FeatureNotUsingException
	{
		if ( transformation == null )
		{
			throw new NullPointerException();
		}
		colorCalculation.addTransformation( transformation );
	}
	
	public Mat4 getTransformation() throws FeatureNotUsingException
	{
		return colorCalculation.getTransformation();
	}
	
	public void resetTransformation() throws FeatureNotUsingException
	{
		colorCalculation.resetTransformation();
	}
	
	public static BufferedImage gammacorrectBufferedImage( BufferedImage original, double gamma )
	{
		return GammaCorrection.gammaCorrection( original, gamma );
	}
	
	public void setScene( Shape scene ) throws FeatureNotUsingException
	{
		colorCalculation.setScene( scene );
	}
	
	public Shape getScene() throws FeatureNotUsingException
	{
		return colorCalculation.getScene();
	}
	
}