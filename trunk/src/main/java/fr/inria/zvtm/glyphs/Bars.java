
package fr.inria.zvtm.glyphs;


import java.util.Vector;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.Font;

import fr.inria.zvtm.glyphs.projection.RProjectedCoords;

import fr.inria.zvtm.glyphs.SIRectangle;


public class Bars extends SIRectangle {

	private static final Color CURRENT_COLOR = Color.decode("#0089cc");
	private static final Color AVERAGE_COLOR = Color.BLACK;
	private static final Font CHART_FONT = new Font("Arial", Font.PLAIN, 10);
	private static final Color TEXT_COLOR = Color.BLACK;

	public static final int MAXWIDTH = 200;
	public static final int HEIGHT = 15;

	public static final int MARGIN = 5;

	public static final int AVERAGE_WIDTH = 3;
	public static final int AVERAGE_HEIGHT = 30;

	int max;
	int velocity;
	boolean isCurrent = true;

	public Bars(double x, double y, int z){
		super(x, y, z, MAXWIDTH, HEIGHT, CURRENT_COLOR, TEXT_COLOR);
		this.setDrawBorder(false);
		//this.setSensitivity(false);
	}

	public void changeToCurrentBar(boolean isCurrent){
		this.isCurrent = isCurrent;
		if(isCurrent){
			setColor(CURRENT_COLOR);
		} else {
			setColor(AVERAGE_COLOR);
		}
	}

	public void setData(int velocity){
		setData(velocity, velocity);
	}

	public void setData(int max, int velocity){
		if(velocity > max){
			this.max = velocity;
		} else {
			this.max = max;
		}
		this.velocity = velocity;

		int widthCurrent = (int)( (float)(velocity)/(float)(max) * MAXWIDTH );
		widthCurrent = ( widthCurrent % 2 == 0 ) ? widthCurrent : widthCurrent + 1;
		setWidth(widthCurrent);
	}

	public int getMax(){
		return max;
	}

	public int getVelocity(){
		return velocity;
	}

	public boolean isCurrent(){
		return isCurrent;
	}

	@Override
    public void draw(Graphics2D g, int vW, int vH, int i, Stroke stdS, AffineTransform stdT, int dx, int dy){
        if (alphaC != null && alphaC.getAlpha()==0){return;}
        if(isCurrent){
        	if ((pc[i].cw>1) && (pc[i].ch>1)) {
	            if (alphaC != null){
	                g.setComposite(alphaC);
	                //repaint only if object is visible
	                if (filled) {
	                    g.setColor(this.color);
	                    g.fillRect(dx+pc[i].cx, dy+pc[i].cy-pc[i].ch, 2*pc[i].cw, 2*pc[i].ch);
	                }
	                if (paintBorder){
	                    g.setColor(borderColor);
	                    g.drawRect(dx+pc[i].cx, dy+pc[i].cy-pc[i].ch, 2*pc[i].cw-1, 2*pc[i].ch-1);
	                }
	                g.setComposite(acO);
	            }
	            else {
	                //repaint only if object is visible
	                if (filled) {
	                    g.setColor(this.color);
	                    g.fillRect(dx+pc[i].cx, dy+pc[i].cy-pc[i].ch, 2*pc[i].cw, 2*pc[i].ch);
	                }
	                if (paintBorder){
	                    g.setColor(borderColor);
	                    g.drawRect(dx+pc[i].cx, dy+pc[i].cy-pc[i].ch, 2*pc[i].cw-1, 2*pc[i].ch-1);
	                }
	            }
	        }
	        else if ((pc[i].cw<=1) ^ (pc[i].ch<=1)) {
	            //repaint only if object is visible  (^ means xor)
	            g.setColor(this.color);
	            if (alphaC != null){
	                g.setComposite(alphaC);
	                if (pc[i].cw<=1){
	                    g.fillRect(dx+pc[i].cx, dy+pc[i].cy-pc[i].ch, 1, 2*pc[i].ch);
	                }
	                else if (pc[i].ch<=1){
	                    g.fillRect(dx+pc[i].cx, dy+pc[i].cy, 2*pc[i].cw, 1);
	                }
	                g.setComposite(acO);
	            }
	            else {
	                //repaint only if object is visible
	                if (pc[i].cw<=1){
	                    g.fillRect(dx+pc[i].cx, dy+pc[i].cy-pc[i].ch, 1, 2*pc[i].ch);
	                }
	                else if (pc[i].ch<=1){
	                    g.fillRect(dx+pc[i].cx, dy+pc[i].cy, 2*pc[i].cw, 1);
	                } 
	            }
	        }
	        else {
	            if (alphaC != null){
	                g.setComposite(alphaC);
	                g.setColor(this.color);
	                g.fillRect(dx+pc[i].cx, dy+pc[i].cy, 1, 1);
	                g.setComposite(acO);
	            }
	            else {
	                g.setColor(this.color);
	                g.fillRect(dx+pc[i].cx, dy+pc[i].cy, 1, 1);
	            }
	        }

	        g.setFont(CHART_FONT);
	        g.setColor(TEXT_COLOR);
	        g.drawString(velocity + " kph", dx+pc[i].cx + 2*pc[i].cw + MARGIN, dy+pc[i].cy+pc[i].ch);
	        g.setComposite(acO);

        } else {

        	if ((pc[i].cw>1) && (pc[i].ch>1)) {
	            if (alphaC != null){
	                g.setComposite(alphaC);
	                //repaint only if object is visible
	                if (filled) {
	                    g.setColor(this.color);
	                    //g.fillRect(dx+pc[i].cx + 2*pc[i].cw, dy+pc[i].cy - AVERAGE_HEIGHT/2, AVERAGE_WIDTH, AVERAGE_HEIGHT);//2*pc[i].cw, 2*pc[i].ch);
						g.fillRect(dx+pc[i].cx + 2*pc[i].cw, dy+pc[i].cy -pc[i].ch- AVERAGE_HEIGHT/2, AVERAGE_WIDTH, AVERAGE_HEIGHT);//2*pc[i].cw, 2*pc[i].ch);
	                }
					g.setComposite(acO);
	            }
	            else {
	                //repaint only if object is visible
	                if (filled) {
	                    g.setColor(this.color);
	                    g.fillRect(dx+pc[i].cx + 2*pc[i].cw, dy+pc[i].cy -pc[i].ch- AVERAGE_HEIGHT/2, AVERAGE_WIDTH, AVERAGE_HEIGHT); //2*pc[i].cw, 2*pc[i].ch);
	                }
	            }
	        }
	        else if ((pc[i].cw<=1) ^ (pc[i].ch<=1)) {
	            //repaint only if object is visible  (^ means xor)
	            g.setColor(this.color);
	            if (alphaC != null){
	                g.setComposite(alphaC);
	                if (pc[i].cw<=1){
	                    g.fillRect(dx+pc[i].cx, dy+pc[i].cy -pc[i].ch- AVERAGE_HEIGHT/2, 1, AVERAGE_HEIGHT);//2*pc[i].ch);
	                }
	                else if (pc[i].ch<=1){
	                    g.fillRect(dx+pc[i].cx + 2*pc[i].cw, dy+pc[i].cy -pc[i].ch- AVERAGE_HEIGHT/2, AVERAGE_WIDTH, 1);//2*pc[i].cw, 1);
	                }
	                g.setComposite(acO);
	            }
	            else {
	                //repaint only if object is visible
	                if (pc[i].cw<=1){
	                    g.fillRect(dx+pc[i].cx, dy+pc[i].cy -pc[i].ch- AVERAGE_HEIGHT/2, 1, AVERAGE_HEIGHT);//2*pc[i].ch);
	                }
	                else if (pc[i].ch<=1){
	                    g.fillRect(dx+pc[i].cx + 2*pc[i].cw, dy+pc[i].cy -pc[i].ch- AVERAGE_HEIGHT/2, AVERAGE_WIDTH, 1);//2*pc[i].cw, 1);
	                } 
	            }
	        }
	        else {
	            if (alphaC != null){
	                g.setComposite(alphaC);
	                g.setColor(this.color);
	                g.fillRect(dx+pc[i].cx, dy+pc[i].cy -pc[i].ch- AVERAGE_HEIGHT/2, 1, 1);
	                g.setComposite(acO);
	            }
	            else {
	                g.setColor(this.color);
	                g.fillRect(dx+pc[i].cx, dy+pc[i].cy -pc[i].ch- AVERAGE_HEIGHT/2, 1, 1);
	            }
	        }

	        g.setFont(CHART_FONT);
	        g.setColor(TEXT_COLOR);
	        g.drawString(velocity + " kph", dx+pc[i].cx + 2*pc[i].cw + MARGIN, dy+pc[i].cy -pc[i].ch- MARGIN); //+ HEIGHT/2 + MARGIN);
	        g.setComposite(acO);

        }
    }
}
