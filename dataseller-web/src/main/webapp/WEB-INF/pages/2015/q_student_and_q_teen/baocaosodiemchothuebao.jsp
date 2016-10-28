<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="baocaosodiem.heading_page" /></title>
    <meta name="heading" content="<fmt:message key="baocaosodiem.heading_page" />"/>
</head>

<c:set var="prefix" value="/cuahangmobifone" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<security:authorize ifAnyGranted="TONGDAI">
    <c:set var="prefix" value="/tongdai" />
</security:authorize>
<security:authorize ifAnyGranted="BAOCAO">
    <c:set var="prefix" value="/baocao" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/qstudent/baocaosodiemchochuongtrinh.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="baocaosodiem.heading_page" /></h2>

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
                            <%--<div class="panel-body">--%>
                                <%--<div class="form-group">--%>
                                    <%--<label class="col-sm-2 control-label"><fmt:message key="label.so_thue_bao" /></label>--%>
                                    <%--<div class="col-sm-8">--%>
                                        <%--<form:input id="soThueBao" path="pojo.soEz" cssClass="nohtml validate_phone_number form-control" />--%>
                                        <%--<form:errors for="soThueBao" path="pojo.soEz" cssClass="error-inline-validate" />--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.so_diem_tu" /></label>
                                    <div class="col-sm-8">
                                        <form:input id="soDiemTo" path="soDiemTo" cssClass="checkIsNumber nohtml form-control" />
                                        <form:errors for="soDiemTo" path="soDiemTo" cssClass="error-inline-validate" />
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.so_diem_den" /></label>
                                    <div class="col-sm-8">
                                        <form:input id="soDiemFrom" path="soDiemFrom" cssClass="checkIsNumber nohtml form-control" />
                                        <form:errors for="soDiemFrom" path="soDiemFrom" cssClass="error-inline-validate" />
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
                        <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                       partialList="true" sort="external" size="${items.totalItems }" defaultsort="0"
                                       id="tableList" pagesize="${items.reportMaxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 100%;">
                            <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 10%;" >
                                ${tableList_rowNum + (page * Constants.REPORT_MAXPAGEITEMS)}
                            </display:column>
                            <display:column headerClass="table_header_center nowrap" sortable="false" sortName="soEz" property="soEz" titleKey="label.so_thue_bao" class="text-center" style="width: 45%;" />
                            <display:column headerClass="table_header_center" sortable="false" titleKey="label.so_diem" class="text-center" style="width: 45%;" >
                                <fmt:formatNumber type="number" value="${tableList.soDiem}" />
                            </display:column>

                            <display:setProperty name="paging.banner.item_name"><fmt:message key="cuahanggiaodich.baocaotheokhcn.giao_dich_qua"/></display:setProperty>
                            <display:setProperty name="paging.banner.items_name"><fmt:message key="cuahanggiaodich.baocaotheokhcn.giao_dich_qua"/></display:setProperty>
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
        setActiveMenu4Admin('#qStudent2015BaoCaoSoDiemTab');
    });

    function filterForm(){
        $('#crudaction').val('search');
        $('#reportForm').submit();
    }

    function reset(){
        $('#soThueBao').val('');
        $('#soDiemTo').val('');
        $('#soDiemFrom').val('');
    }

    function export2Excel(){
        $('#crudaction').val('export');
        $('#reportForm').submit();
    }
</script>
</body>