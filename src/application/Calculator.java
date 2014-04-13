package application;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

public class Calculator {
	public static double calculate(String form, String var, double x)
			throws UnknownFunctionException, UnparsableExpressionException {
		Calculable calc = new ExpressionBuilder(form).withVariable(var, x)
				.build();

		return calc.calculate();
	}

}
