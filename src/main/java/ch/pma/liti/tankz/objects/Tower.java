package ch.pma.liti.tankz.objects;

import ch.pma.liti.tankz.movement.ITankMovementListener;
import ch.pma.liti.tankz.movement.TowerMovementController;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.annotation.CollisionInfo;
import de.gurkenlabs.litiengine.annotation.EntityInfo;
import de.gurkenlabs.litiengine.annotation.MovementInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.graphics.RenderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

@EntityInfo(width = 91, height = 34)
@MovementInfo(velocity = 70, turnOnMove = false)
@CollisionInfo(collisionBoxWidth = 91, collisionBoxHeight = 34, collision = false)
public class Tower extends Creature implements IUpdateable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Tower.class);
    public static final double INITIAL_X_OFFSET = 20d;
    public static final double INITIAL_Y_OFFSET = 2d;
    private static Tower instance;
    private final TowerMovementController<Tower> towerMovementController;
    private Point cannonTip;
    private float rotationSpeed = 10f;
    private int cannonRadius = 20;

    public static Tower instance() {
        if (instance == null) {
            instance = new Tower();
            instance.setRenderType(RenderType.NORMAL);
        }
        return instance;
    }

    public Point getCannonTip() {
        return cannonTip;
    }

    public void setCannonTip(Point cannonTip) {
        this.cannonTip = cannonTip;
    }


    private Tower() {
        //IMPORTANT: Sprites must respect the naming convention when being imported into the utiLITI
        super("tower1");
        this.towerMovementController = new TowerMovementController<>(this);
        // setup movement controller
        this.addController(towerMovementController);
    }

    @Override
    public void update() {

    }

    public ITankMovementListener getTankMovementListener() {
        return this.towerMovementController;
    }

    public float getTowerRotationSpeed() {
        return this.rotationSpeed;
    }

    public void updateCannonTip(float angle, Point rotationalCenter) {
        float x = (float) (this.cannonRadius * Math.cos(angle) + rotationalCenter.getX());
        float y = (float) (this.cannonRadius * Math.sin(angle) + rotationalCenter.getY());
        this.cannonTip.setLocation(x, y);
    }

    public int getCannonRadius() {
        return this.cannonRadius;
    }
}
