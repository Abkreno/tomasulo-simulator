/*
 * Created by JFormDesigner on Thu Dec 10 14:59:12 EET 2015
 */

package nan.tomasulo.gui;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import nan.tomasulo.Parser;
import nan.tomasulo.cache.Caches;
import nan.tomasulo.common_data_bus.CommonDataBus;
import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.memory.Memory;
import nan.tomasulo.processor.Processor;
import nan.tomasulo.registers.RegisterFile;
import nan.tomasulo.registers.RegisterStat;
import nan.tomasulo.reorderbuffer.ReorderBuffer;
import nan.tomasulo.reservation_stations.FunctionalUnits;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author mohamed mostafa
 */
public class InputsWindow extends JFrame {
	/**
	 * 
	 */
	public static boolean busy = false;
	private static final long serialVersionUID = -1970657112424404666L;

	public static LinkedList<CacheInfoRow> cacheInfo = new LinkedList<>();

	public InputsWindow() {
		initComponents();
		CacheInfoRow curr = new CacheInfoRow();
		cacheInfo.add(curr);
		cachesPane.add(curr);
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}

	private void btnAddCacheActionPerformed(ActionEvent e) {
		if (cacheInfo.size() > 9)
			return;
		CacheInfoRow curr = new CacheInfoRow();
		cacheInfo.add(curr);
		cachesPane.add(curr);
		pack();
	}

	private void btnRemoveCacheActionPerformed(ActionEvent e) {
		if (cacheInfo.size() <= 1)
			return;
		cacheInfo.removeLast();
		cachesPane.removeAll();
		cachesPane.add(colNamesPane);
		for (int i = 0; i < cacheInfo.size(); i++) {
			cachesPane.add(cacheInfo.get(i));
		}
		cachesScrollPane.setViewportView(cachesPane);
		getContentPane().add(cachesScrollPane, CC.xywh(14, 17, 10, 15));
		pack();
	}

	private void button1ActionPerformed(ActionEvent e) {
		if (busy)
			return;
		busy = true;
		int cachesInfo[][] = new int[cacheInfo.size()][];
		for (int i = 0; i < cachesInfo.length; i++) {
			cachesInfo[i] = cacheInfo.get(i).getInfo();
		}
		Caches.initCaches(cachesInfo);
		Memory.init(Integer.parseInt(tfMemBlockSize.getText()));
		Parser.copyProgramToMemory(tfProgramFile.getText().length() == 0 ? "program_1.in"
				: tfProgramFile.getText() + ".in");
		getFuncUnitsInputs();
		int robSize = Integer.parseInt(tfROBSize.getText());
		int maxCommits = Integer.parseInt(tfMxCommitsPerCycle.getText());
		ReorderBuffer.init(robSize, maxCommits);
		RegisterStat.init(8);
		RegisterFile.init();
		int pipeLineWidth = Integer.parseInt(tfPipelineWidth.getText());
		int maxNumOfInstInBuffer = Integer.parseInt(tfInstructionBufferSize
				.getText());
		CommonDataBus.setMaxNumOfWritesPerCycle(Integer.parseInt(tfNumOfCDBs
				.getText()));

		try {
			Processor.setClock(1);
			Processor p = new Processor(pipeLineWidth, maxNumOfInstInBuffer);
			OutputsWindow o = new OutputsWindow(p);
		} catch (InvalidReadException e1) {
			e1.printStackTrace();
		} catch (InvalidWriteException e1) {
			e1.printStackTrace();
		}

	}

	private void getFuncUnitsInputs() {
		int addUnits = Integer.parseInt(tfAddUnits.getText());

		int addUnitsDelay = Integer.parseInt(tfAddUDelay.getText());

		int multUnits = Integer.parseInt(tfMultUnits.getText());

		int multUnitsDelay = Integer.parseInt(tfMultUDelay.getText());

		int loadUnits = Integer.parseInt(tfLoadUnits.getText());

		int storeUnits = Integer.parseInt(tfStoreUnits.getText());

		int branchUnits = Integer.parseInt(tfBranchUnits.getText());

		int branchUnitsDelay = Integer.parseInt(tfBranchUDelay.getText());

		int logicalUnits = Integer.parseInt(tfLogicalUnits.getText());

		int logicalUnitsDelay = Integer.parseInt(tfLogicalUDelay.getText());

		int callUnits = Integer.parseInt(tfCallUnits.getText());

		int callUnitsDelay = Integer.parseInt(tfCallUDelay.getText());

		int jumpUnits = Integer.parseInt(tfJumpUnits.getText());

		int jumpUnitsDelay = Integer.parseInt(tfJumpUDelay.getText());

		FunctionalUnits.initFunctionalUnits(addUnits, addUnitsDelay, multUnits,
				multUnitsDelay, loadUnits, storeUnits, branchUnits,
				branchUnitsDelay, logicalUnits, logicalUnitsDelay, callUnits,
				callUnitsDelay, jumpUnits, jumpUnitsDelay);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - mohamed mostafa
		label1 = new JLabel();
		lblAddUnits = new JLabel();
		tfAddUnits = new JTextField();
		lblAddUnitsDelay = new JLabel();
		tfAddUDelay = new JTextField();
		lblROBSize = new JLabel();
		tfROBSize = new JTextField();
		lblMultUnits = new JLabel();
		tfMultUnits = new JTextField();
		lblMultUnitsDelay = new JLabel();
		tfMultUDelay = new JTextField();
		lblMxCommits = new JLabel();
		tfMxCommitsPerCycle = new JTextField();
		lblBranchUnits = new JLabel();
		tfBranchUnits = new JTextField();
		lbBranchUnitsDelay = new JLabel();
		tfBranchUDelay = new JTextField();
		lblPiplineWidth = new JLabel();
		tfPipelineWidth = new JTextField();
		lblLogicalUnits = new JLabel();
		tfLogicalUnits = new JTextField();
		lblLogicalUnitsDelay = new JLabel();
		tfLogicalUDelay = new JTextField();
		lblInstructionBfrSize = new JLabel();
		tfInstructionBufferSize = new JTextField();
		lblCallUnits = new JLabel();
		tfCallUnits = new JTextField();
		lblCallUnitsDelay = new JLabel();
		tfCallUDelay = new JTextField();
		lblCommonDataBuses = new JLabel();
		tfNumOfCDBs = new JTextField();
		lblJumpUnits = new JLabel();
		tfJumpUnits = new JTextField();
		lblJumpUnitsDelay = new JLabel();
		tfJumpUDelay = new JTextField();
		lblMemAccessDelay = new JLabel();
		tfMemAccessDelay = new JTextField();
		lblMemBlockSize = new JLabel();
		tfMemBlockSize = new JTextField();
		lblStoreUnits = new JLabel();
		tfStoreUnits = new JTextField();
		label2 = new JLabel();
		btnAddCache = new JButton();
		btnRemoveCache = new JButton();
		lblLoadUnits = new JLabel();
		tfLoadUnits = new JTextField();
		cachesScrollPane = new JScrollPane();
		cachesPane = new JPanel();
		colNamesPane = new JPanel();
		lblS = new JLabel();
		lblL = new JLabel();
		lblM = new JLabel();
		lblD = new JLabel();
		lblWP = new JLabel();
		label3 = new JLabel();
		tfProgramFile = new JTextField();
		button1 = new JButton();

		//======== this ========
		setTitle("Tomasulo-Simulator");
		Container contentPane = getContentPane();
		contentPane.setLayout(new FormLayout(
			"55dlu, $lcgap, 24dlu, $lcgap, 33dlu, $lcgap, 24dlu, $lcgap, default, $lcgap, 36dlu, $lcgap, default, $lcgap, 34dlu, $lcgap, 39dlu, $lcgap, 33dlu, $lcgap, 32dlu, $lcgap, 34dlu, $lcgap, default",
			"15dlu, $lgap, pref, 6*($lgap, default), $lgap, 12dlu, $lgap, 17dlu, 7*($lgap, default)"));
		((FormLayout)contentPane.getLayout()).setRowGroups(new int[][] {{3, 5, 7, 9, 11, 13, 17, 19}});

		//---- label1 ----
		label1.setText("Reservation Stations");
		label1.setBorder(Borders.DIALOG);
		label1.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(label1, CC.xywh(1, 1, 7, 1, CC.CENTER, CC.DEFAULT));

		//---- lblAddUnits ----
		lblAddUnits.setText("Add Units #");
		lblAddUnits.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblAddUnits, CC.xy(1, 3, CC.FILL, CC.DEFAULT));

		//---- tfAddUnits ----
		tfAddUnits.setText("2");
		contentPane.add(tfAddUnits, CC.xy(3, 3));

		//---- lblAddUnitsDelay ----
		lblAddUnitsDelay.setText("Delay");
		contentPane.add(lblAddUnitsDelay, CC.xy(5, 3, CC.CENTER, CC.DEFAULT));

		//---- tfAddUDelay ----
		tfAddUDelay.setText("2");
		contentPane.add(tfAddUDelay, CC.xy(7, 3));

		//---- lblROBSize ----
		lblROBSize.setText("Reorder Buffer Size :");
		contentPane.add(lblROBSize, CC.xywh(15, 3, 3, 1, CC.LEFT, CC.DEFAULT));

		//---- tfROBSize ----
		tfROBSize.setText("4");
		contentPane.add(tfROBSize, CC.xy(19, 3));

		//---- lblMultUnits ----
		lblMultUnits.setText("Mult Units #");
		lblMultUnits.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblMultUnits, CC.xy(1, 5, CC.FILL, CC.DEFAULT));

		//---- tfMultUnits ----
		tfMultUnits.setText("2");
		contentPane.add(tfMultUnits, CC.xy(3, 5));

		//---- lblMultUnitsDelay ----
		lblMultUnitsDelay.setText("Delay");
		contentPane.add(lblMultUnitsDelay, CC.xy(5, 5, CC.CENTER, CC.DEFAULT));

		//---- tfMultUDelay ----
		tfMultUDelay.setText("2");
		contentPane.add(tfMultUDelay, CC.xy(7, 5));

		//---- lblMxCommits ----
		lblMxCommits.setText("Max Commits Per Cycle :");
		contentPane.add(lblMxCommits, CC.xywh(15, 5, 3, 1, CC.LEFT, CC.DEFAULT));

		//---- tfMxCommitsPerCycle ----
		tfMxCommitsPerCycle.setText("4");
		contentPane.add(tfMxCommitsPerCycle, CC.xy(19, 5));

		//---- lblBranchUnits ----
		lblBranchUnits.setText("Branch Units #");
		lblBranchUnits.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblBranchUnits, CC.xy(1, 7, CC.FILL, CC.DEFAULT));

		//---- tfBranchUnits ----
		tfBranchUnits.setText("2");
		contentPane.add(tfBranchUnits, CC.xy(3, 7));

		//---- lbBranchUnitsDelay ----
		lbBranchUnitsDelay.setText("Delay");
		contentPane.add(lbBranchUnitsDelay, CC.xy(5, 7, CC.CENTER, CC.DEFAULT));

		//---- tfBranchUDelay ----
		tfBranchUDelay.setText("2");
		contentPane.add(tfBranchUDelay, CC.xy(7, 7));

		//---- lblPiplineWidth ----
		lblPiplineWidth.setText("Pipeline Width :");
		contentPane.add(lblPiplineWidth, CC.xywh(15, 7, 3, 1, CC.LEFT, CC.DEFAULT));

		//---- tfPipelineWidth ----
		tfPipelineWidth.setText("4");
		contentPane.add(tfPipelineWidth, CC.xy(19, 7));

		//---- lblLogicalUnits ----
		lblLogicalUnits.setText("Logical Units #");
		lblLogicalUnits.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblLogicalUnits, CC.xy(1, 9, CC.FILL, CC.DEFAULT));

		//---- tfLogicalUnits ----
		tfLogicalUnits.setText("2");
		contentPane.add(tfLogicalUnits, CC.xy(3, 9));

		//---- lblLogicalUnitsDelay ----
		lblLogicalUnitsDelay.setText("Delay");
		contentPane.add(lblLogicalUnitsDelay, CC.xy(5, 9, CC.CENTER, CC.DEFAULT));

		//---- tfLogicalUDelay ----
		tfLogicalUDelay.setText("2");
		contentPane.add(tfLogicalUDelay, CC.xy(7, 9));

		//---- lblInstructionBfrSize ----
		lblInstructionBfrSize.setText("Instruction Buffer Size :");
		contentPane.add(lblInstructionBfrSize, CC.xywh(15, 9, 3, 1, CC.LEFT, CC.DEFAULT));

		//---- tfInstructionBufferSize ----
		tfInstructionBufferSize.setText("4");
		contentPane.add(tfInstructionBufferSize, CC.xy(19, 9));

		//---- lblCallUnits ----
		lblCallUnits.setText("Call Units #");
		lblCallUnits.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCallUnits, CC.xy(1, 11, CC.FILL, CC.DEFAULT));

		//---- tfCallUnits ----
		tfCallUnits.setText("2");
		contentPane.add(tfCallUnits, CC.xy(3, 11));

		//---- lblCallUnitsDelay ----
		lblCallUnitsDelay.setText("Delay");
		contentPane.add(lblCallUnitsDelay, CC.xy(5, 11, CC.CENTER, CC.DEFAULT));

		//---- tfCallUDelay ----
		tfCallUDelay.setText("2");
		contentPane.add(tfCallUDelay, CC.xy(7, 11));

		//---- lblCommonDataBuses ----
		lblCommonDataBuses.setText("Common Data Buses # :");
		contentPane.add(lblCommonDataBuses, CC.xywh(15, 11, 3, 1, CC.LEFT, CC.DEFAULT));

		//---- tfNumOfCDBs ----
		tfNumOfCDBs.setText("2");
		contentPane.add(tfNumOfCDBs, CC.xy(19, 11));

		//---- lblJumpUnits ----
		lblJumpUnits.setText("Jump Units #");
		lblJumpUnits.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblJumpUnits, CC.xy(1, 13, CC.FILL, CC.DEFAULT));

		//---- tfJumpUnits ----
		tfJumpUnits.setText("2");
		contentPane.add(tfJumpUnits, CC.xy(3, 13));

		//---- lblJumpUnitsDelay ----
		lblJumpUnitsDelay.setText("Delay");
		contentPane.add(lblJumpUnitsDelay, CC.xy(5, 13, CC.CENTER, CC.DEFAULT));

		//---- tfJumpUDelay ----
		tfJumpUDelay.setText("2");
		contentPane.add(tfJumpUDelay, CC.xy(7, 13));

		//---- lblMemAccessDelay ----
		lblMemAccessDelay.setText("Memory Access Delay :");
		contentPane.add(lblMemAccessDelay, CC.xywh(15, 13, 3, 1, CC.LEFT, CC.DEFAULT));

		//---- tfMemAccessDelay ----
		tfMemAccessDelay.setText("5");
		contentPane.add(tfMemAccessDelay, CC.xy(19, 13));

		//---- lblMemBlockSize ----
		lblMemBlockSize.setText("Memory Block Size :");
		contentPane.add(lblMemBlockSize, CC.xywh(15, 15, 3, 1));

		//---- tfMemBlockSize ----
		tfMemBlockSize.setText("16");
		contentPane.add(tfMemBlockSize, CC.xy(19, 15));

		//---- lblStoreUnits ----
		lblStoreUnits.setText("Store Units #");
		lblStoreUnits.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblStoreUnits, CC.xy(1, 17, CC.FILL, CC.DEFAULT));

		//---- tfStoreUnits ----
		tfStoreUnits.setText("2");
		contentPane.add(tfStoreUnits, CC.xy(3, 17));

		//---- label2 ----
		label2.setText("Caches :");
		contentPane.add(label2, CC.xywh(15, 17, 3, 1, CC.CENTER, CC.DEFAULT));

		//---- btnAddCache ----
		btnAddCache.setText("+");
		btnAddCache.setFocusable(false);
		btnAddCache.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnAddCacheActionPerformed(e);
			}
		});
		contentPane.add(btnAddCache, CC.xy(19, 17));

		//---- btnRemoveCache ----
		btnRemoveCache.setText("-");
		btnRemoveCache.setFocusable(false);
		btnRemoveCache.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnRemoveCacheActionPerformed(e);
			}
		});
		contentPane.add(btnRemoveCache, CC.xy(21, 17));

		//---- lblLoadUnits ----
		lblLoadUnits.setText("Load Units #");
		lblLoadUnits.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblLoadUnits, CC.xy(1, 19, CC.FILL, CC.DEFAULT));

		//---- tfLoadUnits ----
		tfLoadUnits.setText("2");
		contentPane.add(tfLoadUnits, CC.xy(3, 19));

		//======== cachesScrollPane ========
		{

			//======== cachesPane ========
			{

				// JFormDesigner evaluation mark
				cachesPane.setBorder(new javax.swing.border.CompoundBorder(
					new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
						"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
						javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
						java.awt.Color.red), cachesPane.getBorder())); cachesPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

				cachesPane.setLayout(new GridLayout(10, 1));

				//======== colNamesPane ========
				{
					colNamesPane.setLayout(new GridLayout(1, 5));

					//---- lblS ----
					lblS.setText("S");
					lblS.setHorizontalAlignment(SwingConstants.CENTER);
					colNamesPane.add(lblS);

					//---- lblL ----
					lblL.setText("L");
					lblL.setHorizontalAlignment(SwingConstants.CENTER);
					colNamesPane.add(lblL);

					//---- lblM ----
					lblM.setText("M");
					lblM.setHorizontalAlignment(SwingConstants.CENTER);
					colNamesPane.add(lblM);

					//---- lblD ----
					lblD.setText("Delay");
					lblD.setHorizontalAlignment(SwingConstants.CENTER);
					colNamesPane.add(lblD);

					//---- lblWP ----
					lblWP.setText("WB/WT");
					lblWP.setHorizontalAlignment(SwingConstants.CENTER);
					colNamesPane.add(lblWP);
				}
				cachesPane.add(colNamesPane);
			}
			cachesScrollPane.setViewportView(cachesPane);
		}
		contentPane.add(cachesScrollPane, CC.xywh(14, 19, 10, 11));

		//---- label3 ----
		label3.setText("Program File :");
		contentPane.add(label3, CC.xy(1, 31, CC.CENTER, CC.DEFAULT));

		//---- tfProgramFile ----
		tfProgramFile.setText("program_1");
		contentPane.add(tfProgramFile, CC.xywh(3, 31, 3, 1));

		//---- button1 ----
		button1.setText("Simulate");
		button1.setFocusable(false);
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button1ActionPerformed(e);
			}
		});
		contentPane.add(button1, CC.xywh(21, 31, 3, 1));
		pack();
		setLocationRelativeTo(getOwner());
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - mohamed mostafa
	private JLabel label1;
	private JLabel lblAddUnits;
	private JTextField tfAddUnits;
	private JLabel lblAddUnitsDelay;
	private JTextField tfAddUDelay;
	private JLabel lblROBSize;
	private JTextField tfROBSize;
	private JLabel lblMultUnits;
	private JTextField tfMultUnits;
	private JLabel lblMultUnitsDelay;
	private JTextField tfMultUDelay;
	private JLabel lblMxCommits;
	private JTextField tfMxCommitsPerCycle;
	private JLabel lblBranchUnits;
	private JTextField tfBranchUnits;
	private JLabel lbBranchUnitsDelay;
	private JTextField tfBranchUDelay;
	private JLabel lblPiplineWidth;
	private JTextField tfPipelineWidth;
	private JLabel lblLogicalUnits;
	private JTextField tfLogicalUnits;
	private JLabel lblLogicalUnitsDelay;
	private JTextField tfLogicalUDelay;
	private JLabel lblInstructionBfrSize;
	private JTextField tfInstructionBufferSize;
	private JLabel lblCallUnits;
	private JTextField tfCallUnits;
	private JLabel lblCallUnitsDelay;
	private JTextField tfCallUDelay;
	private JLabel lblCommonDataBuses;
	private JTextField tfNumOfCDBs;
	private JLabel lblJumpUnits;
	private JTextField tfJumpUnits;
	private JLabel lblJumpUnitsDelay;
	private JTextField tfJumpUDelay;
	private JLabel lblMemAccessDelay;
	private JTextField tfMemAccessDelay;
	private JLabel lblMemBlockSize;
	private JTextField tfMemBlockSize;
	private JLabel lblStoreUnits;
	private JTextField tfStoreUnits;
	private JLabel label2;
	private JButton btnAddCache;
	private JButton btnRemoveCache;
	private JLabel lblLoadUnits;
	private JTextField tfLoadUnits;
	private JScrollPane cachesScrollPane;
	private JPanel cachesPane;
	private JPanel colNamesPane;
	private JLabel lblS;
	private JLabel lblL;
	private JLabel lblM;
	private JLabel lblD;
	private JLabel lblWP;
	private JLabel label3;
	private JTextField tfProgramFile;
	private JButton button1;
	// JFormDesigner - End of variables declaration //GEN-END:variables
	public static class CacheInfoRow extends JPanel {
		JTextField size, lineSize, associativity, delay, writePolicy;

		public CacheInfoRow() {
			size = new JTextField();
			size.setHorizontalAlignment(SwingConstants.CENTER);
			size.setText("128");
			add(size);
			lineSize = new JTextField();
			lineSize.setHorizontalAlignment(SwingConstants.CENTER);
			lineSize.setText("16");
			add(lineSize);
			associativity = new JTextField();
			associativity.setHorizontalAlignment(SwingConstants.CENTER);
			associativity.setText("1");
			add(associativity);
			delay = new JTextField();
			delay.setHorizontalAlignment(SwingConstants.CENTER);
			delay.setText("2");
			add(delay);
			writePolicy = new JTextField();
			writePolicy.setHorizontalAlignment(SwingConstants.CENTER);
			writePolicy.setText("WB");
			add(writePolicy);
			setLayout(new GridLayout(1, 5));
		}

		public int[] getInfo() {
			int[] info = new int[5];
			info[0] = Integer.parseInt(size.getText());
			info[1] = Integer.parseInt(lineSize.getText());
			info[2] = Integer.parseInt(associativity.getText());
			info[3] = Integer.parseInt(delay.getText());
			info[4] = writePolicy.getText().equalsIgnoreCase("WT") ? 0 : 1;
			return info;
		}
	}

	public static void main(String[] args) {
		new InputsWindow();
	}
}
