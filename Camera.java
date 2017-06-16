package rayCast;

public class Camera {
	private Vector eye;
	private Vector direction;
	private Vector upDirection;
	private double screenDist;
	private double screenWidth;
	private double canvasAspectRatio = 2;
	private double screenHeight;
	private double wTheta;
	private double hTheta;
	
	/**
	 * precondition: direction and upDirection are orthogonal to each other
	 */
	public Camera(){this.screenWidth=1;};
	public void setEye(Vector v){this.eye=v;}
	public void setUp(Vector v){this.upDirection = v;}
	public void setScreenDist(double v){this.screenDist = v;}
	public void setDirection(Vector v){this.direction = v;}
	public void setAspect(double a){this.canvasAspectRatio = a;}
	public void setScrrenDist(double d){this.screenDist = d/2.0;};
	public void setScreenWisth(double d){this.screenWidth = d/2;}
	public void setWidth(double aspect){
		this.screenHeight = screenWidth * aspect;
		this.canvasAspectRatio = aspect;
		wTheta = Math.atan((this.screenWidth/2.0)/this.screenDist);
		hTheta = Math.atan((this.screenHeight/2.0)/this.screenDist); 
		
	}
	//init Camera directions an angles
	public void initDir(){
		this.direction =this.direction.sub(this.eye).norm();
		
		Vector dir = this.direction;
		this.upDirection = upDirection.Reverse().cross(dir).cross(dir).norm();
		this.screenHeight = screenWidth * this.canvasAspectRatio;
		wTheta = Math.atan((this.screenWidth/2.0)/this.screenDist);
		hTheta = Math.atan((this.screenHeight/2.0)/this.screenDist); 
		
	}
	public Camera(Vector eye,Vector direction,Vector upDirection,int screenDist,int screenWidth,double aspect){
		this.eye = eye;
		this.direction =direction.sub(eye).norm();
		
		Vector dir = this.direction;
		this.upDirection = upDirection.Reverse().cross(dir).cross(dir).norm();
		System.out.println(this.direction);
		this.screenDist = screenDist;
		this.screenWidth = screenWidth;
		this.screenHeight = screenWidth * aspect;
		this.canvasAspectRatio = aspect;
		wTheta = Math.atan((this.screenWidth/2.0)/this.screenDist);
		hTheta = Math.atan((this.screenHeight/2.0)/this.screenDist); 
		
	}
	
	public Vector getEye(){
		return eye;
	}
	
	public Vector getDirection(){
		return direction;
	}
	
	public Vector getUpDirection(){
		return upDirection;
	}
	
	public double getScreenDist(){
		return screenDist;
	}
	
	public double getScreenWidth(){
		return screenWidth;
	}
	
	public double getScreenHeight(){
		return screenHeight;
	}
	
	public double getWTheat(){
		return this.wTheta;
	}
	
	public double getHTheta(){
		return this.hTheta;
	}
	
}
