package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class Quiz_evenementController {

    @FXML
    private RadioButton q1a1;

    @FXML
    private RadioButton q1a2;

    @FXML
    private RadioButton q1a3;

    @FXML
    private RadioButton q2a1;

    @FXML
    private RadioButton q2a2;

    @FXML
    private RadioButton q2a3;

    @FXML
    private Button submitQuiz;

    @FXML
    private Label scoreLabel;

    private int score = 0;

    public void initialize() {
        ToggleGroup q1Group = new ToggleGroup();
        q1a1.setToggleGroup(q1Group);
        q1a2.setToggleGroup(q1Group);
        q1a3.setToggleGroup(q1Group);

        ToggleGroup q2Group = new ToggleGroup();
        q2a1.setToggleGroup(q2Group);
        q2a2.setToggleGroup(q2Group);
        q2a3.setToggleGroup(q2Group);
    }

    @FXML
    public void submitQuiz() {
        if (q1a1.isSelected() && q2a2.isSelected()) {
            score = 2;
        } else if (q1a1.isSelected() || q2a2.isSelected()) {
            score = 1;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Score: " + score + "/2");
                    alert.show();
        scoreLabel.setText("Score: " + score + "/2");
    }
}