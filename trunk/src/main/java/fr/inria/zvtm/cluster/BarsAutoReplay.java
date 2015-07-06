/*   Copyright (c) INRIA, 2015. All Rights Reserved
 *   Licensed under the GNU LGPL. For full terms see the file COPYING.
 *
 * $Id: BarsAutoReplay.aj 799 2015-04-06 21:15:47Z fdelcampo $
 */

package fr.inria.zvtm.cluster;

import fr.inria.zvtm.cluster.Identifiable;
import fr.inria.zvtm.glyphs.SIRectangle;
import fr.inria.zvtm.glyphs.Bars;
import cl.inria.zvtm.view.Velocity;
import java.awt.Color;


/**
 * Add methods that should be replay by the generic Delta here.
 * See the AbstractAutoReplay aspect in ZVTM-cluster for more details.
 * @see fr.inria.zvtm.AbstractAutoReplay
 */
aspect BarsAutoReplay extends AbstractAutoReplay {

    public pointcut autoReplayMethods(Identifiable replayTarget) :
        this(replayTarget) &&
        if(replayTarget.isReplicated()) &&
        (
         execution(public void Bars.changeToCurrentBar(boolean)) ||
         execution(public boolean Bars.isCurrent()) ||
         execution(public int Bars.getMax()) ||
         execution(public int Bars.getVelocity()) ||
         execution(public void Bars.setData(int)) ||
         execution(public void Bars.setData(int, int)) ||
         execution(public void Velocity.setData(int, int, int)) ||
        );

}