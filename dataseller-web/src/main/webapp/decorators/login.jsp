<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <%@ include file="/common/meta.jsp" %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title><fmt:message key="webapp.name"/>&trade;&nbsp;-&nbsp;<decorator:title/></title>
    <decorator:head/>

    <!-- Bootstrap -->
    <link href="<c:url value="/themes/newteample/vendors/bootstrap/dist/css/bootstrap.min.css" />" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="<c:url value="/themes/newteample/vendors/font-awesome/css/font-awesome.min.css" />" rel="stylesheet">
    <!-- NProgress -->
    <link href="<c:url value="/themes/newteample/vendors/nprogress/nprogress.css" />" rel="stylesheet">
    <!-- Animate.css -->
    <link href="<c:url value="/themes/newteample/vendors/animate.css/animate.min.css" />" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="<c:url value="/themes/newteample/content/css/custom_v1.5.css" />" rel="stylesheet">
</head>
<body <decorator:getProperty property="body.id" writeEntireProperty="true"/>
        class='login <decorator:getProperty property="body.class" writeEntireProperty="false"/>'
        style='signin <decorator:getProperty property="body.style" writeEntireProperty="false"/>'>

    <div>
        <a class="hiddenanchor" id="signup"></a>
        <a class="hiddenanchor" id="signin"></a>

        <div class="login_wrapper">
            <div class="animate form login_form">
                <section class="login_content">
                    <decorator:body/>

                    <jsp:include page="/common/login/login_footer.jsp"/>
                </section>
            </div>
        </div>
    </div>
</body>
<script>
    $(document).ready(function(){
        $('#username').focus();
    })
</script>
</html>
