package org.sjlchatham.sjlcweb.data;

import org.sjlchatham.sjlcweb.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
}
