package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.common.utils.DozerSingletonMapper;
import com.benluck.vms.mobifonedataseller.core.business.DMChuongTrinhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.PromotionDTO;
import com.benluck.vms.mobifonedataseller.domain.BranchMappingEntity;
import com.benluck.vms.mobifonedataseller.domain.DMChuongTrinhEntity;
import com.benluck.vms.mobifonedataseller.session.BranchMappingLocalBean;
import com.benluck.vms.mobifonedataseller.session.DMChuongTrinhLocalBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/2/15
 * Time: 11:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "DMChuongTrinhManagementSessionEJB")
public class DMChuongTrinhManagementSessionBean implements DMChuongTrinhManagementLocalBean{
    private transient final Log log = LogFactory.getLog(getClass());

    public DMChuongTrinhManagementSessionBean() {
    }

    @EJB
    private DMChuongTrinhLocalBean dmChuongTrinhLocalBean;
    @EJB
    private BranchMappingLocalBean branchMappingLocalBean;

    @Override
    public Object[] search(Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.dmChuongTrinhLocalBean.searchByProperties(new HashMap<String, Object>(), sortExpression, sortDirection, firstItem, maxPageItems);
        List<PromotionDTO> dtoList = new ArrayList<PromotionDTO>();
        for(DMChuongTrinhEntity entity : (List<DMChuongTrinhEntity>)resultObject[1]){
            PromotionDTO dto = DozerSingletonMapper.getInstance().map(entity, PromotionDTO.class);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public List<PromotionDTO> findAll() {
        List<DMChuongTrinhEntity> entityList = this.dmChuongTrinhLocalBean.findAll();
        List<PromotionDTO> dtoList = new ArrayList<PromotionDTO>();
        for(DMChuongTrinhEntity entity : entityList){
            dtoList.add(DozerSingletonMapper.getInstance().map(entity, PromotionDTO.class));
        }
        return dtoList;
    }

    @Override
    public void addItem(PromotionDTO dto) throws DuplicateKeyException {
        DMChuongTrinhEntity entity = new DMChuongTrinhEntity();
        entity.setCode(dto.getCode());
        entity.setDescription(dto.getDescription());
        entity.setDbLinkName(dto.getDbLinkName());
        entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        this.dmChuongTrinhLocalBean.save(entity);
    }

    @Override
    public void updateItem(PromotionDTO dto) throws ObjectNotFoundException, DuplicateKeyException {
        DMChuongTrinhEntity dbItem = this.dmChuongTrinhLocalBean.findById(dto.getChuongTrinhId());
        dbItem.setCode(dto.getCode());
        dbItem.setDescription(dto.getDescription());
        dbItem.setDbLinkName(dto.getDbLinkName());
        dbItem.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        this.dmChuongTrinhLocalBean.update(dbItem);
    }

    @Override
    public PromotionDTO findByCode(String code) throws ObjectNotFoundException {
        DMChuongTrinhEntity dbItem = this.dmChuongTrinhLocalBean.findEqualUnique("code", code.trim());
        if(dbItem == null){
            throw new ObjectNotFoundException("Objetc not found for chuong trinh with code: " + code.trim());
        }
        return DozerSingletonMapper.getInstance().map(dbItem, PromotionDTO.class);
    }

    @Override
    public PromotionDTO findById(Long chuongTrinhId) throws ObjectNotFoundException {
        DMChuongTrinhEntity entity = this.dmChuongTrinhLocalBean.findById(chuongTrinhId);
        return DozerSingletonMapper.getInstance().map(entity, PromotionDTO.class);
    }

    @Override
    public void deleteItem(Long promotionId) throws RemoveException {
        List<BranchMappingEntity> branchMappingEntityList = this.branchMappingLocalBean.findProperty("chuongTrinh.chuongTrinhId", promotionId);
        if(branchMappingEntityList.size() > 0){
            throw new RemoveException("Delete ChuongTrinh failed!. There are mapping(s) to this ChuongTrinh.");
        }else{
            this.dmChuongTrinhLocalBean.delete(promotionId);
        }
    }
}
