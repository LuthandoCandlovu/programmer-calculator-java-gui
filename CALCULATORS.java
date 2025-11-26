/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.calculators;

/**
 *
 * @author 202126369
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CALCULATORS extends JFrame implements ActionListener {

    private JComboBox<String> fromBase, toBase;
    private JTextField inputField, resultField;
    private JButton convertButton;

    public CALCULATORS() {
        super("CALCULATORS - Number System Converter");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        String[] bases = {"Binary", "Octal", "Decimal", "Hexadecimal"};

        add(new JLabel("From:"));
        fromBase = new JComboBox<>(bases);
        add(fromBase);

        add(new JLabel("To:"));
        toBase = new JComboBox<>(bases);
        add(toBase);

        add(new JLabel("Input:"));
        inputField = new JTextField();
        add(inputField);

        add(new JLabel("Result:"));
        resultField = new JTextField();
        resultField.setEditable(false);
        add(resultField);

        convertButton = new JButton("Convert");
        convertButton.addActionListener(this);
        add(convertButton);

        JButton arithmeticButton = new JButton("Open Arithmetic");
        arithmeticButton.addActionListener(e -> openArithmeticWindow());
        add(arithmeticButton);
    }

    // Handle the Convert Button
    @Override
    public void actionPerformed(ActionEvent e) {
        String input = inputField.getText().trim();
        String from = (String) fromBase.getSelectedItem();
        String to = (String) toBase.getSelectedItem();

        try {
            int fromBaseVal = switch (from) {
                case "Binary" -> 2;
                case "Octal" -> 8;
                case "Decimal" -> 10;
                case "Hexadecimal" -> 16;
                default -> throw new IllegalStateException("Invalid base");
            };

            int toBaseVal = switch (to) {
                case "Binary" -> 2;
                case "Octal" -> 8;
                case "Decimal" -> 10;
                case "Hexadecimal" -> 16;
                default -> throw new IllegalStateException("Invalid base");
            };

            double decimalValue = toDecimal(input, fromBaseVal);
            String result = fromDecimal(decimalValue, toBaseVal, 8); // precision 8

            resultField.setText(result);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input or unsupported format!");
        }
    }

    // Converts base-X (with fractional part) to Decimal
    private double toDecimal(String number, int base) {
        String[] parts = number.split("\\.");
        double result = 0;

        // Whole part
        result += Integer.parseInt(parts[0], base);

        // Fraction part
        if (parts.length > 1) {
            String frac = parts[1];
            for (int i = 0; i < frac.length(); i++) {
                int digit = Character.digit(frac.charAt(i), base);
                result += digit / Math.pow(base, i + 1);
            }
        }

        return result;
    }

    // Converts Decimal (with fraction) to base-X
    private String fromDecimal(double number, int base, int precision) {
        int wholePart = (int) number;
        double fractionPart = number - wholePart;

        String wholeStr = Integer.toString(wholePart, base).toUpperCase();

        StringBuilder fracStr = new StringBuilder();
        while (precision-- > 0 && fractionPart != 0) {
            fractionPart *= base;
            int digit = (int) fractionPart;
            fracStr.append(Character.forDigit(digit, base));
            fractionPart -= digit;
        }

        return fracStr.length() > 0 ? wholeStr + "." + fracStr : wholeStr;
    }

    // Opens a new window for Arithmetic Operations
    private void openArithmeticWindow() {
        JFrame arithmeticFrame = new JFrame("Arithmetic Operations");
        arithmeticFrame.setSize(400, 250);
        arithmeticFrame.setLayout(new GridLayout(5, 2));

        JTextField num1Field = new JTextField();
        JTextField num2Field = new JTextField();
        JComboBox<String> baseBox = new JComboBox<>(new String[]{"Binary", "Octal", "Decimal", "Hexadecimal"});
        JComboBox<String> operationBox = new JComboBox<>(new String[]{"+", "-", "*", "/"});
        JTextField resultField = new JTextField();
        resultField.setEditable(false);
        JButton calcBtn = new JButton("Calculate");

        calcBtn.addActionListener(e -> {
            try {
                String base = (String) baseBox.getSelectedItem();
                int b = switch (base) {
                    case "Binary" -> 2;
                    case "Octal" -> 8;
                    case "Decimal" -> 10;
                    case "Hexadecimal" -> 16;
                    default -> 10;
                };

                int num1 = Integer.parseInt(num1Field.getText(), b);
                int num2 = Integer.parseInt(num2Field.getText(), b);
                int result = switch ((String) operationBox.getSelectedItem()) {
                    case "+" -> num1 + num2;
                    case "-" -> num1 - num2;
                    case "*" -> num1 * num2;
                    case "/" -> num2 != 0 ? num1 / num2 : 0;
                    default -> 0;
                };

                resultField.setText(Integer.toString(result, b).toUpperCase());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(arithmeticFrame, "Error: " + ex.getMessage());
            }
        });

        arithmeticFrame.add(new JLabel("First Number:"));
        arithmeticFrame.add(num1Field);
        arithmeticFrame.add(new JLabel("Second Number:"));
        arithmeticFrame.add(num2Field);
        arithmeticFrame.add(new JLabel("Base:"));
        arithmeticFrame.add(baseBox);
        arithmeticFrame.add(new JLabel("Operation:"));
        arithmeticFrame.add(operationBox);
        arithmeticFrame.add(calcBtn);
        arithmeticFrame.add(resultField);

        arithmeticFrame.setVisible(true);
    }

    // Run the program
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CALCULATORS().setVisible(true));
    }
}
