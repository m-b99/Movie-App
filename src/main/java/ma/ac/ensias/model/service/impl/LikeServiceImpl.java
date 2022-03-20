package ma.ac.ensias.model.service.impl;

import ma.ac.ensias.exception.DaoException;
import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.model.dao.LikeDao;
import ma.ac.ensias.model.dao.factory.DaoFactory;
import ma.ac.ensias.model.entity.Like;
import ma.ac.ensias.model.service.LikeService;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.Optional;

public class LikeServiceImpl implements LikeService {

    private final LikeDao likeDao = DaoFactory.getInstance().getLikeDao();

    @Override
    public boolean update(Like like) throws ServiceException {
        try{
            if(likeDao.isExists(like)){
                likeDao.update(like);
            }else{
                likeDao.save(like);
            }
        }catch (DaoException e){
            throw new ServiceException(e);
        }
        return true;
    }

    @Override
    public MutablePair<Integer, Integer> calcLikesAndDislikes(int reviewId) throws ServiceException {
        MutablePair<Integer, Integer> result;
        try{
            result = likeDao.getLikesAndDislikes(reviewId);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Optional<Like> findLike(int userId, int reviewId) throws ServiceException {
        try {
            return likeDao.findLike(userId, reviewId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteLike(int userId, int reviewId) throws ServiceException {
        try {
            return likeDao.deleteLike(userId, reviewId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
