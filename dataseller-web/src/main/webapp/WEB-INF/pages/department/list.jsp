<%@ taglib prefix="td" uri="http://displaytag.sf.net" %>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<c:url var="addUrl" value="/admin/thongtincuahang.html" />
<c:url var="importShopUrl" value="/admin/department/import.html" />
<c:url var="formlUrl" value="/admin/danhmuccuahang.html" />


<html>
<head>
    <title>Danh mục cửa hàng</title>
    <meta name="heading" content="Danh mục cửa hàng"/>
</head>
<body>


<div class="pageheader">
    <h2><i class="fa fa-edit"></i> Danh mục cửa hàng</h2>

</div>
<div class="contentpanel">
    <div class="panel panel-default">
        <div class="panel-heading">
            <div class="panel-btns">

                <a class="btn btn-primary" href="${importShopUrl}"><fmt:message key="label.button.import" /></a>
                <a class="btn btn-primary" href="${addUrl}"> <fmt:message key="button.add"/></a>
            </div>
            <h4 class="panel-title">Danh sách cửa hàng</h4>
        </div>
        <div class="panel-body panel-body-nopadding">
            <c:if test ="${not empty messageResponse}">
                <div class="alert alert-${alertType}">
                    <a class="close" data-dismiss="alert" href="#">&times;</a>
                        ${messageResponse}
                </div>
            </c:if>
            <div class="clear" ></div>
            <div class="row-fluid report-filter">
                <form:form commandName="items" id="listForm" action="${formlUrl}" method="post" autocomplete="off" name="listForm">
                    <div class="col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="chiNhanhMenu" path="pojo.chiNhanhId" cssStyle="width: 250px;" >
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${chiNhanhList}" var="chiNhanhVar">
                                                <option <c:if test="${item.pojo.chiNhanhId eq chiNhanhVar.chiNhanhId}">selected="true"</c:if> value="${chiNhanhVar.chiNhanhId}">${chiNhanhVar.name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <a class="btn btn-info" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <div class="table-responsive">
                        <div id="table2_wrapper" class="dataTables_wrapper" role="grid" >
                            <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formlUrl}"
                                           partialList="true" sort="external" size="${items.totalItems}" defaultsort="1"
                                           id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em;">

                                <display:column headerClass="table_header_center" sortable="false" class="text-center" titleKey="label.stt" style="width: 5%;">
                                    ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                </display:column>
                                <display:column headerClass="table_header nowrap" property="tenChiNhanh" sortable="false" class="text-center nowrap" titleKey="Tên chi nhánh" style="20%"/>
                                <display:column headerClass="table_header_center nowrap" property="code" sortName="code" sortable="true" class="text-center" titleKey="Mã cửa hàng" style="20%"/>
                                <display:column headerClass="table_header" property="name" sortName="name" sortable="true" titleKey="Tên cửa hàng" style="20%"/>
                                <display:column headerClass="table_header_center" property="tel" sortName="tel" sortable="true" class="text-center" titleKey="Số điện thoại" style="20%"/>
                                <display:column headerClass="table_header" property="address" sortName="address" sortable="true" titleKey="Địa chỉ" style="20%"/>

                                <display:column headerClass="table_header_center" class="text-center table_menu_items" titleKey="label.action" style="width:10%;white-space:nowrap;">
                                    <a href="${addUrl}?pojo.departmentId=${tableList.departmentId}&pojo.shopCode.shopCodeChiNhanhId=${tableList.shopCode.shopCodeChiNhanhId}" class="tip-top" title="Sửa">Sửa</a>
                                </display:column>
                                <display:setProperty name="paging.banner.item_name" value="cửa hàng"/>
                                <display:setProperty name="paging.banner.items_name" value="cửa hàng"/>
                            </display:table>
                            <form:hidden path="crudaction" id="crudaction"/>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
    <script language="javascript" type="text/javascript">
        $(document).ready(function(){
            // active tab
            setActiveMenu4Admin('#dmCuaHangTab');

            $(".deleteLink").click(function(){
                var id = $(this).attr("id");
                bootbox.confirm('<fmt:message key="delete.confirm.message.title"/>', '<fmt:message key="delete.confirm.message.content"/>', function(r) {
                    if(r){
                        if(id != null && id != undefined){
                            $("<input type='hidden' name='checkList' value='"+id+"'>").appendTo($("#listForm"));
                            $("#crudaction").val("delete");
                            $("#listForm").submit();
                        }
                    }
                });
            });
        });

        function submitForm(){
            $("#crudaction").val("find");
            $('#listForm').submit();
        }
    </script>
</div>