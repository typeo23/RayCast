package rayCast;

public class Ray {
	private Vector vector;
	private Vector point;
	public int rec=0;
	
	public Ray(Vector vector, Vector point){
		this.vector = vector.norm();
		this.point = point;
	}
	public Ray(Vector vector, Vector point,int rec){
		this(vector,point);
		this.rec = rec;
	}
		
	public Ray(Camera camera, double  i, double  j,int w,int h){
		double d = camera.getScreenDist();
		double wTheta = camera.getWTheat();
		double hTheta = camera.getHTheta();
		
		Vector forwared = camera.getDirection();
		Vector up = camera.getUpDirection();
		Vector right = forwared.cross(up);
		
		Vector p1 = forwared.MultScalar(d);
		double dtant =d*Math.tan(wTheta);
		double dtanth =d*Math.tan(hTheta);
		p1 =p1.sub(right.MultScalar(dtant));
		p1=p1.add(up.MultScalar(dtanth));
		
		Vector dir = p1.add(right.MultScalar(((double)i/(0.5*(double)w) -0.5 )*2*dtant));
		dir = dir.sub(up.MultScalar(((double)j/(0.5*(double)h) -0.5)*2*dtanth));
		dir = dir.norm();
		
		this.vector = dir;
		this.point = camera.getEye();
	}
	
	public Vector getStartPoint(){
		return this.point;
	}
	
	public Vector getDirection(){
		return this.vector;
	}
}
