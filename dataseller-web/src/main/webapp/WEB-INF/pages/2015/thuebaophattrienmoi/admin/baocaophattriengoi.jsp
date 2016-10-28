<%--
  Created by IntelliJ IDEA.
  User: vovanphuc0810
  Date: 7/15/15
  Time: 11:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="baocaophattriengoi.heading_page" /></title>
    <meta name="heading" content="<fmt:message key="baocaophattriengoi.heading_page" />"/>
    <link href="<c:url value="/themes/promotion/css/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
</head>
<c:set var="prefix" value="/baocao" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/thuebaophattrienmoi/BaoCaoPhatTrienGoi.html"/>

<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="baocaophattriengoi.heading_page" /></h2>

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
                                        <form:select id="packageMenu" path="pojo.promPackageDTO.package_Id" cssStyle="width: 250px;">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${promPackage}" var="promPackage">
                                                <option <c:if test="${item.pojo.promPackageDTO.package_Id eq promPackage.package_Id}">selected="true"</c:if> value="${promPackage.package_Id}">${promPackage.package_Name}</option>
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
                                            <input name="fromDate" id="fromDate" class="required nohtml prevent_type text-center form-control" type="text"
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
                                            <input name="toDate" id="toDate" class="required nohtml prevent_type text-center form-control" type="text"
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
                                       id="tableList" pagesize="${items.reportMaxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 1000px;">
                            <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 50px;" >
                                ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                            </display:column>
                            <display:column headerClass="table_header_center nowarp"  property="promPackageDTO.package_Name" titleKey="label.ten_goi" class="nowrap" style="width: 70px;" />

                            <display:column headerClass="table_header_center" titleKey="label.gia_goi" class="text-right" style="width: 80px;" >
                                <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.promPackageDTO.price}" />
                            </display:column>
                            <display:column headerClass="table_header_center" property="accumulated_quantity" titleKey="label.sl_thuebao_dkGoi" class="text-right nowrap" style="width: 150px;" />
                            <display:column headerClass="table_header_center" property="new_reg_quantity" titleKey="label.sl_thuebao_dkMoi" class="text-right nowrap" style="width: 100px;" />
                            <display:column headerClass="table_header_center" property="finished_quantity" titleKey="label.sl_goi_hethan" class="text-right nowrap" style="width: 150px;" />
                            <display:column headerClass="table_header_center" titleKey="label.thuc_tang_giam" class="text-right nowrap" style="width: 100px;" >
                                ${tableList.new_reg_quantity - tableList.finished_quantity}
                            </display:column>
                            <display:column headerClass="table_header_center" property="renewal_quantity" titleKey="label.sl_thuebao_gia_han" class="text-right nowrap" style="width: 100px;" />
                            <display:column headerClass="table_header_center" titleKey="label.tong_so_goi" class="text-right nowrap" style="width: 100px;" >
                                ${tableList.new_reg_quantity - tableList.renewal_quantity}
                            </display:column>
                            <display:column headerClass="table_header_center" titleKey="label.doanh_thu_goi" class="text-right nowrap" style="width: 150px;" >
                                <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.package_revenue}" />
                            </display:column>

                            <display:setProperty name="paging.banner.item_name"><fmt:message key="cuahanggiaodich.baocaotheokhcn.giao_dich_qua"/></display:setProperty>
                            <display:setProperty name="paging.banner.items_name"><fmt:message key="cuahanggiaodich.baocaotheokhcn.giao_dich_qua"/></display:setProperty>
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
        setActiveMenu4Admin('#tbptm2015ReportPhatTrienGoiTab');

        var toDate = new Date(),
            fromDate = new Date(toDate.getFullYear(), toDate.getMonth(), 1);
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

        if(${not empty item.fromDate}){
            var convertedDate = '${not empty item.fromDate ? vms:convertDateToString(item.fromDate, datePattern) : 'undefined'}';
            if(convertedDate != null && typeof convertedDate != 'undefined'){
                var arr = convertedDate.split('/'),
                    date = arr[0],
                    month = arr[1] - 1,
                    year = arr[2];
                fromDate.setDate(date);
                fromDate.setMonth(month);
                fromDate.setYear(year);
            }
        }
        $("#fromDate").datepicker("setDate", fromDate);


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
        if(${not empty item.toDate}){
            var convertedDate = '${not empty item.toDate ? vms:convertDateToString(item.toDate, datePattern) : 'undefined'}';
            if(convertedDate != null && typeof convertedDate != 'undefined'){
                var arr = convertedDate.split('/'),
                        date = arr[0],
                        month = arr[1] - 1,
                        year = arr[2];
                toDate.setDate(date);
                toDate.setMonth(month);
                toDate.setYear(year);
            }
        }
        $("#toDate").datepicker("setDate", toDate);


        $('#reportTableContainer').mCustomScrollbar({axis:"x"});
        checkMovePageLinks();
    });

    function checkMovePageLinks(){
        if(${not empty items.listResult}){
            $('#reportTableContainer .pagebanner').appendTo('#linksContainer');
            $('#reportTableContainer .pagelinks').appendTo('#linksContainer');
        }
    }

    function filterForm(){
        if($('#reportForm').valid()){
            $('#crudaction').val('search');
            $('#reportForm').submit();
        }
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
