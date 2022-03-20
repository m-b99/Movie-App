package ma.ac.ensias.util.impl;

import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.util.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileServiceImpl implements FileService {

    private final String AVATAR_UPLOAD_DIRECTORY = new StringBuilder("C:")
            .append(File.separator).append("Users").append(File.separator).append("user1").append(File.separator).append("Desktop")
            .append(File.separator).append("MovieStar-master").append(File.separator).append("src").append(File.separator).append("main").append(File.separator)
            .append("webapp").append(File.separator).append("img").append(File.separator).append("avatar").toString();

    private static FileServiceImpl instance;

    private FileServiceImpl(){ }

    public static FileServiceImpl getInstance(){
        if(instance == null){
            instance = new FileServiceImpl();
        }
        return instance;
    }

    @Override
    public byte[] readFile(String fileName) throws ServiceException {
        byte[] result;
        String fileUri = AVATAR_UPLOAD_DIRECTORY + File.separator + fileName;
        Path filePath = Path.of(fileUri);
        if (Files.exists(filePath)) {
            try {
                result = Files.readAllBytes(filePath);
            } catch (IOException e) {
                throw new ServiceException("Cant read file", e);
            }
        } else {
            throw new ServiceException("File not exists");
        }
        return result;
    }
}
