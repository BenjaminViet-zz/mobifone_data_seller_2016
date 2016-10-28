package com.benluck.vms.mobifonedataseller.core.business;

import javax.ejb.ObjectNotFoundException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/13/14
 * Time: 2:29 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SoDiemCTTichDiemManagementlocalBean {
    /**
     * Fetch report data for CT Tich Diem Cuoc Goi promotion
     * @param properties
     * @param soThueBao
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    Object[] statisticCumulativeScoresByDate_tdcg(Map<String, Object> properties, String soThueBao, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Fetch report data for CT Q-Student Q-Teen promotion
     * @param properties
     * @param soThueBao
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    Object[] statisticCumulativeScoresByDate_qStudent(Map<String, Object> properties, String soThueBao, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Fetch report data for CT Tich Diem Cuoc Goi promotion
     * @param soThueBao
     * @return
     */
    Object[] statisticCumulativeScoresByMonth_tdcg(String soThueBao);

    /**
     * Fetch report data for CT Q-Student Q-Teen promotion
     * @param phoneNumber
     * @return
     */
    Object[] statisticCumulativeScoresByMonth_qStudent(String phoneNumber);

    /**
     *  Perform querying in DB to verify the phoneNumber has either registered or not in the promotion Tich Diem Cuoc Goi Nhan Voucher.
     * @param phoneNumber
     * @return True is this phone number has registered to the program. Else False.
     * @throws ObjectNotFoundException
     */
    Boolean checkIfAlreadyRegistered_tdcg(String phoneNumber) throws ObjectNotFoundException;

    /**
     *  Perform querying in DB to verify the phoneNumber has either registered or not in the promotion Q-Teen and Q-.Student.
     * @param phoneNumber
     * @return True is this phone number has registered to the program. Else False.
     * @throws ObjectNotFoundException
     */
    Boolean checkIfAlreadyRegistered_qStudent(String phoneNumber) throws ObjectNotFoundException;


    /**
     * Get total of score related to the phone number parameter in the promotion Tich Diem Cuoc Goi Nhan Voucher.
     * @param phoneNumber
     * @return
     */
    Integer getCurrentScoreTotal_tdcg(String phoneNumber);

    /**
     * Get total of score related to the phone number parameter in the promotion Q-Teen and Q-Student.
     * @param phoneNumber
     * @return
     */
    Integer getCurrentScoreTotal_qStudent(String phoneNumber);

    /**
     * Perform gift exchange to the phone number in the promotion Tich Diem Cuoc Goi Nhan Voucher.
     * @param phoneNumber
     * @throws ObjectNotFoundException
     */
    void exchangeGift_tdcg(String phoneNumber) throws ObjectNotFoundException;

    /**
     * Perform gift exchange to the phone number in the promotion Q-Teen and Q-Student.
     * @param phoneNumber
     * @throws ObjectNotFoundException
     */
    void exchangeGift_qStudent(String phoneNumber) throws ObjectNotFoundException;
}
