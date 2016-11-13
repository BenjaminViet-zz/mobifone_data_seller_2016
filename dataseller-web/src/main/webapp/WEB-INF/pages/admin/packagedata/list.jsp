<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<head>
    <title><fmt:message key="packagedata.list.heading" /></title>
    <meta name="menu" content="<fmt:message key="packagedata.list.heading" />"/>
</head>

<c:set var="prefix" value="/user" />
<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize access="hasAnyAuthority('KHDN')">
    <c:set var="prefix" value="/khdn" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/package_data/list.html"/>

<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="admin.data_code_list.heading_page" /></h3>
    </div>
</div>
<div class="clearfix"></div>
<c:if test ="${not empty messageResponse}">
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_content">
                    <div class="alert alert-${alertType} no-bottom">
                        <a class="close" data-dismiss="alert" href="#">&times;</a>
                            ${messageResponse}
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
        </div>
    </div>
</c:if>

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_content">
                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                               partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                               id="tableList" pagesize="${items.maxPageItems}" export="false"
                               class="table table-striped table-bordered" style="margin: 1em 0 1.5em;">

                    <display:column headerClass="table_header text-center" titleKey="label.stt" class="text-center" style="width: 3%;" >
                        ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                    </display:column>
                    <display:column headerClass="table_header text-center" property="name" sortName="name" class="text-center" titleKey="packagedata.label.tenGoiCuoc" style="15%"/>
                    <display:column headerClass="table_header text-center" sortName="value" class="text-center" titleKey="packagedata.label.giaGoiCuoc" style="15%">
                        <fmt:formatNumber type="number" value="${tableList.value}" />
                    </display:column>
                    <display:column headerClass="table_header text-center" property="volume" sortable="false" class="text-center" titleKey="packagedata.label.dungLuongMienPhi" style="width: 30%" />
                    <display:column headerClass="table_header text-center" property="durationText" sortable="false" class="text-center" titleKey="packagedata.label.thoiGianSuDung" style="width: 15%" />
                    <display:column headerClass="table_header text-center" property="numberOfExtend" sortable="false" class="text-center" titleKey="packagedata.label.soLanGiaHan" style="width: 15%" />
                    <display:column headerClass="table_header text-center" property="tk" sortable="false" class="text-center" titleKey="packagedata.label.tk" style="width: 7%" />
                    <display:setProperty name="paging.banner.item_name"><fmt:message key="packagedata.label.package_item" /></display:setProperty>
                    <display:setProperty name="paging.banner.items_name"><fmt:message key="packagedata.label.package_item" /></display:setProperty>
                </display:table>
            </div>
        </div>
    </div>
</div>

<script language="javascript" type="text/javascript">
    $(document).ready(function(){
        function replaceSpecialCharacter() {
            var str = $('#tableList').text();
            var textToReplace = '{delimiter_line}';
            var replaceWith = '<span class="line_separator"></span>'
            if (str.indexOf(textToReplace) == -1) {

            } else {
                $("td:contains(" + textToReplace + ")").addClass('breakTwoLines');
                var newString = $('td.breakTwoLines').text();
                var result = newString.replace(textToReplace, replaceWith);
                $('td.breakTwoLines').html(result);
            }
        }
        replaceSpecialCharacter();

        function resetForm(){
            $("input[type='text']").val('');
            selectFirstItemSelect2('#chonGoiCuoc');
        }
    })
</script>