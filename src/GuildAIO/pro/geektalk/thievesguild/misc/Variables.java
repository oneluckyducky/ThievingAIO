package pro.geektalk.thievesguild.misc;

import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.interactive.NPC;

public class Variables {
	public static Long startTime;

	public static String status = "";

	public static int startingExperience, startingLevel, actions, gainedLevels,
			world, mode, foodId = -1;

	public static int loop = 100;

	public static boolean ready, jacking = false;

	public static Filter<NPC> npcFilter;
	

	public static int lastXpSubmit = 0;
	public static long lastTimeSubmit = 0;

}
