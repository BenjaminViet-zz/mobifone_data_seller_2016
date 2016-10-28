<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<c:url var="formUrl" value="/kenhphanphoi/xem-diem-doi-thuong.html" />
<body class="sub-page">
    <div class=" container">
        <section class="content background-none">
            <div class="row">
                <div class="col-md-offset-5 col-md-7 col-xs-12">
                    <article>
                        <form:form id="loginForm" commandName="item" cssClass="form-inline" role="form" action="${formUrl}">
                            <!-- InstanceBeginEditable name="Content" -->
                            <c:choose>
                                <c:when test="${not empty enableLogin && enableLogin}">
                                    <h1 class="text-red padding-bottom-large"><b>Lấy Mã Đăng Nhập</b></h1>
                                    <c:if test="${not empty messageResponse}">
                                        <div class="alert alert-${alertType}">
                                                ${messageResponse}
                                        </div>
                                    </c:if>
                                    <div class="clear"></div>
                                    <p> Nhập số MobiEZ của bạn để lấy mã.</p>
                                    <div class="form-group">
                                        <form:input path="pojo.userName" cssClass="form-control onlyNumberInputForceValue" maxlength="11" id="mobiEZ" placeholder="Nhập số điện thoại" />
                                    </div>
                                    <a class="btn btn-danger" onclick="javascript: submitForm();">Xác nhận</a>
                                    <p>&nbsp;</p>
                                    <div class="clear"></div>
                                    <p>Phần dành cho chi nhánh, click tại <a href="/login.jsp?error=0">đây</a> để đăng nhập.</p>
                                </c:when>
                                <c:when test="${not empty enterPasswordOTP && enterPasswordOTP}">
                                    <h1 class="text-red padding-bottom-large"><b>Đăng Nhập</b></h1>
                                    <c:if test="${not empty messageResponse}">
                                        <div class="alert alert-${alertType}">
                                                ${messageResponse}
                                        </div>
                                    </c:if>
                                    <div class="clear"></div>
                                    <p>Nhập mã xác nhận của bạn.</p>
                                    <div class="form-group">
                                        <label class="sr-only" for="maxacnhan">mã xác nhận</label>
                                        <form:input path="pojo.password" cssClass="form-control" id="maxacnhan" placeholder="Nhập mã xác nhận" />
                                    </div>
                                    <a class="btn btn-danger" onclick="javascript: submitForm();">Đăng nhập</a>
                                    <p>&nbsp;</p>
                                </c:when>
                                <c:otherwise>
                                    <div class="text-center"> <h1 class="text-red margin-bottom-large"><b>XEM ĐIỂM - ĐỔI THƯỞNG</b></h1></div>

                                    <h4 style="font-size: 22px;line-height: 1.6;" class="bg-blue padding-round text-center text-white box-border margin-top">Hệ thống đang cập nhật, Xin vui lòng quay lại vào ngày 30/10/2014</h4>
                                    <p>&nbsp;</p>
                                    <p>&nbsp;</p>
                                </c:otherwise>
                            </c:choose>
                            <!-- InstanceEndEditable -->
                        </form:form>
                    </article>
                </div>
            </div>
        </section>
    </div>
    <script type="text/javascript">
        $(document).ready(function(){
            highlightKppFocusPage('#kppXemDiemDoiThuong');

            $('.onlyNumberInputForceValue').keyup(function(){
                $(this).val(function(el,value){
                    return value.replace(/[^\d]/g,'');
                });
            });

            <c:if test="${not empty enableLogin && enableLogin && empty enterPasswordOTP}">
                $('#loginForm').submit(function(){
                    setCookie("mobiEZ", $.trim($('#mobiEZ').val()), 100);
                });

                var lastestMobiEZ = getCookie("mobiEZ");
                if(lastestMobiEZ != ""){
                    $('#mobiEZ').val(lastestMobiEZ);
                }
            </c:if>
        });

        function submitForm(){
            $('#loginForm').submit();
        }
    </script>
</body>