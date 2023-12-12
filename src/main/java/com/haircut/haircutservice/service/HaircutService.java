package com.haircut.haircutservice.service;

import com.haircut.haircutservice.dto.HaircutRequest;
import com.haircut.haircutservice.dto.HaircutResponse;
import com.haircut.haircutservice.model.Haircut;
import com.haircut.haircutservice.repository.HaircutRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HaircutService {

    private final HaircutRepository haircutRepository;

    public void createHaircut(HaircutRequest haircutRequest) {
        Haircut haircut = Haircut.builder()
                .name(haircutRequest.getName())
                .price(haircutRequest.getPrice())
                .status(haircutRequest.getStatus()).build();

        haircutRepository.save(haircut);
        log.info("Haircut " + haircut.getId() + " is saved");
    }

    public List<HaircutResponse> getAllHaircuts() {
        List<Haircut> haircuts = haircutRepository.findAll();

        return haircuts.stream().map(this::mapToHaircutResponse).toList();
    }

    private HaircutResponse mapToHaircutResponse(Haircut haircut) {
        return HaircutResponse.builder()
                .id(haircut.getId())
                .name(haircut.getName())
                .status(haircut.getStatus())
                .price(haircut.getPrice())
                .build();
    }
}
