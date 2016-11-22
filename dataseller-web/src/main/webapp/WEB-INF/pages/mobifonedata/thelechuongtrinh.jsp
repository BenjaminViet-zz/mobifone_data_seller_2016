<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<link href="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">

<style>
    #tableList2.mobile tbody tr td:nth-of-type(1){
        width: 45px;
    }
    #tableList2.mobile tbody tr td:nth-of-type(2){
        width: 147px;
    }
    #tableList2.mobile tbody tr td:nth-of-type(3){
        width: 106px;
    }
    #tableList2.mobile tbody tr td:nth-of-type(4){
        width: 340px;
    }
    #tableList2.mobile tbody tr td:nth-of-type(5){
        width: 185px;
    }
    #tableList2.mobile tbody tr td:nth-of-type(6){
        width: 145px;
    }
    #tableList2.mobile tbody tr td:nth-of-type(7){
        width: 56px;
    }
</style>

<body class="q_student_q_teen_2015_home_page">
<section class="content">
    <div class="container">
        <div class="row">
            <div class=" col-xs-12">
                <article id="q-student-the-le-chuong-trinh">
                    <!-- InstanceBeginEditable name="Content" -->
                    <div class="text-center"><h2 class="text-red margin-bottom-large text-uppercase"><b>Nội dung chương trình</b></h2></div>
                    <h5 class="text-red padding-round-small display-inline text-uppercase">Nội dung gói cước bán buôn Data:</h5>
                    <p><i class="fa fa-info-circle" aria-hidden="true"></i> Nội dung gói cước:</p>
                    <table class="table">
                        <thead>
                        <tr>
                            <th class="text-center">STT</th>
                            <th class="text-center">Nội dung</th>
                            <th class="text-center">Mô tả</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="text-center">1</td>
                            <td>Mã gói cước</td>
                            <td class="text-center">BB_Data</td>
                        </tr>
                        <tr>
                            <td rowspan="2" class="text-center">2</td>
                            <td>Giá gói cước (đồng) đã gồm thuế GTGT</td>
                            <td class="text-center">100.000.000</td>
                        </tr>
                        <tr>
                            <td>Số lượng data_code</td>
                            <td class="text-center">(*)</td>
                        </tr>
                        </tbody>
                    </table>
                    <p><i class="fa fa-info-circle" aria-hidden="true"></i> Lưu ý: (*)Tùy thuộc vào số lượng data_code cung cấp cho từng Doanh nghiệp theo các loại mệnh giá.</p>
                    <p><i class="fa fa-info-circle" aria-hidden="true"></i> Một số quy định khi đăng ký gói cước:</p>
                    <ul class="ul-thele-q-student">
                        <li><p><i class="fa fa-check" aria-hidden="true"></i> Doanh nghiệp khi mua gói cước sẽ được đăng ký vào thuê bao VAS của doanh nghiệp.</p></li>
                        <li><p><i class="fa fa-check" aria-hidden="true"></i> Gói cước được trừ 01 lần vào tháng đăng ký và không gia hạn tự động</p></li>
                        <li><p><i class="fa fa-check" aria-hidden="true"></i> Doanh nghiệp có thể mua 01 hoặc nhiều gói cước BB_Data trong cùng 01 chu kỳ cước.</p></li>
                        <li><p><i class="fa fa-check" aria-hidden="true"></i> Gói cước chỉ được thực hiện đăng ký tại cửa hàng trên form đấu nối, không đăng ký bằng SMS, USSD, web/wap;</p></li>
                    </ul>

                    <h5 class="text-red padding-round-small display-inline text-uppercase">Data_code:</h5>
                    <p><i class="fa fa-info-circle" aria-hidden="true"></i> Được cung cấp cho khách hà`ng dưới dạng file excel có bảo mật, trong đó:</p>
                    <ul class="ul-thele-q-student">
                        <li><p><i class="fa fa-check" aria-hidden="true"></i> Serial data_code: gồm 12 chữ số;</p></li>
                        <li><p><i class="fa fa-check" aria-hidden="true"></i> Mã bảo mật: 12 chữ số được mã hóa bằng thuật toán SHA 256 trước khi cập nhật vào hệ thống quản lý mã thẻ.</p></li>
                    </ul>

                    <p><i class="fa fa-info-circle" aria-hidden="true"></i> Hiệu lực sử dụng của data_code: không quá 60 ngày kể từ khi danh sách data_code  được cập nhật vào hệ thống quản lý mã thẻ.</p>
                    <p><i class="fa fa-info-circle" aria-hidden="true"></i> Các mệnh giá, dung lượng và thời hạn sử dụng data_code bao gồm:</p>
                    <ul class="ul-thele-q-student">
                        <li><p><i class="fa fa-check" aria-hidden="true"></i> <b>Các gói khai trên IN</b>:</p></li>
                    </ul>

                    <div id="tableListContainer" style="width: 100%;">
                        <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                       partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                       id="tableList" pagesize="${items.maxPageItems}" export="false"
                                       class="table text-center" style="margin: 1em 0 1.5em; width: 1024px;">

                            <display:column headerClass="table_header text-center" titleKey="label.stt" class="text-center" style="width: 49px;" >
                                ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                            </display:column>
                            <display:column headerClass="table_header text-center" property="name" sortName="name" class="text-center" titleKey="packagedata.label.tenGoiCuoc" style="width: 170px;"/>
                            <display:column headerClass="table_header text-center" sortName="value" class="text-center" titleKey="packagedata.label.giaGoiCuoc" style="width: 125px;">
                                <fmt:formatNumber var="value" type='number' maxFractionDigits='0' value='${tableList.value}' />
                                ${fn:replace(value, ',', '.')}
                            </display:column>
                            <display:column headerClass="table_header text-center breakTwoLines" property="volume" class="text-center" titleKey="packagedata.label.dungLuongMienPhi" style="width: 300px;" >

                            </display:column>
                            <display:column headerClass="table_header text-center" property="durationText" class="text-center" titleKey="packagedata.label.thoiGianSuDung" style="width: 155px;" />
                            <display:column headerClass="table_header text-center" property="numberOfExtend" class="text-center" titleKey="packagedata.label.soLanGiaHan" style="width: 155px;" />
                            <display:column headerClass="table_header text-center" property="tk" class="text-center" titleKey="packagedata.label.tk" style="width: 70px;" />
                        </display:table>
                    </div>


                    <ul class="ul-thele-q-student">
                        <li><p><i class="fa fa-check" aria-hidden="true"></i> <b>Các gói khai trên PCRF</b>:</p></li>
                    </ul>

                    <div id="tableListContainer2" style="width: 100%;">
                        <table id="tableList2" class="table text-center" style="width: 1024px;">
                            <thead>
                            <tr>
                                <th class="text-center">TT</th>
                                <th class="text-center">Tên gói</th>
                                <th class="text-center">Giá gói(đ)</th>
                                <th class="text-center">DL miễn phí (MB)</th>
                                <th class="text-center">Thời gian sử dụng</th>
                                <th class="text-center">Số lần gia hạn</th>
                                <th class="text-center">TK</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>1</td>
                                <td>DataQT1A</td>
                                <td>1.000</td>
                                <td>100MB</td>
                                <td>01 ngày</td>
                                <td>0</td>
                                <td>N/A</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>DataQT3A</td>
                                <td>3.000</td>
                                <td>300MB</td>
                                <td>01 ngày</td>
                                <td>0</td>
                                <td>N/A</td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>DataQT5A</td>
                                <td>5.000</td>
                                <td>500MB</td>
                                <td>01 ngày</td>
                                <td>0</td>
                                <td>N/A</td>
                            </tr>
                            <tr>
                                <td>4</td>
                                <td>DataQT8A</td>
                                <td>8.000</td>
                                <td>600MB</td>
                                <td>03 ngày</td>
                                <td>0</td>
                                <td>N/A</td>
                            </tr>
                            <tr>
                                <td>5</td>
                                <td>DataQT10A</td>
                                <td>10.000</td>
                                <td>1000MB</td>
                                <td>10 ngày</td>
                                <td>0</td>
                                <td>N/A</td>
                            </tr>
                            <tr>
                                <td>6</td>
                                <td>DataQT50A</td>
                                <td>50.000</td>
                                <td>1500MB</td>
                                <td>31 ngày</td>
                                <td>0</td>
                                <td>N/A</td>
                            </tr>
                            <tr>
                                <td>7</td>
                                <td>DataQT70A</td>
                                <td>70.000</td>
                                <td>2000MB</td>
                                <td>31 ngày</td>
                                <td>0</td>
                                <td>N/A</td>
                            </tr>
                            <tr>
                                <td>8</td>
                                <td>DataQT35A(*)</td>
                                <td>35.000</td>
                                <td>1.536MB truy cập IP ngoài Facebook<span class="line_separator"></span>Miễn phí truy cập IP Facebook</td>
                                <td>31 ngày</td>
                                <td>11</td>
                                <td>N/A</td>
                            </tr>

                            </tbody>
                        </table>
                    </div>
                     <!-- InstanceEndEditable -->
                </article>
            </div>
        </div>
    </div>
</section>

<script src="<c:url value="/themes/admin/js/jquery.mask.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script type="text/javascript">
    $(document).ready(function(){


        /*--------------------------------*/
        /* background-attachment fixed IE */
        /*--------------------------------*/

        $('body').on("mousewheel", function () {
            event.preventDefault();

            var wheelDelta = event.wheelDelta;

            var currentScrollPosition = window.pageYOffset;
            window.scrollTo(0, currentScrollPosition - wheelDelta);
        });

        /*--------------------------------*/
        /* //background-attachment fixed IE */
        /*--------------------------------*/



        function replaceSpecialCharacter() {
            var str = $('#tableList').text();
            var textToReplace = '{delimiter_line}';
            var replaceWith = '<span class="line_separator"></span>';
            if (str.indexOf(textToReplace) == -1) {

            } else {
                $("#tableList td:contains(" + textToReplace + ")").addClass('breakTwoLines');
                var newString = $('td.breakTwoLines').text();
                var result = newString.replace(textToReplace, replaceWith);
                $('td.breakTwoLines').html(result);
            }
        }
        replaceSpecialCharacter();
        initScrollablePane();
    });

    function initScrollablePane(){
        if($(window).width() > min_desktop_screen_width){
            return;
        }

        var $tableContainer = $('#tableListContainer');
        $tableContainer.height($tableContainer.height() + 50);
        $tableContainer.mCustomScrollbar({axis:"x"});

        var $tableContainer2 = $('#tableListContainer2');
        $('#tableList2').addClass('mobile');
        $tableContainer2.height($tableContainer2.height() + 50);
        $tableContainer2.mCustomScrollbar({axis:"x"});
    }
</script>
</body>