

package cl.inria.zvtm.view;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import fr.inria.zvtm.engine.VirtualSpaceManager;
import fr.inria.zvtm.engine.ViewPanel;
import fr.inria.zvtm.engine.View;
import fr.inria.zvtm.engine.Camera;

import fr.inria.zvtm.event.ViewAdapter;

import fr.inria.zvtm.glyphs.Glyph;


class WallViewerEvtHld extends ViewAdapter {

    static float ZOOM_SPEED_COEF = 1.0f/50.0f;
    static double PAN_SPEED_COEF = 50.0;
    static final float WHEEL_ZOOMIN_COEF = 21.0f;
    static final float WHEEL_ZOOMOUT_COEF = 22.0f;
    static float WHEEL_MM_STEP = 1.0f;

    //remember last mouse coords
    int lastJPX,lastJPY;

    WallViewer application;

    boolean panning = false;

    WallViewerEvtHld(WallViewer app){
        this.application = app;
    }

    public void press1(ViewPanel v, int mod, int jpx, int jpy, MouseEvent e){
        lastJPX = jpx;
        lastJPY = jpy;
        panning = true;
        v.setDrawDrag(true);
    }

    public void release1(ViewPanel v, int mod, int jpx, int jpy, MouseEvent e){
        application.mCamera.setXspeed(0);
        application.mCamera.setYspeed(0);
        application.mCamera.setZspeed(0);
        v.setDrawDrag(false);
        panning = false;
    }

    public void mouseDragged(ViewPanel v, int mod, int buttonNumber, int jpx, int jpy, MouseEvent e){
        if (panning){
            Camera c = v.cams[0];
            double a = (c.focal+Math.abs(c.altitude))/c.focal;
            application.mCamera.setXspeed((long)((jpx-lastJPX)*(a/PAN_SPEED_COEF)));
            application.mCamera.setYspeed((long)((lastJPY-jpy)*(a/PAN_SPEED_COEF)));
            application.mCamera.setZspeed(0);
        }
    }

    public void mouseWheelMoved(ViewPanel v, short wheelDirection, int jpx, int jpy, MouseWheelEvent e){
        double a = (application.mCamera.focal+Math.abs(application.mCamera.altitude)) / application.mCamera.focal;
        if (wheelDirection  == WHEEL_UP){
            // zooming in
            application.mCamera.altitudeOffset(a*WHEEL_ZOOMOUT_COEF);
            VirtualSpaceManager.INSTANCE.repaint();
        }
        else {
            //wheelDirection == WHEEL_DOWN, zooming out
            application.mCamera.altitudeOffset(-a*WHEEL_ZOOMIN_COEF);
            VirtualSpaceManager.INSTANCE.repaint();
        }
    }

    public void enterGlyph(Glyph g){
        g.highlight(true, null);
    }

    public void exitGlyph(Glyph g){
        g.highlight(false, null);
    }

    public void viewClosing(View v){
        application.exit();
    }

}

