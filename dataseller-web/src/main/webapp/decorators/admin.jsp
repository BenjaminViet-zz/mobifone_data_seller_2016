<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="<c:url value="/themes/admin/images/favicon2.ico"/>" type="image/png">

    <title><decorator:title/></title>

    <!-- Bootstrap -->
    <link href="<c:url value="/themes/newteample/vendors/bootstrap/dist/css/bootstrap.min.css" />" rel="stylesheet" />
    <!-- Font Awesome -->
    <link href="<c:url value="/themes/newteample/vendors/font-awesome/css/font-awesome.min.css" />" rel="stylesheet" />
    <!-- NProgress -->
    <link href="<c:url value="/themes/newteample/vendors/nprogress/nprogress.css" />" rel="stylesheet" />
    <!-- iCheck -->
    <link href="<c:url value="/themes/newteample/vendors/iCheck/skins/flat/green.css" />" rel="stylesheet" />
    <!-- bootstrap-progressbar -->
    <link href="<c:url value="/themes/newteample/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css" />" rel="stylesheet" />
    <!-- JQVMap -->
    <link href="<c:url value="/themes/newteample/vendors/jqvmap/dist/jqvmap.min.css" />" rel="stylesheet"/>
    <!-- bootstrap-daterangepicker -->
    <link href="<c:url value="/themes/newteample/vendors/bootstrap-daterangepicker/daterangepicker.css" />" rel="stylesheet" />

    <!-- Select2 -->
    <link href="<c:url value="/themes/newteample/vendors/select2/dist/css/select2.min.css" />" rel="stylesheet" />

    <!-- Switchery -->
    <link href="<c:url value="/themes/newteample/vendors/switchery/dist/switchery.min.css" />" rel="stylesheet" />

    <link rel="stylesheet" type="text/css" href="<c:url value="/themes/admin/css/jquery-ui-1.10.3.css"/>"/>

    <!-- Datatables -->
    <%--<link href="<c:url value="/themes/newteample/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" />" rel="stylesheet" />--%>
    <%--<link href="<c:url value="/themes/newteample/vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" />" rel="stylesheet" />--%>
    <%--<link href="<c:url value="/themes/newteample/vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" />" rel="stylesheet" />--%>
    <%--<link href="<c:url value="/themes/newteample/vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" />" rel="stylesheet" />--%>
    <%--<link href="<c:url value="/themes/newteample/vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" />" rel="stylesheet" />--%>

    <!-- Custom Theme Style -->
    <link href="<c:url value="/themes/newteample/content/css/custom_v1.0.css" />" rel="stylesheet" />

    <link href="<c:url value="/themes/admin/css/admin_v1.1.css" />" rel="stylesheet" />

    <!-- jQuery -->
    <script src="<c:url value="/themes/admin/js/jquery-1.11.1.min.js" />"></script>
    <script src="<c:url value="/themes/admin/js/jquery-ui-1.10.3.min.js"/>"></script>
    <script src="<c:url value="/themes/admin/js/bootbox.min.js"/>"></script>
    <script src="<c:url value="/themes/admin/js/jquery.validate.js"/>"></script>
    <script src="<c:url value="/themes/admin/js/jquery.mask.min.js"/>"></script>
    <script src="<c:url value="/themes/admin/js/spin.js"/>"></script>
    <script src="<c:url value="/scripts/global.source.v_1.2.js"/>"></script>
    <decorator:head/>
</head>
<body <decorator:getProperty property="body.id" writeEntireProperty="true"/>
        class='nav-md <decorator:getProperty property="body.class" writeEntireProperty="false"/>'
        style='<decorator:getProperty property="body.style" writeEntireProperty="false"/>'>
    <div class="container body">
        <div class="main_container">
            <jsp:include page="/common/admin/admin_nav.jsp"></jsp:include>
            <jsp:include page="/common/admin/admin_header.jsp"/>

            <!-- page content -->
            <div class="right_col" role="main">
                <decorator:body/>
            </div>

            <jsp:include page="/common/admin/admin_footer.jsp"/>
        </div>
    </div>
    <div id="ajaxLoading"></div>
</body>
</html>
