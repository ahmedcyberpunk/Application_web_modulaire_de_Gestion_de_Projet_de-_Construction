package tn.esprit.pigc.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
    private final SimpMessagingTemplate messagingTemplate;

    // Inject SimpMessagingTemplate for sending messages
    public NotificationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // This method will send the notification message to the frontend
    public void sendNotification(String message) {
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}
