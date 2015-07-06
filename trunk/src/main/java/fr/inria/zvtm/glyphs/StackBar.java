
package fr.inria.zvtm.glyphs;


import java.util.Vector;

import java.awt.Color;

import fr.inria.zvtm.glyphs.VRectangle;
import fr.inria.zvtm.glyphs.VText;
import fr.inria.zvtm.glyphs.Composite;


public class StackBar extends Composite {
	
	static int Z_BAR = 10;
	static int MAXWIDTH = 200;
	static int HEIGHT = 15;
	static int DISPLACE = 5;
	static int MARKWIDTH = 2;
	static int MARKHEIGHT = HEIGHT*2;

	int min;
	int max;

	int current;
	int average;
	
	Vector<VRectangle> bars;
	Vector<VText> labels;

	private static Color COLOR_BAR = Color.decode("#0089cc"); /*S[] = { Color.decode("#33bcff") , Color.decode("#0089cc"),
		Color.decode("0x00ABFF"), Color.decode("0xFF5400"), Color.decode("0xFF0000"), Color.decode("0xFF00AA"), Color.decode("0x9100FF"),
		Color.decode("0x1100FF"), Color.decode("0x00CCFF"), Color.decode("0x00FF4D"), Color.decode("0xDDFF00"), Color.decode("0x91FF00"),
		Color.decode("#33bcff") , Color.decode("#0089cc")};*/
	
	private static Color COLOR_MARK = Color.BLACK;

	private static Color COLOR_LABEL = Color.decode("#0089cc");


	public StackBar(int current, int average){
		this(current, average, Math.min(current, average), Math.max(current, average));
	}

	public StackBar(int current, int average, int min, int max){
		bars = new Vector(2);
		int widthAverage;
		int widthCurrent;

		this.current = current;
		this.average = average;

		if(average > current){
			if(min < current){
				this.min = min;
			} else {
				this.min = current;
			}
			if(max > average){
				this.max = max;
			} else {
				this.max = average;
			}

			widthAverage =  (int)( (float)(average)/(float)(max) * MAXWIDTH );
			widthCurrent = (int)( (float)(average - current)/(float)(max) * MAXWIDTH );

		} else {
			if(min < average){
				this.min = min;
			} else {
				this.min = average;
			}
			if(max > current){
				this.max = max;
			} else {
				this.max = current;
			}

			widthAverage = (int)( (float)(current - average)/(float)(max) * MAXWIDTH );
			widthCurrent = (int)( (float)(current)/(float)(max) * MAXWIDTH );
		}

		widthAverage = ( widthAverage % 2 == 0 ) ? widthAverage : widthAverage + 1;
		widthCurrent = ( widthCurrent % 2 == 0 ) ? widthCurrent : widthCurrent + 1;

		initBar(widthCurrent, widthAverage);
	}

	private void initBar(int widthCurrent, int widthAverage){

		VRectangle barCurrent = new VRectangle(widthCurrent/2, 0, Z_BAR, widthCurrent, HEIGHT, COLOR_BAR, COLOR_BAR, 1f );
		addChild(barCurrent);
		VRectangle barAverage = new VRectangle(widthAverage, HEIGHT/2, Z_BAR, MARKWIDTH, MARKHEIGHT, COLOR_MARK, COLOR_MARK, 1f );
		addChild(barAverage);

		bars.add(barCurrent);
		bars.add(barAverage);
	}


}