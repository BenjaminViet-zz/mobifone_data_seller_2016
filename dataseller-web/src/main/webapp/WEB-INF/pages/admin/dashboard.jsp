<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="prefix" value="/user" />
<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:set var="prefix" value="/admin" />
</security:authorize>

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_content">
                <div class="bs-example" data-example-id="simple-jumbotron">
                    <div class="jumbotron">
                        <fmt:message key="page_not_available" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>