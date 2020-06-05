package ru.xander.etl.graph.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
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
    public Button buttonShortcuts;
    @FXML
    public TabPane tabsScenarios;
    @FXML
    public GraphListController scenarioListController;

    @FXML
    private void initialize() {
        scenarioListController.setOnOpenScenario(this::openScenario);
        buttonShortcuts.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Shortcuts");
            alert.setHeaderText("");
            alert.setContentText(AcceleratorUtils.getHelp());
            alert.showAndWait();
        });
    }

    public void startup() {
        scenarioListController.refresh();
        AcceleratorUtils.setNewScenario(scenarioListController.buttonNew);
        AcceleratorUtils.setRefreshScenarios(scenarioListController.buttonRefresh);
    }

    private void openScenario(GraphFile graphFile) {
        log.info("Open scenario {}...", graphFile.getName());
    }
}
