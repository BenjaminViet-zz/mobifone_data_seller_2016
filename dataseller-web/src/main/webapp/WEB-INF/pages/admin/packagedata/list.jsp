<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<head>
    <title><fmt:message key="packagedata.list.heading" /></title>
    <meta name="menu" content="<fmt:message key="packagedata.list.heading" />"/>
</head>

<c:set var="prefix" value="/user" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>

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
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="chonGoiCuoc"><fmt:message key="packagedata.label.tenGoiCuoc" />
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
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="giaGoiCuoc"><fmt:message key="packagedata.label.giaGoiCuoc" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="giaGoiCuoc" name="pojo.userName" class="form-control" type="text" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="dungLuongMienPhi"><fmt:message key="packagedata.label.dungLuongMienPhi" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="dungLuongMienPhi" name="pojo.userName" class="form-control" type="text" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="thoiGianSuDung"><fmt:message key="packagedata.label.thoiGianSuDung" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="thoiGianSuDung" name="pojo.userName" class="form-control" type="text" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="soLanGiaHan"><fmt:message key="packagedata.label.soLanGiaHan" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="soLanGiaHan" name="pojo.userName" class="form-control" type="text" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="tk"><fmt:message key="packagedata.label.tk" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="tk" name="pojo.userName" class="form-control" type="text" value="">
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
                <table id="tableList" cellpadding="0" class="table table-striped table-bordered text-center" style="margin: 1em 0 1.5em;" cellspacing="0">
                    <thead>
                    <tr>
                        <th class="table_header text-center">STT</th>
                        <th class="table_header text-center sortable">
                            <a href="">Tên gói</a></th>
                        <th class="table_header text-center sortable">
                            <a href="">Giá gói(đ)</a></th>
                        <th class="table_header text-center sortable">DL miễn phí(MB)</th>
                        <th class="table_header text-center nowrap sortable">
                            <a href="">Thời gian sử dụng</a></th>
                        <th class="table_header text-center sortable">Số lần gia hạn</th>
                        <th class="table_header text-center">TK</th>
                        <th class="table_header text-center">Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                        <tr class="odd">
                            <td style="width: 5%;"></td>
                            <td style="width: 10%"></td>
                            <td style="width: 10%"></td>
                            <td style="width: 30%"></td>
                            <td style="width: 15%"></td>
                            <td style="width: 10%"></td>
                            <td style="width: 10%"></td>
                            <td style="width:10%;">
                                <a href="/admin/user/edit.html?pojo.userId=2" class="tip-top" title="<fmt:message key="label.edit" />"><fmt:message key="label.edit" /></a>
                                <a class="tip-top"><fmt:message key="label.delete" /></a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>