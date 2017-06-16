package rayCast;
import org.eclipse.swt.graphics.ImageData;

public class Surface {
	public Rgb Ks;
	public Rgb Kd;
	public Rgb Ka;
	public Rgb Ie;
	
	public String name;
	public String type="nll";
	
	public int shininess;
	public double checkersSize = 0.1;
	
	public Rgb checkersDiffuse1;
	public Rgb checkersDiffuse2;
	
	public String texture;
	public double reflectance =0;
	
	private ImageData img;
	
	public Surface(){
		Kd = new Rgb(0.8,0.8,0.8);
		Ks = new Rgb(1,1,1);
		Ka = new Rgb(0.1,0.1,0.1);
		Ie = new Rgb(0,0,0);
		shininess =100;
		
		checkersDiffuse1 = new Rgb(1,1,1);
		checkersDiffuse2 = new Rgb(0.1,0.1,0.1);
		
		
	}
	
	public ImageData getImage(){
		return img;
	}
	
	public void LoadImage(){
		img = new ImageData(texture);
	}
}
