<%@ page import="com.benluck.vms.mobifonedataseller.security.util.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="chinhanh.tra_cuu_lich_su_doi_tien_dbh.heading_page" /></title>
    <meta name="heading" content="<fmt:message key="chinhanh.tra_cuu_lich_su_doi_tien_dbh.heading_page" />"/>
</head>

<c:set var="prefix" value="/chinhanh" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/lichsuchikhuyenkhichdbh.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="chinhanh.tra_cuu_lich_su_doi_tien_dbh.heading_page" /></h2>

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
                    <div class="col-md-6">
                        <div class="panel panel-default">
                            <security:authorize ifAnyGranted="ADMIN">
                                <div class="panel-body">
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label"><fmt:message key="label.chi_nhanh" /></label>
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
                                    <label class="col-sm-4 control-label"><fmt:message key="label.quan_huyen" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="districtMenu" path="districtId" cssStyle="width: 250px;" onchange="javascript: ajaxGetRetailDealerList();">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${districtList}" var="district">
                                                <option <c:if test="${item.districtId eq district.district_Id}">selected="true"</c:if> value="${district.district_Id}">${district.district_name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.diembanhang" /></label>
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
                                    <label class="col-sm-4 control-label"><fmt:message key="label.ma_diem_ban_hang" /></label>
                                    <div class="col-sm-8"><form:input path="dealer_code" cssClass="nohtml form-control" /></div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"></label>
                                    <div class="col-sm-8">
                                        <a class="btn btn-info" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                                        <c:if test="${not empty items.listResult}">
                                            <a class="btn btn-info" onclick="javascript: export2Excel();"><fmt:message key="label.export" /></a>
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
                            <display:column headerClass="table_header nowrap" sortable="false" property="branch.branch_name" titleKey="label.chi_nhanh" style="width: 15%;" />
                            <display:column headerClass="table_header nowrap" sortable="false" property="district.district_name" titleKey="label.quan_huyen" style="width: 10%;" />
                            <display:column headerClass="table_header nowrap" sortable="false" property="retailDealer.dealer_code" titleKey="label.ma_diem_ban_hang" style="width: 10%;" />
                            <display:column headerClass="table_header nowrap" sortable="false" property="retailDealer.dealer_name" titleKey="label.ten-diem_ban_hang" style="width: 20%;" />
                            <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.so_tien_doi" class="nowrap text-center" style="width: 15%;" >
                                <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.exchangeAmount}" />
                            </display:column>
                            <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.ngay_doi" class="text-center" style="width: 10%;">
                                <fmt:formatDate value="${tableList.createdDate}" pattern="${datePattern}" />
                            </display:column>
                            <display:setProperty name="paging.banner.item_name"><fmt:message key="cuahanggiaodich.baocaotheokhcn.du_lieu"/></display:setProperty>
                            <display:setProperty name="paging.banner.items_name"><fmt:message key="cuahanggiaodich.baocaotheokhcn.du_lieu"/></display:setProperty>
                        </display:table>
                    </c:if>
                    <input id="crudaction" type="hidden" name="crudaction" />
                    <security:authorize ifAnyGranted="ADMIN">
                        <input id="branchId" type="hidden" value="" />
                    </security:authorize>
                    <security:authorize ifAnyGranted="CHINHANH">
                        <input id="branchId" type="hidden" value="<%=SecurityUtils.getPrincipal().getBranchId()%>" />
                    </security:authorize>
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#menuDoiTienDBHTab', '#lichSuDoiTienDBHTab');
    });

    function submitForm(){
        $('#crudaction').val('search');
        $('#reportForm').submit();
    }

    function export2Excel(){
        $('#crudaction').val('export');
        $('#reportForm').submit();
    }

    function ajaxGetRetailDealerList(){
        $('#retailDealerMenu').find('option:not(:first-child)').remove();
        selectFirstItemSelect2('#retailDealerMenu');
        $.ajax({
            url: '<c:url value="/ajax/admin/getRetailDealerList.html" />',
            type: 'get',
            dataType: 'json',
            data: {branchId: $('#branchId').val(), districtId: $('#districtMenu').val()},
            success: function(res){
                if(res.retailDealerList != null){
                    $(res.retailDealerList).each(function(index, el){
                        $('#retailDealerMenu').append("<option value=" +el.dealer_Id+ ">" + $.trim(el.dealer_code) + ' - ' + $.trim(el.dealer_name) + "</option>");
                    });
                }
            }
        });
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
    </security:authorize>
</script>
</body>
</html>