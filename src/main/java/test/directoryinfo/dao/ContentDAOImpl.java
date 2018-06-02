package test.directoryinfo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import test.directoryinfo.model.Content;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Реализация интерфейса ContentDAO
 */
@Component
public class ContentDAOImpl implements ContentDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ContentDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static private String insertSql = "INSERT INTO content_records (name, size, directoryid) VALUES (?, ?, ?);";

    @Override
    public void save(Content content) {
        jdbcTemplate.update(insertSql, content.getName(), content.getSizeBytes(), content.getDirectoryId());
    }

    @Override
    public void save(Content content, Long directoryId) {
        jdbcTemplate.update(insertSql, content.getName(), content.getSizeBytes(), directoryId);
    }

    @Override
    public List<Content> list(long directoryID) {
        String listSql = "SELECT * FROM content_records where directoryid = " + directoryID + ";";
        return jdbcTemplate.query(listSql, new RowMapper<Content>() {

            @Override
            public Content mapRow(ResultSet contentRows, int rowNum) throws SQLException {

                return new Content( contentRows.getLong("id"),
                        contentRows.getString("name"),
                        contentRows.getLong("size"),
                        contentRows.getLong("directoryid"));

            }

        });
    }
}
