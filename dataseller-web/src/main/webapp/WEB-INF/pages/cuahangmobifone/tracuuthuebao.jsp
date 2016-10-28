<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="cuahanggiaodich.tracuusothuebao.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="cuahanggiaodich.tracuumaphieu.heading_page" />"/>
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
<c:url var="formUrl" value="${prefix}/tracuutheosothuebao.html"/>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="cuahanggiaodich.tracuusothuebao.label" /></h2>

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
            <div style="clear: both;" ></div>
            <div class="row-fluid report-filter">
                <form:form commandName="item" id="listThueBaoForm" action="${formUrl}" method="post" autocomplete="off">
                    <div class="col-md-10">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.so_thue_bao" /></label>
                                    <div class="col-sm-8">
                                        <form:input id="sothuebao" path="pojo.thue_bao" cssClass="validate_phone_number nohtml form-control" />
                                        <form:errors for="sothuebao" path="pojo.thue_bao" cssClass="error-inline-validate" />
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.tinhtrang" /></label>
                                    <div class="col-sm-8"><label><form:radiobutton id="radioAll" path="pojo.da_doi_qua" value="-1"/>&nbsp;<fmt:message key="label.all" /></label>
                                        <%--&nbsp;&nbsp;&nbsp;&nbsp;<span><label><form:radiobutton path="pojo.da_doi_qua" value="${Constants.CHUA_XAC_NHAN_DOI_QUA}"/>&nbsp;<fmt:message key="label.chua_xac_nhan_doi_qua" /></label></span>--%>
                                        <%--&nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="pojo.da_doi_qua" value="${Constants.DA_XAC_NHAN_DOI_QUA}"/>&nbsp;<fmt:message key="label.da_xac_nhan_doi_qua" /></label>--%>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="pojo.da_doi_qua" value="${Constants.CHUA_XAC_NHAN_DOI_QUA}"/>&nbsp;<fmt:message key="label.chua_doi_qua" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="pojo.da_doi_qua" value="${Constants.DA_GIAO_QUA}"/>&nbsp;<fmt:message key="label.da_giao_qua" /></label>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <a class="btn btn-primary" onclick="submitForm();"><fmt:message key="label.search" /></a>
                                    <a class="btn btn-info" onclick="javascript: reset();"><fmt:message key="label.reset" /></a>
                                    <c:if test="${not empty items.listResult}">
                                        <security:authorize ifAnyGranted="NHANVIEN">
                                            <a class="btn btn-info" onclick="javascript: giaoQua();"><fmt:message key="label.giao_qua" /></a>
                                        </security:authorize>
                                    </c:if>
                                    <c:if test="${not empty items.listResult}">
                                        <a class="btn btn-info" onclick="javascript: export2Excel();"><fmt:message key="label.export" /></a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <c:if test="${not empty items.listResult}">
                        <div class="table-responsive">
                            <div id="tableList_wrapper" class="dataTables_wrapper" role="grid" >
                                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formlUrl}"
                                               partialList="true" sort="external" size="${items.totalItems}" defaultsort="1"
                                               id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em;">
                                    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;">
                                        ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                    </display:column>
                                    <display:column headerClass="table_header_center" sortable="false" class="text-center" style="width: 5%;" title="<input type=\"checkbox\" name=\"allCheck\" id=\"allCheck\" onclick=\"checkAll('listThueBaoForm', 'checkList', this)\">">
                                        <c:if test="${tableList.da_doi_qua ne 2}">
                                            <input type="checkbox" name="checkList" value="${tableList.ma_phieu}" onclick="checkAllIfOne('listThueBaoForm', 'checkList', this, 'allCheck')">
                                        </c:if>
                                    </display:column>
                                    <display:column headerClass="table_header nowrap" property="ma_phieu" sortName="ma_phieu" sortable="true" titleKey="label.maPhieu" style="width: 10%;"/>
                                    <display:column headerClass="table_header nowrap" property="thue_bao" sortName="thue_bao" sortable="true" titleKey="label.thueBao" style="width: 10%;"/>
                                    <display:column headerClass="table_header nowrap" sortName="ngay_ps" sortable="true" titleKey="label.ngay.ps" style="width: 10%;">
                                        <fmt:formatDate value="${tableList.ngay_ps}" pattern="${datePattern}" />
                                    </display:column>
                                    <display:column headerClass="table_header_center nowrap" sortName="da_doi_qua" sortable="true" titleKey="label.status" class="text-center nowrap" style="width: 10%;">
                                        <c:choose>
                                            <c:when test = "${tableList.da_doi_qua eq 2}"><fmt:message key="label.da_giao_qua" /></c:when>
                                            <%--<c:when test = "${tableList.da_doi_qua eq 1}"><fmt:message key="label.da_xac_nhan_doi_qua" /></c:when>--%>
                                            <c:otherwise><fmt:message key="label.chua_doi_qua" /></c:otherwise>
                                        </c:choose>
                                    </display:column>
                                    <display:column headerClass="table_header_center nowrap" sortName="ngay_doi_qua" sortable="true" titleKey="label.ngay_doi_qua" class="text-center" style="width: 15%;" >
                                        <fmt:formatDate value="${tableList.ngay_doi_qua}" pattern="${datePattern}"/>
                                    </display:column>
                                    <display:column headerClass="table_header" sortable="false" titleKey="label.cua_hang" property="tenCuaHang" style="width: 20%;" />
                                    <display:column headerClass="table_header_center" sortable="false" titleKey="label.diem_tich_luy" class="text-center" style="width: 10%;" >
                                        <fmt:formatNumber type="number" value="${tableList.diemTichLuy}" />
                                    </display:column>
                                    <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.phieu_doi_qua" /></display:setProperty>
                                    <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.phieu_doi_qua" /></display:setProperty>
                                </display:table>
                            </div>
                        </div>
                    </c:if>
                    <form:hidden path="crudaction"/>
                </form:form>
            </div>
        </div>
    </div>

    <script language="javascript" type="text/javascript">
        $(document).ready(function(){
            setActiveMenu4Admin('#tdcgTraCuuTheoThueBaoTab');
            $('checkbox').select2();

            jQuery.validator.addClassRules('validate_phone_number', {
                validatePhoneNumber: true
            });

            jQuery.validator.addMethod("validatePhoneNumber", function () {
                return validatePhoneNumber($.trim($('#sothuebao').val()));
            }, "<fmt:message key="user.msg.invalid_phone_number"/>");
        });

        function giaoQua(){
            $('#crudaction').val('doi-qua');
            if(validateBeforeSubmit()){
                bootbox.confirm('<fmt:message key="label.confirm_title"/>', '<fmt:message key="label.confirm_operation_content"/>', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r) {
                    if(r){
                        $('#listThueBaoForm').submit();
                    }
                });
            }else{
                bootbox.alert('<fmt:message key="label.alert_title" />', '<fmt:message key="cuahanggiaodich.label.alert_no_ma_phieu_chosen" />', '<fmt:message key="label.dong_y" />', function(r){});
            }
        }

        function validateBeforeSubmit(){
            var result = false;
            $("#tableList input[name*='checkList']").each(function(index, el){
                if($(el).is(':checked')){
                    result = true;
                }
            });
            return result;
        }

        function reset(){
            $('#sothuebao').val('');
            $('#radioAll').attr('checked', true);
        }

        function submitForm(){
            if($('#listThueBaoForm').valid()){
                $("#crudaction").val("search");
                $('#listThueBaoForm').submit();
            }
        }

        function export2Excel(){
            $("#crudaction").val("export");
            $('#listThueBaoForm').submit();
        }
    </script>
</div>