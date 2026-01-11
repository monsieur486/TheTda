package com.mr486.tdacore.service;

import com.mr486.tdacore.dto.AmiListe;
import com.mr486.tdacore.persistance.Ami;
import com.mr486.tdacore.repository.AmiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AmiService {

    private final AmiRepository amiRepository;

    public List<AmiListe> getListeAmis() {
        List<AmiListe> amis = new ArrayList<>();
        List<Ami> amisDb = amiRepository.findAll();

        amisDb.sort(Comparator.comparing(Ami::getIsGuest)
                .thenComparing(Ami::getNom));

        for (Ami ami : amisDb) {
            Integer id = ami.getId();
            String nom = ami.getNom();
            if (ami.getIsGuest()) {
                nom += "⭐";
            }
            AmiListe amiListe = AmiListe.builder()
                    .id(id)
                    .nom(nom)
                    .build();
            amis.add(amiListe);
        }

        return amis;
    }
}
