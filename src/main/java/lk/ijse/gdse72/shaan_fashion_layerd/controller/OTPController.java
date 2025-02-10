package lk.ijse.gdse72.shaan_fashion_layerd.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

public class OTPController implements Initializable {

    @FXML
    private JFXButton btnOK;

    @FXML
    private JFXButton btnSend;

    @FXML
    private Label lblEmail;

    @FXML
    private TextField txtOTP;

    @Setter
    private String Email;



    @FXML
    void btnOKOnAction(ActionEvent event) {

    }

    @FXML
    void btnSendOnAction(ActionEvent event) {
        if (Email == null) {
            new Alert(Alert.AlertType.WARNING, "Customer email is not set.").show();
            return;
        }

        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        String otpString = String.valueOf(otp);

        final String FROM = "navidu200210@gmail.com";

        String subject = "Shaan Fashion - User update or password recovery";
        String body = otpString;

        if (subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Subject and body must not be empty!").show();
            return;
        }

        sendEmailWithGmail(FROM, Email, subject, body);
    }

    private void sendEmailWithGmail(String from, String to, String subject, String messageBody) {
        String PASSWORD = "yjwj rhdp lkrj mygs";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message);
            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully!").show();
        } catch (MessagingException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to send email.").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblEmail.setText(Email);    }
}
