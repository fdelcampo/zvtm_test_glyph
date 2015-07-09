

package cl.inria.zvtm.view;

import fr.inria.zvtm.glyphs.Composite;
import fr.inria.zvtm.glyphs.VText;
import fr.inria.zvtm.glyphs.Bars;
import fr.inria.zvtm.engine.VirtualSpaceManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;


public class Velocity extends Composite{
	
	static final int Z_VELOCITY = 10;
	private static final Color TEXT_COLOR = Color.BLACK;
	static int MARGIN = 20;

	private static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 12);

	private static final AffineTransform AFFINETRANSFORM = new AffineTransform();
	private static final FontRenderContext FRC = new FontRenderContext(AFFINETRANSFORM,true,true); 

	int current_value;
	int average_value;
	int max;
	String text;
	int height_text;
	int width_text;

	public  static final int KPH_WIDTH =  (int)(Bars.CHART_FONT.getStringBounds("000 kph", FRC).getWidth() );

	VText label;
	Bars current;
	Bars average;


	public Velocity(String text){

		this.text = text;
		max = 50;
		current_value = 50;
		average_value = 30;

		label = new VText(0 - MARGIN, 0 - Bars.HEIGHT/4, Z_VELOCITY, TEXT_COLOR, text, VText.TEXT_ANCHOR_END);
		label.setFont(LABEL_FONT);
		label.setTranslucencyValue(1f);
		//label.setSensitivity(false);
		current = new Bars(0.0, 0.0, Z_VELOCITY);
		current.setData(max, current_value);
		
		average = new Bars(0.0, 0.0, Z_VELOCITY);
		average.changeToCurrentBar(false);
		average.setData(max, average_value);

		addChild(label);
		addChild(current);
		addChild(average);
		//this.setSensitivity(false);
		height_text = (int)(LABEL_FONT.getStringBounds(text, FRC).getHeight());
		width_text = (int)(LABEL_FONT.getStringBounds(text, FRC).getWidth());


	}

	public Velocity(String text, int max, int current, int average ){
		this(text);
		setData(max, current, average);
	}

	public void setData(int max, int current, int average){
		if(max >= current && max >= average){
			this.current.setData(max, current);
			this.average.setData(max, average);
		} else {
			this.max = Math.max(current, average);
			this.current.setData(this.max, current);
			this.average.setData(this.max, average);
		}
		current_value = current;
		average_value = average;
		VirtualSpaceManager.INSTANCE.repaint();
	}

	public int getWidth(){
		return width_text + MARGIN + Bars.MAXWIDTH + Bars.MARGIN + KPH_WIDTH;
	}

	public int getHeight(){
		return Bars.AVERAGE_HEIGHT + Bars.MARGIN;
	}

	public int getMax(){
		return max;
	}

	public int getCurrent(){
		return current_value;
	}

	public int getAverage(){
		return average_value;
	}

	public String getText(){
		return text;
	}

}