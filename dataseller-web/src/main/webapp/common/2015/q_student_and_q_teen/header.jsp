<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 01/04/2015
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/taglibs.jsp" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<header class="margin-top-large">
    <div class="container">
        <div class="row">
            <div class="col-lg-2 col-md-2">
                <div class="banner text-center"> <a href="<c:url value="/chuong-trinh.html" />"><img class="img-responsive img-center" src="<c:url value="/themes/kenhphanphoi/images/logo_mobifone.png"/>"></a></div>
            </div>
            <div class="col-lg-9 col-md-10">
                <nav class="margin-top-small">
                    <ul id="kpp_navigation_bar" class=" list-inline text-center ">
                        <!-- Class Active sẽ dựa vào trang hiện tại-->
                        <li id="kppHomePage" class="hidden-xs"><a href="<c:url value="/q-teen-q-student/trang-chu.html" />" class="no-underline text-white">Trang chủ</a></li>
                        <li id="kppTheLeCt"><a href="<c:url value="/q-teen-q-student/the-le-chuong-trinh.html"/> " class="no-underline text-white">Thể lệ chương trình</a></li>
                        <li id="howToExchangePage"><a href="<c:url value="/q-teen-q-student/cach-thuc-doi-qua.html" />" class="no-underline text-white">Cách thức đổi quà</a></li>
                        <li id="positionGiftExchangePage"><a href="<c:url value="/q-teen-q-student/dia-diem-doi-qua.html" />" class="no-underline text-white">Địa điểm đổi quà</a></li>
                        <li id="viewGiftPage"><a href="<c:url value="/q-teen-q-student/xem-diem-doi-qua.html" />" class="no-underline text-white">Xem Điểm/ Đổi Quà</a></li>
                    </ul>
                </nav>
            </div>
        </div>
        <hr class="pull-left no-margin-top hidden-xs">
    </div>
</header>