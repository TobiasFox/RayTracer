package raytracer.imageCreator.shape.withMaterial;

import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.shape.BoundingBox;
import raytracer.imageCreator.shape.withMaterial.group.AbstractGroup;
import raytracer.imageCreator.shape.withMaterial.interfaces.IShapeMaterialMulti;
import raytracer.imageCreator.shape.withMaterial.material.Material;

public class PolySphere extends AbstractGroup implements IShapeMaterialMulti
{

	private Vec3 center;
	private double radius;

	public PolySphere( Vec3 center, double radius )
	{
		this.center = center;
		this.radius = radius;
		createPolySphere();
	}

	private void createPolySphere()
	{
		Vec3 start = center.add(0, radius, 0);
		double singleLevelHeight = (2 * radius) / 5;

		Vec3 center2 = start.sub(0, singleLevelHeight, 0);
		createCap(start, center2);
		start = center.add(0, -radius, 0);
		// createCap(start, direction);
	}

	private void createCap( Vec3 start, Vec3 center2 )
	{
		for ( int i = 0; i < 5; i++ )
		{
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
		return null;
	}

}
