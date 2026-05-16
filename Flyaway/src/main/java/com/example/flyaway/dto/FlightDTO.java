package com.example.hola.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {

    private Long id;

    @NotBlank
    @Pattern(regexp = "^[A-Z0-9]{1,6}$", message = "El numero de vuelo debe tener maximo 6 caracteres: A-Z y 0-9")
    private String numeroVuelo;

    @NotNull
    private LocalDateTime horaSalida;

    @NotNull
    private LocalDateTime horaLlegada;

    @NotNull
    @Positive
    private Integer asientosDisponibles;

    @AssertTrue(message = "La hora de salida debe ser anterior a la hora de llegada")
    public boolean isHorarioValido() {
        if (horaSalida == null || horaLlegada == null) {
            return true;
        }

        return horaSalida.isBefore(horaLlegada);
    }
}
