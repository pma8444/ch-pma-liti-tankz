package ch.pma.liti.tankz.screens;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.resources.Fonts;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.FontUtilities;

import java.awt.*;

public class TestScreen extends GameScreen {

    public TestScreen() {
        super("TEST");
    }

    @Override
    public void render(final Graphics2D g) {
        super.render(g);

        Resources.fonts().add("supaFont", new Font("standard 07_57", Font.PLAIN, 24));
        g.setFont(Resources.fonts().get("supaFont"));
        g.setColor(Color.RED);
        TextRenderer.render(g, "Hoi", 30, 30);
    }
}
