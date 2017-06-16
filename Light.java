package rayCast;

public abstract class Light {
	public Vector position;
	public Rgb Intencity=new Rgb(1,1,1) ;
	/**
	 * 
	 * @param inter
	 * @return intencity at inter.point
	 */
	public abstract Rgb getIntancity(Intersection inter);
	
	/**
	 * 
	 * @param inter
	 * @return  unti vector from the light to intersection point
	 */
	public abstract  Vector getLVector(Intersection inter); 
	
	/**
	 * 
	 * @param inter
	 * @return 0 iff inter.point is shadowd by this light
	 */
	public abstract double isShadowed(Intersection inter);
}
