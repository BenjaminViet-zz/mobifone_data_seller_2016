<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<c:url var="formUrl" value="/thuebaophattrienmoi/kenhphanphoi/tra-cuu-ket-qua-thuc-hien.html" />
<body class="sub-page">
    <div class=" container">
        <section class="background-none">
            <div class="text-center row">
                <div class="col-xs-12">
                    <article>
                        <form:form id="loginForm" commandName="item" cssClass="form-inline" role="form" action="${formUrl}">
                            <!-- InstanceBeginEditable name="Content" -->
                            <c:choose>
                                <c:when test="${empty enterPasswordOTP}">
                                    <h1 class="text-red padding-bottom-large"><b>Lấy Mã Đăng Nhập</b></h1>
                                    <c:if test="${not empty messageResponse}">
                                        <div class="alert alert-${alertType}">
                                                ${messageResponse}
                                        </div>
                                    </c:if>
                                    <div class="clear"></div>
                                    <p> Nhập số MobiEZ của bạn để lấy mã.</p>
                                    <div class="form-group">
                                        <form:input path="user.userName" cssClass="form-control onlyNumberInputForceValue" maxlength="11" id="mobiEZ" placeholder="Nhập số điện thoại" />
                                        &nbsp;<a class="btn btn-danger" onclick="javascript: submitForm();">Xác nhận</a>
                                    </div>
                                    <div class="clear"></div>
                                </c:when>
                                <c:otherwise>
                                    <h1 class="text-red padding-bottom-large"><b>Đăng Nhập</b></h1>
                                    <c:if test="${not empty messageResponse}">
                                        <div class="alert alert-${alertType}">
                                            ${messageResponse}
                                            <c:if test="${not empty allowChangeEZ && allowChangeEZ}">
                                                <a href="<c:url value="${formUrl}?crudaction=change-ez" />">Đổi số EZ</a>.
                                            </c:if>
                                        </div>
                                    </c:if>
                                    <div class="clear"></div>
                                    <p>Nhập mã xác nhận của bạn.</p>
                                    <div class="form-group">
                                        <label class="sr-only" for="maxacnhan">mã xác nhận</label>
                                        <form:input path="user.password" cssClass="form-control" id="maxacnhan" placeholder="Nhập mã xác nhận" />
                                        &nbsp;<a class="btn btn-danger" onclick="javascript: submitForm();">Đăng nhập</a>
                                    </div>
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
            highlightKppFocusPage('#kppKetQuaThucHien');

            <c:if test="${empty enterPasswordOTP}">
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