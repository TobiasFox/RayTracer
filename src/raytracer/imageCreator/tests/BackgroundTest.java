package raytracer.imageCreator.tests;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.withColor.Background;

public class BackgroundTest
{
	Background bg;
	Vec3 origin = new Vec3(0, 0, 0);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
		bg = new Background();
	}

	@Test
	public void testCalculateHit()
	{
		Vec3 pos = bg.calculateHit(new Ray(origin, new Vec3(5, 2, 9), 0, Double.POSITIVE_INFINITY)).position;
		if ( pos.x != Double.POSITIVE_INFINITY && pos.y != Double.POSITIVE_INFINITY
				&& pos.z != Double.POSITIVE_INFINITY )
			fail("nicht übereinstimmend");
	}

	@Test
	public void testCalculateHit1neg()
	{
		Vec3 pos = bg.calculateHit(new Ray(origin, new Vec3(5, -2, 9), 0, Double.POSITIVE_INFINITY)).position;
		if ( pos.x != Double.POSITIVE_INFINITY && pos.y != Double.NEGATIVE_INFINITY
				&& pos.z != Double.POSITIVE_INFINITY )
			fail("nicht übereinstimmend");
	}

	@Test
	public void testCalculateHit2neg()
	{
		Vec3 pos = bg.calculateHit(new Ray(origin, new Vec3(5, -2, -9), 0, Double.POSITIVE_INFINITY)).position;
		if ( pos.x != Double.POSITIVE_INFINITY && pos.y != Double.NEGATIVE_INFINITY
				&& pos.z != Double.NEGATIVE_INFINITY )
			fail("nicht übereinstimmend");
	}

	@Test
	public void testCalculateHit3neg()
	{
		Vec3 pos = bg.calculateHit(new Ray(origin, new Vec3(-5, -2, -9), 0, Double.POSITIVE_INFINITY)).position;
		if ( pos.x != Double.NEGATIVE_INFINITY && pos.y != Double.NEGATIVE_INFINITY
				&& pos.z != Double.NEGATIVE_INFINITY )
			fail("nicht übereinstimmend");
	}

}
