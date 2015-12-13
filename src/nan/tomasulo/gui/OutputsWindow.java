/*
 * Created by JFormDesigner on Sun Dec 13 14:07:27 EET 2015
 */

package nan.tomasulo.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;

import nan.tomasulo.cache.Cache;
import nan.tomasulo.cache.Caches;
import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.processor.Processor;
import nan.tomasulo.registers.RegisterFile;
import nan.tomasulo.reservation_stations.ReservationStation;

/**
 * @author mohamed mostafa
 */
public class OutputsWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8163677483629624324L;
	Object[] tableHeaders = new Object[] { "Instruction", "Issued", "Executed",
			"Written", "Committed" };
	private Processor processor;
	private JLabel[] registersLabels = new JLabel[8];

	public OutputsWindow(Processor p) {
		initComponents();
		initRegistersLabels();
		table1 = new JTable(new Object[][] {}, tableHeaders);
		table1.setEnabled(false);
		{
			scrollPane1.setViewportView(table1);
		}
		pack();
		setVisible(true);
		this.processor = p;
	}

	private void initRegistersLabels() {
		registersLabels[0] = lblR0Value;
		registersLabels[1] = lblR1Value;
		registersLabels[2] = lblR2Value;
		registersLabels[3] = lblR3Value;
		registersLabels[4] = lblR4Value;
		registersLabels[5] = lblR5Value;
		registersLabels[6] = lblR6Value;
		registersLabels[7] = lblR7Value;
	}

	private void btnNextCycleActionPerformed(ActionEvent e) {
		if (processor.isHalted()) {
			return;
		}
		lblCurrentCycle.setText(Processor.getClock() + "");
		try {
			processor.nextClockCycle();
		} catch (InvalidReadException e1) {
			System.err.println(e1.getMessage());
			return;
		} catch (InvalidWriteException e1) {
			System.err.println(e1.getMessage());
			return;
		}
		displayReservationStations();
		displayRegisters();
		if (processor.isHalted()) {
			InputsWindow.busy = false;
			displayResults();
		}
	}

	private void displayRegisters() {
		for (int i = 0; i < registersLabels.length; i++) {
			registersLabels[i].setText(RegisterFile.getRegisterData(i) + "");
		}
	}

	private void displayReservationStations() {
		LinkedList<ReservationStation> reservationStations = Processor
				.getReservationStationsQueue();
		Object[][] info = new Object[reservationStations.size()][5];
		for (int i = 0; i < info.length; i++) {
			info[i][0] = reservationStations.get(i).getInstruction().toString();
			info[i][1] = reservationStations.get(i).getInstruction()
					.getIssuedTime();
			info[i][2] = reservationStations.get(i).getInstruction()
					.getExecutedTime();
			info[i][3] = reservationStations.get(i).getInstruction()
					.getWrittenTime();
			info[i][4] = reservationStations.get(i).getInstruction()
					.getCommitedTime();
			for (int j = 1; j < info[i].length; j++) {
				if (info[i][j].toString().equals("0")) {
					info[i][j] = "\t-";
				}
			}
		}
		table1 = new JTable(info, tableHeaders);
		table1.setEnabled(false);
		{
			scrollPane1.setViewportView(table1);
		}
		pack();
	}

	private void displayResults() {
		lblExecTimeVal.setText(Processor.getClock() - 1 + "");
		lblIPCVal.setText(String.format("%.3f", processor.getIPC()) + "");
		lblBranchMissPredictionValue.setText(processor
				.getBranchMissPredictionPercentage() + "%");
		lblGlobalAmatVal.setText(String.format("%.3f", Caches.getAMAT(0)));
		LinkedList<Cache> caches = Caches.getDataCaches();
		for (int i = 0; i < caches.size(); i++) {
			System.out.println("Cache level " + (i + 1) + " Hit Ration is : "
					+ String.format("%.3f", caches.get(i).getHitRatio()));
		}
	}

	private void thisWindowClosed(WindowEvent e) {
		InputsWindow.busy = false;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - mohamed mostafa
		lblRegisters = new JLabel();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		lblCurrentCycle = new JLabel();
		panel1 = new JPanel();
		label1 = new JLabel();
		lblR0Value = new JLabel();
		label3 = new JLabel();
		lblR1Value = new JLabel();
		label5 = new JLabel();
		lblR2Value = new JLabel();
		label7 = new JLabel();
		lblR3Value = new JLabel();
		label9 = new JLabel();
		lblR4Value = new JLabel();
		label11 = new JLabel();
		lblR5Value = new JLabel();
		label13 = new JLabel();
		lblR6Value = new JLabel();
		label15 = new JLabel();
		lblR7Value = new JLabel();
		lblExec = new JLabel();
		lblExecTimeVal = new JLabel();
		lblGlobalAMAT = new JLabel();
		lblGlobalAmatVal = new JLabel();
		lblCachesInfo = new JLabel();
		btnNextCycle = new JButton();
		lblIPC = new JLabel();
		lblIPCVal = new JLabel();
		lblBranchMissP = new JLabel();
		lblBranchMissPredictionValue = new JLabel();

		// ======== this ========
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				thisWindowClosed(e);
			}
		});
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout) contentPane.getLayout()).columnWidths = new int[] { 0,
				0, 42, 34, 54, 37, 38, 34, 47, 174, 26, 41, 0 };
		((GridBagLayout) contentPane.getLayout()).rowHeights = new int[] { 40,
				34, 67, 48, 43, 0, 0, 0 };
		((GridBagLayout) contentPane.getLayout()).columnWeights = new double[] {
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				1.0E-4 };
		((GridBagLayout) contentPane.getLayout()).rowWeights = new double[] {
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4 };

		// ---- lblRegisters ----
		lblRegisters.setText("Registers");
		lblRegisters.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegisters.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblRegisters, new GridBagConstraints(9, 0, 1, 1, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

		// ======== scrollPane1 ========
		{

			// ---- table1 ----
			table1.setEnabled(false);
			scrollPane1.setViewportView(table1);
		}
		contentPane.add(scrollPane1, new GridBagConstraints(3, 1, 5, 1, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

		// ---- lblCurrentCycle ----
		lblCurrentCycle.setText("0");
		contentPane.add(lblCurrentCycle, new GridBagConstraints(8, 1, 1, 2,
				0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 0, 5, 5), 0, 0));

		// ======== panel1 ========
		{
			panel1.setBackground(new Color(204, 204, 204));
			panel1.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));

			// JFormDesigner evaluation mark
			panel1.setBorder(new javax.swing.border.CompoundBorder(
					new javax.swing.border.TitledBorder(
							new javax.swing.border.EmptyBorder(0, 0, 0, 0),
							"JFormDesigner Evaluation",
							javax.swing.border.TitledBorder.CENTER,
							javax.swing.border.TitledBorder.BOTTOM,
							new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
							java.awt.Color.red), panel1.getBorder()));
			panel1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
				public void propertyChange(java.beans.PropertyChangeEvent e) {
					if ("border".equals(e.getPropertyName()))
						throw new RuntimeException();
				}
			});

			panel1.setLayout(new GridLayout(8, 2));

			// ---- label1 ----
			label1.setText("R0");
			label1.setHorizontalAlignment(SwingConstants.CENTER);
			label1.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
			panel1.add(label1);

			// ---- lblR0Value ----
			lblR0Value.setText("0");
			lblR0Value.setHorizontalAlignment(SwingConstants.CENTER);
			lblR0Value.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
			panel1.add(lblR0Value);

			// ---- label3 ----
			label3.setText("R1");
			label3.setHorizontalAlignment(SwingConstants.CENTER);
			label3.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
			panel1.add(label3);

			// ---- lblR1Value ----
			lblR1Value.setText("0");
			lblR1Value.setHorizontalAlignment(SwingConstants.CENTER);
			lblR1Value.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
			panel1.add(lblR1Value);

			// ---- label5 ----
			label5.setText("R2");
			label5.setHorizontalAlignment(SwingConstants.CENTER);
			label5.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
			panel1.add(label5);

			// ---- lblR2Value ----
			lblR2Value.setText("0");
			lblR2Value.setHorizontalAlignment(SwingConstants.CENTER);
			lblR2Value.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
			panel1.add(lblR2Value);

			// ---- label7 ----
			label7.setText("R3");
			label7.setHorizontalAlignment(SwingConstants.CENTER);
			label7.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
			panel1.add(label7);

			// ---- lblR3Value ----
			lblR3Value.setText("0");
			lblR3Value.setHorizontalAlignment(SwingConstants.CENTER);
			lblR3Value.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
			panel1.add(lblR3Value);

			// ---- label9 ----
			label9.setText("R4");
			label9.setHorizontalAlignment(SwingConstants.CENTER);
			label9.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
			panel1.add(label9);

			// ---- lblR4Value ----
			lblR4Value.setText("0");
			lblR4Value.setHorizontalAlignment(SwingConstants.CENTER);
			lblR4Value.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
			panel1.add(lblR4Value);

			// ---- label11 ----
			label11.setText("R5");
			label11.setHorizontalAlignment(SwingConstants.CENTER);
			label11.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
			panel1.add(label11);

			// ---- lblR5Value ----
			lblR5Value.setText("0");
			lblR5Value.setHorizontalAlignment(SwingConstants.CENTER);
			lblR5Value.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
			panel1.add(lblR5Value);

			// ---- label13 ----
			label13.setText("R6");
			label13.setHorizontalAlignment(SwingConstants.CENTER);
			label13.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
			panel1.add(label13);

			// ---- lblR6Value ----
			lblR6Value.setText("0");
			lblR6Value.setHorizontalAlignment(SwingConstants.CENTER);
			lblR6Value.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
			panel1.add(lblR6Value);

			// ---- label15 ----
			label15.setText("R7");
			label15.setHorizontalAlignment(SwingConstants.CENTER);
			label15.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
			panel1.add(label15);

			// ---- lblR7Value ----
			lblR7Value.setText("0");
			lblR7Value.setHorizontalAlignment(SwingConstants.CENTER);
			lblR7Value.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
			panel1.add(lblR7Value);
		}
		contentPane.add(panel1, new GridBagConstraints(9, 1, 1, 2, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 5, 5), 0, 0));

		// ---- lblExec ----
		lblExec.setText("Execution Time = ");
		lblExec.setHorizontalAlignment(SwingConstants.CENTER);
		lblExec.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(lblExec, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
				new Insets(0, 0, 5, 5), 0, 0));

		// ---- lblExecTimeVal ----
		lblExecTimeVal.setHorizontalAlignment(SwingConstants.CENTER);
		lblExecTimeVal.setForeground(Color.red);
		contentPane.add(lblExecTimeVal, new GridBagConstraints(3, 3, 1, 1, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

		// ---- lblGlobalAMAT ----
		lblGlobalAMAT.setText("Global AMAT =");
		lblGlobalAMAT.setHorizontalAlignment(SwingConstants.CENTER);
		lblGlobalAMAT.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(lblGlobalAMAT, new GridBagConstraints(5, 3, 1, 1, 0.0,
				0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
				new Insets(0, 0, 5, 5), 0, 0));

		// ---- lblGlobalAmatVal ----
		lblGlobalAmatVal.setHorizontalAlignment(SwingConstants.CENTER);
		lblGlobalAmatVal.setForeground(Color.red);
		contentPane.add(lblGlobalAmatVal, new GridBagConstraints(6, 3, 1, 1,
				0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		contentPane.add(lblCachesInfo, new GridBagConstraints(7, 3, 1, 2, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
				new Insets(0, 0, 5, 5), 0, 0));

		// ---- btnNextCycle ----
		btnNextCycle.setText("Next Cycle");
		btnNextCycle.setFocusable(false);
		btnNextCycle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnNextCycleActionPerformed(e);
			}
		});
		contentPane.add(btnNextCycle, new GridBagConstraints(8, 3, 1, 1, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 0, 5, 5), 0, 0));

		// ---- lblIPC ----
		lblIPC.setText("IPC =");
		lblIPC.setHorizontalAlignment(SwingConstants.CENTER);
		lblIPC.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(lblIPC, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
				new Insets(0, 0, 5, 5), 0, 0));

		// ---- lblIPCVal ----
		lblIPCVal.setHorizontalAlignment(SwingConstants.CENTER);
		lblIPCVal.setForeground(Color.red);
		contentPane.add(lblIPCVal, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 5, 5), 0, 0));

		// ---- lblBranchMissP ----
		lblBranchMissP.setText("Branch MissPrediction =");
		lblBranchMissP.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(lblBranchMissP, new GridBagConstraints(5, 4, 1, 1, 0.0,
				0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
				new Insets(0, 0, 5, 5), 0, 0));

		// ---- lblBranchMissPredictionValue ----
		lblBranchMissPredictionValue
				.setHorizontalAlignment(SwingConstants.CENTER);
		lblBranchMissPredictionValue.setForeground(Color.red);
		contentPane.add(lblBranchMissPredictionValue, new GridBagConstraints(6,
				4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - mohamed mostafa
	private JLabel lblRegisters;
	private JScrollPane scrollPane1;
	private JTable table1;
	private JLabel lblCurrentCycle;
	private JPanel panel1;
	private JLabel label1;
	private JLabel lblR0Value;
	private JLabel label3;
	private JLabel lblR1Value;
	private JLabel label5;
	private JLabel lblR2Value;
	private JLabel label7;
	private JLabel lblR3Value;
	private JLabel label9;
	private JLabel lblR4Value;
	private JLabel label11;
	private JLabel lblR5Value;
	private JLabel label13;
	private JLabel lblR6Value;
	private JLabel label15;
	private JLabel lblR7Value;
	private JLabel lblExec;
	private JLabel lblExecTimeVal;
	private JLabel lblGlobalAMAT;
	private JLabel lblGlobalAmatVal;
	private JLabel lblCachesInfo;
	private JButton btnNextCycle;
	private JLabel lblIPC;
	private JLabel lblIPCVal;
	private JLabel lblBranchMissP;
	private JLabel lblBranchMissPredictionValue;
	// JFormDesigner - End of variables declaration //GEN-END:variables

}
