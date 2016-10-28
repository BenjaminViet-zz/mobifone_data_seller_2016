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
    .form-group{
        width: 100% !important;
    }
    input, select{
        width: 140px !important;
    }
</style>

<c:url var="formUrl" value="/kenhphanphoi/xem-diem-doi-thuong-chi-tiet-hang-muc-theo-ngay.html" />
<c:url var="lichSuDiemUrl" value="/kenhphanphoi/xem-diem-doi-thuong.html" />
<body class="sub-page">
    <div class=" container">
        <section class="content background-none">
            <div class="row">
                <div class="col-md-offset-5 col-md-7 col-xs-12 viewScoreContent">
                    <article>
                        <a class="btn btn-danger" style="float:right; margin-bottom: 10px;" onclick="javascript: logout();"><fmt:message key="cuahanggiaodich.label.try_login_another_thue_bao" /></a>
                        <div class="clear"></div>
                        <hr/>
                        <c:if test="${not empty messageResponse}">
                            <div class="alert alert-${alertType}">
                                    ${messageResponse}
                            </div>
                        </c:if>
                        <div class="clear"></div>
                        <form:form id="reportForm" commandName="item" cssClass="form-inline" role="form" action="${formUrl}">
                            <div class="row margin-top">
                                <c:if test="${not empty monthList}">
                                    <div class="col-xs-12" style="margin-bottom: 10px;">
                                        <p class="font-size-small"><b><fmt:message key="label.thang_tich_diem" />: </b></p>
                                        <form:select path="thangTichDiem" cssStyle="width: 100%;" cssClass="form-control option" onchange="javascript: changeMonth();">
                                            <c:forEach items="${monthList}" var="month">
                                                <option <c:if test="${not empty item.thangTichDiem && item.thangTichDiem eq month}">selected="true" </c:if> value="${month}"><fmt:message key="label.thang" />&nbsp;${month}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </c:if>
                                <div class="col-xs-12 col-sm-5">
                                    <p class="font-size-small"><b>Ngày bắt đầu:</b></p>
                                    <div class="form-group row">
                                        <div class="col-xs-9 col-sm-8" style="padding-right: 0px;">
                                            <input id="fromDate" name="fromDate" class="form-control nohtml text-center prevent_type"
                                                   type="text" placeholder="${symbolDateEmpty}" style="width: 100%;"
                                                   value="<fmt:formatDate value="${item.fromDate}" pattern="${datePattern}"/>"/>
                                        </div>
                                        <div class="col-xs-3 col-sm-2 no-padding">
                                            <img class=" img-responsive calendar margin-left-small" src="<c:url value="/themes/promotion/images/Calendar-Time.png"/>" alt="">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-5">
                                    <p class="font-size-small"><b>Ngày kết thúc:</b></p>
                                    <div class="form-group row">
                                        <div class="col-xs-9 col-sm-8" style="padding-right: 0px;">
                                            <input id="toDate" name="toDate" class="form-control nohtml text-center prevent_type"
                                                   type="text" placeholder="${symbolDateEmpty}" style="width: 100%;"
                                                   value="<fmt:formatDate value="${item.toDate}" pattern="${datePattern}"/>"/>
                                        </div>
                                        <div class="col-xs-3 col-sm-2 no-padding">
                                            <img class=" img-responsive calendar margin-left-small" src="<c:url value="/themes/promotion/images/Calendar-Time.png"/>" alt="">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-2">
                                    <p></p>
                                    <div class="clear"></div>
                                    <div class="btn btn-danger" style="float: right; height: 33px; position: relative; top: 16px;" onclick="javascript: submitForm();"><fmt:message key="label.search" /></div>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="clear"></div>
                            <!-- InstanceBeginEditable name="Content" -->
                            <div class="clear"></div>
                            <c:if test="${not empty detailTableListTotalItems && detailTableListTotalItems gt 0}">
                                <c:choose>
                                    <%--Chi tiet theo loai phat sinh 1--%>
                                    <c:when test="${not empty item_Id && item_Id eq Constants.ITEM_ID_1}">
                                        <display:table name="detailTableList" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                                       partialList="true" sort="external" size="${detailTableListTotalItems}" defaultsort="0"
                                                       id="detailTableList" pagesize="${Constants.MAXPAGEITEMS}" export="false" class="table-bordered table-striped table" style="margin: 1em 0 1.5em; width: 100%;">
                                            <display:column headerClass="text-center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                                                ${detailTableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                            </display:column>
                                            <display:column headerClass="text-center" sortable="false" property="soHoaDon" titleKey="label.so_hoa_don" style="width: 10%;"/>
                                            <display:column headerClass="text-center" sortable="false" titleKey="label.ngay_hoa_don" class="text-center" style="width: 10%;">
                                                <fmt:formatDate value="${detailTableList.ngayHoaDon}" pattern="${datePattern}" />
                                            </display:column>
                                            <display:column headerClass="text-center" sortable="false" property="maHangHoa" titleKey="label.ma_hang_hoa" style="width: 10%;"/>
                                            <display:column headerClass="text-center" sortable="false" property="tenHangHoa" titleKey="label.ten_hang_hoa" style="width: 10%;"/>
                                            <display:column headerClass="text-center" sortable="false" titleKey="label.so_luong" class="text-center" style="width: 10%;">
                                                <fmt:formatNumber type="number" value="${detailTableList.soLuong}" />
                                            </display:column>
                                            <display:column headerClass="text-center" sortable="false" titleKey="label.gia_tri" class="text-right" style="width: 10%;">
                                                <fmt:formatNumber type="number" value="${detailTableList.giaTri}" maxFractionDigits="2" />
                                            </display:column>
                                            <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                            <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                        </display:table>
                                    </c:when>
                                    <%--Chi tiet theo loai phat sinh 2--%>
                                    <c:when test="${not empty item_Id && item_Id eq Constants.ITEM_ID_2}">
                                        <display:table name="detailTableList" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                                       partialList="true" sort="external" size="${detailTableListTotalItems}" defaultsort="0"
                                                       id="detailTableList" pagesize="${Constants.MAXPAGEITEMS}" export="false" class="table-bordered table-striped table" style="margin: 1em 0 1.5em; width: 100%;">
                                            <display:column headerClass="text-center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                                                ${detailTableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                            </display:column>
                                            <display:column headerClass="text-center" sortable="false" property="soEZ" titleKey="label.so_EZ" style="width: 10%;"/>
                                            <display:column headerClass="text-center nowrap" sortable="false" titleKey="label.ngay.ps" class="text-center" style="width: 10%;">
                                                <fmt:formatDate value="${detailTableList.ngay_ps}" pattern="${datePattern}" />
                                            </display:column>
                                            <display:column headerClass="text-center nowrap" sortable="false" property="noiDungTinNhan" titleKey="label.noi_dung_tin_nhan" class="text-center" style="width: 35%;" />
                                            <display:column headerClass="text-center nowrap" sortable="false" property="ketQua" titleKey="label.ket_qua" style="width: 40%;" />
                                            <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                            <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                        </display:table>
                                    </c:when>
                                    <%--Chi tiet theo loai phat sinh 3--%>
                                    <c:when test="${not empty item_Id && item_Id eq Constants.ITEM_ID_3}">
                                        <display:table name="detailTableList" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                                       partialList="true" sort="external" size="${detailTableListTotalItems}" defaultsort="0"
                                                       id="detailTableList" pagesize="${Constants.MAXPAGEITEMS}" export="false" class="table-bordered table-striped table" style="margin: 1em 0 1.5em; width: 100%;">
                                            <display:column headerClass="text-center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 8%;" >
                                                ${detailTableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                            </display:column>
                                            <display:column headerClass="text-center" sortable="false" property="so_thue_bao" titleKey="label.so_thue_bao" class="text-center" style="width: 23%;"/>
                                            <display:column headerClass="text-center" sortable="false" titleKey="label.ngay.ps" class="text-center" style="width: 23%;">
                                                <fmt:formatDate value="${detailTableList.ngay_ps}" pattern="${datePattern}" />
                                            </display:column>
                                            <display:column headerClass="text-center" sortable="false" titleKey="label.cuoc_ps" class="text-right" style="width: 23%; white-space: inherit;">
                                                <fmt:formatNumber type="number" value="${detailTableList.cuoc_ps}" maxFractionDigits="2" />
                                            </display:column>
                                            <display:column headerClass="text-center" sortable="false" titleKey="label.so_diem_qui_doi" class="text-center" style="width: 33%;">
                                                <fmt:formatNumber type="number" value="${detailTableList.soDiemQuiDoi}" maxFractionDigits="2" />
                                            </display:column>
                                            <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                            <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                        </display:table>
                                    </c:when>
                                    <%--Chi tiet theo loai phat sinh 4--%>
                                    <c:when test="${not empty item_Id && item_Id eq Constants.ITEM_ID_4}">
                                        <display:table name="detailTableList" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                                       partialList="true" sort="external" size="${detailTableListTotalItems}" defaultsort="0"
                                                       id="detailTableList" pagesize="${Constants.MAXPAGEITEMS}" export="false" class="table-bordered table-striped table" style="margin: 1em 0 1.5em; width: 100%;">
                                            <display:column headerClass="text-center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                                                ${detailTableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                            </display:column>
                                            <display:column headerClass="text-center" sortable="false" property="soHoaDon" titleKey="label.so_hoa_don" style="width: 10%;"/>
                                            <display:column headerClass="text-center" sortable="false" titleKey="label.ngay_hoa_don" class="text-center" style="width: 10%;">
                                                <fmt:formatDate value="${detailTableList.ngayHoaDon}" pattern="${datePattern}" />
                                            </display:column>
                                            <display:column headerClass="text-center" sortable="false" property="maHangHoa" titleKey="label.ma_hang_hoa" style="width: 10%;"/>
                                            <display:column headerClass="text-center" sortable="false" property="tenHangHoa" titleKey="label.ten_hang_hoa" style="width: 30%; word-break: break-word;"/>
                                            <display:column headerClass="text-center" sortable="false" titleKey="label.so_luong" class="text-center" style="width: 10%;">
                                                <fmt:formatNumber type="number" value="${detailTableList.soLuong}" />
                                            </display:column>
                                            <display:column headerClass="text-center" sortable="false" titleKey="label.gia_tri" class="text-right" style="width: 10%;">
                                                <fmt:formatNumber type="number" value="${detailTableList.giaTri}" maxFractionDigits="2" />
                                            </display:column>
                                            <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                            <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                        </display:table>
                                    </c:when>
                                    <%--Chi tiet theo loai phat sinh 5--%>
                                    <c:when test="${not empty item_Id && item_Id eq Constants.ITEM_ID_5}">
                                        <display:table name="detailTableList" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                                       partialList="true" sort="external" size="${detailTableListTotalItems}" defaultsort="0"
                                                       id="detailTableList" pagesize="${Constants.MAXPAGEITEMS}" export="false" class="table-bordered table-striped table" style="margin: 1em 0 1.5em; width: 100%;">
                                            <display:column headerClass="text-center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                                                ${detailTableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                            </display:column>
                                            <display:column headerClass="text-center" sortable="false" property="soEZ" titleKey="label.so_EZ" style="width: 15%;"/>
                                            <display:column headerClass="text-center nowrap" sortable="false" titleKey="label.ngay.ps" class="text-center" style="width: 10%;">
                                                <fmt:formatDate value="${detailTableList.ngay_ps}" pattern="${datePattern}" />
                                            </display:column>
                                            <display:column headerClass="text-center nowrap" sortable="false" property="noiDungTinNhan" titleKey="label.noi_dung_tin_nhan" class="text-center" style="width: 30%;" />
                                            <display:column headerClass="text-center nowrap" sortable="false" property="ketQua" titleKey="label.ket_qua" style="width: 40%;" />
                                            <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                            <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                        </display:table>
                                    </c:when>
                                    <%--Chi tiet theo loai phat sinh 6--%>
                                    <c:when test="${not empty item_Id && item_Id eq Constants.ITEM_ID_6}">
                                        <display:table name="detailTableList" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                                       partialList="true" sort="external" size="${detailTableListTotalItems}" defaultsort="0"
                                                       id="detailTableList" pagesize="${Constants.MAXPAGEITEMS}" export="false" class="table-bordered table-striped table" style="margin: 1em 0 1.5em; width: 100%;">
                                            <display:column headerClass="text-center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                                                ${detailTableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                            </display:column>
                                            <display:column headerClass="text-center" sortable="false" property="maGiaoDich" titleKey="label.ma_giao_dich" style="width: 10%;"/>
                                            <display:column headerClass="text-center" sortable="false" property="soEZ" titleKey="label.so_EZ" class="text-center" style="width: 10%;"/>
                                            <display:column headerClass="text-center" sortable="false" property="maGoi" titleKey="label.ma_goi" style="width: 10%;" />
                                            <display:column headerClass="text-center" sortable="false" property="tenGoi" titleKey="label.ten_goi" style="width: 30%;"/>
                                            <display:column headerClass="text-center" sortable="false" titleKey="label.ngay.ps" class="text-center" style="width: 10%;">
                                                <fmt:formatDate value="${detailTableList.ngay_ps}" pattern="${datePattern}" />
                                            </display:column>
                                            <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                            <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                        </display:table>
                                    </c:when>
                                    <%--Chi tiet theo loai phat sinh 7--%>
                                    <c:when test="${not empty item_Id && item_Id eq Constants.ITEM_ID_7}">
                                        <display:table name="detailTableList" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                                       partialList="true" sort="external" size="${detailTableListTotalItems}" defaultsort="0"
                                                       id="detailTableList" pagesize="${Constants.MAXPAGEITEMS}" export="false" class="table-bordered table-striped table" style="margin: 1em 0 1.5em; width: 100%;">
                                            <display:column headerClass="text-center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                                                ${detailTableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                            </display:column>
                                            <display:column headerClass="text-center" sortable="false" property="soEZ" titleKey="label.so_EZ" style="width: 10%;"/>
                                            <display:column headerClass="text-center nowrap" sortable="false" titleKey="label.ngay.ps" class="text-center" style="width: 10%;">
                                                <fmt:formatDate value="${detailTableList.ngay_ps}" pattern="${datePattern}" />
                                            </display:column>
                                            <display:column headerClass="text-center nowrap" sortable="false" property="noiDungTinNhan" titleKey="label.noi_dung_tin_nhan" class="text-center auto_break" style="width: 35%;" />
                                            <display:column headerClass="text-center nowrap" sortable="false" property="ketQua" titleKey="label.ket_qua" style="width: 40%;" />
                                            <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                            <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                        </display:table>
                                    </c:when>
                                </c:choose>
                            </c:if>
                            <div class="clear"></div>
                            <br/>
                            <a class="btn btn-danger" onclick="javascript: comeback();" /><fmt:message key="label.quay_lai" /></a>
                            <!-- InstanceEndEditable -->
                            <form:hidden path="item_Id" />
                            <form:hidden path="d_Id" />
                            <form:hidden path="cycle" />
                        </form:form>
                    </article>
                </div>
            </div>
        </section>
    </div>
    <script type="text/javascript">
        $(document).ready(function(){
            highlightKppFocusPage('#kppXemDiemDoiThuong');

            var currentMonth = new Date(2014, ${not empty item.thangTichDiem ? item.thangTichDiem : 10}, 0),
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
        });

        function logout(){
            $('#reportForm').attr('action', '${lichSuDiemUrl}?crudaction=dang-xuat');
            $('#reportForm').submit();
        }

        function comeback(){
            $('#reportForm').attr('action', '${formUrl}?crudaction=tro-lai');
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