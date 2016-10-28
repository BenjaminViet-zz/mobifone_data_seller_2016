<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<style>
    article{
        padding: 20px;
    }
    article .table-striped>tbody>tr>th,
    article .table>thead:first-child>tr:first-child>th,
    article.table-striped>tbody>tr:nth-child(odd)>td,
    article .table-striped>tbody>tr:nth-child(odd)>th{
        font-size: 13px !important;
    }
    .col-xs-12{
        padding: 5px 0px;
    }
</style>

<c:url var="formUrl" value="/kenhphanphoi/xem-chi-tiet-trang-thai-ky.html" />
<c:url var="lichSuDiemUrl" value="/kenhphanphoi/xem-diem-doi-thuong.html" />
<body class="sub-page">
    <div class=" container">
        <section class="content background-none">
            <div class="row">
                <div class="col-md-offset-5 col-md-7 col-xs-12">
                    <article>
                        <form:form id="reportForm" commandName="item" cssClass="form-inline" role="form">
                            <!-- InstanceBeginEditable name="Content" -->
                            <c:if test="${not empty messageResponse}">
                                <div class="alert alert-${alertType}">
                                        ${messageResponse}
                                </div>
                            </c:if>
                            <div class="clear"></div>
                            <a class="btn btn-danger" style="float:right;" onclick="javascript: logout();"><fmt:message key="cuahanggiaodich.label.try_login_another_thue_bao" /></a>
                            <div class="clear"></div>
                            <c:if test="${not empty detailData}">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="col-xs-7"><b>1. Mua bộ trọn gói từ Mobifone</b></div>
                                        <div class="col-xs-5">
                                            <c:choose>
                                                <c:when test="${not empty detailData.kit_sales_condition_status && fn:trim(detailData.kit_sales_condition_status) eq '1'}">
                                                    <fmt:message key="label.dat" />
                                                </c:when>
                                                <c:when test="${not empty detailData.kit_sales_condition_status && fn:trim(detailData.kit_sales_condition_status) eq '2'}">
                                                    <fmt:message key="label.khong_dat" />
                                                </c:when>
                                                <c:otherwise><fmt:message key="label.chua_xet" /></c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="col-xs-12">
                                        <div class="col-xs-7"><b>2. Bán trực tiếp BHM đến người tiêu dùng</b></div>
                                        <div class="col-xs-5">
                                            <c:choose>
                                                <c:when test="${not empty detailData.sms_sales_condition_status && fn:trim(detailData.sms_sales_condition_status) eq '1'}">
                                                    <fmt:message key="label.dat" />
                                                </c:when>
                                                <c:when test="${not empty detailData.sms_sales_condition_status && fn:trim(detailData.sms_sales_condition_status) eq '2'}">
                                                    <fmt:message key="label.khong_dat" />
                                                </c:when>
                                                <c:otherwise><fmt:message key="label.chua_xet" /></c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="col-xs-12">
                                        <div class="col-xs-7"><b>3. PSC của BHM</b></div>
                                        <div class="col-xs-5">
                                            <c:choose>
                                                <c:when test="${not empty detailData.sub_charge_condition_status && fn:trim(detailData.sub_charge_condition_status) eq '1'}">
                                                    <fmt:message key="label.dat" />
                                                </c:when>
                                                <c:when test="${not empty detailData.sub_charge_condition_status && fn:trim(detailData.sub_charge_condition_status) eq '2'}">
                                                    <fmt:message key="label.khong_dat" />
                                                </c:when>
                                                <c:otherwise><fmt:message key="label.chua_xet" /></c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="col-xs-12">
                                        <div class="col-xs-7"><b>4. Giới thiệu KH tham gia khuyến mại</b></div>
                                        <div class="col-xs-5">
                                            <c:choose>
                                                <c:when test="${not empty detailData.sub_intro_condition_status && fn:trim(detailData.sub_intro_condition_status) eq '1'}">
                                                    <fmt:message key="label.dat" />
                                                </c:when>
                                                <c:when test="${not empty detailData.sub_intro_condition_status && fn:trim(detailData.sub_intro_condition_status) eq '2'}">
                                                    <fmt:message key="label.khong_dat" />
                                                </c:when>
                                                <c:otherwise><fmt:message key="label.chua_xet" /></c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="col-xs-12">
                                        <div class="col-xs-7"><b>5. Thực hiện bán VAS</b></div>
                                        <div class="col-xs-5">
                                            <c:choose>
                                                <c:when test="${not empty detailData.vas_sales_conditon_status && fn:trim(detailData.vas_sales_conditon_status) eq '1'}">
                                                    <fmt:message key="label.dat" />
                                                </c:when>
                                                <c:when test="${not empty detailData.vas_sales_conditon_status && fn:trim(detailData.vas_sales_conditon_status) eq '2'}">
                                                    <fmt:message key="label.khong_dat" />
                                                </c:when>
                                                <c:otherwise><fmt:message key="label.chua_xet" /></c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="col-xs-12">
                                        <div class="col-xs-7"><b>6. Cung cấp thông tin thị trường</b></div>
                                        <div class="col-xs-5">
                                            <c:choose>
                                                <c:when test="${not empty detailData.market_info_condition_status && fn:trim(detailData.market_info_condition_status) eq '1'}">
                                                    <fmt:message key="label.dat" />
                                                </c:when>
                                                <c:when test="${not empty detailData.market_info_condition_status && fn:trim(detailData.market_info_condition_status) eq '2'}">
                                                    <fmt:message key="label.khong_dat" />
                                                </c:when>
                                                <c:otherwise><fmt:message key="label.chua_xet" /></c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <div class="clear"></div>
                            <br/>
                            <a class="btn btn-danger" onclick="javascript: comeback();" /><fmt:message key="label.quay_lai" /></a>
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
        });

        function logout(){
            $('#reportForm').attr('action', '${lichSuDiemUrl}?crudaction=dang-xuat');
            $('#reportForm').submit();
        }

        function comeback(){
            $('#reportForm').attr('action', '${formUrl}?crudaction=tro-lai');
            $('#reportForm').submit();
        }
    </script>
</body>