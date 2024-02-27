package cz.uhk.fim.workshop.repository;

import cz.uhk.fim.workshop.model.Event;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDateTime;
import java.util.Date;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EventRepositoryTest {

    @Autowired
    private EventRepository repo;

    @BeforeEach
    public void addData(){
        Event e = new Event();
        e.setName("Testovač");
        e.setDescription("Nová akce");
        e.setDate(new Date());
        e.setTime(LocalDateTime.now().toLocalTime());
        repo.save(e);
    }

    @Test
    public void testEventList(){
        Iterable<Event> events = repo.findAll();
        Assertions.assertThat(events).hasSizeGreaterThan(0);
    }

    @Test
    public void testAddNewEvent(){

        Event e = new Event();
        e.setName("Test");
        e.setDescription("Testovací akce");
        e.setDate(new Date());
        e.setTime(LocalDateTime.now().toLocalTime());
        Event savedEvent = repo.save(e);

        Assertions.assertThat(savedEvent).isNotNull();
        Assertions.assertThat(savedEvent.getId()).isGreaterThan(0);
    }



}