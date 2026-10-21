package pe.edu.upeu.sysventas.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.util.SortedSet;

public class AutoCompleteTextField<T>{

    private TextField autoCompleteTextField;
    private final SortedSet<T> entries;
    private final ContextMenu entryMenu = new ContextMenu();
    VBox vbox = new VBox();
    private T lastSelectedObject;

    public AutoCompleteTextField(SortedSet<T> entries, TextField autTF) {
        this.entries = entries;
        this.autoCompleteTextField=autTF;
        //this.autoCompleteTextField.setOnKeyReleased(this::handleKeyReleased);
        this.autoCompleteTextField.setOnKeyTyped(this::handleKeyReleased);
    }

    public void handleKeyReleased(KeyEvent event) {
        acccion();
    }

    // Método para manejar clics del mouse


    public void acccion(){
        ObservableList<MenuItem> menuItems = FXCollections.observableArrayList();
        String input = this.autoCompleteTextField.getText().toLowerCase();

        // Filtro de elementos que coinciden con la entrada
        entries.stream()
                .filter(e -> e.toString().toLowerCase().contains(input))
                .forEach(entry -> {
                    MenuItem item = new MenuItem(entry.toString());
                    //vbox.getChildren().add(new Button(item.getText()));
                    item.setOnAction(e -> {
                        this.autoCompleteTextField.setText(entry.toString());
                        lastSelectedObject = entry;
                        entryMenu.hide();
                    });

                    menuItems.add(item);
                    entryMenu.hide();
                });
        /*ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setPrefHeight(150); // Altura del área de scroll
        scrollPane.setFitToWidth(true);
        CustomMenuItem scrollableItem = new CustomMenuItem(scrollPane, false);
        entryMenu.getItems().setAll(scrollableItem);*/
        entryMenu.getItems().setAll(menuItems);
        if (!menuItems.isEmpty()) {
            entryMenu.show(this.autoCompleteTextField, Side.BOTTOM, 0, 0);
        } else {
            entryMenu.hide();
        }
    }

    public ContextMenu getEntryMenu() {
        return entryMenu;
    }

    public T getLastSelectedObject() {
        return lastSelectedObject;
    }
}
