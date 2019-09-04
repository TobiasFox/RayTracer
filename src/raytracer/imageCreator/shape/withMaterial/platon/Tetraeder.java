package raytracer.imageCreator.shape.withMaterial.platon;

import raytracer.imageCreator.general.Vec3;
import raytracer.imageCreator.shape.BoundingBox;
import raytracer.imageCreator.shape.withMaterial.group.AbstractGroup;
import raytracer.imageCreator.shape.withMaterial.interfaces.IShapeMaterialMulti;
import raytracer.imageCreator.shape.withMaterial.material.Material;
import raytracer.imageCreator.shape.withMaterial.primitives.Triangle;

/**
 * @author Tobias Fox
 *
 */
public class Tetraeder extends AbstractGroup implements IShapeMaterialMulti
{

	private double sideLength;
	private Triangle[] surfaces = new Triangle[4];
	private Material[] materials = new Material[4];

	public Tetraeder( double sideLength, Material front )
	{
		this(sideLength, front, front, front, front);
	}

	public Tetraeder( double sideLength, Material front, Material left, Material right, Material bottom )
	{
		this.sideLength = sideLength;
		materials[0] = front;
		materials[1] = left;
		materials[2] = right;
		materials[3] = bottom;
		createRectangles();
	}

	private void createRectangles()
	{
		Vec3 point1 = new Vec3(0, 0, 0);
		Vec3 point2 = point1.add(sideLength, 0, 0);

		double radiusInnen = sideLength / 6 * Math.sqrt(3);
		double hoehe = sideLength / 3. * Math.sqrt(6);
		Vec3 half = point2.sub(point1).scale(.5);
		Vec3 point3 = half.add(0, 3 * radiusInnen, 0);
		Vec3 point4 = half.add(0, radiusInnen, hoehe);

		surfaces[0] = new Triangle(point1, point3, point2, materials[0]);
		surfaces[1] = new Triangle(point1, point2, point4, materials[1]);
		surfaces[2] = new Triangle(point3, point1, point4, materials[2]);
		surfaces[3] = new Triangle(point2, point3, point4, materials[3]);

		for ( Triangle triangle : surfaces )
			shapes.add(triangle);
	}

	@Override
	public Material[] getMaterials()
	{
		return materials;
	}

	@Override
	public Material getMaterial( int index )
	{
		if ( index >= surfaces.length || index < 0 )
			throw new IllegalArgumentException();

		return surfaces[index].getMaterial(index);
	}

	@Override
	public void setAllMaterials( Material[] material )
	{
		if ( material.length != materials.length || material.length < 0 )
			throw new IllegalArgumentException();

		for ( int i = 0; i < material.length; i++ )
		{
			surfaces[i].setAllMaterials(new Material[] { material[i], material[i] });
			materials[i] = material[i];
		}
	}

	@Override
	public void setMaterial( int index, Material material )
	{
		if ( index >= surfaces.length || index < 0 )
			throw new IllegalArgumentException();
		surfaces[index].setAllMaterials(new Material[] { material, material });
		materials[index] = material;
	}

	@Override
	public BoundingBox getBoundingBox()
	{
		return null;
	}

}
