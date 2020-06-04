package ru.xander.etl.graph.app;

import javafx.stage.Stage;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Alexander Shakhov
 */
public class StageReadyEvent extends ApplicationEvent {

    @Getter
    private final Stage stage;

    public StageReadyEvent(Stage stage) {
        super(stage);
        this.stage = stage;
    }
}
