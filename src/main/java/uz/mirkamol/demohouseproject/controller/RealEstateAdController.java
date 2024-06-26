package uz.mirkamol.demohouseproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.mirkamol.demohouseproject.payload.RealEstateAdRequest;
import uz.mirkamol.demohouseproject.service.RealEstateAdService;

import java.security.Principal;

@RestController
@RequestMapping("/realEstateAd")
@RequiredArgsConstructor
public class RealEstateAdController {
    private final RealEstateAdService adService;

    @PostMapping
    public ResponseEntity<?> addProperty(Principal principal,@Validated @RequestBody RealEstateAdRequest adRequest) {
        var apiResponse = adService.add(principal, adRequest);

        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        var all = adService.getAll();
    return ResponseEntity.ok(all);
    }


}
