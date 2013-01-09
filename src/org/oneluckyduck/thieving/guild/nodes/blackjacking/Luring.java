package org.oneluckyduck.thieving.guild.nodes.blackjacking;

import java.awt.Point;

import org.oneluckyduck.thieving.aio.misc.Methods;
import org.oneluckyduck.thieving.guild.misc.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.NPC;

public class Luring extends Node {

	@Override
	public void execute() {
		final NPC trainer = NPCs.getNearest(Variables.npcFilter);
		if (trainer != null) {
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
			if (Menu.isOpen() && Menu.contains("Lure")) {
				Methods.s("Selecting lure");
				Menu.select("Lure");
				Methods.s("Sleeping");
				final Timer timer = new Timer(2000);
				while (timer.isRunning()) {
					Task.sleep(5);
				}
				Methods.s("Opening menu");
				Methods.openMenu(trainer, true);
				Methods.s("Waiting for menu");
				while (!Menu.isOpen()) {
					Task.sleep(5);
				}
				if (Menu.isOpen() && Menu.contains("Knock-out")) {
					Methods.s("Knocking the fatass out");
					Menu.select("Knock-out");
					final Timer timer1 = new Timer(5600);
					while (trainer.getAnimation() != 12413) {
						Task.sleep(10);
						if (!timer1.isRunning()) {
							Methods.s("Think i failed");
							break;
						}
					}
				}
			} else if (!Menu.contains("Lure")) {
				Methods.s("Whoops! I missed");
				final Point p = Mouse.getLocation();
				int r = Random.nextInt(40, 109);
				Mouse.move(p.x + r, p.y + r);
			}
		} else {
			Methods.s("Null");
		}
	}

	@Override
	public boolean activate() {
		final NPC trainer = NPCs.getNearest(Variables.npcFilter);
		return Methods.isInGame() && trainer != null
				&& trainer.getAnimation() != 12413;
	}
}