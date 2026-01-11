package com.mr486.tdacore.service;


import com.mr486.tdacore.dto.ContratListe;
import com.mr486.tdacore.exeption.TdaException;
import com.mr486.tdacore.model.Contrat;
import com.mr486.tdacore.repository.ContratRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContratService {

    private final ContratRepository contratRepository;

    public List<ContratListe> getContrats() {
        List<ContratListe> contrats = new ArrayList<>();
        List<Contrat> contratsDb = contratRepository.findAll();
        for (Contrat contrat : contratsDb) {
            contrats.add(new ContratListe(contrat.getId(), contrat.getNom()));
        }
        contrats.sort(Comparator.comparing(ContratListe::getId));
        return contrats;
    }

    public Contrat getContratById(Integer id) {
        Contrat contrat = contratRepository.findById(id).orElse(null);
        if (contrat == null) {
            throw new TdaException("Contrat avec " + id + " n'existe pas !!!");
        }
        return contrat;
    }
}
