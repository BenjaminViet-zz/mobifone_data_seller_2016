<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<c:url var="formUrl" value="/admin/department/import.html" />
<c:url var="cancelUrl" value="/admin/danhmuccuahang.html" />
<html>
<head>

</head>
<body>

<div class="pageheader">
    <h2><i class="fa fa-edit"></i> Danh mục cửa hàng</h2>

</div>
<div class="contentpanel">
    <form:form commandName="command"  id="frmDepartmentImport" action="${formUrl}" method="post" cssClass="form-horizontal" novalidate="novalidate" enctype="multipart/form-data">
    <div class="panel panel-default">
        <div class="panel-heading">
            <div class="panel-btns">
                <a href="#null" onclick="javascript:checkSubmit($('#submit'), $('#frmDepartmentImport'));" disabled="disabled" class="btn" id="submit" style="cursor: default;display: none;">
                    <i class="icon-arrow-right"></i>
                    Tiếp tục
                </a>
            </div>
            <h4 class="panel-title">Danh sách cửa hàng</h4>
        </div>
        <div class="panel-body panel-body-nopadding">

        <c:if test="${empty listResult}">
            <div class="widget-box">
                <div class="widget-title"><span class="icon"><i class="icon-file"></i></span><h5>Bước 1 / 2: Chọn file import</h5></div>
                <div class="widget-content nopadding">
                    <div class="import_pane">
                        <p>
                            Trước khi tiếp tục, chúng tôi đề nghị bạn download file import mẫu và xem lại hướng dẫn tạo file import hợp lệ.<br/>
                            Bạn sẽ không thể hoàn thành các bước nếu không có file import hợp lệ được upload.
                            <br/><br/>
                            Bên dưới là những đường link để hướng dẫn bạn cách tạo file import hợp lệ cũng như file import mẫu để bạn làm quen và tiếp tục.
                        </p>

                        <div style="padding: 5px 0px 12px 60px;">
                            <a href="<c:url value="/files/voucher_shop_vw.xls" />">Download file import Danh mục cửa hàng mẫu</a>
                        </div>
                        <p>
                            Nếu bạn đã có sẵn file .xls để import, vui lòng chọn file bằng cách click nút "Choose File" bên dưới.
                        </p>

                    </div>

                    <div style="padding-left: 68px;height:40px;">
                        <input type="file" name="csvfile" id="csvfile"  size="40" onchange="javascript:checkEnableLinkSubmit($(this), $('#submit'));"/>
                    </div>

                    <div style="padding-bottom:5px">
                        <div class="clear"></div>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${!empty listResult}">
        <div class="widget-box">
            <div class="widget-title"><span class="icon"><i class="icon-file"></i></span><h5>Bước 2 / 2: Thông tin file tải lên</h5></div>
            <div class="widget-content nopadding">
                <div class="import_pane">
                    <p>
                        File upload của bạn hợp lệ. Bạn có thể xem lại thông tin bên dưới và kiểm tra lại lần nữa trước khi tiếp tục. Sau đó,
                        ấn nút "Lưu và Cập nhật" để lưu thông tin File upload vào hệ thống.
                    </p>
                </div>
            </div>
        </div>
        <div style="padding-top: 20px;margin-left: 30px;">

            <div class="button-actions" style="padding-right: 16px;">

                <input type="hidden" name="crudaction" value="import-save"/>
                <a class="btn btn-primary" id="btnSave" style="cursor: pointer;">
                    <i class="icon-arrow-up"></i>
                    Lưu
                </a>
                <a href="<c:url value='/admin/department/import.html'/>" class="btn" style="cursor: pointer;">
                    <i class="icon-arrow-left"></i>
                    Quay lại bước 1
                </a>
            </div>
        </div>
        <div class="clear"></div>
        <div class="table-responsive">
                <div id="table2_wrapper" class="dataTables_wrapper" role="grid" >
                <table class="table table-hover dataTable" style="margin: 1em 0 1.5em;float:left">
                    <thead>
                    <tr>
                        <th class="table_header">Mã cửa hàng</th>
                        <th class="table_header">Tên cửa hàng</th>
                        <th class="table_header">Địa chỉ</th>
                        <th class="table_header">Số điện thoại</th>
                        <th class="table_header">Liên hệ</th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${listResult}" var="item" varStatus="loof">
                        <tr <c:if test="${loof.index % 2 == 0}">class="odd" style="border-top: none;" </c:if>>
                            <td>${item.code}</td>
                            <td>${item.name}</td>
                            <td>${item.address}</td>
                            <td>${item.tel}</td>
                            <td>${item.contactName}</td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        </c:if>
        <c:if test="${!empty errors}">
            <div class="widget-box">
                <div class="widget-title"><span class="icon"><i class="icon-file"></i></span><h5>Thông tin sai hoặc chưa có</h5></div>
                <div class="widget-content nopadding">
                    <div class="import_pane">
                        <p>
                            Một số thông tin trong file xls bạn import bị sai hoặc chưa có trong hệ thống.<br/>
                            Chúng tôi đã lưu vào hệ thống những thông tin đúng trong file của bạn.<br/>
                            Dưới đây là danh sách các thông tin không có trong hệ thống.
                        </p>
                    </div>
                </div>
            </div>
            <div class="row-fluid" id="contentError">
                <div style="width:500px;">
                    <table class="tableVms">
                        <thead>
                        <tr>
                            <th class="table_header">Giá trị lỗi hoặc chưa tạo</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="error" items="${errors}">
                            <tr>
                                <td>${error}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
        <div class="clear"></div>
        <a class="btn btn-info" href="<c:url value="/admin/danhmuccuahang.html" />"><fmt:message key="label.huy" /></a>
        </form:form>
        </div>
    </div>
    <script src='<c:url value="/themes/promotion/jquery/jquery.min.js"/>' type='text/javascript'></script>
    <script language="javascript" type="text/javascript">
        $(document).ready(function(){
            // active tab
            setActiveMenu4Admin('#menuDanhMucTab', '#dmCuaHang');

            $("#btnSave").click(function(){
                $("#crudaction").val("insert-update");
                $("#frmDepartmentImport").submit();
            });
        });

        function submitForm(){
            $("#crudaction").val("find");
            $('#frmDepartmentImport').submit();
        }
        function checkEnableLinkSubmit(fileUpload, linkSubmit){
            if (fileUpload.val() != ""){
                linkSubmit.css('display', 'block');
                linkSubmit.removeAttr("disabled");
                linkSubmit.css('cursor', 'pointer');
            }else{
                linkSubmit.css('display', 'none');
                linkSubmit.attr("disabled", "disabled");
                linkSubmit.css('cursor', 'default');
            }
        }

        function checkSubmit(linkSubmit, formSubmit){
            if (linkSubmit.attr("disabled") === undefined){
                formSubmit.submit();
            }
        }
    </script>
</div>
</body>
</html>