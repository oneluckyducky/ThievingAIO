package org.oneluckyduck.thieving.aio.misc;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import org.oneluckyduck.thieving.aio.ThievingAIO;
import org.powerbot.game.bot.Context;

public class GUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GUI() {
		initComponents();
	}

	private void btnGuildActionPerformed(ActionEvent e) {
		Variables.mode = 0;
		if (chkGuildStop.isSelected()) {
			ThievingAIO.jobs
					.add(new org.oneluckyduck.thieving.guild.nodes.blackjacking.Failsafe());
		}
		switch (comboGuildMode.getSelectedIndex()) {
		case 0: // pickpocket trainer
			org.oneluckyduck.thieving.guild.misc.Variables.npcFilter = org.oneluckyduck.thieving.guild.misc.Const.PICKPOCKET_TRAINER_FILTER;
			ThievingAIO.jobs
					.add(new org.oneluckyduck.thieving.guild.nodes.pickpocketing.Pickpocket());
			break;
		case 1: // blackjack advanced trainer
			Variables.jacking = true;
			org.oneluckyduck.thieving.guild.misc.Variables.npcFilter = org.oneluckyduck.thieving.guild.misc.Const.ADVANCED_TRAINER_FILTER;
			ThievingAIO.jobs
					.add(new org.oneluckyduck.thieving.guild.nodes.blackjacking.Luring());
			ThievingAIO.jobs
					.add(new org.oneluckyduck.thieving.guild.nodes.blackjacking.Looting());
			Context.get()
					.getScriptHandler()
					.getScriptContainer()
					.submit(new org.oneluckyduck.thieving.guild.nodes.blackjacking.KeyHandling());
			break;
		case 2: // pickpocket volunteer
			org.oneluckyduck.thieving.guild.misc.Variables.npcFilter = org.oneluckyduck.thieving.guild.misc.Const.PICKPOCKET_VOLUNTEER_FILTER;
			ThievingAIO.jobs
					.add(new org.oneluckyduck.thieving.guild.nodes.pickpocketing.Pickpocket());
			break;
		case 3: // blackjack volunteer
			Variables.jacking = true;
			org.oneluckyduck.thieving.guild.misc.Variables.npcFilter = org.oneluckyduck.thieving.guild.misc.Const.COSHING_VOLUNTEER_FILTER;
			ThievingAIO.jobs
					.add(new org.oneluckyduck.thieving.guild.nodes.blackjacking.Luring());
			ThievingAIO.jobs
					.add(new org.oneluckyduck.thieving.guild.nodes.blackjacking.Looting());
			Context.get()
					.getScriptHandler()
					.getScriptContainer()
					.submit(new org.oneluckyduck.thieving.guild.nodes.blackjacking.KeyHandling());
			break;
		case 4: // practice chests
			ThievingAIO.jobs
					.add(new org.oneluckyduck.thieving.guild.nodes.chests.OpenSouthGates());
			ThievingAIO.jobs
					.add(new org.oneluckyduck.thieving.guild.nodes.chests.OpenSouthChests());
			ThievingAIO.jobs
					.add(new org.oneluckyduck.thieving.guild.nodes.chests.OpenNorthGates());
			ThievingAIO.jobs
					.add(new org.oneluckyduck.thieving.guild.nodes.chests.OpenNorthChests());
			break;
		}
		Variables.ready = true;
		this.setVisible(false);
	}

	private void btnPickpocketActionPerformed(ActionEvent e) {
		Variables.mode = 1;
		org.oneluckyduck.thieving.pickpocketing.misc.Variables.npcName = org.oneluckyduck.thieving.pickpocketing.misc.Const.NAMES_LIST[comboPickpocketNpc
				.getSelectedIndex()];
		Methods.s(org.oneluckyduck.thieving.pickpocketing.misc.Variables.npcName);
		ThievingAIO.jobs.add(new org.oneluckyduck.thieving.pickpocketing.nodes.Pickpocket());
		Variables.ready = true;
		this.setVisible(false);
	}

	private void btnStallsActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void btnChestsActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void initComponents() {
		tabbedPane1 = new JTabbedPane();
		panel1 = new JPanel();
		comboGuildMode = new JComboBox<>();
		btnGuild = new JButton();
		chkGuildStop = new JCheckBox();
		panel2 = new JPanel();
		comboPickpocketNpc = new JComboBox<>();
		txtPickpocketFood = new JTextField();
		label1 = new JLabel();
		label2 = new JLabel();
		chkPickpocketStop = new JCheckBox();
		btnPickpocket = new JButton();
		panel3 = new JPanel();
		comboStalls = new JComboBox<>();
		btnStalls = new JButton();
		txtStallFood = new JTextField();
		label3 = new JLabel();
		chkStallStop = new JCheckBox();
		panel4 = new JPanel();
		comboChests = new JComboBox<>();
		btnChests = new JButton();
		txtChestsFood = new JTextField();
		label4 = new JLabel();
		chkChestStop = new JCheckBox();

		btnStalls.setEnabled(false);
		btnChests.setEnabled(false);

		// ======== this ========
		setTitle("Thieving AIO");
		Container contentPane = getContentPane();

		// ======== tabbedPane1 ========
		{

			// ======== panel1 ========
			{
				// ---- comboGuildMode ----
				comboGuildMode.setModel(new DefaultComboBoxModel<>(
						new String[] { "Pickpocket Trainer",
								"Blackjack Trainer", "Pickpocket Volunteer",
								"Blackjack Volunteer", "Practice Chests" }));

				// ---- btnGuild ----
				btnGuild.setText("Start");
				btnGuild.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						btnGuildActionPerformed(e);
					}
				});

				// ---- chkGuildStop ----
				chkGuildStop.setText("Stop if NPC is unreachable");

				GroupLayout panel1Layout = new GroupLayout(panel1);
				panel1.setLayout(panel1Layout);
				panel1Layout
						.setHorizontalGroup(panel1Layout
								.createParallelGroup()
								.addGroup(
										panel1Layout
												.createSequentialGroup()
												.addContainerGap()
												.addGroup(
														panel1Layout
																.createParallelGroup()
																.addGroup(
																		panel1Layout
																				.createSequentialGroup()
																				.addComponent(
																						chkGuildStop)
																				.addGap(0,
																						83,
																						Short.MAX_VALUE))
																.addGroup(
																		panel1Layout
																				.createSequentialGroup()
																				.addComponent(
																						comboGuildMode,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addContainerGap(
																						110,
																						Short.MAX_VALUE))
																.addGroup(
																		GroupLayout.Alignment.TRAILING,
																		panel1Layout
																				.createSequentialGroup()
																				.addGap(0,
																						143,
																						Short.MAX_VALUE)
																				.addComponent(
																						btnGuild,
																						GroupLayout.PREFERRED_SIZE,
																						73,
																						GroupLayout.PREFERRED_SIZE)
																				.addGap(18,
																						18,
																						18)))));
				panel1Layout
						.setVerticalGroup(panel1Layout
								.createParallelGroup()
								.addGroup(
										panel1Layout
												.createSequentialGroup()
												.addContainerGap()
												.addComponent(
														comboGuildMode,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(
														LayoutStyle.ComponentPlacement.RELATED,
														26, Short.MAX_VALUE)
												.addComponent(chkGuildStop)
												.addGap(5, 5, 5)
												.addComponent(btnGuild)
												.addContainerGap()));
			}
			tabbedPane1.addTab("Thieves Guild", panel1);

			// ======== panel2 ========
			{

				// ---- comboPickpocketNpc ----
				comboPickpocketNpc
						.setModel(new DefaultComboBoxModel<>(new String[] {
								"Man (1)", "Farmer (10)", "Master Farmer (38)",
								"Master Gardener (38)", "Guard (40)",
								"Knight of Ardougne (55)", "Watchman (65)",
								"Paladin (70)", "Gnome (75)", "Hero (80)",
								"Elf (85)", "Dwarf Trader (90)" }));

				// ---- txtPickpocketFood ----
				txtPickpocketFood.setToolTipText("Default id is trout (777)");

				// ---- label1 ----
				label1.setText("Select NPC");

				// ---- label2 ----
				label2.setText("Enter food id");

				// ---- chkPickpocketStop ----
				chkPickpocketStop.setText("Stop when out of food");
				chkPickpocketStop
						.setToolTipText("The scritp will attempt to bank otherwise");

				// ---- btnPickpocket ----
				btnPickpocket.setText("Start");
				btnPickpocket.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						btnPickpocketActionPerformed(e);
					}
				});

				GroupLayout panel2Layout = new GroupLayout(panel2);
				panel2.setLayout(panel2Layout);
				panel2Layout
						.setHorizontalGroup(panel2Layout
								.createParallelGroup()
								.addGroup(
										panel2Layout
												.createSequentialGroup()
												.addContainerGap()
												.addGroup(
														panel2Layout
																.createParallelGroup()
																.addComponent(
																		comboPickpocketNpc,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(
																		label1)
																.addComponent(
																		label2)
																.addGroup(
																		panel2Layout
																				.createSequentialGroup()
																				.addGroup(
																						panel2Layout
																								.createParallelGroup()
																								.addComponent(
																										chkPickpocketStop)
																								.addComponent(
																										txtPickpocketFood,
																										GroupLayout.PREFERRED_SIZE,
																										132,
																										GroupLayout.PREFERRED_SIZE))
																				.addGap(18,
																						18,
																						18)
																				.addComponent(
																						btnPickpocket)))
												.addContainerGap(26,
														Short.MAX_VALUE)));
				panel2Layout
						.setVerticalGroup(panel2Layout
								.createParallelGroup()
								.addGroup(
										panel2Layout
												.createSequentialGroup()
												.addGroup(
														panel2Layout
																.createParallelGroup()
																.addGroup(
																		panel2Layout
																				.createSequentialGroup()
																				.addComponent(
																						label1)
																				.addGap(3,
																						3,
																						3)
																				.addComponent(
																						comboPickpocketNpc,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addPreferredGap(
																						LayoutStyle.ComponentPlacement.RELATED)
																				.addComponent(
																						label2)
																				.addPreferredGap(
																						LayoutStyle.ComponentPlacement.RELATED)
																				.addComponent(
																						txtPickpocketFood,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addPreferredGap(
																						LayoutStyle.ComponentPlacement.UNRELATED)
																				.addComponent(
																						chkPickpocketStop)
																				.addGap(0,
																						0,
																						Short.MAX_VALUE))
																.addGroup(
																		GroupLayout.Alignment.TRAILING,
																		panel2Layout
																				.createSequentialGroup()
																				.addGap(0,
																						0,
																						Short.MAX_VALUE)
																				.addComponent(
																						btnPickpocket)))
												.addContainerGap()));
			}
			tabbedPane1.addTab("Pickpocketing", panel2);

			// ======== panel3 ========
			{

				// ---- comboStalls ----
				comboStalls.setModel(new DefaultComboBoxModel<>(new String[] {
						"Vegetable stall (2)", "Monkey food stall (5)",
						"Ape Atoll general stall (5)", "Crafting stall (5)",
						"Tea stall (5)", "Baker's stall (5)",
						"Silk stall (20)", "Wine stall (22)",
						"Seed stall (27)", "Fur stall (35)",
						"Scimitar stall (65)", "Magic stall (65)",
						"Spice stall (65)", "Gem stall (75)" }));

				// ---- btnStalls ----
				btnStalls.setText("Start");
				btnStalls.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						btnStallsActionPerformed(e);
					}
				});

				// ---- txtStallFood ----
				txtStallFood.setToolTipText("Default id is trout (777)");

				// ---- label3 ----
				label3.setText("Enter food id");

				// ---- chkStallStop ----
				chkStallStop.setText("Stop when out of food");
				chkStallStop
						.setToolTipText("The scritp will attempt to bank otherwise");

				GroupLayout panel3Layout = new GroupLayout(panel3);
				panel3.setLayout(panel3Layout);
				panel3Layout
						.setHorizontalGroup(panel3Layout
								.createParallelGroup()
								.addGroup(
										panel3Layout
												.createSequentialGroup()
												.addContainerGap()
												.addGroup(
														panel3Layout
																.createParallelGroup()
																.addComponent(
																		comboStalls,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(
																		label3)
																.addGroup(
																		panel3Layout
																				.createSequentialGroup()
																				.addGroup(
																						panel3Layout
																								.createParallelGroup()
																								.addComponent(
																										txtStallFood,
																										GroupLayout.PREFERRED_SIZE,
																										132,
																										GroupLayout.PREFERRED_SIZE)
																								.addComponent(
																										chkStallStop))
																				.addGap(18,
																						18,
																						18)
																				.addComponent(
																						btnStalls)))
												.addContainerGap(26,
														Short.MAX_VALUE)));
				panel3Layout
						.setVerticalGroup(panel3Layout
								.createParallelGroup()
								.addGroup(
										panel3Layout
												.createSequentialGroup()
												.addContainerGap()
												.addGroup(
														panel3Layout
																.createParallelGroup()
																.addGroup(
																		panel3Layout
																				.createSequentialGroup()
																				.addComponent(
																						comboStalls,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addPreferredGap(
																						LayoutStyle.ComponentPlacement.RELATED)
																				.addComponent(
																						label3)
																				.addGap(6,
																						6,
																						6)
																				.addComponent(
																						txtStallFood,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addGap(7,
																						7,
																						7)
																				.addComponent(
																						chkStallStop)
																				.addContainerGap(
																						12,
																						Short.MAX_VALUE))
																.addGroup(
																		GroupLayout.Alignment.TRAILING,
																		panel3Layout
																				.createSequentialGroup()
																				.addGap(0,
																						64,
																						Short.MAX_VALUE)
																				.addComponent(
																						btnStalls)
																				.addGap(21,
																						21,
																						21)))));
			}
			tabbedPane1.addTab("Stalls", panel3);

			// ======== panel4 ========
			{

				// ---- comboChests ----
				comboChests.setModel(new DefaultComboBoxModel<>(new String[] {
						"Ardougne/Rellekka/Wildy (13)",
						"Upstairs Ardougne/Rellekka (28)",
						"Upstairs Ardougne (43)", "Rellekka (47)",
						"Hemenster (47)", "Dorgesh-Kaan (52)",
						"Chaos druid tower (59)", "King Lathas's Castle (72)",
						"Dorgesh-Kaan (78)" }));

				// ---- btnChests ----
				btnChests.setText("Start");
				btnChests.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						btnChestsActionPerformed(e);
					}
				});

				// ---- txtChestsFood ----
				txtChestsFood.setToolTipText("Default id is trout (777)");

				// ---- label4 ----
				label4.setText("Enter food id");

				// ---- chkChestStop ----
				chkChestStop.setText("Stop when out of food");
				chkChestStop
						.setToolTipText("The scritp will attempt to bank otherwise");

				GroupLayout panel4Layout = new GroupLayout(panel4);
				panel4.setLayout(panel4Layout);
				panel4Layout
						.setHorizontalGroup(panel4Layout
								.createParallelGroup()
								.addGroup(
										panel4Layout
												.createSequentialGroup()
												.addContainerGap()
												.addGroup(
														panel4Layout
																.createParallelGroup()
																.addGroup(
																		panel4Layout
																				.createSequentialGroup()
																				.addGroup(
																						panel4Layout
																								.createParallelGroup()
																								.addComponent(
																										label4)
																								.addComponent(
																										txtChestsFood,
																										GroupLayout.PREFERRED_SIZE,
																										132,
																										GroupLayout.PREFERRED_SIZE)
																								.addComponent(
																										chkChestStop))
																				.addPreferredGap(
																						LayoutStyle.ComponentPlacement.RELATED,
																						34,
																						Short.MAX_VALUE)
																				.addComponent(
																						btnChests))
																.addGroup(
																		panel4Layout
																				.createSequentialGroup()
																				.addComponent(
																						comboChests,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addGap(0,
																						44,
																						Short.MAX_VALUE)))
												.addContainerGap()));
				panel4Layout
						.setVerticalGroup(panel4Layout
								.createParallelGroup()
								.addGroup(
										GroupLayout.Alignment.TRAILING,
										panel4Layout
												.createSequentialGroup()
												.addGroup(
														panel4Layout
																.createParallelGroup(
																		GroupLayout.Alignment.TRAILING)
																.addGroup(
																		panel4Layout
																				.createSequentialGroup()
																				.addContainerGap()
																				.addComponent(
																						comboChests,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addPreferredGap(
																						LayoutStyle.ComponentPlacement.RELATED,
																						7,
																						Short.MAX_VALUE)
																				.addComponent(
																						label4)
																				.addGap(6,
																						6,
																						6)
																				.addComponent(
																						txtChestsFood,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addGap(7,
																						7,
																						7)
																				.addComponent(
																						chkChestStop))
																.addGroup(
																		panel4Layout
																				.createSequentialGroup()
																				.addGap(20,
																						85,
																						Short.MAX_VALUE)
																				.addComponent(
																						btnChests)))
												.addContainerGap()));
			}
			tabbedPane1.addTab("Chests", panel4);

		}

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(contentPaneLayout
				.createParallelGroup().addGroup(
						contentPaneLayout.createSequentialGroup()
								.addContainerGap().addComponent(tabbedPane1)
								.addContainerGap()));
		contentPaneLayout.setVerticalGroup(contentPaneLayout
				.createParallelGroup().addGroup(
						contentPaneLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(tabbedPane1,
										GroupLayout.DEFAULT_SIZE, 147,
										Short.MAX_VALUE).addContainerGap()));
		pack();
		setLocationRelativeTo(getOwner());

	}

	private JTabbedPane tabbedPane1;
	private JPanel panel1;
	private JComboBox<String> comboGuildMode;
	private JButton btnGuild;
	private JCheckBox chkGuildStop;
	private JPanel panel2;
	private JComboBox<String> comboPickpocketNpc;
	private JTextField txtPickpocketFood;
	private JLabel label1;
	private JLabel label2;
	private JCheckBox chkPickpocketStop;
	private JButton btnPickpocket;
	private JPanel panel3;
	private JComboBox<String> comboStalls;
	private JButton btnStalls;
	private JTextField txtStallFood;
	private JLabel label3;
	private JCheckBox chkStallStop;
	private JPanel panel4;
	private JComboBox<String> comboChests;
	private JButton btnChests;
	private JTextField txtChestsFood;
	private JLabel label4;
	private JCheckBox chkChestStop;
}
