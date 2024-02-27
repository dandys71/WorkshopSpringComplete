package cz.uhk.fim.workshop.repository;

import cz.uhk.fim.workshop.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
    public List<Event> getEventsByName(String name);


    @Query("SELECT e FROM Event e ORDER BY e.name DESC")
    public List<Event> getSortedEvents();

    @Query(value = "SELECT * FROM event ORDER BY name DESC", nativeQuery = true)
    public List<Event> getSortedEventsNative();

    @Query("select e from Event e where e.name LIKE %:eventName%")
    List<Event> findEvent(@Param("eventName") String eventName);
}
