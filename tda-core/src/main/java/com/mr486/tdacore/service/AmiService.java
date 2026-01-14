package com.mr486.tdacore.service;

import com.mr486.tdacore.configuration.ApplicationConfiguration;
import com.mr486.tdacore.dto.AmiListe;
import com.mr486.tdacore.exeption.TdaException;
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
    private final TrombineService trombineService;

    public List<AmiListe> getListeAmis() {
        List<AmiListe> amis = new ArrayList<>();
        List<Ami> amisDb = amiRepository.findAll();

        amisDb.sort(Comparator.comparing(Ami::getIsGuest)
                .thenComparing(Ami::getNom));

        for (Ami ami : amisDb) {
            Integer id = ami.getId();
            String nom = ami.getNom();
            String imageUrl = trombineService.getImageUrl(ami.getId());
            if (ami.getIsGuest()) {
                nom += ApplicationConfiguration.IMAGE_GUEST;
            }
            AmiListe amiListe = AmiListe.builder()
                    .id(id)
                    .nom(nom)
                    .imageUrl(imageUrl)
                    .build();
            amis.add(amiListe);
        }

        return amis;
    }

    public Boolean existeAmi(Integer id) {
        return amiRepository.existsById(id);
    }

    public Ami getAmiById(Integer id) {
        Ami ami = amiRepository.findById(id).orElse(null);
        if (ami == null) {
            throw new TdaException("Ami avec id " + id + " n'existe pas !!!");
        }
        return ami;
    }
}
