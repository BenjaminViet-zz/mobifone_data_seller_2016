<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>


<head>
    <title><fmt:message key="admin.donhang.label.heading" /></title>
    <meta name="menu" content="<fmt:message key="admin.donhang.label.heading" />"/>
</head>

<c:set var="prefix" value="/user" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<c:url var="addUrl" value="${prefix}/order/add.html"/>
<c:url var="formUrl" value="${prefix}/order/list.html"/>

<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="admin.donhang.label.list" /></h3>
    </div>

    <div class="title_right">
        <div class="action-bar">
            <a class="btn btn-primary" href="${addUrl}"> <fmt:message key="label.button.them"/></a>
        </div>
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
                <form id="listForm" name="listForm" class="form-horizontal form-label-left" action="" method="post" autocomplete="off">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="KHDN"><fmt:message key="admin.donhang.label.KHDN" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select id="KHDN" path="pojo.khdn.KHDNId" cssClass="form-control">
                                <option value=""><fmt:message key="label.all" /></option>
                                <c:forEach items="${KHDNList}" var="KHDN">
                                    <option <c:if test="${item.pojo.khdn.KHDNId eq KHDN.KHDNId}">selected="true"</c:if> value="${KHDN.KHDNId}">${KHDN.name}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="packageData"><fmt:message key="admin.donhang.label.tenGoiCuoc" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select id="packageData" path="pojo.packageData.packageDataId" cssClass="form-control">
                                <option value=""><fmt:message key="label.all" /></option>
                                <c:forEach items="${packageDataList}" var="packageData">
                                    <option <c:if test="${item.pojo.packageData.packageDataId eq packageData.packageDataId}">selected="true"</c:if> value="${packageData.packageDataId}">${packageData.name}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a class="btn btn-primary" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                            <a class="btn btn-success" onclick="javacsript: resetForm();" ><fmt:message key="label.reset" /></a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_content">
                <c:choose>
                    <c:when test="${item.crudaction == 'search'}">
                        <display:table class="table table-striped table-bordered text-center">
                            <display:column headerClass="table_header text-center" sortable="true" style="width: 5%" >${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}</display:column>
                            <display:column headerClass="table_header text-center" sortable="true" titleKey="admin.donhang.label.KHDN" style="width: 15%"/>
                            <display:column headerClass="table_header text-center" sortable="true" titleKey="admin.donhang.label.tenGoiCuoc" style="width: 10%" />
                            <display:column headerClass="table_header text-center" sortable="true" titleKey="admin.donhang.label.quantity" style="width: 10%"/>
                            <display:column headerClass="table_header text-center" sortable="true" titleKey="admin.donhang.label.UnitPrice" style="width: 10%"/>
                            <display:column headerClass="table_header text-center" sortable="true" titleKey="admin.donhang.label.shippingDate" style="width: 10%"/>
                            <display:column headerClass="table_header text-center" sortable="true" titleKey="admin.donhang.label.status" style="width: 10%"/>
                            <display:column headerClass="table_header text-center" sortable="true" titleKey="admin.donhang.label.CreatedDate" style="width: 15%"/>
                            <display:column headerClass="table_header text-center" sortable="true" titleKey="admin.donhang.label.LastModified" style="width: 15%"/>
                        </display:table>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="admin.donhang.label.list_not_search_yet" />
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>