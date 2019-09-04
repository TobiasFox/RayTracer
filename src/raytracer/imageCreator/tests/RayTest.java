/**
 * 
 */
package raytracer.imageCreator.tests;

import org.junit.Assert;
import org.junit.Test;

import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;

/**
 * @author Tobias
 *
 */
public class RayTest
{

	/**
	 * @throws java.lang.Exception
	 */
	Vec3 origin = new Vec3(0, 0, 0);
	Vec3 direction = new Vec3(3, 5, 3);
	private double lowerBound = 0;
	private double upperBound = Double.POSITIVE_INFINITY;

	Ray ray = new Ray(origin, direction, lowerBound, upperBound);

	/**
	 * Test method for {@link fox843616.a03.pinholeCamera.Ray#pointAt(double)}.
	 */
	@Test
	public void testPointAt()
	{
		Assert.assertTrue(origin.equals(ray.pointAt(0)));
	}

	/**
	 * Test method for {@link fox843616.a03.pinholeCamera.Ray#pointAt(double)}.
	 */
	@Test
	public void testPointAt2()
	{
		Assert.assertTrue(direction.normalize().equals(ray.pointAt(1)));
	}

	/**
	 * Test method for {@link fox843616.a03.pinholeCamera.Ray#pointAt(double)}.
	 */
	@Test
	public void testPointAt3()
	{
		Assert.assertTrue(origin.add(direction.normalize().scale(2.5)).equals(ray.pointAt(2.5)));
	}

	/**
	 * Test method for {@link fox843616.a03.pinholeCamera.Ray#pointAt(double)}.
	 */
	@Test
	public void testPointUpperBound()
	{
		Vec3 test = ray.pointAt(upperBound + 20);
		Assert.assertNull(test);
	}

	/**
	 * Test method for {@link fox843616.a03.pinholeCamera.Ray#pointAt(double)}.
	 */
	@Test
	public void testPointNegativ()
	{
		Vec3 test = ray.pointAt(-10);
		Assert.assertNull(test);
	}

}
