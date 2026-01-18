package com.mr486.tdawebui.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServerState {
    private int level;

    public ServerState() {
    }

    public ServerState(int level) {
        this.level = level;
    }

}
