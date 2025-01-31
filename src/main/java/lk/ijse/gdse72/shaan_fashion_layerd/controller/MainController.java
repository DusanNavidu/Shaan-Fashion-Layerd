package lk.ijse.gdse72.shaan_fashion_layerd.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML
    private Label lblDes;

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView3;

    @FXML
    private AnchorPane mainPage;

    @FXML
    private Button btnLetsGo;

    @FXML
    void mainPageOnAction(MouseEvent event) {
    }

    @FXML
    public void initialize() {
        autoText();
        autoImage();
    }

    @FXML
    void btnLetsGoOnAction(ActionEvent event) throws IOException {
        mainPage.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        mainPage.getChildren().add(load);
    }

    @FXML
    public void autoText() {
        String text = "Welcome to \n SHAAN FASHION \n POS System";

        new Thread(() -> {
            StringBuilder displayedText = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                char letter = text.charAt(i);
                displayedText.append(letter);

                Platform.runLater(() -> lblDes.setText(displayedText.toString()));

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @FXML
    public void autoImage() {
        String[] images = {
                "/images/pngimg.com - dress_PNG10.png",
                "/images/7-2-dress-shirt-png-hd.png",
                "/images/pngwing.com.png" ,
                "/images/pngimg.com - dress_PNG115.png",
                "/images/men-s-pants-x70KAW0-600-removebg-preview.png"// Add more images as needed
        };

        ImageView[] imageViews = {imageView1, imageView2, imageView3};

        new Thread(() -> {
            int imageIndex = 0; // Index to track the current image
            while (true) { // Infinite loop for continuous rotation
                int viewIndex = imageIndex % imageViews.length; // Rotate through ImageViews
                String imagePath = images[imageIndex % images.length]; // Rotate through images

                Platform.runLater(() -> {
                    try {
                        URL imageURL = getClass().getResource(imagePath);
                        if (imageURL != null) {
                            Image image = new Image(imageURL.toString());
                            imageViews[viewIndex].setImage(image);
                        } else {
                            System.out.println("Image not found at path: " + imagePath);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                imageIndex++; // Move to the next image

                try {
                    Thread.sleep(2000); // Delay before showing the next image
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
