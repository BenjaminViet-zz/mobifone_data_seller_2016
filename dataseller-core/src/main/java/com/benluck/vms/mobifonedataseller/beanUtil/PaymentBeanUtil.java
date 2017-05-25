package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.core.dto.PaymentDTO;
import com.benluck.vms.mobifonedataseller.domain.PaymentEntity;
import com.benluck.vms.mobifonedataseller.domain.PaymentHistoryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/25/17
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public class PaymentBeanUtil {
    public static PaymentDTO entity2DTO(PaymentEntity entity){
        if (entity != null){
            PaymentDTO dto = new PaymentDTO();
            dto.setPaymentId(entity.getPaymentId());
            dto.setKhdn(KHDNBeanUtil.entity2DTO(entity.getKhdn()));
            dto.setOrder(OrderBeanUtil.entity2DTO(entity.getOrder()));
            dto.setCreatedBy(UserBeanUtil.entity2DTO(entity.getCreatedBy()));
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setStatus(entity.getStatus());

            PaymentHistoryEntity lastPaymentHistory = null;
            if (entity.getPaymentHistoryList() != null && entity.getPaymentHistoryList().size() > 0){
                Double paidAmount = 0D;
                for (PaymentHistoryEntity paymentHistoryEntity : entity.getPaymentHistoryList()){
                    if (paymentHistoryEntity.getAmount() != null){
                        paidAmount = paidAmount.doubleValue() + paymentHistoryEntity.getAmount().doubleValue();
                        lastPaymentHistory = paymentHistoryEntity;
                    }
                }

                dto.setTotalPaidAmount(paidAmount);
            }

            if (lastPaymentHistory != null){
                dto.setPaymentDate(lastPaymentHistory.getCreatedDate());
            }

            return dto;
        }
        return null;
    }

    public static List<PaymentDTO> entityList2DTOList(List<PaymentEntity> entityList){
        if (entityList != null){
            List<PaymentDTO> dtoList = new ArrayList<PaymentDTO>();
            for (PaymentEntity entity : entityList){
                dtoList.add(entity2DTO(entity));
            }
            return dtoList;
        }
        return null;
    }
}
