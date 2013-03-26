package pro.geektalk.thievesguild.misc;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Lobby;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Entity;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;
import org.powerbot.game.bot.Context;

public class Methods {

	public static boolean openMenu(final Entity e, final boolean fast) {
		if (fast) {
			final Point p = e.getCentralPoint();
			Mouse.hop(p.x, p.y);
			return Mouse.click(false);
		} else {
			return Mouse.click(e.getCentralPoint(), false);
		}
	}

	public static void setTracking() {
		final WidgetChild orb = Widgets.get(548).getChild(43);
		final Widget counterScreen = Widgets.get(1214);
		if (isWidgetChildVisible(orb))
			openMenu(orb, false);
		if (Menu.isOpen() && Menu.contains("Setup")) {
			Menu.select("Setup");
			Task.sleep(1100);
			final Timer timer = new Timer(4000);
			while (!isWidgetVisible(counterScreen)) {
				Task.sleep(50);
				if (!timer.isRunning())
					break;
			}
		} else {
			final Point p = Mouse.getLocation();
			int r = Random.nextInt(40, 109);
			Mouse.move(p.x + r, p.y + r);
		}
		if (isWidgetVisible(counterScreen)) {
			final WidgetChild counterOne = counterScreen.getChild(22);
			if (isWidgetChildVisible(counterOne)
					&& counterOne.getText().contains("er 1")) {
				if (counterOne.getTextColor() != 14182671)
					counterOne.click(true);
			}
			final WidgetChild thieving = counterScreen.getChild(40);
			if (isWidgetChildVisible(thieving)) {
				thieving.click(true);
				final Timer timer = new Timer(4000);
				final WidgetChild yes = counterScreen.getChild(64);
				while (!isWidgetChildVisible(yes)) {
					Task.sleep(10);
					if (!timer.isRunning())
						break;
				}
				if (isWidgetChildVisible(yes) && yes.getText().contains("es")) {
					yes.click(true);
					Task.sleep(1100);
					counterScreen.getChild(18).click(true);
				}
			}
		}

	}

	public static boolean inventoryContains(final int id) {
		if (Tabs.getCurrent() != Tabs.INVENTORY) {
			Tabs.INVENTORY.open();
		}
		return isItemVisible(Inventory.getItem(id))
				&& Inventory.getCount(id) > 0;
	}

	private static boolean isItemVisible(final Item i) {
		return i != null && i.getWidgetChild().validate()
				&& i.getWidgetChild().visible()
				&& i.getWidgetChild().isOnScreen();
	}

	public static boolean isPlayerStunned() {
		return Settings.get(659) == 7759;
	}

	public static void stopScript(final String s) {
		s(s);
		Context.get().getScriptHandler().stop();
	}

	public static boolean isWidgetChildVisible(final WidgetChild wc) {
		return wc != null && wc.validate() && wc.visible() && wc.isOnScreen();
	}

	public static boolean isWidgetVisible(final Widget w) {
		return w != null && w.validate();
	}

	public static boolean isInGame() {
		return Game.isLoggedIn() && Game.getClientState() == 11
				&& !Context.resolve().refreshing && !Lobby.isOpen();
	}

	public static void s(final String s) {
		Variables.status = s;
		System.out.println(String.format("[Thieves Guild Blackjacker] %s", s));
	}

	public static int getLifePoints() {
		final WidgetChild lifepoints = Widgets.get(748).getChild(8);
		return Methods.isWidgetChildVisible(lifepoints) ? Integer
				.parseInt(lifepoints.getText()) : -1;
	}

	public boolean isIdle() {
		return (Players.getLocal() != null && !Players.getLocal().isMoving()
				&& Players.getLocal().getAnimation() == -1 && (Players
				.getLocal().getInteracting() == null || Players.getLocal()
				.getInteracting().getHealthRatio() == 0));
	}

	public static int getPerHour(final int base, final long time) {
		return (int) ((base) * 3600000D / (System.currentTimeMillis() - time));
	}

	public static Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
			return null;
		}
	}
}
