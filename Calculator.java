import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

class Calculator {
    private JFrame frame;
    private JTextField display;
    private JTextArea historyArea;
    private double num1, num2, result;
    private char operator;
    private ArrayList<String> history;

    public Calculator() {
        history = new ArrayList<>();

        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 700);
        frame.setLayout(new BorderLayout());

        display = new JTextField();
        display.setFont(new Font("San Francisco", Font.PLAIN, 32));
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(display, BorderLayout.NORTH);

        historyArea = new JTextArea();
        historyArea.setFont(new Font("San Francisco", Font.PLAIN, 16));
        historyArea.setEditable(false);
        historyArea.setBackground(Color.LIGHT_GRAY);
        historyArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(new JScrollPane(historyArea), BorderLayout.EAST);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 4, 10, 10));

        String[] buttons = {
                "AC", "C", "⌫", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", ".", "=", "H"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("San Francisco", Font.PLAIN, 24));
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            if (text.equals("AC") || text.equals("C") || text.equals("⌫") || text.equals("/") || text.equals("*") || text.equals("-") || text.equals("+") || text.equals("=")) {
                button.setBackground(new Color(255, 159, 10));
                button.setForeground(Color.WHITE);
            } else if (!text.equals("") && !text.equals("H")) {
                button.setBackground(Color.WHITE);
                button.setForeground(Color.BLACK);
            } else if (text.equals("H")) {
                button.setBackground(new Color(0, 122, 255));
                button.setForeground(Color.WHITE);
            } else {
                button.setEnabled(false);
                button.setVisible(false);
            }

            button.addActionListener(new ButtonClickListener());
            button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            button.setOpaque(true);
            button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panel.add(button);
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".")) {
                display.setText(display.getText() + command);
            } else if (command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/")) {
                num1 = Double.parseDouble(display.getText());
                operator = command.charAt(0);
                display.setText("");
            } else if (command.equals("=")) {
                num2 = Double.parseDouble(display.getText());

                switch (operator) {
                    case '+':
                        result = num1 + num2;
                        break;
                    case '-':
                        result = num1 - num2;
                        break;
                    case '*':
                        result = num1 * num2;
                        break;
                    case '/':
                        if (num2 != 0) {
                            result = num1 / num2;
                        } else {
                            display.setText("Error");
                            return;
                        }
                        break;
                }
                display.setText(String.valueOf(result));
                history.add(num1 + " " + operator + " " + num2 + " = " + result);
            } else if (command.equals("C")) {
                display.setText("");
            } else if (command.equals("AC")) {
                display.setText("");
                history.clear();
                historyArea.setText("");
            } else if (command.equals("⌫")) {
                String currentText = display.getText();
                if (currentText.length() > 0) {
                    display.setText(currentText.substring(0, currentText.length() - 1));
                }
            } else if (command.equals("H")) {
                StringBuilder historyText = new StringBuilder();
                for (String entry : history) {
                    historyText.append(entry).append("\n");
                }
                historyArea.setText(historyText.toString());
            }
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
