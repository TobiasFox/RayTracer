/**
 * 
 */

package raytracer.imageCreator.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import raytracer.imageCreator.general.Vec3;

/**
 * @author Tobias
 *
 */
public class Vec3Test
{
	
	/**
	 * @throws java.lang.Exception
	 */
	
	Vec3 vec;
	private static final double DELTA = 1e-15;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		vec = new Vec3( 2, 2.5, 10 );
	}
	
	/**
	 * Test method for
	 * {@link fox843616.general.pinholeCamera.Vec3#add(fox843616.general.pinholeCamera.Vec3)}.
	 */
	@Test
	public void testAdd()
	{
		Vec3 vecAdd = vec.add( new Vec3( 2, 3.5, 2 ) );
		Assert.assertTrue( vecAdd.equals( new Vec3( 4, 6, 12 ) ) );
	}
	
	/**
	 * Test method for
	 * {@link fox843616.general.pinholeCamera.Vec3#sub(fox843616.general.pinholeCamera.Vec3)}.
	 */
	@Test
	public void testSub()
	{
		Vec3 vecSub = vec.sub( new Vec3( 2, 3.5, 2 ) );
		Assert.assertTrue( vecSub.equals( new Vec3( 0, -1, 8 ) ) );
	}
	
	/**
	 * Test method for
	 * {@link fox843616.general.pinholeCamera.Vec3#scale(double)}.
	 */
	@Test
	public void testScale()
	{
		Vec3 vecScale = vec.scale( 3 );
		Assert.assertTrue( vecScale.equals( new Vec3( 6, 7.5, 30 ) ) );
	}
	
	/**
	 * Test method for
	 * {@link fox843616.general.pinholeCamera.Vec3#scalarProduct(fox843616.general.pinholeCamera.Vec3)}.
	 */
	@Test
	public void testSkalarProduct()
	{
		double skalarPro = vec.scalarProduct( new Vec3( 1, 9, 3 ) );
		Assert.assertEquals( 54.5, skalarPro, DELTA );
	}
	
	/**
	 * Test method for
	 * {@link fox843616.general.pinholeCamera.Vec3#angleBetween(fox843616.general.pinholeCamera.Vec3)}.
	 */
	@Test
	public void testAngleBetween()
	{
		double alpha = vec.angleBetween( new Vec3( 0.5, 6, 4 ) );
		Assert.assertEquals( 56 / ( 10.5 * Math.sqrt( 52.25 ) ), alpha, DELTA );
	}
	
	/**
	 * Test method for
	 * {@link fox843616.general.pinholeCamera.Vec3#crossProduct(fox843616.general.pinholeCamera.Vec3)}.
	 */
	@Test
	public void testCrossProduct()
	{
		Vec3 cross = vec.crossProduct( new Vec3( 8, 9, -7 ) );
		Assert.assertTrue( cross.equals( new Vec3( -107.5, 94, -2 ) ) );
	}
	
	/**
	 * Test method for
	 * {@link fox843616.general.pinholeCamera.Vec3#crossProductArea(fox843616.general.pinholeCamera.Vec3)}.
	 */
	@Test
	public void testCrossProductArea()
	{
		double area = vec.crossProductArea( new Vec3( 8, 9, -7 ) );
		double a = Math.sqrt( ( -107.5 * -107.5 ) + ( 94 * 94 ) + ( -2 * -2 ) );
		Assert.assertEquals( a, area, DELTA );
	}
	
	/**
	 * Test method for
	 * {@link fox843616.general.pinholeCamera.Vec3#projection(fox843616.general.pinholeCamera.Vec3)}.
	 */
	@Test
	public void testProjection()
	{
		Vec3 vector = new Vec3( -2, -7, 3 );
		Vec3 projection = vec.projection( vector );
		double a = 8.5 / Math.sqrt( 62 );
		Assert.assertTrue( projection.equals( new Vec3( a * vector.x, a * vector.y, a * vector.z ) ) );
	}
	
	/**
	 * Test method for {@link fox843616.general.pinholeCamera.Vec3#normalize()}.
	 */
	@Test
	public void testNormalize()
	{
		Vec3 normal = vec.normalize();
		Assert.assertTrue( normal.equals( new Vec3( 2 / 10.5, 2.5 / 10.5, 10 / 10.5 ) ) );
	}
	
	@Test
	public void testGetLength()
	{
		Assert.assertEquals( 10.5, vec.length, DELTA );
	}
	
	/**
	 * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals()
	{
		Vec3 v = new Vec3( 1, 2, 3.5 );
		Assert.assertTrue( v.equals( new Vec3( 1, 2, 3.5 ) ) );
	}
	
	@Test
	public void testisOrthogonal()
	{
		Assert.assertTrue( vec.isOrthogonal( new Vec3( 0, -4, 1 ) ) );
	}
	
	@Test
	public void testconvertToCartesianCoordinates()
	{
		Vec3 test = new Vec3( 10.5, 0.3098, 0.8960 );
		Assert.assertTrue( test.convertToCartesianCoordinates().equals( vec ) );
	}
	
	@Test
	public void testconvertToSphereCoordinates()
	{
		Vec3 test = new Vec3( 10.5, 0.3098, 0.8960 );
		Assert.assertTrue( vec.convertToSphereCoordinates().equals( test ) );
	}
	
}
