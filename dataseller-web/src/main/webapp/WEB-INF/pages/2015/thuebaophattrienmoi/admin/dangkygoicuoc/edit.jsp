<%--
  Created by IntelliJ IDEA.
  User: vovanphuc0810
  Date: 2/6/15
  Time: 9:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="dangkygoicuoc2015.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="dangkygoicuoc2015.heading_page" />"/>
</head>
<c:url var="backUrl" value="/admin/userList.html"/>
<c:url var="formUrl" value="/admin/editUserInfo.html"/>

<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="dangkygoicuoc2015.heading_page" /></h2>

</div>
<div class="contentpanel">

    <div class="panel panel-default">
        <div class="panel-heading">
            <div class="panel-btns">
                <a class="btn btn-primary" style="margin-top:-10px" href="${importUrl}"> <fmt:message key="label.button.import"/></a>
            </div>
            <h4 class="panel-title">
                <fmt:message key="dangkygoicuoc2015.heading_page" />
            </h4>
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
                    <div class="col-md-10">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.so_thue_bao" /></label>
                                    <div class="col-sm-8">
                                        <form:input path="pojo.soThueBao" cssClass="form-control" />
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.goi_cuoc" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="userGroupMenu" path="pojo.goiCuoc" >
                                            <option value=""><fmt:message key="label.select" /></option>
                                            <c:forEach items="${dangKyGoiCuoc2015List}" var="goiCuoc">
                                                <option value="${goiCuoc.goiCuoc}">${goiCuoc.goiCuoc}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.ngay_hieu_luc_tu" /></label>
                                    <div class="col-sm-8">
                                        <div class="input-append date" >
                                            <input name="fromDate" id="fromDate" class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.pojo.ngayHieuLuc}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="fromDateIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.ngay_hieu_luc_den" /></label>
                                    <div class="col-sm-8">
                                        <div class="input-append date" >
                                            <input name="toDate" id="toDate" class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.pojo.ngayHetHan}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="toDateIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" name="crudaction" value="insert-update" />
                    <form:hidden path="pojo.soThueBao" />
                </form:form>
            </div>
        </div>
        <br />
        <div class="panel-footer">
            <div class="row">
                <div class="col-sm-6 col-sm-offset-2">
                    <a class="btn btn-info">Làm mới</a>
                    <button id="btnSave" class="btn btn-info">
                        <c:choose>
                            <c:when test="${not empty item.pojo.soThueBao}"><fmt:message key="label.update" /></c:when>
                            <c:otherwise><fmt:message key="label.save" /></c:otherwise>
                        </c:choose>
                    </button>&nbsp;
                    <a href="<c:url value="/admin/userList.html"/>" class="btn btn-info"><fmt:message key="label.huy" /></a>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        var fromDateVar = $("#fromDate").datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    fromDateVar.hide();
                }).data('datepicker');

        $('#fromDateIcon').click(function () {
            $('#signedDate').focus();
            return true;
        });

        var toDateVar = $("#toDate").datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    toDateVar.hide();
                }).data('datepicker');

        $('#toDateIcon').click(function () {
            $('#signedDate').focus();
            return true;
        });
    });
</script>
</body>