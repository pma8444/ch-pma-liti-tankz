package ch.pma.liti.tankz.movement;

import ch.pma.liti.tankz.objects.Tower;
import de.gurkenlabs.litiengine.entities.IMobileEntity;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.physics.MovementController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.MouseEvent;

public class TowerMovementController<T extends IMobileEntity> extends MovementController<T> implements ITankMovementListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TowerMovementController.class);
    private static final int REVERSE_VELOCITY = -1;
    private static final int NO_VELOCITY = 0;
    private final Tower tower;

    public TowerMovementController(T mobileEntity) {
        super(mobileEntity);
        this.tower = (Tower) mobileEntity;
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

    private void handleMouseMovement(MouseEvent mouseEvent) {
        float xDistance = (float) (mouseEvent.getPoint().getX() - this.tower.getRotationalCenter().getX());
        float yDistance = (float) (mouseEvent.getPoint().getY() - this.tower.getRotationalCenter().getY());
        this.tower.setAngleToTurn(Math.toDegrees(Math.atan2(yDistance, xDistance)));
    }
}
