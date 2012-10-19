package desktop;

import calculator.Calculator;
import calculator.exception.CalculationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.math.MathContext;
import java.math.RoundingMode;

public class Client extends JFrame {

    static private Logger logger;

    static private Calculator calculator;

    static private JTextField textField;
    static private JTextArea textArea;
    static private JButton buttonClear;
    static private JToggleButton buttonVerbose;
    static private JSpinner spinner;
    static private JComboBox comboBox;

    static private final String newline = System.getProperty("line.separator");

    public Client() {

        super("Calculator");
        logger = LoggerFactory.getLogger(Client.class.getName());
        calculator = new Calculator();
        initGUI();

    }

    private void initGUI() {

        logger.debug("Desktop client start");

        add(BorderLayout.PAGE_START, makeTextField());
        add(BorderLayout.CENTER, new JScrollPane(makeTextArea()));

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel("Precision:"));
        panel.add(makeSpinnerPrecision());
        panel.add(new JLabel("Rounding Mode:"));
        panel.add(makeComboBoxRoundingMode());
        panel.add(makeButtonClear());
        panel.add(makeButtonVerbose());
        add(BorderLayout.PAGE_END, panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.debug("Desktop client stop");
            }
        });
        setMinimumSize(new Dimension(800, 600));
        pack();
        setVisible(true);

    }

    private JTextField makeTextField() {
        textField = new JTextField();
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expression = textField.getText();
                textArea.append(expression + newline);
                try {
                    logger.debug("Calculate expression: " + expression);
                    String result = calculator.calculate(expression).toString();
                    if (buttonVerbose.isSelected()) {
                        textArea.append(calculator.getProcessBuffer().toString() + newline);
                        textArea.append("=" + newline + result + newline);
                    } else {
                        textArea.append("=" + newline + result + newline);
                    }
                    logger.debug("Calculation result: " + result);
                } catch (Exception ex) {
                    logger.error(ex.toString());
                    if (CalculationException.class.isInstance(ex)) {
                        int position = ((CalculationException) ex).token.getStart();
                        textField.setCaretPosition(position);
                        if (buttonVerbose.isSelected()) {
                            textArea.append(calculator.getProcessBuffer().toString() + newline);
                        }
                        textArea.append(ex.toString() + newline);
                    }
                }
                textArea.append(newline);
                // set caret to end of  text in textArea
                textArea.setCaretPosition(textArea.getDocument().getLength());
            }
        });
        return textField;
    }

    private JTextArea makeTextArea() {
        textArea = new JTextArea();
        textArea.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 3) {
                    textArea.setText("");
                    textField.grabFocus();
                }
            }
        });
        textArea.setEditable(false);
        textArea.setTabSize(4);
        return textArea;
    }

    private JButton makeButtonClear() {
        buttonClear = new JButton("Clear");
        buttonClear.setMnemonic(KeyEvent.VK_C);
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.debug("Calculator output clear");
                textArea.setText("");
                textField.grabFocus();
            }
        });
        return buttonClear;
    }

    private JToggleButton makeButtonVerbose() {
        buttonVerbose = new JToggleButton("Verbose");
        buttonVerbose.setMnemonic(KeyEvent.VK_V);
        buttonVerbose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.debug("Calculator verbose set to " + buttonVerbose.isSelected());
                textField.grabFocus();
            }
        });
        return buttonVerbose;
    }

    private JSpinner makeSpinnerPrecision() {
        spinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        spinner.setValue(calculator.getMathContext().getPrecision());
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                logger.debug("Calculator precision set to " + spinner.getValue());
                setCalculatorMathContext();
                textField.grabFocus();
            }
        });
        return spinner;
    }

    private JComboBox makeComboBoxRoundingMode() {
        comboBox = new JComboBox(RoundingMode.values());
        comboBox.setSelectedItem(calculator.getMathContext().getRoundingMode());
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.debug("Calculator roundingMode set to " + comboBox.getSelectedItem());
                setCalculatorMathContext();
                textField.grabFocus();
            }
        });
        return comboBox;
    }

    private void setCalculatorMathContext() {
        calculator.setMathContext(new MathContext((Integer) spinner.getValue(), (RoundingMode) comboBox.getSelectedItem()));
    }

}
