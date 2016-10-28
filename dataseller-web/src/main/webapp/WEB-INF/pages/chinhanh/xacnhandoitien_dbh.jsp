<%@ page import="com.benluck.vms.mobifonedataseller.security.util.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="chinhanh.doi_tien_dbh.hading_page" /></title>
    <meta name="heading" content="<fmt:message key="chinhanh.doi_tien_dbh.hading_page" />"/>
    <style>
        .tableVms th.order1 a {
             padding-right: 0px;
        }
    </style>
</head>
<c:set var="prefix" value="/chinhanh" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/chikhuyenkhichdbh.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="chinhanh.doi_tien_dbh.hading_page" /></h2>

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
                <form:form commandName="item" id="searchForm" action="${formUrl}" method="post" autocomplete="off" validate="validate">
                    <div class="col-md-14">
                        <div class="panel panel-default">
                            <security:authorize ifAnyGranted="ADMIN">
                                <div class="panel-body">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                        <div class="col-sm-8">
                                            <form:select id="branchMenu" path="branchId" cssStyle="width: 250px;" onchange="javascript: ajaxGetDistrictList(); ajaxGetRetailDealerListByBranchId();">
                                                <option value=""><fmt:message key="label.all" /></option>
                                                <c:forEach items="${branchList}" var="branch">
                                                    <option <c:if test="${item.branchId eq branch.branch_Id}">selected="true"</c:if> value="${branch.branch_Id}">${branch.branch_name}</option>
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                    </div>
                                </div>
                            </security:authorize>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.quan_huyen" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="districtMenu" path="district.district_Id" cssStyle="width: 150px;" onchange="javascript: ajaxGetRetailDealerList();">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${districtList}" var="district">
                                                <option <c:if test="${item.district.district_Id eq district.district_Id}">selected="true"</c:if> value="${district.district_Id}">${district.district_name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.diembanhang" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="retailDealerMenu" path="dealer_Id" cssStyle="width: 250px;">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${retailDealerList}" var="retailDealer">
                                                <option <c:if test="${item.dealer_Id eq retailDealer.dealer_Id}">selected="true"</c:if> value="${retailDealer.dealer_Id}">${fn:trim(retailDealer.dealer_code)} - ${fn:trim(retailDealer.dealer_name)}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.thang" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="monthList" path="cycle" cssStyle="width: 250px;">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${monthList}" var="month">
                                                <option <c:if test="${item.cycle eq month}">selected="true"</c:if> value="${month}"><fmt:message key="label.thang" />&nbsp;${month}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.trang_thai_chot_ky" /></label>
                                    <div class="col-sm-8">
                                        <label><form:radiobutton path="trang_thai_chot_ky" value="-1"/>&nbsp;<fmt:message key="label.all" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="trang_thai_chot_ky" value="${Constants.DEALER_ACCONT_ACTION_DA_CHOT_KY}"/>&nbsp;<fmt:message key="label.da_chot_ky" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="trang_thai_chot_ky" value="${Constants.DEALER_ACCONT_ACTION_CHUA_CHOT_KY}"/>&nbsp;<fmt:message key="label.chua_chot_ky" /></label>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.trang_thai_doi_tien_1" /></label>
                                    <div class="col-sm-8">
                                        <label><form:radiobutton path="trang_thai_doi_tien" value="-1"/>&nbsp;<fmt:message key="label.all" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="trang_thai_doi_tien" value="${Constants.DEALER_ACCOUNT_ACTION_DA_DOI_TIEN}"/>&nbsp;<fmt:message key="label.da_doi_tien" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="trang_thai_doi_tien" value="${Constants.DEALER_ACCOUNT_ACTION_CHUA_DOI_TIEN}"/>&nbsp;<fmt:message key="label.chua_doi_tien" /></label>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.trang_thai_du_dk" /></label>
                                    <div class="col-sm-8">
                                        <label><form:radiobutton path="trang_thai_du_dk_doi_tien" value="-1"/>&nbsp;<fmt:message key="label.all" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="trang_thai_du_dk_doi_tien" value="${Constants.TRANG_THAI_DOI_TIEN_DAT_KPP}"/>&nbsp;<fmt:message key="label.dat" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="trang_thai_du_dk_doi_tien" value="${Constants.TRANG_THAI_DOI_TIEN_KHONG_DAT_KPP}"/>&nbsp;<fmt:message key="label.khong_dat" /></label>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"></label>
                                    <div class="col-sm-8">
                                        <a class="btn btn-info" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                                        <c:if test="${not empty items.listResult}">
                                            <a class="btn btn-info" onclick="javascript: export2Excel();"><fmt:message key="label.export" /></a>
                                            <security:authorize ifAnyGranted="CHINHANH">
                                                <a class="btn btn-info" onclick="javascript: doiTien();"><fmt:message key="label.doi_tien" /></a>
                                            </security:authorize>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
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
                            <display:column headerClass="table_header_center nowrap" sortable="false" title="<input type=\"checkbox\" name=\"allCheck\" id=\"allCheck\" onclick=\"checkAll('searchForm', 'checkList', this)\">" class="text-center" style="width: 5%;">
                                <c:if test="${(not empty tableList.cycle_lock_status && tableList.cycle_lock_status eq Constants.DEALER_ACCONT_ACTION_DA_CHOT_KY)
                                                && (empty tableList.exchange_money_status || tableList.exchange_money_status eq Constants.DEALER_ACCOUNT_ACTION_CHUA_DOI_TIEN)
                                                && (empty tableList.condition_status || tableList.condition_status eq Constants.DEALER_ACCOUNT_ACTION_DAT_DK_DOI_TIEN)}">
                                    <fmt:formatDate var="cycle" value="${tableList.cycle}" pattern="MM" />
                                    <input type="checkbox" name="checkList" value="${tableList.dealer.dealer_Id}$${cycle}" onclick="checkAllIfOne('searchForm', 'checkList', this, 'allCheck')">
                                </c:if>
                            </display:column>
                            <display:column headerClass="table_header" sortable="false" property="branch_Name" titleKey="label.chi_nhanh" class="nowrap" style="width: 10%;" />
                            <display:column headerClass="table_header" sortable="false" property="tenQuanHuyen" titleKey="label.quan_huyen" class="nowrap" style="width: 10%;" />
                            <display:column headerClass="table_header" sortable="false" property="dealer.dealer_code" titleKey="label.ma_diem_ban_hang" style="width: 10%;" />
                            <display:column headerClass="table_header" sortable="false" property="dealer.dealer_name" titleKey="label.ten-diem_ban_hang" style="width: 10%;" />
                            <display:column headerClass="table_header_center" sortable="true" sortName="cycle_lock_status" titleKey="label.ky" class="text-center" style="width: 10%;">
                                <fmt:formatDate value="${tableList.cycle}" pattern="MM" />/<fmt:formatDate value="${tableList.cycle}" pattern="YYYY" />
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" titleKey="label.so_tien_duoc_doi" class="text-center nowrap" style="width: 10%;" >
                                <fmt:formatNumber type="number" value="${tableList.exchange_amount}" maxFractionDigits="2" />
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" titleKey="label.trang_thai_chot_ky" class="text-center" style="width: 10%;" >
                                <c:choose>
                                    <c:when test="${not empty tableList.cycle_lock_status && tableList.cycle_lock_status eq Constants.DEALER_ACCONT_ACTION_DA_CHOT_KY}">
                                        <fmt:message key="label.da_chot_ky" />
                                    </c:when>
                                    <c:otherwise><fmt:message key="label.chua_chot_ky" /></c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" titleKey="label.trang_thai_du_dk" class="text-center" style="width: 10%;" >
                                <c:choose>
                                    <c:when test="${not empty tableList.condition_status && tableList.condition_status eq '1'}">
                                        <fmt:message key="label.dat" />
                                    </c:when>
                                    <c:otherwise><fmt:message key="label.khong_dat" /></c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" titleKey="label.trang_thai_doi_tien" class="text-center" style="width: 15%;" >
                                <c:choose>
                                    <c:when test="${not empty tableList.exchange_money_status && tableList.exchange_money_status eq Constants.DEALER_ACCOUNT_ACTION_DA_DOI_TIEN}">
                                        <fmt:message key="label.da_doi_tien" />
                                    </c:when>
                                    <c:otherwise><fmt:message key="label.chua_doi_tien" /></c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.diem_ban_hang"/></display:setProperty>
                            <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.diem_ban_hang"/></display:setProperty>
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
        setActiveMenu4Admin('#menuDoiTienDBHTab', '#doiTienDBHTab');
    });

    function submitForm(){
        $('#crudaction').val('search');
        $('#searchForm').submit();
    }

    function export2Excel(){
        $('#crudaction').val('export');
        $('#searchForm').submit();
    }

    <security:authorize ifAnyGranted="CHINHANH">
        function doiTien(){
            $('#crudaction').val('doi-tien');
            if(validateBeforeSubmit()){
                bootbox.confirm('<fmt:message key="label.confirm_title"/>', '<fmt:message key="label.confirm_operation_content"/>', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r) {
                    if(r){
                        $('#searchForm').submit();
                    }
                });
            }else{
                bootbox.alert('<fmt:message key="label.alert_title" />', '<fmt:message key="chinhanh.doi_tien_dbh.label.alert_no_dbh_chosen" />', '<fmt:message key="label.dong_y" />', function(r){});
            }
        }
    </security:authorize>

    function validateBeforeSubmit(){
        var result = false;
        $("#tableList input[name*='checkList']").each(function(index, el){
            if($(el).is(':checked')){
                result = true;
            }
        });
        return result;
    }

    <security:authorize ifAnyGranted="ADMIN">
    function ajaxGetDistrictList(){
        $('#districtMenu').find('option:not(:first-child)').remove();
        selectFirstItemSelect2('#districtMenu');
        $.ajax({
            url: '<c:url value="/ajax${prefix}/getDistrictListByBranch.html" />',
            type: 'get',
            dataType: 'json',
            data: {branchId: $('#branchMenu').val()},
            success: function(res){
                if(res.districtList != null){
                    $(res.districtList).each(function(index, el){
                        $('#districtMenu').append("<option value=" +el.district_Id+ ">" +el.district_name+ "</option>");
                    });
                }
            }
        });
    }
    </security:authorize>

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
            url: '<c:url value="/ajax/admin/getRetailDealerList.html" />',
            type: 'get',
            dataType: 'json',
            data: {branchId: branchId, districtId: $('#districtMenu').val()},
            success: function(res){
                if(res.retailDealerList != null){
                    $(res.retailDealerList).each(function(index, el){
                        $('#retailDealerMenu').append("<option value=" +el.dealer_Id+ ">" +el.dealer_name+ "</option>");
                    });
                }
            }
        });
    }

    function ajaxGetRetailDealerListByBranchId(){
        $('#retailDealerMenu').find('option:not(:first-child)').remove();
        selectFirstItemSelect2('#retailDealerMenu');
        $.ajax({
            url: '<c:url value="/ajax/admin/getRetailDealerListByBranchId.html" />',
            type: 'get',
            dataType: 'json',
            data: {branchId: $('#branchMenu').val()},
            success: function(res){
                if(res.retailDealerList != null){
                    $(res.retailDealerList).each(function(index, el){
                        $('#retailDealerMenu').append("<option value=" +el.dealer_Id+ ">" + $.trim(el.dealer_code) + ' - ' + $.trim(el.dealer_name) + "</option>");
                    });
                }
            }
        });
    }
</script>
</body>
</html>