package rayCast;



public abstract class Shape implements IShape {
    public Surface surface = new Surface();
    
	
	
	public abstract Point2D getUV(Vector p);
	
	public Surface  getSurf(){
		return surface;
	}
	
	public abstract Point2D getTextureSize();
	
	
}
