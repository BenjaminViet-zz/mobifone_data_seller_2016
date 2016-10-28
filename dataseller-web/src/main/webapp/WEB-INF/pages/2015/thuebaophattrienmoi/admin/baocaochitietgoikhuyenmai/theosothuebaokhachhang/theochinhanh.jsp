<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 3/2/15
  Time: 2:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="baocaochitietgoikhuyenmai.headingpage" /></title>
    <meta name="heading" content="<fmt:message key="baocaochitietgoikhuyenmai.headingpage" />"/>
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
<c:url var="formUrl" value="${prefix}/thuebaophattrienmoi/baocaochitietgoikm_theoKhachHang_theochinhanh.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="baocaochitietgoikhuyenmai.headingpage" /></h2>

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
                                           id="tableList" pagesize="${items.reportMaxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 1500px;">
                                <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 50px;" >
                                    ${tableList_rowNum + (page * Constants.REPORT_MAXPAGEITEMS)}
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" property="branch.branch_name" titleKey="label.chi_nhanh" class="nowrap" style="width: 150px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoiT50_s" titleKey="label.T50" class="text-right" style="width: 50px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoi3T50_s" titleKey="label.3T50" class="text-right" style="width: 50px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoiT100_s" titleKey="label.T100" class="text-right" style="width: 50px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoi3T100_s" titleKey="label.3T100" class="text-right" style="width: 50px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoiT50_CT" titleKey="label.T50" class="text-right" style="width: 50px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoi3T50_CT" titleKey="label.3T50" class="text-right" style="width: 50px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoiT100_CT" titleKey="label.T100" class="text-right" style="width: 50px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoi3T100_CT" titleKey="label.3T100" class="text-right" style="width: 50px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoiT50_KCT" titleKey="label.T50" class="nowrap text-right" style="width: 50px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoi3T50_KCT" titleKey="label.3T50" class="nowrap text-right" style="width: 50px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoiT100_KCT" titleKey="label.T100" class="nowrap text-right" style="width: 50px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="soLuongGoi3T100_KCT" titleKey="label.3T100" class="nowrap text-right" style="width: 50px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="viTriDangKy" titleKey="label.vi_tri_dang_ky" class="nowrap text-right" style="width: 150px;" />

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
        setActiveMenu4Admin('#menuBaoCaoTBPTMTab', '#bcTheoKhachHangTheoChiNhanhTab');

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
                "<th colspan='4' class='table_header_center nowrap '>Số lượng gói KM đăng ký thành công</th>" +
                "<th colspan='4' class='table_header_center nowrap '>Số lượng gói KM được chi trả</th>" +
                "<th colspan='5' class='table_header_center nowrap '>Số lượng gói KM không được chi trả</th>" +
                "</tr>" +
                "<tr>" +
                "<th class='table_header_center '>T50</th>" +
                "<th class='table_header_center '>3T50</th>" +
                "<th class='table_header_center '>T100</th>" +
                "<th class='table_header_center '>3T100</th>" +
                "<th class='table_header_center '>T50</th>" +
                "<th class='table_header_center '>3T50</th>" +
                "<th class='table_header_center '>T100</th>" +
                "<th class='table_header_center '>3T100</th>" +
                "<th class='table_header_center '>T50</th>" +
                "<th class='table_header_center '>3T50</th>" +
                "<th class='table_header_center '>T100</th>" +
                "<th class='table_header_center '>3T100</th>" +
                "<th class='table_header_center '>Vị trí đăng ký</th>" +
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