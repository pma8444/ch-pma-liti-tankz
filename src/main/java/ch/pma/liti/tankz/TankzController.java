package ch.pma.liti.tankz;

import ch.pma.liti.tankz.screens.TestScreen;
import de.gurkenlabs.litiengine.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TankzController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TankzController.class);

    public TankzController() {
        init();
    }

    private void init() {
        Game.setInfo("tankz.xml");
        Game.init(null);
        Game.start();
        LOGGER.debug("[{}] version [{}], presented by [{}]", Game.info().getName(), Game.info().getVersion(), Game.info().getCompany());
        Game.screens().add(new TestScreen());
        Game.screens().display("TEST");
    }
}
