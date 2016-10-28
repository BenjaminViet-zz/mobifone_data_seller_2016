<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2/26/15
  Time: 10:17 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="baocaotinnhandangkygoi.heading_page" /></title>
    <meta name="heading" content="<fmt:message key="baocaotinnhandangkygoi.heading_page" />"/>
</head>
<c:set var="prefix" value="/baocao" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/thuebaophattrienmoi/baocaotinnhandangkygoidbh.html"/>

<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="baocaotinnhandangkygoi.heading_page" /></h2>

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
                <form:form commandName="item" id="reportForm" action="${formUrl}" method="post" autocomplete="off" validate="validate">
                    <div class="col-md-10">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.loai_goi_KM" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="packageMenu" path="pojo.goiCuoc.package_Id" cssStyle="width: 250px;">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${promPackage}" var="promPackage">
                                                <option <c:if test="${item.pojo.goiCuoc.package_Id eq promPackage.package_Id}">selected="true"</c:if> value="${promPackage.package_Id}">${promPackage.package_Name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
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
                                        <a class="btn btn-info" onclick="javascript: filterForm();"><fmt:message key="label.search" /></a>
                                        <a class="btn btn-info" onclick="javascript: reset();"><fmt:message key="label.reset" /></a>
                                        <c:if test="${not empty items.listResult}">
                                            <a class="btn btn-info" onclick="javascript: export2Excel();"><fmt:message key="label.export" /></a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <hr/>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <c:if test="${not empty items.listResult}">
                        <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                       partialList="true" sort="external" size="${items.totalItems }" defaultsort="0"
                                       id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 100%;">
                            <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                                ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                            </display:column>
                            <display:column headerClass="table_header_center nowrap" property="soEZDangKy" titleKey="label.so_ez_dang_ky" class="text-center" style="width: 15%;" />
                            <display:column headerClass="table_header_center nowrap" property="thueBaoKH" titleKey="label.thue_bao_kh" class="text-center nowrap" style="width: 15%;" />
                            <display:column headerClass="table_header_center nowrap"  property="goiCuoc.package_Name" titleKey="label.ten_goi" class="nowrap" style="width: 15%;" />
                            <display:column headerClass="table_header_center nowrap" titleKey="label.thoi_gian_dang_ky" class="text-center" style="width: 15%;">
                                <fmt:formatDate value="${tableList.thoiGianDK}" pattern="${dateTimePattern}" />
                            </display:column>
                            <display:column headerClass="table_header_center nowrap" titleKey="label.muc_khuyen_khich" class="nowrap text-right" style="width: 15%;" >
                                <fmt:formatNumber type="number" value="${tableList.mucKhuyenKhich}" maxFractionDigits="2"/>
                            </display:column>
                            <display:column headerClass="table_header_center nowrap"  property="serial" titleKey="label.serial" class="nowrap" style="width: 10%;" />
                            <display:column headerClass="table_header_center" sortable="false" titleKey="label.trang_thai" class="text-right" style="width: 15%;" >
                                <c:choose>
                                    <c:when test="${not empty tableList.transStatus && tableList.transStatus eq Constants.TINH_TRANG_KHONG_THANH_CONG}">
                                        ${tableList.transError}
                                    </c:when>
                                    <c:when test="${not empty tableList.transStatus && tableList.transStatus eq Constants.TINH_TRANG_THANH_CONG}">
                                        <fmt:message key="label.thanh_cong"/>
                                    </c:when>
                                </c:choose>
                            </display:column>

                            <display:setProperty name="paging.banner.item_name"><fmt:message key="cuahanggiaodich.baocaotheokhcn.giao_dich_qua"/></display:setProperty>
                            <display:setProperty name="paging.banner.items_name"><fmt:message key="cuahanggiaodich.baocaotheokhcn.giao_dich_qua"/></display:setProperty>
                        </display:table>
                    </c:if>
                    <input id="crudaction" type="hidden" name="crudaction" />
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#tbptm2015ReportTinNhanDangKyGoiDBHTab');

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

    function filterForm(){
        $('#crudaction').val('search');
        $('#reportForm').submit();
    }

    function reset(){
        selectFirstItemSelect2('#packageMenu');
        $('#fromDate').val('');
        $('#toDate').val('');
    }

    function export2Excel(){
        $('#crudaction').val('export');
        $('#reportForm').submit();
    }
</script>
</body>
