<%--
  Created by IntelliJ IDEA.
  User: manlm
  Date: 8/8/2016
  Time: 9:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<nav id="nav-menu" class="navbar-default navbar-static-side my-menu" role="navigation">
    <div class="sidebar-collapse">
        <ul class="nav metismenu" id="side-menu">
            <li class="nav-header">
                <div class="dropdown profile-element">
                    <span>
                        <img alt="image" class="img-circle"
                             src="${pageContext.request.contextPath}/resources/img/profile.jpg"/>
                    </span>
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <span class="clear">
                            <span class="block m-t-xs">
                                <strong class="font-bold">
                                        Man Le
                                </strong>
                            </span>
                            <span class="text-muted text-xs block">
                                Admin
                                <b class="caret"></b>
                            </span>
                        </span>
                    </a>
                    <ul class="dropdown-menu animated fadeInRight m-t-xs">
                        <li><a href="profile.html"><spring:message code="profile"/></a></li>
                        <li class="divider"></li>
                        <li>
                            <a href="login.html" onclick="document.getElementById('logoutForm').submit()">
                                <spring:message code="logout"/>
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="logo-element">
                    <spring:message code="ISMB_short"/>
                </div>
            </li>
            <li class="active">
                <a href="index.html"><i class="fa fa-user"></i> <span class="nav-label">Main view</span></a>
            </li>
            <li>
                <a href="minor.html"><i class="fa fa-th-large"></i> <span class="nav-label">Minor view</span> </a>
            </li>
        </ul>

    </div>
</nav>