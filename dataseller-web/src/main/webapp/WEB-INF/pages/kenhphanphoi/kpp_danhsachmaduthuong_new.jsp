<%@page trimDirectiveWhitespaces="true"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<style>
    ul#nav-bar li{
        background: none !important;
        margin: 0px;
        padding: 0px;
    }
    .col-xs-4 {
        width: 33.33333333%;
        font-size: 15.5px;
        padding-left: 0px;
        padding-bottom: 10px;
    }
    select{
        padding: 3px;
    }
    .traCuuBtn {
        margin-top: 10px;
        margin-bottom: 10px;
    }
</style>
<body class="sub-page">
    <div class=" container">
        <section class="content">
            <div class="container">
                <!-- InstanceBeginEditable name="content" -->
                <div class="row">
                    <div class="col-xs-12 col-sm-3">
                        <div class="bg-blue no-padding box-border">
                            <ul id="nav-bar" class="nav">
                                <li><a href="<c:url value="/kenhphanphoi/xem-diem-doi-thuong.html" />" class="text-white">Lịch sử điểm</a></li>
                                <li class="active"><a href="<c:url value="/kenhphanphoi/xem-diem-doi-thuong.html" />?crudaction=danh-sach-ma-du-thuong" class="text-white">Danh sách mã dự thưởng</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-offset-1 col-sm-8 padding-left-larger viewScoreContent">
                        <article>
                            <h2 class="text-red no-margin padding-bottom-large">Lịch sử điểm</h2>
                            <b>${thongTinDBH}</b>
                            <a class="btn btn-danger" style="float:right;" onclick="javascript: logout();"><fmt:message key="cuahanggiaodich.label.try_login_another_thue_bao" /></a>
                            <hr />
                            <h4 class="text-red"><b>Xem theo ngày</b></h4>
                            <form:form id="reportForm" commandName="item" cssClass="form-inline" role="form" action="${formUrl}">
                                <b> <fmt:message key="label.tong_so_ma_du_thuong_hien_tai" />: ${tongSoMaDuThuongHienTai}</b><br/>
                                <div class="clear"></div>
                                <div id="danhSachMaDuThuongContentPane">
                                    <display:table name="danhSachMaDuThuongTableList" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                                   partialList="true" sort="external" size="${danhSachMaDuThuongTableListTotalItems}"
                                                   id="danhSachMaDuThuongTableList" pagesize="${Constants.MAXPAGEITEMS}" export="false" class="table-bordered table-striped table" style="margin: 1em 0 1.5em; width: 100%;">
                                        <display:column headerClass="table_header_center nowrap" sortable="false" property="ma_phieu" titleKey="label.ma_du_thuong" style="width: 20%;" />
                                        <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.ngay.ps" class="nowrap" style="width: 20%;" >
                                            <fmt:formatDate value="${danhSachMaDuThuongTableList.ngay_ps}" pattern="${datePattern}" />
                                        </display:column>
                                        <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.trang_thai" class="nowrap" style="width: 60%;" >
                                            <c:choose>
                                                <c:when test="${fn:trim(danhSachMaDuThuongTableList.exchange_gift_status) eq '0'}"><fmt:message key="label.chua_quay_so" /></c:when>
                                                <c:when test="${fn:trim(danhSachMaDuThuongTableList.exchange_gift_status) eq '1'}"><fmt:message key="label.trung_thuong" /></c:when>
                                            </c:choose>
                                        </display:column>
                                        <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.ma_du_thuong"/></display:setProperty>
                                        <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.ma_du_thuong"/></display:setProperty>
                                    </display:table>
                                </div>
                                <form:hidden path="currentView" id="currentView" />
                                <form:hidden path="pojo.userName" />
                            </form:form>
                        </article>
                    </div>
                </div>
                <!-- InstanceEndEditable -->
            </div>
        </section>
    </div>
    <script type="text/javascript">
        $(document).ready(function(){
            highlightKppFocusPage('#kppXemDiemDoiThuong');

            var fromDateVar = $("#fromDate").datepicker({
                dateFormat: 'dd/mm/yy',
                onRender: function (date) {
                }}).on('changeDate',function (ev) {
                        fromDateVar.hide();
                    }).data('datepicker');

            $('#fromDateIcon').click(function () {
                $('#signedDate').focus();
                return true;
            });

            var toDateVar = $("#toDate").datepicker({
                dateFormat: 'dd/mm/yy',
                onRender: function (date) {
                }}).on('changeDate',function (ev) {
                        toDateVar.hide();
                    }).data('datepicker');

            $('#toDateIcon').click(function () {
                $('#signedDate').focus();
                return true;
            });
        });

        function logout(){
            $('#reportForm').attr('action', '${formUrl}?crudaction=dang-xuat');
            $('#reportForm').submit();
        }
    </script>
</body>