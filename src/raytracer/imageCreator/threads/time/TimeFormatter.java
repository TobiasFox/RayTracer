package raytracer.imageCreator.threads.time;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatter
{
	private static final SimpleDateFormat TIMEFORMATTER = new SimpleDateFormat("HH.mm.ssss");
	private static Date date = new Date();

	/**
	 * formats a String with committed long
	 * 
	 * @param startTime
	 * @return String with time from parameter
	 */
	public static String getFormatTime( long startTime )
	{
		date.setTime(startTime);
		return TIMEFORMATTER.format(date);
	}

	/**
	 * formats a String with current time
	 * 
	 * @return String with current time
	 */
	public static String getActualFormatTime()
	{
		return getFormatTime(System.currentTimeMillis());
	}

}
