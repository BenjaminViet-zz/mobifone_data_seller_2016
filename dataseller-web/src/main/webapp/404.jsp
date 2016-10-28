<%@ include file="/common/taglibs.jsp"%>
<page:applyDecorator name="default">
<head>
    <title><fmt:message key="404.title"/></title>
    <meta name="heading" content="<fmt:message key='404.title'/>"/>
    <link rel="stylesheet" href="<c:url value='/themes/${appConfig["csstheme"]}/css/404.css' />" />
</head>
<body>
    <div id="content404" class="container-fluid" align="center" style="text-align: center;padding:0px;">
        <div class="row-fluid">
            <div class="span12">
                <div class="lost-bannercontent">
                    <p class="text"><fmt:message key="404.title"/></p>
                    <p class="text">
                        <fmt:message key="404.message">
                            <fmt:param value="${requestScope['javax.servlet.forward.request_uri']}"></fmt:param>
                        </fmt:message>
                    </p>

                    <p class="link"><a href="<c:url value="/"/>" class="btn"><fmt:message key="button.backToHome"/></a></p>
                    <div class="clear"></div>
                </div>
            </div>
        </div>
    </div>
</body>
</page:applyDecorator>