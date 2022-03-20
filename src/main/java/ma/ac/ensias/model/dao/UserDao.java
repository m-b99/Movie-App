package ma.ac.ensias.model.dao;

import ma.ac.ensias.exception.DaoException;
import ma.ac.ensias.model.entity.BaseEntity;
import ma.ac.ensias.model.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(int id) throws DaoException;

    List<User> findAll() throws DaoException;

    List<User> findAllByCriteria(Map<String, String> criteria) throws DaoException;

    List<User> findWithLimits(int offset, int limit) throws DaoException;

    boolean save(BaseEntity entity) throws DaoException;

    boolean update(BaseEntity entity) throws DaoException;

    boolean delete(int id) throws DaoException;

    int findCount() throws DaoException;

    Optional<User> findByLogin(String login) throws DaoException;

    Optional<User> findByLoginAndUserHash(String login, String userHash) throws DaoException;

}
