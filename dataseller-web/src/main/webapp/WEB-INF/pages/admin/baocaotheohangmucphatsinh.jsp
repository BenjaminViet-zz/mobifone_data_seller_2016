<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="admin.baocaotheohangmucphatsinh.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="admin.baocaotheohangmucphatsinh.heading_page" />"/>
    <link href="<c:url value="/themes/promotion/css/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
</head>

<c:set var="prefix" value="/baocao" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/baocaotheohangmucphatsinh.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="admin.baocaotheohangmucphatsinh.heading_page" /></h2>
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
                <form:form commandName="item" id="reportForm" action="${formUrl}" method="post" autocomplete="off">
                    <div class="col-md-8">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="branchMenu" path="branch_Id" cssStyle="width: 250px;" onchange="javascript: ajaxGetDistrictList();">
                                            <security:authorize ifAnyGranted="ADMIN,BAOCAO">
                                                <option value=""><fmt:message key="label.all" /></option>
                                            </security:authorize>
                                            <c:forEach items="${branchList}" var="branch">
                                                <option <c:if test="${item.branch_Id eq branch.branch_Id}">selected="true"</c:if> value="${branch.branch_Id}">${branch.branch_name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.quan_huyen" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="districtMenu" path="district_Id" cssStyle="width: 250px;" onchange="javascript: ajaxGetRetailDealerList();">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${districtList}" var="district">
                                                <option <c:if test="${item.district_Id eq district.district_Id}">selected="true"</c:if> value="${district.district_Id}">${district.district_name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.hang_muc_phat_sinh"/></label>
                                    <div class="col-sm-8">
                                        <form:select id="itemList" path="item_Id" cssStyle="width: 200px;">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${itemList}" var="itemVar">
                                                <option <c:if test="${item.item_Id eq itemVar.item_Id}">selected="true"</c:if> value="${itemVar.item_Id}">${itemVar.item_name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.tu_ngay" /></label>
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
                                    <label class="col-sm-4 control-label"><fmt:message key="label.den_ngay" /></label>
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
                        <div id="reportTableContainer" style="width: 100%;">
                            <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                           partialList="true" sort="external" size="${items.totalItems }" defaultsort="0"
                                           id="tableList" pagesize="${items.reportMaxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 1650px;">
                                <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 50px;" >
                                    ${tableList_rowNum + (page * Constants.REPORT_MAXPAGEITEMS)}
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" property="branch_Name" titleKey="label.chi_nhanh" class="nowrap" style="width: 250px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="district_Name" titleKey="label.quan_huyen" class="nowrap" style="width: 150px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="item_Name" titleKey="label.hang_muc_phat_sinh" class="nowrap" style="width: 200px;" />
                                <display:column headerClass="table_header_center" sortable="false" titleKey="label.so_ps" class="text-right nowrap" style="width: 50px;" >
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.soPS}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" property="donViTinh" titleKey="label.don_vi_tinh" class="text-center nowrap" style="width: 100px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="dealer_Code" titleKey="label.ma_diem_ban_hang" class="nowrap" style="width: 100px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="dealer_Name" titleKey="label.ten-diem_ban_hang" class="nowrap" style="width: 200px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="cycle" titleKey="label.thang" class="text-center nowrap" style="width: 50px;" />
                                <display:column headerClass="table_header_center" sortable="false" titleKey="label.quy_diem" class="text-right nowrap" style="width: 100px;" >
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.quyDiem}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="label.so_tien_tuong_ung" class="text-right nowrap" style="width: 150px;" >
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.soTienTuongUng}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="label.so_ma_du_thuong" class="text-right nowrap" style="width: 100px;" >
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.soMaDuThuong}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="label.ghi_chu" class="text-center nowrap" style="width: 150px;" >
                                    <c:choose>
                                        <c:when test="${not empty tableList.du_dieu_kien_status && tableList.du_dieu_kien_status eq Constants.DU_DIEU_KIEN}">
                                            <fmt:message key="admin.baocaotheohangmucphatsinh.label.du_dieu_kien" />
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="admin.baocaotheohangmucphatsinh.khong_du_dieu_kien" />
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                            </display:table>
                        </div>
                        <div id="linksContainer">

                        </div>
                    </c:if>
                    <form:hidden path="crudaction"/>
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/scripts/jquery/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#tdcg2014ReportHangMucPSTab');

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
        checkMovePageLinks();
    });

    function checkMovePageLinks(){
        if(${not empty items.listResult}){
            $('#reportTableContainer .pagebanner').appendTo('#linksContainer');
            $('#reportTableContainer .pagelinks').appendTo('#linksContainer');
        }
    }

    function submitForm(){
        $('#crudaction').val('search');
        $('#reportForm').submit();
    }

    function reset(){
        selectFirstItemSelect2('#cycleMenu');
        selectFirstItemSelect2('#branchMenu');
        selectFirstItemSelect2('#districtMenu');
        selectFirstItemSelect2('#itemMenu');
        $('#fromDate').val('');
        $('#toDate').val('');
    }

    function export2Excel(){
        $('#crudaction').val('export');
        $('#reportForm').submit();
    }

    function ajaxGetDistrictList(){
        $('#districtMenu').find('option:not(:first-child)').remove();
        $('#retailDealerMenu').find('option:not(:first-child)').remove();
        selectFirstItemSelect2('#districtMenu');
        selectFirstItemSelect2('#retailDealerMenu');
        $.ajax({
            url: '<c:url value="/ajax/admin/getDistrictListByBranch.html" />',
            type: 'get',
            dataType: 'json',
            data: {branchId: $('#branchMenu').val()},
            success: function(res){
                if(res.districtList != null){
                    $(res.districtList).each(function(index, el){
                        $('#districtMenu').append("<option value=" +el.district_Id+ ">" + el.district_name+ "</option>");
                    });
                }
            }
        });
    }
</script>
</body>

