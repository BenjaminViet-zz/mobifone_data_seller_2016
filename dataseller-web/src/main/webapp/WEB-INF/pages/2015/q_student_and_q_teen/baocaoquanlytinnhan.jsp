<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="admin.baocaoquanlytinnhan.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="admin.baocaoquanlytinnhan.heading_page" />"/>
    <style>
        #tableNote td{
            padding: 3px;
        }
        #tableNote tr td:first-child{
            vertical-align: top;
        }
    </style>
</head>
<c:set var="prefix" value="/baocao" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/qstudent/bcquanlytinnhan.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="admin.baocaoquanlytinnhan.heading_page" /></h2>

</div>
<div class="contentpanel">
    <div class="panel panel-default">

        <div class="panel-body panel-body-nopadding">
            <c:if test ="${not empty messageResponse}">
                <div class="alert alert-${alertType}">
                    <a class="close" data-dismiss="alert" href="#">&times;</a>
                        ${messageResponse}
                </div>
                <div class="clear"></div>
            </c:if>
            <div class="row-fluid report-filter">
                <form:form commandName="item" id="reportForm" action="${formUrl}" method="post" autocomplete="off">
                    <div class="col-md-8">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="admin.baocaoquanlytinnhan.label.doi_tuong_from_thue_bao" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="loaiDoiTuongMenu" path="loaiTinNhanTuKHCN" cssStyle="width: 200px;">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <option <c:if test="${item.loaiTinNhanTuKHCN eq Constants.DK_MOBIFONE}">selected="true"</c:if> value="${Constants.DK_MOBIFONE}">${Constants.DK_MOBIFONE}</option>
                                            <option <c:if test="${item.loaiTinNhanTuKHCN eq Constants.HUY_MOBIFONE}">selected="true"</c:if> value="${Constants.HUY_MOBIFONE}">${Constants.HUY_MOBIFONE}</option>
                                            <option <c:if test="${item.loaiTinNhanTuKHCN eq Constants.KT_MOBIFONE}">selected="true"</c:if> value="${Constants.KT_MOBIFONE}">${Constants.KT_MOBIFONE}</option>
                                            <option <c:if test="${item.loaiTinNhanTuKHCN eq Constants.DP_MOBIFONE}">selected="true"</c:if> value="${Constants.DP_MOBIFONE}">${Constants.DP_MOBIFONE}</option>
                                        </form:select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="admin.baocaoquanlytinnhan.label.doi_tuong_from_VMS" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="loaiDoiTuongMenu" path="loaiTinNhanTuVMS" cssStyle="width: 200px;">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <option <c:if test="${item.loaiTinNhanTuVMS eq Constants.LOAITINNHAN_1}">selected="true"</c:if> value="${Constants.LOAITINNHAN_1}">${Constants.LOAITINNHAN_1}</option>
                                            <option <c:if test="${item.loaiTinNhanTuVMS eq Constants.LOAITINNHAN_2_1}">selected="true"</c:if> value="${Constants.LOAITINNHAN_2_1}">${Constants.LOAITINNHAN_2_1}</option>
                                            <option <c:if test="${item.loaiTinNhanTuVMS eq Constants.LOAITINNHAN_2_2}">selected="true"</c:if> value="${Constants.LOAITINNHAN_2_2}">${Constants.LOAITINNHAN_2_2}</option>
                                            <option <c:if test="${item.loaiTinNhanTuVMS eq Constants.LOAITINNHAN_3}">selected="true"</c:if> value="${Constants.LOAITINNHAN_3}">${Constants.LOAITINNHAN_3}</option>
                                            <option <c:if test="${item.loaiTinNhanTuVMS eq Constants.LOAITINNHAN_4}">selected="true"</c:if> value="${Constants.LOAITINNHAN_4}">${Constants.LOAITINNHAN_4}</option>
                                            <option <c:if test="${item.loaiTinNhanTuVMS eq Constants.LOAITINNHAN_5}">selected="true"</c:if> value="${Constants.LOAITINNHAN_5}">${Constants.LOAITINNHAN_5}</option>
                                            <option <c:if test="${item.loaiTinNhanTuVMS eq Constants.LOAITINNHAN_6}">selected="true"</c:if> value="${Constants.LOAITINNHAN_6}">${Constants.LOAITINNHAN_6}</option>
                                            <option <c:if test="${item.loaiTinNhanTuVMS eq Constants.LOAITINNHAN_7_1}">selected="true"</c:if> value="${Constants.LOAITINNHAN_7_1}">${Constants.LOAITINNHAN_7_1}</option>
                                            <option <c:if test="${item.loaiTinNhanTuVMS eq Constants.LOAITINNHAN_7_2}">selected="true"</c:if> value="${Constants.LOAITINNHAN_7_2}">${Constants.LOAITINNHAN_7_2}</option>
                                            <option <c:if test="${item.loaiTinNhanTuVMS eq Constants.LOAITINNHAN_8}">selected="true"</c:if> value="${Constants.LOAITINNHAN_8}">${Constants.LOAITINNHAN_8}</option>
                                            <option <c:if test="${item.loaiTinNhanTuVMS eq Constants.LOAITINNHAN_9}">selected="true"</c:if> value="${Constants.LOAITINNHAN_9}">${Constants.LOAITINNHAN_9}</option>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="admin.baocaoquanlytinnhan.label.tu_ngay" /></label>
                                    <div class="col-sm-8">
                                        <div class="input-append date" >
                                            <input name="fromDate" id="fromDate" class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.fromDate}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="fromDateIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="admin.baocaoquanlytinnhan.label.tu_ngay" /></label>
                                    <div class="col-sm-8">
                                        <div class="input-append date" >
                                            <input name="toDate" id="toDate" class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.toDate}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="toDateIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <a class="btn btn-info" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                                    <a class="btn btn-info" onclick="javascript: reset();"><fmt:message key="label.reset" /></a>
                                    <c:if test="${not empty reportData && fn:length(reportData) gt 0}">
                                        <a class="btn btn-info" onclick="javascript: export2Excel();"><fmt:message key="label.export" /></a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <span class="text-note">* Các loại tin nhắn của VMS đến khách hàng sẽ được hiển thị tương ứng. Xem chú thích bên dưới:</span><br/>
                    <table id="tableNote">
                        <tr>
                            <td><span class="text-note">${Constants.LOAITINNHAN_1}</span></td>
                            <td>MobiFone thuc hien CT danh cho thue bao Q-Teen và Q-Student “Tich diem cuoc goi, nhan ngay voucher” den 31/05/2015: Tang 1 phieu mua hang 50.000d khi tich luy du 100 diem (1 diem = 1.000d tru trong Tai khoan chinh).CT chi ap dung tai Tp.Ho Chi Minh. Dang ky soan DK MOBIFONE gui 9234( mien cuoc tin nhan).</td>
                        </tr>
                        <tr>
                            <td><span class="text-note">${Constants.LOAITINNHAN_2_1}</span></td>
                            <td>Quy khach da dong y tham gia CT danh cho thue bao Q-Teen và Q-Student “Tich diem cuoc goi, nhan ngay voucher”: MobiFone tang 1 phieu mua hang 50.000d khi Quy khach tich luy du 100 diem (1 diem = 1.000d tru trong Tai khoan chinh). CT den 31/05/2015, ap dung tai Tp. Ho Chi Minh.</td>
                        </tr>
                        <tr>
                            <td><span class="text-note">${Constants.LOAITINNHAN_2_2}</span></td>
                            <td>Cu phap dang ky cua Quy khach khong dung. Soan tin nhan DK MOBIFONE gui 9234 (mien cuoc phi) de tham gia CTdanh cho thue bao Q-Teen và Q-Student “Tich diem cuoc goi, nhan ngay voucher”.</td>
                        </tr>
                        <tr>
                            <td><span class="text-note">${Constants.LOAITINNHAN_3}</span></td>
                            <td>Quy khach khong thuoc doi tuong tham gia CT danh cho thue bao Q-Teen và Q-Student“Tich diem cuoc goi, nhan ngay voucher”. Lien he 9090 de biet them chi tiet.</td>
                        </tr>
                        <tr>
                            <td><span class="text-note">${Constants.LOAITINNHAN_4}</span></td>
                            <td>Quy khach da tich luy duoc X diem trong CT danh cho thue bao Q-Teen và Q-Student “Tich diem cuoc goi, nhan ngay voucher” tinh den het ngay N (Sau khi doi ma). Tiep tuc tich luy diem de doi phieu mua hang tri gia 50.000d. Chi tiet L/H 9090.</td>
                        </tr>
                        <tr>
                            <td><span class="text-note">${Constants.LOAITINNHAN_5}</span></td>
                            <td>Hay goi va nhan tin de tich luy diem va doi phieu mua hang 50.000d. Den het ngay N, Quy khach da tich luy duoc X diem trong CT danh cho thue bao Q-Teen và Q-Student “Tich diem cuoc goi, nhan ngay voucher”. Chi tiet lien he 9090.</td>
                        </tr>
                        <tr>
                            <td><span class="text-note">${Constants.LOAITINNHAN_6}</span></td>
                            <td>Quy khach da tich luy du diem de nhan qua tang mien phi tu MobiFone. Soan DP MOBIFONEgui 9234 hoac Reply (Tra loi): DP MOBIFONE de nhan ma so doi qua tang (mien cuoc tin nhan).</td>
                        </tr>
                        <tr>
                            <td><span class="text-note">${Constants.LOAITINNHAN_7_1}</span></td>
                            <td>Ma doi phieu mua hang cua CT danh cho thue bao Q-Teen và Q-Student“Tich diem cuoc goi, nhan ngay voucher” la x1x2x3x4x5x6x7x8x9x10x11x12. Vui long den cac cua hang MobiFone tai Tp. Ho Chi Minh de doi phieu mua hang tri gia 50.000d truoc 15/06/2015. Luu y: CT se ket thuc som neu so luong phieu mua hang duoc doi het. Chi tiet L/H 9090.</td>
                        </tr>
                        <tr>
                            <td><span class="text-note">${Constants.LOAITINNHAN_7_2}</span></td>
                            <td>Ma doi thuong cua CT danh cho thue bao Q-Teen và Q-Student “Tich diem cuoc goi, nhan ngay voucher” tu :  x1x2x3x4x5x6x7x8x9x10x11x12 den ,y1y2y3y4y5y6y7y8y9y10y11y12. Vui long den cac cua hang MobiFone tai Tp. Ho Chi Minh de doi phieu mua hang truoc 15/06/2015. Luu y: CT se ket thuc som neu so luong phieu mua hang duoc doi het. Chi tiet L/H 9090.</td>
                        </tr>
                        <tr>
                            <td><span class="text-note">${Constants.LOAITINNHAN_8}</span></td>
                            <td>MobiFone xin thong bao chuong trinh “Tich diem cuoc goi, nhan ngay voucher” da ket thuc do toan bo phieu mua hang da duoc doi. Cam on ban da su dung dich vu cua MobiFone. Chi tiet lien he 9090.</td>
                        </tr>
                        <tr>
                            <td><span class="text-note">${Constants.LOAITINNHAN_9}</span></td>
                            <td>Quy khach da nhan duoc 10 phieu doi thuong trong CT "Tich diem cuoc goi, nhan ngay Voucher" nen khong the tiep tuc doi phieu them nua. Chi tiet lien he 9090.</td>
                        </tr>
                    </table>
                    <div class="clear"></div>
                    <c:if test="${not empty reportData}">
                        <table class="tableVms table-hover">
                            <tr>
                                <th rowspan="2" class="table_header_center"><strong><fmt:message key="admin.baocaoquanlytinnhan.label.loai_tin_nhan" /></strong></th>
                                <th rowspan="2" class="table_header_center"><strong><fmt:message key="admin.baocaoquanlytinnhan.label.thoi_gian_nhan_tin" /></strong></th>
                                <th rowspan="2" class="table_header_center"><strong><fmt:message key="admin.baocaoquanlytinnhan.label.noi_dung_tin_nhan" /></strong></th>
                                <th rowspan="2" class="table_header_center"><strong><fmt:message key="admin.baocaoquanlytinnhan.label.so_luong_tb" /></strong></th>
                                <th colspan="3" class="table_header_center"><strong><fmt:message key="admin.baocaoquanlytinnhan.label.tinh_trang_nhan_tin" /></strong></th>
                            </tr>
                            <tr>
                                <th class="table_header_center"><strong><fmt:message key="admin.baocaoquanlytinnhan.label.so_luong_tn" /></strong></th>
                                <th class="table_header_center"><strong><fmt:message key="admin.baocaoquanlytinnhan.label.tin_nhan_thanh_cong" /></strong></th>
                                <th class="table_header_center"><strong><fmt:message key="admin.baocaoquanlytinnhan.label.khong_thanh_cong" /></strong></th>
                            </tr>
                            <c:set var="index" value="${0}" />
                            <c:forEach items="${reportData}" var="report">
                                <tr class="<c:if test="${index % 2 eq 0}">even</c:if> <c:if test="${index % 2 ne 0}">odd</c:if>">
                                    <c:set var="index" value="${index + 1}" />
                                    <td class="text-center">${report.doiTuong}</td>
                                    <td class="text-center"><fmt:formatDate value="${report.thoiGianNhanTin}" pattern="${datePattern}" /></td>
                                    <td class="text-center">${report.noiDungTinNhan}</td>
                                    <td class="text-center"><fmt:formatNumber type="number" value="${report.soLuongTB}" /></td>
                                    <td class="text-center"><fmt:formatNumber type="number" value="${report.soLuongTinNhan}" /></td>
                                    <td class="text-center"><fmt:formatNumber type="number" value="${report.soLuongTinNhanThanhCong}" /></td>
                                    <td class="text-center"><fmt:formatNumber type="number" value="${report.soLuongTinNhanKhongThanhCong}" /></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                    <input id="crudaction" type="hidden" name="crudaction" />
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#qStudent2015ReportQuanLyTinNhanTab');

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

    function submitForm(){
        $('#crudaction').val('search');
        $('#reportForm').submit();
    }

    function reset(){
        selectFirstItemSelect2('#loaiDoiTuongMenu');
        $('#fromDate').val('');
        $('#toDate').val('');
    }

    function export2Excel(){
        $('#crudaction').val('export');
        $('#reportForm').submit();
    }
</script>
</body>