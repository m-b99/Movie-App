package ma.ac.ensias.controller.tag;

import ma.ac.ensias.controller.attribute.SessionAttributeName;
import ma.ac.ensias.model.entity.User;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;

public class AccessUserTag extends TagSupport {
    private int userId;

    @Override
    public int doStartTag() {
        HttpSession session = pageContext.getSession();
        User user = (User)session.getAttribute(SessionAttributeName.USER);
        if(user != null){
            if(user.getId() == userId){
                return EVAL_BODY_INCLUDE;
            }
        }
        return SKIP_BODY;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
