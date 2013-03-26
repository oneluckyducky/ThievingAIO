package pro.geektalk.thievesguild.nodes.pickpocketing;

import java.awt.Point;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.NPC;

import pro.geektalk.thievesguild.misc.Methods;
import pro.geektalk.thievesguild.misc.Variables;

public class Pickpocket extends Node {

	@Override
	public boolean activate() {
		final NPC trainer = NPCs.getNearest(Variables.npcFilter);
		return Methods.isInGame() && trainer != null
				&& !Methods.isPlayerStunned()
				&& !Players.getLocal().isInCombat();
	}

	@Override
	public void execute() {
		final NPC trainer = NPCs.getNearest(Variables.npcFilter);
		Methods.s("Opening menu");
		if (!trainer.isOnScreen())
			Camera.turnTo(trainer);
		Methods.openMenu(trainer, true);
		Methods.s("Waiting for menu");
		final Timer t = new Timer(2000);
		while (!Menu.isOpen()) {
			Task.sleep(5);
			if (!t.isRunning())
				break;
		}
		if (Menu.isOpen() && Menu.contains("Pickpocket")) {
			Methods.s("Selecting pickpocket");
			final Point p = Mouse.getLocation();
			Mouse.hop(p.x, p.y + Random.nextInt(40, 45));
			Mouse.click(true);
			Task.sleep(100, 200);
		} else if (!Menu.contains("Pickpocket")) {
			Methods.s("Whoops! I missed");
			final Point p = Mouse.getLocation();
			int r = Random.nextInt(40, 109);
			Mouse.move(p.x + r, p.y + r);
		}
	}

}
