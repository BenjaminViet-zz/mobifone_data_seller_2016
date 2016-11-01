<%@ taglib prefix="ft" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: thaihoang
  Date: 10/31/2016
  Time: 11:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>


<head>
    <title><fmt:message key="packagedata.list.heading" /></title>
    <meta name="menu" content="<fmt:message key="packagedata.list.heading" />"/>
</head>


<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="admin.data_code_list.heading_page" /></h3>
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
                    <%--<div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="LastModified"><fmt:message key="admin.donhang.label.LastModified" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="LastModified" name="pojo.LastModified" class="form-control" type="text" value="">
                        </div>
                    </div>--%>
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
            </div>
        </div>
    </div>
</div>