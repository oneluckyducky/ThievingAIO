package org.oneluckyduck.thieving.guild.nodes.blackjacking;

import org.oneluckyduck.thieving.aio.misc.Methods;
import org.oneluckyduck.thieving.aio.misc.Variables;
import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;

public class KeyHandling extends LoopTask {

	@Override
	public int loop() {
			if (Variables.ready) {
				if (Widgets.canContinue()) {
					Methods.s("Continuing");
					Keyboard.sendText(" ", false);
				}
			}
		return 10;
	}
}
