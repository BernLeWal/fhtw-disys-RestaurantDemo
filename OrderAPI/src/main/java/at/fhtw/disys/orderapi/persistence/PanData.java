package at.fhtw.disys.orderapi.persistence;

import at.fhtw.disys.orderapi.model.Pan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class PanData {

    private static final Logger logger = LoggerFactory.getLogger(PanData.class);
    private static final String CONNECTION_STRING ="jdbc:postgresql://localhost:5432/orders_db";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(CONNECTION_STRING, "postgres", "postgres");
    }

    public int getCleanCount() {
        try ( var conn = connect() ) {
            var query = "SELECT COUNT(*) FROM pans WHERE dirty=0";
            var stmt = conn.prepareStatement(query);
            var result = stmt.executeQuery();
            if ( result.next() ) {
                return result.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            logger.error(e.toString());
            return -1;
        }
    }

    public Pan fetchPan() {
        Pan pan;
        String query = "";
        try ( var conn = connect() ) {
            query = "SELECT id, diameter, teflon FROM pans WHERE dirty=0";
            var stmt = conn.prepareStatement(query);
            var result = stmt.executeQuery();
            if ( result.next() ) {
                pan = new Pan(
                        result.getInt(1),
                        result.getInt(2),
                        result.getInt(3) > 0
                );

                query = "UPDATE pans SET dirty=1 WHERE id=" + pan.getId();
                stmt = conn.prepareStatement(query);
                stmt.executeUpdate();
                return pan;
            }
            logger.warn("No clean pan found!");
            return null;
        } catch (SQLException e) {
            logger.error(query + " " + e);
            return null;
        }
    }

    public void returnPan(Pan pan) {
        String query = "";
        try ( var conn = connect() ) {
            query = "UPDATE pans SET dirty=0 WHERE id=" + pan.getId();
            var stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(query + " " + e);
        }
    }
}
