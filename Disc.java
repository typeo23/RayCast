package rayCast;

public class Disc extends Shape {

	private Vector center;
	private Vector normal;
	private double radius;
	
	double t = 0;
	
	public Disc(){}
	public void setCenter(Vector center){this.center = center;}
	public void setNormal(Vector normal){this.normal = normal.norm();}
	public void setRadius(double d){this.radius = d;}
	public Disc(Vector center, Vector normal, double radius){
		this.center = center;
		this.normal = normal.norm();
		this.radius = radius;
	} 
	
	//return the point intersection between the ray and the plane
	private Vector findPointInPlane(Ray ray){	
		Vector start = ray.getStartPoint();
		Vector dir = ray.getDirection();
		
		//d is from the plane equation
		double d = -1*normal.dot(center);
		
		this.t = (-1) * (start.dot(normal) + d) / (dir.dot(normal));
		
		if (t < 0)
			t = 0;
		
		//start + dir*t
		return (dir.MultScalar(t).add(start));
	}
	
	
	public double Intersect(Ray ray) {
		double distance = findPointInPlane(ray).sub(center).getSize();
		
		if(t > 0)
			//if (|point - center| < radius) then the point in the disc
			if(distance <= radius)
				return t;
		
		return 0;
	}


	public void getIntersection(Ray ray, double t, Intersection inter) {
		inter.rayVector = ray.getStartPoint().add(ray.getDirection().MultScalar(t));
		inter.norm = normal;
	}
	
	@Override
	public Point2D getTextureSize() {
		return new Point2D(1,1);
	}

	@Override
	public Point2D getUV(Vector p) {
		
        double v = p.sub(center).getSize();

        double k = 0;
		Vector konst = new BVector();
		boolean found = false;
		
		double dirx = normal.getXyz().xr;
		double diry = normal.getXyz().yt;
		double dirz = normal.getXyz().zf;
		
		if(dirx == 0){
			konst.setXyz(1,0,0);
			found = true;
		}
			
		if(diry == 0){
			konst.setXyz(0,1,0);
			found = true;
		}
		
		if(dirz == 0){
			konst.setXyz(0,0,1);
			found = true;
		}
			
		if(!found){
			//(1,k,1) * (dir) = 0
			k = (-1)*(dirx +dirz)/diry;
			konst.setXyz(1,k,1);
			konst = konst.norm();
			found = true;
		}
		
		Vector konstPoint = center.add(konst.MultScalar(radius));
		Vector konstPoint2 = center.add(konst.MultScalar(-1*radius));
		
		Vector vp = p.sub(center).norm();
				
		Vector v1 = p.sub(konstPoint);
		Vector v2 = p.sub(konstPoint2);

		double u = Math.acos(vp.dot(konst));
		
		if((normal.add(v1.cross(v2).norm())).getSize() < 1)
			u = 2*Math.PI - u;
		
		
		return new Point2D(v/radius,u/(2*Math.PI));

	}

}
