
package raytracer.imageCreator.sampling.imageQuality;

import raytracer.imageCreator.enums.ImageQualities;

public class ImageQualityFactory
{

	public static ImageQuality create( int width, int height, ImageQualities variation )
	{
		switch ( variation )
		{
			case Bit8:
				return new Image8Bit(width, height);
			case Bit16:
				return new Image16Bit(width, height);
			default:
				return new Image16Bit(width, height);
		}
	}
}
