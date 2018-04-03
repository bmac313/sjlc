package org.sjlchatham.sjlcweb.data;

import org.sjlchatham.sjlcweb.models.Authorities;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AuthoritiesDao extends CrudRepository<Authorities, Integer> {
}
