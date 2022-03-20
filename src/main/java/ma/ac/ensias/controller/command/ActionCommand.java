package ma.ac.ensias.controller.command;

import ma.ac.ensias.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionCommand {

    CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
