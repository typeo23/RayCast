/**
 * 
 */
package rayCast;

/**
 * @author itay
 *
 */
public class PointLight extends Light {

	public double kc=1,kl,kq;
	/* (non-Javadoc)
	 * @see rayCast.Light#getIntancity(rayCast.Intersection)
	 */
	public PointLight(){};
	
	public void setKc(double kc){this.kc = kc;}
	public void setKl(double kl){this.kl = kl;}
	public void setKq(double kq){this.kq = kq;}
	
	public void setPos(Vector v){this.position = v;}
	public void setColor(Rgb col){this.Intencity = col;}
	
	public PointLight(Vector pos,Rgb inte){
		Intencity = inte;
		position = pos;
		kc =1;
		kl =0;
		kq=0;
	}
	
	public PointLight(double x,double y,double z,Vector pos,Rgb inte){
		Intencity = inte;
		position = pos;
		kc=x;
		kl=y;
		kq=z;
	}
	public Rgb getIntancity(Intersection inter) {
		
		
		Vector tmp= position.sub(inter.point);//inter.norm.Reverse().add(position);
		double d = tmp.getSize();
		
		double r = Intencity.r / (kc + kl*d + kq*Math.pow(d,2));
		double g = Intencity.g / (kc + kl*d + kq*Math.pow(d,2));
		double b = Intencity.b / (kc + kl*d + kq*Math.pow(d,2));
		
		return new Rgb(r,g,b);
		
		
		
		 
	}
	
	public  double isShadowed(Intersection inter){
		
		Vector dir =this.position.sub(inter.point).norm();
		Vector point = inter.point;
		Ray ray = new Ray(dir,point);

		Intersection inter2 = new Intersection();
		inter2.Intersec(ray,RayTracer.m_scene);
		
		if(inter2.shape  ==null)
			return 1;
		
		return 0;
	}
	
	public   Vector getLVector(Intersection inter){
		return this.position.sub(inter.point).norm();
	}

}
