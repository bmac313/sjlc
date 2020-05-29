package org.sjlchatham.sjlcweb.data;

import org.sjlchatham.sjlcweb.models.ChurchEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ChurchEventDao extends PagingAndSortingRepository<ChurchEvent, Integer> {
    ChurchEvent findFirstByOrderByIdDesc();
    ChurchEvent findFirstByOrderByEventDateDesc();
}
