package client.controllers.message;

import client.request.SocketHandler;
import client.store.MyProfileStore;
import client.utils.ImageUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import shared.models.Message;
import shared.request.Packet;
import shared.request.StatusCode;

public class MessageCardController {

    @FXML
    private Label lblContent;

    @FXML
    private ImageView imgReceiverAvatar, imgSenderAvatar, imgPhoto;

    @FXML
    private FontAwesomeIconView iconEdit;

    @FXML
    private JFXButton btnEdit, btnDelete;

    @FXML
    private JFXTextArea txtEditMessage;

    private boolean editMode;
    private Message message;

    @FXML
    void deleteMessage(ActionEvent event) {
        Packet packet = new Packet("message-action");
        packet.put("type", "delete");
        packet.put("message-id", message.id);
        Packet res = SocketHandler.getSocketHandlerWithoutException().sendPacketAndGetResponse(packet);
        if (res.getStatus() == StatusCode.OK) {
            btnDelete.setVisible(false);
            btnEdit.setVisible(false);
            lblContent.setText("Deleted!");
            txtEditMessage.setVisible(false);
            lblContent.setVisible(true);
        }
    }

    @FXML
    void editMessage(ActionEvent event) {
        editMode = !editMode;
        lblContent.setVisible(!editMode);
        txtEditMessage.setVisible(editMode);
        iconEdit.setIcon(editMode ? FontAwesomeIcon.CHECK : FontAwesomeIcon.PENCIL);
        if (editMode)
            txtEditMessage.setText(message.getContent());
        else {
            Packet packet = new Packet("message-action");
            packet.put("type", "edit");
            packet.put("content", txtEditMessage.getText());
            packet.put("message-id", message.id);
            Packet res = SocketHandler.getSocketHandlerWithoutException().sendPacketAndGetResponse(packet);
            if (res.getStatus() == StatusCode.OK) {
                message.setContent(txtEditMessage.getText());
                lblContent.setText(message.getContent());
            }
        }
    }

    public void showMessage(Message message) {
        this.message = message;
        txtEditMessage.setVisible(false);
        btnEdit.setVisible(false);
        btnDelete.setVisible(false);
        if (message.getSender().id == MyProfileStore.getInstance().getUser().id) {
            btnDelete.setVisible(true);
            btnEdit.setVisible(true);
            if (message.getSender().getPhoto() != null)
                imgSenderAvatar.setImage(ImageUtils.load(message.getSender().getPhoto()));
        }
        else if (message.getSender().getPhoto() != null)
            imgReceiverAvatar.setImage(ImageUtils.load(message.getSender().getPhoto()));
        lblContent.setText(message.getContent());
        if (message.getPhoto() != null)
            imgPhoto.setImage(ImageUtils.load(message.getPhoto()));
    }
}
