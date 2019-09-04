package raytracer.imageCreator.shape.withMaterial.interfaces;

import raytracer.imageCreator.shape.withMaterial.material.Material;

public interface IShapeMaterialMulti
{
	public Material[] getMaterials();

	public Material getMaterial( int index );

	public void setAllMaterials( Material[] material );

	public void setMaterial( int index, Material material );
}
