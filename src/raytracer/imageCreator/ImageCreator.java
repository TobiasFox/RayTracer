/**
 * 
 */

package raytracer.imageCreator;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import raytracer.imageCreator.enums.ImageQualities;
import raytracer.imageCreator.enums.PixelColorCalculations;
import raytracer.imageCreator.exceptions.FeatureNotUsingException;
import raytracer.imageCreator.general.Mat4;
import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.Sampling;
import raytracer.imageCreator.sampling.imageQuality.ImageQuality;
import raytracer.imageCreator.sampling.imageQuality.ImageQualityFactory;
import raytracer.imageCreator.shape.Shape;
import raytracer.imageCreator.shape.withMaterial.material.Density;
import raytracer.imageCreator.threads.CalculationRunnable;
import raytracer.imageCreator.threads.CalculationThread;
import raytracer.imageCreator.threads.ThreadData;
import raytracer.imageCreator.threads.time.TimeFormatter;
import raytracer.imageCreator.threads.time.TimeObject;

/**
 * @author Tobias Fox
 *
 */
public class ImageCreator implements ImageCreatorInterface
{
	
	private int width;
	private int height;
	private int abtastRate;
	private int threadCount;
	private ExecutorService threadPool;
	private Sampling sampling;
	private ImageQuality imageQuality;
	public static Density DEFAULTDENSITY = Density.AIR;
	
	/**
	 * creates a new Imagecreator with given parameter.
	 * 
	 * @param width
	 * @param height
	 * @param abtastRate
	 * @param quality
	 * @param variation
	 */
	public ImageCreator( int width, int height, int abtastRate, ImageQualities quality,
			PixelColorCalculations variation )
	{
		this.width = width;
		this.height = height;
		this.abtastRate = abtastRate;
		threadCount = 4;
		threadPool = Executors.newFixedThreadPool( threadCount );
		sampling = new Sampling( width, height, abtastRate, variation );
		imageQuality = ImageQualityFactory.create( width, height, quality );
	}
	
	/**
	 * calls the other constructor with 16 Bit ImageQuality
	 * 
	 * @param width
	 * @param height
	 * @param abtastRate
	 * @param variation
	 */
	public ImageCreator( int width, int height, int abtastRate, PixelColorCalculations variation )
	{
		this( width, height, abtastRate, ImageQualities.Bit16, variation );
	}
	
	public static BufferedImage gammacorrectBufferedImage( BufferedImage original, double gamma )
	{
		return Sampling.gammacorrectBufferedImage( original, gamma );
	}
	
	@Override
	public void setWorld( World world ) throws FeatureNotUsingException
	{
		sampling.setWorld( world );
	}
	
	@Override
	public World getWorld() throws FeatureNotUsingException
	{
		return sampling.getWorld();
	}
	
	@Override
	public void createRandomCircles( int numberOfCircles ) throws FeatureNotUsingException
	{
		sampling.createRandomCircles( numberOfCircles );
	}
	
	@Override
	public void startWithThreadPool()
	{
		if ( sceneEmpty() )
		{
			return;
		}
		
		int[][] rangeArray = calcRanges();
		CountDownLatch latch = new CountDownLatch( rangeArray.length );
		long currentTime = System.currentTimeMillis();
		ThreadData data = new ThreadData( height, sampling, imageQuality );
		for ( int i = 0; i < rangeArray.length; i++ )
		{
			data.setStart( rangeArray[i][0] );
			data.setEnd( rangeArray[i][1] );
			CalculationRunnable run = new CalculationRunnable( data, latch );
			threadPool.execute( run );
		}
		
		// wait until every runnable has finished
		try
		{
			latch.await();
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}
		
		TimeObject time = new TimeObject( currentTime );
		System.out.printf( "%s Insgesamt benötigte %s: %.3f%n%n", TimeFormatter.getActualFormatTime(), time.getUnit(),
				time.getTime() );
	}
	
	@Override
	public void startWithThreadPool( boolean shutDownPool )
	{
		startWithThreadPool();
		if ( shutDownPool )
		{
			threadPool.shutdown();
		}
	}
	
	@Override
	public void closeThreadPool()
	{
		threadPool.shutdown();
	}
	
	@Override
	public void startWithThreads()
	{
		if ( sceneEmpty() )
		{
			return;
		}
		
		int threads = ( width % 4 ) == 0 ? 4 : 2;
		
		CalculationThread[] threadList = new CalculationThread[threads];
		
		for ( int i = 0; i < threads; i++ )
		{
			ThreadData data = new ThreadData( ( width / threads ) * i, ( width / threads ) * ( i + 1 ), height,
					sampling, imageQuality );
			threadList[i] = new CalculationThread( data );
		}
		
		long currentTime = System.currentTimeMillis();
		for ( CalculationThread calculationThread : threadList )
		{
			calculationThread.start();
		}
		
		for ( CalculationThread calculationThread : threadList )
		{
			try
			{
				calculationThread.join();
			} catch( InterruptedException e )
			{
				e.printStackTrace();
			}
		}
		TimeObject time = new TimeObject( currentTime );
		System.out.printf( "Benötigte %s: %.3f%n", time.getUnit(), time.getTime() );
		time = null;
		for ( CalculationThread calculationThread : threadList )
		{
			calculationThread = null;
		}
		threadList = null;
	}
	
	@Override
	public void start()
	{
		if ( sceneEmpty() )
		{
			return;
		}
		
		long currentTime = System.currentTimeMillis();
		for ( int x = 0; x != width; x++ )
		{
			System.out.println( "Reihe: " + x );
			for ( int y = 0; y != height; y++ )
			{
				Vec3 color = sampling.stratifiedSamplingGammaCorrected( x, y );
				imageQuality.setPixel( x, y, color );
			}
		}
		TimeObject time = new TimeObject( currentTime );
		System.out.printf( "Benötigte %s: %.3f%n", time.getUnit(), time.getTime() );
	}
	
	@Override
	public void savePicture( String filename ) throws IOException
	{
		imageQuality.write( filename );
		System.out.printf( "%s Wrote image: %s %n%n", TimeFormatter.getActualFormatTime(), filename );
	}
	
	@Override
	public int getAbtastrate()
	{
		return abtastRate;
	}
	
	@Override
	public void setAbtastrate( int abtastrate )
	{
		this.abtastRate = abtastrate;
	}
	
	@Override
	public void setGamma( double gamma )
	{
		sampling.setGamma( gamma );
	}
	
	@Override
	public double getGamma()
	{
		return sampling.getGamma();
	}
	
	@Override
	public ImageQualities getImageQuality()
	{
		return imageQuality.getQuality();
	}
	
	@Override
	public void setDefaultColor( Vec3 defaultColor ) throws FeatureNotUsingException
	{
		sampling.setDefaultColor( defaultColor );
	}
	
	@Override
	public void getDefaultColor() throws FeatureNotUsingException
	{
		sampling.getDefaultColor();
	}
	
	@Override
	public void setDefaultDensity( Density density )
	{
		DEFAULTDENSITY = density;
	}
	
	@SuppressWarnings( "static-access" )
	@Override
	public Density getDefaultDensity()
	{
		return this.DEFAULTDENSITY;
	}
	
	@Override
	public void setPixelColorCalculation( PixelColorCalculations variation )
	{
		sampling.setPixelColorCalculation( variation );
	}
	
	@Override
	public PixelColorCalculations getPixelColorCalculation()
	{
		return sampling.getPixelColorCalculation();
	}
	
	@Override
	public void setRecursiveDepth( int depth ) throws FeatureNotUsingException
	{
		sampling.setRecursiveDepth( depth );
	}
	
	@Override
	public int getRecursiveDepth() throws FeatureNotUsingException
	{
		return sampling.getRecursiveDepth();
	}
	
	public void addTransformation( Mat4 transformation ) throws FeatureNotUsingException
	{
		sampling.addTransformation( transformation );
	}
	
	public void setTransformation( Mat4 transformation ) throws FeatureNotUsingException
	{
		sampling.setTransformation( transformation );
	}
	
	public void resetTransformation() throws FeatureNotUsingException
	{
		sampling.resetTransformation();
	}
	
	public Mat4 getTransformation() throws FeatureNotUsingException
	{
		return sampling.getTransformation();
	}
	
	private int[][] calcRanges()
	{
		// calculates the ranges(start, end) from the runnables
		int start = 0;
		int count = 6;
		int end = 0;
		int rest = 0;
		boolean loop = true;
		while ( loop )
		{
			if ( ( width % count ) == 0 )
			{
				loop = false;
				end = width / count;
			}
			else
			{
				count--;
				// at least every thread have one runnable
				if ( count <= threadCount )
				{
					loop = false;
					rest = width % count;
					end = ( width - rest ) / count;
				}
			}
		}
		
		int[][] array = new int[rest == 0 ? count : count + 1][2];
		for ( int i = 0; i < ( rest == 0 ? array.length : array.length - 1 ); i++ )
		{
			array[i][0] = start + ( end * i );
			array[i][1] = end * ( i + 1 );
		}
		
		// calculate ranges from last part with rest
		if ( rest != 0 )
		{
			array[count][0] = array[count - 1][1];
			array[count][1] = array[count - 1][1] + rest;
		}
		return array;
	}
	
	private boolean sceneEmpty()
	{
		Object testShape = null;
		try
		{
			switch ( sampling.getPixelColorCalculation() )
			{
				case MonteCarloRaytrace:
					testShape = sampling.getWorld();
					break;
				case Normal:
				case RayTrace:
					testShape = sampling.getScene();
				default:
					break;
			}
		} catch( FeatureNotUsingException e )
		{
			return true;
		}
		if ( testShape == null )
		{
			return true;
		}
		return false;
	}
	
	public void setScene( Shape scene ) throws FeatureNotUsingException
	{
		sampling.setScene( scene );
	}
	
}
