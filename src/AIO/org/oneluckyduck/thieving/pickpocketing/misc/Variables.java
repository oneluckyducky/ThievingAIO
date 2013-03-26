package org.oneluckyduck.thieving.pickpocketing.misc;

import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.interactive.NPC;

public class Variables {

	public static String npcName = "";

	public static Filter<NPC> npcFilter = new Filter<NPC>() {
		@Override
		public boolean accept(NPC n) {
			return n.getName().toLowerCase().equals(npcName.toLowerCase())
					&& n.getLocation().canReach();
		}
	};
}
