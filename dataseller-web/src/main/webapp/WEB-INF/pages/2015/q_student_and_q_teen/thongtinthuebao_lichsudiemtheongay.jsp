<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<display:table name="lichSuDiemTableList" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
               partialList="true" sort="external" size="${totalItems}" defaultsort="0"
               id="lichSuDiemTableList" pagesize="${Constants.MAXPAGEITEMS}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 100%;">
    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.ngay" class="text-center nowrap" style="width: 10%;" >
        <fmt:formatDate value="${lichSuDiemTableList.ngay_ps}" pattern="${datePattern}" />
    </display:column>
    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.diem_tich_luy" class="nowrap text-center" style="width: 30%;" >
        <fmt:formatNumber type="number" maxFractionDigits="2" value="${lichSuDiemTableList.so_tien_psc / Constants.UNIT_SCORE}" />
    </display:column>
    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.loai_ps" class="nowrap text-center" style="width: 30%;" >
        <c:choose>
            <c:when test="${fn:trim(lichSuDiemTableList.type_input) eq fn:trim(Constants.PSC_TYPE_INPUT_PTM)}">
                <fmt:message key="label.psc_ptm" />
            </c:when>
            <c:when test="${fn:trim(lichSuDiemTableList.type_input) eq fn:trim(Constants.PSC_TYPE_INPUT_PSC)}">
                <fmt:message key="label.psc_psc" />
            </c:when>
        </c:choose>
    </display:column>
    <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
    <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
</display:table>