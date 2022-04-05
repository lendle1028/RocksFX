/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rocks.imsofa.rocksfx.table;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 *
 * @author lendle
 */
public class TableColumnEx<S, T> extends TableColumn<S, T> {
    private TableCellRenderer<S, T> tableCellRenderer = null;
    private TableCellEditor<S, T> tableCellEditor = null;
    public TableColumnEx() {
        init();
    }

    public TableColumnEx(String string) {
        super(string);
        init();
    }
    
    protected void init(){
        this.setCellFactory(new DefaultCellFactory());
        this.setCellValueFactory(new DefaultCellValueFactory());
    }

    public TableCellRenderer<S, T> getTableCellRenderer() {
        return tableCellRenderer;
    }

    public void setTableCellRenderer(TableCellRenderer<S, T> tableCellRenderer) {
        this.tableCellRenderer = tableCellRenderer;
    }

    public TableCellEditor<S, T> getTableCellEditor() {
        return tableCellEditor;
    }

    public void setTableCellEditor(TableCellEditor<S, T> tableCellEditor) {
        this.tableCellEditor = tableCellEditor;
    }
    
    
    class DefaultCellValueFactory implements Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> {

        @Override
        public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> param) {
            T value = tableCellRenderer.getValue(param, param.getValue());
            return new SimpleObjectProperty<>(value);
        }

    }

    class DefaultCellFactory implements Callback<TableColumn<S, T>, TableCell<S, T>> {

        @Override
        public TableCell<S, T> call(TableColumn<S, T> param) {
            return new TableCell<>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                    if (empty || item == null) {
                        this.setGraphic(null);
                    } else {
                        S object=param.getTableView().getItems().get(this.getIndex());
                        Node node = tableCellRenderer.getRendererComponent(this, object, this.getIndex(), empty);
                        this.setGraphic(node);
                    }
                }

                @Override
                public void startEdit() {
                    super.startEdit(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                    final S object=param.getTableView().getItems().get(this.getIndex());
                    
                    TableCell<S, T> self = this;
                    tableCellEditor.setOnStopEdit(new TableCellEditor.OnStopEdit<T>() {
                        @Override
                        public void editStopped(TableCellEditor.StopEdit mode, T value) {
                            if (mode.equals(TableCellEditor.StopEdit.CANCEL)) {
                                cancelEdit();
                                Node node=tableCellRenderer.getRendererComponent(self, object, self.getIndex(), false);
                                setGraphic(node);
                            } else {
                                int index = getIndex();
                                S object = self.getTableView().getItems().get(index);
                                tableCellEditor.setValue(self, object, value);
                                commitEdit(value);
                                Node node=tableCellRenderer.getRendererComponent(self, object, self.getIndex(), false);
                                setGraphic(node);
                                System.out.println(object);
                            }
                        }

                    });
                    Node node = tableCellEditor.getEditorComponent(this, object, this.getIndex());
                    this.setGraphic(node);
                    tableCellEditor.startEdit();
                }
            };
        
        }

     
    }
}
