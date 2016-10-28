<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 3/3/15
  Time: 9:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="admin.anhxachinhanh.heading_page" /></title>
    <meta name="heading" content="<fmt:message key="admin.anhxachinhanh.heading_page" />"/>
</head>
<c:url var="formUrl" value="/admin/anhxachinhanh.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="admin.anhxachinhanh.heading_page" /></h2>

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
                    <div class="panel-body">
                        <div class="form-group col-sm-12">
                            <label class="col-sm-2 control-label"><fmt:message key="dmchuongtrinh.label.ten_chuong_trinh"/></label>
                            <div class="col-sm-8">
                                <form:select id="chuongTrinhMenu" path="pojo.chuongTrinh.chuongTrinhId"
                                             cssStyle="width: 250px;"
                                             onchange="javascript: loadBrandByDBLink();">
                                    <c:forEach items="${dmChuongTrinhList}" var="dmChuongTrinh">
                                        <option
                                                <c:if test="${item.pojo.chuongTrinh.chuongTrinhId eq dmChuongTrinh.chuongTrinhId}">selected="true"</c:if>
                                                value="${dmChuongTrinh.chuongTrinhId}">${dmChuongTrinh.description}</option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                    </div>
                    <table class="table-bordered table-striped table">
                        <tr>
                            <th class="table_header_center">STT</th>
                            <th><fmt:message key="label.chi_nhanh" />(Chương Trình)</th>
                            <th><fmt:message key="admin.anhxachinhanh.chi_nhanh_anh_xa" />(DB Link)</th>
                        </tr>
                        <c:set var="indexRow" value="${0}" />
                        <c:forEach items="${chiNhanhList}" var="chiNhanhVar" varStatus="statusVar">
                            <c:set var="indexRow" value="${indexRow + 1}" />
                            <tr>
                                <td class="text-center">${indexRow}</td>
                                <td>${chiNhanhVar.name}</td>
                                <td>
                                    <form:select id="chiNhanhLazyListId_${statusVar.count - 1}" path="branchMappingList[${statusVar.count - 1}].branchId" cssClass="required nohtml" cssStyle="width: 250px;" onchange="javascript: checkIfChosen(this);">
                                        <option value=""><fmt:message key="label.select" /></option>
                                        <c:forEach items="${branchList}" var="branch">
                                            <c:set var="isMapped" value="${0 gt 1}" />
                                            <c:forEach items="${branchMappingList}" var="branchMappingVar">
                                                <c:if test="${branchMappingVar.chiNhanh.chiNhanhId eq chiNhanhVar.chiNhanhId && branchMappingVar.branchId eq branch.branch_Id && !isMapped}">
                                                    <c:set var="isMapped" value="${1 gt 0}" />
                                                </c:if>
                                            </c:forEach>
                                            <option <c:if test="${isMapped}">selected="true"</c:if> value="${branch.branch_Id}">${branch.branch_name}</option>
                                        </c:forEach>
                                    </form:select>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <div class="clear"></div>
                    <a class="btn btn-info" onclick="javascript: submitForm();"><fmt:message key="admin.anhxachinhanh.submit_form" /></a>
                    <input type="hidden" name="crudaction" id="crudaction" value="insert-update" />
                    <c:forEach items="${chiNhanhList}" var="chiNhanhVar" varStatus="statusVar">
                        <input type="hidden" name="branchMappingList[${statusVar.count - 1}].chiNhanh.chiNhanhId" value="${chiNhanhVar.chiNhanhId}" />
                    </c:forEach>
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#anhXaChiNhanhTab');
    });

    <%--function checkIfChosen(selectEl){--%>
        <%--var selectedValue = $.trim($(selectEl).val()),--%>
                <%--duplicated = false;--%>
        <%--$('#reportForm').validate().element(selectEl);--%>
        <%--if(selectedValue != ''){--%>
            <%--$("select[id*='chiNhanhLazyListId_']").each(function(index,el){--%>
                <%--if($.trim($(el).val()) != ''){--%>
                    <%--if($.trim($(el).val()) == selectedValue){--%>
                        <%--if($.trim($(el).attr('id')) != $.trim($(selectEl).attr('id'))){--%>
                            <%--displayError4El(selectEl, '<fmt:message key="admin.anhxachinhanh.duplicated_chi_nhanh_anh_xa" />');--%>
                            <%--duplicated = true;--%>
                        <%--}--%>
                    <%--}--%>
                <%--}--%>
            <%--});--%>
        <%--}--%>
        <%--return duplicated;--%>
    <%--}--%>

    function displayError4El(el, message){
        $(el).closest("td").find("span[class*='error-inline-validate']").each(function(index1, el1){
            $(el1).remove();
        });
        if(message != ''){
            $(el).closest("td").addClass('error');
            $(el).closest("td").append("<span class='error-inline-validate' for='" +$(el).attr('id')+ "' generated='true'>" +message+ "</span>");
        }else{
            $(el).closest("td").append("<span class='error-inline-validate valid' for='" +$(el).attr('id')+ "' generated='true'></span>");
        }
    }
    function loadBrandByDBLink(){
        $('.required').each(function(index, el){
            $(el).removeClass('required');
        });
        $("#crudaction").val("load");
        $('#reportForm').submit();
    }

    function submitForm(){
        if($('#reportForm').valid()){
//            if(validateIfDuplicatedChiNhanhAnhXaChosen()){
                $('#reportForm').submit();
//            }
        }
    }

//    function validateIfDuplicatedChiNhanhAnhXaChosen(){
//        var result = true;
//        $("select[id*='chiNhanhLazyListId_']").each(function(index,el){
//            if(checkIfChosen(el)){
//                result = false;
//            }
//        });
//        return result;
//    }
</script>
</body>
</html>