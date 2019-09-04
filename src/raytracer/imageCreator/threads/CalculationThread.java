/**
 * 
 */
package raytracer.imageCreator.threads;

import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.Sampling;
import raytracer.imageCreator.sampling.imageQuality.ImageQuality;
import raytracer.imageCreator.threads.time.TimeObject;

/**
 * @author Tobias Fox
 *
 */
public class CalculationThread extends Thread
{

	/**
	 * 
	 */
	private int start;
	private int end;
	private int height;
	private Sampling sampling;
	private ImageQuality imageQuality;
	private static int count = 0;
	private int countHere = count++;

	public CalculationThread( ThreadData data )
	{
		start = data.getStart();
		end = data.getEnd();
		this.height = data.getHeight();
		this.sampling = data.getSampling();
		imageQuality = data.getImageQuality();
		setName("CalculationThread" + countHere);
	}

	@Override
	public void run()
	{
		double section = (double) end - start;
		double calc = section * 1.0 / 20;
		int intervall = (int) (calc + start);
		int step = (int) (calc);

		System.out.println(getName() + " start");
		long startTime = System.currentTimeMillis();
		// SimpleDateFormat format = new SimpleDateFormat("hh.mm.ssss");
		// System.out.println(format.format(new Date(startTime)));
		for ( int x = start; x != end; x++ )
		{
			// System.out.println(getName() + " Reihe: " + x);
			for ( int y = 0; y != height; y++ )
			{
				Vec3 color = sampling.stratifiedSamplingGammaCorrected(x, y);
				imageQuality.setPixel(x, y, color);
			}

			if ( x == intervall )
			{
				TimeObject time = new TimeObject(startTime);
				System.out.printf("%s %.0f%% in %.3f %s%n", getName(), ((x - start) / section) * 100.0, time.getTime(),
						time.getUnit());
				intervall += step;
			}
		}
		TimeObject time = new TimeObject(startTime);
		System.out.printf("%s Benötigte %s: %.3f%n", getName(), time.getUnit(), time.getTime());
	}

}