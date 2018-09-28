package org.sjlchatham.sjlcweb.data;

import org.sjlchatham.sjlcweb.models.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PasswordResetTokenDao extends CrudRepository<PasswordResetToken, Integer> {
}
