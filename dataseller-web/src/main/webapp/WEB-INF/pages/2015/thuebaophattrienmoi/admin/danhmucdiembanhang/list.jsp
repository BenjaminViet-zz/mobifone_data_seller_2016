<%@ page import="com.benluck.vms.mobifonedataseller.security.util.SecurityUtils" %>
<%--
  Created by IntelliJ IDEA.
  User: vovanphuc0810
  Date: 2/12/15
  Time: 9:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="td" uri="http://displaytag.sf.net" %>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<c:set var="prefix" value="/admin" />
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<c:url var="formlUrl" value="${prefix}/thuebaophattrienmoi/danhmucdiembanhang.html" />
<head>
    <title>Danh mục điểm bán hàng</title>
    <meta name="heading" content="Danh mục điểm bán hàng"/>
    <link href="<c:url value="/themes/promotion/css/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
</head>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i> Danh mục điểm bán hàng</h2>

</div>
<div class="contentpanel">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">Danh mục điểm bán hàng</h4>
        </div>
        <div class="panel-body panel-body-nopadding">
            <c:if test ="${not empty messageResponse}">
                <div class="alert alert-${alertType}">
                    <a class="close" data-dismiss="alert" href="#">&times;</a>
                    ${messageResponse}
                </div>
            </c:if>
            <div class="clear" ></div>
            <div class="row-fluid report-filter">
                <form:form commandName="items" id="listForm" action="${formlUrl}" method="post" autocomplete="off" name="listForm">
                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group col-sm-12">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="branchMenu" path="pojo.branch.branch_Id" cssStyle="width: 250px;" onchange="javascript: ajaxGetDistrictList(); ajaxGetRetailDealerListByBranchId();">
                                            <security:authorize ifAnyGranted="ADMIN">
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
                                <div class="form-group col-sm-12">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.quan_huyen" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="districtMenu" path="pojo.district.district_Id" cssStyle="width: 250px;" onchange="javascript: ajaxGetRetailDealerList();">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${districtList}" var="district">
                                                <option <c:if test="${item.pojo.district.district_Id eq district.district_Id}">selected="true"</c:if> value="${district.district_Id}">${district.district_name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group col-sm-12">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.diembanhang" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="retailDealerMenu" path="pojo.dealer_Id" cssStyle="width: 250px;">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${retailDealerList}" var="retailDealer">
                                                <option <c:if test="${item.pojo.dealer_Id eq retailDealer.dealer_Id}">selected="true"</c:if> value="${retailDealer.dealer_Id}">${fn:trim(retailDealer.dealer_code)} - ${fn:trim(retailDealer.dealer_name)}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group col-sm-12">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.so_EZ" /></label>
                                    <div class="col-sm-8">
                                        <form:input id="soEZ" path="pojo.ez_isdn" cssClass="nohtml validate_phone_number form-control" />
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group col-sm-12">
                                    <label class="col-sm-2 control-label"></label>
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
                    <div class="table-responsive">
                        <div id="reportTableContainer" style="width: 100%;">
                            <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formlUrl}"
                                           partialList="true" sort="external" size="${items.totalItems}" defaultsort="1"
                                           id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 1400px;">

                                <display:column headerClass="table_header_center nowrap" sortable="false" class="text-center" titleKey="label.stt" style="width: 50px;">
                                    ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                </display:column>
                                <display:column headerClass="table_header_center nowrap" property="dealer_name" titleKey="Tên cửa hàng" style="width: 200px;"/>
                                <display:column headerClass="table_header_center nowrap" class="text-center" property="dealer_code" titleKey="Mã cửa hàng" style="width: 150px;"/>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="label.so_EZ" style="width: 150px;" class="text-center">
                                    <c:choose>
                                        <c:when test="${not empty tableList.primary && tableList.primary eq '0'}">
                                            ${tableList.ez_isdn}@
                                        </c:when>
                                        <c:otherwise>
                                            ${tableList.ez_isdn}
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column headerClass="table_header_center nowrap" property="branch_name" titleKey="Tên chi nhánh" style="width: 200px;"/>
                                <display:column headerClass="table_header_center nowrap" property="district_name" titleKey="Quận huyện" style="width: 200px;"/>
                                <display:column headerClass="table_header_center nowrap" property="address" titleKey="Địa chỉ" style="width: 200px;"/>
                                <display:column headerClass="table_header_center nowrap" titleKey="Ngày tạo" style="width: 100px;">
                                    <fmt:formatDate pattern="${datePattern}" value="${tableList.createdDate}" />
                                </display:column>
                                <display:column headerClass="table_header_center nowrap" titleKey="Đăng Ký Thoả Thuận" class="text-center" style="width: 150px;">
                                    <c:choose>
                                        <c:when test="${not empty tableList.propertiesCode && tableList.propertiesCode eq Constants.DEALER_HAVE_DOC}">
                                            <fmt:message key="label.co" />
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="label.khong"/>
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>

                                <display:setProperty name="paging.banner.item_name" value="điểm bán hàng"/>
                                <display:setProperty name="paging.banner.items_name" value="điểm bán hàng"/>
                            </display:table>
                            <form:hidden path="crudaction" id="crudaction"/>
                        </div>
                        <div id="linksContainer">

                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
    <script type="text/javascript" src="<c:url value="/scripts/jquery/jquery.mCustomScrollbar.concat.min.js"/>"></script>
    <script language="javascript" type="text/javascript">
        $(document).ready(function(){
            // active tab
           setActiveMenu4Admin('#dmDiemBanHangTab');

            jQuery.validator.addClassRules('validate_phone_number', {
                validatePhoneNumber: true
            });

            jQuery.validator.addMethod("validatePhoneNumber", function () {
                return validatePhoneNumber($.trim($('#soEZ').val()));
            }, "<fmt:message key="user.msg.invalid_ez"/>");

            $('#reportTableContainer').mCustomScrollbar({axis:"x"});
            checkMovePageLinks();
        });

        function checkMovePageLinks(){
            if(${not empty items.listResult}){
                $('#reportTableContainer .pagebanner').appendTo('#linksContainer');
                $('#reportTableContainer .pagelinks').appendTo('#linksContainer');
            }
        }

        function submitForm(){
            if($('#listForm').valid()){
                $("#crudaction").val("search");
                $('#listForm').submit();
            }
        }
        function export2Excel(){
            $('#crudaction').val('export');
            $('#listForm').submit();
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