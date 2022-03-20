package ma.ac.ensias.util;

/**
 * Provides default avatar for user
 *
 * @author Dmitriy Belotskiy
 */
public class
ImagePathProvider {
    private static final String DEFAULT_AVATAR_PATH = "/img/avatar/default.png";

    public static String getDefaultAvatar(){
        return DEFAULT_AVATAR_PATH;
    }
}
