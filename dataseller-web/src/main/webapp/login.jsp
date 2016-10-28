<%@page trimDirectiveWhitespaces="true"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="login.title"/></title>
    <style>
        #loginContainer{
            float: none;
            margin: 0px auto;
        }
    </style>
</head>
    <div class="row">
        <div id="loginContainer" class="col-md-5">

            <form name="loginForm" action="<c:url value="/j_security_check"/>" method="post">
                <h4 class="nomargin"><fmt:message key="login"/></h4>
                <p class="mt5 mb20"><fmt:message key="page.login.info"/></p>

                <input type="text" name="j_username" id="username" class="form-control uname" placeholder="<fmt:message key="page.login.username"/>" />
                <input type="password" name="j_password" id="password" class="form-control pword" placeholder="<fmt:message key="page.login.password"/>" />
                <button class="btn btn-success btn-block" id="submitForm"><fmt:message key="login"/></button>
                <c:if test="${not empty param.error}">
                    <small style="color: orangered">
                        <c:choose>
                            <c:when test="${param.error == 1}">
                                <fmt:message key="login.error.passwordmissedmatch"/>
                            </c:when>
                            <c:when test="${param.error == 2}">
                                <fmt:message key="login.error.sessionexpired"/>
                            </c:when>
                        </c:choose>
                    </small>
                </c:if>
            </form>
        </div>
    </div>

<script type="text/javascript">
    $(document).ready(function(){
        $('#submitForm').click(function(){
            if($('#username').val() == '' || $('#password').val() == '') {
                alert('<fmt:message key="login.error.empty.username.or.password"/>');
                return false;
            }
            return true;
        });
    });
</script>