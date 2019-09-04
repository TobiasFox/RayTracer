
package raytracer.imageCreator.shape.withMaterial.group;

import raytracer.imageCreator.general.Mat4;
import raytracer.imageCreator.general.Transformation;
import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.sampling.pinholeCamera.Ray;
import raytracer.imageCreator.shape.BoundingBox;
import raytracer.imageCreator.shape.Shape;
import raytracer.imageCreator.shape.withMaterial.Hit;

public class Group extends AbstractGroup
{
	
	private Transformation transform;
	private BoundingBox boundingBox = new BoundingBox( new Vec3( 0, 0, 0 ), new Vec3( 0, 0, 0 ) );
	private BoundingBox originalBoundingBox = boundingBox;
	
	public Group()
	{
		this( Mat4.identity );
	}
	
	public Group( Shape... shapes )
	{
		this( Mat4.identity );
		addAllShape( true, shapes );
	}
	
	public Group( Mat4 transformation )
	{
		transform = new Transformation( transformation );
	}
	
	public Group( Mat4 transformation, Shape... shapes )
	{
		this( transformation );
		addAllShape( true, shapes );
	}
	
	@Override
	public Hit calculateHit( Ray ray )
	{
		if ( !boundingBox.intersect( ray ) )
		{
			return null;
		}
		
		Ray transformedRay = transform.transformRayWithInvert( ray );
		Hit hit = super.calculateHit( transformedRay );
		if ( hit == null )
		{
			return null;
		}
		return transform.transformHit( hit );
	}
	
	public boolean addShape( Shape shape )
	{
		return addAllShape( false, shape );
	}
	
	protected boolean addShape( boolean saveOriginalBoundingBox, Shape shape )
	{
		boolean result = shapes.add( shape );
		calcBounds( saveOriginalBoundingBox );
		return result;
	}
	
	protected boolean addAllShape( boolean saveOriginalBoundingBox, Shape... shape )
	{
		for ( Shape sh : shape )
		{
			if ( !shapes.add( sh ) )
			{
				calcBounds( saveOriginalBoundingBox );
				return false;
			}
		}
		calcBounds( saveOriginalBoundingBox );
		return true;
	}
	
	public boolean addAllShape( Shape... shape )
	{
		return addAllShape( false, shape );
	}
	
	public boolean removeShape( Shape shape )
	{
		return shapes.remove( shape );
	}
	
	public Mat4 getTransformation()
	{
		return transform.get();
	}
	
	public Mat4 getInvertTransformation()
	{
		return transform.getInvert();
	}
	
	public void resetTransformation()
	{
		transform.set( Mat4.identity );
		boundingBox = originalBoundingBox;
	}
	
	public void setTransformation( Mat4 transformation )
	{
		transform.set( transformation );
		calcBounds();
	}
	
	public void addTransformation( Mat4 transformation )
	{
		transform.add( transformation );
		calcBounds();
	}
	
	private void calcBounds( boolean saveOriginalBoundingBox )
	{
		for ( Shape shape : shapes )
		{
			boundingBox = boundingBox.addBoundingBox( shape.getBoundingBox() );
		}
		if ( saveOriginalBoundingBox )
		{
			originalBoundingBox = boundingBox;
		}
		boundingBox = transform.transformBoundingBox( boundingBox );
	}
	
	private void calcBounds()
	{
		calcBounds( false );
	}
	
	@Override
	public BoundingBox getBoundingBox()
	{
		return boundingBox;
	}
	
}
