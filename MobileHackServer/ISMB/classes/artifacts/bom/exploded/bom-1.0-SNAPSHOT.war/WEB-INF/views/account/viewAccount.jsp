<%--
  Created by IntelliJ IDEA.
  User: manlm
  Date: 9/10/2016
  Time: 12:04 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="${pageContext.request.contextPath}/resources/js/viewAccount.js"></script>

<div class="card">
    <div class="my-c-header">
        <h2 id="tableHeader"><spring:message code="manage_account"/></h2>
    </div>
    <div class="card-body card-padding">
        <a class="btn btn-primary waves-effect" href="viewAddAccount">
            <spring:message code="btn_add"/>
        </a>
        <br>
        <div class="row m-t-20">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="table-responsive">
                        <table id="user-data-table" class="table table-striped">
                            <thead>
                            <tr>
                                <th data-column-id="username" data-order="desc"><spring:message code="username"/></th>
                                <th data-column-id="email"><spring:message code="email"/></th>
                                <th data-column-id="phone"><spring:message code="phone"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="account" items="${adminAccountList}">
                                <tr>
                                    <td>${account.username}</td>
                                    <td>${account.email}</td>
                                        <%--<td>${account.phone}</td>--%>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

</script>
