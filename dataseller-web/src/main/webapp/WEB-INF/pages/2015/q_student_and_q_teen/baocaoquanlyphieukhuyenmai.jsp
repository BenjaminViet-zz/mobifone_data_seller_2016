<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="cuahanggiaodich.baocaoquanlypkm.heading_page" /></title>
    <meta name="heading" content="<fmt:message key="cuahanggiaodich.baocaoquanlypkm.heading_page" />"/>
</head>

<c:set var="prefix" value="/cuahangmobifone" />
<security:authorize ifAnyGranted="TONGDAI">
    <c:set var="prefix" value="/tongdai" />
</security:authorize>
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<security:authorize ifAnyGranted="BAOCAO">
    <c:set var="prefix" value="/baocao" />
</security:authorize>

<c:url var="formUrl" value="${prefix}/qstudent/quanlyphieukhuyenmai.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="cuahanggiaodich.baocaoquanlypkm.heading_page" /></h2>

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
                <form:form commandName="item" id="searchForm" action="${formUrl}" method="post" autocomplete="off">
                    <div class="col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="branchMenu" path="pojo.branch.branch_Id" cssStyle="width: 250px;" onchange="javascript: ajaxGetDepartmentList();">
                                            <security:authorize ifAnyGranted="ADMIN,TONGDAI,BAOCAO">
                                                <option value=""><fmt:message key="label.all" /></option>
                                            </security:authorize>
                                            <c:forEach items="${branchList}" var="chiNhanhVar">
                                                <option <c:if test="${item.pojo.branch.branch_Id eq chiNhanhVar.branch_Id}">selected="true"</c:if> value="${chiNhanhVar.branch_Id}">${chiNhanhVar.branch_name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.ten_cua_hang" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="cuaHangMenu" path="pojo.departmentId" cssStyle="width: 250px;">
                                            <security:authorize ifAnyGranted="ADMIN,CHINHANH,TONGDAI">
                                                <option value=""><fmt:message key="label.all" /></option>
                                            </security:authorize>
                                            <c:forEach items="${departmentList}" var="department">
                                                <option <c:if test="${item.pojo.departmentId eq department.departmentId}">selected="true"</c:if> value="${department.departmentId}">${department.name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <a class="btn btn-info" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                                    <a class="btn btn-info" onclick="javascript: reset();"><fmt:message key="label.reset" /></a>
                                    <c:if test="${not empty reportDataList}">
                                        <a class="btn btn-info" onclick="javascript: export2Excel();"><fmt:message key="label.export" /></a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <c:if test="${not empty reportDataList}">
                        <table class="tableVms table-hover">
                            <tr>
                                <th class="table_header nowrap"><fmt:message key="label.don_vi" /></th>
                                <th class="table_header_center nowrap"><fmt:message key="label.phieu_qua" /></th>
                                <th class="table_header_center nowrap"><fmt:message key="label.so_luong_phieu_da_doi" /></th>
                                <th class="table_header_center nowrap"><fmt:message key="label.ton" /></th>
                            </tr>
                            <c:set var="totalSoLuongNhap" value="${0}" />
                            <c:set var="totalSoLuongPhieuDaDoi" value="${0}" />
                            <c:set var="totalTon" value="${0}" />
                            <c:set var="index" value="${0}" />
                            <c:forEach items="${reportDataList}" var="chiNhanhVar">
                                <tr class="<c:if test="${index % 2 eq 0}">even</c:if> <c:if test="${index % 2 ne 0}">odd</c:if>">
                                    <c:set var="index" value="${index + 1}" />
                                    <td class="nowrap"><strong>${chiNhanhVar.branch_name}</strong></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <c:if test="${chiNhanhVar.department != null}">
                                        <c:forEach items="${chiNhanhVar.department}" var="cuaHang">
                                            <tr class="<c:if test="${index % 2 eq 0}">even</c:if> <c:if test="${index % 2 ne 0}">odd</c:if>">
                                                <c:set var="index" value="${index + 1}" />
                                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${cuaHang.name}</td>
                                                <td class="text-center">${cuaHang.gift.name}</td>
                                                <td class="text-center">${cuaHang.soLuongPhieuDaDoi}</td>
                                                <td class="text-center">${cuaHang.ton}</td>
                                                <c:set var="totalSoLuongPhieuDaDoi" value="${totalSoLuongPhieuDaDoi + cuaHang.soLuongPhieuDaDoi}" />
                                                <c:set var="totalTon" value="${totalTon + cuaHang.ton}" />
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                </tr>
                            </c:forEach>
                            <c:set var="index" value="${index + 1}" />
                            <tr class="<c:if test="${index % 2 eq 0}">even</c:if> <c:if test="${index % 2 ne 0}">odd</c:if>">
                                <td><strong><fmt:message key="label.tong" /></strong></td>
                                <td class="text-center"><strong></strong></td>
                                <td class="text-center"><strong>${totalSoLuongPhieuDaDoi}</strong></td>
                                <td class="text-center"><strong>${totalTon}</strong></td>
                            </tr>
                        </table>
                    </c:if>
                    <input id="crudaction" type="hidden" name="crudaction" />
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#qStudent2015ReportQuanLyPhieuKMTab');
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
        $('#searchForm').submit();
    }


    function export2Excel(){
        $('#crudaction').val('export');
        $('#searchForm').submit();
    }

    function reset(){
        selectFirstItemSelect2('#branchMenu');
        selectFirstItemSelect2('#cuaHangMenu');
    }
</script>
</body>