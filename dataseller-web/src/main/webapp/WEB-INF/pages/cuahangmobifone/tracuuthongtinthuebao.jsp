<%@ taglib prefix="fnt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="cuahanggiaodich.thongtinthuebao.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="cuahanggiaodich.thongtinthuebao.heading_page" />"/>
    <style>
        .text-form{
            top: 5px;
        }
    </style>
</head>

<c:set var="prefix" value="/cuahangmobifone" />
<security:authorize ifAnyGranted="TONGDAI">
    <c:set var="prefix" value="/tongdai" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/tracuuthongtinthuebao.html"/>
<div class="ajax-progress"></div>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="cuahanggiaodich.thongtinthuebao.heading_page" /></h2>

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
            <div style="clear: both;" ></div>
            <div class="row-fluid report-filter">
                <form:form commandName="item" id="searchForm" action="${formUrl}" method="post" autocomplete="off">
                    <div class="col-md-10">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.so_thue_bao" /></label>
                                    <div class="col-sm-8">
                                        <form:input id="sothuebao" path="thue_bao" cssClass="required nohtml validate_phone_number form-control" />
                                        <form:errors for="sothuebao" path="thue_bao" cssClass="error-inline-validate" />
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <div class="col-sm-8">
                                        <a class="btn btn-info" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                                    </div>
                                </div>
                            </div>
                            <hr/>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <c:if test="${not empty item.soDiemHienTai}">
                        <div class="panel-body">
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><fmt:message key="label.so_diem_hien_tai" /></label>
                                <div class="col-sm-8"><span class="text-form">${item.soDiemHienTai}</span></div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty item.soPhieuDaDoi}">
                        <div class="panel-body">
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><fmt:message key="label.so_phieu_da_doi" /></label>
                                <div class="col-sm-8"><span class="text-form">${item.soPhieuDaDoi}</span></div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty item.soDiemHienTai}">
                        <ul class="nav nav-tabs promotion_nav_tab" id="thong_tin_nav_tab">
                            <li class="active"><a href="#danhSachMaPhieuTab"><fmt:message key="cuahanggiaodich.thongtinthuebao.label.danh_sach_ma_phieu" /></a></li>
                            <li><a href="#danhSachDiemTichLuyNgayTab"><fmt:message key="cuahanggiaodich.thongtinthuebao.label.lich_su_diem_tich_luy_ngay" /></a></li>
                            <li><a href="#diemTichLuyThangTab"><fmt:message key="cuahanggiaodich.thongtinthuebao.label.diem_tich_luy_thang" /></a></li>
                        </ul>
                        <div class="tab-content viewScoreContent">
                            <div class="tab-pane active" id="danhSachMaPhieuTab">
                                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                               partialList="true" sort="external" size="${items.totalItems }" defaultsort="0"
                                               id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 100%;">
                                    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                                        ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                    </display:column>
                                    <display:column headerClass="table_header nowrap" sortable="false" property="ma_phieu" titleKey="label.maPhieu" class="nowrap" style="width: 15%;" />
                                    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.ngay.ps" class="text-center" style="width: 10%;" >
                                        <fmt:formatDate value="${tableList.ngay_ps}" pattern="${datePattern}" />
                                    </display:column>
                                    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.trang_thai" class="text-center nowrap" style="width: 10%;" >
                                        <c:choose>
                                            <c:when test="${not empty tableList.da_doi_qua && tableList.da_doi_qua eq 2}">
                                                <fmt:message key="label.da_doi_qua"/>
                                            </c:when>
                                            <c:otherwise><fmt:message key="label.chua_doi_qua" /></c:otherwise>
                                        </c:choose>
                                    </display:column>
                                    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.ngay_doi_qua" class="nowrap text-center" style="width: 30%;" >
                                        <fmt:formatDate value="${tableList.ngay_doi_qua}" pattern="${datePattern}" />
                                    </display:column>
                                    <display:column headerClass="table_header nowrap" sortable="false" property="cua_hang_doi_qua_name" titleKey="label.cua_hang" class="nowrap" style="width: 30%;" />
                                    <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.ma_phieu"/></display:setProperty>
                                    <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.ma_phieu"/></display:setProperty>
                                </display:table>
                            </div>
                            <div id="danhSachDiemTichLuyNgayTab" class="tab-pane viewScoreContent">
                                <div class="col-xs-6">
                                    <div class="form-group">
                                        <input id="fromDateLichSuDiemTheoNgay" class="form-control nohtml text-center prevent_type"
                                               type="text" placeholder="${symbolDateEmpty}" />
                                    </div>
                                    <img id="fromDateLichSuDiemTheoNgayIcon" class="img-responsive calendar margin-left-small" src="<c:url value="/themes/promotion/images/Calendar-Time.png"/>" alt="">
                                </div>
                                <div class="col-xs-6">
                                    <div class="form-group">
                                        <input id="toDateLichSuDiemTheoNgay" class="form-control nohtml text-center prevent_type"
                                               type="text" placeholder="${symbolDateEmpty}" />
                                    </div>
                                    <img id="toDateLichSuDiemTheoNgayIcon" class=" img-responsive calendar margin-left-small" src="<c:url value="/themes/promotion/images/Calendar-Time.png"/>" alt="">
                                </div>
                                <div class="col-xs-12 text-center">
                                    <a class="btn btn-info" style="padding-top: 10px;" onclick="javascript: traCuuLichSuDiemTheoNgay();"><i class="icon-refresh"></i>&nbsp;<fmt:message key="label.search" /></a>
                                </div>
                                <div class="clear"></div>
                                <div id="lichSuDiemTheoNgayContentPane">
                                    <%--this content will be loaded by ajax--%>
                                </div>
                            </div>
                            <div id="diemTichLuyThangTab" class="tab-pane">
                                <table class="tableVms table-hover">
                                    <tr>
                                        <th class="table_header_center"><fmt:message key="label.thang" /></th>
                                        <th class="table_header_center"><fmt:message key="label.tong_diem" /></th>
                                    </tr>
                                    <c:forEach items="${lichSuDiemTheoThangTableList}" var="statisticMonthData">
                                        <tr>
                                            <td class="text-center">${statisticMonthData.month}</td>
                                            <td class="text-center"><fnt:formatNumber type="number" value="${statisticMonthData.total}" /></td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:if>
                    <input type="hidden" name="crudaction" value="search" />
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#tdcgTraCuuThongTinThueBaoTab');

        $('#thong_tin_nav_tab a').click(function (e) {
            e.preventDefault();
            $(this).tab('show');
        });

        var fromDateLichSuDiemTheoNgayVar = $("#fromDateLichSuDiemTheoNgay").datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    fromDateLichSuDiemTheoNgayVar.hide();
                }).data('datepicker');

        $('#fromDateLichSuDiemTheoNgayIcon').click(function () {
            $('#fromDateLichSuDiemTheoNgay').focus();
            return true;
        });

        var toDateLichSuDiemTheoNgayVar = $("#toDateLichSuDiemTheoNgay").datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    toDateLichSuDiemTheoNgayVar.hide();
                }).data('datepicker');

        $('#toDateLichSuDiemTheoNgayIcon').click(function () {
            $('#toDateLichSuDiemTheoNgay').focus();
            return true;
        });

        bindAjaxPagination4MaPhieuTableList();

        jQuery.validator.addClassRules('validate_phone_number', {
            validatePhoneNumber: true
        });

        jQuery.validator.addMethod("validatePhoneNumber", function () {
            return validatePhoneNumber($.trim($('#sothuebao').val()));
        }, "<fmt:message key="user.msg.invalid_phone_number"/>");
    });

    function submitForm(){
        if($('#searchForm').valid()){
            $('#searchForm').submit();
        }
    }

    function bindAjaxPagination4MaPhieuTableList(){
        $("#danhSachMaPhieuTab span.pagelinks a").click(function(){
            var params = $(this).attr('href').replace(/(\?)+/g, '').split('&'),
                paramPagination = '',
                regCheckPagination = new RegExp("-p$");
            $(params).each(function(index, param){
                if(param.split('=').length > 1){
                    var tmp = param.split('=')[0];
                    if(regCheckPagination.test(tmp)){
                        paramPagination = param;
                    }
                }
            });
            filterAjaxMaPhieuTableList(paramPagination);
            return false;
        });
    }

    function filterAjaxMaPhieuTableList(tableParamPagination){
        $('body').showLoading();
        var  params = {
            crudaction: 'search',
            soThueBao: $.trim($('#sothuebao').val())
        }
        if(typeof tableParamPagination != 'undefined' && $.trim(tableParamPagination) != ''){
            params[tableParamPagination.split('=')[0]] = tableParamPagination.split('=')[1];
        }
        $.ajax({
            url: "<c:url value="/ajax${prefix}/loadajaxmaphieulist.html"/>",
            type: "POST",
            dataType: "html",
            data: params,
            complete: function(res){
                $('#danhSachMaPhieuTab').html("");
                if(res.responseText != null){
                    var form = $(res.responseText);
                    $('#danhSachMaPhieuTab').html(form);
                    bindAjaxPagination4MaPhieuTableList();
                }
                $('body').removeLoading();
            }
        });
    }

    function traCuuLichSuDiemTheoNgay(){
        $('body').showLoading();
        filterAjaxLichSuDiemTheoNgay();
    }

    function bindAjaxPagination4LichSuDiemTheoNgayTableList(){
        $("#lichSuDiemTheoNgayContentPane span.pagelinks a").click(function(){
            var params = $(this).attr('href').replace(/(\?)+/g, '').split('&'),
                    paramPagination = '',
                    regCheckPagination = new RegExp("-p$");
            $(params).each(function(index, param){
                if(param.split('=').length > 1){
                    var tmp = param.split('=')[0];
                    if(regCheckPagination.test(tmp)){
                        paramPagination = param;
                    }
                }
            });
            filterAjaxLichSuDiemTheoNgay(paramPagination);
            return false;
        });
    }

    function filterAjaxLichSuDiemTheoNgay(tableParamPagination){
        $('body').showLoading();
        var params = {
            fromDate: $('#fromDateLichSuDiemTheoNgay').val(),
            toDate: $('#toDateLichSuDiemTheoNgay').val(),
            thue_bao: '${item.thue_bao}',
            crudaction: 'search'
        }
        if(typeof tableParamPagination != 'undefined' && $.trim(tableParamPagination) != ''){
            params[tableParamPagination.split('=')[0]] = tableParamPagination.split('=')[1];
        }
        $.ajax({
            url: '<c:url value="/ajax${prefix}/loadlichsudiemtheongay.html" />',
            type: 'get',
            dataType: 'json',
            data: params,
            complete: function(res){
                $('#lichSuDiemTheoNgayContentPane').empty();
                if(res.responseText != null){
                    $('#lichSuDiemTheoNgayContentPane').html(res.responseText);
                    bindAjaxPagination4LichSuDiemTheoNgayTableList();
                }
                $('body').removeLoading();
            }
        });
    }
</script>