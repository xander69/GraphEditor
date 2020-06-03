package ru.xander.etl.graph.app;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.xander.etl.graph.controller.MainController;

@Component
public class PrimaryStageInitializer implements ApplicationListener<StageReadyEvent> {

    private static final double MIN_WIDTH = 800.0;
    private static final double MIN_HEIGHT = 600.0;

    private final FxWeaver fxWeaver;

    @Autowired
    public PrimaryStageInitializer(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        FxControllerAndView<MainController, Node> controllerAndView = fxWeaver.load(MainController.class);
        Stage stage = stageReadyEvent.getStage();
        controllerAndView.getView().ifPresent(parent -> {
            Scene scene = new Scene((Parent) parent, MIN_WIDTH, MIN_HEIGHT);
            stage.setScene(scene);
        });
        stage.setTitle("Graph Editor v1.0");
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
        stage.show();
    }
}
