package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.core.dto.PaymentDTO;
import com.benluck.vms.mobifonedataseller.core.dto.PaymentHistoryDTO;
import com.benluck.vms.mobifonedataseller.domain.PaymentHistoryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/25/17
 * Time: 23:19
 * To change this template use File | Settings | File Templates.
 */
public class PaymentHistoryBeanUtil {
    public static PaymentHistoryDTO entity2DTO(PaymentHistoryEntity entity){
        if (entity != null){
            PaymentHistoryDTO dto = new PaymentHistoryDTO();

            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setPaymentId(entity.getPayment().getPaymentId());
            paymentDTO.setOrder(OrderBeanUtil.entity2DTO(entity.getPayment().getOrder()));
            paymentDTO.setCreatedBy(UserBeanUtil.entity2DTO(entity.getCreatedBy()));
            paymentDTO.setCreatedDate(entity.getCreatedDate());
            paymentDTO.setStatus(entity.getPayment().getStatus());
            dto.setPayment(paymentDTO);

            PaymentBeanUtil.updateOrderTotalAndCalculateRemainingAmount(entity.getPayment(), paymentDTO);

            dto.setPaymentHistoryId(entity.getPaymentHistoryId());
            dto.setAmount(entity.getAmount());
            dto.setStatus(entity.getStatus());
            dto.setCreatedBy(UserBeanUtil.entity2DTO(entity.getCreatedBy()));
            dto.setPaymentDate(entity.getPaymentDate());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setLastModifiedDate(entity.getLastModifiedDate());
            dto.setModifiedBy(UserBeanUtil.entity2DTO(entity.getModifiedBy()));

            return dto;
        }
        return null;
    }

    public static List<PaymentHistoryDTO> entityList2DTOList(List<PaymentHistoryEntity> entityList){
        if (entityList != null){
            List<PaymentHistoryDTO> dtoList = new ArrayList<PaymentHistoryDTO>();
            for (PaymentHistoryEntity entity : entityList){
                dtoList.add(entity2DTO(entity));
            }
            return dtoList;
        }
        return null;
    }
}
