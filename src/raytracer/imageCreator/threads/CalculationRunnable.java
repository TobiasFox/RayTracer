
package raytracer.imageCreator.threads;

import java.util.concurrent.CountDownLatch;
import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.Sampling;
import raytracer.imageCreator.sampling.imageQuality.ImageQuality;
import raytracer.imageCreator.threads.time.TimeFormatter;
import raytracer.imageCreator.threads.time.TimeObject;

public class CalculationRunnable implements Runnable
{
	
	private int start;
	private int end;
	private int height;
	private Sampling sampling;
	private ImageQuality imageQuality;
	private CountDownLatch latch;
	private static final int OUTPUTFREQUENCE = 2;
	private double numOfRunnables;
	
	public CalculationRunnable( ThreadData data, CountDownLatch latch )
	{
		start = data.getStart();
		end = data.getEnd();
		this.height = data.getHeight();
		this.sampling = data.getSampling();
		this.imageQuality = data.getImageQuality();
		this.latch = latch;
		numOfRunnables = ( latch.getCount() );
	}
	
	@Override
	public void run()
	{
		// calculate intervall for displaying option
		int numOfRows = end - start;
		int rowsBetweenIntervalls = numOfRows / OUTPUTFREQUENCE;
		int intervall = rowsBetweenIntervalls + start;
		long startTime = System.currentTimeMillis();
		StringBuilder b = new StringBuilder( TimeFormatter.getActualFormatTime() );
		b.append( " start: " ).append( start ).append( " until: " ).append( end ).append( " rows: " )
				.append( numOfRows );
		System.out.println( b );
		for ( int x = start; x != end; x++ )
		{
			for ( int y = 0; y != height; y++ )
			{
				Vec3 color = sampling.stratifiedSamplingGammaCorrected( x, y );
				imageQuality.setPixel( x, y, color );
			}
			if ( x == intervall )
			{
				// runnable reaches next interval -> display status
				TimeObject time = new TimeObject( startTime );
				System.out.printf( "%s %s %.0f%% in %.3f %s%n", TimeFormatter.getActualFormatTime(),
						Thread.currentThread().getName(), ( ( x - start ) / (double) numOfRows ) * 100.0,
						time.getTime(), time.getUnit() );
				intervall += rowsBetweenIntervalls;
			}
		}
		TimeObject time = new TimeObject( startTime );
		latch.countDown();
		System.out.printf( "%s %s Benötigte %s: %.3f - vom gesamten Bild fertig: %s%%%n",
				TimeFormatter.getActualFormatTime(), Thread.currentThread().getName(), time.getUnit(), time.getTime(),
				( ( numOfRunnables - (int) ( latch.getCount() ) ) / numOfRunnables ) * 100 );
	}
	
	public void setStart( int start )
	{
		this.start = start;
	}
	
	public void setEnd( int end )
	{
		this.end = end;
	}
	
}
