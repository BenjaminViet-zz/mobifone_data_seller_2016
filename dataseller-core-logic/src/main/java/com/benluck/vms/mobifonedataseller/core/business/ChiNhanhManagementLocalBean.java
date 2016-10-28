package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ChiNhanhDTO;

import javax.ejb.DuplicateKeyException;
import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/19/14
 * Time: 1:42 PM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface ChiNhanhManagementLocalBean {
    /**
     * Fetch for CT tich diem cuoc goi
     * @param chiNhanhId
     * @param departmentId
     * @return
     */
    List<ChiNhanhDTO> baoCaoQuanLyPhieuKhuyenMai_tdcg(Long chiNhanhId, Long departmentId);

    /**
     * Fetch for CT QStudent Q-Teen
     *
     * @param code
     * @param pojo
     * @return
     */
    List<BranchDTO> baoCaoQuanLyPhieuKhuyenMai_qStudent(String code, ChiNhanhDTO pojo) throws ObjectNotFoundException;

    /**
     * Fetch all ChiNhanh in the promotion.
     * @return A list of ChiNhanhDTO
     */
    List<ChiNhanhDTO> findAll();

    /**
     * Retrive ChiNhanh by chiNhanhId.
     * @param chiNhanhId
     * @return ChiNhanhDTO
     * @throws ObjectNotFoundException
     */
    ChiNhanhDTO findById(Long chiNhanhId) throws ObjectNotFoundException;

    /**
     * Get list of ChiNhanh by paging.
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] search(Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Retrieve ChiNhanh information by chiNhanh number.
     * @param chiNhanh
     * @return ChiNhanhDTO
     */
    ChiNhanhDTO findByChiNhanhUnique(Integer chiNhanh);

    /**
     * Perform uodating ChiNhanh details into DB.
     * @param dto
     * @throws ObjectNotFoundException
     * @throws DuplicateKeyException
     */
    void updateItem(ChiNhanhDTO dto) throws ObjectNotFoundException, DuplicateKeyException;

    /**
     * Perform mapping chiNhanh to branch which stayed in DB Link side.
     * @param chiNhanhList
     * @throws ObjectNotFoundException
     * @throws DuplicateKeyException
     */
    void anhXaChiNhanh(List<ChiNhanhDTO> chiNhanhList) throws ObjectNotFoundException, DuplicateKeyException;

    /**
     * Retrieve ChiNhanh by departmentId.
     * @param departmentId
     * @return ChiNhanhDTO
     * @throws ObjectNotFoundException
     */
    ChiNhanhDTO findChiNhanhByDepartmentId(Long departmentId) throws ObjectNotFoundException;
}
