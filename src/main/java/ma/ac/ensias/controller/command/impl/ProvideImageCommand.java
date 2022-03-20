package ma.ac.ensias.controller.command.impl;

import ma.ac.ensias.controller.attribute.RequestParameterName;
import ma.ac.ensias.controller.command.ActionCommand;
import ma.ac.ensias.controller.command.CommandResult;
import ma.ac.ensias.exception.CommandException;
import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.util.FileService;
import ma.ac.ensias.util.impl.FileServiceImpl;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProvideImageCommand implements ActionCommand {

    private static final String CAUSE_HEADER = "cause";
    private final FileService fileService = FileServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String fileName = (String)request.getAttribute(RequestParameterName.FILE_NAME);
        if (!fileName.isEmpty()) {
            try (ServletOutputStream outputStream = response.getOutputStream()) {
                outputStream.write(fileService.readFile(fileName));
            } catch (IOException | ServiceException e) {
                response.addHeader(CAUSE_HEADER, e.getMessage());
            }
        }
        return null;
    }
}
