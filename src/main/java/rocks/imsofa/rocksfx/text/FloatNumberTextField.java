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
public class FloatNumberTextField extends PatternedTextField {
    private static Pattern pattern=Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

    public FloatNumberTextField() {
        super(pattern);
    }

    public FloatNumberTextField(String string) {
        super(pattern, string);
    }
}
