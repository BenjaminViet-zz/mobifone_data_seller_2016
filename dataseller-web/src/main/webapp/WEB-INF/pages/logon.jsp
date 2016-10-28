<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/common/taglibs.jsp"%>

<security:authorize ifAnyGranted="ADMIN">
    <c:redirect url="/admin/userList.html"/>
</security:authorize>

<security:authorize ifAnyGranted="NHANVIEN">
    <c:redirect url="/cuahangmobifone/tracuutheomaphieu.html"/>
</security:authorize>

<security:authorize ifAnyGranted="TONGDAI">
    <c:redirect url="/tongdai/tracuuthongtinthuebao.html"/>
</security:authorize>

<security:authorize ifAnyGranted="CHINHANH">
    <c:redirect url="/chinhanh/thuebaophattrienmoi/danhmucdiembanhang.html"/>
</security:authorize>
<security:authorize ifAnyGranted="BAOCAO">
    <c:redirect url="/baocao/thuebaophattrienmoi/baocaotinnhandangkygoidbh.html"/>
</security:authorize>
<security:authorize ifNotGranted="NHANVIEN,ADMIN">
    <c:redirect url="/tich-diem-cuoc-goi-nhan-voucher/trang-chu.html"/>
</security:authorize>


