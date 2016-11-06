<%--
  Created by IntelliJ IDEA.
  User: thaihoang
  Date: 11/1/2016
  Time: 9:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="ft" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<head>
    <title><fmt:message key="admin.khdn.label.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="admin.khdn.label.heading_page" />"/>
</head>

<c:url var="addUrl" value="/admin/vendor/add.html"/>
<c:url var="editUrl" value="/admin/vendor/edit.html"/>
<c:url var="formUrl" value="${prefix}/vendor/list.html"/>

<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="admin.orderhistory.label.heading" /></h3>
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

                <form:form commandName="item" cssClass="form-horizontal form-label-left" id="listForm" action="${formUrl}" method="post" autocomplete="off" name="listForm">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="KHDN"><fmt:message key="admin.donhang.label.KHDN" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select id="KHDN" path="pojo.KHDNId" cssClass="form-control">
                                <option value=""><fmt:message key="label.all" /></option>
                                <c:forEach items="${KHDNList}" var="KHDN">
                                    <option <c:if test="${item.pojo.KHDNId eq KHDN.KHDNId}">selected="true"</c:if> value="${KHDN.KHDNId}">${KHDN.name}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>

                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a class="btn btn-success" onclick="javacsript: resetForm();" ><fmt:message key="label.reset" /></a>
                            <a class="btn btn-primary" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                        </div>
                    </div>
                    <input type="hidden" name="crudaction" value="search" />
                </form:form>


                <%--<form id="listForm" name="listForm" class="form-horizontal form-label-left" action="" method="post" autocomplete="off">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="chonKHDN"><fmt:message key="admin.donhang.label.KHDN" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select id="chonKHDN" class="form-control" required="">
                                <option value="">Chọn Khách Hàng Doanh Nghiệp</option>
                                <option value="vienthonga">Viễn Thông A</option>
                                <option value="tgdd">Thế Giới Di Động</option>
                                <option value="fpt">FPT Shop</option>

                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="chonGoiCuoc"><fmt:message key="admin.donhang.label.tenGoiCuoc" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select id="chonGoiCuoc" class="form-control" required="">
                                <option value="">Chọn gói</option>
                                <option value="DataQT1B">DataQT1B</option>
                                <option value="net">DataQT3B</option>
                                <option value="mouth">DataQT5B</option>
                                <option value="mouth">DataQT8B</option>
                                <option value="mouth">DataQT10B</option>
                                <option value="mouth">DataQT50B</option>
                                <option value="mouth">DataQT70B</option>
                                <option value="mouth">DataQT35B(*)</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="quantity"><fmt:message key="admin.donhang.label.quantity" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="quantity" name="pojo.quantity" class="form-control" type="text" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="UnitPrice"><fmt:message key="admin.donhang.label.UnitPrice" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="UnitPrice" name="pojo.UnitPrice" class="form-control" type="text" value="">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="shippingDate"><fmt:message key="admin.donhang.label.shippingDate" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="shippingDate" name="pojo.shippingDate" class="form-control" type="text" value="">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="status"><fmt:message key="admin.donhang.label.status" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="status" name="pojo.status" class="form-control" type="text" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="CreatedDate"><fmt:message key="admin.donhang.label.CreatedDate" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="CreatedDate" name="pojo.CreatedDate" class="form-control" type="text" value="">
                        </div>
                    </div>
                    &lt;%&ndash;<div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="LastModified"><fmt:message key="admin.donhang.label.LastModified" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="LastModified" name="pojo.LastModified" class="form-control" type="text" value="">
                        </div>
                    </div>&ndash;%&gt;
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a class="btn btn-primary" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                            <a class="btn btn-success" onclick="javacsript: resetForm();" ><fmt:message key="label.reset" /></a>
                        </div>
                    </div>
                </form>--%>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_content">
                    <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                   partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                   id="tableList" pagesize="${items.maxPageItems}" export="false"
                                   class="table table-striped table-bordered" style="margin: 1em 0 1.5em;">
                        <display:column headerClass="table_header text-center" sortable="false" class="text-center" titleKey="label.stt" style="width: 4%">${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}</display:column>
                        <display:column headerClass="table_header text-center" property="name" sortable="false" titleKey="admin.khdn.label.name" style="width: 20%" />
                        <display:column headerClass="table_header text-center" property="mst" sortable="false" titleKey="admin.khdn.label.mst" style="width: 20%" />
                        <display:column headerClass="table_header text-center" property="gpkd" sortable="false" titleKey="admin.khdn.label.gpkd" style="width: 26%" />
                        <display:column headerClass="table_header text-center" sortable="true" class="text-center" sortName="issuedContractDate" titleKey="admin.khdn.label.issuedContractDate" style="width: 10%">
                            <fmt:formatDate value="${tableList.issuedContractDate}" pattern="${datePattern}" />
                        </display:column>
                        <display:column headerClass="table_header text-center" property="stb_vas" sortable="false" class="text-center" titleKey="admin.khdn.label.stb_vas" style="width: 15%" />
                        <display:column headerClass="table_header text-center" class="text-center" titleKey="label.action" style="width:15%;">
                            <a href="${editUrl}?pojo.KHDNId=${tableList.KHDNId}" class="tip-top" title="<fmt:message key="label.edit" />"><fmt:message key="label.edit" /></a>
                            | <a class="tip-top" onclick="javascript: deleteKHDN(${tableList.KHDNId});"><fmt:message key="label.delete" /></a>
                        </display:column>


                        <display:setProperty name="paging.banner.item_name"><fmt:message key="admin.khdn.footer.label.doanhnghiep" /></display:setProperty>
                        <display:setProperty name="paging.banner.items_name"><fmt:message key="admin.khdn.footer.label.doanhnghiep" /></display:setProperty>--%>
                    </display:table>
            </div>
        </div>
    </div>
</div>

<script language="javascript" type="text/javascript">
    function resetForm(){
         $("input[type='text']").val('');
         selectFirstItemSelect2('#userGroupMenu');
     }

     function submitForm(){
        $('#listForm').submit();
     }

    function deleteKHDN(userId){
        bootbox.confirm('<fmt:message key="label.confirm_title" />', '<fmt:message key="label.confirm_operation_content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
            if(r){
                document.location.href = '${formUrl}?pojo.KHDNId=' + userId + '&crudaction=delete';
            }
        });
    }
</script>