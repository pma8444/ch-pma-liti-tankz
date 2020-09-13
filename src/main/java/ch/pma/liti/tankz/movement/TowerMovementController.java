package ch.pma.liti.tankz.movement;

import ch.pma.liti.tankz.objects.Tower;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IMobileEntity;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.physics.MovementController;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TowerMovementController<T extends IMobileEntity> extends MovementController<T> implements ITankMovementListener {

    private static final int NO_VELOCITY = 0;
    private final Tower tower;
    private Point mouseLocation;

    public TowerMovementController(T mobileEntity) {
        super(mobileEntity);
        this.tower = (Tower) mobileEntity;
        Input.mouse().onMoved(this::handleMouseMovement);
        mouseLocation = new Point((int)Input.mouse().getLocation().getX(), (int)Input.mouse().getLocation().getY());
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
        updateAngleToTurn();
    }

    private void handleMouseMovement(MouseEvent mouseEvent) {
        setMouseLocation(mouseEvent.getPoint());
        updateAngleToTurn();
    }

    private synchronized void updateAngleToTurn() {
        float xDistance = (float) (mouseLocation.getX() / Game.graphics().getBaseRenderScale() - this.tower.getRotationalCenter().getX());
        float yDistance = (float) (mouseLocation.getY() / Game.graphics().getBaseRenderScale() - this.tower.getRotationalCenter().getY());
        this.tower.setAngleToTurn(Math.toDegrees(Math.atan2(yDistance, xDistance)));
    }

    private void setMouseLocation(Point point) {
        this.mouseLocation = point;
    }
}
