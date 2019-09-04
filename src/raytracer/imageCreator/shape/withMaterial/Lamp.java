/**
 * 
 */

package raytracer.imageCreator.shape.withMaterial;

import raytracer.imageCreator.general.Mat4;
import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.shape.BoundingBox;
import raytracer.imageCreator.shape.Shape;
import raytracer.imageCreator.shape.withMaterial.group.AbstractGroup;
import raytracer.imageCreator.shape.withMaterial.group.Group;
import raytracer.imageCreator.shape.withMaterial.interfaces.IShapeMaterialMulti;
import raytracer.imageCreator.shape.withMaterial.material.Material;
import raytracer.imageCreator.shape.withMaterial.material.PerfectDiffuseMaterial;
import raytracer.imageCreator.shape.withMaterial.material.ReflectiveMaterial;
import raytracer.imageCreator.shape.withMaterial.primitives.Sphere;
import raytracer.imageCreator.shape.withMaterial.primitives.Triangle;
import raytracer.imageCreator.shape.withMaterial.texture.ConstantTexture;

/**
 * @author Tobias Fox
 *
 */
public class Lamp extends AbstractGroup implements IShapeMaterialMulti
{
	
	private static final Vec3[] POINTS = new Vec3[] {
			new Vec3( 0, 0, 0 ), new Vec3( 4, 0, 0 ), new Vec3( 4, 0, -4 ), new Vec3( 0, 0, -4 )
	};
	private static final Vec3 TOP = new Vec3( 2, 4, -2 );
	private BoundingBox boundingBox = new BoundingBox( new Vec3( 0, 0, 0 ), new Vec3( 0, 0, 0 ) );
	
	public Lamp( Vec3 colorFront, ReflectiveMaterial reflective, Vec3 zylinderColor )
	{
		
		for ( int i = 0; i < POINTS.length; i++ )
		{
			Triangle t = new Triangle( POINTS[i], POINTS[( i + 1 ) % POINTS.length], TOP,
					new PerfectDiffuseMaterial( new ConstantTexture( colorFront ) ), reflective );
			shapes.add( t );
		}
		
		shapes.add( new Group( Mat4.translate( 2, 1.2, -2 ), new Sphere( 1.2, reflective ) ) );
		
		double height = 3;
		shapes.add( new Group( Mat4.translate( TOP.add( 0, height / 2.05, 0 ) ), new Zylinder( POINTS[0], .3, height,
				new PerfectDiffuseMaterial( new ConstantTexture( zylinderColor ) ) ) ) );
		
		for ( Shape shape : shapes )
		{
			boundingBox = boundingBox.addBoundingBox( shape.getBoundingBox() );
		}
	}
	
	@Override
	public Material[] getMaterials()
	{
		return null;
	}
	
	@Override
	public Material getMaterial( int index )
	{
		return null;
	}
	
	@Override
	public void setAllMaterials( Material[] material )
	{
	}
	
	@Override
	public void setMaterial( int index, Material material )
	{
	}
	
	@Override
	public BoundingBox getBoundingBox()
	{
		return boundingBox;
	}
	
}
