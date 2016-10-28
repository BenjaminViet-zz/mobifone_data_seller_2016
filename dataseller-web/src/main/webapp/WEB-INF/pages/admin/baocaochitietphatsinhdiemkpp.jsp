<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="admin.chitiethangmucphatsinhdiemkpp.heading_page" /></title>
    <meta name="heading" content="<fmt:message key="admin.chitiethangmucphatsinhdiemkpp.heading_page" />"/>
    <style>
        .text-form {
            position: relative;
            top: 5px;
        }
    </style>
</head>

<c:set var="prefix" value="/baocao" />
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="TONGDAI">
    <c:set var="prefix" value="/tongdai" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/baocaochitiethangmucphatsinhdiemkpp.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="admin.chitiethangmucphatsinhdiemkpp.heading_page" /></h2>

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
            <c:if test="${not empty items.listResult}">
                <a class="btn btn-info" style="float: right; margin-top: 10px;" onclick="javascript: export2Excel();"><fmt:message key="label.export" /></a>
                <div class="clear"></div>
                <hr/>
            </c:if>
            <div class="row-fluid report-filter">
                <form:form commandName="item" id="reportForm" action="${formUrl}" method="post" autocomplete="off" validate="validate">
                    <div class="col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.ma_diem_ban_hang" /></label>
                                    <div class="col-sm-8"><span class="text-form">${retailDealer.dealer_code}</span></div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.ten-diem_ban_hang" /></label>
                                    <div class="col-sm-8"><span class="text-form">${fn:trim(retailDealer.dealer_name)}</span></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <c:if test="${not empty items.totalItems && items.totalItems gt 0}">
                        <c:choose>
                            <%--Chi tiet theo loai phat sinh 1--%>
                            <c:when test="${not empty item_Id && item_Id eq Constants.ITEM_ID_1}">
                                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                               partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                               id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 100%;">
                                    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                                        ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                    </display:column>
                                    <display:column headerClass="table_header_center" sortable="false" property="soHoaDon" titleKey="label.so_hoa_don" class="text-center" style="width: 15%;"/>
                                    <display:column headerClass="table_header_center" sortable="false" titleKey="label.ngay_hoa_don" class="text-center" style="width: 15%;">
                                        <fmt:formatDate value="${tableList.ngayHoaDon}" pattern="${datePattern}" />
                                    </display:column>
                                    <display:column headerClass="table_header_center" sortable="false" property="maHangHoa" class="text-center" titleKey="label.ma_hang_hoa" style="width: 15%;"/>
                                    <display:column headerClass="table_header_center" sortable="false" property="tenHangHoa" titleKey="label.ten_hang_hoa" style="width: 30%;"/>
                                    <display:column headerClass="table_header_center" sortable="false" titleKey="label.so_luong" class="text-center" style="width: 10%;">
                                        <fmt:formatNumber type="number" value="${tableList.soLuong}" />
                                    </display:column>
                                    <display:column headerClass="table_header_center" sortable="false" titleKey="label.gia_tri" class="text-center" style="width: 10%;">
                                        <fmt:formatNumber type="number" value="${tableList.giaTri}" maxFractionDigits="2" />
                                    </display:column>
                                    <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                    <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                </display:table>
                            </c:when>
                            <%--Chi tiet theo loai phat sinh 2--%>
                            <c:when test="${not empty item_Id && item_Id eq Constants.ITEM_ID_2}">
                                <%--<display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"--%>
                                               <%--partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"--%>
                                               <%--id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 100%;">--%>
                                    <%--<display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >--%>
                                        <%--${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}--%>
                                    <%--</display:column>--%>
                                    <%--<display:column headerClass="table_header_center" sortable="false" property="soEZ" titleKey="label.so_EZ" class="text-center" style="width: 15%;"/>--%>
                                    <%--<display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.ngay.ps" class="text-center" style="width: 20%;">--%>
                                        <%--<fmt:formatDate value="${tableList.ngay_ps}" pattern="${datePattern}" />--%>
                                    <%--</display:column>--%>
                                    <%--<display:column headerClass="table_header_center nowrap" sortable="false" property="noiDungTinNhan" titleKey="label.noi_dung_tin_nhan" class="text-center" style="width: 30%;" />--%>
                                    <%--<display:column headerClass="table_header_center nowrap" sortable="false" property="ketQua" titleKey="label.ket_qua" style="width: 30%;" />--%>
                                    <%--<display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>--%>
                                    <%--<display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>--%>
                                <%--</display:table>--%>

                                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                               partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                               id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 100%;">
                                    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                                        ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                    </display:column>
                                    <display:column headerClass="table_header_center" sortable="false" property="dealer_Code" titleKey="label.ma_diem_ban_hang" class="text-center" style="width: 10%;"/>
                                    <display:column headerClass="table_header_center" sortable="false" property="dealer_Name" titleKey="label.ten-diem_ban_hang" class="text-center" style="width: 20%;"/>
                                    <display:column headerClass="table_header_center" sortable="false" property="soEZ" titleKey="label.so_EZ" class="text-center" style="width: 15%;"/>
                                    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.ngay.ps" class="text-center" style="width: 15%;">
                                        <fmt:formatDate value="${tableList.ngay_ps}" pattern="${datePattern}" />
                                    </display:column>
                                    <display:column headerClass="table_header_center nowrap" sortable="false" property="soKhachHang" titleKey="label.so_khach_hang" class="text-center" style="width: 15%;" />
                                    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.ngay_kich_hoat" class="text-center" style="width: 15%;" >
                                        <fmt:formatDate value="${tableList.ngayKichHoat}" pattern="${datePattern}" />
                                    </display:column>
                                    <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                    <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                </display:table>
                            </c:when>
                            <%--Chi tiet theo loai phat sinh 3--%>
                            <c:when test="${not empty item_Id && item_Id eq Constants.ITEM_ID_3}">
                                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                               partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                               id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 100%;">
                                    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 10%;" >
                                        ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                    </display:column>
                                    <display:column headerClass="table_header_center" sortable="false" property="so_thue_bao" titleKey="label.so_thue_bao" class="text-center" style="width: 15%;"/>
                                    <display:column headerClass="table_header_center" sortable="false" titleKey="label.ngay_nhan_tin" class="text-center" style="width: 15%;">
                                        <fmt:formatDate value="${tableList.ngayNhanTin}" pattern="${datePattern}" />
                                    </display:column>
                                    <display:column headerClass="table_header_center" sortable="false" titleKey="label.ngay_kich_hoat" class="text-center" style="width: 15%;">
                                        <fmt:formatDate value="${tableList.ngayKichHoat}" pattern="${datePattern}" />
                                    </display:column>
                                    <display:column headerClass="table_header_center" sortable="false" titleKey="label.ngay.ps" class="text-center" style="width: 15%;">
                                        <fmt:formatDate value="${tableList.ngay_ps}" pattern="${datePattern}" />
                                    </display:column>
                                    <display:column headerClass="table_header_center" sortable="false" titleKey="label.cuoc_ps" class="text-center" style="width: 15%; white-space: inherit;">
                                        <fmt:formatNumber type="number" value="${tableList.cuoc_ps}" maxFractionDigits="2" />
                                    </display:column>
                                    <display:column headerClass="table_header_center" sortable="false" titleKey="label.so_diem_qui_doi" class="text-center" style="width: 15%;">
                                        <fmt:formatNumber type="number" value="${tableList.soDiemQuiDoi}" maxFractionDigits="2" />
                                    </display:column>
                                    <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                    <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                </display:table>
                            </c:when>
                            <%--Chi tiet theo loai phat sinh 4--%>
                            <c:when test="${not empty item_Id && item_Id eq Constants.ITEM_ID_4}">
                                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                               partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                               id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 100%;">
                                    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                                        ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                    </display:column>
                                    <display:column headerClass="table_header_center" sortable="false" property="soHoaDon" titleKey="label.so_hoa_don" class="text-center" style="width: 15%;"/>
                                    <display:column headerClass="table_header_center" sortable="false" titleKey="label.ngay_hoa_don" class="text-center" style="width: 15%;">
                                        <fmt:formatDate value="${tableList.ngayHoaDon}" pattern="${datePattern}" />
                                    </display:column>
                                    <display:column headerClass="table_header_center" sortable="false" property="maHangHoa" titleKey="label.ma_hang_hoa" class="text-center" style="width: 15%;"/>
                                    <display:column headerClass="table_header_center" sortable="false" property="tenHangHoa" titleKey="label.ten_hang_hoa" style="width: 20%; word-break: break-word;"/>
                                    <display:column headerClass="table_header_center" sortable="false" titleKey="label.so_luong" class="text-center" style="width: 15%;">
                                        <fmt:formatNumber type="number" value="${tableList.soLuong}" />
                                    </display:column>
                                    <display:column headerClass="table_header_center" sortable="false" titleKey="label.gia_tri" class="text-center" style="width: 15%;">
                                        <fmt:formatNumber type="number" value="${tableList.giaTri}" maxFractionDigits="2" />
                                    </display:column>
                                    <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                    <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                </display:table>
                            </c:when>
                            <%--Chi tiet theo loai phat sinh 5--%>
                            <c:when test="${not empty item_Id && item_Id eq Constants.ITEM_ID_5}">
                                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                               partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                               id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 100%;">
                                    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                                        ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                    </display:column>
                                    <display:column headerClass="table_header_center" sortable="false" property="soEZ" titleKey="label.so_EZ" class="text-center" style="width: 15%;"/>
                                    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.ngay.ps" class="text-center" style="width: 10%;">
                                        <fmt:formatDate value="${tableList.ngay_ps}" pattern="${datePattern}" />
                                    </display:column>
                                    <display:column headerClass="table_header_center nowrap" sortable="false" property="noiDungTinNhan" titleKey="label.noi_dung_tin_nhan" class="text-center" style="width: 30%;" />
                                    <display:column headerClass="table_header_center nowrap" sortable="false" property="ketQua" titleKey="label.ket_qua" style="width: 40%;" />
                                    <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                    <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                </display:table>
                            </c:when>
                            <%--Chi tiet theo loai phat sinh 6--%>
                            <c:when test="${not empty item_Id && item_Id eq Constants.ITEM_ID_6}">
                                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                               partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                               id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 100%;">
                                    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                                        ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                    </display:column>
                                    <display:column headerClass="table_header_center" sortable="false" property="maGiaoDich" titleKey="label.ma_giao_dich" class="text-center" style="width: 15%;"/>
                                    <display:column headerClass="table_header_center" sortable="false" property="soEZ" titleKey="label.so_EZ" class="text-center" style="width: 15%;"/>
                                    <display:column headerClass="table_header_center" sortable="false" property="maGoi" titleKey="label.ma_goi" class="text-center" style="width: 15%;" />
                                    <display:column headerClass="table_header_center" sortable="false" property="tenGoi" titleKey="label.ten_goi" style="width: 35%;"/>
                                    <display:column headerClass="table_header_center" sortable="false" titleKey="label.ngay.ps" class="text-center" style="width: 15%;">
                                        <fmt:formatDate value="${tableList.ngay_ps}" pattern="${datePattern}" />
                                    </display:column>
                                    <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                    <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                </display:table>
                            </c:when>
                            <%--Chi tiet theo loai phat sinh 7--%>
                            <c:when test="${not empty item_Id && item_Id eq Constants.ITEM_ID_7}">
                                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                               partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                               id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 100%;">
                                    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                                        ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                    </display:column>
                                    <display:column headerClass="table_header_center" sortable="false" property="soEZ" titleKey="label.so_EZ" class="text-center" style="width: 15%;"/>
                                    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.ngay.ps" class="text-center" style="width: 15%;">
                                        <fmt:formatDate value="${tableList.ngay_ps}" pattern="${datePattern}" />
                                    </display:column>
                                    <display:column headerClass="table_header_center nowrap" sortable="false" property="noiDungTinNhan" titleKey="label.noi_dung_tin_nhan" class="text-center" style="width: 35%;" />
                                    <display:column headerClass="table_header_center nowrap" sortable="false" property="ketQua" titleKey="label.ket_qua" style="width: 20%;" />
                                    <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                    <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                </display:table>
                            </c:when>
                        </c:choose>
                    </c:if>
                    <input id="crudaction" type="hidden" name="crudaction" />
                    <form:hidden path="item_Id" />
                    <form:hidden path="dealer_Id" />
                    <c:if test="${not empty item.fromDate}">
                        <input type="hidden" name="fromDate" value="<fmt:formatDate value="${item.fromDate}" pattern="${datePattern}" />" />
                    </c:if>
                    <c:if test="${not empty item.fromDate}">
                        <input type="hidden" name="toDate" value="<fmt:formatDate value="${item.toDate}" pattern="${datePattern}" />" />
                    </c:if>
                </form:form>
                <div class="clear"></div>
                <br/>
                <a class="btn btn-info" onclick="javascript: goBack();"><fmt:message key="label.quay_lai" /></a>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#menuDoiTienDBHTab', '#traCuuThongTinKPPTab');
    });

    function goBack(){
        $('#reportForm').attr('action', '${formUrl}?crudaction=go-back');
        $('#reportForm').submit();
    }

    <c:if test="${not empty items.listResult}">
    function export2Excel(){
        $('#crudaction').val('export');
        $('#reportForm').submit();
    }
    </c:if>
</script>
</body>