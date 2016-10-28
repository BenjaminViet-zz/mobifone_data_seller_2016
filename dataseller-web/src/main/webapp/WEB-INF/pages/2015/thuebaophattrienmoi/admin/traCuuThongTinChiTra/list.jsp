<%--
  Created by IntelliJ IDEA.
  User: vovanphuc0810
  Date: 2/6/15
  Time: 9:02 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="chitraDBH.heading_page" /></title>
    <meta name="heading" content="<fmt:message key="chitraDBH.heading_page" />"/>
    <style>
        .tableVms th.order1 a {
            padding-right: 0px;
        }
    </style>
</head>

<c:url var="formUrl" value="${prefix}/chikhuyenkhichdbh.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="chitraDBH.heading_page" /></h2>

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
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="branchMenu" path="pojo.chiNhanh" cssStyle="width: 250px;">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${chiTraDBHList}" var="chiNhanhVar">
                                                <option value="${chiNhanhVar.chiNhanh}">${chiNhanhVar.chiNhanh}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.diembanhang" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="retailDealerMenu" path="pojo.diemBanHang" cssStyle="width: 250px;">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${chiTraDBHList}" var="diemBanHang">
                                                <option value="${diemBanHang.diemBanHang}">${diemBanHang.diemBanHang}</option>
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
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.pojo.tuNgay}" />" placeholder="${symbolDateEmpty}"/>
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
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.pojo.tuNgay}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="toDateIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"></label>
                                    <div class="col-sm-8">
                                        <a class="btn btn-info" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                                        <a class="btn btn-info" onclick="javascript: export2Excel();"><fmt:message key="label.export" /></a>
                                        <%--<c:if test="${not empty items.listResult}">
                                            <a class="btn btn-info" onclick="javascript: export2Excel();"><fmt:message key="label.export" /></a>
                                            <a class="btn btn-info" onclick="javascript: doiTien();"><fmt:message key="label.doi_tien" /></a>
                                        </c:if>--%>
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
                            <display:column headerClass="table_header" sortable="false" property="chiNhanh" titleKey="label.chi_nhanh" class="nowrap" style="width: 15%;" />
                            <display:column headerClass="table_header" sortable="false" property="diemBanHang" titleKey="label.dbh" class="nowrap" style="width: 20%;" />
                            <display:column headerClass="table_header" sortable="false" titleKey="label.tu_ngay" style="width: 15%;" >
                                <fmt:formatDate value="${tableList.tuNgay}" pattern="${datePattern}" />
                            </display:column>
                            <display:column headerClass="table_header" sortable="false" titleKey="label.den_ngay" style="width: 15%;" >
                                <fmt:formatDate value="${tableList.denNgay}" pattern="${datePattern}" />
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" titleKey="label.so_tien_chi_tra" class="text-center nowrap" style="width: 15%;" >
                                <fmt:formatNumber type="number" value="${tableList.soTien}" maxFractionDigits="2" />
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" titleKey="label.trang_thai" class="text-center" style="width: 15%;" >
                                <c:choose>
                                    <c:when test="${not empty tableList.trangThai && tableList.trangThai eq 1}">
                                        <fmt:message key="label.da_chi_tra" />
                                    </c:when>
                                    <c:otherwise><fmt:message key="label.chua_chi_tra" /></c:otherwise>
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

    function submitForm(){
        $('#crudaction').val('search');
        $('#searchForm').submit();
    }

    function export2Excel(){
        $('#crudaction').val('export');
        $('#searchForm').submit();
    }
</script>
</body>
</html>
