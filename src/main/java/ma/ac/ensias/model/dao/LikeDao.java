package ma.ac.ensias.model.dao;

import ma.ac.ensias.exception.DaoException;
import ma.ac.ensias.model.entity.Like;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.Optional;


public interface LikeDao {


    MutablePair<Integer, Integer> getLikesAndDislikes(int reviewId) throws DaoException;

    boolean update(Like like) throws DaoException;

    boolean save(Like like) throws DaoException;

    boolean isExists(Like like) throws DaoException;

    Optional<Like> findLike(int userId, int reviewId) throws DaoException;

    boolean deleteLike(int userId, int reviewId) throws DaoException;

}
