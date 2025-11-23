package skillForge;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Paint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.util.ArrayList;

public class AnalyticsGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ChartPanel trialsPanel;
	private ChartPanel averagePanel;
	private ChartPanel scalePanel;
	private ChartPanel completionPanel;
	
	private Analytics analytics;
	private Course course;
	private String studentId;
	


	public AnalyticsGUI(String studentId,Course course,Analytics analytics) {
		
		
		this.studentId = studentId;
		this.course = course;
		this.analytics = analytics;
		setTitle("Insights for " + course.getTitle());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(900,700);
		
        contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(10,10,10,10));
		setContentPane(contentPane);
		try {
		averageChart();
		trialsChart();
		scaleChart();
		completionChart();
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		
		JPanel graphPanel = new JPanel(new GridLayout(2,2,10,10));
		graphPanel.add(averagePanel);
		graphPanel.add(trialsPanel);
		graphPanel.add(scalePanel);
		graphPanel.add(completionPanel);

		
		contentPane.add(graphPanel,BorderLayout.CENTER);
		setLocationRelativeTo(null);
		setVisible(true);


		
	}
	public void completionChart(){
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		ArrayList<Lesson> lessons = course.getLessons();
		if(lessons != null) {
		for(Lesson l : lessons) {
			double complete = analytics.isLessonPassed(studentId, l.getLessonId())?100.0:0.0;
			//System.out.println("Lesson: " + l.getTitle() + ", Completion %: " + complete);

			data.setValue(complete,"Complete % ",l.getTitle());
		}
		}

		JFreeChart completeChart = ChartFactory.createLineChart("Lesson Completion", "Lesson", "Completion %", data,PlotOrientation.VERTICAL,false,true,false);
		
		CategoryPlot linePlot = completeChart.getCategoryPlot();
		linePlot.setBackgroundPaint(SystemColor.inactiveCaption);
		
		LineAndShapeRenderer lineRen = (LineAndShapeRenderer) linePlot.getRenderer();
		lineRen.setSeriesPaint(0,Color.BLACK);
		linePlot.getRangeAxis().setRange(0.0,100.0);
		
	    completionPanel = new ChartPanel(completeChart);

		
		
	}
	public void averageChart() {
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		ArrayList<Lesson> lessons = course.getLessons();
		
		if(lessons != null) {
		for(Lesson l : lessons) {
			double average = analytics.averageScore(l.getLessonId());
			//System.out.println("Lesson: " + l.getTitle() + ", Average Score: " + average);
     
			data.setValue(average,"Average Score ",l.getTitle());
		
		
		}
		}
		
		JFreeChart averageChart = ChartFactory.createBarChart("Quiz Average", "Lesson", "Average Score", data,PlotOrientation.VERTICAL,false,true,false);
		
		CategoryPlot barPlot = averageChart.getCategoryPlot();
		barPlot.setBackgroundPaint(SystemColor.inactiveCaption);
		barPlot.getRangeAxis().setRange(0.0,100.0);
		
		BarRenderer barRen = (BarRenderer) barPlot.getRenderer();
		barRen.setSeriesPaint(0, Color.BLACK);
		
	    averagePanel = new ChartPanel(averageChart);

		
		
	}
	public void scaleChart() {
		DefaultCategoryDataset data = new DefaultCategoryDataset();
	     ArrayList<Lesson> lessons = course.getLessons();
	        if (lessons != null) {
		for(Lesson l : lessons) {
			int high = analytics.highestScore(l.getLessonId());
			int least = analytics.leastScore(l.getLessonId());
			
			
            high = (high >= 0) ? Math.min(high, 100) : 0;
            least = (least >= 0) ? Math.max(least, 0) : 0;
     
			data.setValue(high,"Highest ",l.getTitle());
			data.setValue(least,"Least ",l.getTitle());
		}
	        }
		

		
		JFreeChart scaleChart = ChartFactory.createBarChart("Highest and lowest Scores", "Lesson", " Score", data,PlotOrientation.VERTICAL,false,true,false);
		
		CategoryPlot barPlot = scaleChart.getCategoryPlot();
		barPlot.setBackgroundPaint(SystemColor.inactiveCaption);
		barPlot.getRangeAxis().setRange(0.0,5.0);
		
		BarRenderer bRen = (BarRenderer) barPlot.getRenderer();
		bRen.setSeriesPaint(0, Color.BLACK);
		bRen.setSeriesPaint(1, Color.BLACK);
		
	    scalePanel = new ChartPanel(scaleChart);

	}
	public void trialsChart() {
		DefaultCategoryDataset data = new DefaultCategoryDataset();
        ArrayList<Lesson> lessons = course.getLessons();
        if (lessons != null) {
        	int max=0;
        
		for(Lesson l : lessons) {
			int trials = analytics.getNumberOfTrials(l.getLessonId());
			data.setValue(trials, "Trials", l.getTitle());
			max = Math.max(max, trials);

		}
		
        }

		
		JFreeChart trialsChart = ChartFactory.createLineChart("Trials per Lesson ", "Lesson", " Trials", data,PlotOrientation.VERTICAL,false,true,false);
		
		CategoryPlot lPlot = trialsChart.getCategoryPlot();
		lPlot.setBackgroundPaint(SystemColor.inactiveCaption);
		lPlot.getRangeAxis().setRange(0.0,10.0);
		
		LineAndShapeRenderer bRenn = (LineAndShapeRenderer) lPlot.getRenderer();
		bRenn.setSeriesPaint(0, Color.BLACK);
		
		
	    trialsPanel = new ChartPanel(trialsChart);

	
}
}
