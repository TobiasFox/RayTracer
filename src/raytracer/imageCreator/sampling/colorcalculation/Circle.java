/**
 * 
 */

package raytracer.imageCreator.sampling.colorcalculation;

import java.awt.Color;
import raytracer.imageCreator.general.Random;

/**
 * @author Tobias Fox
 *
 */
class Circle
{
	
	private int width;
	private int height;
	private int x;
	private int y;
	private int radius;
	private int red;
	private int green;
	private int blue;
	private Color color;
	
	public Circle( int width, int height )
	{
		this.width = width;
		this.height = height;
		generateAttributes();
		red = (int) ( Random.random() * 255 );
		green = (int) ( Random.random() * 255 );
		blue = (int) ( Random.random() * 255 );
		setColor( new Color( red, green, blue ) );
	}
	
	public Circle( int width, int height, int x, int y, int radius, int red, int green, int blue )
	{
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.red = red;
		this.green = green;
		this.blue = blue;
		setColor( new Color( red, green, blue ) );
	}
	
	public void generateAttributes()
	{
		radius = (int) ( ( Random.random() * 50 ) + 10 );
		x = (int) ( ( Random.random() * ( width - ( 2 * radius ) ) ) + radius );
		y = (int) ( ( Random.random() * ( height - ( 2 * radius ) ) ) + radius );
	}
	
	public boolean isInside( double xs, double ys )
	{
		double range = calculateRange( xs, ys );
		if ( pixelInsideOfCircle( range ) )
		{
			return true;
		}
		return false;
	}
	
	public int getX()
	{
		return x;
	}
	
	public void setX( int x )
	{
		this.x = x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setY( int y )
	{
		this.y = y;
	}
	
	public int getRadius()
	{
		return radius;
	}
	
	public void setRadius( int radius )
	{
		this.radius = radius;
	}
	
	public int getRed()
	{
		return red;
	}
	
	public void setRed( int red )
	{
		this.red = red;
		setColor( new Color( red, green, blue ) );
	}
	
	public int getGreen()
	{
		return green;
	}
	
	public void setGreen( int green )
	{
		this.green = green;
		setColor( new Color( red, green, blue ) );
	}
	
	public int getBlue()
	{
		return blue;
	}
	
	public void setBlue( int blue )
	{
		this.blue = blue;
		setColor( new Color( red, green, blue ) );
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor( Color color )
	{
		this.color = color;
	}
	
	private boolean pixelInsideOfCircle( double range )
	{
		return range <= radius;
	}
	
	private double calculateRange( double xs, double ys )
	{
		return Math.sqrt( ( ( xs - x ) * ( xs - x ) ) + ( ( ys - y ) * ( ys - y ) ) );
	}
}