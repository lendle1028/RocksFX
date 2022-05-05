/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rocks.imsofa.rocksfx.button;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author lendle
 */
public final class SwitchButton extends Pane {

    private Circle leftCircle = null, rightCircle = null;
    private Rectangle rectangle = null;
    private Color mainColor = null, subColor = null;
    private BooleanProperty checkedProperty = new SimpleBooleanProperty(false);
    private Text textUI = null;

    public SwitchButton(boolean initialState) {
        this.setPrefSize(80, 30);
        checkedProperty.set(initialState);
        double width = 80;
        double height = 30;
        mainColor = Color.web("#4dffb8");
        subColor = Color.WHITE;
        Pane toggleButton = this;
        rectangle = new Rectangle(height / 2, 0, width - height, height);
        rectangle.xProperty().bind(Bindings.divide(toggleButton.heightProperty(), 2));
        rectangle.widthProperty().bind(Bindings.subtract(toggleButton.widthProperty(), toggleButton.heightProperty()));
        rectangle.heightProperty().bind(toggleButton.heightProperty());
        rectangle.setFill(mainColor);
        leftCircle = new Circle(height / 2, height / 2, height / 2);
        leftCircle.centerXProperty().bind(Bindings.divide(toggleButton.heightProperty(), 2));
        leftCircle.centerYProperty().bind(Bindings.divide(toggleButton.heightProperty(), 2));
        leftCircle.radiusProperty().bind(Bindings.divide(toggleButton.heightProperty(), 2));
        rightCircle = new Circle(width - height / 2, height / 2, height / 2);
        rightCircle.centerXProperty().bind(Bindings.subtract(toggleButton.widthProperty(), Bindings.divide(toggleButton.heightProperty(), 2)));
        rightCircle.centerYProperty().bind(Bindings.divide(toggleButton.heightProperty(), 2));
        rightCircle.radiusProperty().bind(Bindings.divide(toggleButton.heightProperty(), 2));
        leftCircle.setFill(mainColor);
        leftCircle.setStroke(mainColor);
        rightCircle.setFill(subColor);
        rightCircle.setStroke(mainColor);
        textUI = new Text(height / 2, height / 2 + 4, "ON");
        textUI.setFill(subColor);
        toggleButton.getChildren().addAll(rectangle, leftCircle, rightCircle, textUI);
        toggleButton.setOnMouseClicked((MouseEvent event) -> {
            checkedProperty.set(!checkedProperty.get());
            if (checkedProperty.get()) {
                showCheckedUI();
            } else {
                showUncheckedUI();
            }
        });
        if (checkedProperty.get()) {
            showCheckedUI();
        } else {
            showUncheckedUI();
        }
    }

    protected void showUncheckedUI() {
        leftCircle.setFill(mainColor);
        leftCircle.setStroke(mainColor);
        rightCircle.setFill(subColor);
        rightCircle.setStroke(mainColor);
        textUI.setText("ON");
        textUI.setX(this.getHeight() / 2);
        textUI.setY(this.getHeight() / 2 + 4);
    }

    protected final void showCheckedUI() {
        rightCircle.setFill(mainColor);
        rightCircle.setStroke(mainColor);
        leftCircle.setFill(subColor);
        leftCircle.setStroke(mainColor);
        textUI.setText("OFF");
        textUI.setX(this.getHeight());
        textUI.setY(this.getHeight() / 2 + 4);
    }

    public boolean isChecked() {
        return checkedProperty.get();
    }

    public BooleanProperty checkedProperty() {
        return checkedProperty;
    }
}
