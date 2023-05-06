package ui;

import entite.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class itemController {

    private Service service;
    private MyListener listener;
    
    
    @FXML
    private Label ServiceNameLabel;
    @FXML
    private Label ServiceTypeLabel;
    @FXML
    private ImageView serviceImg;

    
public interface MyListener {
    void onClickListener(Service service);
}
        @FXML
    private void handleClick(MouseEvent event) {
        listener.onClickListener(service);
            System.out.println("Mz");
    }

    public void setData(Service service, MyListener myListener) {
    this.service = service ; 
    this.listener = myListener ; 
    System.out.println("/image/"+service.getImage());   
    Image image = new Image(getClass().getResourceAsStream("/image/"+service.getImage()));
    serviceImg.setImage(image);
    ServiceNameLabel.setText(service.getTitreS());
    ServiceTypeLabel.setText(service.getTypeS());

    
}
}
