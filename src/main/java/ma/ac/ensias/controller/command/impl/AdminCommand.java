package ma.ac.ensias.controller.command.impl;

import ma.ac.ensias.controller.command.ActionCommand;
import ma.ac.ensias.controller.command.CommandResult;
import ma.ac.ensias.controller.path.PagePath;
import ma.ac.ensias.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminCommand implements ActionCommand {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return new CommandResult(PagePath.ADMIN, CommandResult.Type.FORWARD);
    }
}
