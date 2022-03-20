package ma.ac.ensias.controller.command.impl;

import ma.ac.ensias.controller.attribute.RequestMethod;
import ma.ac.ensias.controller.attribute.RequestParameterName;
import ma.ac.ensias.controller.attribute.SessionAttributeName;
import ma.ac.ensias.controller.command.ActionCommand;
import ma.ac.ensias.controller.command.CommandResult;
import ma.ac.ensias.controller.path.UrlPath;
import ma.ac.ensias.exception.CommandException;
import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.model.entity.Like;
import ma.ac.ensias.model.entity.User;
import ma.ac.ensias.model.service.LikeService;
import ma.ac.ensias.model.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class DislikeCommand implements ActionCommand {
    LikeService likeService = ServiceFactory.getInstance().getLikeService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if(request.getMethod().equals(RequestMethod.POST)){
            int reviewId = Integer.parseInt(request.getParameter(RequestParameterName.REVIEW_ID));
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(SessionAttributeName.USER);
            int userId = user.getId();
            try {
                Optional<Like> optionalLike = likeService.findLike(userId, reviewId);
                Like like;
                if(optionalLike.isPresent()){
                    like = optionalLike.get();
                    if(!like.isLike()){
                        likeService.deleteLike(userId,reviewId);
                    }else{
                        like.setLike(false);
                        likeService.update(like);
                    }
                }else{
                    like = new Like(userId, reviewId, false);
                    likeService.update(like);
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }
        return new CommandResult(UrlPath.HOME_DO, CommandResult.Type.RETURN_URL);
    }
}
