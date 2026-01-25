package com.mr486.tdawebui.socket;



import com.mr486.tdawebui.model.ServerState;
import com.mr486.tdawebui.service.ServerStateService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ServerStateWsController {

    private final ServerStateService serverStateService;
    private final SimpMessagingTemplate messagingTemplate;

    public ServerStateWsController(ServerStateService serverStateService,
                                   SimpMessagingTemplate messagingTemplate) {
        this.serverStateService = serverStateService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/state.get")
    @SendTo("/topic/state")
    public ServerState sendState() {
        return serverStateService.getState();
    }

    @MessageMapping("/state.update")
    @SendTo("/topic/state")
    public ServerState updateState(ServerState incoming) {
        int level = incoming.getLevel();
        return serverStateService.updateState(200);
    }

    public void broadcastState() {
        messagingTemplate.convertAndSend("/topic/state", serverStateService.getState());
    }
}
