package br.com.adrianodeveloper.encryption.healthcheck;


import com.palantir.docker.compose.connection.Container;
import com.palantir.docker.compose.connection.waiting.SuccessOrFailure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Michael J. Simons, 2017-11-06
 */
public final class PostgresHealthCheck {

    public static SuccessOrFailure canConnectTo(final Container container) {
        SuccessOrFailure rv;

        try (
                Connection connection = DriverManager.getConnection(container.port(5432).inFormat("jdbc:postgresql://$HOST:$EXTERNAL_PORT/demo?loggerLevel=OFF"), "demo", "demo")
        ) {
            rv = SuccessOrFailure.success();
        } catch (SQLException e) {
            rv = SuccessOrFailure.fromException(e);
        }
        return rv;
    }
}
