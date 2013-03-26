package pro.geektalk.thievesguild.misc.gui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.bot.Context;

import pro.geektalk.thievesguild.GuildAIO;
import pro.geektalk.thievesguild.misc.Const;
import pro.geektalk.thievesguild.misc.Methods;
import pro.geektalk.thievesguild.misc.Variables;
import pro.geektalk.thievesguild.nodes.blackjacking.Failsafe;
import pro.geektalk.thievesguild.nodes.blackjacking.KeyHandling;
import pro.geektalk.thievesguild.nodes.blackjacking.Looting;
import pro.geektalk.thievesguild.nodes.blackjacking.Luring;
import pro.geektalk.thievesguild.nodes.chests.OpenNorthChests;
import pro.geektalk.thievesguild.nodes.chests.OpenNorthGates;
import pro.geektalk.thievesguild.nodes.chests.OpenSouthChests;
import pro.geektalk.thievesguild.nodes.chests.OpenSouthGates;
import pro.geektalk.thievesguild.nodes.pickpocketing.Pickpocket;

public class GUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	GridLayout grid;
	Container pane;

	JLabel label1 = new JLabel("Thieve's Guild AIO by OneLuckyDuck");
	JComboBox<String> action = new JComboBox<String>();
	JCheckBox chkSafe = new JCheckBox("Stop if Npc is unreachable");
	JTextField textWorld = new JTextField("World to log into");
	JButton btnStart = new JButton("Start");

	public GUI() {
		pane = getContentPane();
		grid = new GridLayout(5, 0);

		grid.setVgap(5);

		this.setLayout(grid);
		this.setSize(250, 180);
		this.setTitle("Thieve's Guild AIO " +Const.VERSION);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		this.setLocationRelativeTo(null);

		label1.setHorizontalAlignment(JLabel.CENTER);
		pane.add(label1);

		action.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Pickpocket Trainer", "Blackjack Trainer",
				"Pickpocket Volunteer", "Blackjack Volunteer",
				"Practice Chests" }));
		pane.add(action);

		pane.add(chkSafe);

		textWorld.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (textWorld.getText().equalsIgnoreCase("World to log into")) {
					textWorld.setText("");
				}

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				if (textWorld.getText().isEmpty()) {
					textWorld.setText("World to log into");
				}

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

		});
		pane.add(textWorld);

		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				startScript(e);

			}

		});
		pane.add(btnStart);
	}

	private void startScript(ActionEvent e) {
		if (textWorld.getText().isEmpty()
				| textWorld.getText().equalsIgnoreCase("World to log into")) {
			Variables.world = Random.nextInt(82, 106);
		} else {
			try {
				Variables.world = Integer.parseInt(textWorld.getText());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (chkSafe.isSelected()) {
			GuildAIO.jobs.add(new Failsafe());
		}
		switch (action.getSelectedIndex()) {
		case 0: // pickpocket trainer
			Variables.npcFilter = Const.PICKPOCKET_TRAINER_FILTER;
			GuildAIO.jobs.add(new Pickpocket());
			break;
		case 1: // blackjack advanced trainer
			Variables.jacking = true;
			Variables.npcFilter = Const.ADVANCED_TRAINER_FILTER;
			GuildAIO.jobs.add(new Luring());
			GuildAIO.jobs.add(new Looting());
			Context.get().getScriptHandler().getScriptContainer()
					.submit(new KeyHandling());
			break;
		case 2: // pickpocket volunteer
			Variables.npcFilter = Const.PICKPOCKET_VOLUNTEER_FILTER;
			GuildAIO.jobs
					.add(new pro.geektalk.thievesguild.nodes.pickpocketing.Pickpocket());
			break;
		case 3: // blackjack volunteer
			Variables.jacking = true;
			Variables.npcFilter = Const.COSHING_VOLUNTEER_FILTER;
			GuildAIO.jobs.add(new Luring());
			GuildAIO.jobs.add(new Looting());
			Context.get().getScriptHandler().getScriptContainer()
					.submit(new KeyHandling());
			break;
		case 4: // practice chests
			GuildAIO.jobs.add(new OpenSouthGates());
			GuildAIO.jobs.add(new OpenSouthChests());
			GuildAIO.jobs.add(new OpenNorthGates());
			GuildAIO.jobs.add(new OpenNorthChests());
			break;
		}
		this.dispose();
		Variables.startTime = System.currentTimeMillis();
		Variables.startingExperience = Skills.getExperience(Skills.THIEVING);
		Methods.s(String.format("Logging into world %d", Variables.world));
		Context.setLoginWorld(Variables.world);
		Variables.startingLevel = Skills.getRealLevel(Skills.THIEVING);
		Methods.s(String
				.format("Starting at level %d", Variables.startingLevel));
		Tabs.INVENTORY.open();
		Variables.ready = true;
	}
}
