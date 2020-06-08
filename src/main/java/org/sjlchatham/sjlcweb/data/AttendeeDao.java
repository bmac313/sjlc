package org.sjlchatham.sjlcweb.data;

import org.sjlchatham.sjlcweb.models.Attendee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface AttendeeDao extends CrudRepository<Attendee, Integer> {
    List<Attendee> findByEventIdOrderByLastNameAsc(int eventId);
    List<Attendee> findByEventIdOrderByLastNameAscFirstNameAscMiAscEmailAsc(int eventId);
}
