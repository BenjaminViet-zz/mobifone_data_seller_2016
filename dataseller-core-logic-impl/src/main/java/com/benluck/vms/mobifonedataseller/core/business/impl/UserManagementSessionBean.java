package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.security.DesEncrypterUtils;
import com.benluck.vms.mobifonedataseller.common.utils.Config;
import com.benluck.vms.mobifonedataseller.common.utils.DozerSingletonMapper;
import com.benluck.vms.mobifonedataseller.core.business.UserManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DepartmentDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RoleDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.domain.*;
import com.benluck.vms.mobifonedataseller.session.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


@javax.ejb.Stateless(name = "UserManagementSessionEJB")
public class UserManagementSessionBean implements UserManagementLocalBean{
    @EJB
    private UserLocalBean usersLocalBean;
   @EJB
    private UserGroupLocalBean userGroupLocalBean;
    @EJB
    private UserPasswordLocalBean userPasswordLocalBean;
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;
    @EJB
    private GiftDeliveryAgentHistoryLocalBean giftDeliveryAgentHistoryLocalBean;
    @EJB
    private GiftDeliveryThueBaoLocalBean giftDeliveryThueBaoLocalBean;

    public UserManagementSessionBean() {
    }

    @Override
    public UserDTO findByUsername(String username) throws ObjectNotFoundException {
        UserEntity entity = usersLocalBean.findByUserName(username);
        return DozerSingletonMapper.getInstance().map(entity, UserDTO.class);
    }

    @Override
    public UserDTO findById(Long userId) throws ObjectNotFoundException {
        UserEntity entity = usersLocalBean.findById(userId);
        if (entity == null){
            throw new ObjectNotFoundException("Not found user  "+ userId);
        }
        return  DozerSingletonMapper.getInstance().map(entity, UserDTO.class);
    }

    @Override
    public UserDTO addItem(UserDTO userDTO) throws DuplicateKeyException, ObjectNotFoundException {
        try {
            UserEntity entity = new UserEntity();
            entity.setUserName(userDTO.getUserName());
            entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            entity.setMobileNumber(userDTO.getMobileNumber());
            entity.setDisplayName(userDTO.getDisplayName());
            entity.setPassword(DesEncrypterUtils.getInstance().encrypt(userDTO.getPassword()));
            entity.setEmail(userDTO.getEmail());
            entity.setMobileNumber(userDTO.getMobileNumber());
            entity.setStatus(userDTO.getStatus());

            VmsUserGroupEntity VmsUserGroupEntity = new VmsUserGroupEntity();
            VmsUserGroupEntity.setUserGroupId(userDTO.getUserGroup().getUserGroupId());
            entity.setUserGroup(VmsUserGroupEntity);

            if(userDTO.getChiNhanh() != null && userDTO.getChiNhanh().getChiNhanhId() != null){
                ChiNhanhEntity chiNhanhEntity = new ChiNhanhEntity();
                chiNhanhEntity.setChiNhanhId(userDTO.getChiNhanh().getChiNhanhId());
                entity.setChiNhanh(chiNhanhEntity);
            }

            if(userDTO.getDepartment() != null && userDTO.getDepartment().getDepartmentId() != null){
                DepartmentEntity departmentEntity = new DepartmentEntity();
                departmentEntity.setDepartmentId(userDTO.getDepartment().getDepartmentId());
                entity.setDepartment(departmentEntity);
            }

            entity = this.usersLocalBean.save(entity);
            return DozerSingletonMapper.getInstance().map(entity, UserDTO.class);
        }catch ( DuplicateKeyException de){
            throw new DuplicateKeyException("Can not insert User");
        }
    }

    @Override
    public UserDTO updateItem(UserDTO dto) throws ObjectNotFoundException, DuplicateKeyException {
        UserEntity entity = this.usersLocalBean.findById(dto.getUserId());
        if (entity == null){
            throw new ObjectNotFoundException("Not found user "+ dto.getUserId());
        }

        if(dto.getDepartment() != null && dto.getDepartment().getDepartmentId() != null){
            DepartmentEntity departmentEntity = new DepartmentEntity();
            departmentEntity.setDepartmentId(dto.getDepartment().getDepartmentId());
            entity.setDepartment(departmentEntity);
        }else{
            if(entity.getDepartment() != null){
                entity.setDepartment(null);
            }
        }

        if(dto.getChiNhanh() != null && dto.getChiNhanh().getChiNhanhId() != null){
            ChiNhanhEntity chiNhanhEntity = new ChiNhanhEntity();
            chiNhanhEntity.setChiNhanhId(dto.getChiNhanh().getChiNhanhId());
            entity.setChiNhanh(chiNhanhEntity);
        }else{
            if(entity.getChiNhanh() != null){
                entity.setChiNhanh(null);
            }
        }

        VmsUserGroupEntity VmsUserGroupEntity = new VmsUserGroupEntity();
        VmsUserGroupEntity.setUserGroupId(dto.getUserGroup().getUserGroupId());
        entity.setUserGroup(VmsUserGroupEntity);

        entity.setPassword(DesEncrypterUtils.getInstance().encrypt(dto.getPassword()));
        entity.setEmail(dto.getEmail());
        entity.setDisplayName(dto.getDisplayName());
        entity.setMobileNumber(dto.getMobileNumber());
        entity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        entity.setStatus(dto.getStatus());
        return DozerSingletonMapper.getInstance().map(usersLocalBean.update(entity), UserDTO.class);
    }

    @Override
    public Integer deleted(String checkList[]) throws RemoveException {
        Integer res = 0;
        if (checkList!= null && checkList.length > 0){
            for (String id : checkList){
                try{
                    this.usersLocalBean.delete(Long.parseLong(id));
                    res ++;
                }catch (RemoveException re){
                    throw new RemoveException("Has errors");
                }

            }
        }
        return res;
    }

    @Override
    public Object[] search(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems) {
        Object[] result = this.usersLocalBean.findListUser(properties, sortExpression, sortDirection, firstItem, maxPageItems);
        List resultSet = (List)result[1];
        List<UserDTO> listUserDTO = new ArrayList<>();
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            UserDTO dto = new UserDTO();
            dto.setUserId(Long.valueOf(tmpArr[0].toString()));
            dto.setUserName(tmpArr[1].toString());
            dto.setDisplayName(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setMobileNumber(tmpArr[3] != null ? tmpArr[3].toString() : null);

            if(tmpArr[4] != null){
                DepartmentDTO departmentDTO = new DepartmentDTO();
                departmentDTO.setDepartmentId(Long.valueOf(tmpArr[4].toString()));
                departmentDTO.setName(tmpArr[5].toString());
                dto.setDepartment(departmentDTO);
            }

            dto.setStatus(Integer.valueOf(tmpArr[6].toString()));

            UserGroupDTO userGroupDTO = new UserGroupDTO();
            userGroupDTO.setUserGroupId(Long.valueOf(tmpArr[7].toString()));
            userGroupDTO.setName(tmpArr[8].toString());
            dto.setUserGroup(userGroupDTO);

            if(tmpArr[9] != null){
                dto.setTenChiNhanh(tmpArr[9].toString());
            }
            listUserDTO.add(dto);
        }
        result[1] =   listUserDTO;
        return result;
    }

    @Override
    public UserDTO findByCode(String code) throws ObjectNotFoundException {
        UserEntity dbItem = this.usersLocalBean.findByUserName(code);
        if (dbItem == null ) throw new ObjectNotFoundException("Not found User "+ code);
        return DozerSingletonMapper.getInstance().map(dbItem, UserDTO.class);
    }

    @Override
    public UserDTO findByMobileNumber(String phoneNumber) throws ObjectNotFoundException {
        UserEntity dbItem = this.usersLocalBean.findByMobileNumber(phoneNumber);
        if (dbItem == null ) throw new ObjectNotFoundException("Not found User "+ phoneNumber);
        return DozerSingletonMapper.getInstance().map(dbItem, UserDTO.class);
    }

    @Override
    public UserDTO findByEmail(String email) throws ObjectNotFoundException {
        UserEntity dbItem = this.usersLocalBean.findEqualUnique("email", email);
        if (dbItem == null) throw new ObjectNotFoundException("Not found User "+email);
        return DozerSingletonMapper.getInstance().map(dbItem, UserDTO.class);


    }

    @Override
    public void updateProfile(UserDTO dto) throws ObjectNotFoundException, DuplicateKeyException {
        UserEntity entity = this.usersLocalBean.findById(dto.getUserId());
        if (entity == null){
            throw new ObjectNotFoundException("Not found user "+ dto.getUserId());
        }

        entity.setUserName(dto.getUserName());
        entity.setPassword(DesEncrypterUtils.getInstance().encrypt(dto.getPassword()));
        entity.setDisplayName(dto.getDisplayName());
        entity.setEmail(dto.getEmail());
        entity.setStatus(dto.getStatus());
        entity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        entity = usersLocalBean.update(entity);
    }

    @Override
    public List<UserDTO> searchByName(String name) throws ObjectNotFoundException {
        List<UserDTO> resultList = new ArrayList<UserDTO>();
        List<UserEntity> entities = this.usersLocalBean.findByName("fullname",name);
        for (UserEntity entity : entities){
            UserDTO UserDTO = DozerSingletonMapper.getInstance().map(entity, UserDTO.class);
            resultList.add(UserDTO);
        }
        return resultList;
    }

    @Override
    public List<RoleDTO> findRoleOfUser(Long userId) throws ObjectNotFoundException {
        UserEntity userEntity = usersLocalBean.findById(userId);
        List<RoleDTO> listResult = new ArrayList<RoleDTO>();
        if(userEntity.getRoles() != null)
        {
            if(userEntity.getRoles().size() > 0){
                for(RoleEntity roleEntity: userEntity.getRoles())
                {
                    RoleDTO roleDTO = DozerSingletonMapper.getInstance().map(roleEntity, RoleDTO.class);
                    listResult.add(roleDTO);
                }
            }
        }
        return listResult;
    }

    @Override
    public Object[] saveImport(List<UserDTO> listUsers) {
        int createCount = 0;
        int updateCount = 0;
        int errorCount = 0 ;
        for(UserDTO userDTO : listUsers){
            if(userDTO.getDepartment().getDepartmentId() != null){
                boolean flagUpdate = true;
                UserEntity userEntity = new UserEntity();
                try{
                    userEntity = this.usersLocalBean.findByUserName(userDTO.getUserName());
                    userEntity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
                }catch (Exception exception){
                    userEntity = new UserEntity();
                    flagUpdate = false;
                    userEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                }
                userEntity.setUserName(userDTO.getUserName());
                userEntity.setDisplayName(userDTO.getDisplayName());
                userEntity.setPassword(DesEncrypterUtils.getInstance().encrypt("123456"));
                userEntity.setDepartment(DozerSingletonMapper.getInstance().map(userDTO.getDepartment(), DepartmentEntity.class));
                userEntity.setStatus(1);
                try{
                    if(flagUpdate){
                        updateCount++;
                        this.usersLocalBean.update(userEntity);
                    }else{
                        userEntity.setUserGroup(this.userGroupLocalBean.findEqualUnique("code", "NHANVIEN"));
                        this.usersLocalBean.save(userEntity);
                        createCount++;
                    }
                }catch (Exception e){
                    errorCount ++;
                }


            }
        }
        return new Object[]{createCount, updateCount, errorCount};
    }

    @Override
    public void saveUserInfoAndGenerateVerifyLoginCode(UserDTO dto, String serverIP, String userGroupCode) throws ObjectNotFoundException, DuplicateKeyException{
        try{
            UserEntity userEntity = null;
            String maXacNhan = "";
            try{
                userEntity = this.usersLocalBean.findByUserName(dto.getUserName());
            }catch (ObjectNotFoundException oe){
                userEntity = new UserEntity();
                userEntity.setUserName(dto.getUserName());
                userEntity.setDisplayName(dto.getUserName());
                userEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                userEntity.setMobileNumber(dto.getUserName());
                VmsUserGroupEntity VmsUserGroupEntity = this.userGroupLocalBean.findUserGroupByCode(userGroupCode);
                userEntity.setUserGroup(VmsUserGroupEntity);

                userEntity = this.usersLocalBean.save(userEntity);
            }

            maXacNhan = saveUserPasswordIfExpired(userEntity, userGroupCode);

            sendVerifyCode2KHCN(dto.getUserName(), maXacNhan, serverIP, userGroupCode);
        }catch (DuplicateKeyException de){
            throw new DuplicateKeyException("Duplicate key for userEntity with mobifone number:" + dto.getUserName());
        }
    }

    @Override
    public void deleteById(Long userId) throws RemoveException{
        this.usersLocalBean.delete(userId);
    }

    @Override
    public Boolean checkIfHasRelatedToData(Long userId) {
        if(!daTungNhapHang(userId)){
            if(daTungGiaoQua(userId)){
                return true;
            }
        }
        return  false;
    }

    private boolean daTungNhapHang(Long userId){
        List<GiftDeliveryAgentHistoryEntity> entityList = this.giftDeliveryAgentHistoryLocalBean.findBySubscriberId(userId);
        if(entityList.size() > 0){
            return true;
        }
        return  false;
    }

    private boolean daTungGiaoQua(Long userId){
        List<GiftDeliveryThueBaoEntity> entityList = this.giftDeliveryThueBaoLocalBean.findByDelivererId(userId);
        if(entityList.size() > 0){
            return  true;
        }
        return  false;
    }

    private String saveUserPasswordIfExpired(UserEntity userEntity, String userGroupCode) throws DuplicateKeyException{
        StringBuilder verifyCodeStr = null;
        UserPasswordEntity userPasswordEntity = this.userPasswordLocalBean.findLastestBySoPhoneNumber(userEntity.getUserName());
        boolean flagSaveNew = false;
        if(userPasswordEntity != null){
            if(userPasswordEntity.getExpiredTime().compareTo(new Time(System.currentTimeMillis())) < 0){
                flagSaveNew = true;
            }else{
                verifyCodeStr = new StringBuilder(userPasswordEntity.getPassword());
            }
        }else{
            flagSaveNew = true;
        }
        if(flagSaveNew){
            verifyCodeStr = new StringBuilder(RandomStringUtils.randomAlphabetic(6));
            UserPasswordEntity entity = new UserPasswordEntity();
            entity.setUser(userEntity);
            entity.setPassword(verifyCodeStr.toString());
            entity.setCreatedTime(new Timestamp(System.currentTimeMillis()));

            String expiredInMinute = Config.getInstance().getProperty("verifycode.time_expired_in_minutes");
            Calendar expiredTime = Calendar.getInstance();
            expiredTime.add(Calendar.MINUTE, Integer.valueOf(expiredInMinute));
            entity.setExpiredTime(new Timestamp(expiredTime.getTimeInMillis()));
            entity.setStatus(Constants.USERPASSWORD_IS_USED);

            if(userGroupCode.equals(Constants.USERGROUP_KPP)){
                entity.setPasswordType(Constants.PASSWORD_TYPE_KPP);
            }else{
                entity.setPasswordType(Constants.PASSWORD_TYPE_TB);
            }
            this.userPasswordLocalBean.save(entity);
        }
        return verifyCodeStr.toString();
    }

    private void sendVerifyCode2KHCN(String mobifoneNumber, String verifyCode, String serverIP, String userGroupCode){
        String flagSendSms = Config.getInstance().getProperty("send_sms.enable");
        if(StringUtils.isNotBlank(flagSendSms) && flagSendSms.equalsIgnoreCase("true")){
            String chuong_trinh = Config.getInstance().getProperty("promotion_type_configure");
            switch (chuong_trinh){
                case Constants.CT_THUE_BAO_PTM:
                    sendSMS4CTPhatTrienThueBaoMoi(serverIP, mobifoneNumber, verifyCode, userGroupCode);
                    break;
                default:
                    sendSMS4CTTichDiemCuocGoi(serverIP, mobifoneNumber, verifyCode, userGroupCode);
                    break;
            }
        }
    }

    private void sendSMS4CTTichDiemCuocGoi(String serverIP, String mobifoneNumber, String verifyCode, String userGroupCode){
        StringBuilder smsContent = new StringBuilder();
        if(userGroupCode.equals(Constants.USERGROUP_TB)){
            smsContent = new StringBuilder("Ma dang nhap CT Tich diem cuoc goi nhan ngay voucher cua quy khach la: ").append(verifyCode);
            ctTichDiemLocalBean.sendSMS(serverIP, mobifoneNumber, smsContent.toString());
        }else{
            smsContent = new StringBuilder("Ma dang nhap CT Tich diem tren kennh phan phoi cua quy khach la: ").append(verifyCode);
            ctTichDiemLocalBean.sendSMS(serverIP, mobifoneNumber, smsContent.toString());
        }
    }

    private void sendSMS4CTPhatTrienThueBaoMoi(String serverIP, String mobifoneNumber, String verifyCode, String userGroupCode){
        StringBuilder smsContent = new StringBuilder("Ma dang nhap CT cua quy khach la: ").append(verifyCode);
        ctTichDiemLocalBean.sendSMS(serverIP, mobifoneNumber, smsContent.toString());
    }
}
