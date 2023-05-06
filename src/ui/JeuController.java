package ui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JeuController implements Initializable {

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button6;

    @FXML
    private Button button7;

    @FXML
    private Button button8;

    @FXML
    private Button button9;

    @FXML
    private Text winnerText;

    private int playerTurn = 0;

    ArrayList<Button> buttons;
    @FXML
    private Button retour;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons = new ArrayList<>(Arrays.asList(button1,button2,button3,button4,button5,button6,button7,button8,button9));

        buttons.forEach(button ->{
            setupButton(button);
            button.setFocusTraversable(false);
        });
    }

    @FXML
    void restartGame(ActionEvent event) {
        buttons.forEach(this::resetButton);
        winnerText.setText("Tic-Tac-Toe");
    }

    public void resetButton(Button button){
        button.setDisable(false);
        button.setText("");
    }

    private void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            setPlayerSymbol(button);
            button.setDisable(true);
            checkIfGameIsOver();
        });
    }

    public void setPlayerSymbol(Button button){
        if(playerTurn % 2 == 0){
            button.setText("X");
            playerTurn = 1;
        } else{
            button.setText("O");
            playerTurn = 0;
        }
    }

    public void checkIfGameIsOver(){
        for (int a = 0; a < 8; a++) {
            if((button1.getText() + button2.getText() + button3.getText()).equals("XXX")){
                winnerText.setText("X won!");
                a=8;
            }else if((button4.getText() + button5.getText() + button6.getText()).equals("XXX")){
                winnerText.setText("X won!");
                a=8;
            }else if((button7.getText() + button8.getText() + button9.getText()).equals("XXX")){
                winnerText.setText("X won!");
                a=8;
            }else if((button1.getText() + button5.getText() + button9.getText()).equals("XXX")){
                winnerText.setText("X won!");
                a=8;
            }else if((button3.getText() + button5.getText() + button7.getText()).equals("XXX")){
                winnerText.setText("X won!");
                a=8;
            }else if((button1.getText() + button4.getText() + button7.getText()).equals("XXX")){
                winnerText.setText("X won!");
                a=8;
            }else if((button2.getText() + button5.getText() + button8.getText()).equals("XXX")){
                winnerText.setText("X won!");
                a=8;
            }else if((button3.getText() + button6.getText() + button9.getText()).equals("XXX")){
                winnerText.setText("X won!");
                a=8;
            }else if((button1.getText() + button2.getText() + button3.getText()).equals("OOO")){
                winnerText.setText("O won!");
                a=8;
            }else if((button4.getText() + button5.getText() + button6.getText()).equals("OOO")){
                winnerText.setText("O won!");
                a=8;
            }else if((button7.getText() + button8.getText() + button9.getText()).equals("OOO")){
                winnerText.setText("O won!");
                a=8;
            }else if((button1.getText() + button5.getText() + button9.getText()).equals("OOO")){
                winnerText.setText("O won!");
                a=8;
            }else if((button3.getText() + button5.getText() + button7.getText()).equals("OOO")){
                winnerText.setText("O won!");
                a=8;
            }else if((button1.getText() + button4.getText() + button7.getText()).equals("OOO")){
                winnerText.setText("O won!");
                a=8;
            }else if((button2.getText() + button5.getText() + button8.getText()).equals("OOO")){
                winnerText.setText("O won!");
                a=8;
            }else if((button3.getText() + button6.getText() + button9.getText()).equals("OOO")){
                winnerText.setText("O won!");
                a=8;
            }
        }
    }

   

    @FXML
    private void retour(ActionEvent event) {
         retour.setOnAction(retour -> {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Evenement.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) retour.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        });
    }
    
}