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
                        <li id="kppHomePage" class="hidden-xs"><a href="<c:url value="/kenhphanphoi/trang-chu.html" />" class="no-underline text-white">Trang chủ</a></li>
                        <li id="kppTheLeCt"><a href="<c:url value="/kenhphanphoi/the-le-chuong-trinh.html" />" class="no-underline text-white">Thể lệ chương trình</a></li>
                        <li id="kppCachThucDoiThuong"><a href="<c:url value="/kenhphanphoi/cach-thuc-du-thuong.html" />" class="no-underline text-white">Cách thức đổi thưởng</a></li>
                        <li id="kppXemDiemDoiThuong"><a href="<c:url value="/kenhphanphoi/xem-diem-doi-thuong.html" />" class="no-underline text-white">Xem điểm/Đổi thưởng</a></li>
                        <li id="kppKetQuaDuThuong"><a href="<c:url value="/kenhphanphoi/ket-qua-du-thuong.html" />" class="no-underline text-white">Kết quả dự thưởng</a></li>
                    </ul>
                </nav>
            </div>
        </div>
        <hr class="pull-left no-margin-top hidden-xs">
    </div>
</header>