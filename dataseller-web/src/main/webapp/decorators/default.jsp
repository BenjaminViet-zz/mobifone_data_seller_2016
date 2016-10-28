<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<html lang="en">
<head>
    <%@ include file="/common/meta.jsp" %>
    <meta charset="utf-8">
    <title>Mobifone :: | Hệ thống tích lũy điểm thưởng</title>
    <link href="<c:url value="/themes/promotion/bootstrap/css/bootstrap.min.css"/>" rel='stylesheet' type='text/css'/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/themes/promotion/css/style-general.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/themes/promotion/css/style-mobifone_v1_1.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/themes/promotion/css/style.v1_1.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/themes/admin/css/jquery-ui-1.10.3.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/themes/promotion/css/vms-promotion.v1_4.css"/>"/>

    <script src='<c:url value="/themes/promotion/jquery/jquery.min.js"/>' type='text/javascript'></script>
    <script src='<c:url value="/themes/admin/js/jquery-ui-1.10.3.min.js"/>' type='text/javascript'></script>

    <script src='<c:url value="/themes/promotion/bootstrap/js/bootstrap.min.js"/>'></script>
    <script src='<c:url value="/themes/promotion/bootstrap/js/bootstrap-tab.js"/>'></script>
    </script><script src='<c:url value="/scripts/global.source.v_2.0.js"/>'></script>
</head>
<decorator:head/>
<body <decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class"
                                                                                                   writeEntireProperty="true"/>>
    <jsp:include page="/common/header.jsp"/>
    <decorator:body/>
    <jsp:include page="/common/footer.jsp"/>
</body>
</html>