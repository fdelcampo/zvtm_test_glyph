
package cl.inria.zvtm.glyphs;


import java.util.Vector;

import java.awt.Color;

import fr.inria.zvtm.glyphs.VRectangle;
import fr.inria.zvtm.glyphs.Composite;


public class StackBar extends Composite {
	
	static int Z_BAR = 10;
	static int MAXWIDTH = 200;
	static int HEIGHT = 15;
	static int DISPLACE = 5;

	int min;
	int max;
	
	Vector<VRectangle> bars;

	private static Color COLORS_BARS[] = { Color.decode("#33bcff") , Color.decode("#0089cc"),
		Color.decode("0x00ABFF"), Color.decode("0xFF5400"), Color.decode("0xFF0000"), Color.decode("0xFF00AA"), Color.decode("0x9100FF"),
		Color.decode("0x1100FF"), Color.decode("0x00CCFF"), Color.decode("0x00FF4D"), Color.decode("0xDDFF00"), Color.decode("0x91FF00"),
		Color.decode("#33bcff") , Color.decode("#0089cc")};

	public StackBar(int up, int down){
		bars = new Vector(2);

		if(down > up){
			min = up;
			max = down;
			int widthDown =  (int)( (float)(down)/max * MAXWIDTH );
			int widthUp = (int)( (float)(down - up)/max * MAXWIDTH );

			widthDown = ( widthDown % 2 == 0 ) ? widthDown : widthDown + 1;
			widthUp = ( widthUp % 2 == 0 ) ? widthUp : widthUp + 1;

			VRectangle barDown = new VRectangle(widthDown/2, 0, Z_BAR, widthDown, HEIGHT, COLORS_BARS[0], COLORS_BARS[0], 1f );
			addChild(barDown);

			VRectangle barUp = new VRectangle(widthUp/2, DISPLACE, Z_BAR, widthUp, HEIGHT, COLORS_BARS[1], COLORS_BARS[1], 1f );
			addChild(barUp);

			bars.add(barDown);
			bars.add(barUp);

		} else {
			min = down;
			max = up;
			int widthDown = (int)( (float)(up - down)/max * MAXWIDTH );
			int widthUp = (int)( (float)(up)/max * MAXWIDTH );

			widthDown = ( widthDown % 2 == 0 ) ? widthDown : widthDown + 1;
			widthUp = ( widthUp % 2 == 0 ) ? widthUp : widthUp + 1;

			VRectangle barUp = new VRectangle(widthUp/2, 0, Z_BAR, widthUp, HEIGHT, COLORS_BARS[1], COLORS_BARS[1], 1f );
			addChild(barUp);

			VRectangle barDown = new VRectangle(widthDown/2, DISPLACE, Z_BAR, widthDown, HEIGHT, COLORS_BARS[0], COLORS_BARS[0], 1f );
			addChild(barDown);

			bars.add(barDown);
			bars.add(barUp);

		}

	}

	public StackBar(int up, int down, int min, int max){
		bars = new Vector(2);

		if(down > up){
			if(min < up){
				this.min = min;
			} else {
				this.min = up;
			}
			if(max > down){
				this.max = max;
			} else {
				this.max = down;
			}

			int widthDown =  (int)( (float)(down)/(float)(max) * MAXWIDTH );
			int widthUp = (int)( (float)(down - up)/(float)(max) * MAXWIDTH );

			widthDown = ( widthDown % 2 == 0 ) ? widthDown : widthDown + 1;
			widthUp = ( widthUp % 2 == 0 ) ? widthUp : widthUp + 1;

			VRectangle barDown = new VRectangle(widthDown/2, 0, Z_BAR, widthDown, HEIGHT, COLORS_BARS[0], COLORS_BARS[0], 1f );
			addChild(barDown);

			VRectangle barUp = new VRectangle(widthUp/2, DISPLACE, Z_BAR, widthUp, HEIGHT, COLORS_BARS[1], COLORS_BARS[1], 1f );
			addChild(barUp);

			bars.add(barDown);
			bars.add(barUp);

		} else {

			if(min < down){
				this.min = min;
			} else {
				this.min = down;
			}
			if(max > up){
				this.max = max;
			} else {
				this.max = up;
			}

			int widthDown = (int)( (float)(up - down)/(float)(max) * MAXWIDTH );
			int widthUp = (int)( (float)(up)/(float)(max) * MAXWIDTH );

			widthDown = ( widthDown % 2 == 0 ) ? widthDown : widthDown + 1;
			widthUp = ( widthUp % 2 == 0 ) ? widthUp : widthUp + 1;

			VRectangle barUp = new VRectangle(widthUp/2, 0, Z_BAR, widthUp, HEIGHT, COLORS_BARS[1], COLORS_BARS[1], 1f );
			addChild(barUp);

			VRectangle barDown = new VRectangle(widthDown/2, DISPLACE, Z_BAR, widthDown, HEIGHT, COLORS_BARS[0], COLORS_BARS[0], 1f );
			addChild(barDown);

			bars.add(barDown);
			bars.add(barUp);

		}
	}


}