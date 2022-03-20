<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title>Login - MovieApp</title>
    <jsp:include page="/WEB-INF/jsp/shared/style_import.jsp"/>
</head>
<body class="login_body">
<jsp:include page="/WEB-INF/jsp/shared/header.jsp"/>
<body>
<div class="container">
    <div class="d-flex justify-content-center h-100">
        <div class="card">
            <div class="card-header">
                <h3>Login</h3>
                <div class="d-flex justify-content-end social_icon">
                    <span><i class="fab fa-facebook-square"></i></span>
                    <span><i class="fab fa-google-plus-square"></i></span>
                    <span><i class="fab fa-twitter-square"></i></span>
                </div>
            </div>
            <div class="card-body">
                <form action="login.do" method="post" onsubmit=
                        "return loginValidate(this.login.value, this.password.value);">
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-user"></i></span>
                        </div>
                        <input type="text" name="login" class="form-control" placeholder="UserName">
                    </div>
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-key"></i></span>
                        </div>
                        <input type="password" name="password" class="form-control" placeholder="Password">
                    </div>
                    <div class="row align-items-center remember">
                        <input type="checkbox" name="rememberMe">Remember me?
                    </div>
                    <div class="form-group">
                        <input type="submit" value="Login" class="btn float-right login_btn">
                    </div>
                </form>
                <div class="text-danger error">
                    <c:if test="${errorMessage != \"\"}">
                        ${errorMessage}
                    </c:if>
                </div>
                <div class="text-success success">
                    <c:if test="${requestScope.successMessage != \"\"}">
                        ${requestScope.successMessage}
                    </c:if>
                </div>
            </div>
            <div class="card-footer">
                <div class="d-flex justify-content-center links">
                    Don't have an account?
                    <a href="${pageContext.request.contextPath}/register.do" class="ml-2">
                        Register
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/WEB-INF/jsp/shared/scripts_import.jsp"/>
</body>
</html>
