package ma.ac.ensias.model.creator;

import ma.ac.ensias.model.entity.enums.Role;
import ma.ac.ensias.model.entity.enums.Status;
import ma.ac.ensias.model.entity.User;

public class UserCreator {
    private UserCreator(){}
    public static User createUserAfterRegistration(String login, String passwordHash){
        String userHash = ((Integer)login.hashCode()).toString();
        return new User(login, "",passwordHash, Role.REVIEWER, "",
                false, "", "",userHash, Status.ACTIVE);
    }
}
