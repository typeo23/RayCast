package rayCast;
import java.util.*;

public class Intersection {
	public Vector rayVector;
	public Vector norm;
	public Shape shape;
	public double t;
	public Vector point;
	public Scene scene;
	public Ray ray;
	
	public void Intersec(Ray ray, Scene scn){
		
		this.scene = scn;
		this.ray = ray;
		Iterator<Shape> iter = scn.shapes.iterator();
		Shape tempShape = null;
		double temp1 = 0;

		
		while(iter.hasNext()){
			Shape s = iter.next();
			double temp2 = s.Intersect(ray);
			
			if ((temp2 > 0) && (temp2 <= temp1 || temp1 == 0)){
				temp1 = temp2;
				tempShape = s;
				
				
			}
		}
		
		t = temp1;
		
		if(t == 0)
			return;
		else{		
	
			tempShape.getIntersection(ray, t, this);
			point  = ray.getStartPoint();
			point = point.add(ray.getDirection().MultScalar(t-1e-5));

			this.shape = tempShape;
		}
		
	}

}
