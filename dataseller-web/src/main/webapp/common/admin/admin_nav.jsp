<%@ include file="/common/taglibs.jsp" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<c:set var="prefix" value="/baocao" />
<security:authorize ifAnyGranted="NHANVIEN">
    <c:set var="prefix" value="/cuahangmobifone" />
</security:authorize>
<security:authorize ifAnyGranted="TONGDAI">
    <c:set var="prefix" value="/tongdai" />
</security:authorize>
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<div class="leftpanelinner">

<ul class="mega-menu right popup_menu">
    <li class="dc-mega-li"><a href="#" class="dc-mega">Dữ Liệu Hệ Thống<span class="dc-mega-icon"></span></a>
        <div class="sub-container mega" style="margin-top: -100px; z-index: 1000; width: 770px; display: none; left: 209px;">
            <security:authorize ifAnyGranted="ADMIN,TONGDAI,CHINHANH">
                <ul class="sub">
                    <div class="row first">
                        <li class="mega-unit mega-hdr"><a href="#" class="mega-hdr-a">Danh Mục Hệ Thống</a>
                            <ul>
                                <security:authorize ifAnyGranted="ADMIN">
                                    <li id="dmNguoiDungTab"><a href="<c:url value="${prefix}/danhmucnguoidung.html"/>">1. Danh Mục Người Dùng</a></li>
                                    <li id="dmChiNhanhTab"><a href="<c:url value="${prefix}/danhmucchinhanh.html"/>">2. Danh Mục Chi Nhánh</a></li>
                                    <li id="dmCuaHangTab"><a href="<c:url value="${prefix}/danhmuccuahang.html"/>">3. Danh Mục Cửa Hàng</a></li>
                                    <li id="dmChuongTrinh"><a href="<c:url value="${prefix}/danhmucchuongtrinh.html"/>">4. Danh Mục Chương Trình</a></li>
                                </security:authorize>
                                <li id="dmDiemBanHangTab"><a href="<c:url value="${prefix}/thuebaophattrienmoi/danhmucdiembanhang.html"/>">5. Danh Mục Điểm Bán Hàng</a></li>
                                <li id="dmGoiCuocTab"><a href="<c:url value="${prefix}/thuebaophattrienmoi/danhmucgoicuoc.html"/>">6. Danh Mục Gói Cước</a></li>
                            </ul>
                        </li>
                    </div>
                    <div class="row">
                        <li class="mega-unit mega-hdr"><a href="#" class="mega-hdr-a">Import</a>
                            <ul>
                                <li id="dmDangKyThoaThuanTab"><a href="<c:url value="${prefix}/thuebaophattrienmoi/importDSDangKyThoaThuanDBH.html"/>">1. Import DS ĐBH Đăng Ký Thoả Thuận</a></li>
                            </ul>
                        </li>
                    </div>
                    <security:authorize ifAnyGranted="ADMIN">
                        <div class="row last">
                            <li class="mega-unit mega-hdr"><a href="#" class="mega-hdr-a">Ánh Xạ Chi Nhánh</a>
                                <ul>
                                    <li id="anhXaChiNhanhTab"><a href="<c:url value="${prefix}/anhxachinhanh.html"/>">1. Ánh Xạ Chi Nhánh</a></li>
                                </ul>
                            </li>
                        </div>
                    </security:authorize>
                </ul>
            </security:authorize>
        </div>
    </li>
</ul>
<ul class="mega-menu right popup_menu" id="mega-1">
    <li class="dc-mega-li"><a href="#" class="dc-mega">Tích Điểm Cuộc Gọi 2014<span class="dc-mega-icon"></span></a>
        <div class="sub-container mega" style="margin-top: -100px; z-index: 1000; width: 770px; display: none; left: 209px;"><ul class="sub">
            <security:authorize ifAnyGranted="TONGDAI,ADMIN,CHINHANH,NHANVIEN">
                <div class="row first">
                    <security:authorize ifAllGranted="CHINHANH,ADMIN">
                        <li class="mega-unit mega-hdr"><a href="#" class="mega-hdr-a">Tra Cứu Thông Tin Thuê Bao</a>
                            <ul>
                                <li id="tdcgTraCuuThongTinThueBaoTab"><a href="<c:url value="${prefix}/tracuuthongtinthuebao.html" />">1. Tra Cứu Thông tin thuê bao</a></li>
                            </ul>
                        </li>
                    </security:authorize>
                </div>
                <div class="row">
                    <security:authorize ifAnyGranted="NHANVIEN,TONGDAI,ADMIN,CHINHANH">
                        <li class="mega-unit mega-hdr"><a href="#" class="mega-hdr-a">Đổi Quà</a>
                            <ul>
                                <li id="tdcgTraCuuTheoMaPhieuTab"><a href="<c:url value="${prefix}/tracuutheomaphieu.html" />">1. Tra Cứu Theo Mã Phiếu</a></li>
                                <li id="tdcgTraCuuTheoThueBaoTab"><a href="<c:url value="${prefix}/tracuutheosothuebao.html" />">2. Tra Cứu Theo Thuê Bao</a></li>
                            </ul>
                        </li>
                    </security:authorize>
                </div>
                <div class="row">
                    <security:authorize ifAnyGranted="ADMIN,CHINHANH">
                        <li class="mega-unit mega-hdr"><a href="#" class="mega-hdr-a">Chi khuyến khích ĐBH</a>
                            <ul>
                                <li id="doiTienDBHTab"><a href="<c:url value="${prefix}/chikhuyenkhichdbh.html" />"><i class="fa fa-caret-right"></i>Chi khuyến khích ĐBH</a></li>
                                <li id="lichSuDoiTienDBHTab"><a href="<c:url value="${prefix}/lichsuchikhuyenkhichdbh.html" />"><i class="fa fa-caret-right"></i>Lịch sử chi khuyến khích ĐBH</a></li>
                                <li id="traCuuThongTinKPPTab"><a href="<c:url value="${prefix}/baocaochitietphatsinh.html" />"><i class="fa fa-caret-right"></i>Báo cáo chi tiết phát sinh</a></li>
                            </ul>
                        </li>
                    </security:authorize>
                </div>
                <div class="row">
                    <security:authorize ifAnyGranted="NHANVIEN,TONGDAI,ADMIN,CHINHANH">
                        <li class="mega-unit mega-hdr last"><a href="#" class="mega-hdr-a">Tra Cứu Cửa Hàng Khác</a>
                            <ul>
                                <li id="tdcgTraCuuKhoTab"><a href="<c:url value="${prefix}/khohangcuahangkhac.html" />">1. Tra Cứu Kho</a></li>
                            </ul>
                        </li>
                    </security:authorize>
                </div>

                <div class="row">
                    <security:authorize ifAnyGranted="CHINHANH,ADMIN">
                        <li class="mega-unit mega-hdr"><a href="#" class="mega-hdr-a">Kho Vận</a>
                            <ul>
                                <security:authorize ifAnyGranted="NHANVIEN">
                                    <li id="tdcg2014NhapKhoTab"><a href="<c:url value="${prefix}/nhaphang.html" />">1. Nhập Kho</a></li>
                                </security:authorize>
                                <li id="tdcg2014LichSuNhapKhoTab"><a href="<c:url value="${prefix}/lichsunhapkho.html" />">2. Lịch Sử Nhập Kho</a></li>
                                <security:authorize ifAnyGranted="NHANVIEN">
                                    <li id="tdcg2014XuatKhoTab"><a href="<c:url value="${prefix}/xuatkho.html" />">3. Xuất Kho</a></li>
                                </security:authorize>
                                <li id="tdcg2014LichSuXuatKhoTab"><a href="<c:url value="${prefix}/lichsuxuatkho.html" />">4. Lịch Sử Xuất Kho</a></li>
                            </ul>
                        </li>
                    </security:authorize>
                </div>
                <div class="row">
                    <security:authorize ifAnyGranted="ADMIN,CHINHANH">
                        <li class="mega-unit mega-hdr last"><a href="#" class="mega-hdr-a">Báo Cáo ĐL/ĐBH</a>
                            <ul>
                                <li id="tdcg2014BCChiTietHangMucPSCTab" class="nav-parent menu_level_1"><a href=""><span>1. B/c Chi Tiết Hạng Mục PSC</span></a>
                                    <ul class="children " style="display: none;">
                                        <li class="dropdown">
                                            <a href="#" data-toggle="dropdown" role="button" id="drop1" class="dropdown-toggle">1.1 B/c Thông Tin Thị Trường<b class="caret"></b></a>
                                            <ul aria-labelledby="drop3" role="menu" class="dropdown-menu" id="menu1">
                                                <li id="bcThongThiThiTruongTheoDaiLyTab" role="presentation"><a href="<c:url value="${prefix}/baocaothongtinthitruongtheodaily.html" />" tabindex="-1" role="menuitem">1.1-1 B/c Theo ĐBH</a></li>
                                                <li id="bcThongThiThiTruongTheoQuanHuyenTab" role="presentation"><a href="<c:url value="${prefix}/baocaothongtinthitruongtheoquanhuyen.html" />" tabindex="-1" role="menuitem">1.1-2 B/c Tổng Hợp Theo Q/H</a></li>
                                                <li id="bcThongThiThiTruongTheoChiNhanhTab" role="presentation"><a href="<c:url value="${prefix}/baocaothongtinthitruongtheochinhanh.html" />" tabindex="-1" role="menuitem">1.1-3 B/c Tổng Hợp Theo CN</a></li>
                                            </ul>
                                        </li>
                                        <li><a href="<c:url value="${prefix}/baocaochitiethangmucpsc_muaBTGTuMobifone_theodaily.html" />" class="parent"><span>1.2 B/c Mua BTG Từ MobiFone</span></a></li>
                                        <li><a href="<c:url value="${prefix}/baocaochitiethangmucpsc_mua_the_tu_Mobifone_theodaily.html" />" class="parent"><span>1.3 B/c Mua Thẻ Từ MobiFone</span></a></li>
                                        <li class="dropdown">
                                            <a href="#" data-toggle="dropdown" role="button" id="drop2" class="dropdown-toggle">1.4 B/c Bán VAS<b class="caret"></b></a>
                                            <ul aria-labelledby="drop3" role="menu" class="dropdown-menu" id="menu2">
                                                <li id="bcBanVASTheoDBHTab" role="presentation"><a href="<c:url value="${prefix}/baocaochitiethangmucpsc_ban_vas_theodaily.html" />" tabindex="-1" role="menuitem">1.4-1 B/c Tổng Hợp Theo ĐBH</a></li>
                                                <li id="bcMuaTheTuMobiFoneTheoQuanHuyenTab" role="presentation"><a href="<c:url value="${prefix}/baocaochitiethangmucpsc_ban_vas_theoquanhuyen.html" />" tabindex="-1" role="menuitem">1.4-2 B/c Tổng Hợp Theo Q/H</a></li>
                                                <li id="bcMuaTheTuMobiFoneTheoChiNhanhTab" role="presentation"><a href="<c:url value="${prefix}/baocaochitiethangmucpsc_ban_vas_theochinhanh.html" />" tabindex="-1" role="menuitem">1.4-3 Báo Cáo Tổng Hợp Theo CN</a></li>
                                            </ul>
                                        </li>
                                        <li class="dropdown">
                                            <a href="#" data-toggle="dropdown" role="button" id="drop3" class="dropdown-toggle">1.5 B/c Giới Thiệu KH Tham Gia CTKM<b class="caret"></b></a>
                                            <ul aria-labelledby="drop3" role="menu" class="dropdown-menu" id="menu3">
                                                <li id="bcGioiThieuKHThamGiaCTKMTheoDBHTab" role="presentation"><a href="<c:url value="${prefix}/baocaochitiethangmucpsc_gioi_thieu_kh_tham_gia_ctkm_theodaily.html" />" tabindex="-1" role="menuitem">1.5-1 B/c Tổng Hợp Theo ĐBH</a></li>
                                                <li id="bcGioiThieuKHThamGiaCTKMQuanHuyenTab" role="presentation"><a href="<c:url value="${prefix}/baocaochitiethangmucpsc_gioi_thieu_kh_tham_gia_ctkm_theoquanhuyen.html" />" tabindex="-1" role="menuitem">1.5-2 B/c Tổng Hợp Theo Q/H</a></li>
                                                <li id="bcGioiThieuKHThamGiaCTKMTheoChiNhanhTab" role="presentation"><a href="<c:url value="${prefix}/baocaochitiethangmucpsc_gioi_thieu_kh_tham_gia_ctkm_theochinhanh.html" />" tabindex="-1" role="menuitem">1.5-3 B/c Tổng Hợp Theo CN</a></li>
                                            </ul>
                                        </li>
                                        <li class="dropdown">
                                            <a href="#" data-toggle="dropdown" role="button" id="drop4" class="dropdown-toggle">1.6 B/c Bán Trực Tiếp BTG + PSC<b class="caret"></b></a>
                                            <ul aria-labelledby="drop1" role="menu" class="dropdown-menu" id="menu4">
                                                <li id="bcThongThiThiTruongMenu" role="presentation">
                                                    <div id="menu">
                                                        <ul class="menu">
                                                            <li id="bcBanTrucTiepBTGAndPSCTheoDaiLyTab" role="presentation"><a href="<c:url value="${prefix}/baocaochitiethangmucpsc_ban_truc_tiep_btg_va_psc_theodaily.html" />" tabindex="-1" role="menuitem">1.6-1 B/c Tổng Hợp Theo ĐBH</a></li>
                                                            <li id="bcBanTrucTiepBTGAndPSCTheoQuanHuyenTab" role="presentation"><a href="<c:url value="${prefix}/baocaochitiethangmucpsc_ban_truc_tiep_btg_va_psc_theoquanhuyen.html" />" tabindex="-1" role="menuitem">1.6-2 B/c Tổng Hợp Theo Q/H</a></li>
                                                            <li id="bcBanTrucTiepBTGAndPSCTheoChiNhanhTab" role="presentation"><a href="<c:url value="${prefix}/baocaochitiethangmucpsc_ban_truc_tiep_btg_va_psc_theochinhanh.html" />" tabindex="-1" role="menuitem">1.6-3 B/c Tổng Hợp Theo CN</a></li>
                                                        </ul>
                                                    </div>
                                                </li>
                                            </ul>
                                        </li>
                                    </ul>
                                </li>
                                <li id="tdcg2014ReportTinNhanTab" class="dropdown">
                                    <a href="#" data-toggle="dropdown" role="button" id="drop8" class="dropdown-toggle">2. B/c Tin Nhắn.<b class="caret"></b></a>
                                    <ul aria-labelledby="drop3" role="menu" class="dropdown-menu" id="menu8">
                                        <li id="bcTinNhanKppTheoDaiLyTab" role="presentation"><a href="<c:url value="${prefix}/baocaotinnhan_kpp_theodaily.html" />" tabindex="-1" role="menuitem">2.1 B/c Theo Mã ĐBH</a></li>
                                        <li id="bcTinNhanKppTheoQuanHuyenTab" role="presentation"><a href="<c:url value="${prefix}/baocaotinnhan_kpp_theoquanhuyen.html" />" tabindex="-1" role="menuitem">2.2 B/c Theo Q/H</a></li>
                                        <li id="bcTinNhanKppTheoChiNhanhTab" role="presentation"><a href="<c:url value="${prefix}/baocaotinnhan_kpp_theochinhanh.html" />" tabindex="-1" role="menuitem">2.3 B/c Theo CN</a></li>
                                    </ul>
                                </li>
                                <li id="tdcg2014ReportCanhBaoTab" class="dropdown">
                                    <a href="#" data-toggle="dropdown" role="button" id="drop9" class="dropdown-toggle">3. B/c Cảnh Báo.<b class="caret"></b></a>
                                    <ul aria-labelledby="drop3" role="menu" class="dropdown-menu" id="menu9">
                                        <li id="bcCanhBaoTheoDaiLyTab" role="presentation"><a href="<c:url value="${prefix}/baocaocanhbao_kpp_theodaily.html" />" tabindex="-1" role="menuitem">3.1 B/c Theo Mã ĐBH</a></li>
                                        <li id="bcCanhBaoTheoQuanHuyenTab" role="presentation"><a href="<c:url value="${prefix}/baocaocanhbao_kpp_theoquanhuyen.html" />" tabindex="-1" role="menuitem">3.2 B/c Theo Q/H</a></li>
                                        <li id="bcCanhBaoTheoChiNhanhTab" role="presentation"><a href="<c:url value="${prefix}/baocaocanhbao_kpp_theochinhanh.html" />" tabindex="-1" role="menuitem">3.3 B/c Theo CN</a></li>
                                    </ul>
                                </li>
                                <li id="tdcg2014ReportTienThuongTab" class="dropdown">
                                    <a href="#" data-toggle="dropdown" role="button" id="drop5" class="dropdown-toggle">4. B/c Tiền Thưởng Mã Thưởng<b class="caret"></b></a>
                                    <ul aria-labelledby="drop2" role="menu" class="dropdown-menu" id="menu5">
                                        <li id="bcTienThuongMaDuThuongTheoDaiLyTab" role="presentation"><a href="<c:url value="${prefix}/baocaotienthuongmaduthuongtheodaily.html" />" tabindex="-1" role="menuitem">4.1 B/c Theo ĐBH</a></li>
                                        <li id="bcTienThuongMaDuThuongTheoQuanHuyenTab" role="presentation"><a href="<c:url value="${prefix}/baocaotienthuongmaduthuongtheoquanhuyen.html" />" tabindex="-1" role="menuitem">4.2 B/c Tổng Hợp Theo Q/H</a></li>
                                        <li id="bcTienThuongMaDuThuongTheoChiNhanhTab" role="presentation"><a href="<c:url value="${prefix}/baocaotienthuongmaduthuongtheochinhanh.html" />" tabindex="-1" role="menuitem">4.3 B/c Tổng Hợp Theo CN</a></li>
                                    </ul>
                                </li>
                                <li id="tdcg2014ReportHangHoaTab" class="dropdown">
                                    <a href="#" data-toggle="dropdown" role="button" id="drop6" class="dropdown-toggle">5. B/c Hàng Hoá<b class="caret"></b></a>
                                    <ul aria-labelledby="drop3" role="menu" class="dropdown-menu" id="menu6">
                                        <li id="bcHangHoaTheoDaiLyTab" role="presentation"><a href="<c:url value="${prefix}/baocaohanghoatheodaily.html" />" tabindex="-1" role="menuitem">5.1 B/c Theo Mã ĐBH</a></li>
                                        <li id="bcHangHoaTheoQuanHuyenTab" role="presentation"><a href="<c:url value="${prefix}/baocaohanghoatheoquanhuyen.html" />" tabindex="-1" role="menuitem">5.2 B/c Tổng Hợp Theo Q/H</a></li>
                                        <li id="bcHangHoaTheoChiNhanhTab" role="presentation"><a href="<c:url value="${prefix}/baocaohanghoatheochinhanh.html" />" tabindex="-1" role="menuitem">5.3 B/c Tổng Hợp Theo CN</a></li>
                                    </ul>
                                </li>
                                <li id="tdcg2014ReportKetQuaCTKPPTab" class="dropdown">
                                    <a href="#" data-toggle="dropdown" role="button" id="drop7" class="dropdown-toggle">6. B/c Kết Quả Chương Trình KPP.<b class="caret"></b></a>
                                    <ul aria-labelledby="drop3" role="menu" class="dropdown-menu" id="menu7">
                                        <li id="bcKetQuaChuongTrinhKppTheoDaiLyTab" role="presentation"><a href="<c:url value="${prefix}/baocaotonghopketquachuongtrinh_kpp_theodaily.html" />" tabindex="-1" role="menuitem">6.1 B/c Theo Mã ĐBH</a></li>
                                        <li id="bcKetQuaChuongTrinhKppTheoQuanHuyenTab" role="presentation"><a href="<c:url value="${prefix}/baocaotonghopketquachuongtrinh_kpp_theoquanhuyen.html" />" tabindex="-1" role="menuitem">6.2 B/c Theo Q/H</a></li>
                                        <li id="bcKetQuaChuongTrinhKppTheoChiNhanhTab" role="presentation"><a href="<c:url value="${prefix}/baocaotonghopketquachuongtrinh_kpp_theochinhanh.html" />" tabindex="-1" role="menuitem">6.3 B/c Theo CN</a></li>
                                    </ul>
                                </li>
                                <li id="tdcg2014ReportDanhSachMaThuongTab"><a href="<c:url value="${prefix}/baocaodanhsachmathuong.html" />">7. B/c Danh Sách Mã Thưởng</a></li>
                                <li id="tdcg2014ReportHangMucPSTab"><a href="<c:url value="${prefix}/baocaotheohangmucphatsinh.html" />">8. B/c Theo Hạng Mục PS</a></li>
                            </ul>
                        </li>
                    </security:authorize>
                </div>
                <div class="row last">
                    <security:authorize ifAnyGranted="TONGDAI,ADMIN,CHINHANH,NHANVIEN">
                        <li class="mega-unit mega-hdr"><a href="#" class="mega-hdr-a">Báo Cáo KHCN</a>
                            <ul>
                                <li id="tdcg2014ReportTheoKHCNTab"><a href="<c:url value="${prefix}/baocaotheokhcn.html" />">1. B/c Theo KHCN</a></li>
                                <li id="tdcg2014ReportQuanLyPhieuKMTab"><a href="<c:url value="${prefix}/quanlyphieukhuyenmai.html" />">2. B/c Quản Lý Phiếu KM</a></li>
                                <security:authorize ifAnyGranted="ADMIN,CHINHANH">
                                    <li id="tdcg2014ReportQuanLyTinNhanTab"><a href="<c:url value="${prefix}/bcquanlytinnhan.html" />">3. B/c Quản Lý Tin Nhắn</a></li>
                                    <li id="tdcg2014ReportDanhGiaKetQuaThucHienCTTab"><a href="<c:url value="${prefix}/baocaodanhgiaketquathuchienct.html" />">4. B/c Đánh Giá KQTH Chương Trình</a></li>
                                </security:authorize>
                            </ul>
                        </li>
                    </security:authorize>
                </div>
            </security:authorize>
        </ul></div>
    </li>
</ul>
<ul class="mega-menu right popup_menu">
    <li class="dc-mega-li"><a href="#" class="dc-mega">Thuê Bao PTM 2015<span class="dc-mega-icon"></span></a>
        <div class="sub-container mega" style="margin-top: -100px; z-index: 1000; width: 770px; display: none; left: 209px;">
            <security:authorize ifAnyGranted="ADMIN,TONGDAI,CHINHANH,BAOCAO">
                <ul class="sub">
                    <security:authorize ifAnyGranted="ADMIN, CHINHANH">
                        <div class="row first">
                            <li class="mega-unit mega-hdr"><a href="#" class="mega-hdr-a">Chi Khuyến Khích</a>
                                <ul>
                                    <li id="tbptm2015PaymentChiKhuyenKhichTab"><a href="<c:url value="${prefix}/chitradiembanhang.html" />">Chi Khuyến Khích ĐBH</a></li>
                                    <li id="tbptm2015LichSuChiKhuyenKhichTab"><a href="<c:url value="${prefix}/thuebaophattrienmoi/lichsuchitra.html" />">Lịch Sử Chi Khuyến Khích ĐBH</a></li>
                                </ul>
                            </li>
                        </div>
                        <div class="row">

                        </div>
                    </security:authorize>
                    <security:authorize ifAnyGranted="ADMIN,CHINHANH,BAOCAO">
                        <div class="row last" style="min-height: 200px;">
                            <li class="mega-unit mega-hdr"><a href="#" class="mega-hdr-a">Báo Cáo</a>
                                <ul>
                                    <li id="tbptm2015ReportTinNhanDangKyGoiDBHTab"><a href="<c:url value="${prefix}/thuebaophattrienmoi/baocaotinnhandangkygoidbh.html" />">1. B/c Tin Nhắn Đăng Ký Gói ĐBH</a></li>
                                    <li id="tbptm2015ReportThongKeThueBaoThamGiaGoiCuocTab"><a href="<c:url value="${prefix}/thuebaophattrienmoi/baocaothongkethuebao_thamgiagoicuoc_theodaily.html" />">2. B/c Thống Kê TB Tham Gia Gói Cước</a></li>
                                    <li id="tbptm2015ReportTongHopKQCTTab" class="dropdown">
                                        <a href="#" data-toggle="dropdown" role="button" id="drop10" class="dropdown-toggle">3. B/c Tổng Hợp KQCT.<b class="caret"></b></a>
                                        <ul aria-labelledby="drop3" role="menu" class="dropdown-menu" id="menu10">
                                            <li id="bcTongHopKQCTtheoDailyTab" role="presentation"><a href="<c:url value="${prefix}/thuebaophattrienmoi/baocaotonghop_ketquachuongtrinh_theodaily.html" />" tabindex="-1" role="menuitem">3.1 B/c Theo Mã ĐBH</a></li>
                                            <li id="bcTongHopKQCTTheoQuanHuyenTab" role="presentation"><a href="<c:url value="${prefix}/thuebaophattrienmoi/baocaotonghop_ketquachuongtrinh_theoquanhuyen.html" />" tabindex="-1" role="menuitem">3.2 B/c Theo Q/H</a></li>
                                            <li id="bcTongHopKQCTTheoChiNhanhTab" role="presentation"><a href="<c:url value="${prefix}/thuebaophattrienmoi/baocaotonghop_ketquachuongtrinh_theochinhanh.html" />" tabindex="-1" role="menuitem">3.3 B/c Theo CN</a></li>
                                        </ul>
                                    </li>
                                    <li id="tbptm2015ReportChiTietTheoTenGoiAndMaGoiTab" class="dropdown">
                                        <a href="#" data-toggle="dropdown" role="button" id="drop11" class="dropdown-toggle">4. B/c Chi Tiết Theo Tên Gói/ Mã Gói<b class="caret"></b></a>
                                        <ul aria-labelledby="drop3" role="menu" class="dropdown-menu" id="menu11">
                                            <li id="bcTheoMaGoiVaTenGoiTheoDailyTab" role="presentation"><a href="<c:url value="${prefix}/thuebaophattrienmoi/baocaochitietgoikm_theomagoivatengoi_theodaily.html" />" tabindex="-1" role="menuitem">4.1 B/c Theo ĐBH</a></li>
                                            <li id="bcTheoMaGoiVaTenGoiQuanHuyenTab" role="presentation"><a href="<c:url value="${prefix}/thuebaophattrienmoi/baocaochitietgoikm_theomagoivatengoi_theoquanhuyen.html" />" tabindex="-1" role="menuitem">4.2 B/c Theo Q/H</a></li>
                                            <li id="bcTheoMaGoiVaTenGoiChiNhanhTab" role="presentation"><a href="<c:url value="${prefix}/thuebaophattrienmoi/baocaochitietgoikm_theomagoivatengoi_theochinhanh.html" />" tabindex="-1" role="menuitem">4.3 B/c Theo CN</a></li>
                                        </ul>
                                    </li>
                                    <li id="tbptm2015ReportTinNhanTab" class="dropdown">
                                        <a href="#" data-toggle="dropdown" role="button" id="drop12" class="dropdown-toggle">5. B/c Tin Nhắn<b class="caret"></b></a>
                                        <ul aria-labelledby="drop3" role="menu" class="dropdown-menu" id="menu12">
                                            <li id="bcTinNhantheoDailyTab" role="presentation"><a href="<c:url value="${prefix}/thuebaophattrienmoi/baocaotinnhan_theodaily.html" />" tabindex="-1" role="menuitem">5.1 B/c Theo mã ĐBH</a></li>
                                            <li id="bcTinNhanTheoQuanHuyenTab" role="presentation"><a href="<c:url value="${prefix}/thuebaophattrienmoi/baocaotinnhan_theoquanhuyen.html" />" tabindex="-1" role="menuitem">5.2 B/c Theo Q/H</a></li>
                                            <li id="bcTinNhanTheoChiNhanhTab" role="presentation"><a href="<c:url value="${prefix}/thuebaophattrienmoi/baocaotinnhan_theochinhanh.html" />" tabindex="-1" role="menuitem">5.3 B/c Theo CN</a></li>
                                        </ul>
                                    </li>
                                    <li id="tbptm2015ReportChiTraKhuyenKhichDBHTab"><a href="<c:url value="${prefix}/thuebaophattrienmoi/baocaochitrakhuyenkhich_cho_dbh.html" />">6. B/c Chi Trả Khuyến Khích Cho ĐBH</a></li>
                                    <li id="tbptm2015ReportEZNhanTinThamGiaCTTab"><a href="<c:url value="${prefix}/thuebaophattrienmoi/baocaoeznhantinthamgiachuongtrinh.html" />">7. B/c EZ Nhắn Tin Tham Gia CT</a></li>
                                    <li id="tbptm2015ReportPhatTrienGoiTab"><a href="<c:url value="${prefix}/thuebaophattrienmoi/BaoCaoPhatTrienGoi.html" />">8. B/c Phát Triển Gói</a></li>
                                </ul>
                            </li>
                        </div>
                    </security:authorize>
                </ul>
            </security:authorize>
        </div>
    </li>
</ul>
<%--Q-Teean and Student--%>
<ul class="mega-menu right popup_menu">
    <li class="dc-mega-li"><a href="#" class="dc-mega">Q-Teen & Q-Student 2015<span class="dc-mega-icon"></span></a>
        <div class="sub-container mega" style="margin-top: -100px; z-index: 1000; width: 770px; display: none; left: 209px;">
            <ul class="sub">
                <div class="row first">
                    <security:authorize ifAnyGranted="TONGDAI,ADMIN,CHINHANH">
                        <li class="mega-unit mega-hdr"><a href="#" class="mega-hdr-a">Tra Cứu Thông Tin Thuê Bao</a>
                            <ul>
                                <li id="qStudent2015TraCuuThongTinThueBao"><a href="<c:url value="${prefix}/qstudent/tracuuthongtinthuebao.html" />">1. Tra Cứu Thông tin thuê bao</a></li>
                            </ul>
                        </li>
                    </security:authorize>
                </div>
                <div class="row">
                    <security:authorize ifAnyGranted="NHANVIEN,TONGDAI,ADMIN,CHINHANH">
                        <li class="mega-unit mega-hdr"><a href="#" class="mega-hdr-a">Đổi Quà</a>
                            <ul>
                                <li id="qStudent2015DoiQuaTheoMaPhieu"><a href="<c:url value="${prefix}/qstudent/tracuutheomaphieu.html" />">1. Tra Cứu Theo Mã Phiếu</a></li>
                                <li id="qStudent2015DoiQuaTheoSoThueBao"><a href="<c:url value="${prefix}/qstudent/tracuutheosothuebao.html" />">2. Tra Cứu Theo Thuê Bao</a></li>
                            </ul>
                        </li>
                    </security:authorize>
                </div>
                <div class="row">
                    <security:authorize ifAnyGranted="NHANVIEN,TONGDAI,ADMIN,CHINHANH">
                        <li class="mega-unit mega-hdr last"><a href="#" class="mega-hdr-a">Tra Cứu Cửa Hàng Khác</a>
                            <ul>
                                <li id="qStudent2015KhoHangCuaHangKhac"><a href="<c:url value="${prefix}/qstudent/khohangcuahangkhac.html" />">1. Tra Cứu Kho</a></li>
                            </ul>
                        </li>
                    </security:authorize>
                </div>
               <%-- <div class="row last">
                    <security:authorize ifAnyGranted="NHANVIEN,CHINHANH,ADMIN">
                    <li class="mega-unit mega-hdr"><a href="#" class="mega-hdr-a">Kho Vận</a>
                        <ul>
                            <security:authorize ifAnyGranted="NHANVIEN">
                                <li><a href="<c:url value="${prefix}/nhaphang.html" />">1. Nhập Kho</a></li>
                            </security:authorize>
                            <li><a href="<c:url value="${prefix}/qstudent/lichsunhapkho.html" />">2. Lịch Sử Nhập Kho</a></li>
                            <security:authorize ifAnyGranted="NHANVIEN">
                                <li><a href="<c:url value="${prefix}/xuatkho.html" />">3. Xuất Kho</a></li>
                            </security:authorize>
                            <li><a href="<c:url value="${prefix}/qstudent/lichsuxuatkho.html" />">4. Lịch Sử Xuất Kho</a></li>
                        </ul>
                    </li>
                    </security:authorize>
                </div>--%>
                <div class="row last">
                    <security:authorize ifAnyGranted="NHANVIEN,TONGDAI,ADMIN,CHINHANH">
                        <li class="mega-unit mega-hdr"><a href="#" class="mega-hdr-a">Báo Cáo KHCN</a>
                            <ul>
                                <li id="qStudent2015ReportTheoKHCNTab"><a href="<c:url value="${prefix}/qstudent/baocaotheokhcn.html" />">1. B/c Theo KHCN</a></li>
                                <li id="qStudent2015ReportQuanLyPhieuKMTab"><a href="<c:url value="${prefix}/qstudent/quanlyphieukhuyenmai.html" />">2. B/c Quản Lý Phiếu KM</a></li>
                                <security:authorize ifAnyGranted="ADMIN,CHINHANH">
                                    <li id="qStudent2015ReportQuanLyTinNhanTab"><a href="<c:url value="${prefix}/qstudent/bcquanlytinnhan.html" />">3. B/c Quản Lý Tin Nhắn</a></li>
                                    <li id="qStudent2015ReportDanhGiaKetQuaThucHienCTTab"><a href="<c:url value="${prefix}/qstudent/baocaodanhgiaketquathuchienct.html" />">4. B/c Đánh Giá KQTH Chương Trình</a></li>
                                </security:authorize>
                                <li id="qStudent2015BaoCaoSoDiemTab"><a href="<c:url value="${prefix}/qstudent/baocaosodiemchochuongtrinh.html" />">5. B/c Số Điểm Cho Chương Trình</a></li>
                            </ul>
                        </li>
                    </security:authorize>
                </div>
            </ul>
        </div>
    </li>
</ul>

</div><!-- leftpanelinner -->