package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;
import java.util.List;


/**
 * AbstractSearchDTO for remote invoke
 * 
 * @author benluck
 * 
 * @param <E>
 */
public class AbstractSearchDTO<E> implements Serializable {

    private static final long serialVersionUID = -8456399129453526155L;
    protected int maxPageItems = 20;
	private int firstItem = 0;
	private int totalItems = 0;
	private String sortExpression;
	private String sortDirection;
	private String[] checkList;
	private List<E> listResult;
    private String crudaction;
    private String tableId = "tableList";

    private int page = 1;

	protected E pojo;
	/**
	 * @return the pojo
	 */
	public E getPojo() {
		return pojo;
	}

	/**
	 * @param pojo the pojo to set
	 */
	public void setPojo(E pojo) {
		this.pojo = pojo;
	}

	public String[] getCheckList() {
		return checkList;
	}

	public void setCheckList(String[] checkList) {
		this.checkList = checkList;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public List<E> getListResult() {
		return listResult;
	}

	public void setListResult(List<E> listResult) {
		this.listResult = listResult;
	}

	public int getFirstItem() {
		return firstItem;
	}

	public void setFirstItem(int firstItem) {
		this.firstItem = firstItem;
	}

	public int getCurrentPage() {
		return this.firstItem / this.maxPageItems + 1;
	}

	/**
	 * @return the maxPageItems
	 */
	public int getMaxPageItems() {
		return maxPageItems;
	}

	/**
	 * @param maxPageItems
	 *            the maxPageItems to set
	 */
	public void setMaxPageItems(int maxPageItems) {
		this.maxPageItems = maxPageItems;
	}

	/**
	 * @return the sortExpression
	 */
	public String getSortExpression() {
		return sortExpression;
	}

	/**
	 * @param sortExpression the sortExpression to set
	 */
	public void setSortExpression(String sortExpression) {
		this.sortExpression = sortExpression;
	}

	/**
	 * @return the sortDirection
	 */
	public String getSortDirection() {
		return sortDirection;
	}

	/**
	 * @param sortDirection the sortDirection to set
	 */
	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

    /**
     *
     * @return the crudaction value for CRUD actions
     */
    public String getCrudaction() {
        return crudaction;
    }

    /**
     * @param crudaction the crudaction value to set
     */
    public void setCrudaction(String crudaction) {
        this.crudaction = crudaction;
    }

    /**
     * @return the table id value from submitting.
     */
    public String getTableId() {
        return tableId;
    }

    /**
     * @param tableId value of table id to set
     */
    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    /**
     * @return the page index on table paging.
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page the page index to set
     */
    public void setPage(int page) {
        this.page = page;
    }


}
