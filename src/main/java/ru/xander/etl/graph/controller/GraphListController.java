package ru.xander.etl.graph.controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.xander.etl.graph.service.GraphFile;
import ru.xander.etl.graph.service.GraphFileService;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author Alexander Shakhov
 */
@Slf4j
@Component
@FxmlView("/views/scenarioList.fxml")
public class GraphListController {

    @FXML
    public ListView<GraphFile> listScenarios;
    @FXML
    public Button buttonNew;
    @FXML
    public Button buttonRefresh;
    @FXML
    public TextField textFilter;

    private final GraphFileService graphFileService;

    @Autowired
    public GraphListController(GraphFileService graphFileService) {
        this.graphFileService = graphFileService;
    }

    @FXML
    private void initialize() {
        configureListView();
        buttonNew.setOnAction(event -> newScenario());
        buttonRefresh.setOnAction(event -> refresh());
    }

    public void refresh() {
        graphFileService.load();
    }

    public void newScenario() {
        TextInputDialog dialog = newScenarioDialog();
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(graphFileService::add);
    }


    public void setOnOpenScenario(Consumer<GraphFile> openScenarioConsumer) {
        listScenarios.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2) {
                GraphFile graphFile = listScenarios.getSelectionModel().getSelectedItem();
                if (graphFile != null) {
                    openScenarioConsumer.accept(graphFile);
                }
            }
        });
    }

    private void configureListView() {
        listScenarios.setEditable(false);
        listScenarios.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listScenarios.setCellFactory(scenarioFileCellFactory());

        ObservableList<GraphFile> graphFiles = graphFileService.getGraphFiles();
        FilteredList<GraphFile> filteredGraphFiles = new FilteredList<>(graphFiles, f -> true);

        SortedList<GraphFile> sortedGraphFiles = new SortedList<>(filteredGraphFiles);
        sortedGraphFiles.setComparator(Comparator.comparing(f -> f.getName().toUpperCase()));

        listScenarios.setItems(sortedGraphFiles);

        textFilter.textProperty().addListener(observable -> {
            String filterText = textFilter.getText();
            if (StringUtils.isEmpty(filterText)) {
                filteredGraphFiles.setPredicate(f -> true);
            } else {
                filteredGraphFiles.setPredicate(f -> f.getName().toUpperCase().contains(filterText.toUpperCase()));
            }
        });
    }

    private Callback<ListView<GraphFile>, ListCell<GraphFile>> scenarioFileCellFactory() {
        return param -> new ListCell<GraphFile>() {
            @Override
            protected void updateItem(GraphFile item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                if (empty || (item == null)) {
                    setGraphic(null);
                } else {
                    setGraphic(drawScenarioFileCell(item));
                }
            }

            private Node drawScenarioFileCell(GraphFile graphFile) {
                AnchorPane pane = new AnchorPane();
                Label labelName = new Label();
                labelName.setText(graphFile.getName());

                Hyperlink labelDelete = new Hyperlink();
                labelDelete.setText("x");
                labelDelete.setUnderline(false);
                labelDelete.setOnMouseReleased(event -> {
                    Alert alert = deleteScenarioDialog(graphFile.getName());
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && (result.get() == ButtonType.OK)) {
                        graphFileService.delete(graphFile);
                    }
                    labelDelete.setVisited(false);
                });

                pane.getChildren().addAll(labelName, labelDelete);
                AnchorPane.setLeftAnchor(labelName, 5.0);
                AnchorPane.setTopAnchor(labelName, 5.0);
                AnchorPane.setRightAnchor(labelDelete, 5.0);
                return pane;
            }
        };
    }

    private static TextInputDialog _newScenarioDialog;

    private static TextInputDialog newScenarioDialog() {
        if (_newScenarioDialog == null) {
            _newScenarioDialog = new TextInputDialog("SCENARIO_NAME_1");
            _newScenarioDialog.setTitle("New Scenario");
            _newScenarioDialog.setHeaderText("Enter scenario name");
            _newScenarioDialog.setContentText("Name:");
        }
        return _newScenarioDialog;
    }

    private static Alert _deleteScenarioDialog;

    private static Alert deleteScenarioDialog(String scenarioName) {
        if (_deleteScenarioDialog == null) {
            _deleteScenarioDialog = new Alert(Alert.AlertType.CONFIRMATION);
            _deleteScenarioDialog.setTitle("Delete scenario");
        }
        _deleteScenarioDialog.setHeaderText("Delete scenario " + scenarioName + "?");
        return _deleteScenarioDialog;
    }
}
