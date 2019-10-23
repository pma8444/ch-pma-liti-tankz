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
    private static final int ROT_CENTER_X_OFFSET = 20;
    private static final int ROT_CENTER_Y_OFFSET = 0;
    public static final double INITIAL_X_OFFSET = 20d;
    public static final double INITIAL_Y_OFFSET = 2d;
    private static Tower instance;
    private final TowerMovementController<Tower> towerMovementController;
    private Point cannonTip;
    private int cannonRadius = 70;
    private double angleToTurn;
    private float currentAngle = 0;

    private Tower() {
        //IMPORTANT: Sprites must respect the naming convention when being imported into the utiLITI
        super("tower1");
        this.towerMovementController = new TowerMovementController<>(this);
        // setup movement controller
        this.addController(towerMovementController);
        this.cannonTip = new Point();
    }

    public static Tower instance() {
        if (instance == null) {
            instance = new Tower();
            instance.setRenderType(RenderType.NORMAL);
        }

        return instance;
    }

    public void updateCannonTip() {
        float angle = (float) Math.toRadians(this.currentAngle);
        float x = (float) (this.cannonRadius * Math.cos(angle) + getRotationalCenter().getX());
        float y = (float) (this.cannonRadius * Math.sin(angle) + getRotationalCenter().getY());
        this.cannonTip.setLocation(x, y);
    }

    @Override
    public void update() {
        LOGGER.debug("curr: [{}], target: [{}]", currentAngle, angleToTurn);
        float angleCorrection = getAngleCorrection();
        this.currentAngle += angleCorrection;

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(this.currentAngle), ROT_CENTER_X_OFFSET, this.getHeight() / 2 + ROT_CENTER_Y_OFFSET);
        this.getAnimationController().setAffineTransform(affineTransform);

        updateCannonTip();
    }

    public ITankMovementListener getTankMovementListener() {
        return this.towerMovementController;
    }

    public void setAngleToTurn(double angleToTurn) {
        this.angleToTurn = angleToTurn;
    }

    @Override
    public void render(Graphics2D g) {
        double radius = 2d;
        Game.graphics().renderShape(g, new Ellipse2D.Double(getCannonTip().getX() - radius, getCannonTip().getY() - radius, 2.0 * radius, 2.0 * radius));
        Game.graphics().renderShape(g, new Ellipse2D.Double(getRotationalCenter().getX() - radius, getRotationalCenter().getY() - radius, 2.0 * radius, 2.0 * radius));
    }

    public Point getCannonTip() {
        return this.cannonTip;
    }

    private float getTowerRotationSpeed() {
        float rotationSpeed = 1f;
        return rotationSpeed;
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
        int x = (int) (this.getX() + ROT_CENTER_X_OFFSET);
        int y = (int) (this.getY() + this.getHeight() / 2 + ROT_CENTER_Y_OFFSET);
        return new Point(x, y);
    }
}
