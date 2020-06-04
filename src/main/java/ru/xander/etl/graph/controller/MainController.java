package ru.xander.etl.graph.controller;

import javafx.fxml.FXML;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 * @author Alexander Shakhov
 */
@Slf4j
@Component
@FxmlView("/views/main.fxml")
public class MainController {
    @FXML
    private void initialize() {
        log.info("Graph editor started.");
    }
}
