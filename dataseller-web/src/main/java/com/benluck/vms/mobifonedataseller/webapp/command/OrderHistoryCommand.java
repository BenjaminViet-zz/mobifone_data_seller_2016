package com.benluck.vms.mobifonedataseller.webapp.command;

import com.benluck.vms.mobifonedataseller.core.dto.OrderDataCodeDTO;
import com.benluck.vms.mobifonedataseller.core.dto.OrderHistoryDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/3/16
 * Time: 07:12
 * To change this template use File | Settings | File Templates.
 */
public class OrderHistoryCommand extends AbstractCommand<OrderHistoryDTO>{
    public OrderHistoryCommand(){
        this.pojo = new OrderHistoryDTO();
    }

    private List<OrderDataCodeDTO> cardCodeList;
    private Integer cardCodePageIndex = 1;
    private Integer firstCardCodePageItem = 0;
    private Integer totalCardCodeItems = 0;
    private String sortExpressionCardCode;
    private String sortDirectionCardCode;
    private Integer cardCodeMaxPageItems = 100;

    public List<OrderDataCodeDTO> getCardCodeList() {
        return cardCodeList;
    }

    public void setCardCodeList(List<OrderDataCodeDTO> cardCodeList) {
        this.cardCodeList = cardCodeList;
    }

    public Integer getCardCodePageIndex() {
        return cardCodePageIndex;
    }

    public void setCardCodePageIndex(Integer cardCodePageIndex) {
        this.cardCodePageIndex = cardCodePageIndex;
    }

    public Integer getFirstCardCodePageItem() {
        return firstCardCodePageItem;
    }

    public void setFirstCardCodePageItem(Integer firstCardCodePageItem) {
        this.firstCardCodePageItem = firstCardCodePageItem;
    }

    public Integer getTotalCardCodeItems() {
        return totalCardCodeItems;
    }

    public void setTotalCardCodeItems(Integer totalCardCodeItems) {
        this.totalCardCodeItems = totalCardCodeItems;
    }

    public String getSortExpressionCardCode() {
        return sortExpressionCardCode;
    }

    public void setSortExpressionCardCode(String sortExpressionCardCode) {
        this.sortExpressionCardCode = sortExpressionCardCode;
    }

    public String getSortDirectionCardCode() {
        return sortDirectionCardCode;
    }

    public void setSortDirectionCardCode(String sortDirectionCardCode) {
        this.sortDirectionCardCode = sortDirectionCardCode;
    }

    public Integer getCardCodeMaxPageItems() {
        return cardCodeMaxPageItems;
    }

    public void setCardCodeMaxPageItems(Integer cardCodeMaxPageItems) {
        this.cardCodeMaxPageItems = cardCodeMaxPageItems;
    }
}
