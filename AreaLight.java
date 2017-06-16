package rayCast;

public class AreaLight {
	
	public Vector p1;
	public Vector p2;
	public Vector p3;
	public Rgb i;
	public int grid;
	
	public AreaLight(){
		
	}
	
	public void setP1(Vector p1){
		this.p1 = p1;		
	}
	
	public void setP2(Vector p2){
		this.p2 = p2;		
	}
	
	public void setP3(Vector p3){
		this.p3 = p3;		
	}
	
	public void setI(Rgb i){
		this.i = i;
	}
	
	public void setGridSize(int grid){
		this.grid = grid;
	}
	
	public void commit(Scene scene){
		Vector v1 = p2.sub(p1);
		Vector v2 = p3.sub(p2);
		
		Rgb pointI = i.DivByDouble(grid*grid);
		double factor1 = (double)v1.getSize() / (double)grid;
		double factor2 = (double)v2.getSize() / (double)grid;
		
		v1 = v1.norm();
		v2 = v2.norm();
		
		for(int i = 0; i < grid; i++)
			for(int j = 0; j < grid; j++){
				Vector point = p1.add(v1.MultScalar(((Math.random()*0.5)+0.5)*(double)i*factor1));
				point = point.add(v2.MultScalar(((Math.random()*0.5)+0.5)*(double)j*factor2));
				
				Light light = new PointLight(point,pointI);
				
				scene.lights.add(light);
			}
	}
}
