<%--
  Created by IntelliJ IDEA.
  User: vovanphuc0810
  Date: 4/2/15
  Time: 11:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="cuahanggiaodich.lichsuxuatkho.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="cuahanggiaodich.lichsuxuatkho.heading_page" />"/>
</head>

<c:set var="prefix" value="/cuahangmobifone" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/qstudent/lichsuxuatkho.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="cuahanggiaodich.lichsuxuatkho.heading_page" /></h2>

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
                <form:form commandName="item" id="stockForm" action="${formUrl}" method="post" autocomplete="off">
                    <div class="col-md-10">
                        <div class="panel panel-default">
                            <security:authorize ifAnyGranted="ADMIN,CHINHANH">
                                <div class="panel-body">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                        <div class="col-sm-8">
                                            <form:select id="chiNhanhMenu" path="chiNhanhId" cssStyle="width: 250px;" cssClass="required nohtml" onchange="javascript: ajaxGetDepartmentList();">
                                                <option value=""><fmt:message key="label.select" /></option>
                                                <c:forEach items="${chiNhanhList}" var="chiNhanhVar">
                                                    <option <c:if test="${item.chiNhanhId eq chiNhanhVar.chiNhanhId}">selected="true"</c:if> value="${chiNhanhVar.chiNhanhId}">${chiNhanhVar.name}</option>
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label"><fmt:message key="label.ten_cua_hang" /></label>
                                        <div class="col-sm-8">
                                            <form:select id="cuaHangMenu" path="departmentId" cssStyle="width: 250px;" cssClass="required nohtml">
                                                <option value=""><fmt:message key="label.select" /></option>
                                                <c:forEach items="${departmentList}" var="department">
                                                    <option <c:if test="${item.departmentId eq department.departmentId}">selected="true"</c:if> value="${department.departmentId}">${department.name}</option>
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                    </div>
                                </div>
                            </security:authorize>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.tu_ngay" /></label>
                                    <div class="col-sm-8">
                                        <div class="input-append date" >
                                            <input name="fromDate" id="fromDate" class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.fromDate}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="fromDateIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.den_ngay" /></label>
                                    <div class="col-sm-8">
                                        <div class="input-append date" >
                                            <input name="toDate" id="toDate" class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.toDate}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="toDateIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <div class="col-sm-8">
                                        <a class="btn btn-info" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                                    </div>
                                </div>
                            </div>
                            <hr/>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <c:if test="${not empty items.listResult && fn:length(items.listResult) gt 0}">
                        <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                       partialList="true" sort="external" size="${items.totalItems }" defaultsort="0"
                                       id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 100%;">
                            <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                                ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                            </display:column>
                            <display:column headerClass="table_header nowrap" sortable="false" titleKey="label.ngay_xuat" class="nowrap" style="width: 25%;" >
                                <fmt:formatDate value="${tableList.createdDate}" pattern="${datePattern}" />
                            </display:column>
                            <display:column headerClass="table_header nowrap" sortable="false" property="nguoi_xuat" titleKey="label.nguoi_xuat" style="width: 40%;" />
                            <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.so_luong_xuat" class="text-center" style="width: 30%;" >
                                <fmt:formatNumber type="number" value="${tableList.quantity}" />
                            </display:column>
                            <display:setProperty name="paging.banner.item_name"><fmt:message key="lichsugiaonhanhang.label.display_table.gift_delivery_time"/></display:setProperty>
                            <display:setProperty name="paging.banner.items_name"><fmt:message key="lichsugiaonhanhang.label.display_table.gift_delivery_time"/></display:setProperty>
                        </display:table>
                    </c:if>
                    <input type="hidden" name="crudaction" value="search" />
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#menuQStudentTab', '#lichSuXuatKhoTab');

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

    function submitForm(){
        var isAdmin = false;
    <security:authorize ifAnyGranted="ADMIN,CHINHANH">
                isAdmin = true;
    </security:authorize>
        if(!isAdmin){
            $('#stockForm').submit();
        }else{
            if($('#stockForm').valid()){
                $('#stockForm').submit();
            }
        }
    }

    <security:authorize ifAnyGranted="ADMIN">
            function ajaxGetDepartmentList(){
                $('#cuaHangMenu').find('option:not(:first-child)').remove();
                selectFirstItemSelect2('#cuaHangMenu');
                if($('#chiNhanhMenu').val() != ''){
                    $.ajax({
                        url: '<c:url value="/ajax/cuahangmobifone/getDeprtmentListByChiNhanh.html" />',
                        type: 'get',
                        dataType: 'json',
                        data: {chiNhanh: $('#chiNhanhMenu').val()},
                        success: function(res){
                            if(res.departmentList != null){
                                $(res.departmentList).each(function(index, el){
                                    $('#cuaHangMenu').append("<option value=" +el.departmentId+ ">" +el.name+ "</option>");
                                });
                            }
                        }
                    });
                }
            }
            </security:authorize>
</script>
</body>
</html>