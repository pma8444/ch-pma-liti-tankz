package ch.pma.liti.tankz.objects;

import ch.pma.liti.tankz.movement.ITankMovementListener;
import ch.pma.liti.tankz.movement.TankMovementController;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.annotation.CollisionInfo;
import de.gurkenlabs.litiengine.annotation.EntityInfo;
import de.gurkenlabs.litiengine.annotation.MovementInfo;
import de.gurkenlabs.litiengine.entities.Creature;

import java.awt.*;

@EntityInfo(width = 81, height = 40)
@MovementInfo(velocity = 70, turnOnMove = false)
@CollisionInfo(collisionBoxWidth = 81, collisionBoxHeight = 40, collision = true)
public class Tank extends Creature implements IUpdateable {

    public Tank(ITankMovementListener tankMovementListener) {
        //IMPORTANT: Sprites must follow the naming convention when being imported into the utiLITI
        super("tank1");
        // setup movement controller
        this.addController(new TankMovementController<>(this, new Point((int)this.getWidth() / 2, (int)this.getHeight() / 2), tankMovementListener));
    }

    @Override
    public void update() {

    }
}
