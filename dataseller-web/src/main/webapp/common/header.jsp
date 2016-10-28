<%@page trimDirectiveWhitespaces="true"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<c:url var="dashboardUrl" value="/"/>
<header class="margin-top-large">
    <div class="container">
        <div class="row">
            <div class="col-md-2">
                <div class="banner text-center"><a href="/chuong-trinh.html"><img class="img-responsive img-center" src="<c:url value="/themes/promotion/images/logo_mobifone.png"/>"></a></div>
            </div>
            <div class="col-md-10">
                <nav class="margin-top-small">
                    <ul id="navigation-header-tab" class=" list-inline text-center ">
                        <li id="homePage" class="hidden-xs"><a href="<c:url value="/tich-diem-cuoc-goi-nhan-voucher/trang-chu.html" />" class="no-underline text-white">Trang chủ</a></li>
                        <li id="rulePage"><a href="<c:url value="/tich-diem-cuoc-goi-nhan-voucher/the-le-chuong-trinh.html" />" class="no-underline text-white">Thể lệ chương trình</a></li>
                        <!-- Class Active sẽ dựa vào trang hiện tại-->
                        <li id="howToExchangePage"><a href="<c:url value="/tich-diem-cuoc-goi-nhan-voucher/cach-thuc-doi-qua.html" />" class="no-underline text-white">Cách thức đổi quà</a></li>
                        <!-- ************************************** -->
                        <li id="positionGiftExchangePage"><a href="<c:url value="/tich-diem-cuoc-goi-nhan-voucher/dia-diem-doi-qua.html" />" class="no-underline text-white">Địa điểm đổi quà</a></li>
                        <li id="viewGiftPage"><a href="<c:url value="/tich-diem-cuoc-goi-nhan-voucher/xem-diem-doi-qua.html" />" class="no-underline text-white">Xem điểm/Đổi quà</a></li>
                    </ul>
                </nav>
            </div>
        </div>
        <hr class="pull-left no-margin-top">
    </div>
</header>



