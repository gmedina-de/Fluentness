package org.fluentness.prototype.repository;

import org.fluentness.prototype.model.User;
import org.fluentness.repository.CrudRepository;
import org.fluentness.service.log.Log;
import org.fluentness.service.persistence.Persistence;

import java.sql.SQLException;

public class UserRepository extends CrudRepository<User> {

    public UserRepository(Persistence persistence, Log log) throws SQLException {
        super(persistence, log, User.class);
    }
}
