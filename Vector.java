/**
 * 
 */
package rayCast;

/**
 * @author itay
 *
 */
public interface Vector {
	
	/**
	 * 
	 * @return  x,y,z
	 */
	public Point3D getXyz(); 
	/**
	 * 
	 * @return r, theta, phi
	 */
	public Point3D getRtf(); 
	
	/**
	 * set xyz location
	 * @param point
	 */
	public void setXyz(Point3D p);
	
	/**
	 * sets r theta phi location
	 * @param p
	 */
	public void setRtf(Point3D p);
	
	/**
	 * 
	 * @param v 
	 * @return sum of this and v
	 */
	public Vector add(Vector v);
	
	/**
	 * 
	 * @param v 
	 * @return difference of this and v
	 */
	public Vector sub(Vector v);
	
	/**
	 * 
	 * @param v 
	 * @return dot product this and v
	 */
	public double dot(Vector v);
	
	/**
	 * 
	 * @param v 
	 * @return cross of this and v
	 */
	public Vector cross(Vector v);
	
	/**
	 * 
	 * @return a unit vector in the direction of this
	 */
	public Vector norm();
	
	/**
	 * multiply by a scalar
	 * @param s
	 * @return
	 */
	public Vector MultScalar(double s);
	
	/**
	 * get vector size
	 */
	public double getSize();
	
	public String toString();
	
	public void setXyz(double x,double y, double z);
	public Vector Reverse();

}
