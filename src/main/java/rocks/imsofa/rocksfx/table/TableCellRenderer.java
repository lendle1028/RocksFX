/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rocks.imsofa.rocksfx.table;

import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

/**
 * 
 * @author lendle
 * @param <S> model object type
 * @param <T> value type
 */
public interface TableCellRenderer<S, T> {
    public T getValue(TableColumn.CellDataFeatures<S, T> param, S object);
    public Node getRendererComponent(TableCell<S, T> tableCell, S object, int index, boolean empty);
}
