
package raytracer.imageCreator.shape.withMaterial;

import raytracer.imageCreator.general.Mat4;
import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.shape.Shape;
import raytracer.imageCreator.shape.withMaterial.group.Group;
import raytracer.imageCreator.shape.withMaterial.material.PerfectDiffuseMaterial;
import raytracer.imageCreator.shape.withMaterial.primitives.Sphere;
import raytracer.imageCreator.shape.withMaterial.texture.ConstantTexture;

/**
 * @author Tobias Fox
 *
 */
public class HouseOfCircles extends Group
{
	
	private Vec3 dimensions;
	private int levels;
	private double roofHeight;
	private Vec3 start;
	private double radius;
	private Vec3 colorOne;
	private Vec3 colorTwo;
	
	private double limitationLeft = .2;
	private double limitationRight = .8;
	
	public HouseOfCircles( Vec3 dimensions, int levels, double roofHeight, Vec3 start, double radius, Vec3 colorOne )
	{
		this( dimensions, levels, roofHeight, start, radius, colorOne, Mat4.identity );
	}
	
	public HouseOfCircles( Vec3 dimensions, int levels, double roofHeight, Vec3 start, double radius, Vec3 colorOne,
			Vec3 colorTwo )
	{
		this( dimensions, levels, roofHeight, start, radius, colorOne, colorTwo, Mat4.identity );
	}
	
	public HouseOfCircles( Vec3 dimensions, int levels, double roofHeight, Vec3 start, double radius, Vec3 colorOne,
			Mat4 transformation )
	{
		this.dimensions = dimensions;
		this.levels = levels;
		this.roofHeight = roofHeight;
		this.start = start;
		this.radius = radius;
		this.colorOne = colorOne;
		setTransformation( transformation );
	}
	
	public HouseOfCircles( Vec3 dimensions, int levels, double roofHeight, Vec3 start, double radius, Vec3 colorOne,
			Vec3 colorTwo, Mat4 transformation )
	{
		this.dimensions = dimensions;
		this.levels = levels;
		this.roofHeight = roofHeight;
		this.start = start;
		this.radius = radius;
		this.colorOne = colorOne;
		this.colorTwo = colorTwo;
		setTransformation( transformation );
	}
	
	public HouseOfCircles createHouse()
	{
		start = start.sub( 0, radius, 0 );
		Group basis = createBasis();
		Group roof = createRoof();
		addAllShape( true, basis, roof );
		
		return this;
	}
	
	public HouseOfCircles createRadiantHouse()
	{
		if ( colorTwo == null )
		{
			return createHouse();
		}
		
		start = start.sub( 0, radius, 0 );
		Vec3 colorSection = dimensions.y != 0. ? colorOne.scale( ( roofHeight / dimensions.y ) )
				.add( colorTwo.scale( 1 - ( roofHeight / dimensions.y ) ) ) : colorTwo;
		Group basis = createBasis( colorOne, colorSection );
		Group roof = createRoof( colorSection, colorTwo );
		if ( basis != null )
		{
			addShape( true, basis );
		}
		if ( roof != null )
		{
			addShape( true, roof );
		}
		
		return this;
	}
	
	private Group createRoof( Vec3 colorOne, Vec3 colorTwo )
	{
		if ( roofHeight == 0 )
		{
			return null;
		}
		
		Vec3 e = start.add( 0, dimensions.y - roofHeight, 0 );
		Vec3 f = start.add( dimensions.x, dimensions.y - roofHeight, 0 );
		Vec3 g = start.add( dimensions.x, dimensions.y - roofHeight, -dimensions.z );
		Vec3 h = start.add( 0, dimensions.y - roofHeight, -dimensions.z );
		Vec3 i = start.add( 0, dimensions.y, -dimensions.z / 2 );
		Vec3 j = start.add( dimensions.x, dimensions.y, -dimensions.z / 2 );
		int verticalCount = radius != 0. ? (int) Math.floor( ( e.sub( i ).length / radius ) * 1.5 ) : 5;
		int horizontalCount2 = radius != 0. ? (int) ( Math.floor( Math.abs( dimensions.x ) / radius ) * 1.5 ) : 5;
		
		// rechts
		Group g9 = createLine( g, j, colorOne, colorTwo, verticalCount );
		Group g10 = createLine( f, j, colorOne, colorTwo, verticalCount );
		
		// links
		Group g11 = createLine( h, i, colorOne, colorTwo, verticalCount );
		Group g12 = createLine( e, i, colorOne, colorTwo, verticalCount );
		
		Group g13 = createLine( i, j, colorTwo, horizontalCount2 );
		
		return new Group( g9, g10, g11, g12, g13 );
	}
	
	private Group createBasis( Vec3 colorOne, Vec3 colorTwo )
	{
		if ( levels == 0 )
		{
			return null;
		}
		Group gr = new Group();
		Vec3 a = start;
		Vec3 b = a.add( dimensions.x, 0, 0 );
		Vec3 c = a.add( dimensions.x, 0, -dimensions.z );
		Vec3 d = a.add( 0, 0, -dimensions.z );
		Vec3 colOne = colorOne;
		Vec3 colorSection;
		double singleLevelHeight = ( dimensions.y - roofHeight ) / levels;
		int verticalCount = radius != 0. ? (int) Math.floor( ( singleLevelHeight / radius ) * 1.5 ) : 5;
		int horizontalCount1 = radius != 0. ? (int) ( Math.floor( Math.abs( dimensions.z ) / radius ) * 1.5 ) : 5;
		int horizontalCount2 = radius != 0. ? (int) Math.floor( ( Math.abs( dimensions.x ) / radius ) * 1.5 ) : 5;
		
		for ( int i = 1; i < ( levels + 1 ); i++ )
		{
			Vec3 e = a.add( 0, singleLevelHeight, 0 );
			Vec3 f = a.add( dimensions.x, singleLevelHeight, 0 );
			Vec3 g = a.add( dimensions.x, singleLevelHeight, -dimensions.z );
			Vec3 h = a.add( 0, singleLevelHeight, -dimensions.z );
			
			colorSection = levels != 0.
					? colorOne.scale( 1 - ( i / (double) levels ) ).add( colorTwo.scale( i / (double) levels ) )
					: colorTwo;
			
			// vertical
			Group g1 = createLine( a, e, colOne, colorSection, verticalCount );
			Group g2 = createLine( b, f, colOne, colorSection, verticalCount );
			Group g3 = createLine( d, h, colOne, colorSection, verticalCount );
			Group g4 = createLine( c, g, colOne, colorSection, verticalCount );
			
			// horizontal
			Group g6 = createLine( e, h, colorSection, horizontalCount1 );
			Group g5 = createLine( h, g, colorSection, horizontalCount2 );
			Group g7 = createLine( g, f, colorSection, horizontalCount1 );
			Group g8 = createLine( f, e, colorSection, horizontalCount2 );
			
			gr.addAllShape( g1, g2, g3, g4, g5, g6, g7, g8 );
			a = e;
			b = f;
			d = h;
			c = g;
			colOne = colorSection;
		}
		return gr;
	}
	
	private Group createRoof()
	{
		if ( roofHeight == 0 )
		{
			return null;
		}
		
		Vec3 e = start.add( 0, dimensions.y - roofHeight, 0 );
		Vec3 f = start.add( dimensions.x, dimensions.y - roofHeight, 0 );
		Vec3 g = start.add( dimensions.x, dimensions.y - roofHeight, -dimensions.z );
		Vec3 h = start.add( 0, dimensions.y - roofHeight, -dimensions.z );
		Vec3 i = start.add( 0, dimensions.y, -dimensions.z / 2 );
		Vec3 j = start.add( dimensions.x, dimensions.y, -dimensions.z / 2 );
		int verticalCount = radius != 0. ? (int) Math.floor( ( e.sub( i ).length / radius ) * 1.5 ) : 5;
		int horizontalCount2 = radius != 0. ? (int) ( Math.floor( Math.abs( dimensions.x ) / radius ) * 1.5 ) : 5;
		
		// rechts
		Group g9 = createLine( g, j, colorOne, verticalCount );
		Group g10 = createLine( f, j, colorOne, verticalCount );
		
		// links
		Group g11 = createLine( h, i, colorOne, verticalCount );
		Group g12 = createLine( e, i, colorOne, verticalCount );
		
		Group g13 = createLine( i, j, colorOne, horizontalCount2 );
		
		return new Group( g9, g10, g11, g12, g13 );
	}
	
	private Group createBasis()
	{
		if ( levels == 0 )
		{
			return null;
		}
		Group gr = new Group();
		Vec3 a = start;
		Vec3 b = a.add( dimensions.x, 0, 0 );
		Vec3 c = a.add( dimensions.x, 0, -dimensions.z );
		Vec3 d = a.add( 0, 0, -dimensions.z );
		double singleLevelHeight = ( dimensions.y - roofHeight ) / levels;
		int verticalCount = radius != 0. ? (int) Math.floor( ( singleLevelHeight / radius ) * 1.5 ) : 5;
		int horizontalCount1 = radius != 0. ? (int) ( Math.floor( Math.abs( dimensions.z ) / radius ) * 1.5 ) : 5;
		int horizontalCount2 = radius != 0. ? (int) Math.floor( ( Math.abs( dimensions.x ) / radius ) * 1.5 ) : 5;
		
		for ( int i = 1; i < ( levels + 1 ); i++ )
		{
			Vec3 e = a.add( 0, singleLevelHeight, 0 );
			Vec3 f = a.add( dimensions.x, singleLevelHeight, 0 );
			Vec3 g = a.add( dimensions.x, singleLevelHeight, -dimensions.z );
			Vec3 h = a.add( 0, singleLevelHeight, -dimensions.z );
			
			// vertical
			Group g1 = createLine( a, e, colorOne, verticalCount );
			Group g2 = createLine( b, f, colorOne, verticalCount );
			Group g3 = createLine( d, h, colorOne, verticalCount );
			Group g4 = createLine( c, g, colorOne, verticalCount );
			
			// horizontal
			Group g6 = createLine( e, h, colorOne, horizontalCount1 );
			Group g5 = createLine( h, g, colorOne, horizontalCount2 );
			Group g7 = createLine( g, f, colorOne, horizontalCount1 );
			Group g8 = createLine( f, e, colorOne, horizontalCount2 );
			
			gr.addAllShape( g1, g2, g3, g4, g5, g6, g7, g8 );
			a = e;
			b = f;
			d = h;
			c = g;
		}
		return gr;
	}
	
	private Group createLine( Vec3 start, Vec3 end, Vec3 colorOne, Vec3 colorTwo, int count )
	{
		Group gr = new Group();
		
		Vec3 center;
		Vec3 color;
		PerfectDiffuseMaterial material;
		for ( double i = 0.; i < 1.; i += ( 1. / count ) )
		{
			center = start.scale( 1 - i ).add( end.scale( i ) );
			color = colorOne.scale( 1 - i ).add( colorTwo.scale( i ) );
			material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
			Shape sphere9 = new Sphere( center,
					( i < limitationLeft ) || ( i > limitationRight ) ? radius * .5 : radius, material );
			// i < ((count / 2.) /count) ?radius *i : radius *(1. - i)
			gr.addShape( sphere9 );
		}
		return gr;
	}
	
	private Group createLine( Vec3 start, Vec3 end, Vec3 color, int count )
	{
		Group gr = new Group();
		
		Vec3 center;
		PerfectDiffuseMaterial material;
		for ( double i = 0.; i < 1.; i += ( 1. / count ) )
		{
			Vec3 direction = end.sub( start ).normalize();
			Vec3 a = start.scale( 1 - i ).add( end.scale( i ) );
			center = new Vec3( direction.x == 0 ? start.x : a.x, direction.y == 0 ? start.y : a.y,
					direction.z == 0 ? start.z : a.z );
			material = new PerfectDiffuseMaterial( new ConstantTexture( color ) );
			Shape sphere9 = new Sphere( center,
					( i < limitationLeft ) || ( i > limitationRight ) ? radius * .5 : radius, material );
			gr.addShape( sphere9 );
		}
		
		return gr;
	}
	
	public double getLimitationLeft()
	{
		return limitationLeft;
	}
	
	public void setLimitationLeft( double limitationLeft )
	{
		this.limitationLeft = limitationLeft;
	}
	
	public double getLimitationRight()
	{
		return limitationRight;
	}
	
	public void setLimitationRight( double limitationRight )
	{
		this.limitationRight = limitationRight;
	}
	
}
