package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**
 * Calculator program implements easy-to-use graphical calculator, similar to
 * old Windows calculator.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class Calculator extends JFrame {

    /** Serial. */
    private static final long serialVersionUID = -5531362847702585460L;

    /** Buttons. */
    private HashMap<String, JButton> buttonMap;
    /** Calculator stack. */
    private Stack<Double> stack;

    /** Calculator display. */
    private JLabel display;
    /** Reset calculator display. */
    private static boolean resetDisplay;
    /** Last number in display. */
    private double lastNumber;
    /** Inverse check-box. */
    private static boolean inverseOp;
    /** Last operation. */
    private BinaryOperation lastOperation;

    /**
     * Constructor for Calculator class.
     */
    public Calculator() {
        buttonMap = new HashMap<String, JButton>();
        stack = new Stack<Double>();
        setLocation(20, 50);
        setTitle("Calculator");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI();
    }

    /**
     * Initializes GUI.
     */
    private void initGUI() {
        CalcLayout pane = new CalcLayout(5);
        JPanel p = new JPanel(pane);

        display = new JLabel("0", SwingConstants.RIGHT);
        display.setMinimumSize(new Dimension(75, 75));
        display.setVerticalAlignment(SwingConstants.BOTTOM);
        display.setBackground(Color.WHITE);
        display.setOpaque(false);
        display.setFont(new Font(display.getFont().toString(), Font.BOLD, 30));

        p.add(display, new RCPosition(1, 1));

        addNumberButtons(p);
        addOperatorButtons(p);
        addFunctionButtons(p);
        addControlButtons(p);
        getContentPane().add(p);
    }

    /**
     * Adds calculator control buttons on given panel.
     * 
     * @param p
     *            Panel.
     */
    private void addControlButtons(JPanel p) {
        JButton clr = createButton("clr");
        JButton res = createButton("res");
        JButton push = createButton("push");
        JButton pop = createButton("pop");
        JCheckBox inverse = new JCheckBox("Inv");

        inverse.setToolTipText("Inverse trigonometric and exp operations");

        ActionListener clear = e -> {
            display.setText("0");
        };

        ActionListener reset = e -> {
            display.setText("0");
            resetDisplay = true;
            inverse.setSelected(false);
            lastNumber = 0.0;
            lastOperation = null;
        };

        ActionListener pushStack = e -> {
            String text = display.getText();
            double number;
            try {
                number = Double.parseDouble(text);
                stack.push(number);
            } catch (NumberFormatException ign) {
            }
        };

        ActionListener popStack = e -> {
            if (stack.isEmpty()) {
                display.setText("Empty stack");
                resetDisplay = true;
                return;
            }
            Double number = stack.pop();
            display.setText(number.toString());
        };

        inverse.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (inverse.isSelected()) {
                    inverseOp = true;
                } else {
                    inverseOp = false;
                }
                inverseButtonName();
                repaint();
            }
        });

        clr.addActionListener(clear);
        res.addActionListener(reset);
        push.addActionListener(pushStack);
        pop.addActionListener(popStack);

        p.add(clr, "1,7");
        p.add(res, new RCPosition(2, 7));
        p.add(push, new RCPosition(3, 7));
        p.add(pop, new RCPosition(4, 7));
        p.add(inverse, new RCPosition(5, 7));
    }

    /**
     * Adds calculator function buttons on given panel.
     * 
     * @param p
     *            Panel.
     */
    private void addFunctionButtons(JPanel p) {
        ActionListener execOperation = e -> {
            JButton button = (JButton) e.getSource();
            String buttonName = button.getText();
            UnaryOperation operation = getUnaryOperation(buttonName);

            double value = Double.parseDouble(display.getText());
            double result = executeUnaryOperation(operation, value);
            display.setText(Double.toString(result));
        };

        ActionListener xPownOp = e -> {
            executeBinaryOperation(new PownOperation());
        };

        JButton invx = createButton("1/x");
        invx.addActionListener(execOperation);

        JButton xpown = createButton("x^n");
        xpown.addActionListener(xPownOp);

        JButton sine = createButton("sin");
        sine.addActionListener(execOperation);

        JButton log = createButton("log");
        log.addActionListener(execOperation);

        JButton cosine = createButton("cos");
        cosine.addActionListener(execOperation);

        JButton ln = createButton("ln");
        ln.addActionListener(execOperation);

        JButton tan = createButton("tan");
        tan.addActionListener(execOperation);

        JButton ctg = createButton("ctg");
        ctg.addActionListener(execOperation);

        p.add(invx, new RCPosition(2, 1));
        p.add(sine, new RCPosition(2, 2));
        p.add(log, new RCPosition(3, 1));
        p.add(cosine, new RCPosition(3, 2));
        p.add(ln, new RCPosition(4, 1));
        p.add(tan, new RCPosition(4, 2));
        p.add(xpown, new RCPosition(5, 1));
        p.add(ctg, new RCPosition(5, 2));
    }

    /**
     * Returns unary operation associated with given calculator button.
     * 
     * @param buttonName
     *            Button name.
     * @return Instance of {@link UnaryOperation}.
     */
    private UnaryOperation getUnaryOperation(String buttonName) {
        switch (buttonName) {
        case "1/x":
            return new InverseOperation();
        case "sin":
            return new SinOperation();
        case "cos":
            return new CosOperation();
        case "tan":
            return new TanOperation();
        case "ctg":
            return new CtgOperation();
        case "log":
            return new LogOperation();
        case "ln":
            return new LnOperation();
        case "asin":
            return new SinOperation();
        case "acos":
            return new CosOperation();
        case "atan":
            return new TanOperation();
        case "actg":
            return new CtgOperation();
        case "10^x":
            return new LogOperation();
        case "e^x":
            return new LnOperation();
        default:
            return null;
        }
    }

    /**
     * Executes given operation over given value.
     * 
     * @param operation
     *            Operation to execute.
     * @param value
     *            Value.
     * @return Result of operation.
     */
    private double executeUnaryOperation(UnaryOperation operation, double value) {
        return operation.execute(value);
    }

    /**
     * Adds calculator number buttons on given panel.
     * 
     * @param p
     *            Panel.
     */
    private void addNumberButtons(JPanel p) {
        JButton[] buttons = new JButton[10];

        ActionListener action = e -> {
            JButton button = (JButton) e.getSource();
            String number = button.getText();
            String text = display.getText();
            if (text.equals("0")) {
                display.setText("");
            }
            if (resetDisplay) {
                display.setText("");
            }
            display.setText(display.getText() + number);
            resetDisplay = false;
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = createButton(Integer.toString(i));
            buttons[i].setForeground(Color.red);
            buttons[i].addActionListener(action);
        }
        p.add(buttons[0], new RCPosition(5, 3));
        for (int i = 0; i < 3; i++) {
            p.add(buttons[i + 1], new RCPosition(4, 3 + i));
        }
        for (int i = 3; i < 6; i++) {
            p.add(buttons[i + 1], new RCPosition(3, i));
        }
        for (int i = 6; i < 9; i++) {
            p.add(buttons[i + 1], new RCPosition(2, i - 3));
        }
    }

    /**
     * Adds calculator operator buttons on given panel.
     * 
     * @param p
     *            Panel.
     */
    private void addOperatorButtons(JPanel p) {
        ActionListener decimalPoint = e -> {
            if (!display.getText().contains(".")) {
                display.setText(display.getText() + ".");
            }
        };

        ActionListener signChange = e -> {
            String text = display.getText();
            if (text.equals("0")) {
                return;
            }
            if (text.startsWith("-")) {
                display.setText(text.substring(1));
            } else {
                display.setText("-" + text);
            }
        };

        ActionListener divOp = e -> {
            executeBinaryOperation(new DivisionOperation());
        };

        ActionListener mulOp = e -> {
            executeBinaryOperation(new MultiplyOperation());
        };

        ActionListener plusOp = e -> {
            executeBinaryOperation(new AddOperation());
        };

        ActionListener subOp = e -> {
            executeBinaryOperation(new SubOperation());
        };

        ActionListener result = e -> {
            executeBinaryOperation(null);
        };

        JButton equal = createButton("=");
        equal.addActionListener(result);

        JButton division = createButton("/");
        division.addActionListener(divOp);

        JButton multiply = createButton("*");
        multiply.addActionListener(mulOp);

        JButton minus = createButton("-");
        minus.addActionListener(subOp);

        JButton plus = createButton("+");
        plus.addActionListener(plusOp);

        JButton decimal = createButton(".");
        decimal.addActionListener(decimalPoint);

        JButton sign = createButton("+/-");
        sign.addActionListener(signChange);

        p.add(equal, new RCPosition(1, 6));
        p.add(division, new RCPosition(2, 6));
        p.add(multiply, new RCPosition(3, 6));
        p.add(minus, new RCPosition(4, 6));
        p.add(plus, new RCPosition(5, 6));
        p.add(decimal, new RCPosition(5, 5));
        p.add(sign, new RCPosition(5, 4));
    }

    /**
     * Creates button with given text.
     * 
     * @param text
     *            Button text.
     * @return Instance of {@link JButton}.
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setMinimumSize(new Dimension(40, 40));
        button.setPreferredSize(new Dimension(75, 60));
        button.setFont(new Font(button.getFont().toString(), Font.BOLD, 15));
        buttonMap.put(text, button);
        return button;
    }

    /**
     * Executes given binary operation over stored values.
     * 
     * @param operator
     *            Instance of {@link BinaryOperation}.
     */
    private void executeBinaryOperation(BinaryOperation operator) {
        Double value;
        try {
            value = Double.parseDouble(display.getText());
        } catch (NumberFormatException e) {
            display.setText("Invalid operation");
            resetDisplay = true;
            return;
        }
        if (lastOperation != null) {
            double result = processLastOperator();
            display.setText(Double.toString(result));
            lastNumber = result;
        } else {
            lastNumber = value;
        }
        resetDisplay = true;
        lastOperation = operator;
    }

    /**
     * Process last operator.
     * 
     * @return Result of last entered operation.
     */
    double processLastOperator() {
        double result = 0;
        if (lastOperation != null) {
            double valueInDisp = Double.parseDouble(display.getText());
            result = lastOperation.execute(lastNumber, valueInDisp);
        }
        return result;
    }

    /**
     * BinaryOperation interface represents mathematical operation with two
     * operands.
     * 
     * @author Filip Gulan
     * @version 1.0
     *
     */
    private interface BinaryOperation {
        /**
         * Executes operation over given operands.
         * 
         * @param value1
         *            First operand.
         * @param value2
         *            Second operand.
         * @return Result of operation.
         */
        public double execute(double value1, double value2);
    }

    /**
     * AddOperation class implements binary operation of adding two numbers.
     * 
     * @author Filip Gulan
     * @version 1.0
     *
     */
    private static class AddOperation implements BinaryOperation {
        @Override
        public double execute(double value1, double value2) {
            return value1 + value2;
        }
    }

    /**
     * SubOperation class implements binary operation of subtracting two
     * numbers.
     * 
     * @author Filip Gulan
     * @version 1.0
     *
     */
    private static class SubOperation implements BinaryOperation {
        @Override
        public double execute(double value1, double value2) {
            return value1 - value2;
        }
    }

    /**
     * MultiplyOperation class implements binary operation of multiplying two
     * numbers.
     * 
     * @author Filip Gulan
     * @version 1.0
     *
     */
    private static class MultiplyOperation implements BinaryOperation {
        @Override
        public double execute(double value1, double value2) {
            return value1 * value2;
        }
    }

    /**
     * MultiplyOperation class implements binary operation of dividing two
     * numbers.
     * 
     * @author Filip Gulan
     * @version 1.0
     *
     */
    private static class DivisionOperation implements BinaryOperation {
        @Override
        public double execute(double value1, double value2) {
            return value1 / value2;
        }
    }

    /**
     * PownOperation class implements operation of raising the first operand to
     * the power of the second operand.
     * 
     * @author Filip Gulan
     * @version 1.0
     *
     */
    private static class PownOperation implements BinaryOperation {
        @Override
        public double execute(double value1, double value2) {
            if (inverseOp) {
                return Math.pow(value2, 1.0 / value1);
            }
            return Math.pow(value1, value2);
        }
    }

    /**
     * UnaryOperation interface represents mathematical operation with one
     * operand.
     * 
     * @author Filip Gulan
     * @version 1.0
     *
     */
    private interface UnaryOperation {
        /**
         * Executes operation over given operand.
         * 
         * @param value
         *            Operand
         * @return Result of operation.
         */
        public double execute(double value);
    }

    /**
     * SinOperation represents sin(operand) operation.
     * 
     * @author Filip Gulan
     * @version 1.0
     *
     */
    private static class SinOperation implements UnaryOperation {
        @Override
        /**
         * {@InheritDoc}
         */
        public double execute(double value) {
            resetDisplay = true;
            if (inverseOp) {
                return Math.asin(value);
            }
            return Math.sin(value);
        }
    }

    /**
     * CosOperation represents cos(operand) operation.
     * 
     * @author Filip Gulan
     * @version 1.0
     *
     */
    private static class CosOperation implements UnaryOperation {
        @Override
        /**
         * {@inheritDoc}
         */
        public double execute(double value) {
            resetDisplay = true;
            if (inverseOp) {
                return Math.acos(value);
            }
            return Math.cos(value);
        }
    }

    /**
     * CosOperation represents tan(operand) operation.
     * 
     * @author Filip Gulan
     * @version 1.0
     *
     */
    private static class TanOperation implements UnaryOperation {
        @Override
        /**
         * {@inheritDoc}
         */
        public double execute(double value) {
            resetDisplay = true;
            if (inverseOp) {
                return Math.atan(value);
            }
            return Math.tan(value);
        }
    }

    /**
     * CosOperation represents ctg(operand) operation.
     * 
     * @author Filip Gulan
     * @version 1.0
     *
     */
    private static class CtgOperation implements UnaryOperation {
        @Override
        /**
         * {@inheritDoc}
         */
        public double execute(double value) {
            resetDisplay = true;
            if (inverseOp) {
                return Math.PI / 2 - Math.atan(value);
            }
            return 1 / Math.tan(value);
        }
    }

    /**
     * CosOperation represents log(operand) operation.
     * 
     * @author Filip Gulan
     * @version 1.0
     *
     */
    private static class LogOperation implements UnaryOperation {
        @Override
        /**
         * {@inheritDoc}
         */
        public double execute(double value) {
            resetDisplay = true;
            if (inverseOp) {
                return Math.pow(10, value);
            }
            return Math.log10(value);
        }
    }

    /**
     * CosOperation represents ln(operand) operation.
     * 
     * @author Filip Gulan
     * @version 1.0
     *
     */
    private static class LnOperation implements UnaryOperation {
        @Override
        /**
         * {@inheritDoc}
         */
        public double execute(double value) {
            resetDisplay = true;
            if (inverseOp) {
                return Math.pow(Math.E, value);
            }
            return Math.log(value);
        }
    }

    /**
     * CosOperation represents 1/(operand) operation.
     * 
     * @author Filip Gulan
     * @version 1.0
     *
     */
    private static class InverseOperation implements UnaryOperation {
        @Override
        /**
         * {@inheritDoc}
         */
        public double execute(double value) {
            resetDisplay = true;
            return 1.0 / value;
        }
    }

    /**
     * Inverse function button names.
     */
    private void inverseButtonName() {
        if (inverseOp) {
            buttonMap.get("sin").setText("asin");
            buttonMap.get("tan").setText("atan");
            buttonMap.get("ctg").setText("actg");
            buttonMap.get("cos").setText("acos");
            buttonMap.get("ln").setText("e^x");
            buttonMap.get("log").setText("10^x");
            buttonMap.get("x^n").setText("n\u221Ax");
        } else {
            buttonMap.get("sin").setText("sin");
            buttonMap.get("tan").setText("tan");
            buttonMap.get("ctg").setText("ctg");
            buttonMap.get("cos").setText("cos");
            buttonMap.get("ln").setText("ln");
            buttonMap.get("log").setText("log");
            buttonMap.get("x^n").setText("x^n");
        }
    }

    /**
     * Start point of program Calculator.
     * 
     * @param args
     *            Command line arguments. Not used.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager
                        .getSystemLookAndFeelClassName());
            } catch (Exception e) {
            }
            JFrame frame = new Calculator();
            frame.pack();
            frame.setMinimumSize(new Dimension(550, 250));
            frame.setVisible(true);
        });
    }

}
