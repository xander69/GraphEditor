package ru.xander.etl.graph.util;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * @author Alexander Shakhov
 */
public final class AcceleratorUtils {

    public static String getHelp() {
        return "Ctrl+N - New scenario\n" +
                "Ctrl+R - Refresh scenario list\n";
    }

    public static void setNewScenario(Button button) {
        KeyCodeCombination combination = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
        button.getScene().getAccelerators().put(combination, button::fire);
    }

    public static void setRefreshScenarios(Button button) {
        KeyCodeCombination combination = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
        button.getScene().getAccelerators().put(combination, button::fire);
    }

}
