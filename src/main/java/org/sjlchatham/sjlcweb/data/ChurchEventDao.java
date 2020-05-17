package org.sjlchatham.sjlcweb.data;

import org.sjlchatham.sjlcweb.models.ChurchEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ChurchEventDao extends CrudRepository<ChurchEvent, Integer>{
}
