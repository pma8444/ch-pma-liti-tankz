package ch.pma.liti.tankz;

import ch.pma.liti.tankz.movement.ITankMovementListener;
import ch.pma.liti.tankz.objects.Tank;
import ch.pma.liti.tankz.objects.Tower;
import ch.pma.liti.tankz.screens.InGameScreen;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.FreeFlightCamera;
import de.gurkenlabs.litiengine.resources.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TankzController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TankzController.class);

    public TankzController() {
        init();
    }

    private void init() {
        Game.setInfo("tankz.xml");
        Game.allowDebug(true);
        Game.init(null);

        LOGGER.debug("[{}] version [{}], presented by [{}]", Game.info().getName(), Game.info().getVersion(), Game.info().getCompany());

        Resources.load("game.litidata");
        Camera camera = new FreeFlightCamera();
        camera.setClampToMap(true);
        Game.world().setCamera(camera);

        Game.graphics().setBaseRenderScale(1f);

        // set a basic gravity for all levels.
        Game.world().setGravity(0);

        // add default game logic for when a level was loaded
        Game.world().addLoadedListener(e -> {

            // spawn the player instance on the spawn point with the name "spawnTank"
            Spawnpoint enter = e.getSpawnpoint("spawnTank");
            if (enter != null) {
                Tower tower = Tower.instance();
                ITankMovementListener tankMovementListener = tower.getTankMovementListener();
                Tank tank = Tank.instance(tankMovementListener);
                enter.spawn(tank);
                enter.spawn(tower);
                tower.setX(tower.getX()+Tower.INITIAL_X_OFFSET);
                tower.setY(tower.getY()+Tower.INITIAL_Y_OFFSET);
            }
        });

        Game.world().loadEnvironment("tankz_level_1");
        Game.screens().add(new InGameScreen());
        Game.screens().display(InGameScreen.NAME);

        Game.start();
    }
}
