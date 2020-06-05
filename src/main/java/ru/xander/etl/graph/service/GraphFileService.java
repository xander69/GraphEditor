package ru.xander.etl.graph.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.xander.etl.graph.app.Preferences;
import ru.xander.etl.graph.graph.etl.EtlScenario;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author Alexander Shakhov
 */
@Getter
@Slf4j
@Service
public class GraphFileService {

    private static final String GRF_EXTENSION = ".grf";

    private final ObservableList<GraphFile> graphFiles = FXCollections.observableArrayList();
    private final File graphPath;

    public GraphFileService(@Value("${editor.graph.path}") String graphPath) {
        log.info("Initialize graph file service");
        this.graphPath = new File(graphPath);
    }

    public void add(String scenarioName) {
        log.info("Add scenario {}", scenarioName);

        File file = new File(graphPath, scenarioName + GRF_EXTENSION);
        EtlScenario scenario = createEmptyScenario(scenarioName);

        GraphFile graphFile = new GraphFile();
        graphFile.setFile(file);
        graphFile.setScenario(scenario);
        graphFile.setState(GraphFile.State.NEW);

        this.graphFiles.add(graphFile);
    }

    public void save(GraphFile graphFile) {
        log.info("Save scenario {}", graphFile.getName());
        //TODO: persist scenario to file
    }

    public void delete(GraphFile graphFile) {
        log.info("Delete scenario {}", graphFile.getName());
        this.graphFiles.remove(graphFile);
        //TODO: remove scenario file
    }

    public void load() {
        log.info("Load scenario list from {} path", graphPath.getAbsolutePath());
        this.graphFiles.clear();
        File[] files = this.graphPath.listFiles(pathname -> pathname.getName().endsWith(GRF_EXTENSION));
        if (files != null) {
            for (File file : files) {
                GraphFile graphFile = new GraphFile();
                graphFile.setFile(file);
                graphFile.setState(GraphFile.State.SAVED);
                graphFiles.add(graphFile);
            }
        }
    }

    private EtlScenario createEmptyScenario(String scenarioName) {
        return EtlScenario.builder()
                .version(1)
                .name(scenarioName)
                .author(Preferences.getPreferences().getAuthor())
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .startupParams(new ArrayList<>())
                .contextParams(new ArrayList<>())
                .internalParams(new ArrayList<>())
                .stages(new ArrayList<>())
                .metadataList(new ArrayList<>())
                .build();
    }
}
