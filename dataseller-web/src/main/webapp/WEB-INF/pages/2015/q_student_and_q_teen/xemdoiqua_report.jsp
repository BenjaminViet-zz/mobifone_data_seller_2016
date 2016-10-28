<%@page trimDirectiveWhitespaces="true"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<c:url var="formUrl" value="/q-teen-q-student/xem-diem-doi-qua.html" />
<c:url var="doiQuaUrl" value="/q-teen-q-student/doi-qua.html" />
<c:url var="kppLogoutUrl" value="/q-teen-q-student/logout.html" />
<link href="<c:url value="/themes/promotion/css/jquery.jscrollpane.css"/>" rel="stylesheet">
<style>
    .tableVms th{
        min-width: 250px;
    }
    .traCuuBtn{
        margin-top: 10px;
    }
</style>
<body class="q_student_q_teen_2015_home_page">
<section class="content" style="min-height: 800px;">
    <div class="container">
        <div class="q_teen_q_student_left_img"></div>
        <div class="row margin-top-larger">
            <div class="col-xs-12 col-sm-3">
                <div class=" bg-blue no-padding box-border">
                    <ul id="right_navigation_bar" class="nav">
                        <li class="<c:if test="${not empty xemDiemFlag && xemDiemFlag}">active</c:if> no-padding no-margin"><a href="${formUrl}?crudaction=xem-diem" class="text-white">Lịch sử điểm</a></li>
                        <li class="<c:if test="${not empty doiQuaFlag && doiQuaFlag}">active</c:if> border-top no-padding no-margin"><a class="text-white" href="${formUrl}?crudaction=doi-qua">Đổi quà</a></li>
                    </ul>
                </div>
            </div>
            <div class=" col-xs-12 col-sm-offset-1 col-sm-8 padding-left-larger">
                <article>
                    <form:form id="reportForm" action="${formUrl}" commandName="item" cssClass="form-inline" role="form">
                        <c:choose>
                            <c:when test="${not empty xemDiemFlag && xemDiemFlag}">
                                <h2 class="text-red no-margin padding-bottom-large"><fmt:message key="label.lich_su_diem" /></h2>
                                <c:if test ="${not empty messageResponse}">
                                    <div class="alert alert-${alertType}">
                                        <a class="close" data-dismiss="alert" href="#">&times;</a>
                                            ${messageResponse}
                                    </div>
                                </c:if>
                                <div class="clear"></div>
                                <a class="btn btn-primary" style="float:right;" href="${kppLogoutUrl}"><fmt:message key="cuahanggiaodich.label.try_login_another_thue_bao" /></a>
                                <ul class="nav nav-tabs" id="viewScoreHistoryNavTab">
                                    <li class="active"><a href="#statisticByDate"><fmt:message key="label.theo_ngay" /></a></li>
                                    <li><a href="#statisticByMonth"><fmt:message key="label.theo_thang" /></a></li>
                                </ul>

                                <div class="tab-content viewScoreContent">
                                    <div class="tab-pane active" id="statisticByDate">
                                        <div class="row">
                                            <div class="col-xs-6">
                                                <p><b> <fmt:message key="label.ngay_bat_dau" />: </b></p>
                                            </div>
                                            <div class="col-xs-6">
                                                <p><b> <fmt:message key="label.ngay_ket_thuc" />:</b></p>
                                            </div>
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <input id="fromDate" name="fromDate" class="form-control nohtml text-center prevent_type"
                                                           type="text" placeholder="${symbolDateEmpty}"
                                                           value="<fmt:formatDate value="${item.pojo.fromDate}" pattern="${datePattern}"/>"/>
                                                </div>
                                                <img id="fromDateIcon" class=" img-responsive calendar margin-left-small" src="<c:url value="/themes/promotion/images/Calendar-Time.png"/>" alt="">
                                            </div>
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <input id="toDate" name="toDate" class="form-control nohtml text-center prevent_type"
                                                           type="text" placeholder="${symbolDateEmpty}"
                                                           value="<fmt:formatDate value="${item.pojo.toDate}" pattern="${datePattern}"/>"/>
                                                </div>
                                                <img id="toDateIcon" class=" img-responsive calendar margin-left-small" src="<c:url value="/themes/promotion/images/Calendar-Time.png"/>" alt="">
                                            </div>
                                            <div class="col-xs-12 text-center">
                                                <a class="btn btn-info traCuuBtn" onclick="javascript: searchForm();"><i class="icon-refresh"></i>&nbsp;<fmt:message key="label.search" /></a>
                                            </div>
                                        </div>
                                        <div class="clear"></div>
                                        <br/>
                                        <span class="text-note" style="margin-top: 10px;">CỨ 100 ĐIỂM TÍCH LŨY SẼ ĐƯỢC QUY ĐỔI THÀNH PHIẾU MUA HÀNG TRỊ GIÁ 50.000 ĐỒNG</span>
                                        <div class="clear"></div>
                                        <div id="statisticByDateContainer" style="height: 400px; margin-top: 10px;">
                                            <table class="table-bordered table-striped table">
                                                <tr>
                                                    <th><fmt:message key="label.ngay" /></th>
                                                    <th><fmt:message key="label.diem" /></th>
                                                    <th><fmt:message key="label.loai_ps" /></th>
                                                </tr>
                                                <c:forEach items="${statisticByDateList}" var="statisticDateData">
                                                    <tr>
                                                        <td><fmt:formatDate value="${statisticDateData.ngay_ps}" pattern="${datePattern}" /></td>
                                                        <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${statisticDateData.so_tien_psc / Constants.UNIT_SCORE}" /></td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${fn:trim(statisticDateData.type_input) eq fn:trim(Constants.PSC_TYPE_INPUT_PTM)}">
                                                                    <fmt:message key="label.psc_ptm" />
                                                                </c:when>
                                                                <c:when test="${fn:trim(statisticDateData.type_input) eq fn:trim(Constants.PSC_TYPE_INPUT_PSC)}">
                                                                    <fmt:message key="label.psc_psc" />
                                                                </c:when>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                        </div>
                                        <h4 style="position: relative; top: -10"> <b> <fmt:message key="label.tong_diem" />: <span class=" text-blue">
                                <c:choose>
                                    <c:when test="${not empty statisticByDateList && fn:length(statisticByDateList) gt 0}">
                                        <c:set var="totalPoint" value="${0}" />
                                        <c:forEach items="${statisticByDateList}" var="statisticDateData">
                                            <c:set var="totalPoint" value="${totalPoint + statisticDateData.so_tien_psc}" />
                                        </c:forEach>
                                        <fmt:formatNumber type="number" maxFractionDigits="2" value="${totalPoint / Constants.UNIT_SCORE}" />
                                    </c:when>
                                    <c:otherwise>0</c:otherwise>
                                </c:choose>
                                </span> <fmt:message key="label.diem_1" />
                                        </b></h4>
                                    </div>
                                    <div class="tab-pane" id="statisticByMonth">
                                        <div id="statisticByMonthContainer" style="height: 400px; margin-top: 10px;">
                                            <table class="table-bordered table-striped table">
                                                <tr>
                                                    <th><fmt:message key="label.thang" /></th>
                                                    <th><fmt:message key="label.tong_diem" /></th>
                                                </tr>
                                                <c:forEach items="${statisticByMonthList}" var="statisticMonthData">
                                                    <tr>
                                                        <td>${statisticMonthData.month}</td>
                                                            <td>${statisticMonthData.total}</td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${not empty doiQuaFlag && doiQuaFlag}">
                                <c:set var="allowGiftExchange" value="${1 eq 0}" />
                                <h2 class="text-red no-margin">Đổi quà</h2>
                                <hr />
                                <h4><b> <fmt:message key="label.so_diem_hien_tai" />: <span class="text-blue"><fmt:formatNumber type="number" maxFractionDigits="2" value="${totalCurrentPoint}" /></span> điểm</b></h4>
                                <h3><fmt:message key="label.ma_thuong" />:</h3>
                                <table class="table table-bordered table-striped">
                                    <tr>
                                        <th><fmt:message key="label.ngay" /></th>
                                        <th><fmt:message key="label.ma" /></th>
                                        <th><fmt:message key="label.da_nhan" /></th>
                                    </tr>
                                    <c:forEach items="${danhSachPhieuDoiQuaList}" var="maPhieu">
                                        <tr>
                                            <td><fmt:formatDate value="${maPhieu.ngay_ps}" pattern="${datePattern}" /></td>
                                            <td>${maPhieu.ma_phieu}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${maPhieu.da_doi_qua eq 2}"><fmt:message key="label.da_nhan_qua" /></c:when>
                                                    <%--<c:when test="${maPhieu.da_doi_qua eq 1}"><fmt:message key="label.da_doi_qua" /></c:when>--%>
                                                    <c:otherwise>
                                                        <fmt:message key="label.chua_doi_qua" />
                                                        <c:set var="allowGiftExchange" value="${1 gt 0}" />
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                                <c:if test="${allowGiftExchange}">
                                    <%--<div class="row">--%>
                                        <%--<div class="col-xs-12 col-sm-12 margin-top">--%>
                                            <%--<a class="btn btn-info" onclick="javascript: giftExchange();"><fmt:message key="label.doi_qua" /></a>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                </c:if>
                            </c:when>
                        </c:choose>
                        <input id="statisticBy" type="hidden" name="statistic" />
                    </form:form>
                </article>
            </div>
        </div>
    </div>
</section>
<script type="text/javascript" src="<c:url value="/scripts/jquery/jquery.jscrollpane.min.js"/>"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $('#viewScoreHistoryNavTab a').click(function (e) {
            e.preventDefault();
            $(this).tab('show');
        });

        if(${not empty showStatisticByMonth && showStatisticByMonth}){
            $('#viewScoreHistoryNavTab a:last').tab('show');
        }

        highlightFocusPage('#viewGiftPage');
        $('#statisticByDateContainer').jScrollPane();

        var fromDateVar = $("#fromDate").datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    fromDateVar.hide();
                }).data('datepicker');

        $('#fromDateIcon').click(function () {
            $('#fromDate').focus();
            return true;
        });

        var toDateVar = $("#toDate").datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    toDateVar.hide();
                }).data('datepicker');

        $('#toDateIcon').click(function () {
            $('#toDate').focus();
            return true;
        });
    });

    function searchForm(){
        if($('#viewScoreHistoryNavTab').find('li:first-child').hasClass('active')){
            $('#statisticBy').val('byDate');
        }else{
            $('#statisticBy').val('byMonth');
        }
        $('#reportForm').submit();
    }

    function giftExchange(){
        $('#reportForm').attr('action', '${doiQuaUrl}');
        $('#reportForm').submit();
    }
</script>
</body>