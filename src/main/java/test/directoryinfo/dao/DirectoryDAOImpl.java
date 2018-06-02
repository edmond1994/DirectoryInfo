package test.directoryinfo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import test.directoryinfo.model.Directory;

import java.sql.*;
import java.util.List;

/**
 * Реализация интерфейса DirectoryDAO
 */
@Component
public class DirectoryDAOImpl implements DirectoryDAO{

    static private String insertSql = "INSERT INTO directory_records (path, dircount, filecount, totalsize, addtime) VALUES (?, ?, ?, ?, ?);";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public DirectoryDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(Directory directory) {

        final Directory directoryTemp = directory;
        KeyHolder recordKey = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                final PreparedStatement ps = connection.prepareStatement(insertSql,
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, directoryTemp.getPath());
                ps.setInt(2, directoryTemp.getDirCount());
                ps.setInt(3, directoryTemp.getFileCount());
                ps.setLong(4, directoryTemp.getTotalSizeBytes());
                ps.setTimestamp(5, directoryTemp.getAddTime());
                return ps;
            }
        }, recordKey);

        return recordKey.getKey().longValue();
    }

    @Override
    public List<Directory> list() {
        String listSql = "SELECT * FROM directory_records;";
        return jdbcTemplate.query(listSql, new RowMapper<Directory>() {

            @Override
            public Directory mapRow(ResultSet dirRows, int rowNum) throws SQLException {

                return new Directory( dirRows.getLong("id"),
                        dirRows.getString("path"),
                        dirRows.getInt("dircount"),
                        dirRows.getInt("filecount"),
                        dirRows.getLong("totalsize"),
                        dirRows.getTimestamp("addtime"));
            }

        });

    }
}
