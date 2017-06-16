package rayCast;



public class Cylinder extends Shape {
	
	private Vector start;
	private Vector dir;
	private double length;
	private double radius;
	
	public Cylinder(){};
	public void setStart(Vector start){this.start = start;}
	public void setDir(Vector dir){this.dir = dir.norm();}
	public void setLength(double d){this.length = d;}
	public void setRadius(double d){this.radius =d;}
	
	public Cylinder(Vector start,Vector dir,double length,double radius){
		this.start = start;
		this.dir = dir;
		this.length = length;
		this.radius = radius;
		
		//normalize the direction vector
		this.dir = this.dir.norm();
	}
	
	//check whether the ray intersect given cylinder
	// if yes returns t, else returns 0.
	public double Intersect(Ray ray){
		Vector P = ray.getStartPoint();
		Vector V = ray.getDirection();
		
		Vector X = (P.sub(start)).cross(dir);
		Vector Y = V.cross(dir);
		
		double a = Y.dot(Y);
		double b = 2*(X.dot(Y));
		double c = X.dot(X) - (radius*radius);
		
		double delta = b*b - 4*a*c;
		
		if(delta < 0)
			return 0;
		else{
			delta = Math.sqrt(delta);
			
			double t1 = (double)(-b + delta)/(double)(2*a);
			double t2 = (double)(-b - delta)/(double)(2*a);
			
			double tmin = t1;
			double tmax = t1;
			
			if(t2 < t1)
				tmin = t2;
			else
				tmax = t2;
			
			t1 = checkLength(tmin, ray);
			t2 = checkLength(tmax, ray);
			
			if(t1 > 0)
				return t1;
			if(t2 > 0)
				return t2;
			
			return 0;
		}
	}
	
	private Vector findNormal(Vector point, Ray ray){
		
		double k = point.sub(start).dot(dir);
		Vector C = start.add(dir.MultScalar(k));
		Vector normal = point.sub(C).norm();
		
		if(normal.dot(ray.getDirection()) > 0)
			return normal.Reverse();
		else
			return normal;
	}

	//check whether the point between start and "start + length"
	private double checkLength(double t, Ray ray) {
		
		if(t == 0)
			return 0;
		
		//point = P + t*V
		Vector point = (ray.getStartPoint()).add(ray.getDirection().MultScalar(t));
		
		//B = start + dir*length
		Vector B = start.add(dir.MultScalar(length));
		
		Vector a = (point.sub(start)).norm();
		Vector b = (point.sub(B)).norm();
		Vector c = B.sub(start).norm();
		
		if(a.dot(c) >=0 && b.dot(c.Reverse()) >= 0)
			return t;
		else
			return 0;
	}

	@Override
	public void getIntersection(Ray ray, double t, Intersection inter) {
		inter.rayVector = ray.getStartPoint().add(ray.getDirection().MultScalar(t));
		inter.norm = findNormal(inter.rayVector,ray);
	}
	

	@Override
	public Point2D getTextureSize() {
		//return new Point2D(2*Math.PI*radius,length);
		return new Point2D(1,1);
	}

	@Override
	public Point2D getUV(Vector p) {
		
		double v = p.sub(start).dot(dir);
		
		Vector center = start.add(dir.MultScalar(Math.abs(v)));
				
		double dirx = dir.getXyz().xr;
		double diry = dir.getXyz().yt;
		double dirz = dir.getXyz().zf;
		
		double k = 0;
		Vector konst = new BVector();
		boolean found = false;
		
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
		
		if((dir.add(v1.cross(v2).norm())).getSize() < 1)
			u = 2*Math.PI - u;

		return new Point2D(v/length,u/(2*Math.PI));
	}

}
