<%--
  Created by IntelliJ IDEA.
  User: thaihoang
  Date: 11/1/2016
  Time: 9:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<head>
    <title><fmt:message key="admin.add_donhang.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="admin.add_donhang.heading_page" />"/>
</head>


<c:url var="backUrl" value="/admin/order/list.html"/>

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_content">
                <form:form commandName="item" cssClass="form-horizontal form-label-left" id="formEdit" action="" method="post" validate="validate">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">
                            <fmt:message key="label.status" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <%--<form:select cssClass="form-control" id="status" path="pojo.status" cssStyle="width: 150px;">
                                <option <c:if test="${1 eq item.pojo.status}">selected="true"</c:if> value="1"><fmt:message key="label.active" /></option>
                                <option <c:if test="${0 eq item.pojo.status}">selected="true"</c:if> value="0"><fmt:message key="label.inactive" /></option>
                            </form:select>--%>
                        </div>
                    </div>
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a href="${backUrl}" class="btn btn-info"><fmt:message key="label.huy" /></a>&nbsp;
                            <button id="btnSave" class="btn btn-primary">
                                <fmt:message key="label.update" />
                            </button>
                        </div>
                    </div>
                    <input type="hidden" name="crudaction" value="insert-update" />

                </form:form>
            </div>
        </div>
    </div>
</div>