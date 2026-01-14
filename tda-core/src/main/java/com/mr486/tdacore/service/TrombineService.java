package com.mr486.tdacore.service;

import com.mr486.tdacore.persistance.Trombine;
import com.mr486.tdacore.repository.TrombineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrombineService {

    private final TrombineRepository trombineRepository;

    public String getImageUrl(Integer id) {

        return trombineRepository.findById(id)
                .map(Trombine::getNom)
                .orElse("inconnu.png");
    }
}
