package rayCast;

public class Rectangle extends Shape {
	
	private Vector p0;
	private Vector p1;
	private Vector p2;
	private Vector p3;
	
	private double t=0;
	
	public Rectangle() {};
	
	public void setP0(Vector p0){
		this.p0 = p0;
	}
	
	public void setP1(Vector p1){
		this.p1 = p1;
	}
	
	public void setP2(Vector p2){
		this.p2 = p2;
	}
	
	public void commit(){this.p3 = p2.add(p1.sub(p0));}
	public Rectangle(Vector p0,Vector p1, Vector p2){
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
		
		//p3 = p2 + (p1 - p0)
		this.p3 = p2.add(p1.sub(p0));
	}
	
	//Overload
	public Rectangle(Vector p0,Vector p1, Vector p2, Vector p3){
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	//return normal of the plane of the rectangle
	private Vector findNormal(){
		Vector v1 = p1.sub(p0);
		Vector v2 = p2.sub(p0);
		
		Vector normal = v2.cross(v1).norm();
		
		return normal;	
	}
	
	//return the point intersection between the ray and the plane
	private Vector findPointInPlane(Ray ray){
		Vector zer = new BVector();
		zer.setXyz(new Point3D(0,0,0));
				
		Vector start = ray.getStartPoint();
		Vector dir = zer.sub(ray.getDirection());
		Vector normal = this.findNormal();
		
		//p1 point on the rectangle
		//d is from the plane equation
		double d = -1*normal.dot(p1);
		
		this.t = (-1) * (start.dot(normal) + d) / (dir.dot(normal));
		
		//start + dir*t
		return (dir.MultScalar(t).add(start));
	}
	
	//whether p1 and p2 are in the same side of ab
	private boolean SameSide(Vector p1, Vector p2, Vector a, Vector b){
		//(b-a) X (p1-a)
	    Vector cp1 = b.sub(a).cross(p1.sub(a));
	    //(b-a) X (p2-a)
	    Vector cp2 = b.sub(a).cross(p2.sub(a));
	    
	    if (cp1.dot(cp2) >= 0)
	    	return true;
	    else 
	    	return false;
	}

	
	private boolean checkPointInsideTriangle(Vector p, Vector a, Vector b, Vector c){
	    if (SameSide(p,a,b,c) && SameSide(p,b, a,c) && SameSide(p,c,a,b))
	    	return true;
	    else 
	    	return false;
	}
		
	
	public double Intersect(Ray ray) {
		Vector PointInPlane = findPointInPlane(ray);
		
		if(checkPointInsideTriangle(PointInPlane,p0,p1,p3) ||
				checkPointInsideTriangle(PointInPlane,p0,p3,p2))
			if (t< 0)
			   return (-1*t);
		
		return 0;
	}


	public void getIntersection(Ray ray, double t, Intersection inter) {
		inter.rayVector = ray.getStartPoint().add(ray.getDirection().MultScalar(t));
		Vector zer = new BVector();
		zer.setXyz(new Point3D(0,0,0));
		inter.norm = zer.sub(findNormal());

	}
	
	public Point2D getUV(Vector p){
		Vector rp = p.sub(p0);
		Vector v1 = p1.sub(p0).norm();
		Vector v2 = p2.sub(p0).norm();
		
		return new Point2D(rp.dot(v1),rp.dot(v2));
	}
	
	public Point2D getTextureSize(){
		
		return new Point2D(p1.sub(p0).getSize()*2,p2.sub(p0).getSize()*2);	
	}

}
