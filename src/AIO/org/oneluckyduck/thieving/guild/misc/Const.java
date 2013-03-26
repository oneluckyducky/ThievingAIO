package org.oneluckyduck.thieving.guild.misc;

import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.interactive.NPC;

public class Const {

	public static final int PICKPOCKET_TRAINER_ID = 11281;
	public static final int ADVANCED_TRAINER_ID = 11297;

	public static final int[] PICKPOCKET_VOLUNTEER_IDS = { 11283, 11285, 11287 };
	public static final int[] COSHING_VOLUNTEER_IDS = { 11289, 11291, 11293 };

	public static final Filter<NPC> PICKPOCKET_TRAINER_FILTER = new Filter<NPC>() {

		@Override
		public boolean accept(NPC n) {
			return n.getId() == PICKPOCKET_TRAINER_ID
					&& n.getLocation().canReach();
		}

	};

	public static final Filter<NPC> ADVANCED_TRAINER_FILTER = new Filter<NPC>() {

		@Override
		public boolean accept(NPC n) {
			return n.getId() == ADVANCED_TRAINER_ID
					&& n.getLocation().canReach();
		}

	};

	public static final Filter<NPC> PICKPOCKET_VOLUNTEER_FILTER = new Filter<NPC>() {

		@Override
		public boolean accept(NPC n) {
			for (int id : PICKPOCKET_VOLUNTEER_IDS) {
				return n.getId() == id && n.getLocation().canReach();
			}
			return false;
		}

	};
	public static final Filter<NPC> COSHING_VOLUNTEER_FILTER = new Filter<NPC>() {

		@Override
		public boolean accept(NPC n) {
			for (int id : COSHING_VOLUNTEER_IDS) {
				return n.getId() == id && n.getLocation().canReach();
			}
			return false;
		}

	};
}
