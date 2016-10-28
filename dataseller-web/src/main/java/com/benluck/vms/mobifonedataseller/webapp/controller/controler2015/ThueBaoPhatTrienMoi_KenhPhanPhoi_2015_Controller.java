package com.benluck.vms.mobifonedataseller.webapp.controller.controler2015;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 2/10/15
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ThueBaoPhatTrienMoi_KenhPhanPhoi_2015_Controller extends ApplicationObjectSupport{
    private Log log = LogFactory.getLog(ThueBaoPhatTrienMoi_KenhPhanPhoi_2015_Controller.class);

    @RequestMapping("/thuebaophattrienmoi/kenhphanphoi/cach-thuc-nhan-thuong.html")
    public ModelAndView cachThucNhanThuong(){
        return new ModelAndView("/2015/thuebaophattrienmoi/kenhphanphoi/cachthucnhanthuong");
    }

    @RequestMapping("/thuebaophattrienmoi/kenhphanphoi/the-le-chuong-trinh.html")
    public ModelAndView theLeChuongTrinh(){
        return new ModelAndView("/2015/thuebaophattrienmoi/kenhphanphoi/thelechuongtrinh");
    }
}
