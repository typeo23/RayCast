/**
 * 
 */
package rayCast;

/**
 * @author itay
 *
 */
public interface IShape {
	//private Material material;
	
	public double Intersect(Ray ray);
	
	public void getIntersection(Ray ray, double t,Intersection inter);

	public  Point2D getTextureSize();

}
