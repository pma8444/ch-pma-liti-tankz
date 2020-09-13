package ch.pma.liti.tankz.objects;

import ch.pma.liti.tankz.ani.CannonShotAnimationEmitter;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class CannonShotController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CannonShotController.class);
    private static final double SHOT_ANI_OFFSET_Y = 8d;
    private final Spritesheet spritesheet;
    private Point2D.Double spawnPoint;
    private double currentAngle;

    public CannonShotController(String cannonFlareSpritesheet) {
        Input.mouse().onClicked(this::fireShot);
        this.spritesheet = Resources.spritesheets().get(cannonFlareSpritesheet);
    }

    private void fireShot(MouseEvent mouseEvent) {
        playShotAnimation();
    }

    private void playShotAnimation() {
        final CannonShotAnimationEmitter boom = new CannonShotAnimationEmitter(getSpawnPoint(), this.spritesheet);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(currentAngle), 0, 0);
        boom.getAnimationController().setAffineTransform(affineTransform);
        Game.world().environment().add(boom);
    }

    private Point2D getSpawnPoint() {
        return this.spawnPoint;
    }

    /**
     * Sets the initial spawn point for the shot-animation.
     *
     * @param cannonTip
     * @param cannonRadius
     */
    public void initSpawnPoint(Point2D cannonTip, int cannonRadius) {
        this.spawnPoint = new Point2D.Double(cannonTip.getX(), cannonTip.getY() - SHOT_ANI_OFFSET_Y);
        this.currentAngle = -90 - Math.toDegrees(Math.atan(cannonRadius / -SHOT_ANI_OFFSET_Y));
    }

    /**
     * Transforms the spawnpoint for the shot-animation according the rotation of the tower.
     *
     * @param rotationalCenter
     * @param angleCorrection
     * @param cannonRadius
     */
    public void updateSpawnPoint(Point rotationalCenter, float angleCorrection, int cannonRadius) {
        this.currentAngle += angleCorrection;
        double angle = Math.toRadians(this.currentAngle);
        this.spawnPoint = new Point2D.Double(cannonRadius * Math.cos(angle) + rotationalCenter.getX(), cannonRadius * Math.sin(angle) + rotationalCenter.getY());
    }
}
