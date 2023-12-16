package com.haircut.haircutservice.service;

import com.haircut.haircutservice.dto.HaircutRequest;
import com.haircut.haircutservice.dto.HaircutResponse;
import com.haircut.haircutservice.model.Haircut;
import com.haircut.haircutservice.repository.HaircutRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void updateHaircut(String haircutId, HaircutRequest haircutRequest) {
        Optional<Haircut> optionalHaircut = haircutRepository.findById(haircutId);

        if (optionalHaircut.isEmpty()) {
            log.warn("Haircut with ID " + haircutId + " not found");
        }

        Haircut existingHaircut = optionalHaircut.get();
        existingHaircut.setName(haircutRequest.getName());
        existingHaircut.setPrice(haircutRequest.getPrice());
        existingHaircut.setStatus(haircutRequest.getStatus());

        haircutRepository.save(existingHaircut);
        log.info("Haircut " + haircutId + " is updated");
    }

    private HaircutResponse mapToHaircutResponse(Haircut haircut) {
        return HaircutResponse.builder()
                .id(haircut.getId())
                .name(haircut.getName())
                .status(haircut.getStatus())
                .price(haircut.getPrice())
                .build();
    }

    public Optional<HaircutResponse> detailHaircut(String haircutId) {
        Optional<Haircut> optionalHaircut = haircutRepository.findById(haircutId);

        return optionalHaircut.map(haircut ->
                HaircutResponse.builder()
                        .id(haircut.getId())
                        .name(haircut.getName())
                        .status(haircut.getStatus())
                        .price(haircut.getPrice())
                        .build()
        );
    }
}
