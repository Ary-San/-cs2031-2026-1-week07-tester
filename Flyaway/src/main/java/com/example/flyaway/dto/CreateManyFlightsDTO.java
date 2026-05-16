package com.example.flyaway.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CreateManyFlightsDTO {
    @Valid
    @NotEmpty
    private List<FlightDTO> inputs;

    public List<FlightDTO> getInputs() {
        return inputs;
    }

    public void setInputs(List<FlightDTO> inputs) {
        this.inputs = inputs;
    }
}
