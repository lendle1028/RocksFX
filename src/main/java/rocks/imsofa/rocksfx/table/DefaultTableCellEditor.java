/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rocks.imsofa.rocksfx.table;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.time.ZoneId;
import java.util.Date;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author lendle
 */
public class DefaultTableCellEditor<S, T> extends TableCellEditor<S, T> {
    
    private String propertyName = null;
//    private TextField tf = null;
    private Node uiComponent = null;
    private ValueConverter<S, T> valueConverter = new DefaultValueConverter<>();
    private Class propertyClass = null;
    
    public DefaultTableCellEditor(String propertyName) {
        this.propertyName = propertyName;
    }
    
    public ValueConverter<S, T> getValueConverter() {
        return valueConverter;
    }
    
    public void setValueConverter(ValueConverter<S, T> valueConverter) {
        this.valueConverter = valueConverter;
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
    
    protected Node createUIComponent(S object) {
        if (propertyClass == null) {
            inferPropertyClass(object);
        }
        if (propertyClass.equals(String.class)) {
            return new TextField();
        } else if (propertyClass.equals(Boolean.class) || propertyClass.equals(boolean.class)) {
            return new CheckBox();
        } else if (propertyClass.equals(Integer.class) || propertyClass.equals(int.class)) {
            return new TextField();
        } else if (propertyClass.equals(Double.class) || propertyClass.equals(double.class)) {
            return new TextField();
        } else if (propertyClass.equals(Float.class) || propertyClass.equals(float.class)) {
            return new TextField();
        } else if (propertyClass.equals(Date.class)) {
            return new DatePicker();
        }
        return null;
    }
    
    @Override
    public Node getEditorComponent(TableCell<S, T> tableCell, S object, int index) {
        uiComponent = this.createUIComponent(object);
        if (uiComponent instanceof TextField) {
            final TextField tf = (TextField) uiComponent;
            tf.setText((String) tableCell.getItem());
            tf.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode().equals(KeyCode.ESCAPE)) {
                        stopEdit(StopEdit.CANCEL, null);
                    }
                }
            });
            tf.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    stopEdit(StopEdit.COMMIT, valueConverter.fromUIValue(object, tf.getText()));
                }
            });
            tf.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!tf.isFocused()) {
                        stopEdit(StopEdit.CANCEL, null);
                    }
                }
            });
        } else if (uiComponent instanceof CheckBox) {
            final CheckBox checkBox = (CheckBox) uiComponent;
            boolean currentValue = (boolean) tableCell.getItem();
            checkBox.setSelected(!currentValue);
            
            checkBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!checkBox.isFocused()) {
                        stopEdit(StopEdit.CANCEL, null);
                    }
                }
            });
            stopEdit(StopEdit.COMMIT, (T) Boolean.valueOf(checkBox.isSelected()));
        } else if (uiComponent instanceof DatePicker) {
            final DatePicker datePicker = (DatePicker) uiComponent;
            
            Date currentDate = (Date) tableCell.getItem();
            datePicker.setValue(currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            datePicker.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    stopEdit(StopEdit.COMMIT, (T)java.util.Date.from(datePicker.getValue().atStartOfDay()
                            .atZone(ZoneId.systemDefault())
                            .toInstant()));
                }
            });
            datePicker.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!datePicker.isFocused()) {
                        stopEdit(StopEdit.CANCEL, null);
                    }
                }
            });
            datePicker.show();
        }
        
        return uiComponent;
    }
    
    @Override
    public void startEdit() {
        uiComponent.requestFocus();
    }
    
    @Override
    public void setValue(TableCell<S, T> tableCell, S object, T value) {
        try {
            PropertyDescriptor[] pds = Introspector.getBeanInfo(object.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                if (pd.getName().equals(propertyName)) {
                    pd.getWriteMethod().invoke(object, value);
                }
            }
        } catch (IntrospectionException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * convert value from UI component to data model
     *
     * @param <T>
     */
    public static interface ValueConverter<S, T> {
        
        public T fromUIValue(S object, Object value);
    }
    
    public class DefaultValueConverter<S, T> implements ValueConverter<S, T> {
        
        @Override
        public T fromUIValue(S object, Object value) {
            try {
                PropertyDescriptor[] pds = Introspector.getBeanInfo(object.getClass()).getPropertyDescriptors();
                for (PropertyDescriptor pd : pds) {
                    if (pd.getName().equals(propertyName)) {
                        if (pd.getPropertyType().equals(String.class)) {
                            return (T) value.toString();
                        } else if (pd.getPropertyType().equals(Integer.class)) {
                            return (T) Integer.valueOf(value.toString());
                        } else if (pd.getPropertyType().equals(Double.class)) {
                            return (T) Double.valueOf(value.toString());
                        } else if (pd.getPropertyType().equals(Float.class)) {
                            return (T) Float.valueOf(value.toString());
                        } else if (pd.getPropertyType().equals(Boolean.class)) {
                            return (T) Boolean.valueOf(value.toString());
                        }
                    }
                }
            } catch (IntrospectionException ex) {
                ex.printStackTrace();
            }
            return null;
        }
        
    }
}
