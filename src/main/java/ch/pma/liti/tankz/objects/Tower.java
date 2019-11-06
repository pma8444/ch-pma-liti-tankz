package ch.pma.liti.tankz.objects;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.annotation.CollisionInfo;
import de.gurkenlabs.litiengine.annotation.EntityInfo;
import de.gurkenlabs.litiengine.annotation.MovementInfo;
import de.gurkenlabs.litiengine.graphics.IRenderable;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

@EntityInfo(width = 91, height = 34)
@MovementInfo(velocity = 70, turnOnMove = false)
@CollisionInfo(collisionBoxWidth = 91, collisionBoxHeight = 34, collision = false)
public class Tower extends AbstractTower implements IUpdateable, IRenderable {

    //TODO: Config options combined with sprite-prefix
    private static final int ROT_CENTER_X_OFFSET = 20;
    private static final int ROT_CENTER_Y_OFFSET = 0;
    public static final double INITIAL_X_OFFSET = 20d;
    public static final double INITIAL_Y_OFFSET = 2d;
    private static final int CANNON_RADIUS = 70;

    public Tower() {
        //IMPORTANT: Sprites must respect the naming convention when being imported into the utiLITI
        super("tower1");
    }

    @Override
    public void update() {
        float angleCorrection = getAngleCorrection();
        this.currentAngle += angleCorrection;

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(this.currentAngle), ROT_CENTER_X_OFFSET, this.getHeight() / 2 + ROT_CENTER_Y_OFFSET);
        this.getAnimationController().setAffineTransform(affineTransform);

        updateCannonTip();
    }

    @Override
    public void render(Graphics2D g) {
        double radius = 2d;
        Game.graphics().renderShape(g, new Ellipse2D.Double(getCannonTip().getX() - radius, getCannonTip().getY() - radius, 2.0 * radius, 2.0 * radius));
        Game.graphics().renderShape(g, new Ellipse2D.Double(getRotationalCenter().getX() - radius, getRotationalCenter().getY() - radius, 2.0 * radius, 2.0 * radius));
    }

    @Override
    public Point getRotationalCenter() {
        int x = (int) (this.getX() + ROT_CENTER_X_OFFSET);
        int y = (int) (this.getY() + this.getHeight() / 2 + ROT_CENTER_Y_OFFSET);
        return new Point(x, y);
    }

    @Override
    protected float getTowerRotationSpeed() {
        //TODO: Config option
        float rotationSpeed = 1f;
        return rotationSpeed;
    }

    @Override
    protected float getAngleCorrection() {
        float angleCorrection = 0f;
        float currentAngleDifference = (float) (this.angleToTurn - this.currentAngle);
        float normalizedAngleDifference = Math.abs(currentAngleDifference);

        if(normalizedAngleDifference > 360) {
            normalizedAngleDifference -= 360;
        }

        if (normalizedAngleDifference > 1 && (normalizedAngleDifference < 359)) {
            angleCorrection = Math.copySign(this.getTowerRotationSpeed(), currentAngleDifference);
            if (normalizedAngleDifference > 180) {
                angleCorrection = angleCorrection * -1;
            }
        }
        return angleCorrection;
    }

    @Override
    protected void updateCannonTip() {
        float angle = (float) Math.toRadians(this.currentAngle);
        float x = (float) (CANNON_RADIUS * Math.cos(angle) + getRotationalCenter().getX());
        float y = (float) (CANNON_RADIUS * Math.sin(angle) + getRotationalCenter().getY());
        this.cannonTip.setLocation(x, y);
    }
}
