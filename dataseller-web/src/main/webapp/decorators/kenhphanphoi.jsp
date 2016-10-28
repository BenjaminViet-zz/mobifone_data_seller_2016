<html lang="en">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible"content="IE=edge">
    <meta name="description"content="">
    <meta name="author"content="">
    <title>Mobifone :: | Tích điểm trên kênh phân phối</title>

    <link rel="stylesheet" type="text/css" href="<c:url value="/themes/kenhphanphoi/css/style-general_v1.1.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/themes/kenhphanphoi/css/style-mobifone_v1.12.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/themes/kenhphanphoi/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/themes/kenhphanphoi/css/style.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/themes/admin/css/jquery-ui-1.10.3.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/themes/kenhphanphoi/css/kpp.v1.7.css"/>">

    <script src="<c:url value="/themes/kenhphanphoi/js/jquery.min.js"/>" type="text/javascript"></script>
    <script src='<c:url value="/themes/admin/js/jquery-ui-1.10.3.min.js"/>' type='text/javascript'></script>
    <script src="<c:url value="/themes/kenhphanphoi/js/bootstrap.min.js"/>" type="text/javascript"></script>
    </script><script src='<c:url value="/scripts/global.source.v_2.0.js"/>'></script>
</head>
<body <decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class"
                                                                                                   writeEntireProperty="true"/>>
<jsp:include page="/common/kenhphanphoi_header.jsp"/>
<decorator:body/>
<jsp:include page="/common/kenhphanphoi_footer.jsp"/>
</body>
</html>