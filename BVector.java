package rayCast;

public class BVector implements Vector {

	private Point3D xyz;
	boolean validXyz;
	private Point3D rtf;
	boolean validRtf;
	
	public BVector()
	{
		xyz = new Point3D(0,0,0);
		rtf= new Point3D(0,0,0);
	}
	
	public Vector add(Vector v) {
		Vector u = new BVector();
		Point3D p = v.getXyz();
		double x = p.xr + xyz.xr;
		double y = p.yt + xyz.yt;
		double z = p.zf + xyz.zf;
		
		Point3D p2 = new Point3D(x,y,z);
		u.setXyz(p2);
		return u;
	}

	public Vector cross(Vector v) {
		Point3D p2 = v.getXyz();
		Point3D p1 = this.getXyz();
		
		double x = p1.yt * p2.zf - p1.zf*p2.yt;
		double y =  p1.zf * p2.xr - p1.xr*p2.zf;
		double z =  p1.xr * p2.yt - p1.yt*p2.xr;
		
		Point3D p3 = new Point3D(x,y,z);
		Vector u = new BVector();
		u.setXyz(p3);
		return u;
	}

	public double dot(Vector v) {
		Point3D p1 = v.getXyz();
		Point3D p2 = this.getXyz();
		
		double x = p1.xr * p2.xr;
		double y = p1.yt * p2.yt;
		double z = p1.zf * p2.zf;
		
		return x+y+z;
	}

	public Point3D getRtf() {
		if (validRtf)
			return rtf;
		
		double r =this.getSize();
		double t = Math.acos(xyz.zf/r);
		double f = Math.atan(xyz.yt/xyz.xr);
		Point3D p = new Point3D(r,t,f);
		this.rtf = p;
		validRtf = true;
		return p;
	}

	public Point3D getXyz() {
		if (validXyz)
			return xyz;
		
		double x = rtf.xr*Math.sin(rtf.yt)*Math.cos(rtf.zf);
		double y = rtf.xr*Math.sin(rtf.yt)*Math.sin(rtf.zf);
		double z = rtf.xr*Math.cos(rtf.yt);
		Point3D p = new Point3D(x,y,z);
		this.xyz = p;
		validXyz = true;
		return p;
	}

	public Vector norm() {
		Vector u ;
		u= (this.MultScalar(1.0/this.getSize()));
		return u;
	}

	public void setRtf(Point3D p) {
		rtf = p;
		validXyz = false;
		validRtf = true;

	}

	public void setXyz(Point3D p) {
		xyz = p;
		validXyz = true;
		validRtf = false;
	}
	
	public void setXyz(double x,double y, double z) {
		xyz.xr = x;
		xyz.yt = y;
		xyz.zf = z;
		validXyz = true;
		validRtf = false;

	}

	public Vector Reverse(){
		return this.MultScalar(-1);
	}
	public Vector sub(Vector v) {
		Vector u = new BVector();
		Point3D p = v.getXyz();
		double x =  xyz.xr - p.xr;
		double y = xyz.yt -  p.yt;
		double z = xyz.zf - p.zf ;
		
		Point3D p2 = new Point3D(x,y,z);
		u.setXyz(p2);
		return u;
	}
	
	/**
	 * multiply by a scalar
	 * @param s
	 * @return
	 */
	public Vector MultScalar(double s){
		Point3D p = this.getXyz();
		double x  = p.xr * s;
		double y = p.yt * s;
		double z = p.zf * s;
		
		Point3D p2 = new Point3D(x,y,z);
		Vector u = new BVector();
		u.setXyz(p2);
		return u;
		
	}
	
	/**
	 * get vector size
	 */
	public double getSize(){
		Point3D p = this.getXyz();
		double length;
		length = Math.sqrt(Math.pow(p.xr, 2) +Math.pow(p.yt, 2) + Math.pow(p.zf, 2));
		return length;
		
	}
	
	public String toString(){
		return this.xyz.xr + " " + this.xyz.yt + " " + this.xyz.zf;
	}

}
