package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/30/14
 * Time: 4:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO implements Serializable{
    private static final long serialVersionUID = -6275546617707418475L;

    private Double tieuChi1_tb_moi;
    private Double tieuChi1_tb_cu;

    private Double tieuChi2_tb_moi;
    private Double tieuChi2_tb_cu;

    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachNgayRender_tieuChi3And4 = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachNgayRender_tieuChi5And6 = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();

    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi3_summary = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi3_loai_t = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi3_loai_s = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi3_loai_d = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi3_loai_r = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi3_loai_v = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi3_loai_k = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private Double total_tieuChi3_truoc = new Double(0);
    private Double total_tieuChi3_sau = new Double(0);

    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi4_summary = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi4_loai_t = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi4_loai_s = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi4_loai_d = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi4_loai_r = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi4_loai_v = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi4_loai_k = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private Double total_tieuChi4_truoc = new Double(0);
    private Double total_tieuChi4_sau = new Double(0);

    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi5_summary = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi5_loai_t = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi5_loai_s = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi5_loai_d = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi5_loai_r = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi5_loai_v = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi5_loai_k = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private Double total_tieuChi5_truoc = new Double(0);
    private Double total_tieuChi5_sau = new Double(0);

    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi6_summary = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi6_loai_t = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi6_loai_s = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi6_loai_d = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi6_loai_r = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi6_loai_v = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi6_loai_k = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
    private Double total_tieuChi6_truoc = new Double(0);
    private Double total_tieuChi6_sau = new Double(0);

    private Double tieuChi7_sl_tb_ddk_cap_ma_moi = new Double(0);       // Số lượng thue bao du dk cap ma thuong moi
    private Double tieuChi7_sl_tb_ddk_cap_ma_cu = new Double(0);       // Số lượng thue bao du dk cap ma thuong cu
    private Double tieuChi7_1_ma_moi = new Double(0);
    private Double tieuChi7_1_ma_cu = new Double(0);
    private Double tieuChi7_2_ma_moi = new Double(0);
    private Double tieuChi7_2_ma_cu = new Double(0);
    private Double tieuChi7_3_ma_moi = new Double(0);
    private Double tieuChi7_3_ma_cu = new Double(0);
    private Double tieuChi7_tren_3_ma_moi = new Double(0);
    private Double tieuChi7_tren_3_ma_cu = new Double(0);

    private Double tieuChi8_sl_tb_da_doi_thuong_moi = new Double(0);       // So luong thue bao da doi thuong moi.
    private Double tieuChi8_sl_tb_da_doi_thuong_cu = new Double(0);       // So luong thue bao da doi thuong cu.
    private Double tieuChi8_1_phieu_moi = new Double(0);
    private Double tieuChi8_1_phieu_cu = new Double(0);
    private Double tieuChi8_2_phieu_moi = new Double(0);
    private Double tieuChi8_2_phieu_cu = new Double(0);
    private Double tieuChi8_3_phieu_moi = new Double(0);
    private Double tieuChi8_3_phieu_cu = new Double(0);
    private Double tieuChi8_tren_3_phieu_moi = new Double(0);
    private Double tieuChi8_tren_3_phieu_cu = new Double(0);

    private Double tieuChi9_sl_ma_doi_da_ps_moi = new Double(0);       // So luong ma doi da phat sinh moi.
    private Double tieuChi9_sl_ma_doi_da_ps_cu = new Double(0);       // So luong ma doi da phat sinh cu.

    private Double tieuChi10_sl_phieu_qua_tang_da_doi_moi = new Double(0);       // So luong phieu qua tang da doi moi.
    private Double tieuChi10_sl_phieu_qua_tang_da_doi_cu = new Double(0);       // So luong phieu qua tang da doi cu.

    public Double getTieuChi1_tb_moi() {
        return tieuChi1_tb_moi;
    }

    public void setTieuChi1_tb_moi(Double tieuChi1_tb_moi) {
        this.tieuChi1_tb_moi = tieuChi1_tb_moi;
    }

    public Double getTieuChi1_tb_cu() {
        return tieuChi1_tb_cu;
    }

    public void setTieuChi1_tb_cu(Double tieuChi1_tb_cu) {
        this.tieuChi1_tb_cu = tieuChi1_tb_cu;
    }

    public Double getTieuChi2_tb_moi() {
        return tieuChi2_tb_moi;
    }

    public void setTieuChi2_tb_moi(Double tieuChi2_tb_moi) {
        this.tieuChi2_tb_moi = tieuChi2_tb_moi;
    }

    public Double getTieuChi2_tb_cu() {
        return tieuChi2_tb_cu;
    }

    public void setTieuChi2_tb_cu(Double tieuChi2_tb_cu) {
        this.tieuChi2_tb_cu = tieuChi2_tb_cu;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi3_summary() {
        return tieuChi3_summary;
    }

    public void setTieuChi3_summary(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi3_summary) {
        this.tieuChi3_summary = tieuChi3_summary;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi3_loai_t() {
        return tieuChi3_loai_t;
    }

    public void setTieuChi3_loai_t(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi3_loai_t) {
        this.tieuChi3_loai_t = tieuChi3_loai_t;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi3_loai_s() {
        return tieuChi3_loai_s;
    }

    public void setTieuChi3_loai_s(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi3_loai_s) {
        this.tieuChi3_loai_s = tieuChi3_loai_s;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi3_loai_d() {
        return tieuChi3_loai_d;
    }

    public void setTieuChi3_loai_d(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi3_loai_d) {
        this.tieuChi3_loai_d = tieuChi3_loai_d;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi3_loai_r() {
        return tieuChi3_loai_r;
    }

    public void setTieuChi3_loai_r(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi3_loai_r) {
        this.tieuChi3_loai_r = tieuChi3_loai_r;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi3_loai_v() {
        return tieuChi3_loai_v;
    }

    public void setTieuChi3_loai_v(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi3_loai_v) {
        this.tieuChi3_loai_v = tieuChi3_loai_v;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi4_loai_v() {
        return tieuChi4_loai_v;
    }

    public void setTieuChi4_loai_v(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi4_loai_v) {
        this.tieuChi4_loai_v = tieuChi4_loai_v;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi5_loai_v() {
        return tieuChi5_loai_v;
    }

    public void setTieuChi5_loai_v(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi5_loai_v) {
        this.tieuChi5_loai_v = tieuChi5_loai_v;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi6_loai_v() {
        return tieuChi6_loai_v;
    }

    public void setTieuChi6_loai_v(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi6_loai_v) {
        this.tieuChi6_loai_v = tieuChi6_loai_v;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi3_loai_k() {
        return tieuChi3_loai_k;
    }

    public void setTieuChi3_loai_k(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi3_loai_k) {
        this.tieuChi3_loai_k = tieuChi3_loai_k;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi4_summary() {
        return tieuChi4_summary;
    }

    public void setTieuChi4_summary(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi4_summary) {
        this.tieuChi4_summary = tieuChi4_summary;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi4_loai_t() {
        return tieuChi4_loai_t;
    }

    public void setTieuChi4_loai_t(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi4_loai_t) {
        this.tieuChi4_loai_t = tieuChi4_loai_t;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi4_loai_s() {
        return tieuChi4_loai_s;
    }

    public void setTieuChi4_loai_s(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi4_loai_s) {
        this.tieuChi4_loai_s = tieuChi4_loai_s;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi4_loai_d() {
        return tieuChi4_loai_d;
    }

    public void setTieuChi4_loai_d(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi4_loai_d) {
        this.tieuChi4_loai_d = tieuChi4_loai_d;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi4_loai_r() {
        return tieuChi4_loai_r;
    }

    public void setTieuChi4_loai_r(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi4_loai_r) {
        this.tieuChi4_loai_r = tieuChi4_loai_r;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi4_loai_k() {
        return tieuChi4_loai_k;
    }

    public void setTieuChi4_loai_k(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi4_loai_k) {
        this.tieuChi4_loai_k = tieuChi4_loai_k;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi5_summary() {
        return tieuChi5_summary;
    }

    public void setTieuChi5_summary(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi5_summary) {
        this.tieuChi5_summary = tieuChi5_summary;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi5_loai_t() {
        return tieuChi5_loai_t;
    }

    public void setTieuChi5_loai_t(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi5_loai_t) {
        this.tieuChi5_loai_t = tieuChi5_loai_t;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi5_loai_s() {
        return tieuChi5_loai_s;
    }

    public void setTieuChi5_loai_s(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi5_loai_s) {
        this.tieuChi5_loai_s = tieuChi5_loai_s;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi5_loai_d() {
        return tieuChi5_loai_d;
    }

    public void setTieuChi5_loai_d(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi5_loai_d) {
        this.tieuChi5_loai_d = tieuChi5_loai_d;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi5_loai_r() {
        return tieuChi5_loai_r;
    }

    public void setTieuChi5_loai_r(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi5_loai_r) {
        this.tieuChi5_loai_r = tieuChi5_loai_r;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi5_loai_k() {
        return tieuChi5_loai_k;
    }

    public void setTieuChi5_loai_k(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi5_loai_k) {
        this.tieuChi5_loai_k = tieuChi5_loai_k;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi6_summary() {
        return tieuChi6_summary;
    }

    public void setTieuChi6_summary(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi6_summary) {
        this.tieuChi6_summary = tieuChi6_summary;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi6_loai_t() {
        return tieuChi6_loai_t;
    }

    public void setTieuChi6_loai_t(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi6_loai_t) {
        this.tieuChi6_loai_t = tieuChi6_loai_t;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi6_loai_s() {
        return tieuChi6_loai_s;
    }

    public void setTieuChi6_loai_s(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi6_loai_s) {
        this.tieuChi6_loai_s = tieuChi6_loai_s;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi6_loai_d() {
        return tieuChi6_loai_d;
    }

    public void setTieuChi6_loai_d(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi6_loai_d) {
        this.tieuChi6_loai_d = tieuChi6_loai_d;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi6_loai_r() {
        return tieuChi6_loai_r;
    }

    public void setTieuChi6_loai_r(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi6_loai_r) {
        this.tieuChi6_loai_r = tieuChi6_loai_r;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getTieuChi6_loai_k() {
        return tieuChi6_loai_k;
    }

    public void setTieuChi6_loai_k(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> tieuChi6_loai_k) {
        this.tieuChi6_loai_k = tieuChi6_loai_k;
    }

    public Double getTieuChi7_sl_tb_ddk_cap_ma_moi() {
        return tieuChi7_sl_tb_ddk_cap_ma_moi;
    }

    public void setTieuChi7_sl_tb_ddk_cap_ma_moi(Double tieuChi7_sl_tb_ddk_cap_ma_moi) {
        this.tieuChi7_sl_tb_ddk_cap_ma_moi = tieuChi7_sl_tb_ddk_cap_ma_moi;
    }

    public Double getTieuChi7_sl_tb_ddk_cap_ma_cu() {
        return tieuChi7_sl_tb_ddk_cap_ma_cu;
    }

    public void setTieuChi7_sl_tb_ddk_cap_ma_cu(Double tieuChi7_sl_tb_ddk_cap_ma_cu) {
        this.tieuChi7_sl_tb_ddk_cap_ma_cu = tieuChi7_sl_tb_ddk_cap_ma_cu;
    }

    public Double getTieuChi7_1_ma_moi() {
        return tieuChi7_1_ma_moi;
    }

    public void setTieuChi7_1_ma_moi(Double tieuChi7_1_ma_moi) {
        this.tieuChi7_1_ma_moi = tieuChi7_1_ma_moi;
    }

    public Double getTieuChi7_1_ma_cu() {
        return tieuChi7_1_ma_cu;
    }

    public void setTieuChi7_1_ma_cu(Double tieuChi7_1_ma_cu) {
        this.tieuChi7_1_ma_cu = tieuChi7_1_ma_cu;
    }

    public Double getTieuChi7_2_ma_moi() {
        return tieuChi7_2_ma_moi;
    }

    public void setTieuChi7_2_ma_moi(Double tieuChi7_2_ma_moi) {
        this.tieuChi7_2_ma_moi = tieuChi7_2_ma_moi;
    }

    public Double getTieuChi7_2_ma_cu() {
        return tieuChi7_2_ma_cu;
    }

    public void setTieuChi7_2_ma_cu(Double tieuChi7_2_ma_cu) {
        this.tieuChi7_2_ma_cu = tieuChi7_2_ma_cu;
    }

    public Double getTieuChi7_3_ma_moi() {
        return tieuChi7_3_ma_moi;
    }

    public void setTieuChi7_3_ma_moi(Double tieuChi7_3_ma_moi) {
        this.tieuChi7_3_ma_moi = tieuChi7_3_ma_moi;
    }

    public Double getTieuChi7_3_ma_cu() {
        return tieuChi7_3_ma_cu;
    }

    public void setTieuChi7_3_ma_cu(Double tieuChi7_3_ma_cu) {
        this.tieuChi7_3_ma_cu = tieuChi7_3_ma_cu;
    }

    public Double getTieuChi7_tren_3_ma_moi() {
        return tieuChi7_tren_3_ma_moi;
    }

    public void setTieuChi7_tren_3_ma_moi(Double tieuChi7_tren_3_ma_moi) {
        this.tieuChi7_tren_3_ma_moi = tieuChi7_tren_3_ma_moi;
    }

    public Double getTieuChi7_tren_3_ma_cu() {
        return tieuChi7_tren_3_ma_cu;
    }

    public void setTieuChi7_tren_3_ma_cu(Double tieuChi7_tren_3_ma_cu) {
        this.tieuChi7_tren_3_ma_cu = tieuChi7_tren_3_ma_cu;
    }

    public Double getTieuChi8_sl_tb_da_doi_thuong_moi() {
        return tieuChi8_sl_tb_da_doi_thuong_moi;
    }

    public void setTieuChi8_sl_tb_da_doi_thuong_moi(Double tieuChi8_sl_tb_da_doi_thuong_moi) {
        this.tieuChi8_sl_tb_da_doi_thuong_moi = tieuChi8_sl_tb_da_doi_thuong_moi;
    }

    public Double getTieuChi8_sl_tb_da_doi_thuong_cu() {
        return tieuChi8_sl_tb_da_doi_thuong_cu;
    }

    public void setTieuChi8_sl_tb_da_doi_thuong_cu(Double tieuChi8_sl_tb_da_doi_thuong_cu) {
        this.tieuChi8_sl_tb_da_doi_thuong_cu = tieuChi8_sl_tb_da_doi_thuong_cu;
    }

    public Double getTieuChi8_1_phieu_moi() {
        return tieuChi8_1_phieu_moi;
    }

    public void setTieuChi8_1_phieu_moi(Double tieuChi8_1_phieu_moi) {
        this.tieuChi8_1_phieu_moi = tieuChi8_1_phieu_moi;
    }

    public Double getTieuChi8_1_phieu_cu() {
        return tieuChi8_1_phieu_cu;
    }

    public void setTieuChi8_1_phieu_cu(Double tieuChi8_1_phieu_cu) {
        this.tieuChi8_1_phieu_cu = tieuChi8_1_phieu_cu;
    }

    public Double getTieuChi8_2_phieu_moi() {
        return tieuChi8_2_phieu_moi;
    }

    public void setTieuChi8_2_phieu_moi(Double tieuChi8_2_phieu_moi) {
        this.tieuChi8_2_phieu_moi = tieuChi8_2_phieu_moi;
    }

    public Double getTieuChi8_2_phieu_cu() {
        return tieuChi8_2_phieu_cu;
    }

    public void setTieuChi8_2_phieu_cu(Double tieuChi8_2_phieu_cu) {
        this.tieuChi8_2_phieu_cu = tieuChi8_2_phieu_cu;
    }

    public Double getTieuChi8_3_phieu_moi() {
        return tieuChi8_3_phieu_moi;
    }

    public void setTieuChi8_3_phieu_moi(Double tieuChi8_3_phieu_moi) {
        this.tieuChi8_3_phieu_moi = tieuChi8_3_phieu_moi;
    }

    public Double getTieuChi8_3_phieu_cu() {
        return tieuChi8_3_phieu_cu;
    }

    public void setTieuChi8_3_phieu_cu(Double tieuChi8_3_phieu_cu) {
        this.tieuChi8_3_phieu_cu = tieuChi8_3_phieu_cu;
    }

    public Double getTieuChi8_tren_3_phieu_moi() {
        return tieuChi8_tren_3_phieu_moi;
    }

    public void setTieuChi8_tren_3_phieu_moi(Double tieuChi8_tren_3_phieu_moi) {
        this.tieuChi8_tren_3_phieu_moi = tieuChi8_tren_3_phieu_moi;
    }

    public Double getTieuChi8_tren_3_phieu_cu() {
        return tieuChi8_tren_3_phieu_cu;
    }

    public void setTieuChi8_tren_3_phieu_cu(Double tieuChi8_tren_3_phieu_cu) {
        this.tieuChi8_tren_3_phieu_cu = tieuChi8_tren_3_phieu_cu;
    }

    public Double getTieuChi9_sl_ma_doi_da_ps_moi() {
        return tieuChi9_sl_ma_doi_da_ps_moi;
    }

    public void setTieuChi9_sl_ma_doi_da_ps_moi(Double tieuChi9_sl_ma_doi_da_ps_moi) {
        this.tieuChi9_sl_ma_doi_da_ps_moi = tieuChi9_sl_ma_doi_da_ps_moi;
    }

    public Double getTieuChi9_sl_ma_doi_da_ps_cu() {
        return tieuChi9_sl_ma_doi_da_ps_cu;
    }

    public void setTieuChi9_sl_ma_doi_da_ps_cu(Double tieuChi9_sl_ma_doi_da_ps_cu) {
        this.tieuChi9_sl_ma_doi_da_ps_cu = tieuChi9_sl_ma_doi_da_ps_cu;
    }

    public Double getTieuChi10_sl_phieu_qua_tang_da_doi_moi() {
        return tieuChi10_sl_phieu_qua_tang_da_doi_moi;
    }

    public void setTieuChi10_sl_phieu_qua_tang_da_doi_moi(Double tieuChi10_sl_phieu_qua_tang_da_doi_moi) {
        this.tieuChi10_sl_phieu_qua_tang_da_doi_moi = tieuChi10_sl_phieu_qua_tang_da_doi_moi;
    }

    public Double getTieuChi10_sl_phieu_qua_tang_da_doi_cu() {
        return tieuChi10_sl_phieu_qua_tang_da_doi_cu;
    }

    public void setTieuChi10_sl_phieu_qua_tang_da_doi_cu(Double tieuChi10_sl_phieu_qua_tang_da_doi_cu) {
        this.tieuChi10_sl_phieu_qua_tang_da_doi_cu = tieuChi10_sl_phieu_qua_tang_da_doi_cu;
    }

    public Double getTotal_tieuChi3_truoc() {
        return total_tieuChi3_truoc;
    }

    public void setTotal_tieuChi3_truoc(Double total_tieuChi3_truoc) {
        this.total_tieuChi3_truoc = total_tieuChi3_truoc;
    }

    public Double getTotal_tieuChi3_sau() {
        return total_tieuChi3_sau;
    }

    public void setTotal_tieuChi3_sau(Double total_tieuChi3_sau) {
        this.total_tieuChi3_sau = total_tieuChi3_sau;
    }

    public Double getTotal_tieuChi4_truoc() {
        return total_tieuChi4_truoc;
    }

    public void setTotal_tieuChi4_truoc(Double total_tieuChi4_truoc) {
        this.total_tieuChi4_truoc = total_tieuChi4_truoc;
    }

    public Double getTotal_tieuChi4_sau() {
        return total_tieuChi4_sau;
    }

    public void setTotal_tieuChi4_sau(Double total_tieuChi4_sau) {
        this.total_tieuChi4_sau = total_tieuChi4_sau;
    }

    public Double getTotal_tieuChi5_truoc() {
        return total_tieuChi5_truoc;
    }

    public void setTotal_tieuChi5_truoc(Double total_tieuChi5_truoc) {
        this.total_tieuChi5_truoc = total_tieuChi5_truoc;
    }

    public Double getTotal_tieuChi5_sau() {
        return total_tieuChi5_sau;
    }

    public void setTotal_tieuChi5_sau(Double total_tieuChi5_sau) {
        this.total_tieuChi5_sau = total_tieuChi5_sau;
    }

    public Double getTotal_tieuChi6_truoc() {
        return total_tieuChi6_truoc;
    }

    public void setTotal_tieuChi6_truoc(Double total_tieuChi6_truoc) {
        this.total_tieuChi6_truoc = total_tieuChi6_truoc;
    }

    public Double getTotal_tieuChi6_sau() {
        return total_tieuChi6_sau;
    }

    public void setTotal_tieuChi6_sau(Double total_tieuChi6_sau) {
        this.total_tieuChi6_sau = total_tieuChi6_sau;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getDanhSachNgayRender_tieuChi3And4() {
        return danhSachNgayRender_tieuChi3And4;
    }

    public void setDanhSachNgayRender_tieuChi3And4(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachNgayRender_tieuChi3And4) {
        this.danhSachNgayRender_tieuChi3And4 = danhSachNgayRender_tieuChi3And4;
    }

    public List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> getDanhSachNgayRender_tieuChi5And6() {
        return danhSachNgayRender_tieuChi5And6;
    }

    public void setDanhSachNgayRender_tieuChi5And6(List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachNgayRender_tieuChi5And6) {
        this.danhSachNgayRender_tieuChi5And6 = danhSachNgayRender_tieuChi5And6;
    }
}
