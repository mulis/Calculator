package console;

import calculator.Calculator;

import java.math.MathContext;
import java.util.Scanner;

public class ConsoleClient {

    static private Calculator calculator;

    public static void main(String[] args) {

        try {

            if (args.length > 0) {

                calculator = new Calculator();

                // discover keys
                boolean console = true;
                Integer precision = null;
                boolean verbose = false;
                boolean interactive = false;
                boolean help = false;
                String expression = null;

                for (String arg : args) {
                    if (arg.equalsIgnoreCase("-c")) console = true;
                    else if (arg.toLowerCase().startsWith("-p:")) precision = Integer.decode(args[2].substring(3));
                    else if (arg.equalsIgnoreCase("-v")) verbose = true;
                    else if (arg.equalsIgnoreCase("-i")) interactive = true;
                    else if (arg.equalsIgnoreCase("-h")) help = true;
                    else expression = arg;
                }

                // process keys
                if (precision != null) setPrecision(precision);
                if (help) {
                    help();
                } else if (interactive) {
                    interactive(verbose);
                } else if (console) {
                    console(expression, verbose);
                }

            } else {

                help();

            }

        } catch (Exception ex) {
            System.err.println(ex);
        }

    }

    private static void setPrecision(Integer precision) {

        calculator.setMathContext(new MathContext(precision));

    }

    private static void help() {
        String name = ConsoleClient.class.getName();
        System.out.println(
                "Calculation program.\n" +
                        "Usage: " + name + " [[-c] [-p:number] [-v] \"expression\" | [-c] [-p:number] [-v] [-i] | -h]\n" +
                        "\t" + name + " : start gui\n" +
                        "\t\"expression\" : expression to calculate\n" +
                        "\t-c : calculate to console\n" +
                        "\t-p:number : set precision to \"number\" digits \n" +
                        "\t-v : calculate verbosely\n" +
                        "\t-i : start console interactive mode\n" +
                        "\t-h : print help\n" +
                        "Valid expression operations:\n" +
                        "\taddition : +\n" +
                        "\tsubtraction : -\n" +
                        "\tparentheses : ()\n" +
                        "Valid signed numbers must be enclosed in parentheses: (-2)\n"
        );
    }

    private static void interactive(boolean verbose) {
        System.out.println(
                "Interactive mode.\n" +
                        "Input expression and press enter key.\n" +
                        "To exit input dot symbol and press enter key\n"
        );
        Scanner in = new Scanner(System.in);
        String expression;
        while (true) {
            if (in.hasNextLine()) {
                expression = in.nextLine().replaceFirst("\\n$", "");
                if (expression.equals(".")) {
                    break;
                }
                if (expression.equals("")) {
                    System.err.println("Expression not found");
                } else {
                    outputCalculation(expression, verbose);
                }
            }
        }
    }

    private static void console(String expression, boolean verbose) {
        if (expression == null) {
            System.err.println("Expression not found");
        } else {
            System.out.println(expression);
            outputCalculation(expression, verbose);
        }
    }

    private static void outputCalculation(String expression, boolean verbose) {
        try {
            String result = calculator.calculate(expression).toString();
            if (verbose) {
                System.out.println(calculator.getProcessBuffer().toString());
            }
            System.out.println("=\n" + result);
        } catch (Exception ex) {
            if (verbose) {
                System.out.println(calculator.getProcessBuffer().toString());
            }
            System.err.println(ex.toString());
        }
    }

}

