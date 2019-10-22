package ch.pma.liti.tankz.objects;

import ch.pma.liti.tankz.movement.ITankMovementListener;
import ch.pma.liti.tankz.movement.TowerMovementController;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.annotation.CollisionInfo;
import de.gurkenlabs.litiengine.annotation.EntityInfo;
import de.gurkenlabs.litiengine.annotation.MovementInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.graphics.RenderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

@EntityInfo(width = 91, height = 34)
@MovementInfo(velocity = 70, turnOnMove = false)
@CollisionInfo(collisionBoxWidth = 91, collisionBoxHeight = 34, collision = false)
public class Tower extends Creature implements IUpdateable, IRenderable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Tower.class);
    private static final int ROT_CENTER_WIDTH_OFFSET = 30;
    public static final double INITIAL_X_OFFSET = 20d;
    public static final double INITIAL_Y_OFFSET = 2d;
    private static Tower instance;
    private final TowerMovementController<Tower> towerMovementController;
    private Point cannonTip;
    private float rotationSpeed = 1f;
    private int cannonRadius = 20;
    private double angleToTurn;
    private float angleCorrection = 0;
    private float currentAngle = 0;

    private Tower() {
        //IMPORTANT: Sprites must respect the naming convention when being imported into the utiLITI
        super("tower1");
        this.towerMovementController = new TowerMovementController<>(this);
        // setup movement controller
        this.addController(towerMovementController);
    }

    public static Tower instance() {
        if (instance == null) {
            instance = new Tower();
            instance.setRenderType(RenderType.NORMAL);
        }

        return instance;
    }

    public Point getCannonTip() {
        return new Point((int)(this.getX() + this.getWidth()), (int)(this.getY() + this.getHeight() / 2));
    }

    @Override
    public void update() {
        if((int) currentAngle != (int) angleToTurn) {
            LOGGER.debug("curr: [{}], target: [{}]", currentAngle, angleToTurn);
            this.angleCorrection = getAngleCorrection();
            this.currentAngle += this.angleCorrection;

            AffineTransform affineTransform = new AffineTransform();
            affineTransform.rotate(Math.toRadians(this.currentAngle), ROT_CENTER_WIDTH_OFFSET, this.getHeight() / 2);
            this.getAnimationController().setAffineTransform(affineTransform);
        }
    }

    public ITankMovementListener getTankMovementListener() {
        return this.towerMovementController;
    }

    public void setAngleToTurn(double angleToTurn) {
        this.angleToTurn = angleToTurn;
    }

    private float getTowerRotationSpeed() {
        return this.rotationSpeed;
    }

    private float getAngleCorrection() {
        float angleCorrection = 0f;
        float currentAngleDifference = (float) (this.angleToTurn - this.currentAngle);
        float normalizedAngleDifference = Math.abs(currentAngleDifference);

        LOGGER.debug("n: [{}]", normalizedAngleDifference);

        if (normalizedAngleDifference > 1 && (normalizedAngleDifference < 359 || normalizedAngleDifference > 361)) {
            angleCorrection = Math.copySign(this.getTowerRotationSpeed(), currentAngleDifference);
            if (normalizedAngleDifference > 180) {
                angleCorrection = angleCorrection * -1;
            }
        }
        return angleCorrection;
    }

    private Point getRotationalCenter() {
        int x = (int) (this.getX() + this.getWidth() / 2 - ROT_CENTER_WIDTH_OFFSET);
        int y = (int) (this.getY() + this.getHeight() / 2);
        return new Point(x, y);
    }

    @Override
    public void render(Graphics2D g) {
        double radius = 5d;
        Game.graphics().renderShape(g, new Ellipse2D.Double(getCannonTip().getX() - radius, getCannonTip().getY() - radius, 2.0 * radius, 2.0 * radius));
    }
}
