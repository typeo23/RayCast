/**
 * 
 */
package rayCast;

import rayCast.Parser.ParseException;

/**
 * @author itay
 *
 */
public class SceneParser extends Parser {
		
		

	private Scene scene;
	private Object obj;
	private String objectName;
	private String type;

	
	public SceneParser(Scene scene){
		super();
		this.scene = scene;
	}
	
	public boolean addObject(String name) throws ParseException
	{
		super.addObject(name);
		objectName = name;	
		
		if (name.compareTo("rectangle")==0){
			obj = new Rectangle();
			this.type = "shape";
		}
		
		if (name.compareTo("box")==0){
			obj = new Box();
			this.type = "shape";
		}
		
		if (name.compareTo("cylinder")==0){
			obj = new Cylinder();
			this.type = "shape";
		}
		
		if (name.compareTo("disc")==0){
			obj = new Disc();
			this.type = "shape";
		}
		
		if(name.compareTo( "sphere")==0){
			obj = new Sphere();
			this.type = "shape";
		}
		
		if(name.compareTo("light-directed")==0){
			obj = new DirectionalLight();
			this.type = "light";
		}
		if(name.compareTo("light-point")==0){
			obj = new PointLight();
			this.type = "light";
		}
		if(name.compareTo("camera")==0){
			obj = new Camera();
			this.type = "camera";
		}
		if(name.compareTo("scene")==0){
			this.type = "scene";
		}
		if(name.compareTo("light-area")==0){
			obj = new AreaLight();
			this.type = "areaLight";
		}
		
		return true;
		
	}
	
	public boolean setParameter(String name, String[] args) throws ParseException
	{
		super.setParameter(name, args);
		if(name.compareTo("mtl-diffuse") == 0)
			setDiffuzeParam(getRgbFromParams(args));
		if(name.compareTo("checkers-diffuse1") == 0)
			setCheckers1Param(getRgbFromParams(args));
		if(name.compareTo("checkers-diffuse2") == 0)
			setCheckers2Param(getRgbFromParams(args));
		if(name.compareTo("checkers-size") == 0)
			setCheckersSizeParam(Double.parseDouble(args[0]));
		
		if(name.compareTo("reflectance") == 0)
			setReflectParam(Double.parseDouble(args[0]));
		if(name.compareTo("mtl-type") == 0)
			setTypeParam(args[0]);
		if(name.compareTo("texture") == 0)
			setTextParam(args[0]);
		if(name.compareTo("mtl-speculare") == 0)
			setSpecularParam(getRgbFromParams(args));
		if(name.compareTo("mtl-ambient") == 0)
			setAmbientParam(getRgbFromParams(args));
		if(name.compareTo("mtl-ambient") == 0)
			setAmbientParam(getRgbFromParams(args));
		if(name.compareTo("mtl-emission") == 0)
			setEmissionParam(getRgbFromParams(args));
		if(name.compareTo("mtl-shininess ") == 0)
			setShineParam(Integer.parseInt(args[0]));
		
		if (objectName.compareTo("rectangle") ==0){
			addRectangleParams(name,args);
		}
		
		if (objectName.compareTo("disc") ==0){
			addDiscParams(name,args);
		}
		
		if (objectName.compareTo("cylinder") ==0){
			addCylinderParams(name,args);
		}
		
		if(objectName.compareTo("sphere")==0){
			addSphereParams(name,args);
		}
		
		if(objectName.compareTo("box")==0){
			addBoxParams(name,args);
		}
		
		if(objectName.compareTo("light-directed") ==0){
			addDirectedLightParams(name,args);
		}
		if(objectName.compareTo("light-point") ==0){
			addPointLightParams(name,args);
		}
		if(objectName.compareTo("scene")==0){
			addSceneParams(name,args);
		}
		if(objectName.compareTo("camera") == 0){
			addCameraParams(name,args);
		}
		if(objectName.compareTo("light-area") == 0){
			addAreaParams(name,args);
		}
		
	    return true;
	}
	
	
	private void setDiffuzeParam (Rgb rgb){
		Shape s = (Shape)obj;
		s.surface.Kd=rgb;
	}
	private void setCheckers1Param (Rgb rgb){
		Shape s = (Shape)obj;
		s.surface.checkersDiffuse1=rgb;
	}
	private void setCheckers2Param (Rgb rgb){
		Shape s = (Shape)obj;
		s.surface.checkersDiffuse2=rgb;
	}
	private void setTypeParam (String name){
		Shape s = (Shape)obj;
		s.surface.type=name;
	}
	
	private void setTextParam (String name){
		Shape s = (Shape)obj;
		s.surface.texture=name;
	}
	
	private void setSpecularParam (Rgb rgb){
		Shape s = (Shape)obj;
		s.surface.Ks=rgb;
	}
	
	private void setAmbientParam (Rgb rgb){
		Shape s = (Shape)obj;
		s.surface.Ka=rgb;
	}
	
	private void setEmissionParam (Rgb rgb){
		Shape s = (Shape)obj;
		s.surface.Ie=rgb;
	}
	
	private void setShineParam (int  d){
		Shape s = (Shape)obj;
		s.surface.shininess=d;
	}
	private void setReflectParam (double  d){
		Shape s = (Shape)obj;
		s.surface.reflectance=d;
	}
	private void setCheckersSizeParam (double  d){
		Shape s = (Shape)obj;
		s.surface.checkersSize=d;
	}
	
	private Vector getVectorFromParams(String[] args){
		double x,y,z;
		x = Double.parseDouble(args[0]);
		y = Double.parseDouble(args[1]);
		z = Double.parseDouble(args[2]);
		Point3D xyz = new Point3D(x,y,z);
		Vector v = new BVector();
		v.setXyz(xyz);
		return v;
		
	}
	
	private Rgb getRgbFromParams(String[] args){
		double x,y,z;
		x = Double.parseDouble(args[0]);
		y = Double.parseDouble(args[1]);
		z = Double.parseDouble(args[2]);
		return new Rgb(x,y,z);
		
	}
	
	
	private void addRectangleParams(String name,String[] args){
		Rectangle rec = (Rectangle)obj;	
		if (name.compareTo("p0") == 0 ){
			rec.setP0(getVectorFromParams(args));
		}
		if (name.compareTo("p1") == 0 ){
			rec.setP1(getVectorFromParams(args));
		}
		if (name.compareTo("p2") == 0){
			rec.setP2(getVectorFromParams(args));
		}
	}
	
	private void addAreaParams(String name,String[] args){
		AreaLight area = (AreaLight)obj;	
		if (name.compareTo("p0") == 0 ){
			area.setP1(getVectorFromParams(args));
		}
		if (name.compareTo("p1") == 0 ){
			area.setP2(getVectorFromParams(args));
		}
		if (name.compareTo("p2") == 0){
			area.setP3(getVectorFromParams(args));
		}
		if (name.compareTo("grid-width") == 0){
			area.setGridSize(Integer.parseInt(args[0]));
		}
		if (name.compareTo("color") == 0){
			area.setI(getRgbFromParams(args));
		}
	}
	
	private void addBoxParams(String name,String[] args){
		Box box = (Box)obj;	
		if (name.compareTo("p0") == 0 ){
			box.setP0(getVectorFromParams(args));
		}
		if (name.compareTo("p1") == 0 ){
			box.setP1(getVectorFromParams(args));
		}
		if (name.compareTo("p2") == 0){
			box.setP2(getVectorFromParams(args));
		}
		if (name.compareTo("p3") == 0){
			box.setP3(getVectorFromParams(args));
		}
	}
	
	
	
	private void addSphereParams(String name,String[] args){
		Sphere s = (Sphere)obj;
		if (name.compareTo("radius") == 0){
			s.setRadius(Double.parseDouble(args[0]));
		}
		if (name.compareTo("center") == 0 ){
			s.setCenter(getVectorFromParams(args));
		}
	}
	
	private void addDiscParams(String name,String[] args){
		Disc s = (Disc)obj;
		if (name.compareTo("radius") == 0){
			s.setRadius(Double.parseDouble(args[0]));
		}
		if (name.compareTo("center") == 0 ){
			s.setCenter(getVectorFromParams(args));
		}
		if (name.compareTo("normal") == 0 ){
			s.setNormal(getVectorFromParams(args));
		}
	}
	
	private void addCylinderParams(String name,String[] args){
		Cylinder s = (Cylinder)obj;
		if (name.compareTo("radius") == 0){
			s.setRadius(Double.parseDouble(args[0]));
		}
		if (name.compareTo("length") == 0){
			s.setLength(Double.parseDouble(args[0]));
		}
		if (name.compareTo("start") == 0 ){
			s.setStart(getVectorFromParams(args));
		}
		if (name.compareTo("direction") == 0 ){
			s.setDir(getVectorFromParams(args));
		}
	}
	
	private void addDirectedLightParams(String name,String[] args){
		DirectionalLight l = (DirectionalLight)(obj);
		if (name.compareTo("direction") == 0 ){
			l.setDirection(getVectorFromParams(args));
		}
		if (name.compareTo("color") == 0 ){
			l.setIntencity(getRgbFromParams(args));
		}
	}
	
	private void addPointLightParams(String name,String[] args){
		PointLight l = (PointLight)(obj);
		if (name.compareTo("pos") == 0 ){
			l.setPos(getVectorFromParams(args));
		}
		if (name.compareTo("color") == 0 ){
			l.setColor(getRgbFromParams(args));
		}
	}
	private void addCameraParams(String name,String[] args){
		Camera c = (Camera)obj;
		if (name.compareTo("eye") == 0){
			c.setEye(getVectorFromParams(args));
		}
		if (name.compareTo("look-at") == 0 ){
			c.setDirection(getVectorFromParams(args));
		}
		if (name.compareTo("screen-dist") == 0 ){
			c.setScreenDist(Double.parseDouble(args[0]));
		}
		if (name.compareTo("up-direction") == 0){
			c.setUp(getVectorFromParams(args));
		}
		if (name.compareTo("direction") == 0){
			c.setDirection(getVectorFromParams(args));
		}
		if (name.compareTo("screen-dist") == 0 ){
			c.setScreenDist(Double.parseDouble(args[0]));
		}
		if (name.compareTo("screen-width") == 0 ){
			c.setScreenWisth(Double.parseDouble(args[0]));
		}
		
		
	}
	private void addSceneParams(String name,String[] args){
		if (name.compareTo("background-col") == 0){
			this.scene.backCol=getRgbFromParams(args);
		}
		if (name.compareTo("ambient-light") == 0 ){
			this.scene.ambient=getRgbFromParams(args);
		}
		if (name.compareTo("super-samp-width") == 0){
			this.scene.superSample=Integer.parseInt(args[0]);
		}
		if (name.compareTo("background-tex") == 0){
			this.scene.backtext = args[0];
			this.scene.getImageData();
		}
	}
	
	public void commit() throws ParseException
	{
		if (objectName.compareTo("light-area") ==0){
			AreaLight r = (AreaLight)obj;
			r.commit(this.scene);
		}
		if (objectName.compareTo("rectangle") ==0){
			Rectangle r = (Rectangle)obj;
			r.commit();
		}
		if (objectName.compareTo("box") ==0){
			Box r = (Box)obj;
			r.commit();
		}
		if (this.type.compareTo("shape") == 0){
			this.scene.shapes.add((Shape)obj);
		}
		if (this.type.compareTo("light") == 0 ){
			this.scene.lights.add((Light)obj);
		}
		if (this.type.compareTo( "camera") == 0){
			this.scene.camera = (Camera)obj;
		}
		
		obj = null;
		this.objectName = null;
		this.type = null;
	}
	
}
