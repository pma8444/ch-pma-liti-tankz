package ch.pma.liti.tankz.objects;

import ch.pma.liti.tankz.movement.ITankMovementListener;
import ch.pma.liti.tankz.movement.TowerMovementController;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.graphics.RenderType;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public abstract class AbstractTower extends Creature {

    private final TowerMovementController<AbstractTower> towerMovementController;
    protected AffineTransform affineTransform;
    Point2D cannonTip;
    double angleToTurn;
    float currentAngle = 0;

    AbstractTower(String spritePrefix) {
        super(spritePrefix);
        this.towerMovementController = new TowerMovementController<>(this);
        this.addController(towerMovementController);
        this.cannonTip = new Point();
        this.setRenderType(RenderType.NORMAL);
        this.affineTransform = new AffineTransform();
    }

    Point2D getCannonTip() {
        return this.cannonTip;
    }

    void setCannonTip(Point2D cannonTip) {
        this.cannonTip = cannonTip;
    }

    public ITankMovementListener getTankMovementListener() {
        return this.towerMovementController;
    }

    public void setAngleToTurn(double angleToTurn) {
        this.angleToTurn = angleToTurn;
    }

    protected abstract float getTowerRotationSpeed();

    protected abstract float getAngleCorrection();

    public abstract Point getRotationalCenter();

    protected abstract void updateCannonTip();

    public abstract void initCannonShotController();
}
