package cz.uhk.fim.workshop.dtoIn;

import cz.uhk.fim.workshop.model.Event;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Date;

public class EventDtoIn extends Event {
    private Integer id;

    @NotBlank(message = "Attribute name is mandatory")
    @Length(min = 3, max = 64, message = "Attribute name can be minimum of 3 and maximum of 64 characters")
    private String name;


    @Length(max = 512, message = "Attribute name can be maximum of 512 characters")
    private String description;

    //yyyy-MM-dd
    @NotNull(message = "Attribute date is mandatory")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @NotNull(message = "Attribute time is mandatory")
    @DateTimeFormat(pattern = "hh:mm")
    private LocalTime time;

    @NotNull(message = "Attribute publicEvent is mandatory")
    private boolean publicEvent;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public LocalTime getTime() {
        return time;
    }

    @Override
    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public Boolean getPublicEvent() {
        return publicEvent;
    }

    @Override
    public void setPublicEvent(Boolean publicEvent) {
        this.publicEvent = publicEvent;
    }
}
