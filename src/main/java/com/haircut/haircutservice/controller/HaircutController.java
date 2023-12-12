package com.haircut.haircutservice.controller;

import com.haircut.haircutservice.dto.HaircutRequest;
import com.haircut.haircutservice.dto.HaircutResponse;
import com.haircut.haircutservice.service.HaircutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/haircut")
@RequiredArgsConstructor
public class HaircutController {
    private final HaircutService haircutService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody HaircutRequest haircutRequest) {
        haircutService.createHaircut(haircutRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<HaircutResponse> getAllHaircuts() {
        return haircutService.getAllHaircuts();
    }
}
