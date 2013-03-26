package org.oneluckyduck.thieving.pickpocketing.nodes;

import org.oneluckyduck.thieving.aio.misc.Methods;
import org.oneluckyduck.thieving.aio.misc.Variables;
import org.powerbot.core.script.job.state.Node;

public class Eating extends Node {

	@Override
	public boolean activate() {
		return Methods.isInGame() && Methods.getLifePoints() <= 600 && Methods.inventoryContains(Variables.foodId);
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
