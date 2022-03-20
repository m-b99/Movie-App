package ma.ac.ensias.util;

import ma.ac.ensias.exception.ServiceException;

/**
 * Interface to work with files
 *
 * @author Dmitriy Belotskiy
 */
public interface FileService {
    /**
     * Reads file by filename
     *
     * @author Dmitriy Belotskiy
     */
    byte[] readFile(String fileName) throws ServiceException;
}
