/**
 * 
 */
package raytracer.imageCreator.threads.time;

/**
 * @author Tobias Fox
 *
 */
public class TimeObject
{
	private String unit = "Sekunden";
	private double time;

	public TimeObject( long previousTime )
	{
		calcTime(previousTime);
	}

	public String getUnit()
	{
		return unit;
	}

	public double getTime()
	{
		return time;
	}

	private void calcTime( long previousTime )
	{
		time = (System.currentTimeMillis() - previousTime) / 1000.0;
		if ( time >= 60 )
		{
			time /= 60;
			unit = "Minuten";
			if ( time >= 60 )
			{
				time /= 60;
				unit = "Stunden";
			}
		}
	}

}
