<%@ include file="/common/taglibs.jsp"%>

<page:applyDecorator name="default">

<head>
    <title><fmt:message key="403.title"/></title>
    <meta name="heading" content="<fmt:message key='403.title'/>"/>
</head>

<div id="content-header">
    <h1><fmt:message key='403.title'/></h1>
</div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">
                <div class="widget-box">
                    <div class="widget-content">
                        <fmt:message key="403.message">
                            <fmt:param><c:url value="/"/></fmt:param>
                        </fmt:message>
                        <div class="clear"></div>
                    </div>
                </div>
             </div>
        </div>
    </div>
</div>

</page:applyDecorator>