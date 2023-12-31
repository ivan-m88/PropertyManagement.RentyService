package com.vanko.rentyservice.web.controllers;

import com.vanko.rentyservice.business.interfaces.ILandlordService;
import com.vanko.rentyservice.business.implementations.exceptions.LandlordNotFoundException;
import com.vanko.rentyservice.viewmodels.LandlordViewModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/landlord")
public class LandlordController {
    private final ILandlordService landlordService;

    public LandlordController(ILandlordService landlordService) {
        this.landlordService = landlordService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getLandlord(@PathVariable int id) {
        var landlordView = this.landlordService.getLandlordView(id);
        if (landlordView != null) {
            return new ResponseEntity<>(landlordView, HttpStatus.OK);
        } else {
            throw new LandlordNotFoundException("Landlord with ID: " + id + " not found!");
        }
    }

    @PostMapping("/addLandlord")
    public ResponseEntity<Void> addLandlord(@Valid @RequestBody LandlordViewModel landlordView,
                                            UriComponentsBuilder uriComponentsBuilder) {
        long id = this.landlordService.addLandlord(landlordView);

        URI uri = uriComponentsBuilder.path("/api/landlord/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }
}
