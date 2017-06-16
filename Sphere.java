/**
 * 
 */
package rayCast;

/**
 * @author itay
 *
 */
public class Sphere extends  Shape {

	/* (non-Javadoc)
	 * @see rayCast.Shape#Intersect(rayCast.Ray)
	 */
	
	private double r;
	private Vector center;
	
   public Sphere(){};
   
   public void setRadius(double r){this.r=r;}
   
   public void setCenter(Vector v){this.center = v;}
	
	public Sphere(double r, Vector v){
	   this.center =v;
	   this.r =r;
		
	}
	
   //overloading
   
	public Sphere(double r, double x,double y,double z){
		Vector v = new BVector();
		v.setXyz(new Point3D(x,y,z));
		this.center =v;
		this.r = r;
	}
	
	
	public double Intersect(Ray ray) {
		
		// double a = *** Not required for 'pure' spheres (of uniform radius) ***
		Vector tmp =  ray.getDirection();
		Vector tmp2 = ray.getStartPoint().sub(center);
		double b = 2* tmp.dot(tmp2);
		
		
		double c = Math.pow(tmp2.getSize(),2) - Math.pow(r, 2);

		double d = b*b - 4*c;

		if (d <= 0)
			return 0;

		d = Math.sqrt(d);
		double t1 = (-b+d)/2.0;
		double t2 = (-b-d)/2.0;

		if (t1 <0 || t2 < 0)
			return 0;

		// Ray is inside if there is only 1 positive root
		// Added for refractive transparency
		if (t1 < 0 && t2 > 0)
		{
			return t2;
		}
		if (t2 < 0 && t1 > 0)
		{
			return t1;
		}

		return (t1 < t2) ? t1 : t2;
	}
	
	public void getIntersection(Ray ray, double t,Intersection inter){
		inter.rayVector = ray.getStartPoint().add(ray.getDirection().MultScalar(t));
		inter.norm = inter.rayVector.sub(center).norm();
						
	}
	
	public Point2D getUV(Vector p){
	/*	Vector rp = p.sub(center);
        double v = Math.acos(rp.getXyz().zf/r);
        double u = Math.acos(rp.getXyz().xr/(r * Math.sin(v)));
        if (rp.getXyz().yt < 0)
            u = -u;
        if (rp.getXyz().zf < 0)
            v = v + Math.PI;
        System.out.println(u + " " + v);
        return new Point2D(u / (2*Math.PI)+0.5, (v / (Math.PI)-0.5)/2);*/
		Vector cent = center.sub(p);
		double u = cent.getRtf().yt/((Math.PI));
		double v = cent.getRtf().zf;
		if (v < 0 )
			v+= Math.PI;
		v/=(Math.PI);
		return new Point2D(u,v);

	}
	
	public  Point2D getTextureSize(){
		return new Point2D(1,1);
	}

}
