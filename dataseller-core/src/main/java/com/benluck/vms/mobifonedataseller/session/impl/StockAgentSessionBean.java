package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.domain.StockAgentEntity;
import com.benluck.vms.mobifonedataseller.session.StockAgentLocalBean;
import org.apache.commons.lang.StringUtils;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/15/14
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "StockAgentSessionEJB")
public class StockAgentSessionBean extends AbstractSessionBean<StockAgentEntity, Long> implements StockAgentLocalBean{
    public StockAgentSessionBean() {
    }

    @Override
    public StockAgentEntity findByDepartmentId(Long departmentId) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("from StockAgentEntity s where s.department.departmentId = :departmentId");
        Query query = entityManager.createQuery(sqlQueryClause.toString());
        query.setParameter("departmentId", departmentId);
        List<StockAgentEntity> entityList = (List<StockAgentEntity>)query.getResultList();
        if(entityList.size() > 0){
            return entityList.get(0);
        }
        return  null;
    }

    @Override
    public Integer countInventoryTotalByAgentId_tdcg(Long departmentId){
        StringBuilder sqlQueryClause = new StringBuilder("select nvl(sum(s.total), 0) from StockAgentEntity s where s.department.departmentId = :departmentId");
        Query query = entityManager.createQuery(sqlQueryClause.toString());
        query.setParameter("departmentId", departmentId);
        return Integer.valueOf(query.getSingleResult().toString());
    }

    @Override
    public Integer countInventoryTotalByAgentId_qStudent(String shopCode, Long giftId) {
        StringBuilder sqlQueryClause = new StringBuilder("select nvl(sum(sg.QUANTITY), 0) from QSV_2015_SHOP_STOCK_GOODS@MKITDW_KM_QSV_DBL sg where upper(sg.SHOP_CODE) = upper(:shopCode) and sg.gift_Id =:giftId ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("shopCode", shopCode);
        query.setParameter("giftId", giftId);
        return Integer.valueOf(query.getSingleResult().toString());
    }

    @Override
    public StockAgentEntity findByShopIdOfDelivererId(Long shopUserId) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("from StockAgentEntity s where exists (select 1 from UserEntity u where u.userId = :shopUserId and u.department.departmentId = s.department.departmentId)");
        Query query = entityManager.createQuery(sqlQueryClause.toString());
        query.setParameter("shopUserId", shopUserId);
        List<StockAgentEntity> entityList = (List<StockAgentEntity>)query.getResultList();
        if(entityList.size() > 0){
            return entityList.get(0);
        }
        return  null;
    }

    @Override
    public Object[] traCuuKhoHangCuaHangMobifoneKhac_tdcg(Integer chiNhanh, Long agentId, Integer trang_thai_kho, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" select sa.stockAgentId, d.departmentId, d.code, d.name, cn.chiNhanh ")
                .append("           , nvl((select sum(sa.total) from Vms_stock_agent sa where sa.agentId = d.departmentId),0) as inventory ")
                .append(" from Vms_department d ")
                .append("   left join Vms_Stock_Agent sa on sa.agentId = d.departmentId ")
                .append("   left join Vms_shopCode_chiNhanh sc on upper(sc.shopcode) = upper(d.code) ")
                .append("   inner join Vms_chiNhanh cn on cn.chiNhanhId = sc.chiNhanhId ")
                .append("   where 1 = 1 ");
        if(chiNhanh != null){
            mainQuery.append(" and cn.chiNhanh = :chiNhanh ");
        }
        if(agentId != null){
            mainQuery.append(" and d.departmentId = :departmentId ");
        }
        if(StringUtils.isNotBlank(sortExpression)){
            mainQuery.append(" order by ").append(sortExpression);
        }else{
            mainQuery.append(" order by cn.chiNhanh ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            mainQuery.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }else{
            mainQuery.append(" asc ");
        }

        StringBuilder sqlQueryClause = new StringBuilder(" select stockAgentId, departmentId, code, name, chiNhanh, case when inventory < 0 then 0 else inventory end as inventory from ( ").append(mainQuery).append(" ) where 1 = 1 ");
        if(trang_thai_kho != null && !trang_thai_kho.equals(-1)){
            if(trang_thai_kho.equals(0)){
                sqlQueryClause.append(" and inventory <= 0 ");
            }else if(trang_thai_kho.equals(1)){
                sqlQueryClause.append(" and inventory > 0 ");
            }
        }
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(chiNhanh != null){
            query.setParameter("chiNhanh", chiNhanh);
        }
        if(agentId != null){
            query.setParameter("departmentId", agentId);
        }
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder("select count(*) from ( ").append(mainQuery).append(" ) ");
        if(trang_thai_kho != null && !trang_thai_kho.equals(-1)){
            if(trang_thai_kho.equals(0)){
                sqlCountClause.append(" and inventory <= 0 ");
            }else if(trang_thai_kho.equals(1)){
                sqlCountClause.append(" and inventory > 0 ");
            }
        }
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(chiNhanh != null){
            countQuery.setParameter("chiNhanh", chiNhanh);
        }
        if(agentId != null){
            countQuery.setParameter("departmentId", agentId);
        }
        Integer count = Integer.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] traCuuKhoHangCuaHangMobifoneKhac_qStudent(Map<String, Object> properties, Long chuongTrinhId, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" from QSV_2015_SHOP_STOCK_GOODS@MKITDW_KM_QSV_DBL sg ")
                .append("   left join VMS_DEPARTMENT d On upper(d.CODE) = upper(sg.SHOP_CODE)  ")
                .append("   left join Vms_shopCode_chiNhanh sc on upper(sc.shopcode) = upper(d.code) ")
                .append("   left join BRANCH_MAPPING bm on bm.CHINHANHID = sc.CHINHANHID ")
                .append("   left join BRANCH@MKITDW_KM_QSV_DBL b on b.BRANCH_ID = bm.BRANCHID ")
                .append("   left join QSV_2015_GIFT@MKITDW_KM_QSV_DBL g on g.GIFT_ID = sg.GIFT_ID ")
                .append("   where 1 = 1 and chuongTrinhId =  ")
                .append(chuongTrinhId);

        if(properties.get("branchId") != null){
            mainQuery.append(" and b.BRANCH_ID = :branchId ");
        }
        if(properties.get("departmentId") != null){
            mainQuery.append(" and d.departmentId = :departmentId ");
        }

        mainQuery.append(" group by b.BRANCH_NAME ,d.code, d.name,g.GIFT_NAME ")
                .append("  order by d.code, d.name,b.BRANCH_NAME,g.GIFT_NAME ");

        StringBuilder sqlQueryClause = new StringBuilder();

        if(properties.get("trang_thai_kho") != null){
            sqlQueryClause.append("  select  ")
                    .append("  departmentCode  ")
                    .append("  ,departmentName ")
                    .append("  ,branchName ")
                    .append("  ,giftName ")
                    .append("  ,inventory  ")
                    .append("   from (");
        }

        sqlQueryClause.append("  select  ")
                .append("  d.code as departmentCode ")
                .append("  ,d.name as departmentName ")
                .append("  ,b.BRANCH_NAME as branchName ")
                .append("  ,g.GIFT_NAME as giftName ")
                .append("  , nvl( sum(sg.QUANTITY),0 ) as inventory  ")
                .append(mainQuery);

        if(properties.get("trang_thai_kho") != null){
            sqlQueryClause.append(" ) where 1 = 1  ");
            if (properties.get("trang_thai_kho").equals(1)) {
                sqlQueryClause.append(" and inventory > 0 ");
            } else {
                sqlQueryClause.append(" and inventory <= 0 ");
            }
        }


        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branchId")!= null){
            query.setParameter("branchId", properties.get("branchId"));
        }
        if(properties.get("departmentId") != null){
            query.setParameter("departmentId", properties.get("departmentId"));
        }
        if(properties.get("departmentId") != null){
            query.setParameter("departmentId", properties.get("departmentId"));
        }
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder("select count(departmentName) from ( ").append(sqlQueryClause).append(" )  ");;

        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branchId")!= null){
            countQuery.setParameter("branchId", properties.get("branchId"));
        }
        if(properties.get("departmentId") != null){
            countQuery.setParameter("departmentId", properties.get("departmentId"));
        }
        if(properties.get("departmentId") != null){
            countQuery.setParameter("departmentId", properties.get("departmentId"));
        }
        Integer count = Integer.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public List baoCaoQuanLyPhieuKhuyenMai_tdcg(Long chiNhanhId, Long departmentId) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select d.departmentid, d.code, d.name, sa.stockAgentId ")
                    .append("           , case when count(sa.stockAgentId) > 0 ")
                    .append("                       then (select nvl(sum(h.quantity), 0) ")
                    .append("                               from Vms_gif_delivery_agent_history h ")
                    .append("                               where h.stockAgentId = sa.stockAgentId) ")
                    .append("                   else 0 end as soLuongNhapKho ")
                    .append("           , case when count(sa.stockAgentId) > 0 ")
                    .append("                       then (select nvl(sum(dth.quantity), 0) ")
                    .append("                               from Vms_gift_delivery_thuebao_his dth ")
                    .append("                               where dth.giftDeliveryThueBaoId in (select dt.giftDeliveryThueBaoId ")
                    .append("                                                                   from Vms_gift_delivery_thuebao dt ")
                    .append("                                                                   where dt.nvGiaoId in (select u.userId ")
                    .append("                                                                                           from Vms_user u where u.departmentId is not null and u.departmentId = d.departmentId))) else 0 end as soLuongPhieuDaDoi ")
                    .append("           , case when count(sa.stockAgentId) > 0 ")
                    .append("                       then nvl(sum(sa.total), 0) else 0 end as ton ")
                    .append("   from Vms_department d ")
                    .append("   left join Vms_stock_agent sa on sa.agentId = d.departmentId ")
                    .append("   inner join Vms_shopCode_chiNhanh sc on upper(sc.shopCode) = upper(d.code) ")
                    .append("   where sc.chiNhanhId = :chiNhanh ");
        if(departmentId != null){
            sqlQueryClause.append(" and d.departmentId = :departmentId ");
        }
        sqlQueryClause.append("   group by d.departmentid, d.code, d.name, sa.stockAgentId ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("chiNhanh", chiNhanhId);
        if(departmentId != null){
            query.setParameter("departmentId", departmentId);
        }
        return query.getResultList();
    }

    @Override
    public List baoCaoQuanLyPhieuKhuyenMai_qStudent(Long branchId, Long chuongTrinhId, Long departmentId) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select SG.SHOP_CODE")
                .append("        , d.name  ")
                .append("        ,G.GIFT_NAME  ")
                .append("        , NVL(SG.QUANTITY, 0) as TON ")
                .append("       , (select count(mp.shop_Code) from QSV_2015_MA_PHIEU@MKITDW_KM_QSV_DBL mp where upper(mp.shop_Code) = upper(sg.shop_Code)) as soPhieuDaDoi ")
                .append("   from QSV_2015_SHOP_STOCK_GOODS@MKITDW_KM_QSV_DBL SG  ")
                .append("   inner join QSV_2015_GIFT@MKITDW_KM_QSV_DBL G on G.GIFT_ID = SG.GIFT_ID  ")
                .append("   inner join vms_department d on upper(d.code) = upper(sg.shop_Code) ")
                .append("   left join VMS_SHOPCODE_CHINHANH sc on upper(sc.SHOPCODE) = upper(sg.shop_Code)  ")
                .append("   left Join BRANCH_MAPPING bm on bm.CHINHANHID = sc.CHINHANHID  ")
                .append("  where 1 = 1 and bm.chuongTrinhId = :chuongTrinh_Id and bm.BranchId = :branch_Id  ");
        if(departmentId != null){
            sqlQueryClause.append(" and d.departmentId = :departmentId ");
        }
        sqlQueryClause.append("   ORDER by d.code, d.name,g.GIFT_NAME  ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("branch_Id", branchId);
        query.setParameter("chuongTrinh_Id", chuongTrinhId);
        if(departmentId != null){
            query.setParameter("departmentId", departmentId);
        }
        return query.getResultList();
    }
}
