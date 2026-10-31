package com.trakto.googlemapsclean.interfaces.rest.controller;

import com.trakto.googlemapsclean.application.query.ValidateAddressQueryService;
import com.trakto.googlemapsclean.interfaces.rest.assembler.AddressValidationDtoAssembler;
import com.trakto.googlemapsclean.interfaces.rest.dto.request.AddressValidateRequest;
import com.trakto.googlemapsclean.interfaces.rest.dto.response.AddressValidateResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressValidationController {

    private final ValidateAddressQueryService validateAddressService;
    private final AddressValidationDtoAssembler assembler;

    public AddressValidationController(ValidateAddressQueryService validateAddressService,
                                       AddressValidationDtoAssembler assembler) {
        this.validateAddressService = validateAddressService;
        this.assembler = assembler;
    }

    @PostMapping("/validate-address")
    public ResponseEntity<AddressValidateResponse> validate(
            @Valid @RequestBody AddressValidateRequest request) {

        var verdict = validateAddressService.handle(request.address());
        return ResponseEntity.ok(assembler.toAddressValidateResponseFromDomain(verdict));
    }
}