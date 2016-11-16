// Show the document's title on the status bar
window.defaultStatus=document.title;
var mobile_screen_width = 768;
var min_desktop_screen_width = 1024;

/**
 * Remove white spaces before submitting the Form.
 * @param formName
 */
function trimAndSubmitForm(formName) {
    $(formName).find('input:text').each(function(){
        var orgValue = $(this).val();
        $(this).val($.trim(orgValue));
    });
    $(formName).validate({
        errorPlacement: function(error, element) {},
        invalidHandler: function(event, validator) {
            bootbox.alert("Warning", validator.errorList[0].message);
        }
    });
    $(formName).submit();
}

function validateDateFormat(inputId, field) {
    if (!isDate($("#" + inputId).val(), inputId)) {
        bootbox.alert("Error", field + ": Invalid date format");
        return false;
    }
    return true;
}

$(document).ready(function() {
    try{
        $('select').not('.notAutoInitSelect2').select2();

        $('.prevent_type').keydown(function(event){
            if (event.keyCode >= 0){
                event.preventDefault();
                return false;
            }
        });

        $('.onlyNumberInputForceValue').keyup(function(){
            $(this).val(function(el,value){
                value = value.replace(/^(0+)/, '');
                return value.replace(/[^\d]/g,'');
            });
        });

        // form validator
        jQuery.validator.addClassRules('required', {
            required: true
        });

        jQuery.validator.addClassRules('required-email', {
            required: true,
            email: true
        });

        jQuery.validator.addClassRules('no-special-character', {
            notSpecialCharacter: true
        });

        jQuery.validator.addClassRules('hoa-don', {
            required: true,
            hoadon: true
        });

        jQuery.validator.addMethod("hoa-don", function(value, element) {
            return value != null && value !=undefined && value != "";
        }, "Nhập số hóa đơn hoặc 0.");


        jQuery.validator.addMethod("number", function(value, element) {
            var nameRegex = /^[0-9.]+$/;
            return nameRegex.test(value);
        }, "Chỉ nhập số.");

        jQuery.validator.addMethod("notSpecialCharacter", function(value, element) {
            var nameRegex = /^[0-9a-zA-Z_-]+$/;
            return nameRegex.test(value);
        }, "Value contains illegal characters.");

        $("form").each(function(){
            if($(this).attr("validate") != null && $(this).attr("validate") != undefined){

                $(this).validate({
                    errorClass: "error-inline-validate",
                    errorElement: "span",
                    ignore: "",
                    errorPlacement: function(error, element) {
                        var parentEl = $(element).parent();
                        if(parentEl.find('span.add-on-attach').eq(0).length){
                            var left = $(parentEl).find('input').eq(0).outerWidth();
                            $(error).css('position', 'absolute');
                            $(error).css('left', left + 70);
                            $(error).css('top', 6);
                        }
                        $(parentEl).append(error);
                    },
                    highlight:function(element, errorClass, validClass) {
                        if($(element).hasClass('required') || $(element).hasClass('required-email') || $(element).hasClass('customRequired')) {
                            $(element).closest('.control-group').removeClass('success').addClass('error');
                        }
                    },
                    unhighlight: function(element, errorClass, validClass) {
                        if($(element).hasClass('required') || $(element).hasClass('required-email') || $(element).hasClass('customRequired')) {
                            $(element).closest('.control-group').removeClass('error').addClass('success');
                        }
                    },
                    success: function(element) {
                        var itemId = element.attr('for');
                        if($("[id='" + itemId + "']").hasClass('required') || $("[id='" + itemId + "']").hasClass('required-email')
                            || $("[id='" + itemId + "']").hasClass('customRequired')) {
                            var parentEl = $(element).parent();
                            if(parentEl.find('span.add-on-attach').eq(0).length){
                                var left = $(parentEl).find('input').eq(0).outerWidth();
                                $(element).css('position', 'absolute');
                                $(element).css('left', left + 70);
                                $(element).css('top', 6);
                            }
                            element.text('OK!').addClass('valid').closest('.control-group').removeClass('error').addClass('success');
                        }else {
                            element.remove();
                        }
                    }
                });
            }

        });
    }catch(err) { }

    //form validator
    try{
        jQuery.validator.addClassRules('required', {
            required: true
        });

        jQuery.validator.addClassRules('required-email', {
            required: true,
            email: true
        });

        jQuery.validator.addClassRules('no-special-character', {
            notSpecialCharacter: true
        });

        jQuery.validator.addClassRules('hoa-don', {
            required: true,
            hoadon: true
        });

        jQuery.validator.addMethod("hoa-don", function(value, element) {
            return value != null && value !=undefined && value != "";
        }, "Nhập số hóa đơn hoặc 0.");


        jQuery.validator.addMethod("number", function(value, element) {
            var nameRegex = /^[0-9.]+$/;
            return nameRegex.test(value);
        }, "Chỉ nhập số.");

        jQuery.validator.addMethod("notSpecialCharacter", function(value, element) {
            var nameRegex = /^[0-9a-zA-Z_-]+$/;
            return nameRegex.test(value);
        }, "Value contains illegal characters.");

        $("form").each(function(){
            if($(this).attr("novalidate") != null && $(this).attr("novalidate") != undefined){

                $(this).validate({
                    errorClass: "error-inline-validate",
                    errorElement: "span",
                    ignore: "",
                    highlight:function(element, errorClass, validClass) {
                        if($(element).hasClass('required') || $(element).hasClass('required-email') || $(element).hasClass('customRequired')) {
                            $(element).closest('.control-group').removeClass('success').addClass('error');
                        }
                    },
                    unhighlight: function(element, errorClass, validClass) {
                        if($(element).hasClass('required') || $(element).hasClass('required-email') || $(element).hasClass('customRequired')) {
                            $(element).closest('.control-group').removeClass('error').addClass('success');
                        }
                    },
                    success: function(element) {
                        var itemId = element.attr('for');
                        if($("[id='" + itemId + "']").hasClass('required') || $("[id='" + itemId + "']").hasClass('required-email')
                                || $("[id='" + itemId + "']").hasClass('customRequired')) {
                            element.text('OK!').addClass('valid').closest('.control-group').removeClass('error').addClass('success');
                        }else {
                            element.remove();
                        }
                    }
                });
            }

        });
    } catch(error){}

    $('.not_allow_input').keydown(function(event){
        event.preventDefault();
    });

    $("#btnShowHidePassword").click(function () {
        var $el = $(this),
            $input = $el.parent().find('input').eq(0);
        var className = $input.attr('class');
        if ($el.html() == "Xem") {
            $el.html("Ẩn");
            var c = $("#password").val();
            $("#password").remove();
            $("<input type='text' class='" + className + "' name='pojo.password' id='password'>").val(c).attr("style", "width:160px").insertBefore("#btnShowHidePassword")
        } else {
            $el.html("Xem");
            var c = $("#password").val();
            $("#password").remove();
            $("<input type='password' class='" + className + "' name='pojo.password' id='password'>").val(c).attr("style", "width:160px").insertBefore("#btnShowHidePassword")
        }
    });

    $('.onlyNumberInputForceValue').keyup(function(){
        $(this).val(function(el,value){
            return value.replace(/[^\d]/g,'');
        });
    });

    try{
        $('.popup_menu').dcVerticalMegaMenu({
            rowItems: '3',
            speed: 'slow',
            effect: 'slide',
            direction: 'right'
        });
    }catch (error){}

    $('.nav-parent').on('click', function(){
        checkResizeHeightOfParentPopup(this);
    });





    /*-------------------------------------*/
    /* Scroll TOP*/
    /*-------------------------------------*/
    $(window).scroll(function(){
        if ( $(this).scrollTop() > 100 ){
            $('.scrollTop').fadeIn();
        } else {
            $('.scrollTop').fadeOut();
        }
    });

    // Trigger scroll to top
    $('.scrollTop').on('click', function(){
        $('html, body').animate({scrollTop: 0}, 800);
        return false;
    });

    /*-------------------------------------*/
    /* Scroll TOP*/
    /*-------------------------------------*/
});

function checkResizeHeightOfParentPopup(el){
    if($(el).closest('.sub-container').length){
        setTimeout(function(){
            var anchorMegaEl = $(el).closest('.popup_menu');
            $(anchorMegaEl).find('.sub-container').height($(anchorMegaEl).find('ul').eq(0).outerHeight());
        }, 300);
    }
}

function jQueryMask(){
    $('.date').mask('00/00/0000');
    $('.time').mask('00:00:00');
    $('.date_time').mask('00/00/0000 00:00:00');
    $('.cep').mask('00000-000');
    $('.phone').mask('0000-0000');
    $('.phone_with_ddd').mask('(00) 0000-0000');
    $('.phone_us').mask('(000) 000-0000');
    $('.mixed').mask('AAA 000-S0S');
    $('.cpf').mask('000.000.000-00', {reverse: true});
    $('.cnpj').mask('00.000.000/0000-00', {reverse: true});
    $('.money').mask('000,000,000,000,000,000,000,000,000', {reverse: true});
    $('.money2').mask("#.##0,00", {reverse: true});
    $('.ip_address').mask('0ZZ.0ZZ.0ZZ.0ZZ', {
        translation: {
            'Z': {
                pattern: /[0-9]/, optional: true
            }
        }
    });
    $('.ip_address').mask('099.099.099.099');
    $('.percent').mask('##0,00%', {reverse: true});
    $('.clear-if-not-match').mask("00/00/0000", {clearIfNotMatch: true});
    $('.placeholder').mask("00/00/0000", {placeholder: "__/__/____"});
    $('.fallback').mask("00r00r0000", {
        translation: {
            'r': {
                pattern: /[\/]/,
                fallback: '/'
            },
            placeholder: "__/__/____"
        }
    });
    $('.selectonfocus').mask("00/00/0000", {selectOnFocus: true});
}

function jQueryMaskNonThousandFormat(){
    $('.money').mask('00000000000000000000000', {reverse: true});
}

function selectFirstItemSelect2(selectEl){
    $(selectEl).val($(selectEl).find('option:eq(0)').val()).trigger('change');
}

function setSelectedValueForSelectMenu(select2Id, valueOfOption){
    $(select2Id).select2('val', valueOfOption);
}


function forceNumericInputsOnly(elementInput){
    $(elementInput).keydown(function(event){
        // Ensure that it is a number and stop the keypress
        if (event.keyCode >= 65 && event.keyCode <= 90) {
            event.preventDefault();
        }
    });
}

function displayError4El(el, message){
    $(el).closest("div[class*='controls']").find("span[class*='error-inline-validate']").each(function(index1, el1){
        $(el1).remove();
    });
    if(message != ''){
        $(el).closest("div[class*='control-group']").addClass('error');
        $(el).closest("div[class*='controls']").append("<span class='error-inline-validate' for='" +$(el).attr('id')+ "' generated='true'>" +message+ "</span>");
    }else{
        $(el).closest("div[class*='control-group']").addClass('success');
        $(el).closest("div[class*='controls']").append("<span class='error-inline-validate valid' for='" +$(el).attr('id')+ "' generated='true'></span>");
    }
}

function goBack(){
    document.location = document.referrer;
}

function validateRequired(element){
    if($(element).closest('form').length && typeof $(element).closest('form').attr('novalidate') != 'undefined'){
        $(element).closest('form').validate().element(element);
    }
}

function highlightFocusPage(idTab){
    $('#navigation-header-tab li').each(function(index, el){
        $(el).removeClass('active');
    });
    $(idTab).addClass('active');
}

function highlightKppFocusPage(idTab){
    $('#kpp_navigation_bar li').each(function(index, el){
        $(el).removeClass('active');
    });
    $(idTab).addClass('active');
}

function checkAllIfOne(formId, listName, thisStatus, checkboxAll){
    var allCheck = document.getElementById(formId).elements[checkboxAll];
    if(!thisStatus.checked){	//uncheck one  checkbox
        allCheck.checked = false;
        allCheck.parentNode.className = allCheck.parentNode.className.replace('checked', '');
    }else{	//check one  checkbox
        //var checkList = document.getElementsByName(listName);
        var checkList = document.getElementById(formId).elements[listName];
        var listSize = checkList.length;
        if(listSize == undefined) {
            if(checkList.checked) {
                allCheck.checked = true;
                allCheck.parentNode.className = allCheck.parentNode.className + " checked";
            }

        } else {
            if(listSize > 0){ //Have one or more elements
                for(i = 0;i < listSize; i++)
                    if(!checkList[i].checked)
                        return false;
                allCheck.checked = true;
                allCheck.parentNode.className = allCheck.parentNode.className + " checked";
            }
        }

    }
}

function checkAll(formId, listName, thisStatus){
    var list = document.getElementById(formId).elements[listName];
    if(list != null){
        var listSize = list.length;
        if(listSize == undefined) {
            if (!list.disabled) {
                if (thisStatus.checked) {
                    list.checked = true;
                    list.parentNode.className = list.parentNode.className + ' checked';
                }else{
                    list.checked = false;
                    list.parentNode.className = list.parentNode.className.replace('checked', '');
                }
            }
        } else {
            if(listSize > 1) { //Have one or more element
                for(i = 0; i < listSize; i++) {
                    if (!list[i].disabled) {
                        if (thisStatus.checked) {
                            list[i].checked = true;
                            list[i].parentNode.className = list[i].parentNode.className + ' checked';
                        }else{
                            list[i].checked = false;
                            list[i].parentNode.className = list[i].parentNode.className.replace('checked', '');
                        }
                    }
                }
            }
        }
    }
}

function validatePhoneNumber(phoneNumber){
    if($.trim(phoneNumber) != ''){
        var reg = new RegExp("^0{1}\\d{9,10}$");
        return reg.test(phoneNumber);
    }
    return true;
}

function validateIsNumber(number){
    if($.trim(number) != ''){
        var reg = new RegExp('^[0-9]+$');
        return reg.test(number);
    }
    return true;
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length,c.length);
    }
    return "";
}

function setActiveMenu4Admin(menuTab){
    $(menuTab).closest('.mega-unit').addClass('selected');
    $(menuTab).closest('.popup_menu').addClass('selected');
    $(menuTab).addClass('active-tab');
}

function initDatePickerWithMinAndMaxDate(datePickerEl, startDate, endDate){
    $(datePickerEl).datepicker({
        dateFormat: 'dd/mm/yy',
        minDate: startDate,
        maxDate: endDate})
}

function currencyFormat4El(element){
    if($(element).is('input')){
        $(element).formatCurrency({ roundToDecimalPlace: 2, eventOnDecimalsEntered: true });
    }else{
        $(element).formatCurrency({ roundToDecimalPlace: 2, eventOnDecimalsEntered: true });
    }
}

(function ( $ ) {
    $.fn.showLoading = function() {
        var loadingEl = $("<div class='loading_ajax'><span class='content'>Đang tải dữ liệu. Vui lòng đợi...</span></div>");
        $(loadingEl).height($(document).outerHeight());
        $(loadingEl).width($(document).outerWidth());
        $(this).append(loadingEl);
        $(loadingEl).find('span').css('top', (screen.height / 2) - 100);
    };
    $.fn.removeLoading = function() {
        $(this).find('.loading_ajax').remove();
    };
}( jQuery ));