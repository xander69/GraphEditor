package ru.xander.etl.graph.service;

import lombok.Getter;
import lombok.Setter;
import ru.xander.etl.graph.graph.etl.EtlScenario;

import java.io.File;

/**
 * @author Alexander Shakhov
 */
@Setter
@Getter
public class GraphFile {

    private File file;
    private EtlScenario scenario;
    private State state;

    public String getName() {
        return file.getName();
    }

    public enum State {
        NEW,
        MODIFIED,
        SAVED
    }
}
