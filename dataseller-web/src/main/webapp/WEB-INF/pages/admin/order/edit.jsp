<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="titlePage" value="admin.add_donhang.heading_page" />
<c:if test="${not empty item.pojo.orderId}">
    <c:set var="titlePage" value="admin.edit_donhang.heading_page" />
</c:if>

<head>
    <title><fmt:message key="${titlePage}" /></title>
    <meta name="menu" content="<fmt:message key="${titlePage}" />"/>
</head>

<c:set var="prefix" value="/user" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>

<c:url var="formUrl" value="${prefix}/order/add.html" />
<c:if test="${not empty item.pojo.orderId}">
    <c:url var="formUrl" value="${prefix}/order/edit.html" />
</c:if>
<c:url var="backUrl" value="${prefix}/order/list.html" />

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_content">
                <form:form commandName="item" cssClass="form-horizontal form-label-left" id="formEdit" action="${formUrl}" method="post" validate="validate">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="KHDN"><fmt:message key="admin.donhang.label.KHDN" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select id="KHDN" path="pojo.khdn.KHDNId" cssClass="form-control required">
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
                            <form:select id="packageData" path="pojo.packageData.packageDataId" cssClass="form-control required">
                                <option value=""><fmt:message key="label.all" /></option>
                                <c:forEach items="${packageDataList}" var="packageData">
                                    <option data-unitPrice="${packageData.value}" <c:if test="${item.pojo.packageData.packageDataId eq packageData.packageDataId}">selected="true"</c:if> value="${packageData.packageDataId}">${packageData.name}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="quantity"><fmt:message key="admin.donhang.label.quantity" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="quantity" path="pojo.quantity" cssClass="form-control required form-control" ></form:input>
                            <form:errors for="quantity" path="pojo.quantity" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="unitPrice"><fmt:message key="admin.donhang.label.UnitPrice" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="unitPrice" path="pojo.unitPrice" cssClass="form-control required form-control" ></form:input>
                            <form:errors for="unitPrice" path="pojo.unitPrice" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="issuedDate"><fmt:message key="admin.donhang.label.issueDate" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12 xdisplay_inputx form-group has-feedback">
                            <input type="text" class="form-control has-feedback-left data_picker" name="issuedDate" id="issuedDate" aria-describedby="inputSuccess2Status4">
                            <span class="fa fa-calendar-o form-control-feedback left" aria-hidden="true"></span>
                            <%--<span id="inputSuccess2Status4" class="sr-only">(success)</span>--%>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="shippingDate"><fmt:message key="admin.donhang.label.shippingDate" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12 xdisplay_inputx form-group has-feedback">
                            <input type="text" class="form-control has-feedback-left data_picker" name="shippingDate" id="shippingDate" aria-describedby="inputSuccess2Status4">
                            <span class="fa fa-calendar-o form-control-feedback left" aria-hidden="true"></span>
                                <%--<span id="inputSuccess2Status4" class="sr-only">(success)</span>--%>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">
                            <fmt:message key="admin.donhang.label.status" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select cssClass="form-control" id="status" path="pojo.status" cssStyle="width: 150px;">
                                <option <c:if test="${Constants.ORDER_STATUS_PROCESSING eq item.pojo.status}">selected="true"</c:if> value="1"><fmt:message key="label.in_progress" /></option>
                                <option <c:if test="${Constants.ORDER_STATUS_FINISH eq item.pojo.status}">selected="true"</c:if> value="0"><fmt:message key="label.finish" /></option>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a href="${backUrl}" class="btn btn-success"><fmt:message key="label.huy" /></a>&nbsp;
                            <button id="btnSave" class="btn btn-primary">
                                <c:choose>
                                    <c:when test="${not empty item.pojo.orderId}"><fmt:message key="label.update" /></c:when>
                                    <c:otherwise><fmt:message key="label.save" /></c:otherwise>
                                </c:choose>
                            </button>
                        </div>
                    </div>
                    <input type="hidden" name="crudaction" value="insert-update" />
                    <form:hidden path="pojo.orderId" />
                </form:form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        $("#btnSave").click(function(){
            if($('#formEdit').valid()){
                $("#formEdit").submit();
            }
        });

        $('option:not(:first-child)', '#packageData').each(function(idx, el){
            $(el).data("unitPrice", $(el).attr('data-unitPrice')).removeAttr('data-unitPrice');
        });

        $('#packageData').on('change', function(){
            $('#unitPrice').val($(this).find('option:selected').data('unitPrice'));
        });
    });
</script>