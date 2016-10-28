package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DozerSingletonMapper;
import com.benluck.vms.mobifonedataseller.core.business.DepartmentManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DepartmentDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ShopCodeChiNhanhDTO;
import com.benluck.vms.mobifonedataseller.domain.*;
import com.benluck.vms.mobifonedataseller.session.*;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import javax.ejb.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: HauKute
 * Date: 10/9/14
 * Time: 6:34 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "DepartmentManagementSessionEJB")
public class DepartmentManagementSessionBean implements DepartmentManagementLocalBean {
    public DepartmentManagementSessionBean(){

    }

    @EJB
    DepartmentLocalBean departmentLocalBean;
    @EJB
    private DMChuongTrinhLocalBean dmChuongTrinhLocalBean;
    @EJB
    private ShopCodeChiNhanhLocalBean shopCodeChiNhanhLocalBean;
    @EJB
    private BranchMappingLocalBean branchMappingLocalBean;

    Mapper mapper = new DozerBeanMapper();

    @Override
    public DepartmentDTO updateItem(DepartmentDTO departmentDTO) throws DuplicateKeyException, javax.ejb.ObjectNotFoundException {
        DepartmentDTO departmentDTO1 = new DepartmentDTO();
        ShopCodeChiNhanhDTO shopCodeChiNhanhDTO = new ShopCodeChiNhanhDTO();
        DepartmentEntity dbItem = this.departmentLocalBean.findById(departmentDTO.getDepartmentId());
        if (dbItem == null) throw new ObjectNotFoundException("Not found department " + departmentDTO.getDepartmentId());

        DepartmentEntity pojo = mapper.map(departmentDTO, DepartmentEntity.class);
        pojo.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        pojo.setCreatedDate(dbItem.getCreatedDate());

        ShopCodeChiNhanhEntity shopCodeChiNhanhEntity = new ShopCodeChiNhanhEntity();
        shopCodeChiNhanhEntity.setShopCode(departmentDTO.getCode());
        ChiNhanhEntity chiNhanhEntity = new ChiNhanhEntity();
        chiNhanhEntity.setChiNhanhId(departmentDTO.getChiNhanhId());
        shopCodeChiNhanhEntity.setChiNhanh(chiNhanhEntity);

        departmentDTO1 =  mapper.map(this.departmentLocalBean.update(pojo), DepartmentDTO.class);
        shopCodeChiNhanhDTO = mapper.map(this.shopCodeChiNhanhLocalBean.update(shopCodeChiNhanhEntity), ShopCodeChiNhanhDTO.class);
        departmentDTO1.setChiNhanhId(shopCodeChiNhanhDTO.getChiNhanh().getChiNhanhId());

        return departmentDTO1;
    }

    @Override
    public DepartmentDTO addItem(DepartmentDTO departmentDTO) throws DuplicateKeyException {
        DepartmentDTO departmentDTO1 = new DepartmentDTO();
        ShopCodeChiNhanhDTO shopCodeChiNhanhDTO = new ShopCodeChiNhanhDTO();

        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setCode(departmentDTO.getCode());
        departmentEntity.setName(departmentDTO.getName());
        departmentEntity.setContactName(departmentDTO.getContactName());
        departmentEntity.setTel(departmentDTO.getTel());
        departmentEntity.setDepartmentType(departmentDTO.getDepartmentType());
        departmentEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        departmentEntity.setModifiedDate(new Timestamp(System.currentTimeMillis()));


        ShopCodeChiNhanhEntity shopCodeChiNhanhEntity = new ShopCodeChiNhanhEntity();
        shopCodeChiNhanhEntity.setShopCode(departmentDTO.getCode());

        ChiNhanhEntity chiNhanhEntity = new ChiNhanhEntity();
        chiNhanhEntity.setChiNhanhId(departmentDTO.getChiNhanhId());
        shopCodeChiNhanhEntity.setChiNhanh(chiNhanhEntity);

        departmentDTO1 = mapper.map(this.departmentLocalBean.save(departmentEntity), DepartmentDTO.class);
        shopCodeChiNhanhDTO = mapper.map(this.shopCodeChiNhanhLocalBean.save(shopCodeChiNhanhEntity), ShopCodeChiNhanhDTO.class);

        departmentDTO1.setChiNhanhId(shopCodeChiNhanhDTO.getChiNhanh().getChiNhanhId());

        return departmentDTO1;
    }

    @Override
    public Integer deleteItems(String[] checkList) {
        Integer res = 0;
        if (checkList != null && checkList.length > 0) {
            res = checkList.length;
            for (String id : checkList) {
                try {
                    departmentLocalBean.delete(Long.parseLong(id));
                } catch (RemoveException e) {
                   // log.error(e.getMessage());
                }
            }
        }
        return res;
    }

    @Override
    public Object[] search(DepartmentDTO departmentDTO, String sortExpression, String sortDirection, Integer firstItems, Integer maxPageItems) {
        Map<String, Object> properties = new HashMap<String, Object>();

        if (departmentDTO.getChiNhanhId() != null) {
            properties.put("chiNhanhId", departmentDTO.getChiNhanhId());
        }

        Object[] res = this.departmentLocalBean.search(properties, firstItems, maxPageItems, sortExpression, sortDirection);
        List<DepartmentDTO> dtoList = new ArrayList<DepartmentDTO>();
        List resultSet = (List)res[1];
        for (Object object : resultSet) {
            Object[] tmpArr = (Object[])object;
            DepartmentDTO dto = new DepartmentDTO();
            dto.setDepartmentId(Long.valueOf(tmpArr[0].toString()));
            dto.setCode(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setName(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setTenChiNhanh(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setTel(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dto.setAddress(tmpArr[5] != null ? tmpArr[5].toString() : null);
            if (tmpArr[6] != null){
                ShopCodeChiNhanhDTO shopCodeChiNhanhDTO = new ShopCodeChiNhanhDTO();
                shopCodeChiNhanhDTO.setShopCodeChiNhanhId(Long.valueOf(tmpArr[6] != null ? tmpArr[6].toString() : null));
                dto.setShopCode(shopCodeChiNhanhDTO);
            }
            dtoList.add(dto);
        }
        res[1] = dtoList;
        return res;
    }

    @Override
    public DepartmentDTO findByIdAndShopCodeId(Long shopCodeBranchId, Long departmentId) {
        List resultSet = null;
        try {
            resultSet = this.departmentLocalBean.findByIdAndShopCodeId(departmentId, shopCodeBranchId);
            List<DepartmentDTO> dtoList = new ArrayList<DepartmentDTO>();
            for (Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                DepartmentDTO dto = new DepartmentDTO();
                dto.setDepartmentId(Long.valueOf(tmpArr[0] != null ?tmpArr[0].toString() : null));
                dto.setCode(tmpArr[1] != null ?tmpArr[1].toString() : null);
                dto.setName(tmpArr[2] != null ?tmpArr[2].toString() : null);
                dto.setAddress(tmpArr[3] != null ?tmpArr[3].toString() : null);
                dto.setTel(tmpArr[4] != null ?tmpArr[4].toString() : null);
                dto.setContactName(tmpArr[5] != null ?tmpArr[5].toString() : null);
                dto.setChiNhanhId(tmpArr[6] != null ?Long.valueOf(tmpArr[6].toString()) : null);
                dtoList.add(dto);
            }
            return  dtoList.get(0);
        } catch (ObjectNotFoundException e) {
            return null;
        }
    }

    @Override
    public List<DepartmentDTO> findDepartmentListByBranchIdAndCtCode(Long branchId, String ctCode) {
        try {
            DMChuongTrinhEntity dmChuongTrinhEntity = this.dmChuongTrinhLocalBean.findEqualUnique("code",ctCode);

            List resultSet = this.departmentLocalBean.findByBranchIdAndpromotionId(branchId, dmChuongTrinhEntity.getChuongTrinhId());
            List<DepartmentDTO> dtoList = new ArrayList<DepartmentDTO>();
            for (Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                DepartmentDTO dto = new DepartmentDTO();
                dto.setDepartmentId(Long.valueOf(tmpArr[0].toString()));
                dto.setCode(tmpArr[1].toString());
                dto.setName(tmpArr[2].toString());
                dtoList.add(dto);
            }
            return  dtoList;
        } catch (ObjectNotFoundException e) {
            return null;
        }
    }

    @Override
    public DepartmentDTO findById(Long departmentId) throws ObjectNotFoundException {
        return mapper.map(departmentLocalBean.findById(departmentId), DepartmentDTO.class);
    }

    @Override
    public DepartmentDTO findByCode(String code) throws ObjectNotFoundException {
        DepartmentEntity entity = departmentLocalBean.findEqualUnique("code", code);
        return DozerSingletonMapper.getInstance().map(entity, DepartmentDTO.class);
    }

    @Override
    public void importAndUpdate(List<DepartmentDTO> listDTO) {
        if(listDTO.size() > 0)
        {
            try{
                for(DepartmentDTO DepartmentDTO :  listDTO)
                {
                    DepartmentEntity departmentEntity = this.mapper.map(DepartmentDTO, DepartmentEntity.class);
                    DepartmentEntity entity = this.departmentLocalBean.findByCode(departmentEntity.getCode());

                    if(entity != null){
                        departmentEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                        departmentEntity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
                        departmentEntity.setDepartmentId(entity.getDepartmentId());
                        this.departmentLocalBean.update(departmentEntity);
                    }
                    else{
                        departmentEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                        departmentEntity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
                        departmentEntity = this.departmentLocalBean.save(departmentEntity);
                    }

                }
                boolean flag = true;
            }
            catch (Exception e)
            {
               // log.error(e.getMessage());
            }
        }
    }

    @Override
    public List<DepartmentDTO> findDepartmentListByChiNhanhId(Long chiNhanhId) {
        List resultSet = this.departmentLocalBean.findDepartmentListByBranchId(chiNhanhId);
        List<DepartmentDTO> dtoList = new ArrayList<DepartmentDTO>();
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            DepartmentDTO dto = new DepartmentDTO();
            dto.setDepartmentId(Long.valueOf(tmpArr[0].toString()));
            dto.setCode(tmpArr[1].toString());
            dto.setName(tmpArr[2].toString());
            dtoList.add(dto);
        }
        return  dtoList;
    }

    @Override
    public List<DepartmentDTO> findAllByType(String type) {
        List<DepartmentDTO> results = new ArrayList<>();
        List<DepartmentEntity> listEntites = (List<DepartmentEntity>)departmentLocalBean.findByProperty("departmentType", type) ;
        for(DepartmentEntity departmentEntity : listEntites){
            results.add(DozerSingletonMapper.getInstance().map(departmentEntity, DepartmentDTO.class));
        }
        return results;
    }

    @Override
    public List<DepartmentDTO> findAll() {
        List<DepartmentEntity> departmentEntities = (List<DepartmentEntity>)departmentLocalBean.findByProperties(new HashMap<String, Object>(), "name", Constants.SORT_ASC, 0, Integer.MAX_VALUE);
        List<DepartmentDTO> departmentDTOs = new ArrayList<DepartmentDTO>();
        for(DepartmentEntity entity : departmentEntities) {
            departmentDTOs.add(DozerSingletonMapper.getInstance().map(entity, DepartmentDTO.class));
        }
        return departmentDTOs;
    }



}
