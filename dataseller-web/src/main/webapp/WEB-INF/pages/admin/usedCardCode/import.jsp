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
    <title><fmt:message key="import_used_card_code.heading" /></title>
    <meta name="menu" content="<fmt:message key="import_used_card_code.heading" />"/>
    <link href="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">

    <style>
        #tablelist.mobile td.width_50_px{
            width: 50px;
        }
        #tablelist.mobile td.width_200_px{
            width: 200px;
        }

        #tablelist.mobile td.width_350_px{
            width: 350px;
        }
    </style>
</head>

<c:set var="prefix" value="${vms:getPrefixUrl()}" />
<c:url var="formUrl" value="${prefix}/import_used_card_code.html"/>

<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="import_used_card_code.page_title" /></h3>
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
                    <div id="wizard" class="form_wizard wizard_horizontal" style="max-height: 700px;">
                        <ul class="wizard_steps">
                            <li>
                                <a href="#step-1">
                                    <span class="step_no">1</span>
                                    <span class="step_descr text-primary">
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
                        <div id="step-1" style="<c:if test="${item.stepImportIndex == Constants.IMPORT_CARD_CODE_STEP_2_UPLOAD}">display: none;</c:if>">
                            <div class="row-fluid pane_info">
                                <div class="widget-box">
                                    <div class="widget-content nopadding">
                                        <div class="m-t-30">
                                            <div class="alert alert-danger alert-dismissible fade in">
                                                <fmt:message key="import_used_card_code.notice_message" ></fmt:message>
                                            </div>
                                            <p><fmt:message key="import.import_page.step1.instruction1"></fmt:message></p>
                                            <p><fmt:message key="import_used_card_code.import_page.step1.instruction3"/></p>
                                            <div class="m-b-10">
                                                <a class="btn btn-info" id="linkTemplate" href="<c:url value="/files/help/template_import_used_card_code.xlsx"/>"><i class="fa fa-download" aria-hidden="true"></i> <fmt:message key="import.import_page.step1.instruction2"/></a>

                                                <div class="chonFileImport">
                                                    <label for="file" class="btn btn-info">
                                                        <i class="fa fa-file-excel-o" aria-hidden="true"></i> <fmt:message key="import.selectFile"></fmt:message>
                                                    </label>
                                                    <input id="file" type="file" name="file" accept=".xlsx" />
                                                </div>

                                            </div>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="step-2">
                            <c:if test="${item.importUsedCardCode}">
                                <div class="alert alert-info alert-dismissible fade in">
                                    <fmt:message key="import_used_card_code.review_list.imported_used_card_code_before_message" />
                                </div>
                            </c:if>
                            <c:if test="${not empty item.errorImportUsedCardCodeList && item.errorImportUsedCardCodeList.size() > 0}">
                                <div id="tableListContainer" style="width: 100%; height: 400px;">
                                    <table id="tableList" cellspacing="0" cellpadding="0" class="table table-striped table-bordered" style="margin: 1em 0 1.5em; height: ${39 + (item.importUsedCardCodeList.size() * 38)}px">
                                        <thead>
                                        <tr>
                                            <th class="table_header text-center"><fmt:message key="label.stt" /></th>
                                            <th class="table_header text-center"><fmt:message key="import_used_card_code.review_list.card_code" /></th>
                                            <c:if test="${not empty item.errorMessage && !item.hasError}">
                                                <th class="table_header text-center"><fmt:message key="import_used_card_code.review_list.status" /></th>
                                            </c:if>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${item.importUsedCardCodeList}" var="usedCardCodeDTO" varStatus="status">
                                            <tr class="<c:if test="${not empty importKHDNDTO.errorMessage}">line-error</c:if> ">
                                                <td style="width: 3%;" class="text-center width_50px">${status.count}</td>
                                                <td style="width: 37%;" class="text-center width_200px">${usedCardCodeDTO.cardCode}</td>
                                                <c:if test="${not empty item.errorMessage && !item.hasError}">
                                                    <td style="width: 60%;" class="width_350px">${usedCardCodeDTO.errorMessage}</td>
                                                </c:if>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:if>
                        </div>
                        <div id="step-3">
                            <div class="m-t-30">
                                <fmt:message key="import_used_card_code.review_list.step_3" />
                            </div>
                        </div>

                    </div>
                    <!-- End SmartWizard Content -->

                    <input id="crudaction" type="hidden" name="crudaction" value="${Constants.ACTION_IMPORT}"/>
                </form:form>
            </div>
        </div>
    </div>
</div>

<!-- jQuery Smart Wizard -->
<script src="<c:url value="/themes/newteample/vendors/jQuery-Smart-Wizard/js/jquery.smartWizard_v1.1.js" />"></script>
<script type="text/javascript" src="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script language="javascript" type="text/javascript">
    var curStepIndex = ${item.stepImportIndex};
    var $buttonNext = null;
    var $buttonPrev = null;
    var $buttonFinish = null;
    var $formEl = $('#listForm');
    var tableHeaderHeight = 39;
    var tableTrHeight = 38;
    var $ajaxLoading = $('#ajaxLoading');

    $(document).ready(function() {
        handleButtons();
        handleFile();
    });

    function initScrollablePane(){
        if($('#tableListContainer').length){
            <c:choose>
                <c:when test="${item.stepImportIndex == Constants.IMPORT_CARD_CODE_STEP_2_UPLOAD && not empty item.errorMessage && !item.hasError}">
                    if($(window).width() >= mobile_screen_width){
                        $('#tableListContainer').mCustomScrollbar();
                        return;
                    }else{
                        $('#tableList').addClass('mobile').width(600);
                        $('#tableListContainer').mCustomScrollbar();
                    }
                </c:when>
                <c:otherwise>
                    $('#tableListContainer').mCustomScrollbar();
                </c:otherwise>
            </c:choose>
        }
    }

    function handleFile(){
        var $buttonNext = $('.buttonNext');

        $('#file').on('change', function(){
            if ( $(this).val() != '' ) {
                $(this).closest('.chonFileImport').find('label').addClass('btn-success').removeClass('btn-info').html('<i class="fa fa-check" aria-hidden="true"></i> <fmt:message key="import.selectFileSuccess"></fmt:message>');
                isShowNextButton(true);
            } else {
                $(this).closest('.chonFileImport').find('label').addClass('btn-info').removeClass('btn-success').html('<i class="fa fa-file-excel-o" aria-hidden="true"></i> <fmt:message key="import.selectFile"></fmt:message>');
                isShowNextButton(false);
            }
        });
    }


    function handleButtons(){
        // check step index == 1
        // add class 'hide' on step content 1
        if ( curStepIndex == ${Constants.IMPORT_CARD_CODE_STEP_2_UPLOAD} ) {
            $('#step-1').addClass('hidden');
        }

        var $wizardForm = $('#wizard');
        $wizardForm.smartWizard({
            transitionEffect: 'slide'
        });

        $buttonNext = $('.buttonNext');
        $buttonPrev = $('.buttonPrevious');
        $buttonFinish = $('.buttonFinish');

        $buttonFinish.data('manual-handle', true);

        if( curStepIndex == ${Constants.IMPORT_CARD_CODE_STEP_1_CHOOSE_FILE} ){
            isShowNextButton(false);

        } else if( curStepIndex == ${Constants.IMPORT_CARD_CODE_STEP_2_UPLOAD} ){
            // trigger click on button Next to go to Step 2
            $buttonNext.trigger('click');

            initScrollablePane();

            // remove class hide in step 1
            $('#step-1').removeClass('hidden');
            // to do code here

            if(${not empty item.errorMessage}){
                // disable button next in case of errors in Import file.
                isShowNextButton(false);
            }

            // process for button Finish
            $buttonFinish.on('click', function(e){
                if($('#step-3:visible').length){
                    bootbox.confirm('<fmt:message key="import.popup.title" />', '<fmt:message key="import.popup.content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
                        if(r){
                            $formEl.submit();
                        }
                    });
                }
            });
        }

        $buttonNext.on('click', function(e){
            if(curStepIndex == ${Constants.IMPORT_CARD_CODE_STEP_1_CHOOSE_FILE}){
                e.stopPropagation();
                e.preventDefault();
                $('#crudaction').val('${Constants.ACTION_UPLOAD}');

                showLoading();

                $formEl.submit();
            } else if(curStepIndex == ${Constants.IMPORT_CARD_CODE_STEP_2_UPLOAD} && ${empty item.errorMessage} && ${!item.hasError}){
                $buttonFinish.removeClass('disableBtnFinish');
            }
            curStepIndex++;
        });

        // handle button previous
        $buttonPrev.on('click', function(){
            $buttonFinish.addClass('disableBtnFinish');
            curStepIndex--;
            if(curStepIndex == ${Constants.IMPORT_CARD_CODE_STEP_1_CHOOSE_FILE}){
                isShowNextButton(false);
            }
        });
    }

    function showLoading(){
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

        var spinner = new Spinner(opts).spin(target);
        overlay();
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

    /**
     *   Disable or enable button next.
     * @param flag True: enable, else disable
     */
    function isShowNextButton(flag){
        if( !flag ){
            $buttonNext.addClass('buttonDisabled')
                    .data('prevent-goforward', true);
        }else{
            $buttonNext.removeClass('buttonDisabled')
                    .removeData('prevent-goforward');
        }
    }
</script>