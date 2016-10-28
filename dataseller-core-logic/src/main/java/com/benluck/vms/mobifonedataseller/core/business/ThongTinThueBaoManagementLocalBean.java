package com.benluck.vms.mobifonedataseller.core.business;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/22/14
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ThongTinThueBaoManagementLocalBean {
    /**
     * Fetch for CT Tich Diem Cuoc Goi
     * @param thue_bao
     * @return
     */
    Integer getSoDiemHienTai_tdcg(String thue_bao);

    /**
     * Fetch for CT Q-Student Q-Teen
     * @param thue_bao
     * @return
     */
    Integer getSoDiemHienTai_qStudent(String thue_bao);

    /**
     * Fetch for CT Tich Diem Cuoc Goi
     * @param thue_bao
     * @return
     */
    Integer getSoPhieuDaDoi_tdcg(String thue_bao);

    /**
     * Fetch for CT Q-Student Q-Teen
     * @param thue_bao
     * @return
     */
    Integer getSoPhieuDaDoi_qStudent(String thue_bao);

    /**
     * Fetch for CT Tich Diem Cuoc Goi
     * @param thue_bao
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    Object[] traCuuThongTinThueBao_tdcg(String thue_bao, int firstItem, int maxPageItems, String sortExpression, String sortDirection);

    /**
     * Fetch for CT Q-Student Q-Teen
     * @param thue_bao
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    Object[] traCuuThongTinThueBao_qStudent(String thue_bao, int firstItem, int maxPageItems, String sortExpression, String sortDirection);

    /**
     * Fetch for CT Thue Bao Phat Trien Moi
     * @param thue_bao
     * @param sortExpression
     * @param sortDirection
     * @param firstItems
     * @param maxPageItems
     * @return
     */
    Object[] search_tbptm(String thue_bao, String sortExpression, String sortDirection, Integer firstItems, Integer maxPageItems);

}
