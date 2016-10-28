<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <%@ include file="/common/meta.jsp" %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title><fmt:message key="webapp.name"/>&trade;&nbsp;-&nbsp;<decorator:title/></title>
    <link rel="stylesheet" href="<c:url value='/themes/admin/css/style.default.v_1.1.css' />" />
    <script type="text/javascript" language="javascript">
        var contextPath = '${vms:getAppURL(pageContext.request)}';
    </script>
    <script src="<c:url value="/themes/admin/js/jquery-1.11.1.min.js"/>"></script>
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src='<c:url value="/themes/admin/js/html5shiv.js"/>' ></script>
    <script src='c:url value="/themes/admin/js/respond.min.js"/>'></script>
    <![endif]-->
    <decorator:head/>
</head>
<body <decorator:getProperty property="body.id" writeEntireProperty="true"/> class='signin <decorator:getProperty property="body.class"
                                                                                                                  writeEntireProperty="true"/>'>
    <!-- Preloader -->
    <div id="preloader">
        <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
    </div>

    <section>
        <div class="signinpanel">
            <decorator:body/>
            <div id="footer" class="signup-footer">
                <jsp:include page="/common/login_footer.jsp"/>
            </div>
        </div>
    </section>
</div>
    <script src="<c:url value="/themes/admin/js/jquery-migrate-1.2.1.min.js" />"></script>
    <script src="<c:url value="/themes/admin/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/themes/admin/js/modernizr.min.js"/>"></script>
    <script src="<c:url value="/themes/admin/js/retina.min.js"/>"></script>
    <script src="<c:url value="/themes/admin/js/custom.js" />"></script>

</body>
</html>
