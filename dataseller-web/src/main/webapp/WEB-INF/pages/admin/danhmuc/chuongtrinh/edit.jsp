<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title>
        <c:choose>
            <c:when test="${not empty item.pojo.chuongTrinhId}">
                <fmt:message key="dmchuongtrinh.edit.heading_page" />
            </c:when>
            <c:otherwise><fmt:message key="dmchuongtrinh.add.heading_page" /></c:otherwise>
        </c:choose>
    </title>
    <c:choose>
        <c:when test="${not empty item.pojo.chuongTrinhId}">
            <meta name="menu" content="<fmt:message key="dmchuongtrinh.edit.heading_page"/>"/>
        </c:when>
        <c:otherwise>
            <meta name="menu" content="<fmt:message key="dmchuongtrinh.add.heading_page"/>"/>
        </c:otherwise>
    </c:choose>
    <style>
        input{
            width: 220px;
        }
    </style>
</head>
<c:url var="backUrl" value="/admin/danhmucchuongtrinh.html"/>
<c:url var="formUrl" value="/admin/danhmucchuongtrinh/themmoi.html"/>
<c:if test="${not empty item.pojo.chuongTrinhId}">
    <c:url var="formUrl" value="/admin/danhmucchuongtrinh/chinhsua.html"/>
</c:if>

<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i>
        <c:choose>
            <c:when test="${not empty item.pojo.chuongTrinhId}">
                <fmt:message key="dmchuongtrinh.edit.heading_page" />
            </c:when>
            <c:otherwise>
                <fmt:message key="dmchuongtrinh.add.heading_page" />
            </c:otherwise>
        </c:choose>
    </h2>

</div>
<div class="contentpanel">

    <div class="panel panel-default">
        <div class="panel-heading">
            <c:if test="${!empty messageResponse}">
                <div class="span12">
                    <div class="alert alert-${alertType}">
                        <a class="close" data-dismiss="alert" href="#">&times;</a>
                            ${messageResponse}
                    </div>
                </div>
            </c:if>
        </div>
        <br />
        <div class="panel-body panel-body-nopadding">
            <div class="row-fluid report-filter">
                <form:form commandName="item" id="formEdit" action="${formUrl}" method="post" validate="validate">
                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="dmchuongtrinh.label.code" /><span class="text-error">*</span></label>
                                    <div class="col-sm-10">
                                        <form:input id="code" path="pojo.code" cssClass="required form-control nohtml"></form:input>
                                        <form:errors for="code" path="pojo.code" cssClass="error-inline-validate"/>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="dmchuongtrinh.label.description" /><span class="text-error">*</span></label>
                                    <div class="col-sm-10">
                                        <form:textarea id="description" path="pojo.description" cssClass="required nohtml form-control"></form:textarea>
                                        <form:errors for="description" path="pojo.description" cssClass="error-inline-validate"/>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="dmchuongtrinh.label.db_link" /><span class="text-error">*</span></label>
                                    <div class="col-sm-10">
                                        <form:input id="db_link" path="pojo.dbLinkName" cssClass="required nohtml form-control"></form:input>
                                        <form:errors for="db_link" path="pojo.dbLinkName" cssClass="error-inline-validate"/>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"></label>
                                    <div class="col-sm-8">
                                        <a id="btnSaveOrEdit" class="btn btn-primary">
                                            <c:choose>
                                                <c:when test="${not empty item.pojo.chuongTrinhId}">
                                                    <fmt:message key="label.update" />
                                                </c:when>
                                                <c:otherwise>
                                                    <fmt:message key="label.add_new" />
                                                </c:otherwise>
                                            </c:choose>
                                        </a>&nbsp;
                                        <a href="${backUrl}" class="btn btn-info"><fmt:message key="label.huy" /></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <form:hidden path="pojo.chuongTrinhId" />
                    <input type="hidden" name="crudaction" value="insert-update" />
                </form:form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        // active tab
        setActiveMenu4Admin('#menuDanhMucTab', '#dmChuongTrinh');

        $("#btnSaveOrEdit").click(function(){
            if($('#formEdit').valid()){
                bootbox.confirm('<fmt:message key="label.confirm_title" />', '<fmt:message key="label.confirm_operation_content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
                    if(r){
                        $("#formEdit").submit();
                    }
                });
            }
        });
    });
</script>
</body>