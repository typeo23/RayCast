/**
 * 
 */
package rayCast;

/**
 * @author itay
 *
 */
public class DirectionalLight extends Light {

	/* (non-Javadoc)
	 * @see rayCast.Light#getIntancity(rayCast.Intersection)
	 */

	
	private Vector direction;
	
	public DirectionalLight(){}
	
	public void setDirection(Vector v){direction =v.norm();}
	public void setIntencity(Rgb r){this.Intencity = r;}
	
	public DirectionalLight(Vector direction,Rgb intencity){
		this.direction  = direction.norm();
		this.Intencity = intencity;
	}
	public Rgb getIntancity(Intersection inter) {
		double fac = inter.norm.dot(direction.Reverse());
		if (fac < 0)
			return new Rgb(0,0,0);
		
		double r = Intencity.r*fac;
		double g = Intencity.g*fac;
		double b = Intencity.b*fac;
		
		return new Rgb(r,g,b);
	}

	/* (non-Javadoc)
	 * @see rayCast.Light#getLVector(rayCast.Intersection)
	 */
	@Override
	public Vector getLVector(Intersection inter) {
		return direction.Reverse();
	}

	public  double isShadowed(Intersection inter){
		Vector dir = direction.Reverse().norm();
		Vector point = inter.point;
		Ray ray = new Ray(dir,point);
		
		Intersection inter2 = new Intersection();
		inter2.Intersec(ray, inter.scene);
		
		if(inter2.shape  ==null)
			return 1;
		return 0;
		
	
	}
}
