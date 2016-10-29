package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.utils.Config;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/9/14
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class HomeController extends ApplicationObjectSupport {

    @RequestMapping(value={"/", "/index*", "index.html", "/chuong-trinh.html", "/home.html"})
    public ModelAndView home(){
        ModelAndView mav = null;
        String chuong_trinh = Config.getInstance().getProperty("promotion_type_configure");
        switch (chuong_trinh){
            case "THUEBAO_PTM":
                mav = new ModelAndView("chuongtrinh_thuebao_ptm_q_student");
                break;
            default:
                mav = new ModelAndView("chuongtrinh");
                break;
        }
        return mav;
    }

    @RequestMapping(value = {"/dasboard.html"})
    public ModelAndView checkLogin(){
        return new ModelAndView("/logon");
    }

    @RequestMapping("/tich-diem-cuoc-goi-nhan-voucher/trang-chu.html")
    public ModelAndView trangChuKHCN(){
        return new ModelAndView("trangchu");
    }

    @RequestMapping(value="/tich-diem-cuoc-goi-nhan-voucher/quay-so.html")
    public ModelAndView quaySo(){
        ModelAndView modelAndView = new ModelAndView("quayso");
        return modelAndView;
    }

    @RequestMapping(value="/tich-diem-cuoc-goi-nhan-voucher/cach-thuc-doi-qua.html")
    public ModelAndView cachThucDoi(){
        ModelAndView modelAndView = new ModelAndView("cachthucdoi");
        return modelAndView;
    }

    @RequestMapping(value="/tich-diem-cuoc-goi-nhan-voucher/dia-diem-doi-qua.html")
    public ModelAndView diaDiemDoi(){
        ModelAndView modelAndView = new ModelAndView("diadiemdoi");
        return modelAndView;
    }

    @RequestMapping(value="/tich-diem-cuoc-goi-nhan-voucher/the-le-chuong-trinh.html")
    public ModelAndView theLeChuongTrinh(){
        ModelAndView modelAndView = new ModelAndView("thelechuongtrinh");
        return modelAndView;
    }

    @RequestMapping(value = "/thuebaophattrienmoi/kenhphanphoi/trang-chu.html")
    public ModelAndView chuongTrinhPTM_thueBao_2015(){
        return new ModelAndView("/2015/thuebaophattrienmoi/kenhphanphoi/trangchu");
    }

    @RequestMapping(value = "/q-teen-q-student/trang-chu.html")
    public ModelAndView chuongTrinhQ_student(){
        return new ModelAndView("/2015/q_student_and_q_teen/trangchu");
    }

    @RequestMapping(value = "/q-teen-q-student/the-le-chuong-trinh.html")
    public ModelAndView theLeChuongTrinh_Q_Student(){
        return new ModelAndView("/2015/q_student_and_q_teen/thelechuongtrinh");
    }
    @RequestMapping(value = "/q-teen-q-student/cach-thuc-doi-qua.html")
    public ModelAndView cachThucDoiQua_Q_Student(){
        return new ModelAndView("/2015/q_student_and_q_teen/cachthucdoi");
    }

    @RequestMapping(value = "/q-teen-q-student/dia-diem-doi-qua.html")
    public ModelAndView điaiemDoiQua_Q_Student(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/2015/q_student_and_q_teen/diadiemdoi");
        mav.addObject("departmentList", new ArrayList<>());

        return mav;
    }
}