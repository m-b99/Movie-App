package ma.ac.ensias.model.service;

import ma.ac.ensias.model.entity.User;
import ma.ac.ensias.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> login(String login, String password) throws ServiceException;

    boolean register(String login, String password, String passwordConfirmed) throws ServiceException;

    Optional<User> findUser(int id) throws ServiceException;

    Optional<User> findUserWithCookies(String login, String userHash) throws ServiceException;

    boolean updateUser(User user) throws ServiceException;

    Optional<User> confirmEmail(int userId, String token) throws ServiceException;

    List<User> findALlUsers() throws ServiceException;


}
