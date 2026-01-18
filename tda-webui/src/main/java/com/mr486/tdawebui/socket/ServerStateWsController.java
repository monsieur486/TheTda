package com.mr486.tdawebui.socket;



import com.mr486.tdawebui.model.ServerState;
import com.mr486.tdawebui.service.ServerStateService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ServerStateWsController {

    private final ServerStateService serverStateService;

    public ServerStateWsController(ServerStateService serverStateService) {
        this.serverStateService = serverStateService;
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
}
