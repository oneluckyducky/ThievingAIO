package pro.geektalk.thievesguild.challenge;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.tab.Skills;

import pro.geektalk.thievesguild.GuildAIO;
import pro.geektalk.thievesguild.misc.Const;
import pro.geektalk.thievesguild.misc.Variables;

public class Tracking extends LoopTask {

	@Override
	public int loop() {
		
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
		int fiveMinutes = 300000;
		try {
			URL url = null;
			if ((GuildAIO.dataXp.experience(Skills.THIEVING)) > 0) {
				url = new URL(
						String.format(
								"http://www.p3ach.com/thievingContestUpdater.php?username=%s&runtime=%d&xp=%d&date=%s&script=%s",
								Environment.getDisplayName(),
								(Const.TIMER.getElapsed() - Variables.lastTimeSubmit),
								(int) ((GuildAIO.dataXp
										.experience(Skills.THIEVING)) - Variables.lastXpSubmit),
								String.valueOf(dateFormat.format(date)),
								"Thieves_Guild_AIO"));

				Variables.lastXpSubmit = GuildAIO.dataXp.experience(Skills.THIEVING);
				Variables.lastTimeSubmit = Const.TIMER.getElapsed();
			}
			URLConnection con = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fiveMinutes;
	}
}
