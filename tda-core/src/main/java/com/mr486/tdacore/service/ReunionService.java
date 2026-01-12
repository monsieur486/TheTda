package com.mr486.tdacore.service;

import com.mr486.tdacore.persistance.Reunion;
import com.mr486.tdacore.repository.ReunionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReunionService {

    private final ReunionRepository reunionRepository;

    public Reunion getReunionActive() {
        return reunionRepository.findFirstByEstActiveTrue()
                .orElseGet(() -> {
                    Reunion nouvelle = new Reunion();
                    nouvelle.setEstActive(true);
                    nouvelle.setStatus(1);
                    return reunionRepository.save(nouvelle);
                });
    }

    public void setReunionActiveStatus(Integer status) {
        Reunion reunion = getReunionActive();
        reunion.setStatus(status);
        reunionRepository.save(reunion);
    }

    public Integer reunionActiveStatus() {
        return getReunionActive().getStatus();
    }

    public UUID getReunionActiveUuid() {
        return getReunionActive().getUuid();
    }
}
