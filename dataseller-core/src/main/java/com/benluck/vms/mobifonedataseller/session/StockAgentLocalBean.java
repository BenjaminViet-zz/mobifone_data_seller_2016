package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.StockAgentEntity;

import javax.ejb.ObjectNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/15/14
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */
public interface StockAgentLocalBean extends GenericSessionBean<StockAgentEntity, Long> {
    StockAgentEntity findByDepartmentId(Long departmentId);

    /**
     * Fetch for CT tich diem cuoc goi
     * @param departmentId
     * @return
     */
    Integer countInventoryTotalByAgentId_tdcg(Long departmentId);

    /**
     * Fetch for CT Q-Student Q-Teen
     *
     *
     * @param shopCode
     * @param giftId
     * @return
     */
    Integer countInventoryTotalByAgentId_qStudent(String shopCode, Long giftId);

    /**
     * Find total of gift in stock of the shop by delivererId (shopUserId)
     * @param shopUserId
     * @return StockAgentEntity.
     */
    StockAgentEntity findByShopIdOfDelivererId(Long shopUserId);

    /**
     * Fetch for CT tich diem cuoc goi
     * @param chiNhanh
     * @param agentId
     * @param trang_thai_kho
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    Object[] traCuuKhoHangCuaHangMobifoneKhac_tdcg(Integer chiNhanh, Long agentId, Integer trang_thai_kho, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Fetch for CT Q-Student Q-Teen
     *
     *
     * @param chuongTrinhId
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    Object[] traCuuKhoHangCuaHangMobifoneKhac_qStudent(Map<String, Object> properties, Long chuongTrinhId, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Fetch for CT tich diem cuoc goi
     * @param chiNhanhId
     * @param departmentId
     * @return
     */
    List baoCaoQuanLyPhieuKhuyenMai_tdcg(Long chiNhanhId, Long departmentId);

    /**
     * Fetch for CT Q-Student Q-teen
     * @param branchId
     * @param departmentId
     * @return
     */
    List baoCaoQuanLyPhieuKhuyenMai_qStudent(Long branchId, Long chuongTrinhId, Long departmentId);
}
