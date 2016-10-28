<%--
  Created by IntelliJ IDEA.
  User: vovanphuc0810
  Date: 4/2/15
  Time: 11:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="cuahanggiaodich.tracuukhocuahangkhac.heading_page" /></title>
    <meta name="heading" content="<fmt:message key="cuahanggiaodich.tracuukhocuahangkhac.heading_page" />"/>
</head>

<c:set var="prefix" value="/cuahangmobifone" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<security:authorize ifAnyGranted="TONGDAI">
    <c:set var="prefix" value="/tongdai" />
</security:authorize>

<c:url var="formUrl" value="${prefix}/qstudent/khohangcuahangkhac.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="cuahanggiaodich.tracuukhocuahangkhac.heading_page" /></h2>

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
                <form:form commandName="item" id="searcForm" action="${formUrl}" method="post" autocomplete="off">
                    <div class="col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="branchMenu" path="pojo.branch.branch_Id" cssStyle="width: 250px;" onchange="javascript: ajaxGetDepartmentList();">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${branchList}" var="branchVar">
                                                <option <c:if test="${item.pojo.branch.branch_Id eq branchVar.branch_Id}">selected="true"</c:if> value="${branchVar.branch_Id}">${branchVar.branch_name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.ten_cua_hang" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="cuaHangMenu" path="pojo.department.departmentId" cssStyle="width: 250px;">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${departmentList}" var="department">
                                                <option <c:if test="${item.pojo.department.departmentId eq department.departmentId}">selected="true"</c:if> value="${department.departmentId}">${department.name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.trang_thai_kho" /></label>
                                    <div class="col-sm-8">
                                        <label><form:radiobutton id="radioAll" path="pojo.trang_thai_kho" value="-1"/>&nbsp;<fmt:message key="label.all" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="pojo.trang_thai_kho" value="0"/>&nbsp;<fmt:message key="label.het_qua" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<span><label><form:radiobutton path="pojo.trang_thai_kho" value="1"/>&nbsp;<fmt:message key="label.con_qua" /></label></span>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <a class="btn btn-info" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                                    <a class="btn btn-info" onclick="javascript: reset();"><fmt:message key="label.reset" /></a>
                                    <c:if test="${not empty items.listResult}">
                                        <a class="btn btn-info" onclick="javascript: export2Excel();"><fmt:message key="label.export" /></a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <c:if test="${not empty items.listResult}">
                        <div id="tableList_wrapper" class="dataTables_wrapper" role="grid" >
                            <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formlUrl}"
                                           partialList="true" sort="external" size="${items.totalItems}" defaultsort="1"
                                           id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em;">
                                <c:set var="highLight" value="" />
                                <c:if test="${tableList.total gt 0}">
                                    <c:set var="highLight" value="hightLight" />
                                </c:if>
                                <display:column headerClass="table_header_center nowrap" sortable="false" class="${highLight} text-center" style="width: 5%;" titleKey="label.stt" >
                                    ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                </display:column>
                                <display:column headerClass="table_header_center nowrap" sortable="false" property="branch.branch_name" sortName="chiNhanh" class="${highLight} text-center" style="width: 20%;" titleKey="label.chi_nhanh" />
                                <display:column headerClass="table_header nowrap" property="department.name" sortable="false" titleKey="label.ten_cua_hang" class="${highLight}" style="width: 30%;"/>
                                <display:column headerClass="table_header nowrap" property="gift.name" sortable="false" titleKey="label.phieu_qua" class="${highLight}" style="width: 30%;"/>
                                <display:column headerClass="table_header_center nowrap" sortName="total" sortable="false" titleKey="label.trang_thai_kho" class="${highLight} text-center" style="width: 10%;">
                                    <c:choose>
                                        <c:when test="${tableList.total gt 0}">
                                            <strong><fmt:message key="label.con_qua" /></strong>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="label.het_qua" />
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column headerClass="table_header_center nowrap" sortName="total" sortable="false" titleKey="label.so_luong" class="${highLight} text-center" style="width: 10%;" >
                                    <fmt:formatNumber type="number" value="${tableList.total}" />
                                </display:column>
                                <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.cua_hang" /></display:setProperty>
                                <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.cua_hang" /></display:setProperty>
                            </display:table>
                        </div>
                    </c:if>
                    <input id="crudaction" type="hidden" name="crudaction" />
                    <form:hidden id="branch" path="pojo.chiNhanh" />
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#qStudent2015KhoHangCuaHangKhac');
    });

    function ajaxGetDepartmentList(){
        $('#branch').val($('#branchMenu').val());
        $('#cuaHangMenu').find('option:not(:first-child)').remove();
        selectFirstItemSelect2('#cuaHangMenu');
        if($('#branchMenu').val() != ''){
            $.ajax({
                url: '<c:url value="/ajax/cuahangmobifone/qstudent/getDeprtmentListByBranchIdAndCtCode.html" />',
                type: 'get',
                dataType: 'json',
                data: {branch: $('#branchMenu').val()},
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

    function submitForm(){
        $('#crudaction').val('search');
        $('#searcForm').submit();
    }

    function reset(){
        selectFirstItemSelect2('#branchMenu');
        selectFirstItemSelect2('#cuaHangMenu');
    }
    function export2Excel(){
        $('#crudaction').val('export');
        $('#searcForm').submit();
    }
</script>
</body>