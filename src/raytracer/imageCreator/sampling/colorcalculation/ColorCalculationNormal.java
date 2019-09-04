/**
 * 
 */

package raytracer.imageCreator.sampling.colorcalculation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import raytracer.imageCreator.general.Vec3;

/**
 * @author Tobias Fox
 *
 */
class ColorCalculationNormal extends ColorCalculation
{

	private List<Circle> circleList = new ArrayList<>();
	private int green = 0;
	private int blue = 255;

	public ColorCalculationNormal( int width, int height )
	{
		super( width, height );
	}

	@Override
	public Vec3 pixelColor( double xs, double ys )
	{
		Color color = null;
		for ( Circle circle : circleList )
		{
			boolean inside = circle.isInside( xs, ys );
			if ( inside )
				color = circle.getColor();
		}
		if ( color != null )
			return new Vec3( color );
		System.out.println( "ys   " + ys );
		if ( ys > 256 || ys < 0 )
		{
			System.out.println();
		}
		double percent = ys / height;
		green = (int) ( ( 1 - percent ) * 255 );
		blue = (int) ( percent * 255 );
		return new Vec3( 0, green, blue );
	}

	@Override
	public void createRandomCircles( int numberOfCircles )
	{
		circleList.clear();
		for ( int i = 0; i < numberOfCircles; i++ )
		{
			Circle newCircle = new Circle( 480, 270 );
			boolean loop = true;
			while ( loop )
				loop = checkAllCircles( newCircle );
			circleList.add( newCircle );
		}
	}

	private boolean checkAllCircles( Circle newCircle )
	{
		for ( Circle circle : circleList )
			if ( circleCollision( newCircle, circle ) )
			{
				newCircle.generateAttributes();
				return true;
			}
		return false;
	}

	private boolean circleCollision( Circle newCircle, Circle circle )
	{
		double abstandX = Math.abs( newCircle.getX() - circle.getX() ) * Math.abs( newCircle.getX() - circle.getX() );
		double abstandY = Math.abs( newCircle.getX() - circle.getX() ) * Math.abs( newCircle.getX() - circle.getX() );
		double abstand = Math.sqrt( abstandX + abstandY );
		int radiusDifferenz = Math.abs( newCircle.getRadius() - circle.getRadius() );
		int radiusSum = newCircle.getRadius() + circle.getRadius();
		boolean newCircleIsBigger = newCircle.getRadius() >= circle.getRadius();
		if ( sameCenter( abstand ) && newCircleIsBigger )
		{
			// kreis hat gleichen mittelpunkt und radius des neuen kreises ist
			// größer und verdeckt den alten
			return true;
		}
		else if ( nestedCircle( abstand, radiusDifferenz ) && newCircleIsBigger )
		{
			// kreise liegen ineinander und neuer kreis hat einen größeren
			// radius und verdeckt den alten
			return true;
		}
		else if ( twoIntersectionPoints( abstand, radiusDifferenz, radiusSum ) && newCircleIsBigger )
		{
			// Kreise schneiden sich in 2 Punkten, radius des neuen ist
			// größer->verdeckt den alten teilweise
			return true;
		}
		return false;
	}

	private boolean sameCenter( double abstand )
	{
		return abstand == 0;
	}

	private boolean nestedCircle( double abstand, int radiusDifferenz )
	{
		return abstand <= radiusDifferenz;
	}

	private boolean twoIntersectionPoints( double abstand, int radiusDifferenz, int radiusSum )
	{
		return radiusDifferenz < abstand && abstand < radiusSum;
	}

	private boolean rectangleCondition( int size, int x, int y )
	{
		return Math.abs( width / 2 - x ) < size / 2 && Math.abs( height / 2 - y ) < size / 2;
	}
}
