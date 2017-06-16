package rayCast;

import org.eclipse.swt.graphics.*;
public class Rgb {
	public double r;
	public double g;
	public double b;
	
	public Rgb(double x,double y, double z){
		r=x;
		g=y;
		b=z;
	}
	
	public RGB getColor(){
		int r = (int)Math.round(this.r * 255.0);
		if (r > 255)
			r=255;
		if (r < 0)
			r=0;
		int g = (int)Math.round(this.g * 255.0);
		if (g > 255)
			g=255;
		if (g < 0)
			r=0;
		int b = (int)Math.round(this.b * 255.0);
		if (b > 255)
			b=255;
		if (b < 0)
			b=0;
		RGB color = new RGB(r,g,b);
		return color;
	}
	
	public Rgb add(Rgb rgb){
		return new Rgb(this.r+rgb.r,this.g+rgb.g,this.b+rgb.b);
	}
	
	public Rgb DivByDouble(double d){
		return new Rgb(this.r/d,this.g/d,this.b/d);
	}
	public Rgb multByDouble(double d){
		return new Rgb(this.r*d,this.g*d,this.b*d);
	}

}
