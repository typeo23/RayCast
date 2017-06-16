package rayCast;

import java.util.Iterator;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

public class GetColor {
	
	/**
	 * 
	 * @param p - point of intersection
	 * @param s - shape
	 * @return the difuuze color at p
	 */
	public static Rgb getDiffuzeColor(Vector p,Shape s){
		if (s.surface.type.compareTo("checkers") ==0 )
			return getCheckersDiffuze( p, s);
		if (s.surface.type.compareTo("texture") ==0 )
			return getTexture( p, s);
		return s.surface.Kd;
	}
	
	public static Rgb getCheckersDiffuze(Vector p,Shape s){
		Point2D uv = s.getUV(p);
		
		if (uv.x < 0)
			uv.x = -uv.x;
		if (uv.y < 0)
			uv.y = -uv.y;
		
		// checkers scale size
		double x = 1/(s.surface.checkersSize)* uv.x;
		double y = 1/((s.surface.checkersSize))*uv.y;
		
		//checks whether to use checkers1 or checkers2	
		int tmpX = (int)Math.round(x/2) % 2;
		int tmpY = (int)Math.round(y/2) % 2;
		
		if ((tmpX == 0 && tmpY != 0) || (tmpX !=0 && tmpY == 0))
			return s.surface.checkersDiffuse1;
		else 
			return s.surface.checkersDiffuse2;
		
		
	
	}
	
	public static Rgb getTexture(Vector p,Shape s){
		ImageData iDat = s.surface.getImage();
		if (iDat == null){
			s.surface.LoadImage();
			iDat = s.surface.getImage();
		}
		Point2D shapeSize = s.getTextureSize();
		//picture scale size
		double xFac = (double)iDat.width/shapeSize.x;
		double yFac = (double)iDat.height/shapeSize.y;
		
		Point2D uv = s.getUV(p);
		
		if (uv.x < 0)
			uv.x = 1-uv.x;
		if (uv.y < 0)
			uv.y = 1-uv.y;
		//compute the point in the texture
		int textX = (int)Math.round(uv.x*xFac);
		if (textX > iDat.width-1)
			textX =iDat.width-1;
		int textY = (int)Math.round(uv.y*yFac);
		if (textY > iDat.height-1)
			textY =iDat.height-1;
		
		int pixelData = iDat.getPixel(textX, textY);
		RGB rgbData = iDat.palette.getRGB(pixelData);
		
		return new Rgb((double)rgbData.red/255.0,(double)rgbData.green/255.0,(double)rgbData.blue/255.0);
		
		
		}
	
	public static Rgb getColor(Intersection inter,Scene scene){
		double r = 0;
		double g = 0;
		double b = 0;
		
		Iterator<Light> iter = scene.lights.iterator();
		
		while(iter.hasNext()){
				
			Light light = iter.next();
			double s = light.isShadowed(inter);
			
			
			Rgb inten = light.getIntancity(inter);
			if (inter.shape == null)
				return new Rgb(0,0,0);
			Rgb diffuze = getDiffuzeColor(inter.rayVector,inter.shape); //get the initial diffuze color
			Vector l = light.getLVector(inter); //l vector from the light source
			
			//calculet the diffuzed color
			r += inten.r*diffuze.r*l.dot(inter.norm)*s;
			
			g += inten.g*diffuze.g*l.dot(inter.norm)*s;
			
			b += inten.b*diffuze.b*l.dot(inter.norm)*s;
				
			
			//specular	
			double dot = 2.0*inter.norm.dot(l);
			Vector tmp = inter.norm.MultScalar(dot);
			tmp = tmp.sub(l); // l plus 90
			double fac = Math.pow(tmp.dot(inter.ray.getDirection().norm()), inter.shape.surface.shininess);
	
			if (fac >0){
			double r2  = inter.shape.surface.Ks.r*fac*inten.r*s;
			double g2 = inter.shape.surface.Ks.g*fac*inten.g*s;
			double b2 = inter.shape.surface.Ks.b*fac*inten.b*s;
			
			if (r2 > 0)
				r += r2;
			
			if (g2 > 0)
				g += g2;
			
			if (b2 > 0)
				b += b2;
			}
		}
		//recursive ray tracing
		if (inter.shape.surface.reflectance !=0 && inter.ray.rec < 16 ){
			// create reflactance ray
			double dot = 2.0*inter.norm.dot(inter.ray.getDirection());
			Vector tmp = inter.norm.MultScalar(dot);
			tmp = tmp.sub(inter.ray.getDirection());
			//trace the ray
			Ray rayRec = new Ray(tmp.norm().Reverse(),inter.point,inter.ray.rec +1);
			Intersection interRec = new Intersection();
			interRec.Intersec(rayRec, inter.scene);
			Rgb col = new Rgb(0,0,0);
			if (interRec.t == 0)
			{
				col = inter.scene.backCol;
			}
			else 
				col = GetColor.getColor(interRec, inter.scene);
			col =col.multByDouble((double)inter.shape.surface.reflectance);
			r += col.r;
			g += col.g;
			b += col.b;
			
		}
		//add ambient light
		r += inter.shape.surface.Ka.r*inter.scene.ambient.r ;
		g += inter.shape.surface.Ka.g *inter.scene.ambient.g;
		b += inter.shape.surface.Ka.b*inter.scene.ambient.b;
		//add ilumination light
		r += inter.shape.surface.Ie.r;
		g += inter.shape.surface.Ie.g;
		b += inter.shape.surface.Ie.b;
		
		//light cannot be negative
		if ( r<0)
			r=0;
		if ( g<0)
			g=0;
		if ( b<0)
			b=0;
		if(r > 1)
			r=1;
		if(g > 1)
			g=1;
		if(b > 1)
			b=1;
		
		
		return new Rgb(r,g,b);
	}

}
