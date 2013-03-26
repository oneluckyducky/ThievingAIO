package pro.geektalk.thievesguild;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
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

import javax.swing.UIManager;

import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.input.Mouse.Speed;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.WidgetCache;
import org.powerbot.game.api.util.SkillData;
import org.powerbot.game.bot.Context;
import org.powerbot.game.client.Client;

import pro.geektalk.thievesguild.challenge.Tracking;
import pro.geektalk.thievesguild.misc.Const;
import pro.geektalk.thievesguild.misc.Methods;
import pro.geektalk.thievesguild.misc.Variables;
import pro.geektalk.thievesguild.misc.gui.GUI;

@Manifest(authors = { "OneLuckyDuck" }, name = "Thieves Guild AIO", version = Const.VERSION, description = Const.DESCRIPTION, website =Const.WEBSITE)
public class GuildAIO extends ActiveScript implements PaintListener,
		MessageListener {

	Client client;
	public static Tree jobContainer = null;
	public static List<Node> jobs = Collections
			.synchronizedList(new ArrayList<Node>());
	boolean world = false;

	private final RenderingHints antialiasing = new RenderingHints(
			RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	public static SkillData dataXp;

	public void onStart() {
		if (!Game.isLoggedIn()) {
			Methods.stopScript("Please log in");
		}
		dataXp = new SkillData();
		Mouse.setSpeed(Speed.FAST);
		for (UIManager.LookAndFeelInfo info : UIManager
				.getInstalledLookAndFeels()) {
			if ("Metal".equals(info.getName())) {
				try {
					UIManager.setLookAndFeel(info.getClassName());
					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							new GUI();
						}

					});
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
		while (!Variables.ready) {
			Task.sleep(10);
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
		return Variables.loop;
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

			final int experienceGained = Skills.getExperience(Skills.THIEVING)
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
			g.drawString(String.format("Level info: %d/%d",
					Skills.getRealLevel(Skills.THIEVING),
					Variables.startingLevel), 3, 38);

			final int actionsHour = Methods.getPerHour(Variables.actions,
					Variables.startTime);

			final String actions = DecimalFormat.getInstance().format(
					Variables.actions);

			final String actionsHourly = DecimalFormat.getInstance().format(
					actionsHour);

			g.drawString(String.format("Actions (hr): %s (%s)", actions,
					actionsHourly), 180, 12);

			g.setFont(new Font("Kristen ITC", Font.BOLD, 11));
			g.drawString("Thieve's Guild AIO by OneLuckyDuck", 5, 375);

			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHints(antialiasing);
			g2.setColor(Color.BLUE);
			g2.setFont(new Font("Arial", Font.BOLD, 12));
			g2.drawImage(Const.BAR, 0, 510, null, null);
			g2.drawString("Status: " + Variables.status, 15, 522);
		}

	}

	@Override
	public void messageReceived(MessageEvent msg) {
		final String message = msg.getMessage();
		if (message.contains("as politely") || message.contains(" retrieve ")
				|| message.contains("manage to")) {
			Variables.actions++;
		}
	}
}
