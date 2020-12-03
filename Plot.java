import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * 
 * @author JRTSN
 * Extends JFrame to display the data and uses JFreeChart as an external library
 */
public class Plot extends JFrame {
	
	Student student = new Student();
	//Add attendance = new Add();
	
	/**
	 * 
	 * @param table
	 * Creates the plot chart and finds all the data
	 */
	public Plot(JTable table)
	{	
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		int numColumns = model.getColumnCount();
		int numRows = model.getRowCount();
		
		
		String chartTitle = "Attendance";
	    String xAxisLabel = "ID Number";
	    String yAxisLabel = "Minutes Attended";
	    
	    XYSeriesCollection data = new XYSeriesCollection();
	    XYSeries series1 = new XYSeries("Attendance 1");
	 
	    /*
	     * Test Data:
	     * series1.add(1.0, 0.3);
	     * series1.add(2.0, 0.3);
	     * series1.add(3.0, 0.25);
	     * series1.add(3.5, 0.35);
	     * series1.add(4.2, 0.60);*/
	    
	    String date = null;
	    LinkedList<String> dates = new LinkedList();
	    int numDates = 0;
	    int totalDates = numColumns - 6;
	    XYDataset dataset = data;
	    
	    /*
	     * Moves through the rows
	     */
	    for(int i = 0; i < numRows; i++)
	    {
	    	/*
	    	 * Moves through the columns
	    	 */
	    	for(int j = 6; j < numColumns; j++)
	    	{
	    		double id = (double) model.getValueAt(i, 1);
	    		double time = (double) model.getValueAt(i, j);
	    		time = time / 75 * 100;
	    		dates.add(model.getColumnName(j));
	    		series1.add(id, time);
	    		numDates++;
	    	}
	    	data.addSeries(series1);
	    }
		 
	    JFreeChart chart = ChartFactory.createScatterPlot(chartTitle, xAxisLabel, yAxisLabel, dataset);
		
		XYPlot xyPlot = (XYPlot) chart.getPlot();
        xyPlot.setRangeCrosshairVisible(true);
        LegendItemCollection chartLegend = new LegendItemCollection();
        Shape shape = new Rectangle(10, 10);
        /*
         * Sets the legend
         */

        Random rand = new Random();
        for(int i = 0; i < totalDates; i++)
        {
        	float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();

            Color randomColor = new Color(r, g, b);
        	chartLegend.add(new LegendItem(dates.get(i), null, null, null, shape, randomColor));
        }
        xyPlot.setFixedLegendItems(chartLegend);
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesPaint(0, Color.blue);
        NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis(); //Sets xAxis
        NumberAxis range = (NumberAxis) xyPlot.getRangeAxis(); //Sets yAxis
        range.setRange(0, 100); //YAxis is 0 to 100
        range.setTickUnit(new NumberTickUnit(5));
        
        JPanel chartPanel = new ChartPanel(chart);
        
		//JFrame frame = new JFrame();
        this.add(chartPanel, BorderLayout.CENTER);
        this.setSize(1920, 1080);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * 
	 * @param table
	 * Method called in order to display the table
	 */
	public void display(JTable table)
	{
		new Plot(table).setVisible(true);
	}
}
