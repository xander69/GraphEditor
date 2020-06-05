package ru.xander.etl.graph.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.xander.etl.graph.app.Preferences;

/**
 * @author Alexander Shakhov
 */
@Component
@FxmlView("/views/settings.fxml")
public class SettingsController {

    @FXML
    public TextField textAuthor;
    @FXML
    public Button buttonSave;
    @FXML
    public Button buttonCancel;

    @FXML
    private void initialize() {
        Preferences preferences = Preferences.getPreferences();
        textAuthor.setText(preferences.getAuthor());
        buttonSave.setOnAction(event -> save());
        buttonCancel.setOnAction(event -> cancel());
    }

    private void save() {
        Preferences preferences = Preferences.getPreferences();
        preferences.setAuthor(textAuthor.getText());
        Preferences.save();
        buttonSave.getScene().getWindow().hide();
    }

    private void cancel() {
        buttonSave.getScene().getWindow().hide();
    }
}
