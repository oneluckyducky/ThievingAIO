package pro.geektalk.thievesguild.nodes.blackjacking;

import java.awt.Point;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.NPC;

import pro.geektalk.thievesguild.misc.Methods;
import pro.geektalk.thievesguild.misc.Variables;

public class Looting extends Node {
	@Override
	public void execute() {
		final NPC trainer = NPCs.getNearest(Variables.npcFilter);
		if (trainer.getAnimation() == 12413) {
			final Timer timer = new Timer(10000);
			Methods.s("He is knocked out, looting");
			if(!trainer.isOnScreen()) Camera.turnTo(trainer);
			while (trainer.getAnimation() == 12413 && timer.isRunning()) {
				Methods.openMenu(trainer, true);
				if (Menu.isOpen()) {
					final Point p = Mouse.getLocation();
					Mouse.hop(p.x, p.y + Random.nextInt(42, 48));
					Mouse.click(true);
				} else {
					Methods.openMenu(trainer, true);
				}
			}
		}
	}

	@Override
	public boolean activate() {
		final NPC trainer = NPCs.getNearest(Variables.npcFilter);
		return Methods.isInGame() && trainer != null
				&& trainer.getAnimation() == 12413;
	}
}