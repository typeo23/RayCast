package rayCast;
import java.util.*;
import org.eclipse.swt.graphics.ImageData;

import java.nio.*;


public class Scene {
	
	public Rgb backCol=new Rgb(0,0,0);
	public String backtext="";
	public Rgb ambient = new Rgb(0,0,0);
	public int superSample=1;
	public Camera camera;
	private IntBuffer imageBuffer;
	public int imageH,imageW;
	
	public List<Shape> shapes = new ArrayList<Shape>();
	public List<Light> lights = new ArrayList<Light>();
	
	public void getImageData(){
		ImageData iDat = new ImageData(backtext);
		imageW = iDat.width;
		imageH = iDat.height;
		imageBuffer = IntBuffer.allocate(imageW*imageH);
		for (int x = 0; x < imageW; x++) {
	           for (int y = 0; y < imageH; y++){
	         	   
	        	   int pixel = iDat.getPixel(x, y);
	        	   imageBuffer.put(y + x*imageH, pixel);
	        		        	   
				}
		}
		
		
	}
	
	public int getImagePixel(int x,int y){
		return imageBuffer.get(y + imageH*x);
	}

}
