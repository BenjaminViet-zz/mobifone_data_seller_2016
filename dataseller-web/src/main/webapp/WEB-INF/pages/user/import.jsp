<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<c:url var="formUrl" value="/admin/importUserFile.html" />
<c:url var="cancelUrl" value="/admin/userList.html" />
<html>
<head>
    <title><fmt:message key="import_user.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="import_user.heading_page" />"/>
</head>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="label.reset" /></h2>
</div>
<div class="contentpanel">
    <div class="panel panel-default">
        <div class="panel-heading">
            <div class="panel-btns">
                <a onclick="importUser();" disabled="disabled" class="btn" id="submit" style="cursor: default;display: none;">
                    <i class="icon-arrow-right"></i>
                    Tiếp tục
                </a>
                <c:if test="${!empty listResult}">
                    <div class="button-actions" style="padding-right: 16px;">
                        <a class="btn btn-primary" href="<c:url value='/admin/importUserFile.html'/>" class="btn" style="cursor: pointer;margin-top:-10px;">
                            <i class="icon-arrow-left"></i>
                            Quay lại bước 1
                        </a>
                        <a class="btn btn-primary" onclick="saveUser();" id="btnSave" style="cursor: pointer;margin-top:-10px;">
                            <i class="icon-arrow-up"></i>
                            Lưu
                        </a>
                        <a class="btn btn-primary" href="<c:url value='/admin/userList.html'/>" class="cancel-link" style="cursor: pointer;margin-top:-10px;">
                            Thoát
                        </a>
                    </div>
                </c:if>
            </div>
            <h4 class="panel-title"><fmt:message key="label.button.import" /></h4>
        </div>
        <div class="panel-body panel-body-nopadding">
            <form:form commandName="command" id="frmUserImport" action="${formUrl}" method="POST" cssClass="form-horizontal" novalidate="novalidate" enctype="multipart/form-data">
                <input type="hidden" id="crudaction" name="crudaction"/>
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
                                    <a href="/files/voucher_user_vw.xls">Download file import user mẫu</a>
                                </div>
                                <p>
                                    Nếu bạn đã có sẵn file .xls để import, vui lòng chọn file bằng cách click nút "Choose File" bên dưới.
                                </p>

                            </div>

                            <div style="padding-left: 68px;height:40px;">
                                <input type="file" name="csvfile" id="csvfile"  size="40" onchange="javascript:checkEnableLinkSubmit($(this), $('#submit'));"/>

                                <%--<div class="button-actions" style="padding-right: 16px;">--%>
                                    <%--<a class="btn  btn-primary" onclick="importUser();" id="submit" style="cursor: default;float:left;">--%>
                                        <%--<i class="icon-arrow-right"></i>--%>
                                        <%--Tiếp tục--%>
                                    <%--</a>--%>
                                <%--</div>--%>
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

                    </div>
                    <div class="clear"></div>
                    <div class="row-fluid" id="contentTable">
                        <div id="tableDMHT" >
                            <table style="width: 100%;">
                                <thead>
                                <tr>
                                    <th class="table_header">Shop_code </th>
                                    <th class="table_header">User_name</th>
                                    <th class="table_header">Full_name</th>
                                    <th class="table_header">Password</th>
                                    <th class="table_header">Active</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${listResult}" var="item" varStatus="loof">
                                    <tr <c:if test="${loof.index % 2 == 0}">class="odd" style="border-top: none;" </c:if>>
                                        <td>${item.department.code}</td>
                                        <td>${item.userName}</td>
                                        <td>${item.displayName}</td>
                                        <td>${item.password}</td>
                                        <td><c:if test="${empty item.department.departmentId}">Lỗi shop code.</c:if></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
            </form:form>
        </div>
    </div>
    <script language="javascript" type="text/javascript">
        $(document).ready(function(){
            // active tab
            setActiveMenu4Admin('#menuDanhMucTab', '#dmNguoiDungTab');
        });

        function importUser(){
            $("#crudaction").val("import");
            $('#frmUserImport').submit();
        }
        function saveUser(){
            $("#crudaction").val("import-save");
            $('#frmUserImport').submit();
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
    </script>
</div>

</body>
</html>