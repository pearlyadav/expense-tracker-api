package com.pearl.expensetracker.repositories;

import com.pearl.expensetracker.domain.Category;
import com.pearl.expensetracker.exceptions.etBadRequestException;
import com.pearl.expensetracker.exceptions.etResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private static final String SQL_FIND_BY_ID = "SELECT C.CATEGORY_ID, C.USER_ID, C.TITLE, C.DESCRIPTION, COALESCE(SUM(T.AMOUNT), 0) TOTAL_EXPENSE " +
            "FROM ET_TRANSACTIONS T RIGHT OUTER JOIN ET_CATEGORIES C ON C.CATEGORY_ID = T.CATEGORY_ID " +
            "WHERE C.USER_ID = ? AND C.CATEGORY_ID = ? GROUP BY C.CATEGORY_ID";

    private static final String SQL_CREATE_CATEGORY = "INSERT INTO ET_CATEGORIES (CATEGORY_ID, USER_ID, TITLE, DESCRIPTION) " +
            "VALUES (NEXTVAL('ET_CATEGORIES_SEQ'), ?, ?, ?)";

    private static final String SQL_FIND_ALL = "SELECT C.CATEGORY_ID, C.USER_ID, C.TITLE, C.DESCRIPTION, COALESCE(SUM(T.AMOUNT), 0) TOTAL_EXPENSE " +
            "FROM ET_TRANSACTIONS T RIGHT OUTER JOIN ET_CATEGORIES C ON C.CATEGORY_ID = T.CATEGORY_ID " +
            "WHERE C.USER_ID = ? GROUP BY C.CATEGORY_ID";

    private static final String SQL_UPDATE = "UPDATE ET_CATEGORIES SET TITLE = ?, DESCRIPTION = ? WHERE USER_ID = ? AND CATEGORY_ID = ?";

    private static final String SQL_DELETE = "DELETE FROM ET_CATEGORIES WHERE USER_ID = ? AND CATEGORY_ID = ?";

    private static final String SQL_DELETE_ALL_TRANSACTIONS = "DELETE FROM ET_TRANSACTIONS WHERE CATEGORY_ID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> findAll(Integer userId) throws etResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{userId}, categoryRowMapper);
    }

    @Override
    public Category findById(Integer userId, Integer categoryId) throws etResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, categoryId}, categoryRowMapper);
        } catch (Exception e) {
            throw new etResourceNotFoundException("Category Not Found!");
        }
    }

    @Override
    public Integer createCategory(Integer userId, String title, String description) throws etBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE_CATEGORY, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setString(2, title);
                ps.setString(3, description);
                return ps;
            }, keyHolder);
            return (Integer) Objects.requireNonNull(keyHolder.getKeys()).get("CATEGORY_ID");
        } catch (Exception e) {
            throw new etBadRequestException("Invalid Request!!!!");
        }
    }

    @Override
    public void update(Integer userId, Integer categoryId, Category category) throws etBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE, category.getTitle(), category.getDescription(), userId, categoryId);
        } catch (Exception e) {
            throw new etBadRequestException("Invalid Request!");
        }
    }

    @Override
    public void removeById(Integer userId, Integer categoryId) {
        this.removeAllCatTransactions(categoryId);
        jdbcTemplate.update(SQL_DELETE, userId, categoryId);
    }

    private void removeAllCatTransactions(Integer categoryId){
        jdbcTemplate.update(SQL_DELETE_ALL_TRANSACTIONS, categoryId);
    }

    private final RowMapper<Category> categoryRowMapper = (((resultSet, i) -> new Category(
            resultSet.getInt("USER_ID"),
            resultSet.getInt("CATEGORY_ID"),
            resultSet.getString("TITLE"),
            resultSet.getString("DESCRIPTION"),
            resultSet.getDouble("TOTAL_EXPENSE")
    )));
}
