package ch.pma.liti.tankz.ani;

import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.graphics.emitters.AnimationEmitter;

import java.awt.geom.Point2D;

public class CannonShotAnimationEmitter extends AnimationEmitter {

    public CannonShotAnimationEmitter(Point2D origin, Spritesheet spritesheet) {
        super(spritesheet, origin);
    }
}
