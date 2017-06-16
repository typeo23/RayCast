package rayCast;

import org.eclipse.swt.graphics.GC;

public class Tests {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    Point3D p1 = new Point3D(1,1,1);
	    Point3D p2 = new Point3D(10,Math.PI,2*Math.PI);
	    
	    Vector v1 = new BVector();
	    Vector v2 = new BVector();
	    
	    v1.setXyz(new Point3D(0,0,-1));
	    v2.setXyz(new Point3D(20,1,0));
	    Point3D p = v2.getXyz();
	    /*System.out.println("v2       "+v2);
	    System.out.println("v1        "+ v1);
	    System.out.println("v2 + v1  "+v2.add(v1));
	    System.out.println("v2       "+v2.sub(v1));
	    */
	    
	    Point3D p6 = v2.getXyz();
	    //System.out.println(v2);
	    
	   
		Vector eye = new BVector();
	    eye.setXyz(new Point3D(0,0,0));
	    Vector look = new BVector();
	    look.setXyz(new Point3D(0,0,-1));
		Vector up = new BVector();
		up.setXyz(new Point3D(0,1,0));
		
		Camera cam = new Camera(eye,look,up,1,1,1);
		Vector sc = new BVector();
		sc.setXyz(new Point3D(0,0,-4));
		Shape sp = new Sphere(1,sc);
		
		Scene scene = new Scene();
		scene.shapes.add(sp);
		
		Intersection inter = new Intersection();
		
		Ray ray = new Ray(cam,50,50,100,100);
		
		System.out.println(ray.getDirection());
		System.out.println(ray.getStartPoint());
		System.out.println(cam.getDirection().cross(cam.getUpDirection()));
	}

}
