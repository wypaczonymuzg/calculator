package application;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.math3.analysis.function.Acosh;
import org.apache.commons.math3.analysis.function.Asinh;
import org.apache.commons.math3.analysis.function.Atanh;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.CustomFunction;
import de.congrace.exp4j.CustomOperator;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.InvalidCustomFunctionException;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

public class Calculator {

  private Calculator() {}

  private static Collection<CustomFunction> customFunctions = new ArrayList<CustomFunction>();
  private static Collection<CustomOperator> customOperators = new ArrayList<CustomOperator>();

  
  
  static {
    CustomOperator greaterEq = new CustomOperator(">=", true, 1, 2) {
      public double applyOperation(double[] values) {
        if (values[0] >= values[1]) {
          return 1d;
        } else {
          return 0d;
        }
      }
    };

    CustomOperator lessEq = new CustomOperator("<=", true, 1, 2) {
      public double applyOperation(double[] values) {
        if (values[0] <= values[1]) {
          return 1d;
        } else {
          return 0d;
        }
      }
    };


    CustomOperator equal = new CustomOperator("==", true, 1, 2) {
      public double applyOperation(double[] values) {
        if (values[0] == values[1]) {
          return 1d;
        } else {
          return 0d;
        }
      }
    };

    CustomOperator notEqual = new CustomOperator("!=", true, 1, 2) {
      public double applyOperation(double[] values) {
        if (values[0] != values[1]) {
          return 1d;
        } else {
          return 0d;
        }
      }
    };


    CustomOperator grThen = new CustomOperator(">", true, 1, 2) {
      public double applyOperation(double[] values) {
        if (values[0] > values[1]) {
          return 1d;
        } else {
          return 0d;
        }
      }
    };


    CustomOperator lessThen = new CustomOperator("<", true, 1, 2) {
      public double applyOperation(double[] values) {
        if (values[0] < values[1]) {
          return 1d;
        } else {
          return 0d;
        }
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

      customOperators.add(factorial);
      customOperators.add(greaterEq);
      customOperators.add(equal);
      customOperators.add(notEqual);
      customOperators.add(lessEq);
      customOperators.add(lessThen);
      customOperators.add(grThen);

      customFunctions.add(atanh);
      customFunctions.add(asinh);
      customFunctions.add(acosh);
      customFunctions.add(truncate);
      customFunctions.add(round);
      customFunctions.add(min);
      customFunctions.add(max);

    } catch (InvalidCustomFunctionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static double calculate(String expression,String param, double xVal)
      throws UnknownFunctionException, UnparsableExpressionException {

    expression = expression.replaceAll("π", "3.14159");
    expression = expression.replaceAll("ε", "2.718281");

    Calculable calc =
        new ExpressionBuilder(expression).withVariable("x", xVal)
            .withCustomFunctions(customFunctions).withOperations(customOperators).build();


    return calc.calculate();
  }
}

