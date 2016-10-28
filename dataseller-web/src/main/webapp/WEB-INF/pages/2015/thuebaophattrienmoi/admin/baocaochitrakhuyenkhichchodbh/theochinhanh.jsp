<%@ page import="com.benluck.vms.mobifonedataseller.security.util.SecurityUtils" %>
<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 3/2/15
  Time: 10:07 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="baocaochitrakhuyenkhichchodbh.headingpage" /></title>
    <meta name="heading" content="<fmt:message key="baocaochitrakhuyenkhichchodbh.headingpage" />"/>
    <link href="<c:url value="/themes/promotion/css/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
</head>
<c:set var="prefix" value="/baocao" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<security:authorize ifAnyGranted="TONGDAI">
    <c:set var="prefix" value="/tongdai" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/thuebaophattrienmoi/baocaochitrakhuyenkhich_cho_dbh_theochinhanh.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="baocaochitrakhuyenkhichchodbh.headingpage" /></h2>

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
                                    <label class="col-sm-2 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                    <div class="col-sm-8">
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
                        <div id="reportTableContainer" style="width: 100%;">
                            <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                           partialList="true" sort="external" size="${items.totalItems }" defaultsort="0"
                                           id="tableList" pagesize="${items.reportMaxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 1400px;">
                                <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 50px;" >
                                    ${tableList_rowNum + (page * Constants.REPORT_MAXPAGEITEMS)}
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" property="branch.branch_name" titleKey="label.chi_nhanh" class="nowrap" style="width: 150px;" />

                                <display:column headerClass="table_header_center" sortable="false" titleKey="label.so_tien_khuyen_khich" class="nowrap text-right" style="width: 150px;">
                                    <fmt:formatNumber type="number" value="${tableList.soTienKhuyenKhich}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="label.so_tien_da_chi" class="nowrap text-right" style="width: 150px;">
                                    <fmt:formatNumber type="number" value="${tableList.soTienDaChi}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="label.thoi_gian_phai_chi" class="nowrap text-right" style="width: 150px;">
                                    <fmt:formatDate pattern="${datePattern}" value="${tableList.thoiGianPhaiChi}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="label.thoi_gian_thuc_chi"  class="nowrap text-right" style="width: 150px;" >
                                    <fmt:formatDate pattern="${datePattern}" value="${tableList.thoiGianThucChi}" />
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
            setActiveMenu4Admin('#menuBaoCaoTBPTMTab', '#bcChiTraKhuyenKhichtheoDailyTab');

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
            "<th colspan='1' class='table_header_center nowrap'>Số tiền khuyến khích</th>" +
            "<th colspan='1' class='table_header_center nowrap'>Số tiền đã chi</th>" +
            "<th colspan='1' class='table_header_center nowrap'>Thời gian phải chi</th>" +
            "<th colspan='1' class='table_header_center nowrap'>Thời gian thực chi</th>"+
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
    selectFirstItemSelect2('#retailDealerMenu')
    selectFirstItemSelect2('#districtMenu')
    $('#fromDate').val('');
    $('#toDate').val('');
}

function export2Excel(){
    $('#crudaction').val('export');
    $('#reportForm').submit();
}
function ajaxGetDistrictList() {
    $('#districtMenu').find('option:not(:first-child)').remove();
    selectFirstItemSelect2('#districtMenu');
    $.ajax({
        url: '<c:url value="/ajax/thuebaotm/getByBranchId.html" />',
        type: 'get',
        dataType: 'json',
        data: {chiNhanh: $('#branchMenu').val()},
        success: function (res) {
            if (res.districtList != null) {
                $(res.districtList).each(function (index, el) {
                    $('#districtMenu').append("<option value=" + el.district_Id + ">" + el.district_name + "</option>");
                });
            }
        }
    });
}

function ajaxGetRetailDealerListByBranchId() {
    $('#retailDealerMenu').find('option:not(:first-child)').remove();
    selectFirstItemSelect2('#retailDealerMenu');
    $.ajax({
        url: '<c:url value="/ajax/thuebaotm/getRetailDealerListByBranchId.html" />',
        type: 'get',
        dataType: 'json',
        data: {branchId: $('#branchMenu').val()},
        success: function (res) {
            if (res.retailDealerList != null) {
                $(res.retailDealerList).each(function (index, el) {
                    $('#retailDealerMenu').append("<option value=" + el.dealer_Id + ">" + $.trim(el.dealer_code) + ' - ' + $.trim(el.dealer_name) + "</option>");
                });
            }
        }
    });
}
function ajaxGetRetailDealerList(){
    $('#retailDealerMenu').find('option:not(:first-child)').remove();
    selectFirstItemSelect2('#retailDealerMenu');
    var branchId = '';
<security:authorize ifNotGranted="ADMIN">
            branchId = '<%=SecurityUtils.getPrincipal().getBranchId()%>';
</security:authorize>
<security:authorize ifAnyGranted="ADMIN">
            branchId = $('#branchMenu').val();
</security:authorize>
    $.ajax({
        url: '<c:url value="/ajax/thuebaotm/getRetailDealerList.html" />',
        type: 'get',
        dataType: 'json',
        data: {branchId: branchId, districtId: $('#districtMenu').val()},
        success: function(res){
            if(res.retailDealerList != null){
                $(res.retailDealerList).each(function(index, el){
                    $('#retailDealerMenu').append("<option value=" +el.dealer_Id+ ">" + $.trim(el.dealer_code) + ' - ' + $.trim(el.dealer_name) +  "</option>");
                });
            }
        }
    });
}
</script>
</body>