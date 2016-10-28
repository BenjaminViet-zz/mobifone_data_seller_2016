<%@page trimDirectiveWhitespaces="true"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<style>
    ul#nav-bar li{
        background: none !important;
        margin: 0px;
        padding: 0px;
    }
    select{
        padding: 3px;
    }
    .traCuuBtn {
        margin-top: 10px;
        margin-bottom: 10px;
    }
    #viewScoreHistoryNavTab{
        margin-top: 10px;
    }
    .summary{
        font-weight: bold;
        color: #cd6052 !important;
    }
    .detail{
        color: #e74c3c;
        text-decoration: underline;
    }
    input{
        width: 195px  !important;
    }
    #danhSachMaDuThuongTableList thead th{
        background-color: #f9f9f9 !important;
    }
    #danhSachMaDuThuongTableList>tbody>tr:nth-child(odd)>td{
        background: none !important;
        background-color: transparent !important;
    }
    #danhSachMaDuThuongTableList>tbody>tr:nth-child(even)>td{
        background-color: #f9f9f9 !important;
    }
    #danhSachMaDuThuongTableList>thead>tr>th{
        border-bottom-width: 1px;
    }
    .table-bordered>thead>tr>th, .table-bordered>tbody>tr>th, .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td, .table-bordered>tbody>tr>td, .table-bordered>tfoot>tr>td{
        border: 1px solid #c9c9c9;
    }
    .text-error{
        color: red;
    }
</style>
<c:url var="formUrl" value="/kenhphanphoi/xem-diem-doi-thuong.html" />
<c:url var="viewDetailByItemUrl" value="/kenhphanphoi/xem-diem-doi-thuong-chi-tiet-hang-muc-theo-ngay.html" />
<c:url var="viewDetailByMonthUrl" value="/kenhphanphoi/xem-chi-tiet-trang-thai-ky.html" />
<body class="sub-page">
    <div class=" container">
        <section class="content">
            <div class="container">
                <!-- InstanceBeginEditable name="content" -->
                <div class="row">
                    <div class="col-xs-12 col-sm-3">
                        <div class="bg-blue no-padding box-border">
                            <%--<ul id="nav-bar" class="nav">--%>
                                <%--<li class="active"><a href="<c:url value="/kenhphanphoi/xem-diem-doi-thuong.html" />" class="text-white">Lịch sử điểm</a></li>--%>
                                <%--<li class="border-top"><a href="<c:url value="/kenhphanphoi/xem-diem-doi-thuong.html" />?crudaction=danh-sach-ma-du-thuong" class="text-white">Danh sách mã dự thưởng</a></li>--%>
                            <%--</ul>--%>
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-offset-1 col-sm-8 padding-left-larger viewScoreContent">
                        <article>
                            <form:form id="reportForm" commandName="item" cssClass="form-inline" role="form" action="${formUrl}">
                                <b>${thongTinDBH}</b>
                                <br/>
                                <div class="clear"></div>
                                <a class="btn btn-danger" style="float:right;" onclick="javascript: logout();"><fmt:message key="cuahanggiaodich.label.try_login_another_thue_bao" /></a>
                                <ul class="nav nav-tabs" id="viewScoreHistoryNavTab">
                                    <li class="active"><a href="#lichSuDiemTab">Lịch sử điểm</a></li>
                                    <li><a href="#danhSachMaDuThuongTab">DS mã dự thưởng</a></li>
                                    <li><a href="#canhBaoTab">Cảnh báo</a></li>
                                </ul>
                                <div class="clear"></div>
                                <div class="tab-content viewScoreContent">
                                    <div class="tab-pane active" id="lichSuDiemTab">
                                        <h2 class="text-red padding-bottom-large">Lịch sử điểm</h2>
                                        <h4 class="text-red"><b>Xem theo ngày</b></h4>
                                        <div class="row">
                                            <div class="col-xs-6">
                                                <p class="font-size-small"><b><fmt:message key="label.thang_tich_diem" />: </b></p>
                                                <form:select path="thangTichDiem" cssStyle="width: 100%;" cssClass="form-control option" onchange="javascript: changeMonth();">
                                                    <option <c:if test="${item.thangTichDiem eq 10}">selected="true" </c:if> value="10"><fmt:message key="label.thang" />&nbsp;10</option>
                                                    <option <c:if test="${item.thangTichDiem eq 11}">selected="true" </c:if> value="11"><fmt:message key="label.thang" />&nbsp;11</option>
                                                    <option <c:if test="${item.thangTichDiem eq 12}">selected="true" </c:if> value="12"><fmt:message key="label.thang" />&nbsp;12</option>
                                                </form:select>
                                            </div>
                                            <div class="col-xs-6">
                                                <p class="font-size-small"><b><fmt:message key="label.loai_ps" />: </b></p>
                                                <form:select path="itemId" cssClass="form-control option">
                                                    <option value=""><fmt:message key="label.all" /></option>
                                                    <c:forEach items="${itemList}" var="itemVar">
                                                        <option <c:if test="${item.itemId eq itemVar.item_Id}">selected="true" </c:if> value="${itemVar.item_Id}">${itemVar.item_name}</option>
                                                    </c:forEach>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="row margin-top">
                                            <div class="col-xs-12 col-sm-6">
                                                <p class="font-size-small"><b>Ngày bắt đầu:</b></p>
                                                <div class="form-group row">
                                                    <div class="col-xs-9 col-sm-10" style="padding-right: 5px;">
                                                        <input id="fromDate" name="fromDate" class="form-control nohtml text-center prevent_type"
                                                               type="text" placeholder="${symbolDateEmpty}"
                                                               value="<fmt:formatDate value="${item.fromDate}" pattern="${datePattern}"/>"/>
                                                    </div>
                                                    <div class="col-xs-3 col-sm-2 no-padding">
                                                        <img class=" img-responsive calendar margin-left-small" src="<c:url value="/themes/promotion/images/Calendar-Time.png"/>" alt="">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-12 col-sm-6">
                                                <p class="font-size-small"><b>Ngày kết thúc:</b></p>
                                                <div class="form-group row">
                                                    <div class="col-xs-9 col-sm-10" style="padding-right: 5px;">
                                                        <input id="toDate" name="toDate" class="form-control nohtml text-center prevent_type"
                                                               type="text" placeholder="${symbolDateEmpty}"
                                                               value="<fmt:formatDate value="${item.toDate}" pattern="${datePattern}"/>"/>
                                                    </div>
                                                    <div class="col-xs-3 col-sm-2 no-padding">
                                                        <img class=" img-responsive calendar margin-left-small" src="<c:url value="/themes/promotion/images/Calendar-Time.png"/>" alt="">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="clear"></div>
                                        <div class="col-xs-14 text-center">
                                            <a class="btn btn-danger traCuuBtn" style="float: right; margin: 20px 0px;" onclick="javascript: submitForm();"><i class="icon-refresh"></i>&nbsp;<fmt:message key="label.search" /></a>
                                        </div>
                                        <br/>
                                        <div class="clear"></div>
                                        <c:set var="totalScoreByDate" value="${0}" />
                                        <c:if test="${not empty lichSuDiemTheoNgayTableList}">
                                            <table class="table-bordered table-striped table">
                                                <tr>
                                                    <th class="text-center" style="width: 5%;">STT</th>
                                                    <th class="text-center" style="width: 35%;">Loại phát sinh điểm</th>
                                                    <th class="text-center" style="width: 20%;">Điểm tích luỹ</th>
                                                    <th class="text-center" style="width: 20%;">Số tiền tương ứng</th>
                                                    <th colspan="2" class="text-center" style="width: 20%;">Số mã dự thưởng</th>
                                                </tr>
                                                <c:set var="indexRow" value="${0}" />
                                                <c:forEach items="${lichSuDiemTheoNgayTableList}" var="report">
                                                    <c:set var="indexRow" value="${indexRow + 1}" />
                                                    <c:set var="totalScoreByDate" value="${totalScoreByDate + report.prom_point}" />
                                                    <tr>
                                                        <td class="text-center">${indexRow}</td>
                                                        <td>${report.tenLoaiPS}</td>
                                                        <td class="text-center">${report.prom_point}</td>
                                                        <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="2" value="${report.soTienTuongUng}" /></td>
                                                        <td colspan="2" class="text-center nowrap">
                                                            <fmt:formatDate var="fromDateVar" value="${item.fromDate}" pattern="${datePattern}" />
                                                            <fmt:formatDate var="toDateVar" value="${item.toDate}" pattern="${datePattern}" />
                                                            <fmt:formatNumber type="number" value="${report.soMaDuThuong}" />
                                                            &nbsp;<a class="detail" href="${viewDetailByItemUrl}?d_Id=${retailDealerDTO.dealer_Id}&item_Id=${report.item_Id}&cycle=${report.cycle}&fromDate=${fromDateVar}&toDate=${toDateVar}&fromStart=1"><fmt:message key="label.chi_tiet" /></a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                            <div class="clear"></div>
                                            <table style="float: right; width: auto;" class="table table-bordered">
                                                <tr>
                                                    <td class="text-center nowrap" style="width: 150px;"><span class="summary">Tổng điểm</span></td>
                                                    <td class="text-center" style="width: 100px;"><span class="summary">${totalScoreByDate}</span></td>
                                                </tr>
                                            </table>
                                            <div class="clear"></div>
                                        </c:if>
                                        <hr />
                                        <h4 class="text-red"><b>Xem theo tháng</b></h4>
                                        <c:set var="totalScoreByMonth" value="${0}" />
                                        <table class="table-bordered table-striped table">
                                            <tr>
                                                <th class="text-center" style="width: 10%;">Tháng</th>
                                                <th class="text-center" style="width: 10%;">Điểm</th>
                                                <th class="text-center" style="width: 20%;">Số tiền tương ứng</th>
                                                <th class="text-center" style="width: 20%;">Số mã dự thưởng</th>
                                                <th class="text-center" style="width: 20%;">Chốt kỳ</th>
                                                <th class="text-center" style="width: 20%;">Trạng thái</th>
                                            </tr>
                                            <c:if test="${not empty lichSuDiemTheoThangTableList}">
                                                <c:forEach items="${lichSuDiemTheoThangTableList}" var="report">
                                                    <tr>
                                                        <c:set var="totalScoreByMonth" value="${totalScoreByMonth + report.prom_point}" />
                                                        <td class="text-center">${report.cycle}</td>
                                                        <td class="text-center">${report.prom_point}</td>
                                                        <td class="text-center nowrap"><fmt:formatNumber type="number" maxFractionDigits="2" value="${report.soTienTuongUng}" /></td>
                                                        <td class="text-center nowrap"><fmt:formatNumber type="number" value="${report.soMaDuThuong}" /></td>
                                                        <td class="text-center nowrap">
                                                            <c:choose>
                                                                <c:when test="${not empty report.cycle_lock_status && fn:trim(report.cycle_lock_status) eq Constants.DEALER_ACCONT_ACTION_DA_CHOT_KY}">
                                                                    <fmt:message key="label.da_chot_ky" />
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <fmt:message key="label.chua_chot_ky" />
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td class="text-center nowrap">
                                                            <a class="detail" href="${viewDetailByMonthUrl}?dealer_Id=${report.dealer_Id}&cycle=${report.cycle}">
                                                                <c:choose>
                                                                    <c:when test="${not empty report.condition_status && fn:trim(report.condition_status) eq '1'}">
                                                                        <fmt:message key="label.dat"/>
                                                                    </c:when>
                                                                    <c:when test="${not empty report.condition_status && fn:trim(report.condition_status) eq '2'}">
                                                                        <fmt:message key="label.khong_dat"/>
                                                                    </c:when>
                                                                    <c:otherwise><fmt:message key="label.chua_xet"/></c:otherwise>
                                                                </c:choose>
                                                            </a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </c:if>
                                        </table>
                                        <div class="clear"></div>
                                        <table style="float: right; width: auto;" class="table table-bordered">
                                            <tr>
                                                <td class="text-center nowrap" style="width: 150px;"><span class="summary">Tổng điểm</span></td>
                                                <td class="text-center" style="width: 100px;"><span class="summary">${totalScoreByMonth}</span></td>
                                            </tr>
                                        </table>
                                        <div class="clear"></div>
                                    </div>
                                    <div class="tab-pane" id="danhSachMaDuThuongTab">
                                        <h2 class="text-red no-margin padding-bottom-large">Danh sách mã dự thưởng</h2>
                                        <b> <fmt:message key="label.tong_so_ma_du_thuong_hien_tai" />: <fmt:formatNumber type="number" value="${tongSoMaDuThuongHienTai}" /></b><br/>
                                        <span class="text-error">(* Chi tiết mã dự thưởng sẽ có sau khi chốt kỳ)</span>
                                        <div class="clear"></div>
                                        <div id="danhSachMaDuThuongContentPane">
                                            <display:table name="danhSachMaDuThuongTableList" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                                           partialList="true" sort="external" size="${danhSachMaDuThuongTableListTotalItems}"
                                                           id="danhSachMaDuThuongTableList" pagesize="${Constants.MAXPAGEITEMS}" export="false" class="table-bordered table-striped table" style="margin: 1em 0 1.5em; width: 100%;">
                                                <display:column headerClass="text-center nowrap" sortable="false" property="ma_phieu" titleKey="label.ma_du_thuong" style="width: 20%;" />
                                                <display:column headerClass="text-center nowrap" sortable="false" titleKey="label.chu_ky" class="nowrap" style="width: 15%;" >
                                                    <fmt:formatDate value="${danhSachMaDuThuongTableList.ngay_ps}" pattern="${datePattern}" />
                                                </display:column>
                                                <display:column headerClass="text-center nowrap" sortable="false" titleKey="label.trang_thai" class="text-center nowrap" style="width: 35%;" >
                                                    <c:choose>
                                                        <c:when test="${fn:trim(danhSachMaDuThuongTableList.exchange_gift_status) eq '0'}"><fmt:message key="label.chua_quay_so" /></c:when>
                                                        <c:when test="${fn:trim(danhSachMaDuThuongTableList.exchange_gift_status) eq '1'}"><fmt:message key="label.trung_thuong" /></c:when>
                                                    </c:choose>
                                                </display:column>
                                                <display:column headerClass="text-center nowrap" sortable="false" titleKey="label.giai" class="text-center" style="width: 30%;" >
                                                    <c:if test="${not empty danhSachMaDuThuongTableList.exchange_gift_status && danhSachMaDuThuongTableList.exchange_gift_status eq '1'}">
                                                        ${danhSachMaDuThuongTableList.exchange_gift_name}
                                                    </c:if>
                                                </display:column>
                                                <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.ma_du_thuong"/></display:setProperty>
                                                <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.ma_du_thuong"/></display:setProperty>
                                            </display:table>
                                        </div>
                                    </div>
                                    <div class="tab-pane" id="canhBaoTab">
                                        <h2 class="text-red no-margin padding-bottom-large">Chi tiết cảnh báo</h2>
                                        <div class="clear"></div>
                                        <div id="canhBaoContentPane">
                                            <display:table name="canhBaoTableList" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                                           partialList="true" sort="external" size="${canhBaoTableListTotalItems}"
                                                           id="canhBaoTableList" pagesize="${Constants.MAXPAGEITEMS}" export="false" class="table-bordered table-striped table" style="margin: 1em 0 1.5em; width: 100%;">
                                                <display:column headerClass="text-center" sortable="false" property="chuKy" titleKey="label.chu_ky" class="text-center" style="width: 10%;" />
                                                <display:column headerClass="text-center" sortable="false" property="item_Name" titleKey="label.hang_muc" style="width: 40%;" />
                                                <display:column headerClass="text-center" sortable="false" titleKey="label.so_phat_sinh" class="text-center nowrap" style="width: 10%;" >
                                                    <fmt:formatNumber type="number" value="${canhBaoTableList.soPS}" />
                                                </display:column>
                                                <display:column headerClass="text-center" sortable="false" titleKey="label.so_diem_da_dat" class="text-center nowrap" style="width: 10%;" >
                                                    <fmt:formatNumber type="number" value="${canhBaoTableList.soDiemDaDat}" />
                                                </display:column>
                                                <display:column headerClass="text-center" sortable="false" titleKey="label.so_phai_dat" class="text-center nowrap" style="width: 10%;" >
                                                    <fmt:formatNumber type="number" value="${canhBaoTableList.soPhaiDat}" />
                                                </display:column>
                                                <display:column headerClass="text-center" sortable="false" titleKey="label.ngay_tong_hop" class="text-center nowrap" style="width: 10%;" >
                                                    <fmt:formatDate value="${canhBaoTableList.ngayTongHop}" pattern="${datePattern}" />
                                                </display:column>
                                                <display:column headerClass="text-center" sortable="false" property="thoiGianConLai" titleKey="label.so_ngay_con_lai" class="text-center nowrap" style="width: 10%;" />
                                                <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.hang_muc_ps"/></display:setProperty>
                                                <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.hang_muc_ps"/></display:setProperty>
                                            </display:table>
                                        </div>
                                    </div>
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

            $('#viewScoreHistoryNavTab a').click(function (e) {
                e.preventDefault();
                $(this).tab('show');

                if($('#danhSachMaDuThuongTab').hasClass('active')){
                    $('#currentView').val('2');
                }else{
                    $('#currentView').val('1');
                }
            });

            $('#viewScoreHistoryNavTab a').eq(${item.currentView - 1}).tab('show');

            var currentMonth = new Date(2014, ${item.thangTichDiem}, 0),
                firstDay = new Date(currentMonth.getFullYear(), currentMonth.getMonth(), 1),
                lastDay = new Date(currentMonth.getFullYear(), currentMonth.getMonth() + 1, 0);

            var fromDateVar = $("#fromDate").datepicker({
                dateFormat: 'dd/mm/yy',
                minDate: firstDay,
                maxDate: lastDay,
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
                minDate: firstDay,
                maxDate: lastDay,
                onRender: function (date) {
                }}).on('changeDate',function (ev) {
                        toDateVar.hide();
                    }).data('datepicker');

            $('#toDateIcon').click(function () {
                $('#signedDate').focus();
                return true;
            });

            $('#danhSachMaDuThuongContentPane span.pagelinks a').click(function(){
                var href = $(this).attr('href');
                if(href.indexOf('currentView=') == -1){
                    href += '&currentView=2';
                }else{
                    if(href.indexOf('currentView=1')){
                        href = href.replace('currentView=1','currentView=2');
                    }
                }
                $(this).attr('href', href);
            });
        });

        function logout(){
            $('#reportForm').attr('action', '${formUrl}?crudaction=dang-xuat');
            $('#reportForm').submit();
        }

        function submitForm(){
            $('#reportForm').submit();
        }

        function changeMonth(){
            $('#fromDate').val('');
            $('#toDate').val('');
            $('#reportForm').submit();
        }
    </script>
</body>