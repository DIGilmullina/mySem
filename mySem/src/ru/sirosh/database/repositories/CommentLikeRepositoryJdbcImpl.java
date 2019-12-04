package ru.sirosh.database.repositories;

import ru.sirosh.models.Comment;
import ru.sirosh.models.CommentLike;
import ru.sirosh.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CommentLikeRepositoryJdbcImpl implements CommentLikeRepository {
    public CommentLikeRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    private Connection connection;

    private final String tableName = "comment_likes";

    private RowMapper<CommentLike> commentLikeRowMapper = row -> {
        return new CommentLike(row.getLong("id"), row.getLong("comment_id"), row.getLong("user_id"));
    };

    @Override
    public CommentLike getByIds(Long userId, Long commentId) {
        String sqlQuery = "SELECT * FROM " + tableName + " where comment_id = ? and user_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, commentId);
            stmt.setLong(2, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                CommentLike cl = null;
                if (rs.next()) {
                    cl = commentLikeRowMapper.mapRow(rs);
                }
                return cl;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Long getCountById(Long commentId) {
        String sqlQuery = "SELECT COUNT(*) FROM " + tableName + " where comment_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, commentId);
            try (ResultSet rs = stmt.executeQuery()) {
                Long count = null;
                if (rs.next()) {
                    count = rs.getLong(1);
                }
                return count;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void changeState(CommentLike commentLike) {
        try{
            if (getByIds(commentLike.getUserId(), commentLike.getCommentId()) != null)
                delete(commentLike);
            else
                save(commentLike);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }


    @Override
    public void save(CommentLike commentLike) {
        String sqlQuery = "insert into " + tableName + "  (comment_id, user_id) values ( ?, ?);";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, commentLike.getCommentId());
            stmt.setLong(2, commentLike.getUserId());
            stmt.executeUpdate();
            ResultSet rs = stmt.executeQuery("select LAST_INSERT_ID();");
            if (rs.next()) {
                commentLike.setId(rs.getLong("LAST_INSERT_ID()"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(CommentLike commentLike) {

    }

    @Override
    public void delete(CommentLike commentLike) {
        String sqlQuery = "delete from " + tableName + " where comment_id = ? and user_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, commentLike.getCommentId());
            stmt.setLong(2, commentLike.getUserId());
            stmt.executeUpdate();
            commentLike = null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<CommentLike> findAll() {
        return null;
    }
}
