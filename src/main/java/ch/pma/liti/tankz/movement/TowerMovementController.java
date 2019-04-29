package ch.pma.liti.tankz.movement;

import de.gurkenlabs.litiengine.entities.IMobileEntity;
import de.gurkenlabs.litiengine.physics.MovementController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class TowerMovementController<T extends IMobileEntity> extends MovementController<T> implements ITankMovementListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TowerMovementController.class);
    private static final int REVERSE_VELOCITY = -1;
    private static final int NO_VELOCITY = 0;
    private final Point rotationalCenter;
    private double actAngle = 0;

    public TowerMovementController(T mobileEntity, Point rotationalCenter) {
        super(mobileEntity);
        this.rotationalCenter = rotationalCenter;
//        Input.mouse().onMoved(this::handleMouseMovement);
    }

    private void handleMouseMovement(MouseEvent mouseEvent) {
        double rad = Math.toRadians(this.getAngle());

//        if (keyCode.getKeyCode() == KeyEvent.VK_W) {
//            this.setDx((float) Math.cos(rad));
//            this.setDy((float) Math.sin(rad));
//            this.setVelocityX(this.getDx());
//            this.setVelocityY(this.getDy());
//        } else if (keyCode.getKeyCode() == KeyEvent.VK_S) {
//            this.setDx((float) Math.cos(rad));
//            this.setDy((float) Math.sin(rad));
//            this.setVelocityX(this.getDx()* REVERSE_VELOCITY);
//            this.setVelocityY(this.getDy()* REVERSE_VELOCITY);
//        } else if (keyCode.getKeyCode() == KeyEvent.VK_A) {
//            this.actAngle--;
//        } else if (keyCode.getKeyCode() == KeyEvent.VK_D) {
//            this.actAngle++;
//        }

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(rad, this.rotationalCenter.getX(), this.rotationalCenter.getY());
        this.getEntity().getAnimationController().setAffineTransform(affineTransform);
    }

    private double getAngle() {
        return this.actAngle;
    }

    @Override
    public void handleMovement() {
        this.moveEntity(this.getVelocityX(), this.getVelocityY());
        this.setVelocityX(NO_VELOCITY);
        this.setVelocityY(NO_VELOCITY);
    }

    @Override
    public void tankMovement(double velocityX, double velocityY) {
        this.setVelocityX(velocityX);
        this.setVelocityY(velocityY);
    }
}
