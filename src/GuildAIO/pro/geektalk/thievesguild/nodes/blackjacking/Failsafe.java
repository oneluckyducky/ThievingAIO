package pro.geektalk.thievesguild.nodes.blackjacking;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.wrappers.interactive.NPC;

import pro.geektalk.thievesguild.misc.Const;
import pro.geektalk.thievesguild.misc.Methods;
import pro.geektalk.thievesguild.misc.Variables;

public class Failsafe extends Node {

	@Override
	public boolean activate() {
		final NPC trainer = NPCs.getNearest(Variables.npcFilter);
		return Methods.isInGame() && trainer != null
				&& !trainer.getLocation().canReach();
	}

	@Override
	public void execute() {
		if (Methods.isInGame())
			Methods.stopScript("Advanced trainer is out of reach. Stopping script after running for "
					+ Const.TIMER.toElapsedString());

	}

}
