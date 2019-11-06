package ch.pma.liti.tankz.objects;

import ch.pma.liti.tankz.ani.CannonShotAnimationEmitter;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.event.MouseEvent;

public class CannonShot {

    private final String cannonFlareSpriteSheet;
    private final Tower tower;

    public CannonShot(Tower tower, String cannonFlareSpritesheet) {
        Input.mouse().onClicked(this::fireShot);
        this.cannonFlareSpriteSheet = cannonFlareSpritesheet;
        this.tower = tower;
    }

    private void fireShot(MouseEvent mouseEvent) {
        final CannonShotAnimationEmitter boom = new CannonShotAnimationEmitter(this.tower.getCannonTip(), this.cannonFlareSpriteSheet);
        Game.world().environment().add(boom);
    }
}
