package raytracer.imageCreator.shape.withMaterial.material;

public enum Density
{
	AIR(1.0), WATER(1.3), GLAS(1.5), DIAMANT(2.4);

	private final double value;

	Density( double value )
	{
		this.value = value;
	}

	public double value()
	{
		return value;
	}
}
