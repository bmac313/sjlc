package org.sjlchatham.sjlcweb.services;

import org.sjlchatham.sjlcweb.data.PasswordResetTokenDao;
import org.sjlchatham.sjlcweb.models.PasswordResetToken;
import org.sjlchatham.sjlcweb.models.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

    @Autowired
    private PasswordResetTokenDao passwordResetTokenDao;

    public UserService() {}

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(user, token);
        passwordResetTokenDao.save(myToken);
    }

}
