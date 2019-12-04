package ru.sirosh.database.repositories;

import ru.sirosh.models.Comment;
import ru.sirosh.builders.CommentBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CommentRepositoryJdbcImpl implements CommentRepository {
    private Connection connection;

    private final String tableName = "comment";

    public CommentRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    private RowMapper<Comment> commentRowMapper = row -> {
        return CommentBuilder.aComment()
                .withId(row.getLong("id"))
                .withAuthorId(row.getLong("user_id"))
                .withPostId(row.getLong("post_id"))
                .withText(row.getString("text"))
                .build();
    };

    @Override
    public Comment findById(Long id) {
        String sqlQuery = "SELECT * FROM " + tableName + "  WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                Comment c = null;
                if (rs.next()) {
                    c = commentRowMapper.mapRow(rs);
                }
                return c;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Comment findByUserId(Long userId) {
        String sqlQuery = "SELECT * FROM " + tableName + "  WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                Comment c = null;
                if (rs.next()) {
                    c = commentRowMapper.mapRow(rs);
                }
                return c;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        String sqlQuery = "SELECT * FROM " + tableName + "  WHERE post_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, postId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Comment> comments  = new ArrayList<>();
                while(rs.next()){
                    comments.add(commentRowMapper.mapRow(rs));
                }
                return comments;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    @Override
    public void save(Comment comment) {
        String sqlQuery = "insert into " + tableName + "  (text, user_id, post_id) values ( ?, ?, ?);";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setString(1, comment.getText());
            stmt.setLong(2, comment.getAuthorId());
            stmt.setLong(3, comment.getPostId());


            stmt.executeUpdate();
            ResultSet rs = stmt.executeQuery("select LAST_INSERT_ID();");
            if (rs.next()) {
                comment.setId(rs.getLong("LAST_INSERT_ID()"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(Comment comment) {

    }

    @Override
    public void delete(Comment comment) {
        String sqlQuery = "delete from " + tableName + " where id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, comment.getId());
            stmt.executeUpdate();
            comment=null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    @Override
    public List<Comment> findAll() {
        return null;
    }
}
