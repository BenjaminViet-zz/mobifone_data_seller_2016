<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="user.profile.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="user.profile.heading_page" />"/>
</head>

<c:set var="prefix" value="/user" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<c:url var="backUrl" value="${prefix}/dashboard.html"/>
<c:url var="formUrl" value="${prefix}/profile.html"/>

<c:if test="${!empty messageResponse}">
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_content">
                    <div class="alert alert-${alertType} no-bottom">
                        <a class="close" data-dismiss="alert" href="#">&times;</a>
                            ${messageResponse}
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_content">
                <form:form commandName="item" cssClass="form-horizontal form-label-left" id="formEdit" action="${formUrl}" method="post" validate="validate">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="displayName">
                            <fmt:message key="label.full_name" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="displayName" path="pojo.displayName" cssClass="required nohtml form-control"></form:input>
                            <form:errors for="displayName" path="pojo.displayName" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userName">
                            <fmt:message key="label.user_name" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="userName" path="pojo.userName" cssClass="required nohtml form-control"></form:input>
                            <form:errors for="userName" path="pojo.userName" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="password">
                            <fmt:message key="user.label.password" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:password id="password" path="pojo.password" cssClass="required nohtml form-control has-attach-add-on"></form:password>
                            <span class="add-on-attach" id="btnShowHidePassword">Xem</span>
                            <form:errors for="password" path="pojo.password" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userGroup">
                            <fmt:message key="user.label.usergroup" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select id="userGroup" path="pojo.userGroup.userGroupId" cssClass="required form-control" cssStyle="width: 250px;">
                                <form:option value=""><fmt:message key="label.select" /></form:option>
                                <c:forEach items="${userGroups}" var="userGroup">
                                    <option <c:if test="${userGroup.userGroupId eq item.pojo.userGroup.userGroupId}">selected="true"</c:if> value="${userGroup.userGroupId}">${userGroup.description}</option>
                                </c:forEach>
                            </form:select>
                            <form:errors path="pojo.userGroup.userGroupId" for="userGroup" cssClass="error-inline-validate" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="status">
                            <fmt:message key="label.status" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select cssClass="form-control" id="status" path="pojo.status" cssStyle="width: 150px;">
                                <option <c:if test="${1 eq item.pojo.status}">selected="true"</c:if> value="1"><fmt:message key="label.active" /></option>
                                <option <c:if test="${0 eq item.pojo.status}">selected="true"</c:if> value="0"><fmt:message key="label.inactive" /></option>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a href="${backUrl}" class="btn btn-info"><fmt:message key="label.home_back" /></a>&nbsp;
                            <button id="btnSave" class="btn btn-primary">
                                <fmt:message key="label.update" />
                            </button>
                        </div>
                    </div>
                    <input type="hidden" name="crudaction" value="insert-update" />
                    <form:hidden path="pojo.userId" />
                </form:form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        $('#password').val('${item.pojo.password}');
        $("#btnSave").click(function(){
            if($('#formEdit').valid()){
                $("#formEdit").submit();
            }
        });
    });
</script>