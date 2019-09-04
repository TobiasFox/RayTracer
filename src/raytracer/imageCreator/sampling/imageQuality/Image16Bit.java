/* Author: Henrik Tramberend tramberend@beuth-hochschule.de */

package raytracer.imageCreator.sampling.imageQuality;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.ComponentSampleModel;
import java.awt.image.DataBufferDouble;
import java.awt.image.DataBufferUShort;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;

import raytracer.imageCreator.enums.ImageQualities;
import raytracer.imageCreator.general.Spectrum;
import raytracer.imageCreator.general.Vec3;

class Image16Bit extends ImageQuality
{

	private WritableRaster raster;

	public Image16Bit( int width, int height )
	{
		Spectrum.DEFAULT = Spectrum.Bit16.value();

		ComponentColorModel ccm = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), false, false,
				ComponentColorModel.OPAQUE, DataBufferDouble.TYPE_USHORT);
		int[] bandOffsets = { 0, 3 * width * height, 3 * width * height * 2 };
		SampleModel sm = new ComponentSampleModel(DataBufferUShort.TYPE_USHORT, width, height, 3, 3 * width,
				bandOffsets);

		bufImage = new BufferedImage(ccm, Raster.createWritableRaster(sm, null), false, null);
		raster = bufImage.getRaster();
	}

	@Override
	public void setPixel( int x, int y, double... c )
	{
		int[] rgb = { (int) (clamp(c[0]) * 65535.999), (int) (clamp(c[1]) * 65535.999),
				(int) (clamp(c[2]) * 65535.999) };
		raster.setPixel(x, y, rgb);
	}

	@Override
	public void setPixel( int x, int y, Vec3 c )
	{
		int[] rgb = { (int) (clamp(c.x) * 65535.999), (int) (clamp(c.y) * 65535.999), (int) (clamp(c.z) * 65535.999) };
		raster.setPixel(x, y, rgb);
	}

	@Override
	public void setPixel( int x, int y, Color color )
	{
		double[] colorArray = { color.getRed(), color.getGreen(), color.getBlue() };
		setPixel(x, y, colorArray);
	}

	@Override
	public ImageQualities getQuality()
	{
		return ImageQualities.Bit16;
	}

	private double clamp( double v )
	{
		return Math.min(Math.max(0, v), 1);
	}

}
