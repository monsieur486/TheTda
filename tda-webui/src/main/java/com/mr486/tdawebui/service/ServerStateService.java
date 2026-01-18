package com.mr486.tdawebui.service;

import com.mr486.tdawebui.model.ServerState;
import org.springframework.stereotype.Service;

@Service
public class ServerStateService {


    private final ServerState state = new ServerState(
            100
    );

    public synchronized ServerState getState() {
        return new ServerState(
                state.getLevel()
        );
    }

    public synchronized ServerState updateState(Integer level) {
        state.setLevel(level);
        return getState();
    }
}

