package application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.math3.analysis.function.Acosh;
import org.apache.commons.math3.analysis.function.Asinh;
import org.apache.commons.math3.analysis.function.Atanh;
import org.apache.commons.math3.util.MathUtils;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.CustomFunction;
import de.congrace.exp4j.CustomOperator;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.InvalidCustomFunctionException;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

public class Calculator {
	//private static Collection<CustomFunction> cusFunctions;

	  private static Collection<CustomFunction> cusFunctions = new ArrayList<CustomFunction>();
	  private static Collection<CustomOperator> cusOperators = new ArrayList<CustomOperator>();
	
	
	
	public static void initFunctions() {
		
		//cusFunctions = new Collection<CustomFunction>();
		
		CustomOperator mod = new CustomOperator("%", true, 3) {
			@Override
			protected double applyOperation(double[] values) {
				if (values[1] == 0d) {
					throw new ArithmeticException("Division by zero!");
				}
				return values[0] % values[1];
			}
		};
		CustomOperator factorial = new CustomOperator("!", true, 6, 1) {
			@Override
			protected double applyOperation(double[] values) {
				double tmp = 1d;
				int steps = 1;
				while (steps < values[0]) {
					tmp = tmp * (++steps);
				}
				return tmp;
			}
		};
		try {
			CustomFunction max = new CustomFunction("max", 2) {
				@Override
				public double applyFunction(double[] values) {
					double max = values[0];
					for (int i = 1; i < this.getArgumentCount(); i++) {
						if (values[i] > max) {
							max = values[i];
						}
					}
					return max;
				}
			};
			CustomFunction min = new CustomFunction("min", 2) {
				@Override
				public double applyFunction(double[] values) {
					double min = values[0];
					for (int i = 1; i < this.getArgumentCount(); i++) {
						if (values[i] < min) {
							min = values[i];
						}
					}
					return min;
				}
			};
			CustomFunction round = new CustomFunction("round") {
				public double applyFunction(double[] values) {
					return Math.round(values[0]);
				}
			};
			CustomFunction truncate = new CustomFunction("trunc", 2) {
				public double applyFunction(double[] values) {
					return Math.floor(values[0]);
				}
			};
			CustomFunction acosh = new CustomFunction("acosh") {
				public double applyFunction(double[] values) {
					return new Acosh().value(values[0]);
				}
			};
			CustomFunction asinh = new CustomFunction("asinh") {
				public double applyFunction(double[] values) {
					return new Asinh().value(values[0]);
				}
			};
			CustomFunction atanh = new CustomFunction("atanh") {
				public double applyFunction(double[] values) {
					return new Atanh().value(values[0]);
				}
			};
			
			cusFunctions.add(atanh);
			cusFunctions.add(asinh);
			cusFunctions.add(acosh);
			cusFunctions.add(truncate);
			cusFunctions.add(round);
			cusFunctions.add(min);
			cusFunctions.add(max);
		} catch (InvalidCustomFunctionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static double calculate(String form, String var, double x)
			throws UnknownFunctionException, UnparsableExpressionException {
		form = form.replaceAll("PI", "3.14159");
		form = form.replaceAll("E", "2.718281");

		Calculable calc = new ExpressionBuilder(form).withVariable(var, x)
				.withCustomFunctions(cusFunctions).build();

		return calc.calculate();
	}

}
