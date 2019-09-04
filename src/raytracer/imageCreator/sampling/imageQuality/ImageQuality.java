package raytracer.imageCreator.sampling.imageQuality;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import raytracer.imageCreator.enums.ImageQualities;
import raytracer.imageCreator.general.Vec3;

public abstract class ImageQuality
{

	protected BufferedImage bufImage;

	public abstract void setPixel( int x, int y, double... c );

	public abstract void setPixel( int x, int y, Vec3 c );

	public abstract void setPixel( int x, int y, Color color );

	public abstract ImageQualities getQuality();

	public void write( String filename ) throws IOException
	{
		File outputfile = new File(filename);
		ImageIO.write(bufImage, "png", outputfile);
	}

}
