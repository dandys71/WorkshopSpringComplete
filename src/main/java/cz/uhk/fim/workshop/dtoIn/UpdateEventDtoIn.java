package cz.uhk.fim.workshop.dtoIn;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UpdateEventDtoIn extends EventDtoIn{
    @NotNull(message = "Attribute id is mandatory")
    @Positive(message = "Attribute id is mandatory")
    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
