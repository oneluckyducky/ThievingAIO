package pro.geektalk.thievesguild.nodes.chests;

import java.awt.Point;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import pro.geektalk.thievesguild.misc.Methods;
import pro.geektalk.thievesguild.misc.enums.Chests;

public class OpenNorthChests extends Node {

	@Override
	public boolean activate() {
		final SceneObject chest = SceneEntities.getNearest(Chests.NORTH
				.getClosedId());
		Methods.s("North chest");
		return Methods.isInGame() && chest != null
				&& chest.getLocation().getY() < 5793;
	}

	@Override
	public void execute() {
		final SceneObject chest = SceneEntities.getNearest(Chests.NORTH
				.getClosedId());
		if (chest != null && Calculations.distanceTo(chest) > 5) {
			Methods.s("North chest is far");
			new Tile(4777, 5787, 0).clickOnMap();
		}
		if (chest != null && chest.isOnScreen()) {
			Methods.s("Opening northern chest");
			if (Random.nextInt(0, 48) < 5) {
				Methods.openMenu(chest, false);
				if (Menu.isOpen() && Menu.contains("Open", "Practice chest")) {
					Menu.select("Open", "Practice chest");
					Task.sleep(1000);
				} else if (!Menu.contains("Open", "Practice chest")) {
					Methods.s("Whoops! I missed");
					final Point p = Mouse.getLocation();
					int r = Random.nextInt(40, 109);
					Mouse.move(p.x + r, p.y + r);
				}
			}
		} else if (!chest.isOnScreen()) {
			Methods.s("Finding closed chest");
			Camera.turnTo(chest);
		}
	}

}
