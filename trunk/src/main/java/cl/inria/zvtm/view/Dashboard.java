
package cl.inria.zvtm.view;


import fr.inria.zvtm.engine.VirtualSpace;
import fr.inria.zvtm.engine.VirtualSpaceManager;
import fr.inria.zvtm.glyphs.Composite;
import fr.inria.zvtm.glyphs.SIRectangle;
import fr.inria.zvtm.glyphs.VText;
import fr.inria.zvtm.glyphs.Glyph;


import java.util.Vector;

import java.awt.Color;


class Dashboard extends SIRectangle{



	public static final int Z_DASHBOARD = 5;

	static final int MARGIN_LEFT = 10;
    static final int MARGIN_RIGHT = 30; // make room for the numerical value of the line with most trains
    static final int MARGIN_TOP = 10;
    static final int MARGIN_BOTTOM = 5;

	private static final Color DEFAULT_BORDER_COLOR = new Color(0,0,0,127);
    private static final Color DEFAULT_FILL_COLOR = new Color(255,255,255,127);

    static int LINE_HEIGHT = 35;

    static int LABEL_WIDTH = 200;
    static int BAR_WIDTH = 200;

    private WallViewer app;
	private VirtualSpace vs;
	Vector<Velocity> velocities;
	SIRectangle background;

    int CW = MARGIN_LEFT + LABEL_WIDTH + BAR_WIDTH + MARGIN_RIGHT;
    int CH = MARGIN_TOP + 0*LINE_HEIGHT + MARGIN_BOTTOM;// + LINE_HEIGHT;

    public static String[] RECORRIDOS = {"Maipú / Hospital Dipreca", "Pudahuel / Mapocho", "Cerrillos / Recoleta", "San Joaquin / Metro Vespucion Norte", "Santiago de Compostela / Viña del Mar sale al Mundo" , "La Serena de la nueva Guinea / Marepoto de Sebastian Piñera", "Potossi / Peor es nada"};
	public static int[][] VELOCIDADES = {{35, 42}, {40, 31}, {26, 32}, {55, 44}, {15, 36}, {90, 120}, {10, 5}};

	public Dashboard(WallViewer app){
		super(0, 0, Z_DASHBOARD, MARGIN_LEFT + LABEL_WIDTH + BAR_WIDTH + MARGIN_RIGHT, MARGIN_TOP + MARGIN_BOTTOM, DEFAULT_FILL_COLOR, DEFAULT_BORDER_COLOR);
		setType(WallViewerEvtHld.T_DASHBOARD);

		this.app = app;
		velocities = new Vector();
		vs = app.mSpace;
		vs.addGlyph(this, true);
		vs.atBottom(this);
		setTranslucencyValue(0.85f);
	}

	public void addVelocity(String text){
		Velocity n = new Velocity(text);
		//n.move(this.vx - MARGIN_RIGHT, this.vy + (LINE_HEIGHT*velocities.size()) - MARGIN_TOP - MARGIN_BOTTOM - LINE_HEIGHT/2 );
		velocities.add(n);
		//Dashboard.VELOCITIES_LENGTH = velocities.size();
		//addChild(n);
		
		vs.addGlyph(n, false);
		vs.onTop(n);
		this.stick(n);
		redraw();
	}

	public void addVelocity(String text, int max, int current, int average){
		Velocity n = new Velocity(text, max, current, average);
		//n.move(this.vx - MARGIN_RIGHT, this.vy +(LINE_HEIGHT*VELOCITIES_LENGTH) - MARGIN_TOP - MARGIN_BOTTOM - LINE_HEIGHT/2 );
		velocities.add(n);
		//Dashboard.VELOCITIES_LENGTH = velocities.size();
		//addChild(n);
		vs.addGlyph(n, false);
		vs.onTop(n);
		this.stick(n);
		redraw();
	}

	/*
	public void draw(){
		//System.out.println("draw");
		background = new SIRectangle(this.vx, this.vy, Z_DASHBOARD+10, CW, CH, DEFAULT_FILL_COLOR, DEFAULT_BORDER_COLOR);
		background.setType(WallViewerEvtHld.T_DASHBOARD);
		//background.setTranslucencyValue(0.85f);
		//background.setOwned(this);
		addChild(background);
		vs.addGlyph(background, true);
	}
	*/

	public void redraw(){
		//System.out.println("redraw");
		int maxVelocity = Integer.MIN_VALUE;
		int maxWidth = Integer.MIN_VALUE;
		for(Velocity iter : velocities){
			if(iter.getCurrent() > maxVelocity) maxVelocity = iter.getCurrent();
			if(iter.getAverage() > maxVelocity) maxVelocity = iter.getAverage();
			if(iter.getWidth() > maxWidth) maxWidth = iter.getWidth();
		}

		CW = MARGIN_LEFT + maxWidth + Velocity.KPH_WIDTH + MARGIN_RIGHT; //
		CH = MARGIN_TOP + velocities.size()*LINE_HEIGHT + MARGIN_BOTTOM;

		setWidth(CW);
		setHeight(CH);	

		int i = 0;
		for(Velocity iter : velocities){
			// + iter.getWidth()/2
			// + fr.inria.zvtm.glyphs.Bars.MAXWIDTH/2 + Velocity.KPH_WIDTH/2  // - this.getWidth()/2
			iter.moveTo(this.vx + CW/2 - Velocity.KPH_WIDTH - fr.inria.zvtm.glyphs.Bars.MAXWIDTH - MARGIN_RIGHT, this.vy - this.getHeight()/2 + i*LINE_HEIGHT + LINE_HEIGHT/2 );
			iter.setData(maxVelocity, iter.getCurrent(), iter.getAverage());
			i++;
		}

	


		VirtualSpaceManager.INSTANCE.repaint();

		
	}

}