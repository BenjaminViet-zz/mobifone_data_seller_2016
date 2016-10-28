<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<style>
    .content {
        min-height: 620px;
        position: relative;
    }
    .home-total a {
        display: inline-block;
    }
    .img-canhan {
        margin: 200px 0 0 200px;
    }
    .img-phanphoi {
        margin: 200px 0 0 -75px;
    }
    .home-total img {
        max-height: 450px;
    }

    @media screen and (-webkit-min-device-pixel-ratio:0) {
        /* Safari and Chrome */
        .img-phanphoi {
            margin-top: 204px;
        }
    }
</style>
<body class="home-total content">
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <div id="khcnLink">
                <a href="#" class="img-canhan"><img class="img-responsive" src="<c:url value="/themes/kenhphanphoi/images/girl-png.png" />"></a>
            </div>
            <div id="kppLink">
                <a href="#" class="img-phanphoi"><img class="img-responsive " src="<c:url value="/themes/kenhphanphoi/images/people-png.png" />"></a>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        $('#khcnLink').bind('touchstart click', function(){
            document.location.href = '<c:url value="/tich-diem-cuoc-goi-nhan-voucher/trang-chu.html" />';
        });
        $('#kppLink').bind('touchstart click', function(){
            document.location.href = '<c:url value="/kenhphanphoi/trang-chu.html" />';
        });

        $('header').remove();
        $('footer').remove();
    });
</script>
</body>