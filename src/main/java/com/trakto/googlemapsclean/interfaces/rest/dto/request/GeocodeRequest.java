package com.trakto.googlemapsclean.interfaces.rest.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GeocodeRequest(
        @NotBlank(message = "La direccion es obligatoria")
        @Size(max = 250, message = "La direccion es demasiado larga")
        String address
) {
}