<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
               partialList="true" sort="external" size="${items.totalItems }" defaultsort="0"
               id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 100%;">
    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
        ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
    </display:column>
    <display:column headerClass="table_header nowrap" sortable="false" property="ma_phieu" titleKey="label.maPhieu" class="nowrap" style="width: 15%;" />
    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.ngay.ps" class="text-center nowrap" style="width: 10%;" >
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