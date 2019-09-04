/**
 * 
 */
package raytracer.imageCreator.sampling.colorcalculation;

import raytracer.imageCreator.enums.PixelColorCalculations;

/**
 * @author Tobias
 *
 */
public class ColorCalculationFactory
{

	public static ColorCalculation create( int width, int height, double angle,
			PixelColorCalculations variation )
	{
		switch ( variation )
		{
			case Normal:
				return new ColorCalculationNormal(width, height);
			case RayTrace:
				return new ColorCalculationRayTracer(width, height, angle);
			case MonteCarloRaytrace:
				return new ColorCalculationMonteCarloRay(width, height, angle);

			default:
				return new ColorCalculationMonteCarloRay(width, height, angle);
		}

	}

}
