import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {

    JTextField input1, input2, result;
    JButton calculateBtn, clearBtn, exitBtn;
    JComboBox<String> operationBox;

    Calculator() {
        setTitle("Calculator App");
        setSize(500, 280);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center
        setLayout(new BorderLayout(10, 10));


        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        input1 = new JTextField();
        input2 = new JTextField();
        result = new JTextField();
        result.setEditable(false);

        operationBox = new JComboBox<>(new String[]{
                "Addition", "Subtraction", "Multiplication", "Division", "Power", "Square Root"
        });
        operationBox.addActionListener(e -> toggleInputFields());

        inputPanel.add(new JLabel("Select Operation:"));
        inputPanel.add(operationBox);
        inputPanel.add(new JLabel("Enter Number 1:"));
        inputPanel.add(input1);
        inputPanel.add(new JLabel("Enter Number 2:"));
        inputPanel.add(input2);
        inputPanel.add(new JLabel("Result:"));
        inputPanel.add(result);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        calculateBtn = new JButton("Calculate");
        clearBtn = new JButton("Clear");
        exitBtn = new JButton("Exit");

        buttonPanel.add(calculateBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(exitBtn);


        calculateBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        exitBtn.addActionListener(this);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        toggleInputFields(); // Initial toggle
        setVisible(true);
    }

    private void toggleInputFields() {
        String operation = (String) operationBox.getSelectedItem();
        boolean isUnary = operation.equals("Square Root");

        input2.setEnabled(!isUnary);
        input2.setEditable(!isUnary);
        input2.setText(isUnary ? "N/A" : "");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitBtn) {
            System.exit(0);
        } else if (e.getSource() == clearBtn) {
            input1.setText("");
            input2.setText("");
            result.setText("");
            input2.setEnabled(true);
            input2.setEditable(true);
            input2.setText("");
            input1.requestFocus();
        } else if (e.getSource() == calculateBtn) {
            String operation = (String) operationBox.getSelectedItem();

            float num1 = 0, num2 = 0;
            boolean validInput = true;

            try {
                if (!input1.getText().isEmpty()) num1 = Float.parseFloat(input1.getText());
                if (!operation.equals("Square Root") && !input2.getText().equals("N/A"))
                    num2 = Float.parseFloat(input2.getText());
            } catch (NumberFormatException ex) {
                validInput = false;
            }

            if (!validInput) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please enter numeric values.");
                return;
            }

            switch (operation) {
                case "Addition":
                    result.setText(String.valueOf(num1 + num2));
                    break;
                case "Subtraction":
                    result.setText(String.valueOf(num1 - num2));
                    break;
                case "Multiplication":
                    result.setText(String.valueOf(num1 * num2));
                    break;
                case "Division":
                    if (num2 == 0) {
                        result.setText("Cannot divide by 0");
                    } else {
                        result.setText(String.valueOf(num1 / num2));
                    }
                    break;
                case "Power":
                    if (num1 == 0 && num2 == 0) {
                        JOptionPane.showMessageDialog(this, "0^0 is an indeterminate form.");
                    } else if (num1 == 0 && num2 == 1) {
                        JOptionPane.showMessageDialog(this, "0^1 = 0, but check if this is what you intended.");
                        result.setText("0");
                    } else {
                        result.setText(String.valueOf((int) Math.pow(num1, num2)));
                    }
                    break;
                case "Square Root":
                    if (num1 < 0) {
                        JOptionPane.showMessageDialog(this, "Cannot calculate square root of a negative number.");
                    } else {
                        result.setText(String.valueOf(Math.sqrt(num1)));
                    }
                    break;
            }
        }
    }
}


