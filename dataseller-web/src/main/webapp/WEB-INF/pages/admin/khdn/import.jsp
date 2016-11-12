<%--
  Created by IntelliJ IDEA.
  User: thaihoang
  Date: 11/1/2016
  Time: 9:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.benluck.vms.mobifonedataseller.common.Constants" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<head>
    <title><fmt:message key="admin.khdn.import_page.heading" /></title>
    <meta name="menu" content="<fmt:message key="admin.khdn.import_page.heading" />"/>
</head>

<c:set var="prefix" value="/user" />
<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/khdn/import.html"/>
<c:url var="ajaxProcessImportUrl" value="/ajax/khdn/import.html"/>

<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="admin.khdn.import_page.page_title" /></h3>
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

                <form:form commandName="item" cssClass="form-horizontal form-label-left" id="listForm" action="${formUrl}" method="post" autocomplete="off" name="listForm" enctype="multipart/form-data">
                    <!-- Start SmartWizard Content -->
                    <div id="wizard" class="form_wizard wizard_horizontal">
                        <ul class="wizard_steps">
                            <li>
                                <a href="#step-1">
                                    <span class="step_no">1</span>
                                    <span class="step_descr">
                                        <fmt:message key="label.step_1" /><br />
                                        <small><fmt:message key="admin.khdn.import_page.step_1_title" /></small>
                                    </span>
                                </a>
                            </li>
                            <li>
                                <a href="#step-2">
                                    <span class="step_no">2</span>
                                    <span class="step_descr">
                                        <fmt:message key="label.step_2" /><br />
                                        <small><fmt:message key="admin.khdn.import_page.step_2_title" /></small>
                                    </span>
                                </a>
                            </li>
                            <li>
                                <a href="#step-3">
                                    <span class="step_no">3</span>
                                    <span class="step_descr">
                                        <fmt:message key="label.step_3" /><br />
                                        <small><fmt:message key="admin.khdn.import_page.step_3_title" /></small>
                                    </span>
                                </a>
                            </li>
                        </ul>
                        <div id="step-1">
                            <div class="row-fluid pane_info">
                                <div class="widget-box">
                                    <div class="widget-content nopadding">
                                        <div style="margin: 10px;">
                                            <p>
                                                <fmt:message key="import.import_page.step1.instruction1"></fmt:message>
                                            </p>
                                            <div style="padding: 5px 0px 12px 60px;">
                                                <a id="linkTemplate" href="<c:url value="/files/help/template_import_khdn.xls"/>"><fmt:message key="import.import_page.step1.instruction2"/></a>
                                            </div>
                                            <p><fmt:message key="import.import_page.step1.instruction3"/></p>

                                        </div>
                                        <div class="clear"></div>
                                        <div class="control-group">
                                            <label class="control-label"></label>
                                            <div class="controls">
                                                <input type="file" name="file" id="file"  size="40"/>
                                            </div>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div id="step-2">
                            <table cellspacing="0" cellpadding="0" class="table table-striped table-bordered" style="margin: 1em 0 1.5em;">
                                <thead>
                                    <tr>
                                        <th class="table_header text-center"><fmt:message key="label.stt" /></th>
                                        <th class="table_header text-center"><fmt:message key="import.review_list.shop_code" /></th>
                                        <th class="table_header text-center"><fmt:message key="import.review_list.shop_name" /></th>
                                        <th class="table_header text-center"><fmt:message key="import.review_list.mst" /></th>
                                        <th class="table_header text-center"><fmt:message key="import.review_list.gpkd" /></th>
                                        <th class="table_header text-center"><fmt:message key="import.review_list.ngay_ky_hop_dong" /></th>
                                        <th class="table_header text-center"><fmt:message key="import.review_list.stb_vas" /></th>
                                        <th class="table_header text-center"><fmt:message key="import.review_list.error_message" /></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${item.importKHDNDTOList}" var="importKHDNDTO" varStatus="status">
                                        <tr>
                                            <td class="text-center">${status.count}</td>
                                            <td class="text-center">${importKHDNDTO.shopCode}</td>
                                            <td>${importKHDNDTO.name}</td>
                                            <td class="text-center">${importKHDNDTO.mst}</td>
                                            <td class="text-center">${importKHDNDTO.gpkd}</td>
                                            <td class="text-center"><fmt:formatDate value="${importKHDNDTO.issuedContractDate}" pattern="${datePattern}" /></td>
                                            <td class="text-center">${importKHDNDTO.stb_vas}</td>
                                            <td class="text-error">${importKHDNDTO.errorMessage}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div id="step-3">
                            <fmt:message key="admin.khdn.import_page.step_3_content" />
                        </div>

                    </div>
                    <!-- End SmartWizard Content -->

                    <c:set var="cruaction" value="${Constants.ACTION_UPLOAD}" />
                    <c:if test="${item.stepImportIndex eq Constants.IMPORT_ORDER_STEP_2_UPLOAD}">
                        <c:set var="cruaction" value="${Constants.ACTION_IMPORT}" />
                    </c:if>

                    <input type="hidden" name="crudaction" value="${cruaction}"/>
                </form:form>
            </div>
        </div>
    </div>
</div>

<!-- jQuery Smart Wizard -->
<script src="<c:url value="/themes/newteample/vendors/jQuery-Smart-Wizard/js/jquery.smartWizard.js" />"></script>
<script language="javascript" type="text/javascript">
    $(document).ready(function() {
        handleButtons();
        handleFile();
    });

    function handleFile(){
        var $buttonNext = $('.buttonNext');

        $('#file').on('change', function(){
            $buttonNext.removeClass('buttonDisabled');
            $buttonNext.data('prevent-goforward', true);
        });
    }

    function handleButtons(){
        // check step index == 1
        // add class 'hide' on step content 1

        var $wizardForm = $('#wizard');

        // update selected step by index
        $wizardForm.smartWizard({
            transitionEffect: 'slide'
        });

        var $buttonNext = $('.buttonNext');
        var $buttonPrev = $('.buttonPrevious');
        var $buttonFinish = $('.buttonFinish');

        $buttonFinish.data('manual-handle', true);

        if(${item.stepImportIndex eq Constants.IMPORT_ORDER_STEP_1_CHOOSE_FILE}){
            $buttonNext.on('click', function(e){
                e.stopPropagation();
                e.preventDefault();
                $('#listForm').submit();
            });

            // need to choose a file to upload to enable this button.
            $buttonNext.addClass('buttonDisabled');
        }else if(${item.stepImportIndex eq Constants.IMPORT_ORDER_STEP_2_UPLOAD}){

            // trigger click on button Next to go to Step 2
            $buttonNext.trigger('click');

            // remove class hide in step 1
            // to do code here

            if(${not empty item.errorMessage}){
                // disable button next in case of errors in Import file.
                $buttonNext.addClass('buttonDisabled');

                $buttonNext.on('click', function(e){
                    e.stopPropagation();
                    e.preventDefault();
                });
            }

            // process for button Finish
            $buttonFinish.on('click', function(e){
                if($('#step-3:visible').length){
                    bootbox.confirm('<fmt:message key="import.popup.title" />', '<fmt:message key="import.popup.content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
                        if(r){
                            $.ajax({
                                url: "${ajaxProcessImportUrl}",
                                type: "GET",
                                dataType: "json",
                                success: function(res){
                                    if(!res.r){
                                        bootbox.alert('<fmt:message key="import.popup.title" />', (res.msg != null ? res.msg : '<fmt:message key="import.unknown_error" />'), function(){});
                                    }else{
                                        // REDIRECT TO import page for step 1.
                                    }
                                }
                            });
                        }
                    });
                }
            });

            // handle button previous
            $buttonPrev.on('click', function(){
                if(!$buttonFinish.hasClass('buttonDisabled')){
                    $buttonFinish.addClass('buttonDisabled');
                }
            });

            // handle disable step label
        }
    }
</script>