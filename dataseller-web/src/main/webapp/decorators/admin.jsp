<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="<c:url value="/themes/admin/images/favicon.png"/>" type="image/png">

    <title><decorator:title/></title>

    <link rel="stylesheet" href="<c:url value="/themes/admin/css/menu_v.1.1.css"/>" />
    <link rel="stylesheet" href="<c:url value="/themes/admin/css/style.default.v_1.1.css"/>" />
    <link rel="stylesheet" href="<c:url value="/themes/admin/css/bootstrap-timepicker.min.css"/>" />
    <link rel="stylesheet" href="<c:url value="/themes/admin/css/jquery.tagsinput.css"/>" />
    <link rel="stylesheet" href="<c:url value="/themes/admin/css/colorpicker.css"/>" />
    <link rel="stylesheet" href="<c:url value="/themes/admin/css/dropzone.css"/>" />
    <link rel="stylesheet" href="<c:url value="/themes/admin/css/dcverticalmegamenu/dcverticalmegamenu_v.1.0.css"/>" />
    <link rel="stylesheet" href="<c:url value="/themes/admin/css/stylePromotion.css"/>" />
    <link rel="stylesheet" href="<c:url value="/themes/admin/css/admin_v.2.10.css"/>" />

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<c:url value='/themes/admin/js/html5shiv.js'/>"></script>
    <script src="<c:url value='/themes/admin/js/respond.min.js'/>"></script>
    <![endif]-->
    <script src="<c:url value="/themes/admin/js/jquery-1.11.1.min.js"/>"></script>
    <script src="<c:url value="/themes/admin/js/hoverIntent/jquery.hoverIntent.minified.js"/>"></script>
    <script src="<c:url value="/themes/admin/js/dcverticalmegamenu/jquery.dcverticalmegamenu.1.3.js"/>"></script>
    <script src="<c:url value="/themes/admin/js/menu.js"/>"></script>
    <script src="<c:url value="/themes/admin/js/bootbox.min.js"/>"></script>
    <script src="<c:url value="/themes/admin/js/jquery.validate.source.v_1.1.js"/>"></script>
    <script src="<c:url value="/scripts/global.source.v_2.0.js"/>"></script>
    <decorator:head/>
</head>
<body>
<!-- Preloader -->
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>


<section>

<div class="leftpanel">

    <div class="logopanel">
        <h1><span>[</span> Mobifone <span>]</span></h1>
    </div><!-- logopanel -->

    <jsp:include page="/common/admin/admin_nav.jsp"></jsp:include>
</div><!-- leftpanel -->

<div class="mainpanel">

    <div class="headerbar">

        <a class="menutoggle"><i class="fa fa-bars"></i></a>



        <div class="header-right">
            <jsp:include page="/common/admin/admin_header.jsp"/>
        </div><!-- header-right -->

    </div><!-- headerbar -->

    <decorator:body/>
</div><!-- mainpanel -->



</section>

<script src="<c:url value="/themes/admin/js/jquery-migrate-1.2.1.min.js"/>"></script>
<script src="<c:url value="/themes/admin/js/jquery-ui-1.10.3.min.js"/>"></script>
<script src="<c:url value="/themes/admin/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/themes/admin/js/modernizr.min.js"/>"></script>
<script src="<c:url value="/themes/admin/js/jquery.sparkline.min.js"/>"></script>
<script src="<c:url value="/themes/admin/js/toggles.min.js"/>"></script>
<script src="<c:url value="/themes/admin/js/retina.min.js"/>"></script>
<script src="<c:url value="/themes/admin/js/jquery.cookies.js"/>"></script>


<script src="<c:url value="/themes/admin/js/jquery.autogrow-textarea.js"/>"></script>
<script src="<c:url value="/themes/admin/js/bootstrap-timepicker.min.js"/>"></script>
<script src="<c:url value="/themes/admin/js/jquery.maskedinput.min.js"/>"></script>
<script src="<c:url value="/themes/admin/js/jquery.tagsinput.min.js"/>"></script>
<script src="<c:url value="/themes/admin/js/jquery.mousewheel.js"/>"></script>
<script src="<c:url value="/themes/admin/js/select2.min.js"/>"></script>
<script src="<c:url value="/themes/admin/js/dropzone.min.js"/>"></script>
<script src="<c:url value="/themes/admin/js/colorpicker.js"/>"></script>
<script src="<c:url value="/themes/admin/js/jquery.formatCurrency-1.4.0.js"/>"></script>


<script src="<c:url value="/themes/admin/js/custom.js"/>"></script>

<script>
    jQuery(document).ready(function(){

        "use strict";

        // Tags Input
        jQuery('#tags').tagsInput({width:'auto'});

        // Select2
        jQuery(".select2").select2({
            width: '100%'
        });

        // Textarea Autogrow
        jQuery('#autoResizeTA').autogrow();

        // Color Picker
        if(jQuery('#colorpicker').length > 0) {
            jQuery('#colorSelector').ColorPicker({
                onShow: function (colpkr) {
                    jQuery(colpkr).fadeIn(500);
                    return false;
                },
                onHide: function (colpkr) {
                    jQuery(colpkr).fadeOut(500);
                    return false;
                },
                onChange: function (hsb, hex, rgb) {
                    jQuery('#colorSelector span').css('backgroundColor', '#' + hex);
                    jQuery('#colorpicker').val('#'+hex);
                }
            });
        }

        // Color Picker Flat Mode
        jQuery('#colorpickerholder').ColorPicker({
            flat: true,
            onChange: function (hsb, hex, rgb) {
                jQuery('#colorpicker3').val('#'+hex);
            }
        });

        // Date Picker
        jQuery('#datepicker').datepicker();

        jQuery('#datepicker-inline').datepicker();

        jQuery('#datepicker-multiple').datepicker({
            numberOfMonths: 3,
            showButtonPanel: true
        });

        // Spinner
        var spinner = jQuery('#spinner').spinner();
        spinner.spinner('value', 0);

        // Input Masks
        jQuery("#date").mask("99/99/9999");
        jQuery("#phone").mask("(999) 999-9999");
        jQuery("#ssn").mask("999-99-9999");

        // Time Picker
        jQuery('#timepicker').timepicker({defaultTIme: false});
        jQuery('#timepicker2').timepicker({showMeridian: false});
        jQuery('#timepicker3').timepicker({minuteStep: 15});


    });
</script>


</body>
</html>
