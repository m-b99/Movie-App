package ma.ac.ensias.model.dao.factory;

import ma.ac.ensias.model.dao.*;
import ma.ac.ensias.model.dao.impl.*;

public class DaoFactory {

    private static volatile DaoFactory instance;

    private final UserDao userDao = new UserDaoImpl();

    private final MovieDao movieDao = new MovieDaoImpl();

    private final ReviewDao reviewDao = new ReviewDaoImpl();

    private final RatingDao ratingDao = new RatingDaoImpl();

    private final LikeDao likeDao = new LikeDaoImpl();

    private DaoFactory() { }

    public static DaoFactory getInstance() {
        if (instance == null) {
            synchronized (DaoFactory.class) {
                if (instance == null) {
                    instance = new DaoFactory();
                }
            }
        }
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public MovieDao getMovieDao() {
        return movieDao;
    }

    public ReviewDao getReviewDao() {
        return reviewDao;
    }

    public RatingDao getRatingDao() {
        return ratingDao;
    }

    public LikeDao getLikeDao() {
        return likeDao;
    }
}
