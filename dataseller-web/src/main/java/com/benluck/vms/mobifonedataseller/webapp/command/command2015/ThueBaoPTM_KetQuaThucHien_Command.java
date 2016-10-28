package com.benluck.vms.mobifonedataseller.webapp.command.command2015;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.RegistrationTransDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 2/10/15
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class ThueBaoPTM_KetQuaThucHien_Command extends AbstractCommand<RegistrationTransDTO>{
    public ThueBaoPTM_KetQuaThucHien_Command(){
        this.pojo = new RegistrationTransDTO();
        this.user = new UserDTO();
    }

    private UserDTO user;
    private String message;
    private Date thoiGianDangKyFrom;
    private Date thoiGianDangKyTo;
    private Timestamp thoiGianDangKyFromTime;
    private Timestamp thoiGianDangKyToTime;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getThoiGianDangKyTo() {
        return thoiGianDangKyTo;
    }

    public void setThoiGianDangKyTo(Date thoiGianDangKyTo) {
        this.thoiGianDangKyTo = thoiGianDangKyTo;
    }

    public Date getThoiGianDangKyFrom() {
        return thoiGianDangKyFrom;
    }

    public void setThoiGianDangKyFrom(Date thoiGianDangKyFrom) {
        this.thoiGianDangKyFrom = thoiGianDangKyFrom;
    }

    public Timestamp getThoiGianDangKyFromTime() {
        return thoiGianDangKyFromTime;
    }

    public void setThoiGianDangKyFromTime(Timestamp thoiGianDangKyFromTime) {
        this.thoiGianDangKyFromTime = thoiGianDangKyFromTime;
    }

    public Timestamp getThoiGianDangKyToTime() {
        return thoiGianDangKyToTime;
    }

    public void setThoiGianDangKyToTime(Timestamp thoiGianDangKyToTime) {
        this.thoiGianDangKyToTime = thoiGianDangKyToTime;
    }
}
