package ch.pma.liti.tankz.ani;

import de.gurkenlabs.litiengine.graphics.emitters.AnimationEmitter;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.geom.Point2D;

public class CannonShotAnimationEmitter extends AnimationEmitter {

    public CannonShotAnimationEmitter(Point2D origin, String spritesheet) {
        super(Resources.spritesheets().get(spritesheet), origin);
    }
}
