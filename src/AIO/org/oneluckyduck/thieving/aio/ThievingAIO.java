package org.oneluckyduck.thieving.aio;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.oneluckyduck.thieving.aio.misc.Const;
import org.oneluckyduck.thieving.aio.misc.GUI;
import org.oneluckyduck.thieving.aio.misc.Methods;
import org.oneluckyduck.thieving.aio.misc.Variables;
import org.oneluckyduck.thieving.aio.misc.skilldata.SkillData;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.input.Mouse.Speed;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.WidgetCache;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.bot.Context;
import org.powerbot.game.client.Client;

@Manifest(authors = { "OneLuckyDuck" }, name = "Thieving AIO", version = 1.0, description = "All-In-One Thieving. Chests. Stalls. Guild. Pickpocketing.")
public class ThievingAIO extends ActiveScript implements PaintListener,
		MessageListener {
	Client client;
	public static Tree jobContainer = null;
	public static List<Node> jobs = Collections
			.synchronizedList(new ArrayList<Node>());
	boolean world = false;

	private final RenderingHints antialiasing = new RenderingHints(
			RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	public void onStart() {
		Mouse.setSpeed(Speed.FAST);
		Variables.startTime = System.currentTimeMillis();
		Variables.startingExperience = Settings.get(91) / 10;
		try {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					final String input = (String) JOptionPane.showInputDialog(
							null, "Choose the world to relog into");
					Variables.world = input != null && input.length() > 0 ? Integer
							.parseInt(input) : Random.nextInt(62, 79);
					world = true;
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (!world) {
			Task.sleep(150);
		}
		Methods.s(String.format("Logging into world %d", Variables.world));
		Context.setLoginWorld(Variables.world);

		Variables.startingLevel = SkillData.getLevel(Skills.THIEVING);
		Methods.s(String
				.format("Starting at level %d", Variables.startingLevel));
		Tabs.INVENTORY.open();
		try {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					GUI gui = new GUI();
					gui.setResizable(false);
					gui.setAlwaysOnTop(true);
					gui.setVisible(true);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		Methods.setTracking();
		while (!Variables.ready) {
			Task.sleep(100);
		}
	}

	@Override
	public int loop() {
		if (Game.getClientState() != Game.INDEX_MAP_LOADED || !Variables.ready) {
			return 1000;
		}
		if (client != Context.client()) {
			WidgetCache.purge();
			Context.get().getEventManager().addListener(this);
			client = Context.client();
		}
		if (jobContainer != null) {
			final Node job = jobContainer.state();
			if (job != null) {
				jobContainer.set(job);
				getContainer().submit(job);
				job.join();
			}
		} else {
			jobContainer = new Tree(jobs.toArray(new Node[jobs.size()]));
		}
		return 100;
	}

	@Override
	public void onRepaint(Graphics g1) {
		if (Game.getClientState() != Game.INDEX_MAP_LOADED) {
			return;
		}
		if (Variables.ready) {
			final Graphics2D g = (Graphics2D) g1;
			g.setRenderingHints(antialiasing);
			final Point mouse = Mouse.getLocation();

			g.setColor(Mouse.isPressed() ? Color.WHITE.brighter() : Color.BLACK
					.darker());
			final Dimension game = Game.getDimensions();
			g.drawLine(mouse.x + game.width, mouse.y, mouse.x - game.width,
					mouse.y);
			g.drawLine(mouse.x, mouse.y + game.height, mouse.x, mouse.y
					- game.height);

			g.setColor(Color.BLACK);
			g.fillRect(0, 0, (int) Game.getDimensions().getWidth(), 50);

			final NumberFormat df = DecimalFormat.getInstance();

			final int experienceGained = (Settings.get(91) / 10)
					- Variables.startingExperience;
			final int experienceHour = Methods.getPerHour(experienceGained,
					Variables.startTime);

			final String experience = df.format(experienceGained);
			final String experienceHourly = df.format(experienceHour);

			g.setColor(Color.GRAY);
			g.setFont(new Font("Arial", Font.BOLD, 11));
			g.drawString("Run Time: " + Const.TIMER.toElapsedString(), 3, 12);
			g.drawString(String.format("Experience Gained (hr): %s (%s)",
					experience, experienceHourly), 3, 25);
			g.drawString(
					String.format("Level info: %d/%d", Variables.startingLevel
							+ Variables.gainedLevels, Variables.startingLevel),
					3, 38);


			switch (Variables.mode) {
			case 0:
				if (Variables.jacking) {
					org.oneluckyduck.thieving.guild.misc.Painting.paint(g);
				}
				break;
			case 1 :
				break;
			default:
				shutdown();
				break;
			}

			g.setFont(new Font("Kristen ITC", Font.BOLD, 11));
			g.drawString("Thieves Guild AIO by OneLuckyDuck", 5, 375);
			
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHints(antialiasing);
			g2.setColor(Color.BLUE);
			g2.setFont(new Font("Arial", Font.BOLD, 12));
			g2.drawImage(Const.BAR, 0, 510, null, null);
			g2.drawString("Status: " + Variables.status, 15, 522);

			// g.setColor(Color.RED);
			// for (SceneObject so : SceneEntities.getLoaded()) {
			// if (Calculations.distanceTo(so) == 1) {
			// final Point point = so.getCentralPoint();
			// g.drawString("" + so.getId(), point.x, point.y);
			// }
			// }
		}

	}

	@Override
	public void messageReceived(MessageEvent msg) {
		final String message = msg.getMessage();
		Variables.blackjacks = message.toLowerCase().contains("as politely ") ? Variables.blackjacks + 1
				: Variables.blackjacks;
	}
}
