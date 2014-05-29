package application;

import java.util.ArrayList;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.PGNode;

import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.paint.Color;

public class Function {
	private String formula;
	private String expression;
	private String expression2;
	private String expression3;
	private double from, to, step;
	Color color;
	String style;
	StringBuilder styleBuilder;
	//ArrayList<XYChart.Series<Number, Number>> list;
	XYChart.Series<Number, Number> series;

	Function(String exp, double from, double to, double step, Color cl,
			String style, StringBuilder styleBuilder)
			throws UnknownFunctionException, UnparsableExpressionException {
		this.formula =this.expression =  exp;
		this.from = from;
		this.to = to;
		this.step = step;
		this.color = cl;
		this.style = style;
		this.styleBuilder = styleBuilder;
		//list = new ArrayList<XYChart.Series<Number, Number>>();

		series = new XYChart.Series<Number, Number>();
		series.setName(formula);
		double fromTo = to - from;
		//double tempx=0, tempy=0, m = 0;

		for (double i = 0; i <= fromTo; i += step) {
			double xVal = (float) (from + i);
			double ret;
			// System.out.println("formula  = "+expression+"\t from = "+from+"\t to = "+to+"\t step = "+step);
			ret = (double) Calculator.calculate(expression, "x", xVal);
			
			
			if (!Double.isNaN(ret) && ret != Double.POSITIVE_INFINITY
					&& ret != Double.NEGATIVE_INFINITY) {
				series.getData().add(
						new XYChart.Data<Number, Number>(xVal, ret));
				// System.out.println("color : " + cl.toString());

			}
		}

	}

	Function(String formula,String exp1, String exp2, String exp3, double from, double to,
			double step, Color cl, String style, StringBuilder styleBuilder)
			throws UnknownFunctionException, UnparsableExpressionException {
		this.formula = formula;
		this.expression = exp1;
		this.expression2 = exp2;
		this.expression3 = exp3;
		this.from = from;
		this.to = to;
		this.step = step;
		this.color = cl;
		this.style = style;
		this.styleBuilder = styleBuilder;

		series = new XYChart.Series<Number, Number>();
		series.setName(formula);
		double fromTo = to - from;

		for (double i = 0; i <= fromTo; i += step) {
			double xVal = (float) (from + i);
			double ret;
			// System.out.println("formula  = "+expression+"\t from = "+from+"\t to = "+to+"\t step = "+step);
			ret = (double) Calculator.calculate(expression, "x", xVal);
			if (!Double.isNaN(ret) && ret != Double.POSITIVE_INFINITY
					&& ret != Double.NEGATIVE_INFINITY) {
				if (ret == 1) {
					ret = (double) Calculator.calculate(exp2, "x", xVal);
				} else {
					ret = (double) Calculator.calculate(exp3, "x", xVal);
				}
				series.getData().add(
						new XYChart.Data<Number, Number>(xVal, ret));
				// System.out.println("color : " + cl.toString());

			}
		}

	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getExpression2() {
		return expression2;
	}

	public void setExpression2(String expression2) {
		this.expression2 = expression2;
	}

	public String getExpression3() {
		return expression3;
	}

	public void setExpression3(String expression3) {
		this.expression3 = expression3;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public StringBuilder getStyleBuilder() {
		return styleBuilder;
	}

	public void setStyleBuilder(StringBuilder styleBuilder) {
		this.styleBuilder = styleBuilder;
	}

	@Override
	public String toString() {
		return "Function " + expression;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public double getFrom() {
		return from;
	}

	public void setFrom(double from) {
		this.from = from;
	}

	public double getTo() {
		return to;
	}

	public void setTo(double to) {
		this.to = to;
	}

	public double getStep() {
		return step;
	}

	public void setStep(double step) {
		this.step = step;
	}

	public XYChart.Series<Number, Number> getSeries() {
		return series;
	}

	public void setSeries(XYChart.Series<Number, Number> series) {
		this.series = series;
	}
}
