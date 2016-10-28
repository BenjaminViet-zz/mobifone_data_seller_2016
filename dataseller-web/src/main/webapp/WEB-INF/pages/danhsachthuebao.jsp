<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="user.list"/></title>
    <meta name="menu" content="<fmt:message key="user.list"/>"/>
</head>
<c:url var="formUrl" value="/admin/user/danhsachthuebao.html"/>
<c:url var="viewUrl" value="/admin/user/view.html"/>
<c:url var="editUrl" value="/admin/editUserInfo.html"/>
<c:url var="importUrl" value="/admin/importUserFile.html"/>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i> Danh mục thuê bao</h2>

</div>
<div class="contentpanel">
    <div class="panel panel-default">
        <div class="panel-heading">
            <div class="panel-btns">
                <a class="btn btn-primary" style="margin-top:-10px" href="${editUrl}"> <fmt:message key="label.button.them"/></a>
                <%--<a class="btn btn-primary" style="margin-top:-10px" href="${importUrl}"> <fmt:message key="label.button.import"/></a>--%>
            </div>
            <h4 class="panel-title">Danh sách thuê bao</h4>
        </div>
        <div class="panel-body panel-body-nopadding">
            <c:if test ="${not empty messageResponse}">
                <div class="alert alert-${alertType}">
                    <a class="close" data-dismiss="alert" href="#">&times;</a>
                    ${messageResponse}
                </div>
            </c:if>
            <div style="clear: both;" ></div>
            <div class="row-fluid report-filter">
                <form:form commandName="items" id="listForm" action="${formUrl}" method="post" autocomplete="off" name="listForm">
                    <div class="col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Tên thuê bao</label>
                                    <div class="col-sm-8">
                                        <input name="pojo.userName" type="text" class="form-control" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Họ và tên</label>
                                    <div class="col-sm-8">
                                        <input name="pojo.displayName" type="email" class="form-control" />
                                    </div>
                                </div>
                            </div>
                            <div class="panel-footer">
                                <button class="btn btn-primary"><fmt:message key="label.search" /></button>
                                <button type="reset" class="btn btn-info"><fmt:message key="label.reset" /></button>
                            </div>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <div id="table2_wrapper" class="dataTables_wrapper" role="grid" >
                            <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formlUrl}"
                                           partialList="true" sort="external" size="${items.totalItems}" defaultsort="1"
                                           id="table2" pagesize="${items.maxPageItems}" export="false" class="table table-hover dataTable" style="margin: 1em 0 1.5em;float:left">

                                <display:column headerClass="sorting" property="userName" sortName="userName" sortable="true" titleKey="label.username" style="20%"/>
                                <display:column headerClass="sorting" property="displayName" sortName="displayName" sortable="true" titleKey="label.fullname" style="20%"/>
                                <display:column headerClass="sorting" property="mobileNumber" sortName="mobileNumber" sortable="true" titleKey="label.mobile" style="20%"/>
                                <display:column headerClass="sorting" property="email" sortName="email" sortable="true" titleKey="user.label.email" style="20%"/>
                                <display:column headerClass="sorting" sortName="status" sortable="true" titleKey="label.status" style="10%">
                                    <c:choose>
                                        <c:when test = "${table2.status == 1}">
                                            Hoạt động
                                        </c:when>
                                        <c:otherwise>
                                            Không hoạt động
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column headerClass="sorting" property="userGroup.name" sortName="userGroup" sortable="true" titleKey="label.usergroup" />
                                <display:column titleKey="label.action" style="width:10%;white-space:nowrap;">
                                    <a href="${editUrl}?pojo.userId=${table2.userId}" class="tip-top" title="Sửa"><i class="icon-edit">Sửa</i></a>
                                </display:column>
                                <display:setProperty name="paging.banner.item_name" value="Nhân viên"/>
                                <display:setProperty name="paging.banner.items_name" value="Nhân viên"/>
                            </display:table>
                            <form:hidden path="crudaction" id="crudaction"/>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
    <script language="javascript" type="text/javascript">
        $(document).ready(function(){
            // active tab
            $('#dmThueBaoTab').addClass('active');

            $(".deleteLink").click(function(){
                var id = $(this).attr("id");
                bootbox.confirm('<fmt:message key="delete.confirm.message.title"/>', '<fmt:message key="delete.confirm.message.content"/>', function(r) {
                    if(r){
                        if(id != null && id != undefined){
                            $("<input type='hidden' name='checkList' value='"+id+"'>").appendTo($("#listForm"));
                            $("#crudaction").val("delete");
                            $("#listForm").submit();
                        }
                    }
                });
            });
        });

        function submitForm(){
            $("#crudaction").val("find");
            $('#listForm').submit();
        }
    </script>
</div>