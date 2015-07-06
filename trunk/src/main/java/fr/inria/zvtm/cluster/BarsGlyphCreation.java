/*   Copyright (c) INRIA, 2014. All Rights Reserved
 *   Licensed under the GNU LGPL. For full terms see the file COPYING.
 *
 * $Id: RaildarGlyphCreation.aj 799 2015-04-06 21:15:47Z fdelcampo $
 */

package fr.inria.zvtm.cluster;

import java.awt.Color;

import fr.inria.zvtm.glyphs.Glyph;
import fr.inria.zvtm.glyphs.Bars;


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

}
