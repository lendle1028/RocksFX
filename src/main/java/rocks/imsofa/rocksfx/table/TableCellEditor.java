/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rocks.imsofa.rocksfx.table;

import java.util.concurrent.CancellationException;
import javafx.scene.Node;
import javafx.scene.control.TableCell;

/**
 * 
 * @author lendle
 * @param <S> model object type
 * @param <T> value type
 */
public abstract class TableCellEditor<S, T> {
    private OnStopEdit onStopEdit=null;
    public abstract Node getEditorComponent(TableCell<S,T> tableCell, S object, int index);
    public abstract void startEdit();
    protected void stopEdit(StopEdit mode, T newValue){
        if(this.onStopEdit!=null){
            this.onStopEdit.editStopped(mode, newValue);
        }
    }

    public void setOnStopEdit(OnStopEdit onStopEdit) {
        this.onStopEdit = onStopEdit;
    }
   
    public abstract void setValue(TableCell<S,T> tableCell, S object, T value);
    public static interface OnStopEdit<T>{
        public void editStopped(StopEdit mode, T newValue);
    }
    
    public static enum StopEdit{
        CANCEL,
        COMMIT
    }

}
