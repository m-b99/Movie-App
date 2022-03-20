<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language"/>
<tags:general title="home">
    <div class="section text-light m-1 p-2">
        <h1 align="center">Welcome To MovieApp!</h1>
        <h4 align="justify">
            <p>Here are some rules you have to know about this website.If you are here for the first time, and you haven`t already registered/login, you are <span class="yellow">GUEST</span>. That means that you can only consult information about different movies. Once you registered and logged in, you are a <span class="yellow">MEMBER</span>, and you can edit your profile, rate a movie and post your review about it. Once you did it, you gain the ability to review movies!. To become an <span class="yellow">ADMINISTRATOR</span>, you have to write to the owner of the web site. Keep in mind that an <span class="yellow">ADMINISTRATOR</span> can block any user or any review, if the last is not appropriate.</p>
        </h4>
    </div>

</tags:general>
