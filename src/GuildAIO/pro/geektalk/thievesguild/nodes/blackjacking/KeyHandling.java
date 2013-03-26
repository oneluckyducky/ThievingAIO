package pro.geektalk.thievesguild.nodes.blackjacking;

import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;

import pro.geektalk.thievesguild.misc.Methods;
import pro.geektalk.thievesguild.misc.Variables;

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
