/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rocks.imsofa.rocksfx.table;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author lendle
 */
public class DefaultTableCellRenderer<S, T> implements TableCellRenderer<S, T> {

    private String propertyName = null;
    private Class<?> propertyClass;

    public DefaultTableCellRenderer(String propertyName) {
        this.propertyName = propertyName;
    }

    private Class inferPropertyClass(S object) {
        try {
            PropertyDescriptor[] pds = Introspector.getBeanInfo(object.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                if (pd.getName().equals(propertyName)) {
                    propertyClass = pd.getPropertyType();
                    return propertyClass;
                }
            }
        } catch (IntrospectionException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private T getValue(S object) {
        try {
            PropertyDescriptor[] pds = Introspector.getBeanInfo(object.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                if (pd.getName().equals(propertyName)) {
                    return (T) pd.getReadMethod().invoke(object);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public T getValue(TableColumn.CellDataFeatures<S, T> param, S object) {
        try {
            return getValue(object);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    protected Node createUIComponent(TableCell<S, T> tableCell, S object, int index) {
        if (propertyClass == null) {
            inferPropertyClass(object);
        }
        Object value=this.getValue(object);
        if (propertyClass.equals(Boolean.class) || propertyClass.equals(boolean.class)) {
            CheckBox checkbox = new CheckBox();
            checkbox.setSelected((Boolean)value);
//            checkbox.setDisable(true);
            checkbox.setStyle("-fx-opacity: 1");
            checkbox.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                    tableCell.getTableView().edit(index, tableCell.getTableColumn());
                }
            });
            return checkbox;

        } else {
            Label label= new Label();
            if(value instanceof Date){
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                label.setText(simpleDateFormat.format((Date)value));
                label.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        tableCell.getTableView().edit(index, tableCell.getTableColumn());
                    }
                });
            }else{
                label.setText(""+value);
            }
            return label;
        }
    }

    @Override
    public Node getRendererComponent(TableCell<S, T> tableCell, S object, int index, boolean empty) {
        Node uiComponent = createUIComponent(tableCell, object, index);
        return uiComponent;
    }

}
