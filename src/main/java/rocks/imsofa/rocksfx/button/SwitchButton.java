/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rocks.imsofa.rocksfx.button;

import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author lendle
 */
public class SwitchButton extends Pane{
    private boolean toggleChecked = true;
    public SwitchButton(){
        this.setPrefSize(80, 30);
        this.setStyle("-fx-border-color: blue");
        double width=80;
        double height=30;
        Pane toggleButton=this;
        Rectangle rectangle = new Rectangle(height/2, 0, width-height, height);
        rectangle.xProperty().bind(Bindings.divide(toggleButton.heightProperty(), 2));
        rectangle.widthProperty().bind(Bindings.subtract(toggleButton.widthProperty(), toggleButton.heightProperty()));
        rectangle.heightProperty().bind(toggleButton.heightProperty());
        rectangle.setFill(Color.GREEN);
        Circle circle1 = new Circle(height/2, height/2, height/2);
        circle1.centerXProperty().bind(Bindings.divide(toggleButton.heightProperty(), 2));
        circle1.centerYProperty().bind(Bindings.divide(toggleButton.heightProperty(), 2));
        circle1.radiusProperty().bind(Bindings.divide(toggleButton.heightProperty(), 2));
        Circle circle2 = new Circle(width-height/2, height/2, height/2);
        circle2.centerXProperty().bind(Bindings.subtract(toggleButton.widthProperty(),Bindings.divide(toggleButton.heightProperty(), 2)));
        circle2.centerYProperty().bind(Bindings.divide(toggleButton.heightProperty(), 2));
        circle2.radiusProperty().bind(Bindings.divide(toggleButton.heightProperty(), 2));
        circle1.setFill(Color.GREEN);
        circle1.setStroke(Color.GREEN);
        circle2.setFill(Color.WHITE);
        circle2.setStroke(Color.GREEN);
        Text text = new Text(height/2, height/2+4, "ON");
        text.setFill(Color.WHITE);
        toggleButton.getChildren().addAll(rectangle, circle1, circle2, text);
        toggleButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                toggleChecked = !toggleChecked;
                if (toggleChecked) {
                    circle1.setFill(Color.GREEN);
                    circle1.setStroke(Color.GREEN);
                    circle2.setFill(Color.WHITE);
                    circle2.setStroke(Color.GREEN);
                    text.setText("ON");
                    text.setX(height/2);
                    text.setY(height/2+4);
                }else{
                    circle2.setFill(Color.GREEN);
                    circle2.setStroke(Color.GREEN);
                    circle1.setFill(Color.WHITE);
                    circle1.setStroke(Color.GREEN);
                    text.setText("OFF");
                    text.setX(height);
                    text.setY(height/2+4);
                }
            }

        });
    }
}
