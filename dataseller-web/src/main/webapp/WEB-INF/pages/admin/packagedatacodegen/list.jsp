<%@ page import="com.benluck.vms.mobifonedataseller.common.Constants" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="packagedatacodegen.list_page.heading" /></title>
    <meta name="menu" content="<fmt:message key="packagedatacodegen.list_page.heading" />"/>
    <link href="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">

    <style>
        #tablelist.mobile td.width_50_px{
            width: 50px;
        }
        #tablelist.mobile td.width_300_px{
            width: 300px;
        }

        #tablelist2.mobile td.width_50_px{
            width: 50px;
        }
        #tablelist2.mobile td.width_300_px{
            width: 300px;
        }
        #tablelist2.mobile td.width_250_px{
            width: 250px;
        }
    </style>
</head>

<c:set var="prefix" value="/user" />
<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/packagedatacodegen/list.html"/>
<c:url var="ajaxGenerateCardCodeUrl" value="/ajax/admin/packagedatacodegen/generateCardCode.html"/>

<form:form commandName="item" cssClass="form-horizontal form-label-left" id="listForm" action="${formUrl}" method="post" autocomplete="off" name="listForm">
    <div class="page-title">
        <div class="title_left">
            <h3><fmt:message key="packagedatacodegen.list_page.search" /></h3>
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
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="packageData"><fmt:message key="packagedatacodegen.packagedata.list" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select cssClass="form-control" id="packageData" path="pojo.packageData.packageDataId" >
                                <option value=""><fmt:message key="label.all" /></option>
                                <c:forEach items="${packageDataList}" var="packageData">
                                    <option <c:if test="${item.pojo.packageData.packageDataId eq packageData.packageDataId}">selected="true"</c:if> value="${packageData.packageDataId}">${packageData.name}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="year"><fmt:message key="label.year" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select cssClass="form-control" id="year" path="pojo.year" onchange="javascript: checkLoadPage();" >
                                <c:forEach items="${yearList}" var="year">
                                    <option <c:if test="${item.pojo.year eq year}">selected="true"</c:if> value="${year}">${year}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a class="btn btn-success" onclick="javascript: resetForm();" ><i class="fa fa-refresh" aria-hidden="true"></i> <fmt:message key="label.reset" /></a>
                            <a class="btn btn-primary" onclick="javascript: submitForm();"><i class="fa fa-search" aria-hidden="true"></i> <fmt:message key="label.search" /></a>
                            <%--<a class="btn btn-primary"><i class="fa fa-cc" aria-hidden="true"></i><fmt:message key="label.regenerate_data_code" /></a>--%>
                        </div>
                    </div>
                    <input type="hidden" name="crudaction" value="<%=Constants.ACTION_SEARCH%>"/>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${not empty items.listResult}">
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_title">
                        <h2><fmt:message key="packagedatacodegen.list_page.title" /></h2>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">
                        <c:choose>
                            <c:when test="${items.listResult.size() > 0}">
                                <div id="tableListContainer" style="width: 100%;">
                                    <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formlUrl}"
                                                   partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                                   id="tableList" pagesize="${items.maxPageItems}" export="false"
                                                   class="table table-striped table-bordered" style="margin: 1em 0 1.5em;">

                                        <display:column headerClass="table_header text-center" titleKey="label.stt" sortable="false" class="text-center width_50_px" style="width: 5%;" >
                                            ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                        </display:column>
                                        <display:column headerClass="table_header text-center" property="packageData.name" sortName="packageData.name" sortable="true" class="width_300_px" titleKey="packagedatacodegen.packagedata.name" style="40%"/>
                                        <display:column headerClass="table_header text-center" sortName="packageData.value" sortable="true" titleKey="packagedatacodegen.packagedata.price" class="width_300_px" style="35%">
                                            <fmt:formatNumber type="number" maxFractionDigits="0" value="${tableList.packageData.value}" />
                                        </display:column>
                                        <display:column headerClass="table_header text-center nowrap" class="text-center width_300_px" titleKey="label.status" style="20%">
                                            <c:choose>
                                                <c:when test="${tableList.status eq Constants.PACKAGE_DATA_CODE_GEN_STATUS_PROCESSING}">
                                                    <fmt:message key="packagedatacodegen.generating_card_code" />
                                                </c:when>
                                                <c:when test="${tableList.status eq Constants.PACKAGE_DATA_CODE_GEN_STATUS_FAILED}">
                                                    <fmt:message key="packagedatacodegen.not_yet_generate_card_code_failed" />
                                                </c:when>
                                                <c:when test="${tableList.status eq Constants.PACKAGE_DATA_CODE_GEN_STATUS_SUCCESS}">
                                                    <fmt:message key="packagedatacodegen.generated_card_code" />
                                                </c:when>
                                            </c:choose>
                                        </display:column>
                                        <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.packagedata" /></display:setProperty>
                                        <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.packagedata" /></display:setProperty>
                                    </display:table>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="packagedatacodegen.packagedata.list_not_found" />
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${not empty items.packageDataNotGenerateCardCodeListResult}">
        <div class="clearfix"></div>
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_title">
                        <h2><fmt:message key="packagedatacodegen.list_page_not_generate_card_code.title" /></h2>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">
                        <a class="btn btn-primary" class="btnGenerateCardCode" id="btnGenerateCardCode" onclick="javascript: generateCardCodeForYear();"><i class="fa fa-cc" aria-hidden="true"></i>
                            <fmt:message key="label.generate_data_code" /></a>
                        <c:choose>
                            <c:when test="${items.packageDataNotGenerateCardCodeListResult.size() > 0}">
                                <div id="tableListContainer2" style="width: 100%;">
                                    <display:table name="items.packageDataNotGenerateCardCodeListResult" cellspacing="0" cellpadding="0" requestURI="${formlUrl}"
                                                   partialList="true" sort="external" size="${items.totalPackageDataNotGenerateCardCode}" defaultsort="0"
                                                   id="tableList2" pagesize="${items.maxPageItems}" export="false"
                                                   class="table table-striped table-bordered" style="margin: 1em 0 1.5em;">

                                        <display:column headerClass="table_header text-center" title="<input style=\"width: 15px;\" type=\"checkbox\" class=\"\" name=\"allCheck\" id=\"allCheck\" onclick=\"checkAll('listForm', 'packageDataGenerationCardCodeListId', this)\">" sortable="false" class="text-center width_50_px" style="width: 5%;" >
                                            <input type="checkbox" id="${tableList2.packageDataId}" class="" style="width: 15px;" name="packageDataGenerationCardCodeListId" value="${tableList2.packageDataId}" onclick="checkAllIfOne('listForm', 'packageDataGenerationCardCodeListId', this, 'allCheck')" />
                                        </display:column>
                                        <display:column headerClass="table_header text-center" titleKey="label.stt" sortable="false" class="text-center width_50_px" style="width: 5%;" >
                                            ${tableList2_rowNum + (page * Constants.MAXPAGEITEMS)}
                                        </display:column>
                                        <display:column headerClass="table_header text-center" property="name" sortName="name" sortable="true" titleKey="packagedatacodegen.packagedata.name" class="width_300_px" style="width: 35;%"/>
                                        <display:column headerClass="table_header text-center" sortName="value" sortable="true" titleKey="packagedatacodegen.packagedata.price" class="width_300_px" style="width: 35;%">
                                            <fmt:formatNumber type="number" maxFractionDigits="0" value="${tableList2.value}" />
                                        </display:column>
                                        <display:column headerClass="table_header text-center nowrap" class="text-center width_250_px" sortName="status" sortable="true" titleKey="label.status" style="width: 20%;">
                                            <fmt:message key="packagedatacodegen.not_yet_generate_card_code" />
                                        </display:column>
                                        <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.packagedata" /></display:setProperty>
                                        <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.packagedata" /></display:setProperty>
                                    </display:table>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="packagedatacodegen.packagedata.list_not_found" />
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</form:form>

<script type="text/javascript" src="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script language="javascript" type="text/javascript">
    var $btnGenCardCode = $('#btnGenerateCardCode');
    var $ajaxLoading = $('#ajaxLoading');
    var $tableList2Checkbox = $('#tableList2 input[type="checkbox"]');

    $(document).ready(function(){
        initScrollablePane();
        
        if($btnGenCardCode.length){
            enableOrDisableButtonGenCardCode();
            $( $tableList2Checkbox ).change(function(){
                enableOrDisableButtonGenCardCode();
            });
        }
    });

    function initScrollablePane(){
        if($(window).width() >= mobile_screen_width){
            return;
        }

        var $tableContainer = $('#tableListContainer');
        if($tableContainer.length){
            $('#tableList').addClass('mobile').width(950);
            $tableContainer.mCustomScrollbar({axis:"x"});
        }

        var $tableContainer2 = $('#tableListContainer2');
        if($tableContainer2.length){
            $('#tableList2').addClass('mobile').width(950);
            $tableContainer2.mCustomScrollbar({axis:"x"});
        }
    }

    function enableOrDisableButtonGenCardCode(){
        if ( $tableList2Checkbox.is(":checked") ) {
            $btnGenCardCode.removeClass("disabled")
                .css('pointer-events', 'auto');
        } else {
            $btnGenCardCode.addClass("disabled")
                    .css('pointer-events', 'visible');
        }
    }

    function overlay(){
        if ( $('#ajaxLoading').children().length ){
            $('#ajaxLoading').css({'position': 'absolute',
                'top': '0', 'left': '0',
                'z-index': '9999',
                'width': '100%',
                'height': '100%',
                'background-color': 'rgba(0,0,0,0.5)'}
            )
        } else {
            $('#ajaxLoading').attr('style', '');
        }
    }

    function submitForm(){
        $('#listForm').submit();
    }

    function resetForm(){
        $("input[type='text']").val('');
        selectFirstItemSelect2('#packageData');
        selectFirstItemSelect2('#year');
    }

    function checkLoadPage(){
        var year = $('#year').val();
        if(year != ''){
            document.location.href = '${formUrl}?year=' + year;
        }
    }

    function generateCardCodeForYear(){

        /*------------------------
            Spinner initial
         ------------------------*/
        var opts = {
            lines: 13 // The number of lines to draw
            , length: 12 // The length of each line
            , width: 5 // The line thickness
            , radius: 14 // The radius of the inner circle
            , scale: 1 // Scales overall size of the spinner
            , corners: 0.9 // Corner roundness (0..1)
            , color: '#000' // #rgb or #rrggbb or array of colors
            , opacity: 0.25 // Opacity of the lines
            , rotate: 0 // The rotation offset
            , direction: 1 // 1: clockwise, -1: counterclockwise
            , speed: 1 // Rounds per second
            , trail: 60 // Afterglow percentage
            , fps: 20 // Frames per second when using setTimeout() as a fallback for CSS
            , zIndex: 2e9 // The z-index (defaults to 2000000000)
            , className: 'spinner' // The CSS class to assign to the spinner
            , top: '50%' // Top position relative to parent
            , left: '50%' // Left position relative to parent
            , shadow: false // Whether to render a shadow
            , hwaccel: false // Whether to use hardware acceleration
            , position: 'absolute' // Element positioning
        };
        var target = document.getElementById('ajaxLoading');
        /*------------------------
            //  Spinner initial
         ------------------------*/



        var $packageDataCodeEls = $("input[name='packageDataGenerationCardCodeListId']:checked");

        if($packageDataCodeEls.length > 0){

            var packageDataIdArr = [];
            $packageDataCodeEls.each(function(index, el){
                packageDataIdArr.push($(el).val());
            });

            bootbox.confirm('<fmt:message key="packagedatacodegen.generation.popup.title" />', '<fmt:message key="packagedatacodegen.generation.popup.content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
                if(r){
                    var params = {
                        year: $('#year').val(),
                        packageDataIds: packageDataIdArr.toString()
                    };

                    $.ajax({
                        url: "${ajaxGenerateCardCodeUrl}",
                        type: "GET",
                        data: params,
                        dataType: "json",
                        success: function(res){
                            if(res.r != null){
                                if ( res.r ) {
                                    var spinner = new Spinner(opts).spin(target);
                                    $ajaxLoading.delay(3000).fadeOut(500);
                                    overlay();
                                    setTimeout(function(){
                                        checkLoadPage();
                                    },3000);
                                } else {
                                    bootbox.alert('<fmt:message key="label.alert_title" />', (res.msg != null ? res.msg : '<fmt:message key="packagedatacodegen.generation.error_generation" />'), function(){});
                                }
                            }
                        }
                    });
                }
            });
        }
    }
</script>