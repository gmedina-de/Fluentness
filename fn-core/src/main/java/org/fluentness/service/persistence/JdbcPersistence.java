package org.fluentness.service.persistence;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.db.DatabaseTypeUtils;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.logger.LocalLog;
import com.j256.ormlite.support.ConnectionSource;
import org.fluentness.service.configuration.Configuration;
import org.fluentness.service.log.Log;

import java.sql.SQLException;

public class JdbcPersistence implements Persistence {

    private JdbcPooledConnectionSource connectionSource;

    public JdbcPersistence(Configuration configuration, Log log) throws SQLException {
        if (configuration.has(DRIVER) && configuration.has(HOST) && configuration.has(PORT) && configuration.has(DATABASE)) {
            String url = String.format("jdbc:%s://%s:%d/%s%s",
                configuration.get(DRIVER),
                configuration.get(HOST),
                configuration.get(PORT),
                configuration.get(DATABASE),
                configuration.get(URL_PARAMETER_QUERY)
            );
            System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "WARNING");
            DatabaseType databaseType = DatabaseTypeUtils.createDatabaseType(url);
            if (configuration.has(USERNAME) && configuration.has(PASSWORD)) {
                connectionSource = new JdbcPooledConnectionSource(url, configuration.get(USERNAME), configuration.get(PASSWORD), databaseType);
            } else {
                connectionSource = new JdbcPooledConnectionSource(url, databaseType);
            }
        } else {
            log.warning("Persistence not initialized due to lacking configuration");
        }
    }


    @Override
    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }
}
