<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="user.profile.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="user.profile.heading_page" />"/>
    <style>
        .form-group label:first-child{
            margin-top: 0px !important;
        }
    </style>
</head>

<c:set var="prefix" value="/admin" />
<security:authorize ifAnyGranted="NHANVIEN">
    <c:set var="prefix" value="/cuahangmobifone" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<security:authorize ifAnyGranted="TONGDAI">
    <c:set var="prefix" value="/tongdai" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/profile.html"/>

<body>
<div class="contentpanel">
    <div class="panel panel-default">

        <div class="panel-body panel-body-nopadding">
            <c:if test ="${not empty messageResponse}">
                <div class="alert alert-${alertType}">
                    <a class="close" data-dismiss="alert" href="#">&times;</a>
                        ${messageResponse}
                </div>
            </c:if>
            <div class="row-fluid report-filter">
                <form:form commandName="item" id="formEdit" action="${formUrl}" method="post" cssClass="form-horizontal" validate="validate">
                    <div class="col-md-10">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.user_name" /></label>
                                    <div class="col-sm-8">
                                        <form:input id="userName" path="pojo.userName" cssClass="required nohtml form-control" />
                                        <form:errors for="userName" path="pojo.userName" cssClass="error-inline-validate" />
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="user.label.password" /></label>
                                    <div class="col-sm-8">
                                        <form:password id="password" path="pojo.password" cssClass="required nohtml form-control" />
                                        <span class="add-on-attach" id="btnShowHidePassword">Xem</span>
                                        <form:errors for="password" path="pojo.password" cssClass="error-inline-validate" />
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="user.label.fullname" /></label>
                                    <div class="col-sm-8">
                                        <form:input id="displayName" path="pojo.displayName" cssClass="nohtml form-control"></form:input>
                                        <form:errors for="displayName" path="pojo.displayName" cssClass="error-inline-validate"></form:errors>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="user.label.email" /></label>
                                    <div class="col-sm-8">
                                        <form:input id="email" path="pojo.email" cssClass="nohtml form-control"></form:input>
                                        <form:errors for="email" path="pojo.email" cssClass="error-inline-validate"></form:errors>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="user.label.usergroup" /></label>
                                    <div class="col-sm-8" style="position: relative; bottom: -6px;"><span>${item.pojo.userGroup.name}</span></div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="user.label.status" /></label>
                                    <div class="col-sm-8">
                                        <form:select path="pojo.status">
                                            <option value="1" <c:if test="${item.pojo.status eq 1}">selected="true"</c:if>><fmt:message key="label.active"/></option>
                                            <option value="0" <c:if test="${item.pojo.status eq 0}">selected="true"</c:if>><fmt:message key="label.inactive"/></option>
                                        </form:select>
                                        <form:errors for="status" path="pojo.status" cssClass="error-inline-validate" />
                                    </div>
                                </div>
                            </div>

                            <div class="controls">
                                <a class="btn btn-info" id="btnSave" >
                                    <i class="icon-edit"> </i>
                                    <fmt:message key="label.update"/>
                                </a>
                                <a href="${prefix}/profile.html" class="btn btn-info">
                                    <i class="icon-backward"></i>
                                    <fmt:message key="label.huy"/>
                                </a>
                            </div>

                            <form:hidden path="pojo.userId"/>
                            <input type="hidden" name="crudaction" id="crudaction" value="insert-update"/>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#menuDanhMucTab', '#dmNguoiDungTab');

        $('#password').val('${item.pojo.password}');
        $("#btnSave").click(function(){
            if($('#formEdit').valid()){
                $("#formEdit").submit();
            }
        });
    });
</script>
</body>