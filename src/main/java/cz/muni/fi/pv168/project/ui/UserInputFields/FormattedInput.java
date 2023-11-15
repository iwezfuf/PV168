package cz.muni.fi.pv168.project.ui.UserInputFields;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;


public class FormattedInput {
    static public JFormattedTextField createIntTextField(int minValue, int maxValue) {
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setMinimum(minValue);
        formatter.setMaximum(maxValue);
        formatter.setValueClass(Integer.class);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        return new JFormattedTextField(formatter);
    }

    static public JFormattedTextField createFloatTextField(float minValue, float maxValue) {
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setMinimum(minValue);
        formatter.setMaximum(maxValue);
        formatter.setValueClass(Float.class);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        return new JFormattedTextField(formatter);
    }

    static public int getInt(String text) {
        return Integer.parseInt(text.replaceAll("[\\s\\u00A0]+", ""));
    }
}