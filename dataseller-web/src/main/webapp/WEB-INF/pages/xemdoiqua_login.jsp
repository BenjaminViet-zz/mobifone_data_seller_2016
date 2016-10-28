<%@page trimDirectiveWhitespaces="true"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<c:url var="formUrl" value="/tich-diem-cuoc-goi-nhan-voucher/xem-diem-doi-qua.html" />
<body class="sub-page">
<section class="content">
    <div class="container">
        <div class="row">
            <div class="col-sm-offset-5 col-sm-7 col-xs-12">
                <article>
                    <form:form commandName="item" cssClass="form-inline" role="form" action="${formUrl}">
                        <!-- InstanceBeginEditable name="Content" -->
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
                        <button type="submit" class="btn btn-info">Đăng nhập</button>
                        <p>&nbsp;</p>
                    </form:form>
                </article>
            </div>
        </div>
    </div>
</section>
<script type="text/javascript">
    $(document).ready(function(){
        highlightFocusPage('#viewGiftPage');
    });
</script>
</body>