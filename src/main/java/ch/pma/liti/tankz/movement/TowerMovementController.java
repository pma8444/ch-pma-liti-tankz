package ch.pma.liti.tankz.movement;

import ch.pma.liti.tankz.objects.Tower;
import de.gurkenlabs.litiengine.entities.IMobileEntity;
import de.gurkenlabs.litiengine.input.Input;
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
    private final Tower tower;
    private double actAngle = 0;
    private float currentAngle = 0;

    public TowerMovementController(T mobileEntity) {
        super(mobileEntity);
        this.tower = (Tower) mobileEntity;
        this.tower.setCannonTip(getRotationalCenter());
        this.tower.getCannonTip().x = (int)this.tower.getCannonTip().getX() + this.tower.getCannonRadius();
        LOGGER.debug("Initial CannonTip Pos [{}]", this.tower.getCannonTip());
        Input.mouse().onMoved(this::handleMouseMovement);
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

    private float getAngleCorrection(float angleToTurn, float currentAngle) {

        float angleCorrection = 0f;
        float currentAngleDifference = angleToTurn - currentAngle;
        float normalizedAngleDifference = Math.abs(currentAngleDifference);

        if (normalizedAngleDifference > 1 && (normalizedAngleDifference < 359 || normalizedAngleDifference > 361)) {
            angleCorrection = Math.copySign(this.tower.getTowerRotationSpeed(), currentAngleDifference);
            if (normalizedAngleDifference > 180) {
                angleCorrection = angleCorrection * -1;
            }
        }
        return angleCorrection;
    }

    private Point getRotationalCenter() {
        int x = (int) (super.getEntity().getX() + super.getEntity().getWidth() / 2);
        int y = (int) (super.getEntity().getY() + super.getEntity().getHeight() / 2);
        return new Point(x, y);
    }

    private void handleMouseMovement(MouseEvent mouseEvent) {
        LOGGER.debug("MouseEvent Pos [{}]", mouseEvent.getPoint());
        LOGGER.debug("CannonTip Pos [{}]", this.tower.getCannonTip());
        float xDistance = (float) (mouseEvent.getPoint().getX() - this.tower.getCannonTip().getX());
        float yDistance = (float) (mouseEvent.getPoint().getY() - this.tower.getCannonTip().getY());
        double angleToTurn = Math.toDegrees(Math.atan2(yDistance, xDistance));
        LOGGER.debug("Angle to turn [{}]", angleToTurn);
        this.currentAngle = getAngleCorrection((float) angleToTurn, this.currentAngle);
        LOGGER.debug("Setting current tower rotation to [{}] degrees", this.currentAngle);
        this.tower.updateCannonTip(this.currentAngle, getRotationalCenter());


        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(this.currentAngle), getRotationalCenter().getX(), getRotationalCenter().getY());
        this.getEntity().getAnimationController().setAffineTransform(affineTransform);
    }
}
