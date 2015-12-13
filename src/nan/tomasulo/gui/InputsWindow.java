/*
 * Created by JFormDesigner on Thu Dec 10 14:59:12 EET 2015
 */

package nan.tomasulo.gui;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
	private static final long serialVersionUID = -1970657112424404666L;
	
	public static LinkedList<CacheInfoRow> cacheInfo = new LinkedList<>();

	public InputsWindow() {
		initComponents();
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
		if (cacheInfo.size() == 0)
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
		CallUDelay = new JTextField();
		lblCommonDataBuses = new JLabel();
		tfNumOfCDBs = new JTextField();
		lblJumpUnits = new JLabel();
		tfJumpUnits = new JTextField();
		lblJumpUnitsDelay = new JLabel();
		JumpUDelay = new JTextField();
		lblMemAccessDelay = new JLabel();
		tfMemAccessDelay = new JTextField();
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

		// ======== this ========
		setTitle("Tomasulo-Simulator");
		Container contentPane = getContentPane();
		contentPane
				.setLayout(new FormLayout(
						"55dlu, $lcgap, 24dlu, $lcgap, 33dlu, $lcgap, 24dlu, $lcgap, default, $lcgap, 36dlu, $lcgap, default, $lcgap, 34dlu, $lcgap, 39dlu, $lcgap, 33dlu, $lcgap, 32dlu, $lcgap, 34dlu",
						"15dlu, $lgap, pref, 5*($lgap, default), $lgap, 12dlu, $lgap, 17dlu, 7*($lgap, default)"));
		((FormLayout) contentPane.getLayout()).setRowGroups(new int[][] { { 3,
				5, 7, 9, 11, 13, 15, 17 } });

		// ---- label1 ----
		label1.setText("Reservation Stations");
		label1.setBorder(Borders.DIALOG);
		label1.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(label1, CC.xywh(1, 1, 7, 1, CC.CENTER, CC.DEFAULT));

		// ---- lblAddUnits ----
		lblAddUnits.setText("Add Units #");
		lblAddUnits.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblAddUnits, CC.xy(1, 3, CC.FILL, CC.DEFAULT));

		// ---- tfAddUnits ----
		tfAddUnits.setText("2");
		contentPane.add(tfAddUnits, CC.xy(3, 3));

		// ---- lblAddUnitsDelay ----
		lblAddUnitsDelay.setText("Delay");
		contentPane.add(lblAddUnitsDelay, CC.xy(5, 3, CC.CENTER, CC.DEFAULT));

		// ---- tfAddUDelay ----
		tfAddUDelay.setText("2");
		contentPane.add(tfAddUDelay, CC.xy(7, 3));

		// ---- lblROBSize ----
		lblROBSize.setText("Reorder Buffer Size :");
		contentPane.add(lblROBSize, CC.xywh(15, 3, 3, 1, CC.LEFT, CC.DEFAULT));
		contentPane.add(tfROBSize, CC.xy(19, 3));

		// ---- lblMultUnits ----
		lblMultUnits.setText("Mult Units #");
		lblMultUnits.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblMultUnits, CC.xy(1, 5, CC.FILL, CC.DEFAULT));

		// ---- tfMultUnits ----
		tfMultUnits.setText("2");
		contentPane.add(tfMultUnits, CC.xy(3, 5));

		// ---- lblMultUnitsDelay ----
		lblMultUnitsDelay.setText("Delay");
		contentPane.add(lblMultUnitsDelay, CC.xy(5, 5, CC.CENTER, CC.DEFAULT));

		// ---- tfMultUDelay ----
		tfMultUDelay.setText("2");
		contentPane.add(tfMultUDelay, CC.xy(7, 5));

		// ---- lblMxCommits ----
		lblMxCommits.setText("Max Commits Per Cycle :");
		contentPane
				.add(lblMxCommits, CC.xywh(15, 5, 3, 1, CC.LEFT, CC.DEFAULT));
		contentPane.add(tfMxCommitsPerCycle, CC.xy(19, 5));

		// ---- lblBranchUnits ----
		lblBranchUnits.setText("Branch Units #");
		lblBranchUnits.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblBranchUnits, CC.xy(1, 7, CC.FILL, CC.DEFAULT));

		// ---- tfBranchUnits ----
		tfBranchUnits.setText("2");
		contentPane.add(tfBranchUnits, CC.xy(3, 7));

		// ---- lbBranchUnitsDelay ----
		lbBranchUnitsDelay.setText("Delay");
		contentPane.add(lbBranchUnitsDelay, CC.xy(5, 7, CC.CENTER, CC.DEFAULT));

		// ---- tfBranchUDelay ----
		tfBranchUDelay.setText("2");
		contentPane.add(tfBranchUDelay, CC.xy(7, 7));

		// ---- lblPiplineWidth ----
		lblPiplineWidth.setText("Pipeline Width :");
		contentPane.add(lblPiplineWidth,
				CC.xywh(15, 7, 3, 1, CC.LEFT, CC.DEFAULT));
		contentPane.add(tfPipelineWidth, CC.xy(19, 7));

		// ---- lblLogicalUnits ----
		lblLogicalUnits.setText("Logical Units #");
		lblLogicalUnits.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblLogicalUnits, CC.xy(1, 9, CC.FILL, CC.DEFAULT));

		// ---- tfLogicalUnits ----
		tfLogicalUnits.setText("2");
		contentPane.add(tfLogicalUnits, CC.xy(3, 9));

		// ---- lblLogicalUnitsDelay ----
		lblLogicalUnitsDelay.setText("Delay");
		contentPane.add(lblLogicalUnitsDelay,
				CC.xy(5, 9, CC.CENTER, CC.DEFAULT));

		// ---- tfLogicalUDelay ----
		tfLogicalUDelay.setText("2");
		contentPane.add(tfLogicalUDelay, CC.xy(7, 9));

		// ---- lblInstructionBfrSize ----
		lblInstructionBfrSize.setText("Instruction Buffer Size :");
		contentPane.add(lblInstructionBfrSize,
				CC.xywh(15, 9, 3, 1, CC.LEFT, CC.DEFAULT));
		contentPane.add(tfInstructionBufferSize, CC.xy(19, 9));

		// ---- lblCallUnits ----
		lblCallUnits.setText("Call Units #");
		lblCallUnits.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCallUnits, CC.xy(1, 11, CC.FILL, CC.DEFAULT));

		// ---- tfCallUnits ----
		tfCallUnits.setText("2");
		contentPane.add(tfCallUnits, CC.xy(3, 11));

		// ---- lblCallUnitsDelay ----
		lblCallUnitsDelay.setText("Delay");
		contentPane.add(lblCallUnitsDelay, CC.xy(5, 11, CC.CENTER, CC.DEFAULT));

		// ---- CallUDelay ----
		CallUDelay.setText("2");
		contentPane.add(CallUDelay, CC.xy(7, 11));

		// ---- lblCommonDataBuses ----
		lblCommonDataBuses.setText("Common Data Buses # :");
		contentPane.add(lblCommonDataBuses,
				CC.xywh(15, 11, 3, 1, CC.LEFT, CC.DEFAULT));
		contentPane.add(tfNumOfCDBs, CC.xy(19, 11));

		// ---- lblJumpUnits ----
		lblJumpUnits.setText("Jump Units #");
		lblJumpUnits.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblJumpUnits, CC.xy(1, 13, CC.FILL, CC.DEFAULT));

		// ---- tfJumpUnits ----
		tfJumpUnits.setText("2");
		contentPane.add(tfJumpUnits, CC.xy(3, 13));

		// ---- lblJumpUnitsDelay ----
		lblJumpUnitsDelay.setText("Delay");
		contentPane.add(lblJumpUnitsDelay, CC.xy(5, 13, CC.CENTER, CC.DEFAULT));

		// ---- JumpUDelay ----
		JumpUDelay.setText("2");
		contentPane.add(JumpUDelay, CC.xy(7, 13));

		// ---- lblMemAccessDelay ----
		lblMemAccessDelay.setText("Memory Access Delay :");
		contentPane.add(lblMemAccessDelay,
				CC.xywh(15, 13, 3, 1, CC.LEFT, CC.DEFAULT));
		contentPane.add(tfMemAccessDelay, CC.xy(19, 13));

		// ---- lblStoreUnits ----
		lblStoreUnits.setText("Store Units #");
		lblStoreUnits.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblStoreUnits, CC.xy(1, 15, CC.FILL, CC.DEFAULT));

		// ---- tfStoreUnits ----
		tfStoreUnits.setText("2");
		contentPane.add(tfStoreUnits, CC.xy(3, 15));

		// ---- label2 ----
		label2.setText("Caches :");
		contentPane.add(label2, CC.xywh(15, 15, 3, 1, CC.CENTER, CC.DEFAULT));

		// ---- btnAddCache ----
		btnAddCache.setText("+");
		btnAddCache.setFocusable(false);
		btnAddCache.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnAddCacheActionPerformed(e);
			}
		});
		contentPane.add(btnAddCache, CC.xy(19, 15));

		// ---- btnRemoveCache ----
		btnRemoveCache.setText("-");
		btnRemoveCache.setFocusable(false);
		btnRemoveCache.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnRemoveCacheActionPerformed(e);
			}
		});
		contentPane.add(btnRemoveCache, CC.xy(21, 15));

		// ---- lblLoadUnits ----
		lblLoadUnits.setText("Load Units #");
		lblLoadUnits.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblLoadUnits, CC.xy(1, 17, CC.FILL, CC.DEFAULT));

		// ---- tfLoadUnits ----
		tfLoadUnits.setText("2");
		contentPane.add(tfLoadUnits, CC.xy(3, 17));

		// ======== cachesScrollPane ========
		{

			// ======== cachesPane ========
			{

				// JFormDesigner evaluation mark
				cachesPane.setBorder(new javax.swing.border.CompoundBorder(
						new javax.swing.border.TitledBorder(
								new javax.swing.border.EmptyBorder(0, 0, 0, 0),
								"JFormDesigner Evaluation",
								javax.swing.border.TitledBorder.CENTER,
								javax.swing.border.TitledBorder.BOTTOM,
								new java.awt.Font("Dialog", java.awt.Font.BOLD,
										12), java.awt.Color.red), cachesPane
								.getBorder()));
				cachesPane
						.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
							public void propertyChange(
									java.beans.PropertyChangeEvent e) {
								if ("border".equals(e.getPropertyName()))
									throw new RuntimeException();
							}
						});

				cachesPane.setLayout(new GridLayout(10, 1));

				// ======== colNamesPane ========
				{
					colNamesPane.setLayout(new GridLayout(1, 5));

					// ---- lblS ----
					lblS.setText("S");
					lblS.setHorizontalAlignment(SwingConstants.CENTER);
					colNamesPane.add(lblS);

					// ---- lblL ----
					lblL.setText("L");
					lblL.setHorizontalAlignment(SwingConstants.CENTER);
					colNamesPane.add(lblL);

					// ---- lblM ----
					lblM.setText("M");
					lblM.setHorizontalAlignment(SwingConstants.CENTER);
					colNamesPane.add(lblM);

					// ---- lblD ----
					lblD.setText("Delay");
					lblD.setHorizontalAlignment(SwingConstants.CENTER);
					colNamesPane.add(lblD);

					// ---- lblWP ----
					lblWP.setText("WB/WT");
					lblWP.setHorizontalAlignment(SwingConstants.CENTER);
					colNamesPane.add(lblWP);
				}
				cachesPane.add(colNamesPane);
			}
			cachesScrollPane.setViewportView(cachesPane);
		}
		contentPane.add(cachesScrollPane, CC.xywh(14, 17, 10, 15));
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
	private JTextField CallUDelay;
	private JLabel lblCommonDataBuses;
	private JTextField tfNumOfCDBs;
	private JLabel lblJumpUnits;
	private JTextField tfJumpUnits;
	private JLabel lblJumpUnitsDelay;
	private JTextField JumpUDelay;
	private JLabel lblMemAccessDelay;
	private JTextField tfMemAccessDelay;
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
	}

	public static void main(String[] args) {
		new InputsWindow();
	}
}
