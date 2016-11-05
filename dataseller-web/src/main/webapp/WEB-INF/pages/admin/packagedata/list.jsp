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
<%--<div class="row">--%>
    <%--<div class="col-md-12 col-sm-12 col-xs-12">--%>
        <%--<div class="x_panel">--%>
            <%--<div class="x_content">--%>
                <%--<form id="listForm" name="listForm" class="form-horizontal form-label-left" action="" method="post" autocomplete="off">--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="control-label col-md-3 col-sm-3 col-xs-12" for="chonGoiCuoc"><fmt:message key="packagedata.label.tenGoiCuoc" />--%>
                        <%--</label>--%>
                        <%--<div class="col-md-6 col-sm-6 col-xs-12">--%>
                            <%--<select id="chonGoiCuoc" class="form-control" required="">--%>
                                <%--<option value="">Chọn gói</option>--%>
                                <%--<option value="DataQT1B">DataQT1B</option>--%>
                                <%--<option value="net">DataQT3B</option>--%>
                                <%--<option value="mouth">DataQT5B</option>--%>
                                <%--<option value="mouth">DataQT8B</option>--%>
                                <%--<option value="mouth">DataQT10B</option>--%>
                                <%--<option value="mouth">DataQT50B</option>--%>
                                <%--<option value="mouth">DataQT70B</option>--%>
                                <%--<option value="mouth">DataQT35B(*)</option>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="control-label col-md-3 col-sm-3 col-xs-12" for="giaGoiCuoc"><fmt:message key="packagedata.label.giaGoiCuoc" />--%>
                        <%--</label>--%>
                        <%--<div class="col-md-6 col-sm-6 col-xs-12">--%>
                                <%--<input id="giaGoiCuoc" name="pojo.userName" class="form-control" type="text" value="">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="control-label col-md-3 col-sm-3 col-xs-12" for="dungLuongMienPhi"><fmt:message key="packagedata.label.dungLuongMienPhi" />--%>
                        <%--</label>--%>
                        <%--<div class="col-md-6 col-sm-6 col-xs-12">--%>
                            <%--<input id="dungLuongMienPhi" name="pojo.userName" class="form-control" type="text" value="">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="control-label col-md-3 col-sm-3 col-xs-12" for="thoiGianSuDung"><fmt:message key="packagedata.label.thoiGianSuDung" />--%>
                        <%--</label>--%>
                        <%--<div class="col-md-6 col-sm-6 col-xs-12">--%>
                            <%--<input id="thoiGianSuDung" name="pojo.userName" class="form-control" type="text" value="">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="control-label col-md-3 col-sm-3 col-xs-12" for="soLanGiaHan"><fmt:message key="packagedata.label.soLanGiaHan" />--%>
                        <%--</label>--%>
                        <%--<div class="col-md-6 col-sm-6 col-xs-12">--%>
                            <%--<input id="soLanGiaHan" name="pojo.userName" class="form-control" type="text" value="">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="control-label col-md-3 col-sm-3 col-xs-12" for="tk"><fmt:message key="packagedata.label.tk" />--%>
                        <%--</label>--%>
                        <%--<div class="col-md-6 col-sm-6 col-xs-12">--%>
                            <%--<input id="tk" name="pojo.userName" class="form-control" type="text" value="">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="form-group last">--%>
                        <%--<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">--%>
                            <%--&lt;%&ndash;<a class="btn btn-primary" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>&ndash;%&gt;--%>
                            <%--<a class="btn btn-success" onclick="javacsript: resetForm();" ><fmt:message key="label.reset" /></a>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</form>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>

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
                        ${tableList.value}
                    </display:column>
                    <display:column headerClass="table_header text-center" property="volume" sortable="false" class="text-center" titleKey="packagedata.label.dungLuongMienPhi" style="width: 30%" />
                    <display:column headerClass="table_header text-center" property="durationText" sortable="true" class="text-center" titleKey="packagedata.label.thoiGianSuDung" style="width: 15%" />
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