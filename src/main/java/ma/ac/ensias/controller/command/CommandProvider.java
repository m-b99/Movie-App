package ma.ac.ensias.controller.command;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class CommandProvider {


    private static final String DO_SUBSTRING = ".do";

    private static final String SLASH = "/";

    private CommandProvider() { }

    public static Optional<ActionCommand> defineCommand(HttpServletRequest request) {
        Optional<ActionCommand> result;
        String url = request.getRequestURI();
        String stringCommand = parseCommandName(url);
        if (stringCommand != null && !stringCommand.isEmpty()) {
            try {
                CommandType commandType = CommandType.valueOf(stringCommand.toUpperCase());
                ActionCommand command = commandType.getCommand();
                result = Optional.of(command);
            } catch (IllegalArgumentException e) {
                result = Optional.empty();
            }
        } else {
            result = Optional.empty();
        }
        return result;
    }

    public static String parseCommandName(String url){
        String commandName;
        int doPosition = url.indexOf(DO_SUBSTRING);
        if(doPosition == -1){
            return null;
        }
        int lastSlashPosition = url.lastIndexOf(SLASH);
        commandName = url.substring(lastSlashPosition + 1, doPosition);
        return commandName;
    }

    public static Optional<CommandType> defineCommandType(HttpServletRequest request){
        String url = request.getRequestURI();
        String stringCommand = parseCommandName(url);
        if(stringCommand == null){
            return Optional.empty();
        }
        CommandType commandType = CommandType.valueOf(stringCommand.toUpperCase());
        return Optional.of(commandType);
    }
}
