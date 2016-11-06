<%@ include file="/common/taglibs.jsp" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<body class="q_student_q_teen_2015_home_page">
<section class="content container">
    <div class="content">
        <div class="q_student_q_teen_2015_home_img"></div>
    </div>
</section>
<script type="text/javascript">
    $(document).ready(function () {

        $('body').on("mousewheel", function () {
            event.preventDefault();

            var wheelDelta = event.wheelDelta;

            var currentScrollPosition = window.pageYOffset;
            window.scrollTo(0, currentScrollPosition - wheelDelta);
        });


        highlightKppFocusPage('#kppHomePage');



        /*--------------------------------*/
        /* background-attachment fixed IE */
        /*--------------------------------*/
        $('body').on("mousewheel", function () {
            event.preventDefault();

            var wheelDelta = event.wheelDelta;

            var currentScrollPosition = window.pageYOffset;
            window.scrollTo(0, currentScrollPosition - wheelDelta);
        });
        /*--------------------------------*/
        /* //background-attachment fixed IE */
        /*--------------------------------*/

    });
</script>
</body>