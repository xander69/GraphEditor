package ru.xander.etl.graph.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.etl.graph.service.GraphFile;
import ru.xander.etl.graph.util.AcceleratorUtils;

/**
 * @author Alexander Shakhov
 */
@Slf4j
@Component
@FxmlView("/views/main.fxml")
public class MainController {

    @FXML
    public GraphListController scenarioListController;
    @FXML
    public Button buttonSettings;
    @FXML
    public Button buttonShortcuts;
    @FXML
    public TabPane tabsScenarios;

    private final FxWeaver fxWeaver;

    @Autowired
    public MainController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @FXML
    private void initialize() {
        scenarioListController.setOnOpenScenario(this::openScenario);
        buttonSettings.setOnAction(event -> openSettings());
        buttonShortcuts.setOnAction(event -> openShortcuts());
    }

    public void startup() {
        scenarioListController.refresh();
        AcceleratorUtils.setNewScenario(scenarioListController.buttonNew);
        AcceleratorUtils.setRefreshScenarios(scenarioListController.buttonRefresh);
    }

    private void openSettings() {
        FxControllerAndView<SettingsController, Node> controllerAndView = fxWeaver.load(SettingsController.class);
        controllerAndView.getView().ifPresent(parent -> {
            Stage stage = new Stage();

            Scene scene = new Scene((Parent) parent);
            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.ESCAPE), () -> stage.close());

            stage.setTitle("Settings");
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(buttonSettings.getScene().getWindow());
            stage.show();
        });
    }

    private void openShortcuts() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Shortcuts");
        alert.setHeaderText("");
        alert.setContentText(AcceleratorUtils.getHelp());
        alert.showAndWait();
    }

    private void openScenario(GraphFile graphFile) {
        log.info("Open scenario {}...", graphFile.getName());
    }
}
