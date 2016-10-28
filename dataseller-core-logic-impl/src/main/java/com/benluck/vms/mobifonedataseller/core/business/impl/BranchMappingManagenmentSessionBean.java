package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.common.utils.DozerSingletonMapper;
import com.benluck.vms.mobifonedataseller.core.business.BranchMappingManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchMappingDTO;
import com.benluck.vms.mobifonedataseller.domain.BranchMappingEntity;
import com.benluck.vms.mobifonedataseller.domain.ChiNhanhEntity;
import com.benluck.vms.mobifonedataseller.domain.DMChuongTrinhEntity;
import com.benluck.vms.mobifonedataseller.domain.UserEntity;
import com.benluck.vms.mobifonedataseller.session.BranchMappingLocalBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.DuplicateKeyException;
import javax.ejb.EJB;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/2/15
 * Time: 11:12 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BranchMappingManagenmentSessionEJB")
public class BranchMappingManagenmentSessionBean implements BranchMappingManagementLocalBean{
    private transient final Log log = LogFactory.getLog(getClass());

    public BranchMappingManagenmentSessionBean() {
    }

    @EJB
    private BranchMappingLocalBean branchMappingLocalBean;

    @Override
    public void anhXaChiNhanh(Long updatedByUserId, List<BranchMappingDTO> branchMappingList) throws DuplicateKeyException{
        for(BranchMappingDTO branchMappingDTO : branchMappingList){
            BranchMappingEntity entity = null;
            try{
                entity = this.branchMappingLocalBean.findByUniqueProperties(branchMappingDTO.getChuongTrinh().getChuongTrinhId(), branchMappingDTO.getChiNhanh().getChiNhanhId());
            }catch (ObjectNotFoundException oe){}
            if(entity != null){
                entity.setBranchId(branchMappingDTO.getBranchId());
                entity.setModifiedDate(new Timestamp(System.currentTimeMillis()));

                UserEntity changedByUser = new UserEntity();
                changedByUser.setUserId(updatedByUserId);
                entity.setChangedBy(changedByUser);
                this.branchMappingLocalBean.update(entity);
            }else{
                entity = new BranchMappingEntity();
                DMChuongTrinhEntity dmChuongTrinhEntity = new DMChuongTrinhEntity();
                dmChuongTrinhEntity.setChuongTrinhId(branchMappingDTO.getChuongTrinh().getChuongTrinhId());
                entity.setChuongTrinh(dmChuongTrinhEntity);

                ChiNhanhEntity chiNhanhEntity = new ChiNhanhEntity();
                chiNhanhEntity.setChiNhanhId(branchMappingDTO.getChiNhanh().getChiNhanhId());
                entity.setChiNhanh(chiNhanhEntity);

                UserEntity createdByUser = new UserEntity();
                createdByUser.setUserId(updatedByUserId);
                entity.setCreatedBy(createdByUser);

                entity.setBranchId(branchMappingDTO.getBranchId());
                entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                this.branchMappingLocalBean.save(entity);
            }
        }
    }

    @Override
    public List<BranchMappingDTO> findByChuongTrinhId(Long programId) {
        List<BranchMappingEntity> entityList = this.branchMappingLocalBean.findByProperty("chuongTrinh.chuongTrinhId", programId);
        List<BranchMappingDTO> dtoList = new ArrayList<BranchMappingDTO>();
        for (BranchMappingEntity entity : entityList){
            dtoList.add(DozerSingletonMapper.getInstance().map(entity, BranchMappingDTO.class));
        }
        return dtoList;
    }

    @Override
    public BranchMappingDTO findByBranchIdAndProgramCode(Long branchId, String programCode) throws ObjectNotFoundException {
        BranchMappingEntity branchMappingEntity = this.branchMappingLocalBean.findByUniqueProperties(branchId, programCode);
        return DozerSingletonMapper.getInstance().map(branchMappingEntity, BranchMappingDTO.class);
    }
}
