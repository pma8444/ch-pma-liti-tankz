package ch.pma.liti.tankz.movement;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IMobileEntity;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.physics.MovementController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class TankMovementController<T extends IMobileEntity> extends MovementController<T> {

    private static final int REVERSE_VELOCITY = -1;
    private static final int NO_VELOCITY = 0;
    private static final Logger LOGGER = LoggerFactory.getLogger(TankMovementController.class);
    private final Point rotationalCenter;
    private final ITankMovementListener tankMovementListener;
    private double actAngle = 0;

    public TankMovementController(T mobileEntity, Point rotationalCenter, ITankMovementListener tankMovementListener) {
        super(mobileEntity);
        this.rotationalCenter = rotationalCenter;
        this.tankMovementListener = tankMovementListener;
        Input.keyboard().onKeyPressed(this::handlePressedKey);
    }

    private void handlePressedKey(final KeyEvent keyCode) {
        double rad = Math.toRadians(this.getAngle());
        if (keyCode.getKeyCode() == KeyEvent.VK_W) {
            this.setDx((float) Math.cos(rad));
            this.setDy((float) Math.sin(rad));
            this.setVelocityX(this.getDx());
            this.setVelocityY(this.getDy());
        } else if (keyCode.getKeyCode() == KeyEvent.VK_S) {
            this.setDx((float) Math.cos(rad));
            this.setDy((float) Math.sin(rad));
            this.setVelocityX(this.getDx()* REVERSE_VELOCITY);
            this.setVelocityY(this.getDy()* REVERSE_VELOCITY);
        } else if (keyCode.getKeyCode() == KeyEvent.VK_A) {
            this.actAngle--;
        } else if (keyCode.getKeyCode() == KeyEvent.VK_D) {
            this.actAngle++;
        }
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(rad, this.rotationalCenter.getX(), this.rotationalCenter.getY());
        this.getEntity().getAnimationController().setAffineTransform(affineTransform);
    }

    private double getAngle() {
        return this.actAngle;
    }

    @Override
    public void handleMovement() {
        final Point2D oldLocation = this.getEntity().getLocation();

        Game.physics().move(this.getEntity(), getPreferredTargetLocation());

        final Point2D newLocation = this.getEntity().getLocation();
        double deltaX = newLocation.getX() - oldLocation.getX();
        double deltaY = newLocation.getY() - oldLocation.getY();

        this.tankMovementListener.tankMovement(deltaX, deltaY);

        this.setVelocityX(NO_VELOCITY);
        this.setVelocityY(NO_VELOCITY);
    }

    private Point2D getPreferredTargetLocation() {
        return new Point2D.Double(this.getEntity().getX() + this.getVelocityX(), this.getEntity().getY() + this.getVelocityY());
    }
}
