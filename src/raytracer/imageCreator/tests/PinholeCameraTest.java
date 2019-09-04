/**
 * 
 */
package raytracer.imageCreator.tests;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.PinholeCamera;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;

/**
 * @author Tobias
 *
 */
public class PinholeCameraTest
{

	/**
	 * Test method for
	 * {@link fox843616.a03.pinholeCamera.PinholeCamera#generateRay(double, double)}.
	 */
	@Test
	public void testGenerateRay()
	{
		PinholeCamera camera = new PinholeCamera(10, 10, Math.PI / 2);
		List<Point> list = new ArrayList<>();
		List<Vec3> results = new ArrayList<>();
		double testResult = 1 / Math.sqrt(3);

		list.add(new Point(0, 0));
		list.add(new Point(5, 5));
		list.add(new Point(10, 10));
		results.add(new Vec3(-testResult, testResult, -testResult));
		results.add(new Vec3(0, 0, -1));
		results.add(new Vec3(testResult, -testResult, -testResult));

		for ( int i = 0; i < list.size() - 1; i++ )
		{
			Ray r = camera.generateRay(list.get(i).getX(), list.get(i).getY(), 0, Double.POSITIVE_INFINITY);
			Vec3 dir = r.direction;
			if ( !dir.equals(results.get(i)) )
				Assert.fail("Vektor " + i + " ist nicht richtig.");
		}
	}
}
