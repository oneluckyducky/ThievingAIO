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
import pro.geektalk.thievesguild.misc.enums.Gates;
import pro.geektalk.thievesguild.misc.enums.Tools;

public class OpenSouthGates extends Node {

	@Override
	public boolean activate() {
		final SceneObject gate = SceneEntities.getNearest(Gates.SOUTH
				.getClosedId());
		Methods.s("South gate");
		return Methods.isInGame() && gate != null
				&& Methods.inventoryContains(Tools.LOCKPICK.getId());
	}

	@Override
	public void execute() {
		final SceneObject gate = SceneEntities.getNearest(Gates.SOUTH
				.getClosedId());
		if (gate != null && Calculations.distanceTo(gate) > 5) {
			Methods.s("South gate is far");
			new Tile(4777, 5787, 0).clickOnMap();
		}
		if (gate != null && gate.isOnScreen()) {
			Methods.s("Opening southern gate");
			Methods.openMenu(gate, false);
			if (Menu.isOpen() && Menu.contains("Open", "Door")) {
				Menu.select("Open", "Door");
				Task.sleep(1000);
			} else if (!Menu.contains("Open", "Door")) {
				Methods.s("Whoops! I missed");
				final Point p = Mouse.getLocation();
				int r = Random.nextInt(40, 109);
				Mouse.move(p.x + r, p.y + r);
			}
		} else if (!gate.isOnScreen()) {
			Methods.s("Finding closed gate");
			Camera.turnTo(gate);
		}
	}
}
