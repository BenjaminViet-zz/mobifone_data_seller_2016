package com.benluck.vms.mobifonedataseller.core.business;


import javax.ejb.DuplicateKeyException;
import javax.ejb.ObjectNotFoundException;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MaPhieuCTTichDiemManagementLocalBean {
    /**
     * Fetch for CT Tich Diem Cuoc Goi
     * @param maPhieu
     * @param da_doi_qua
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    public Object[] searchByMaPhieu_tdcg(String maPhieu, Integer da_doi_qua, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Fetch for CT Q-Student Q-Teen
     * @param maPhieu
     * @param da_doi_qua
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    public Object[] searchByMaPhieu_qStudent(String maPhieu, Integer da_doi_qua, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Fetch for CT Tich Diem Cuoc Goi
     * @param soThueBao
     * @param da_doi_qua
     * @param offset
     * @param limit
     * @param sortDirection
     * @param sortExpression
     * @return
     */
    Object[] searchByThueBao_tdcg(String soThueBao, Integer da_doi_qua, Integer offset, Integer limit, String sortDirection, String sortExpression);

    /**
     * Fetch for CT Q-Student Q-Teen
     * @param soThueBao
     * @param da_doi_qua
     * @param offset
     * @param limit
     * @param sortDirection
     * @param sortExpression
     * @return
     */
    Object[] searchByThueBao_qStudent(String soThueBao, Integer da_doi_qua, Integer offset, Integer limit, String sortDirection, String sortExpression);

    /**
     * Perform exchange for customer in the promotion "Tich Diem Cuoc Goi Nhan Voucher".
     * @param nvGiaoQuaId
     * @param dsMaPhieus
     * @param shopCode
     * @param shopUserName
     * @throws ObjectNotFoundException
     * @throws DuplicateKeyException
     */
    void shopUserGiaoQua_tdcg(Long nvGiaoQuaId, String[] dsMaPhieus, String shopCode, String shopUserName) throws ObjectNotFoundException, DuplicateKeyException;

    /**
     * Perform exchange for customer in the promotion "Q-Teen and Q-Student".
     * @param nvGiaoQuaId
     * @param dsMaPhieus
     * @param shopCode
     * @param shopUserName
     * @param giftId
     * @throws ObjectNotFoundException
     * @throws DuplicateKeyException
     */
    void shopUserGiaoQua_qStudent(Long nvGiaoQuaId, String[] dsMaPhieus, String shopCode, String shopUserName, Long giftId) throws ObjectNotFoundException, DuplicateKeyException;

    /**
     * Fetch for CT tich diem cuoc goi
     * @param soThueBao
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    Object[] search_tdcg(String soThueBao, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Fetch for CT Q-Student Q-Teen
     * @param soThueBao
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    Object[] search_qStudent(String soThueBao, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Get total of exchanged Ma Phieu for the promotion Tich Diem Cuoc Goi Nhan Voucher.
     * @return A number of exchanged Ma Phieu.
     */
    Integer getTotalOfExchangedMaPhieu_tdcg();

    /**
     * Get total of exchanged Ma Phieu for the promotion Q-Teen and Q-Student.
     * @param phoneNumber
     * @return A number of exchanged Ma Phieu.
     */
    Integer getTotalOgExchangedMaPhieu_qStudent(String phoneNumber);

    /**
     * Fetch for CT tich diem cuoc goi
     * @param maPhieuList
     * @return String of Ma Phieu has exchanged before for errors.
     */
    String[] validateMaPhieuNeedToExchanged_tdcg(String[] maPhieuList);

    /**
     * Fetch exchanged Ma Phieu list from list of Ma Phieu for CT Q-Student Q-Teen promotion
     * @param maPhieuList
     * @return An array of exchanged Ma Phieus from list of Ma Phieus parameter that exchanged for gift or money.
     */
    String[] getExchangedMaPhieuListByList_qStudent(String[] maPhieuList);

    /**
     * Canceling gift exchange or money and save to Action Log.
     * @param nvGiaoQuaId
     * @param checkList
     * @param shopCode
     * @param userNameShop
     * @param giftId
     */
    void cancelGiftExchange(Long nvGiaoQuaId, String[] checkList, String shopCode, String userNameShop, Long giftId);
}
