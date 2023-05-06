package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import entite.Service;
import javafx.scene.input.MouseEvent;

public class itemController {

    private Label nameLabel;

    private ImageView img;

    private Service service;
    private MyListener listener;
    @FXML
    private Label ServiceNameLabel;
    @FXML
    private Label ServiceTypeLabel;
    @FXML
    private ImageView serviceImg;

    @FXML
    private void handleClick(MouseEvent event) {
    }

    
public interface MyListener {
    void onClickListener(Service service);
}

    private void handleClick(ActionEvent event) {
        if (listener != null && service != null) {
            listener.onClickListener(service);
        }
    }

    public void setData(Service service, MyListener listener) {
        this.service = service;
        this.listener = listener;
        if (service != null) {
            nameLabel.setText(service.getTitreS());
            String imageFile = service.getImage();
            if (imageFile != null) {
                Image image = new Image(getClass().getResourceAsStream(imageFile));
                img.setImage(image);
            }
        }
    }
}
