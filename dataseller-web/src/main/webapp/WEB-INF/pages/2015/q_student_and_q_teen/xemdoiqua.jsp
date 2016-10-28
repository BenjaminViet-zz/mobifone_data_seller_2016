<%@page trimDirectiveWhitespaces="true"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<c:url var="formUrl" value="/q-teen-q-student/xem-diem-doi-qua.html" />
<link href="<c:url value="/themes/promotion/css/jquery.jscrollpane.css"/>" rel="stylesheet">
<body class="q_student_q_teen_2015_home_page">
<section class="content" style="min-height: 800px;">
    <div class="container">
        <div class="row">
            <div class="q_teen_q_student_left_img"></div>
            <div class="col-sm-offset-5 col-sm-7 col-xs-12">
                <article>
                    <c:choose>
                        <c:when test="${not empty enableLoginFunction && enableLoginFunction}">
                            <form:form commandName="item" cssClass="form-inline" role="form" action="${formUrl}">
                                <!-- InstanceBeginEditable name="Content" -->
                                <h1 class="text-red margin-bottom-large"><b>XEM ĐIỂM - ĐỔI QUÀ</b></h1>

                                <c:if test="${not empty messageResponse}">
                                    <div class="alert alert-${alertType}">
                                            ${messageResponse}
                                    </div>
                                </c:if>

                                <h1 class="text-red padding-bottom-large"><b>Lấy Mã Đăng Nhập</b></h1>
                                <p> Nhập số điện thoại của bạn để lấy mã.</p>
                                <div class="form-group">
                                    <label class="sr-only" for="exampleInputEmail2"><fmt:message key="user.label.email" /></label>
                                    <form:input path="pojo.userName" cssClass="form-control onlyNumberInputForceValue" maxlength="11" id="exampleInputEmail1" placeholder="Nhập số điện thoại" />
                                </div>
                                <button type="submit" class="btn btn-info">Xác nhận</button>
                                <p>&nbsp;</p>
                                <div class="clear"></div>
                                <p>Phần dành cho cửa hàng MobiFone, click tại <a href="/login.jsp?error=0">đây</a> để đăng nhập.</p>
                                <!-- InstanceEndEditable -->
                            </form:form>
                        </c:when>
                        <c:otherwise>
                            <h4 class="bg-blue padding-round text-center text-white box-border margin-top">Hệ thống đang cập nhật, Xin vui lòng quay lại vào ngày 20/10/2014</h4>
                            <p>&nbsp;</p>
                            <p>&nbsp;</p>
                        </c:otherwise>
                    </c:choose>
                </article>
            </div>
        </div>
    </div>
</section>
<script type="text/javascript" src="<c:url value="/scripts/jquery/jquery.jscrollpane.min.js"/>"></script>
<script type="text/javascript">
    $(document).ready(function(){
        highlightFocusPage('#viewGiftPage');
        $('#historyContainer').jScrollPane();

        var fromDateVar = $("#fromDate").datepicker({
            format: 'dd/mm/yyyy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    fromDateVar.hide();
                }).data('datepicker');

        $('#fromDateIcon').click(function () {
            $('#signedDate').focus();
            return true;
        });

        var toDateVar = $("#toDate").datepicker({
            format: 'dd/mm/yyyy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    toDateVar.hide();
                }).data('datepicker');

        $('#toDateIcon').click(function () {
            $('#signedDate').focus();
            return true;
        });

        $('.onlyNumberInputForceValue').keyup(function(){
            $(this).val(function(el,value){
                return value.replace(/[^\d]/g,'');
            });
        });
    });
</script>
</body>