<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<c:url var="formUrl" value="/thuebaophattrienmoi/kenhphanphoi/tra-cuu-ket-qua-thuc-hien.html"/>
<body class="sub-page">
<div class=" container">
    <section>
        <div class="container">
            <!-- InstanceBeginEditable name="content" -->
            <div class="row">
                <div class="col-xs-12 viewScoreContent">
                    <article>
                        <form:form id="reportForm" commandName="item" cssClass="form-inline" role="form" action="${formUrl}">
                            <div class="clear"></div>
                            <div class="info_user">
                                <table class="tblUserInfoKQTH_2015">
                                    <tbody>
                                        <tr>
                                            <td><span class="info_label">Số EZ: </span></td>
                                            <td>${retailDealerInfo.ez_isdn}</td>
                                        </tr>
                                        <tr>
                                            <td><span class="info_label">Mã ĐBH: </span></td>
                                            <td>${retailDealerInfo.dealer_code}</td>
                                        </tr>
                                        <tr>
                                            <td><span class="info_label">Tên ĐBH: </span></td>
                                            <td>${retailDealerInfo.dealer_name}</td>
                                        </tr>
                                        <tr>
                                            <td><span class="info_label">Quận/Huyện: </span></td>
                                            <td>${retailDealerInfo.district_name}</td>
                                        </tr>
                                        <tr>
                                            <td><span class="info_label">Ngày đăng ký: </span></td>
                                            <td><fmt:formatDate value="${retailDealerInfo.createdDate}" pattern="${datePattern}"/></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <a class="btn btn-danger" style="float:right;" onclick="javascript: logout();"><fmt:message key="cuahanggiaodich.label.try_login_another_thue_bao"/></a>
                            <div class="clear"></div>
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <label class="col-md-4 control-label font-size-small no-padding-left"><b><fmt:message key="label.thue_bao" />: </b></label>
                                    <div class="col-md-4 no-padding-left">
                                        <form:input path="pojo.customer_Isdn" cssClass="form-control onlyNumberInputForceValue"></form:input>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="col-md-4 control-label font-size-small no-padding-left"><b><fmt:message key="label.ten_goi" />: </b></label>
                                    <div class="col-md-4 no-padding-left">
                                        <form:select path="pojo.goiCuoc.package_Id" cssClass="form-control option">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${goiCuocList}" var="goiCuoc">
                                                <option <c:if test="${item.pojo.goiCuoc.package_Id eq goiCuoc.package_Id}">selected="true" </c:if> value="${goiCuoc.package_Id}">${goiCuoc.package_Name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="col-md-4 control-label font-size-small no-padding-left"><b><fmt:message key="label.tinh_trang_khuyen_khich" />: </b></label>
                                    <div class="col-md-4 no-padding-left">
                                        <form:select path="pojo.prom_Condition_Status" cssClass="form-control option">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <option <c:if test="${item.pojo.prom_Condition_Status eq Constants.REGISTRATION_TRANS_PROM_CONDITION_STATUS_NO_SATISFY}">selected="true" </c:if> value="0"><fmt:message key="label.khong_hop_le" /></option>
                                            <option <c:if test="${item.pojo.prom_Condition_Status eq Constants.REGISTRATION_TRANS_PROM_CONDITION_STATUS_SATISFY}">selected="true" </c:if> value="1"><fmt:message key="label.hop_le" /></option>
                                            <option <c:if test="${item.pojo.prom_Condition_Status eq Constants.REGISTRATION_TRANS_PROM_CONDITION_STATUS_OVER_RULE}">selected="true" </c:if> value="2"><fmt:message key="label.vuot_quy_dinh" /></option>
                                        </form:select>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="col-md-4 control-label font-size-small no-padding-left"><b><fmt:message key="label.tinh_trang_giao_dich" />: </b></label>
                                    <div class="col-md-4 no-padding-left">
                                        <form:select path="pojo.trans_Status" cssClass="form-control option">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <option <c:if test="${item.pojo.trans_Status eq 0}">selected="true" </c:if> value="0"><fmt:message key="label.khong_thanh_cong" /></option>
                                            <option <c:if test="${item.pojo.trans_Status eq 1}">selected="true" </c:if> value="1"><fmt:message key="label.thanh_cong" /></option>
                                        </form:select>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="col-md-4 control-label font-size-small no-padding-left"><b><fmt:message key="label.ngay_dang_ky_tu" />: </b></label>
                                    <div class="col-md-4 no-padding-left">
                                        <input id="fromDate" name="thoiGianDangKyFrom" class="form-control nohtml text-center prevent_type"
                                               type="text" placeholder="${symbolDateEmpty}"
                                               value="<fmt:formatDate value="${item.thoiGianDangKyFrom}" pattern="${datePattern}"/>"/>
                                    </div>
                                    <div class="col-sm-2 no-padding icon_calendar">
                                        <img class=" img-responsive calendar margin-left-small" src="<c:url value="/themes/promotion/images/Calendar-Time.png"/>" alt="">
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="col-md-4 control-label font-size-small no-padding-left"><b><fmt:message key="label.ngay_dang_ky_den" />: </b></label>
                                    <div class="col-md-4 no-padding-left">
                                        <input id="toDate" name="thoiGianDangKyTo" class="form-control nohtml text-center prevent_type"
                                               type="text" placeholder="${symbolDateEmpty}"
                                               value="<fmt:formatDate value="${item.thoiGianDangKyTo}" pattern="${datePattern}"/>"/>
                                    </div>
                                    <div class="col-sm-2 no-padding icon_calendar">
                                        <img class=" img-responsive calendar margin-left-small" src="<c:url value="/themes/promotion/images/Calendar-Time.png"/>" alt="">
                                    </div>
                                </div>
                                <div class="clear"></div>
                                <div class="col-xs-12 text-center">
                                    <a class="btn btn-danger traCuuBtn" style="margin: 20px 0px;" onclick="javascript: submitForm();"><i class="icon-refresh"></i>&nbsp;<fmt:message key="label.search" /></a>
                                </div>
                            </div>
                            <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                           partialList="true" sort="external" size="${items.totalItems}"
                                           id="tableList" pagesize="${Constants.MAXPAGEITEMS}" export="false" class="table-bordered table-striped table" style="margin: 1em 0 1.5em; width: 100%;">
                                <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                                    ${tableList_rowNum + (page * Constants.REPORT_MAXPAGEITEMS)}
                                </display:column>
                                <display:column headerClass="text-center nowrap" sortable="false" property="ez_Isdn" titleKey="label.so_ez_dang_ky" class="text-center" style="width: 10%;" />
                                <display:column headerClass="text-center nowrap" sortable="false" property="customer_Isdn" titleKey="label.thue_bao_khach_hang" class="nowrap text-center" style="width: 10%;" />
                                <display:column headerClass="text-center nowrap" sortable="false" property="goiCuoc.package_Name" titleKey="label.ten_goi" class="text-center nowrap" style="width: 20%;" />
                                <display:column headerClass="text-center nowrap" sortable="false" titleKey="label.ngay_dang_ky" class="text-center nowrap" style="width: 10%;" >
                                    <fmt:formatDate value="${tableList.trans_Date}" pattern="${datePattern}" />
                                </display:column>
                                <display:column headerClass="text-center nowrap" sortable="false" titleKey="label.tinh_trang_khuyen_khich" class="text-center" style="width: 25%;" >
                                    <c:choose>
                                        <c:when test="${not empty tableList.prom_Condition_Status}">
                                            <c:choose>
                                                <c:when test="${tableList.prom_Condition_Status eq Constants.REGISTRATION_TRANS_PROM_CONDITION_STATUS_NO_SATISFY}"><fmt:message key="label.khong_hop_le" /></c:when>
                                                <c:when test="${tableList.prom_Condition_Status eq Constants.REGISTRATION_TRANS_PROM_CONDITION_STATUS_SATISFY}">
                                                    <fmt:formatNumber type="number" value="${tableList.goiCuoc.prom_Amount}" maxFractionDigits="2" />
                                                </c:when>
                                                <c:when test="${tableList.prom_Condition_Status eq Constants.REGISTRATION_TRANS_PROM_CONDITION_STATUS_OVER_RULE}"><fmt:message key="label.vuot_quy_dinh" /></c:when>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="label.chua_xet" />
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column headerClass="text-center nowrap" sortable="false" titleKey="label.tinh_trang_giao_dich" class="text-center" style="width: 20%;" >
                                     <c:choose>
                                        <c:when test="${not empty tableList.trans_Status && tableList.trans_Status eq Constants.REGISTRATION_TRANS_TRANS_STATUS_SUCCESS}">
                                            <fmt:message key="label.thanh_cong" />
                                        </c:when>
                                        <c:otherwise><fmt:message key="label.khong_thanh_cong" /></c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                            </display:table>
                        </form:form>
                    </article>
                </div>
            </div>
            <!-- InstanceEndEditable -->
        </div>
    </section>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        highlightKppFocusPage('#kppKetQuaThucHien');

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

    function logout() {
        $('#reportForm').attr('action', '${formUrl}?crudaction=dang-xuat');
        $('#reportForm').submit();
    }

    function submitForm() {
        $('#reportForm').submit();
    }
</script>
</body>