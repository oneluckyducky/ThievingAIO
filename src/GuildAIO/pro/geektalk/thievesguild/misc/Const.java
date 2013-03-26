package pro.geektalk.thievesguild.misc;

import java.awt.Image;

import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.NPC;

public class Const {

	public static final Timer TIMER = new Timer(0);

	public static final double VERSION = 1.31;
	public static final String DESCRIPTION = "Thieves Guild. Blackjack. Pickpocket. Chests";
	public static final String WEBSITE = "http://www.powerbot.org/community/topic/882166-thieves-guild-aio-blackjacking-pickpocketing-practice-chest-looting-fast-stable/";

	public final static Image BAR = Methods.getImage("http://puu.sh/1p9xg.png");

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
