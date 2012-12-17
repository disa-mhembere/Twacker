package edu.jhu.twacker.client.manager;

import java.util.Date;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

/**
 * 	The ChartManager class is used to construct graphs from
 * 	the AsyncCallback result string and track the number of
 * 	callbacks received.  
 */
public class ChartManager {
		
	private static DataTable table;
	private static int count;
	private static int colNum;
	private static boolean first;
	
	/**
	 * 	Creates a PieChart from the given data string
	 * 	@param result	the result string
	 */
	public static PieChart createPieChart(String result) {
		return new PieChart(createPieData(result), createPieOptions(result));
	}
	
	/**
	 * 	Creates the data table underlying the pie chart by extracting the
	 * 	sentiment counts from the result string
	 * 	@param result	the result string
	 * 	@return	The data table used to create the pie chart
	 */
	private static AbstractDataTable createPieData(String result) {
		DataTable data2 = DataTable.create();
		data2.addColumn(ColumnType.STRING, "Sentiment");
		data2.addColumn(ColumnType.NUMBER, "Number");
		data2.addRows(4);
		
		String[] days = result.substring(result.indexOf("positive"), result.indexOf("}, \"experts\"")).split(",");
		int[] counts = new int[days.length];
		for (int i = 0; i < days.length; i++) {
			counts[i] = Integer.parseInt(days[i].replaceAll("\\D", ""));
		}
		
		data2.setValue(0, 0, "Positive");
		data2.setValue(0, 1, counts[0]);
		
		data2.setValue(1, 0, "Negative");
		data2.setValue(1, 1, counts[1]);
		
		data2.setValue(2, 0, "Neutral");
		data2.setValue(2, 1, counts[2]);
		
		data2.setValue(3, 0, "ERROR");
		data2.setValue(3, 1, counts[4]);

		return data2;
	}
	
	/**
	 * 	The options for the PieChart. Sets the title to the search term and sets the size and colors of the chart.
	 * @param result
	 * @return
	 */
	private static Options createPieOptions(String result) {
		Options options = Options.create();
		options.setWidth(700);
		options.setHeight(175);
		options.setTitle("Sentiment Results for "	+ result.substring(result.indexOf(": \"")+3, result.indexOf("\", ")));
		options.setColors("GreenYellow", "Red", "LightGray");
		return options;
	}
	
	/**
	 * 	Creates a LineChart from the given data string
	 * 	@param result	the result string
	 */
	public static LineChart createLineChart() {
		return new LineChart(table, createLineOptions());
	}
	
	/**
	 * 	Initialize the data table for the for LineChart and
	 * 	the callback count
	 */
	public static void initDataTable() {
		table = DataTable.create();
		table.addColumn(ColumnType.DATE, "Day");
		count = 0;	//number of callbacks received
		colNum = 0;	//number of callbacks with meaningful data
		first = true;	//first successful callback
	}	
	
	/**
	 * Update the LineChart data table with the frequency data points
	 * for the given search term in the result string. 
	 * @param result the result string 
	 */
	@SuppressWarnings("deprecation")
	public static void updateTable(String result) {
		if (!result.contains("\"data\": []")) {
			table.addColumn(ColumnType.NUMBER, 
							result.substring(result.indexOf(": \"")+3, result.indexOf("\",")));
			
			String[] days = result.substring(result.indexOf("data"), result.indexOf("],")).split(",");
			int[] counts = new int[days.length];
			for (int i = 0; i < days.length; i++) {
				counts[i] = Integer.parseInt(days[i].replaceAll("\\D", ""));
			}
			
			if (first) {
				table.addRows(counts.length);
			}
			Date d = new Date();
			d.setDate(d.getDate()-counts.length);
	
			for (int i = 0; i < counts.length; i++) {
				if (first) {
					d.setDate(d.getDate()+1);
					table.setValue(i, 0, d);
				}
				table.setValue(i, colNum+1, counts[i]);
			}
			first = false;
			colNum++;
		}
		count++;
	}
	
	/**
	 * Options for the LineChart that set the size, title, and format the axes.
	 * @return
	 */
	public static Options createLineOptions() {
		Options options = Options.create();
		options.setWidth(700);
		options.setHeight(525);
		options.setTitle("Twacker Frequency");
		options.setPointSize(5);
		AxisOptions opt = AxisOptions.create();
		opt.set("viewWindowMode", "maximized");
		options.setHAxisOptions(opt);
		return options;
	}
	
	/**
	 * Creates an Expert panel that contains 5 experts
	 * and displays their Twitter info and influence level.
	 * @param result	the result string
	 * @return	The panel containing the experts
	 */
	public static HorizontalPanel createExpertsPanel(String result) {
		if (!result.contains("\"experts\" : []")) {
			String expertStr = result.substring(result.indexOf("experts"), result.indexOf("] } }"));
			String[] experts = expertStr.split("} , \\{");

			HorizontalPanel panel = new HorizontalPanel();
			for (int i = 0; i < experts.length; i++) {
				VerticalPanel expert = new VerticalPanel();
				String photoUrl = experts[i].substring(experts[i].indexOf("\"photoUrl\" : \"")+14, experts[i].indexOf("\", \"influenceLevel\""));
				Image im = new Image(photoUrl);
				im.getElement().getStyle().setWidth(100, Unit.PX);
				im.getElement().getStyle().setHeight(100, Unit.PX);
				expert.add(im);
				String name = experts[i].substring(experts[i].indexOf("username\" : \"")+13, experts[i].indexOf("\", \"photoUrl"));
				String url = experts[i].substring(experts[i].indexOf("\"url\" : \"")+9);
				url = url.substring(0, url.indexOf("\""));
				expert.add(new HTML("<a href='" + url + "'>"+name+"</a>"));
				String influence = experts[i].substring(experts[i].indexOf("\"influenceLevel\" : \"")+20, experts[i].indexOf("\", \"url\""));
				expert.add(new Label("Influence : " + influence));
				expert.getElement().getStyle().setPadding(20, Unit.PX);
				panel.add(expert);
			}
			return panel;
		}
		else 
			return null;
	}
	
	/**
	 * Getter for callback count
	 * @return The number of callbacks
	 */
	public static int getCount() {
		return count;
	}
	
	/**
	 * Increments the callback counter
	 */
	public static void plusCount() {
		count++;
	}
}
