package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import data.DRateParser;

public class Interface extends JComponent implements ActionListener {
	
	private List<String> data;
	private DRateParser drp;
	
	JLabel lastUpdate;
	JLabel nextUpdate;
	JButton refresh;
	
	DefaultTableModel DRateDataModel;
	JTable dRateData;
	
	JTextField average;
	
	Timer timer;
	int mult = 5;
	int time = 1000 * mult;
	
	double total;
	
	public Interface(){
		total =0;
		this.setLayout(new BorderLayout());
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		drp = new DRateParser();
		
		data = drp.retrieveData();
		
		JPanel jpStart = new JPanel(new BorderLayout());
		
		lastUpdate = new JLabel();
		lastUpdate.setText(data.remove(0));
		lastUpdate.setHorizontalAlignment(JTextField.CENTER);
		//lastUpdate.
		
		nextUpdate = new JLabel("5");
		nextUpdate.setHorizontalAlignment(JTextField.CENTER);
		
		jpStart.add(lastUpdate, BorderLayout.NORTH);
		jpStart.add(nextUpdate, BorderLayout.CENTER);
		
		this.add(jpStart, BorderLayout.PAGE_START);
		//this.add(lastUpdate, BorderLayout.CENTER);
		
		refresh = new JButton("Refresh");
		this.refresh.addActionListener(this);
		
		this.add(refresh, BorderLayout.PAGE_END);
		
		DRateDataModel = new DefaultTableModel();
		
		total = total + Double.parseDouble(data.get(1));
		DRateDataModel.addColumn(data.remove(0), new String[] {data.remove(0)});
		//dRateData.getColumnModel().getColumn(0).setHeaderValue(data.remove(0));

		//DRateDataModel.addRow
		while(!data.isEmpty()){
			total = total + Double.parseDouble(data.get(0));
			DRateDataModel.addRow(new String[] {data.remove(0)});
		}
		
		total = total/19;
		total = Math.round(total*100.0)/100.0;
		
		average = new JTextField(Double.toString(total));
		average.setEditable(false);
		average.setHorizontalAlignment(JTextField.CENTER);
		average.setFont(new Font(null,Font.BOLD,18));
		//dRateData.getColumnModel().getColumn(0).set
		
		this.dRateData = new JTable(DRateDataModel);

		this.dRateData.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		
		JTableHeader header = dRateData.getTableHeader();
		
		JPanel table = new JPanel();
		table.setLayout(new BorderLayout());
		table.add(header, BorderLayout.NORTH);
		table.add(dRateData, BorderLayout.CENTER);
		table.add(average, BorderLayout.SOUTH);
		
		this.add(table, BorderLayout.CENTER);
		
		timer = new Timer(1000,this);
		timer.setInitialDelay(1000);
		timer.setRepeats(true);
		timer.start();
	}
	
	private void setGUIData(){
		total=0;
		data.clear();
		data = drp.retrieveData();
		lastUpdate.setText(data.remove(0));
		dRateData.getColumnModel().getColumn(0).setHeaderValue(data.remove(0));
		
		int i=0;
		while(!data.isEmpty()){
			total = total + Double.parseDouble(data.get(0));
			DRateDataModel.setValueAt(data.remove(0), i, 0);
			i++;
		}
		
		total = total/19;
		total = Math.round(total*100.0)/100.0;

		average.setText(Double.toString(total));

	}
	
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.refresh || time==0) {
			this.setGUIData();
			int s = (time/1000);
			nextUpdate.setText(Integer.toString(s));
			time = 1000 * mult;
		}else if(time>0){
			int s = (time/1000);
			nextUpdate.setText(Integer.toString(s));
			time -= 1000;
		}

	}
	
	public static void main(String[] args){
		JFrame jf = new JFrame("PJM - DRate");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Interface interf = new Interface();
		jf.add(interf);

		jf.pack();
		jf.setVisible(true);
	}
}
