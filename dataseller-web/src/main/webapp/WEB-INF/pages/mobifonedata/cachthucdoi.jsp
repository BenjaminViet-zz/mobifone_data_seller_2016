<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<body class="q_student_q_teen_2015_home_page">


<section class="content" style="min-height: 800px;">
    <div class="container">
        <div class="row">
            <div class=" col-xs-12">
                <article id="q-student-the-le-chuong-trinh">
                    <!-- InstanceBeginEditable name="Content" -->
                    <div class="text-center"><h2 class="text-red margin-bottom-large text-uppercase"><b>Quy tắc sử dụng data_code</b></h2></div>
                    <p><i class="fa fa-info-circle" aria-hidden="true"></i> Mỗi một data_code chỉ sử dụng cho duy nhất 01 thuê bao, áp dụng cho cả thuê bao trả trước hoặc trả sau, thuê bao có gói Mobile Internet nền hoặc chưa có gói Mobile Internet.</p>
                    <p><i class="fa fa-info-circle" aria-hidden="true"></i> Mỗi một thuê bao chỉ sử dụng duy nhất 01 data_code tại thời điểm đăng ký data_code hoặc đang trong thời gian sử dụng dung lượng miễn phí của data_code.</p>
                    <p><i class="fa fa-info-circle" aria-hidden="true"></i> Không áp dụng nạp data_code đối với thuê bao khóa 1 chiều hoặc 2 chiều tại thời điểm nạp data_code.</p>
                    <p><i class="fa fa-info-circle" aria-hidden="true"></i> Quy tắc sử dụng data_code:</p>
                    <ul class="ul-thele-q-student">
                                <li><p><i class="fa fa-check" aria-hidden="true"></i> Trong thời hạn sử dụng data_code nếu thuê bao hủy gói MI (gói data nền của thuê bao) thì dung lượng miễn phí theo data_code sẽ không được sử dụng, thuê bao sẽ sử dụng dịch vụ data theo nguyên tắc gói M0.</p></li>
                                <li><p><i class="fa fa-check" aria-hidden="true"></i> Data_code có hiệu lực khi thuê bao sử dụng data_code không hủy gói Mobile Internet nền (gói MI thuê bao đang sử dụng) trong vòng 30 ngày trước, kể từ thời điểm thuê bao nhắn tin sử dụng data_code.</p></li>
                    </ul>
                    <p>Nguyên tắc các gói cước data_code: bổ sung các nội dung sau</p>
                    <ul class="ul-thele-q-student">
                        <li><p><i class="fa fa-check" aria-hidden="true"></i> Tên gói: <strong>DataQTxB</strong>: Đối với thuê bao có gói MI trên IN hoặc chua có gói MI sẽ cộng dung lượng data miễn phí theo từng loại data_code vào tài khoản data_voucher, thứ tự trừ cước tài khoản data_voucher trước.</p></li>
                        <li><p><i class="fa fa-check" aria-hidden="true"></i> Tên gói: <strong>DataQTyA</strong>: Đối với thuê bao có gói MI, YT, FB trên PCRF quản lý thì khi thuê bao đắng ký data_code sẽ tạo thành gói song song với gói MI hiện có, ưu tiên trừ gói cước theo data_code trước các gói cước khác.</p></li>
                    </ul>
                    <h5 class="padding-left-larger"><u>Trong đó:</u></h5>
                    <ul class="ul-thele-q-student padding-left-larger">
                        <li><i class="fa fa-check" aria-hidden="true"></i> <strong>DataQT</strong>: tiền tố bắt buộc</li>
                        <li><i class="fa fa-check" aria-hidden="true"></i> <strong>x, y</strong>: số hàng nghìn theo giá từng gói</li>
                        <li><i class="fa fa-check" aria-hidden="true"></i> <strong>B</strong>: Các gói trên IN; A: Các gói trên PCRF</li>
                    </ul>

                    <h5 class="text-red padding-round-small display-inline text-uppercase">Câu lệnh sử dụng data_code và kiểm tra dung lượng data:</h5>
                    <p><i class="fa fa-info-circle" aria-hidden="true"></i> Đăng ký: DK_QT_<12 số data_code> gửi 999, dấu “_” là dấu cách</p>
                    <ul class="ul-thele-q-student">
                        <li><i class="fa fa-check" aria-hidden="true"></i> Nội dung tin nhắn thông báo: <i>“Quy Khach duoc cong them x MB toc do cao (su dung tai VN), thoi gian su dung den dd/mm/yyyy. Chi tiet lien he 9090”</i>, trong đó x MB tương ứng số MB miễn phí được quy định theo từng data_code nêu trên.</li>
                    </ul>
                    <p><i class="fa fa-info-circle" aria-hidden="true"></i> Kiểm tra dung lượng: KT_DataQT gửi 999</p>
                    <ul class="ul-thele-q-student">
                        <li><i class="fa fa-check" aria-hidden="true"></i> Nội dung tin nhắn thông báo: <i>“Quy Khach con lai x MB toc do cao data qua tang (su dung tai VN), thoi gian su dung den dd/mm/yyyy. Chi tiet lien he 9090”</i>, trong đó x MB tương ứng số MB miễn phí còn lại.</li>
                    </ul>

                    <p><i class="fa fa-info-circle" aria-hidden="true"></i> Cước gửi 999: 200 đồng/SMS (đã bao gồm VAT)</p>
                    <ul class="ul-thele-q-student">
                        <li><i class="fa fa-check" aria-hidden="true"></i> Các trường hợp nhắn sai mã data_code: Nội dung tin nhắn thông báo: <i>“Quy khach vui long kiem tra lai ma data_code. Chi tiet lien he 9090.”</i></li>
                        <li><i class="fa fa-check" aria-hidden="true"></i> Cảnh báo hạn mức sử dụng đối với tài khoản data_voucher: <i>“Dung luong data cong them cua Quy Khach chi con lai 10MB, han su dung den hh:mm:ss, dd/mm/yyyy.”</i></li>
                    </ul>

                    <!-- InstanceEndEditable -->
                </article>
            </div>
        </div>
    </div>
</section>
<script type="text/javascript">
    $(document).ready(function(){
        highlightFocusPage('#howToExchangePage');
    });
</script>
</body>