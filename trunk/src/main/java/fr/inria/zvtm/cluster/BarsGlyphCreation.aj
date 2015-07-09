/*   Copyright (c) INRIA, 2014. All Rights Reserved
 *   Licensed under the GNU LGPL. For full terms see the file COPYING.
 *
 * $Id: RaildarGlyphCreation.aj 799 2015-04-06 21:15:47Z fdelcampo $
 */

package fr.inria.zvtm.cluster;

import java.awt.Color;

import fr.inria.zvtm.glyphs.Glyph;
import fr.inria.zvtm.glyphs.Bars;
import cl.inria.zvtm.view.Velocity;


public aspect BarsGlyphCreation {

    //overrides for various Glyph subclasses
    @Override public GlyphReplicator Bars.getReplicator(){
        return new BarsReplicator(this);
    }


    private static class BarsReplicator extends GlyphCreation.AbstractGlyphReplicator {

        private final boolean isCurrent;
        private final int max;
        private final int velocity;

        BarsReplicator(Bars source){
            super(source);
            isCurrent = source.isCurrent();
            max = source.getMax();
            velocity = source.getVelocity();
        }

        public Glyph doCreateGlyph(){
            Bars retval = new Bars(0d, 0d, 0);
            retval.changeToCurrentBar(isCurrent);
            retval.setData(max, velocity);
            return retval;
        }
    }

    //overrides for various Glyph subclasses

    @Override public GlyphReplicator Velocity.getReplicator(){
        return new VelocityReplicator(this);
    }


    private static class VelocityReplicator extends GlyphCreation.AbstractGlyphReplicator {

        private final int max;
        private final int current;
        private final int average;
        private final String text;

        VelocityReplicator(Velocity source){
            super(source);
            max = source.getMax();
            current = source.getCurrent();
            average = source.getAverage();
            text = source.getText();
        }

        public Glyph doCreateGlyph(){
            Velocity retval = new Velocity(text);
            retval.setData(max, current, average);
            return retval;
        }
    }

    

}
