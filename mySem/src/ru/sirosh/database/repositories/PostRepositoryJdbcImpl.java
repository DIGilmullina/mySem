package ru.sirosh.database.repositories;

import ru.sirosh.models.Post;
import ru.sirosh.builders.PostBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PostRepositoryJdbcImpl implements PostRepository {
    private Connection connection;

    private final String tableName = "post";

    public PostRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    private RowMapper<Post> postRowMapper = row -> {
        return PostBuilder.aPost()
                .withId(row.getLong("id"))
                .withAuthorId(row.getLong("author_id"))
                .withImg(row.getString("img"))
                .withMadeDate(row.getTimestamp("made_date"))
                .withName(row.getString("name"))
                .withText(row.getString("text"))
                .build();
    };

    @Override
    public Post findById(Long id) {
        String sqlQuery = "SELECT * FROM " + tableName + "  WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setString(1, String.valueOf(id));
            try (ResultSet rs = stmt.executeQuery()) {
                Post p = null;
                if (rs.next()) {
                    p = postRowMapper.mapRow(rs);
                }
                return p;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Post findByUserId(Long userId) {
        String sqlQuery = "SELECT * FROM " + tableName + "  WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setString(1, String.valueOf(userId));
            try (ResultSet rs = stmt.executeQuery()) {
                Post p = null;
                if (rs.next()) {
                    p = postRowMapper.mapRow(rs);
                }
                return p;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Long postCount() {
        String sqlQuery = "SELECT count(*) FROM " + tableName;
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            try (ResultSet rs = stmt.executeQuery()) {
                Long count = null;
                if (rs.next()) {
                 return rs.getLong(1);
                }
                return count;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Post> findByPage(Long pageSize, Long page,int sortType) {
        String sqlQuery = null;
        if (sortType == 0){
            sqlQuery = "select * from (select * from " + tableName + " ORDER BY id DESC LIMIT ? ) AS newPost ORDER BY id ASC LIMIT ?";
        } else if (sortType== 1){
            sqlQuery = "select * from (select * from " + tableName + " ORDER BY id DESC LIMIT ? ) AS newPost ORDER BY id ASC LIMIT ?";
        }
        if (sqlQuery == null) return null;
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, (page + 1) * pageSize);
            stmt.setLong(2, pageSize);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Post> posts = new LinkedList<>();
                while (rs.next()){
                    posts.add(postRowMapper.mapRow(rs));
                }
                return posts;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Post post) {
        String sqlQuery = "insert into " + tableName + "  (name, text, author_id, made_date) values ( ?, ?, ?, ?);";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setString(1, post.getName());
            stmt.setString(2, post.getText());
            stmt.setLong(3, post.getAuthorId());
            stmt.setTimestamp(4, post.getMadeDate());


            stmt.executeUpdate();
            ResultSet rs = stmt.executeQuery("select LAST_INSERT_ID();");
            if (rs.next()) {
                post.setId(rs.getLong("LAST_INSERT_ID()"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(Post post) {

    }

    @Override
    public void delete(Post post) {
        String sqlQuery = "delete from " + tableName + " where id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, post.getId());
            stmt.executeUpdate();
            post=null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Post> findAll() {
        return null;
    }
}
