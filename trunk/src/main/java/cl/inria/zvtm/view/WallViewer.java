/*
 *   Copyright (c) INRIA, 2011-2015. All Rights Reserved
 *   Licensed under the GNU LGPL. For full terms see the file COPYING.
 *
 * $Id: WallViewer.java 5400 2015-03-23 20:08:52Z epietrig $
 */

package cl.inria.zvtm.view;

import java.awt.Color;

import java.util.Vector;

import fr.inria.zvtm.engine.VirtualSpaceManager;
import fr.inria.zvtm.engine.VirtualSpace;
import fr.inria.zvtm.engine.View;
import fr.inria.zvtm.engine.Camera;
import fr.inria.zvtm.event.ViewListener;

import fr.inria.zvtm.cluster.ClusterGeometry;
import fr.inria.zvtm.cluster.ClusteredView;

/*
import fr.inria.zvtm.glyphs.Glyph;
import fr.inria.zvtm.glyphs.VCircle;
import fr.inria.zvtm.glyphs.VRectangle;
import fr.inria.zvtm.glyphs.VText;
*/

import cl.inria.zvtm.glyphs.StackBar;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class WallViewer {

    static final int VIEW_W = 1024;
    static final int VIEW_H = 768;

    VirtualSpaceManager vsm = VirtualSpaceManager.INSTANCE;
    VirtualSpace mSpace;
    Camera mCamera;
    View mView;
    ViewListener eh;

    private ClusteredView clusteredView;
    private ClusterGeometry withoutBezels;
    private ClusterGeometry withBezels;
    private boolean sceneUnderBezels = true;

    public WallViewer(Options options){
        vsm.setMaster("WallViewer");
        mSpace = vsm.addVirtualSpace("mainSpace");
        mCamera = mSpace.addCamera();
        Vector cameras = new Vector();
        cameras.add(mCamera);
        // local view (on master)
        mView = vsm.addFrameView(cameras, "zvtm-cluster-basicui", View.STD_VIEW, VIEW_W, VIEW_H, true);
        mView.setAntialiasing(true);
        eh = new WallViewerEvtHld(this);
        mView.setListener(eh, 0);
        mView.setBackgroundColor(Color.GRAY);
        mView.getCursor().setColor(Color.WHITE);
        mView.getCursor().setHintColor(Color.WHITE);
        Vector<Camera> ccameras = new Vector(1);
        ccameras.add(mCamera);
        withoutBezels = new ClusterGeometry(
                options.blockWidth,
                options.blockHeight,
                options.numCols,
                options.numRows);
        withBezels = withoutBezels.addBezels(options.mullionWidth,
               options.mullionHeight);
        clusteredView =
            new ClusteredView(
                    withBezels,
                    options.numRows-1, //origin (block number)
                    options.numCols, //use complete
                    options.numRows, //cluster surface
                    ccameras);
        clusteredView.setBackgroundColor(Color.GRAY);
        vsm.addClusteredView(clusteredView);
    }

    void init(){
        /*
        mSpace.addGlyph(new VCircle(0, 0 , 0, 400, Color.RED));
        for (float angle=0;angle<2*Math.PI;){
            mSpace.addGlyph(new VRectangle(400*Math.cos(angle), 400*Math.sin(angle), 10, 20, 20, Color.WHITE));
            angle += Math.PI/12d;
        }
        mSpace.addGlyph(new VText(0, 0, 0, Color.WHITE, "Hello World!", VText.TEXT_ANCHOR_MIDDLE,4));
        */

        StackBar n = new StackBar(110, 50, 10, 150);
        mSpace.addGlyph(n);

        
        n = new StackBar(50, 150, 10, 150);
        mSpace.addGlyph(n);
        n.move(0, 50);
                

        n = new StackBar(15, 11, 10, 150);
        n.move(0, 100);
        mSpace.addGlyph(n);

         n = new StackBar(10, 120, 10, 150);
        n.move(0, 150);
        mSpace.addGlyph(n);
        

    }

    void exit(){
        System.exit(0);
    }

    public static void main(String[] args){
        Options options = new Options();
        CmdLineParser parser = new CmdLineParser(options);
        try {
            parser.parseArgument(args);
        } catch(CmdLineException ex){
            System.err.println(ex.getMessage());
            parser.printUsage(System.err);
            return;
        }
        (new WallViewer(options)).init();
    }

}

