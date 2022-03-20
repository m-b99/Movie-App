package ma.ac.ensias.controller.tag;

import ma.ac.ensias.controller.attribute.SessionAttributeName;
import ma.ac.ensias.model.entity.User;
import ma.ac.ensias.model.entity.enums.Role;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;

public class AccessRoleTag extends TagSupport {

    private String accessRole;

    @Override
    public int doStartTag() {
        HttpSession session = pageContext.getSession();
        User user = (User)session.getAttribute(SessionAttributeName.USER);
        if(user != null){
            Role userRole = user.getRole();
            if(Role.valueOf(accessRole).getPriority() <= userRole.getPriority()){
                return EVAL_BODY_INCLUDE;
            }
        }
        return SKIP_BODY;
    }

    public void setAccessRole(String accessRole) {
        this.accessRole = accessRole;
    }
}
