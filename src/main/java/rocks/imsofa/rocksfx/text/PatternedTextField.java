/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rocks.imsofa.rocksfx.text;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

/**
 *
 * @author lendle
 */
public class PatternedTextField extends TextField {
    public PatternedTextField(Pattern pattern) {
        this.initFilter(pattern);
    }

    public PatternedTextField(Pattern pattern, String string) {
        super(string);
        this.initFilter(pattern);
    }
    
    protected void initFilter(Pattern pattern) {
        Pattern validEditingState = pattern;

        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c;
            } else {
                return null;
            }
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        this.setTextFormatter(textFormatter);
    }
}
