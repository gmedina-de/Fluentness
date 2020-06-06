package org.fluentness.prototype.user;

import org.fluentness.repository.AbstractRepository;
import org.fluentness.service.log.Log;
import org.fluentness.service.persistence.Persistence;

import java.sql.SQLException;

public class UserRepository extends AbstractRepository<User> {

    public UserRepository(Persistence persistence, Log log) throws SQLException {
        super(persistence, log, User.class);
    }
}
