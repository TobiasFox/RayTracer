/**
 * 
 */
package raytracer.imageCreator.general;

/**
 * @author Tobias Fox
 *
 */

public enum Spectrum
{
	Bit8(255), Bit16(65536);

	private final int value;
	public static int DEFAULT = Bit8.value;

	Spectrum( int value )
	{
		this.value = value;
	}

	public int value()
	{
		return value;
	}
}