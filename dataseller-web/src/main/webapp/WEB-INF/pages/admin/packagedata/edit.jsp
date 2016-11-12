<%--
  Created by IntelliJ IDEA.
  User: thaihoang
  Date: 10/31/2016
  Time: 10:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<head>
    <title><fmt:message key="admin.packagedata.add.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="admin.packagedata.add.heading_page" />"/>
</head>

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_content">
                <form id="listForm" name="listForm" class="form-horizontal form-label-left" action="" method="post" autocomplete="off">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="tenGoiCuoc"><fmt:message key="packagedata.label.tenGoiCuoc" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="tenGoiCuoc" name="pojo.userName" class="form-control" type="text" value="">
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
                            <a class="btn btn-primary" onclick="javascript: submitForm();"><fmt:message key="label.huy" /></a>
                            <a class="btn btn-success" onclick="javascript: resetForm();" ><fmt:message key="label.save" /></a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>