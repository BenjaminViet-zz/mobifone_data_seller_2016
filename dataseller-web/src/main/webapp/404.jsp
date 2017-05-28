<%@ include file="/common/taglibs.jsp"%>
<page:applyDecorator name="admin">
<head>
    <title><fmt:message key="404.title"/></title>
    <meta name="heading" content="<fmt:message key='404.title'/>"/>
    <link rel="stylesheet" href="<c:url value='/themes/${appConfig["csstheme"]}/css/404.css' />" />
</head>
<body>
    <div id="content404" class="container-fluid" align="" style="padding:0px;">
        <div class="container-fluid">
            <div id="content-header">
                <h1><fmt:message key='404.oops'/></h1>
            </div>
            <div class="row-fluid">
                <div class="span12">
                    <div class="widget-box">
                        <div class="widget-content">
                            <p class="text error-content"><fmt:message key="404.title"/></p>
                            <fmt:message key="404.message">
                                <fmt:param value="${requestScope['javax.servlet.forward.request_uri']}"></fmt:param>
                            </fmt:message>
                            <div class="clear"></div>
                            <p class="link error-link"><a href="<c:url value="${vms:getPrefixUrl()}/notification.html"/>" class="btn btn-primary"><fmt:message key="button.backToHome"/></a></p>
                            <div class="clear"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</page:applyDecorator>