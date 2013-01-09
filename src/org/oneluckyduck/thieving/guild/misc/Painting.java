package org.oneluckyduck.thieving.guild.misc;

import java.awt.Graphics2D;
import java.text.DecimalFormat;
import org.oneluckyduck.thieving.aio.misc.Variables;
import org.oneluckyduck.thieving.aio.misc.Methods;

public class Painting {
	public static void paint(final Graphics2D g) {
		
		final int blackjacksHour = Methods.getPerHour(Variables.blackjacks,
				Variables.startTime);

		final String blackjacks = DecimalFormat.getInstance().format(
				Variables.blackjacks);
		
		final String blackjacksHourly = DecimalFormat.getInstance().format(
				blackjacksHour);
		
		g.drawString(String.format("Blackjacks (hr): %s (%s)", blackjacks,
				blackjacksHourly), 180, 12);
		
	}
}
