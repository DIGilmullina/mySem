package ru.sirosh.database.repositories;

import ru.sirosh.models.PostLike;
import ru.sirosh.models.PostLike;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostLikeRepositoryJdbcImpl implements PostLikeRepository {
    public PostLikeRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    private Connection connection;

    private final String tableName = "post_likes";

    private RowMapper<PostLike> PostLikeRowMapper = row -> {
        return new PostLike(row.getLong("id"), row.getLong("post_id"), row.getLong("user_id"));
    };

    @Override
    public PostLike getByIds(Long userId, Long postId) {
        String sqlQuery = "SELECT * FROM " + tableName + " where post_id = ? and user_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, postId);
            stmt.setLong(2, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                PostLike pl = null;
                if (rs.next()) {
                    pl = PostLikeRowMapper.mapRow(rs);
                }
                return pl;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Long getCountById(Long postId) {
        String sqlQuery = "SELECT COUNT(*) FROM " + tableName + " where post_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, postId);
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
    public void changeState(PostLike postLike) {
        try {
            if (getByIds(postLike.getUserId(), postLike.getPostId()) != null)
                delete(postLike);
            else
                save(postLike);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(PostLike PostLike) {
        String sqlQuery = "insert into " + tableName + "  (post_id, user_id) values ( ?, ?);";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, PostLike.getPostId());
            stmt.setLong(2, PostLike.getUserId());
            stmt.executeUpdate();
            ResultSet rs = stmt.executeQuery("select LAST_INSERT_ID();");
            if (rs.next()) {
                PostLike.setId(rs.getLong("LAST_INSERT_ID()"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(PostLike PostLike) {

    }

    @Override
    public void delete(PostLike PostLike) {
        String sqlQuery = "delete from " + tableName + " where post_id = ? and user_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, PostLike.getPostId());
            stmt.setLong(2, PostLike.getUserId());
            stmt.executeUpdate();
            PostLike = null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<PostLike> findAll() {
        return null;
    }
}
