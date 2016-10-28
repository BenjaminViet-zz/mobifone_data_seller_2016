<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="chinhanh.thong_tin_chi_nhanh.heading_page" /></title>
    <meta name="heading" content="<fmt:message key="chinhanh.thong_tin_chi_nhanh.heading_page" />"/>
</head>
<c:url var="formUrl" value="/admin/thongtinchinhanh.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="chinhanh.thong_tin_chi_nhanh.heading_page" /></h2>

</div>
<div class="contentpanel">
    <div class="panel panel-default">

        <div class="panel-body panel-body-nopadding">
            <c:if test ="${not empty messageResponse}">
                <div class="alert alert-${alertType}">
                    <a class="close" data-dismiss="alert" href="#">&times;</a>
                    ${messageResponse}
                </div>
            </c:if>
            <div class="row-fluid report-filter">
                <form:form commandName="item" id="chiNhanhForm" action="${formUrl}" method="post" autocomplete="off" validate="validate">
                    <div class="col-md-10">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                    <div class="col-sm-8">
                                        <form:input id="chiNhanh" path="pojo.chiNhanh" cssClass="required nohtml form-control" />
                                        <form:errors for="chiNhanh" path="pojo.chiNhanh" cssClass="error-inline-validate" />
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.ten_chi_nhanh" /></label>
                                    <div class="col-sm-8">
                                        <form:input id="name" path="pojo.name" cssClass="required nohtml form-control" />
                                        <form:errors for="name" path="pojo.name" cssClass="error-inline-validate" />
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <a class="btn btn-primary" onclick="javascript: submitForm();"><fmt:message key="label.update" /></a>
                                    <a class="btn btn-info" href="<c:url value="/admin/danhmucchinhanh.html" />"><fmt:message key="label.huy" /></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" name="crudaction" value="insert-update" />
                    <form:hidden path="pojo.chiNhanhId" />
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#menuDanhMucTab', '#dmChiNhanhTab');
    });

    function submitForm(){
        if($('#chiNhanhForm').valid()){
            bootbox.confirm('<fmt:message key="label.confirm_title" />', '<fmt:message key="label.confirm_operation_content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
                if(r){
                    $('#chiNhanhForm').submit();
                }
            });
        }
    }
</script>
</body>
</html>