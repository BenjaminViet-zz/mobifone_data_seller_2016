<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<link href="<c:url value="/themes/promotion/css/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
<c:url var="formUrl" value="/kenhphanphoi/ket-qua-du-thuong.html" />
<style>
    .col-xs-2{
        margin-top: 15px;
        font-size: 15.5px;
        font-weight: bold;
    }
    select option{
        padding: 3px 0px;
    }
    .form-control{
        background-color: #fffbe2 !important;
    }

    #reportContainer .mCustomScrollBox{
        height: auto !important;
    }

    #reportContainer .mCSB_horizontal.mCSB_inside > .mCSB_container{
        margin-bottom: 0px !important;
    }
</style>
<body class="sub-page">
<div class=" container">
    <section class="content background-none">
        <div class="row">
            <div class="col-md-offset-5 col-md-7 col-xs-12">
                <article>
                    <form:form id="reportForm" commandName="item" cssClass="form-inline" role="form" action="${formUrl}">
                        <!-- InstanceBeginEditable name="Content" -->
                        <c:choose>
                            <c:when test="${not empty enableViewKQDTFlag && enableViewKQDTFlag}">
                                <c:if test="${not empty messageResponse}">
                                    <div class="alert alert-${alertType}">
                                            ${messageResponse}
                                    </div>
                                </c:if>
                                <div class="clear"></div>
                                <div class="row">
                                    <div class="col-xs-14">
                                        <div class="col-xs-2"><fmt:message key="label.giai" /></div>
                                        <div class="col-xs-4 viewScoreContent">
                                            <div class="form-group">
                                                <form:select path="kpp_gift_Id" cssClass="form-control" cssStyle="width: 150px;">
                                                    <option value=""><fmt:message key="label.all" /></option>
                                                    <c:forEach items="${kppGiftList}" var="gift">
                                                        <option <c:if test="${item.kpp_gift_Id eq gift.gift_Id}">selected="true"</c:if> value="${gift.gift_Id}">${gift.gift_level}</option>
                                                    </c:forEach>
                                                </form:select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-14">
                                        <div class="col-xs-2"><fmt:message key="label.ma_diem_ban_hang" /></div>
                                        <div class="col-xs-4 viewScoreContent">
                                            <div class="form-group">
                                                <form:input path="dealer_Code" cssClass="form-control" cssStyle="width: 150px !important;" />
                                            </div>
                                        </div>
                                        <div class="col-xs-2" style="text-align: right;"><fmt:message key="label.so_EZ" /></div>
                                        <div class="col-xs-4 viewScoreContent">
                                            <div class="form-group">
                                                <form:input path="soEZ" cssClass="form-control onlyNumberInputForceValue" maxlength="11" placeholder="Nhập số EZ" cssStyle="width: 150px !important; position: relative; right: -10px;" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-14">
                                        <div class="col-xs-2"></div>
                                        <div class="col-xs-4 viewScoreContent">
                                            <div class="form-group">
                                            </div>
                                        </div>
                                        <div class="col-xs-2"></div>
                                        <div class="col-xs-4 viewScoreContent">
                                            <div class="form-group" style="float: right;">
                                                <button type="submit" class="btn btn-danger"><fmt:message key="label.search" /></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <br />
                                <div class="clear"></div>
                                <c:if test="${not empty items.listResult}">
                                    <div>
                                        <div id="reportContainer" style="width:100%">
                                            <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                                           partialList="true" sort="external" size="${items.totalItems }" defaultsort="0"
                                                           id="tableList" pagesize="${items.maxPageItems}" export="false" class="table-bordered table-striped table" style="margin: 1em 0 1.5em; width: 100%;">
                                                <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                                                    ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                                </display:column>
                                                <display:column headerClass="table_header nowrap" sortable="false" property="dealer_code" titleKey="label.ma_diem_ban_hang" class="nowrap" style="width: 15%;" />
                                                <display:column headerClass="table_header nowrap" sortable="false" property="dealer_name" titleKey="label.diem_ban_hang" class="nowrap" style="width: 15%;" />
                                                <display:column headerClass="table_header nowrap" sortable="false" property="ma_phieu" titleKey="label.ma_du_thuong" class="nowrap" style="width: 15%;" />
                                                <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.ngay_cap" class="nowrap text-center" style="width: 15%;" >
                                                    <fmt:formatDate value="${tableList.ngay_ps}" pattern="${datePattern}" />
                                                </display:column>
                                                <display:column headerClass="table_header nowrap" sortable="false" property="gift_Level" titleKey="label.giai" class="nowrap" style="width: 15%;" />
                                                <display:column headerClass="table_header nowrap" sortable="false" property="exchange_gift_name" titleKey="label.giai_thuong" class="nowrap" style="width: 15%;" />
                                                <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.ma_trung_thuong"/></display:setProperty>
                                                <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.ma_trung_thuong"/></display:setProperty>
                                            </display:table>
                                        </div>
                                        <div id="linksContainer">

                                        </div>
                                    </div>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <div class="text-center"><h1 class="text-red margin-bottom-large"><b>KẾT QUẢ DỰ THƯỞNG</b></h1></div>
                                <h4 style="font-size: 22px;line-height: 1.6;" class="bg-blue padding-round text-center text-white box-border margin-top">Hệ thống đang cập nhật, Xin vui lòng quay lại vào ngày 30/10/2014</h4>
                                <p>&nbsp;</p>
                                <p>&nbsp;</p>
                            </c:otherwise>
                        </c:choose>
                        <!-- InstanceEndEditable -->
                    </form:form>
                </article>
            </div>
        </div>
    </section>
</div>
<script type="text/javascript" src="<c:url value="/scripts/jquery/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script type="text/javascript">
    $(document).ready(function(){
        highlightKppFocusPage('#kppKetQuaDuThuong');
        $('#reportContainer').mCustomScrollbar({axis:"x"});

        checkMovePageLinks();

        $('#toDateIcon').click(function () {
            $('#signedDate').focus();
            return true;
        });

        $('.onlyNumberInputForceValue').keyup(function(){
            $(this).val(function(el,value){
                return value.replace(/[^\d]/g,'');
            });
        });
    });

    function checkMovePageLinks(){
        if(${not empty items.listResult}){
            $('#reportContainer .pagebanner').appendTo('#linksContainer');
            $('#reportContainer .pagelinks').appendTo('#linksContainer');
        }
    }
</script>
</body>