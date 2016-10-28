<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<body class="q_student_q_teen_2015_home_page">
<section class="content">
    <div class="container">
        <div class="row">
            <div class="q_teen_q_student_left_img"></div>
            <div class="col-sm-offset-5 col-sm-7 col-xs-12">
                <article>
                    <!-- InstanceBeginEditable name="Content" -->
                    <h1 class=" text-red margin-bottom-large"><b>ĐỊA ĐIỂM ĐỔI QUÀ</b></h1>
                    <ul class="ul-thele-q-student list-content">
                        <c:forEach items="${departmentList}" var="departmentVar">
                            <li>${departmentVar.name} - ${departmentVar.address}</li>
                        </c:forEach>
                    </ul>
                    <br/><br/>
                    <!-- InstanceEndEditable -->
                </article>
            </div>
        </div>
    </div>
</section>
<script type="text/javascript">
    $(document).ready(function(){
        highlightFocusPage('#positionGiftExchangePage');
    });
</script>
</body>