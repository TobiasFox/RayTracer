package raytracer.imageCreator.threads;

import raytracer.imageCreator.sampling.Sampling;
import raytracer.imageCreator.sampling.imageQuality.ImageQuality;

/**
 * @author Tobias Fox
 *
 */
public class ThreadData
{
	private int start;
	private int end;
	private int height;
	private Sampling sampling;
	private ImageQuality imageQuality;
	private int width;

	public ThreadData( int start, int end, int height, Sampling sampling, ImageQuality imageQuality )
	{
		this.start = start;
		this.end = end;
		this.height = height;
		this.sampling = sampling;
		this.imageQuality = imageQuality;
	}

	public ThreadData( int height, Sampling sampling, ImageQuality imageQuality )
	{
		this.height = height;
		this.sampling = sampling;
		this.imageQuality = imageQuality;
	}

	public int getStart()
	{
		return start;
	}

	public int getEnd()
	{
		return end;
	}

	public int getHeight()
	{
		return height;
	}

	public int getWidth()
	{
		return width;
	}

	public Sampling getSampling()
	{
		return sampling;
	}

	public ImageQuality getImageQuality()
	{
		return imageQuality;
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
