package rayCast;

public class Box extends Shape {
	
	Rectangle[] rectangels;
	private Vector p0,p1,p2,p3;
	double t = 0;
	int i = 0; // number of the rectangle
	
	public Box(){}
	public void  setP0(Vector p0){this.p0 = p0;}
	public void  setP1(Vector p0){this.p1 = p0;}
	public void  setP2(Vector p0){this.p2 = p0;}
	public void  setP3(Vector p0){this.p3 = p0;}
	
	public void commit(){
		//p4 = p2 + (p1 - po)
		Vector p4 = p2.add(p1.sub(p0));
		
		//p5 = p3 + (p2 - p0)
		Vector p5 = p3.add(p2.sub(p0));
		
		//p6 = p5 + (p1 - p0)
		
		//p7 = p3 + (p1 - p0)
		Vector p7 = p3.add(p1.sub(p0));
		
		
		Rectangle rec1 = new Rectangle(p0,p1,p2);
		Rectangle rec2 = new Rectangle(p2,p4,p5);
		Rectangle rec3 = new Rectangle(p1,p7,p4);
		Rectangle rec4 = new Rectangle(p3,p5,p7);
		Rectangle rec5 = new Rectangle(p0,p3,p1);
		Rectangle rec6 = new Rectangle(p0,p2,p3);
		
		rectangels = new Rectangle[]{rec1,rec2,rec3,rec4,rec5,rec6};
	}
	
	
	public Box(Vector p0, Vector p1, Vector p2, Vector p3){
		//p4 = p2 + (p1 - po)
		Vector p4 = p2.add(p1.sub(p0));
		
		//p5 = p3 + (p2 - p0)
		Vector p5 = p3.add(p2.sub(p0));
		
		//p6 = p5 + (p1 - p0)
		
		//p7 = p3 + (p1 - p0)
		Vector p7 = p3.add(p1.sub(p0));
		
		
		Rectangle rec1 = new Rectangle(p0,p1,p2);
		Rectangle rec2 = new Rectangle(p2,p4,p5);
		Rectangle rec3 = new Rectangle(p1,p7,p4);
		Rectangle rec4 = new Rectangle(p3,p5,p7);
		Rectangle rec5 = new Rectangle(p0,p3,p1);
		Rectangle rec6 = new Rectangle(p0,p2,p3);
		
		rectangels = new Rectangle[]{rec1,rec2,rec3,rec4,rec5,rec6};
	}


	public double Intersect(Ray ray) {
		double temp = Double.MAX_VALUE;
		int num = 0;
		
		for(int j = 0; j < 6; j++){
			Rectangle rec = rectangels[j];
			double t1 = rec.Intersect(ray);
			
			if(temp > t1 && t1 > 0){
				temp = t1;
				num = j;
			}
		}

		if (temp < Double.MAX_VALUE){
			this.t = temp;
			this.i = num;
			return temp;
		}
		else
			return 0;
	}


	public void getIntersection(Ray ray, double t, Intersection inter) {
		rectangels[i].getIntersection(ray, t, inter);
	}
	
	public Point2D getUV(Vector p){
		return rectangels[i].getUV(p);
	}
	
	public Point2D getTextureSize(){
		return new Point2D(1,1);	
	}

}
