package rayCast;

import java.io.*;


import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;


public class RayTracer {

	/**
	 * @param args
	 */
	static Display display;
	public static Scene m_scene = new Scene();
	public static void main(String[] args) 
	{
		display = new Display();
		RayTracer tracer = new RayTracer();
		tracer.runMain(display);
		
		
		display.dispose();
	}
	

	
	void renderTo(ImageData dat, Canvas canvas) throws Parser.ParseException
	{
		// TO-ADD: initialize your scene object
		//m_scene = new Scene();
		//m_scene.setCanvasSize(dat.height, dat.width);
		// need to set its size before parsing.

		try {
			// TO-ADD: create your scene parser and invoke it.
		
			SceneParser f = new SceneParser(m_scene);
			f.parse(new StringReader(m_sceneText.getText()));
			throw new IOException();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// TO-ADD: possibly add post-parse scene initializations
		
		GC gc = new GC(canvas);
		gc.fillRectangle(m_rect);
		
		m_scene.camera.setAspect((double)dat.height/(double)dat.width);
		m_scene.camera.initDir();
		ImageData idat=null;
		Color col = new Color(gc.getDevice(),m_scene.backCol.getColor());
		
		if (m_scene.backtext.length() != 0){
			canvas.setBackgroundImage(new Image(gc.getDevice(),m_scene.backtext));
			idat  = canvas.getBackgroundImage().getImageData();
		}
		else canvas.setBackground(col);
		
		double superSampleFact = 1/(double)m_scene.superSample;//how many rays in a pixel
		
		Intersection inter = new Intersection();
		for(int y = 0; y < dat.height; ++y)
		{
			for(int x = 0; x < dat.width; ++x)
			{	
				Rgb finalCol = new Rgb(0,0,0);
				//super sampling
				for (int i=0;i<m_scene.superSample;i++){
					double currX = x + superSampleFact*(double)i;
					for (int j=0;j<m_scene.superSample;j++){
						double currY = y + superSampleFact*(double)i;
						Ray ray = new Ray(m_scene.camera,currX,currY,canvas.getSize().x,canvas.getSize().y);
						// TO-ADD: get the color for this pixel (shoot rays etc')
						//Vec mycol = m_scene.getColor(x, y);
						inter.Intersec(ray,m_scene);
						
						Rgb color;
						if (inter.t == 0)
							color =m_scene.backCol;
						else{
							color = GetColor.getColor(inter,m_scene);
						}
							
						finalCol = finalCol.add(color);
						// TO-ADD: draw the pixel directly on the canvas
						//Color cc = new Color(display, mycol.r(), mycol.g(), mycol.b());
					}
				}
				finalCol = finalCol.DivByDouble(m_scene.superSample*m_scene.superSample);
				RGB col2 = m_scene.backCol.getColor();
				col2 = finalCol.getColor();
				
				//checks whether we hit the background
				if (inter.t !=0){
					dat.setPixel(x, y, dat.palette.getPixel(col2));
					Color cc = new Color(display, col2.red,col2.green, col2.blue);
				
					gc.setForeground(cc);
					gc.drawPoint(x, y);
					cc.dispose();
				}
				else{
					// if theres a background texture return its color
					if (m_scene.backtext != ""){	
						int u=x;
						if (u >= m_scene.imageW)
							u = m_scene.imageW-1;
						int v=y;
						if (v >= m_scene.imageH)
							v = m_scene.imageH-1;
						int color = m_scene.getImagePixel(u, v);
						RGB rgbcol = idat.palette.getRGB(color);
						dat.setPixel(x, y, dat.palette.getPixel(rgbcol));
						Color cc = new Color(display, rgbcol.red,rgbcol.green, rgbcol.blue);
					
						gc.setForeground(cc);
						gc.drawPoint(x, y);
						cc.dispose();
					}
					//otherwise return the background color
					else{
						 col2 = m_scene.backCol.getColor();
						 dat.setPixel(x, y, dat.palette.getPixel(col2));
						 Color cc = new Color(display, col2.red,col2.green, col2.blue);
							
							gc.setForeground(cc);
							gc.drawPoint(x, y);
							cc.dispose();
						}
					}
				}
			}


		}


	
	public static String readTextFile(Reader in) throws IOException 
	{
		StringBuilder sb = new StringBuilder(1024);
		BufferedReader reader = new BufferedReader(in);
		
		char[] chars = new char[1024];
		while(reader.read(chars)> -1){
			sb.append(String.valueOf(chars));	
		}
		return sb.toString();
	}
	
	void openFile(String filename)
	{
		try {
	
			Reader fr = new FileReader(filename);
			m_sceneText.setText(readTextFile(fr));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
	
	org.eclipse.swt.graphics.Rectangle m_rect;
	ImageData m_imgdat;

	// GUI
	Text m_sceneText;
	
	
	void runMain(final Display display)
	{
		Shell editShell = new Shell(display);
		editShell.setText("Input");
		editShell.setSize(300, 550);
		GridLayout gridEdit = new GridLayout();
		editShell.setLayout(gridEdit);
		 
		Composite editComp = new Composite(editShell, SWT.NONE);
		GridData ld = new GridData();
		ld.heightHint = 30;
		editComp.setLayoutData(ld);
		
		m_sceneText = new Text(editShell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		ld = new GridData(GridData.FILL_BOTH);
		m_sceneText.setLayoutData(ld);
		Font fixed = new Font(display, "Courier New", 10, 0);
		m_sceneText.setFont(fixed);
		
		
		final Shell shell = new Shell(display);
		shell.setText("Ray Tracer Ex");
		shell.setSize(600, 500);
		GridLayout gridLayout = new GridLayout();
		
		shell.setLayout(gridLayout);
		
		// the canvas we'll be drawing on.
		final Canvas canvas = new Canvas(shell, SWT.BORDER | SWT.NO_REDRAW_RESIZE);
		ld = new GridData(GridData.FILL_BOTH);
		canvas.setLayoutData(ld);

		Composite comp = new Composite(shell, SWT.NONE);
		ld = new GridData();
		ld.heightHint = 45;
		comp.setLayoutData(ld);
		
		// "Render Button"
		Button renderBot = new Button(comp, SWT.PUSH);
		renderBot.setText("Render");
		renderBot.setSize(150, 40);

	
		renderBot.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent ev) 
			{
				try {
					m_scene = new Scene();
					m_imgdat = new ImageData(m_rect.width, m_rect.height, 24, new PaletteData(0xFF0000 , 0xFF00 , 0xFF));
					renderTo(m_imgdat, canvas);
				} catch (Parser.ParseException e) {
					System.out.println("Error Parsing text: " + e.getMessage());
				}					
			}
			});


		Button savePngBot = new Button(comp, SWT.PUSH );
		savePngBot.setText("Save PNG");
		savePngBot.setBounds(250, 0, 70, 40);
		savePngBot.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent ev)
			{
				FileDialog dlg = new FileDialog(shell, SWT.SAVE);
				dlg.setText("Save PNG");
				dlg.setFilterExtensions(new String[] { "*.png", "*.*" });
				String selected = dlg.open();
				if (selected == null)
					return;

			    ImageLoader loader = new ImageLoader();
			    loader.data = new ImageData[] { m_imgdat };
			    loader.save(selected, SWT.IMAGE_PNG);
			}
		});
		

		Button openBot = new Button(editComp, SWT.PUSH);
		openBot.setText("Open");
		openBot.setBounds(0, 0, 100, 30);
		
		openBot.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e)
			{
				FileDialog dlg = new FileDialog(shell, SWT.OPEN);
				dlg.setText("Open Model");
				dlg.setFilterExtensions(new String[] { "*.txt", "*.*" });
				String selected = dlg.open();
				if (selected != null)
					openFile(selected);
				
			}
		});


		canvas.addListener (SWT.Resize, new Listener() {
		    public void handleEvent(Event e) {
		       m_rect = canvas.getClientArea();
		    }
		  });		

		canvas.addPaintListener(new PaintListener() 
		{
			public void paintControl(PaintEvent e) 
			{
				GC gc = e.gc;
				if (m_imgdat == null)
				{
					gc.drawLine(0, 0, e.width, e.height);
					return;
				}
				Image img = new Image(display, m_imgdat);
				if (img != null)
				{
					gc.drawImage(img, 0, 0);
				}
				img.dispose();
			}
		});

		shell.open();
		Point l = shell.getLocation();
		editShell.setLocation(new Point(l.x + 650, l.y));
		editShell.open();
		
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		

	
	}

}
