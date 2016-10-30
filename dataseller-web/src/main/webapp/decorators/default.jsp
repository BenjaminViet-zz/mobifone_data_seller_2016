<%--
  Created by IntelliJ IDEA.
  User: Công Thành
  Date: 01/04/2015
  Time: 09:56
  To change this template use File | Settings | File Templates.
--%>
<html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible"content="IE=edge">
    <meta name="description"content="">
    <meta name="author"content="">
    <title>Mobifone :: | Data Code </title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/themes/mobifonedata/font-awesome/css/font-awesome.min.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/themes/mobifonedata/css/style-general_v1.1.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/themes/mobifonedata/css/style-mobifone_v1.12.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/themes/mobifonedata/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/themes/mobifonedata/css/style.css"/>">
    <link href="https://fonts.googleapis.com/css?family=Lato:300,300i,400,400i,700,700i" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<c:url value="/themes/admin/css/jquery-ui-1.10.3.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/themes/mobifonedata/css/kpp_v1.6.css"/>">

    <script src="<c:url value="/themes/mobifonedata/js/jquery.min.js"/>" type="text/javascript"></script>
    <script src='<c:url value="/themes/admin/js/jquery-ui-1.10.3.min.js"/>' type='text/javascript'></script>
    <script src="<c:url value="/themes/mobifonedata/js/bootstrap.min.js"/>" type="text/javascript"></script>
    <script src='<c:url value="/scripts/global.source.v_2.0.js"/>'></script>
</head>
<body <decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class"
                                                                                                   writeEntireProperty="true"/>>
<jsp:include page="/common/mobifonedata/header.jsp"/>
<decorator:body/>
<jsp:include page="/common/mobifonedata/footer.jsp"/>
</body>
</html>