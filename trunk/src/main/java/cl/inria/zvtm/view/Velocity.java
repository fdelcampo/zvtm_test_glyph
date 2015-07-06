

package cl.inria.zvtm.view;

import fr.inria.zvtm.glyphs.Composite;
import fr.inria.zvtm.glyphs.VText;
import fr.inria.zvtm.glyphs.Bars;

import java.awt.Color;
import java.awt.Font;


class Velocity extends Composite{
	
	static final int Z_VELOCITY = 1;
	private static final Color TEXT_COLOR = Color.BLACK;
	static int MARGIN = 20;

	private static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 12);

	int current_value;
	int average_value;
	int max;

	VText label;
	Bars current;
	Bars average;


	public Velocity(String text){

		max = 50;
		current_value = 50;
		average_value = 30;

		label = new VText(0 - MARGIN, 0 - Bars.HEIGHT/4, Z_VELOCITY, TEXT_COLOR, text, VText.TEXT_ANCHOR_END);
		label.setFont(LABEL_FONT);
		label.setSensitivity(false);
		current = new Bars(0.0, 0.0, Z_VELOCITY);
		current.setData(max, current_value);
		
		average = new Bars(0.0, 0.0, Z_VELOCITY);
		average.changeToCurrentBar(false);
		average.setData(max, average_value);

		addChild(label);
		addChild(current);
		addChild(average);
		//this.setSensitivity(false);

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
	}

}