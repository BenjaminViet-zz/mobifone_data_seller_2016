<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <style>
        .content {
            min-height: 620px;
            position: relative;
        }
        .home-total a {
            display: inline-block;
        }
        .img-canhan {
            margin: 102px 0 0 19px;
        }
        .img-phanphoi {
            margin: 104px 0px 0px 34px;
        }
        .home-total img {
            max-height: 450px;
        }

        @media screen and (-webkit-min-device-pixel-ratio:0) {
            /* Safari and Chrome */
            .img-phanphoi {
                margin-top: 102px;
            }

            .img-canhan {
                margin: 100px 0 0 19px;
            }
        }
    </style>
</head>
<body class="home-total content">
<div class="container">
    <div class="row">
        <div class="mobifone_slogan">
            <img class="img-responsive" style="width: 367px; height: 94px;" src="<c:url value="/themes/kenhphanphoi/images/mobifone-slogan.png" />">
        </div>
        <div class="col-xs-12">
            <div id="kppLink">
                <a href="#" class="img-phanphoi"><img class="img-responsive " src="<c:url value="/themes/kenhphanphoi/images/ban-goi-hay-nhan-tien-ngay.png" />"></a>
            </div>
            <div id="khcnLink">
                <a href="#" class="img-canhan"><img class="img-responsive" src="<c:url value="/themes/kenhphanphoi/images/qt-student-new-1.png" />"></a>
            </div>
        </div>
    </div>
</div>
    <script type="text/javascript">
        $(document).ready(function(){
            $('#khcnLink').bind('touchstart click', function(){
                document.location.href = '<c:url value="/q-teen-q-student/trang-chu.html" />';
            });
            $('#kppLink').bind('touchstart click', function(){
                document.location.href = '<c:url value="/thuebaophattrienmoi/kenhphanphoi/trang-chu.html" />';
            });

            $('header').remove();
            $('footer').remove();
        });
    </script>
</body>
</html>