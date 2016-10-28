<%@ page import="com.benluck.vms.mobifonedataseller.security.util.SecurityUtils" %>
<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 3/2/15
  Time: 10:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="baocaotonghopchuongtrinh.headingpage" /></title>
    <meta name="heading" content="<fmt:message key="baocaotonghopchuongtrinh.headingpage" />"/>
    <link href="<c:url value="/themes/promotion/css/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
</head>
<c:set var="prefix" value="/baocao" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/thuebaophattrienmoi/baocaotonghop_ketquachuongtrinh_theochinhanh.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="baocaotonghopchuongtrinh.headingpage" /></h2>

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
                                    <label class="col-sm-3 control-label"><fmt:message key="label.loai_goi_KM" /></label>
                                    <div class="col-sm-7">
                                        <form:select id="promPackageMenu" path="pojo.promPackage.package_Id" cssStyle="width: 250px;">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${promPackage}" var="promPackage">
                                                <option <c:if test="${item.pojo.promPackage.package_Id eq promPackage.package_Id}">selected="true"</c:if> value="${promPackage.package_Id}">${promPackage.package_Name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                    <div class="col-sm-7">
                                        <form:select id="branchMenu" path="pojo.branch.branch_Id" cssStyle="width: 250px;" onchange="javascript: ajaxGetDistrictList(); ajaxGetRetailDealerListByBranchId();">
                                            <security:authorize ifAnyGranted="ADMIN,BAOCAO">
                                                <option value=""><fmt:message key="label.all" /></option>
                                            </security:authorize>
                                            <c:forEach items="${branchList}" var="branch">
                                                <option <c:if test="${item.pojo.branch.branch_Id eq branch.branch_Id}">selected="true"</c:if> value="${branch.branch_Id}">${branch.branch_name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><fmt:message key="label.ngay_giao_dich_tu" /></label>
                                    <div class="col-sm-7">
                                        <div class="input-append date" >
                                            <input name="fromDate" id="fromDate"  class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.fromDate}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="fromDateIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><fmt:message key="label.ngay_giao_dich_den" /></label>
                                    <div class="col-sm-7">
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
                        <div id="reportTableContainer" style="width: 100%;">
                            <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                           partialList="true" sort="external" size="${items.totalItems }" defaultsort="0"
                                           id="tableList" pagesize="${items.reportMaxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 2050px;">
                                <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 50px;" >
                                    ${tableList_rowNum + (page * Constants.REPORT_MAXPAGEITEMS)}
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" property="branch.branch_name" titleKey="label.chi_nhanh" class="nowrap" style="width: 150px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="SLDBHThamGia" titleKey="label.sl_dbh_tham_gia" class="nowrap text-center" style="width: 150px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongBTGMoi" titleKey="label.so_luong_btg_moi" class="nowrap text-center" style="width: 150px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongBTGTon" titleKey="label.so_luong_btg_ton" class="nowrap text-center" style="width: 150px;" >
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="label.tong" class="nowrap text-center" style="width: 200px;" >
                                    <fmt:formatNumber type="number" value="${tableList.soLuongBTGMoi + tableList.soLuongBTGTon}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoiT50_s" titleKey="label.T50" class="text-center" style="width: 150px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoi3T50_s" titleKey="label.3T50" class="text-center" style="width: 150px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoiT100_s" titleKey="label.T100" class="text-center" style="width: 150px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoi3T100_s" titleKey="label.3T100" class="text-center" style="width: 150px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoiT50_CT" titleKey="label.T50" class="text-center" style="width: 150px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoi3T50_CT" titleKey="label.3T50" class="text-center" style="width: 150px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoiT100_CT" titleKey="label.T100" class="text-center" style="width: 150px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoi3T100_CT" titleKey="label.3T100" class="text-center" style="width: 150px;" />
                                <display:column headerClass="table_header_center" sortable="false" titleKey="label.so_luong_btg_moi" class="nowrap text-center" style="width: 150px;" >
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.doanhThuBTGMoi}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="label.so_luong_btg_ton" class="nowrap text-center" style="width: 150px;" >
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.doanhThuBTGTon}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="label.tong" class="nowrap text-center" style="width: 200px;" >
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.doanhThuBTGMoi + tableList.doanhThuBTGTon}" />
                                </display:column>

                                <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                            </display:table>
                        </div>
                        <div id="linksContainer">

                        </div>
                    </c:if>
                    <input id="crudaction" type="hidden" name="crudaction" />
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/scripts/jquery/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#tbptm2015ReportTongHopKQCTTab');

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
        $('#reportTableContainer').mCustomScrollbar({axis:"x"});
        rebuildTableHeaders();
        checkMovePageLinks();
    });

    function rebuildTableHeaders(){
        $('#tableList thead tr').remove();
        $('#tableList thead').html("<tr>" +
                "<th rowspan='2' class='table_header_center '>STT</th>" +
                "<th rowspan='2' class='table_header_center '>Chi nhánh</th>" +
                "<th rowspan='2' class='table_header_center '>SL DBH tham gia</th>" +
                "<th colspan='3' class='table_header_center nowrap'>Số lượng BTG đăng ký gói thành công</th>" +
                "<th colspan='4' class='table_header_center nowrap'>Số lượng gói KM đăng ký thành công</th>" +
                "<th colspan='4' class='table_header_center nowrap'>Số lượng gói KM được chi trả</th>" +
                "<th colspan='4' class='table_header_center nowrap'>Doanh thu thông tin</th>" +
                "</tr>" +
                "<tr>" +
                "<th class='table_header_center '>BTG mới</th>" +
                "<th class='table_header_center '>BTG tồn</th>" +
                "<th class='table_header_center '>Tổng</th>" +
                "<th class='table_header_center '>T50</th>" +
                "<th class='table_header_center '>3T50</th>" +
                "<th class='table_header_center '>T100</th>" +
                "<th class='table_header_center '>3T100</th>" +
                "<th class='table_header_center '>T50</th>" +
                "<th class='table_header_center '>3T50</th>" +
                "<th class='table_header_center '>T100</th>" +
                "<th class='table_header_center '>3T100</th>" +
                "<th class='table_header_center '>BTG mới</th>" +
                "<th class='table_header_center '>BTG tồn</th>" +
                "<th class='table_header_center '>Tổng</th>" +
                "</tr>");
    }

    function checkMovePageLinks(){
        if(${not empty items.listResult}){
            $('#reportTableContainer .pagebanner').appendTo('#linksContainer');
            $('#reportTableContainer .pagelinks').appendTo('#linksContainer');
        }
    }

    function filterForm(){
        $('#crudaction').val('search');
        $('#reportForm').submit();
    }

    function reset(){
        selectFirstItemSelect2('#promPackageMenu')
        selectFirstItemSelect2('#branchMenu')
        $('#fromDate').val('');
        $('#toDate').val('');
    }

    function export2Excel(){
        $('#crudaction').val('export');
        $('#reportForm').submit();
    }
</script>
</body>