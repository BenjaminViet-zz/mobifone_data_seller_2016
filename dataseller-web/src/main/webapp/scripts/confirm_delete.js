<script type="text/javascript">
	function doDeleteItem(actionFuncName, args) {
		bootbox.confirm('<fmt:message key="admin.confirm.delete.title"/>', '<fmt:message key="admin.confirm.deleteone.message"/>',  function(r) {
			if( r ) {
				//Create the function
				var fn = window[actionFuncName];
				//Call the function
				fn(args);
			}
		});
	}
	function doDeleteItems(actionFuncName, args) {
		bootbox.confirm('<fmt:message key="admin.confirm.delete.title"/>', '<fmt:message key="admin.confirm.deletemulti.message"/>', function(r) {
			if( r ) {
				//Create the function
				var fn = window[actionFuncName];
				//Call the function
				fn(args);
			}
		});
	}
	function doDeleteItemByHref(href) {
        bootbox.confirm('<fmt:message key="admin.confirm.delete.title"/>', '<fmt:message key="admin.confirm.deleteone.message"/>', function(r) {
			if( r ) {
				location.href = href;
			}
		});
	}
	function showChoiceWarning() {
		bootbox.alert('<fmt:message key="icon.warning"/>', '<fmt:message key="admin.warning.delete.empty.message"/>');
	}
	function showWarningMsg(msg) {
        bootbox.alert('<fmt:message key="icon.warning"/>', msg);
	}
	function showAlertMsg(title, msg) {
        bootbox.alert(title, msg);
	}
	function showGeneralExMsg() {
		jAlert('<fmt:message key="general.exception.msg"/>', '<fmt:message key="icon.error"/>');
	}
	function doActiveItem(actionFuncName, args) {
		bootbox.confirm('<fmt:message key="admin.confirm.activeuser.title"/>', '<fmt:message key="admin.confirm.activeuser.message"/>', function(r) {
			if( r ) {
				//Create the function
				var fn = window[actionFuncName];
				//Call the function
				fn(args);
			}
		});
	}
	function doActiveItemByHref(href) {
		bootbox.confirm('<fmt:message key="admin.confirm.activeuser.title"/>', '<fmt:message key="admin.confirm.activeuser.message"/>', function(r) {
			if( r ) {
				location.href = href;
			}
		});
	}
    function doDeleteItemDistrictAdminByHref(href, firstName, lastName) {
        bootbox.confirm('<fmt:message key="admin.confirm.delete.district.admin.title"/>', '<fmt:message key="admin.confirm.delete.district.admin.message"/>' + ' ' + firstName + ' ' + lastName + '\'s account?', function(r) {
            if( r ) {
                location.href = href;
            }
        });
    }
	function doDisableItem(actionFuncName, args) {
		bootbox.confirm('<fmt:message key="admin.confirm.disable.title"/>', '<fmt:message key="admin.confirm.disableone.message"/>', function(r) {
			if( r ) {
				//Create the function
				var fn = window[actionFuncName];
				//Call the function
				fn(args);
			}
		});
	}
    function doDisableItemByHref(href) {
        bootbox.confirm('<fmt:message key="admin.confirm.disable.title"/>', '<fmt:message key="admin.confirm.disableone.message"/>', function(r) {
            if( r ) {
                location.href = href;
            }
        });
    }
    function doDisableItemBySubmitForm(formName, title, firstName, lastName, label1, label2, mapData, actionURL) {
        var message = '<fmt:message key="admin.confirm.disable.user.message"><fmt:param>' + firstName +
                      '</fmt:param><fmt:param>' + lastName + '</fmt:param></fmt:message>';
        $('body').modalmanager('loading');
        bootbox.confirm(title, message, label1, label2, function(r) {
            if( r ) {
                $('body').modalmanager('loading');
                if (mapData != undefined && actionURL != undefined){
                    for (var i in mapData) {
                        $(i).val(mapData[i]);
                    }
                    $(formName).attr("action", actionURL);
                }
                $(formName).submit();
            }
        });
    }
    function doActiveItemBySubmitForm(formName, title, firstName, lastName, label1, label2, mapData, actionURL) {
        var message = '<fmt:message key="admin.confirm.active.user.message"><fmt:param>' + firstName +
            '</fmt:param><fmt:param>' + lastName + '</fmt:param></fmt:message>';
        $('body').modalmanager('loading');
        bootbox.confirm(title, message, label1, label2, function(r) {
            if( r ) {
                $('body').modalmanager('loading');
                if (mapData != undefined && actionURL != undefined){
                    for (var i in mapData) {
                        $(i).val(mapData[i]);
                    }
                    $(formName).attr("action", actionURL);
                }
                $(formName).submit();
            }
        });
    }
    function doDisableItemByAjaxCall(url, title, firstName, lastName, label1, label2, formName) {
        var message = '<fmt:message key="admin.confirm.disable.user.message"><fmt:param>' + firstName +
            '</fmt:param><fmt:param>' + lastName + '</fmt:param></fmt:message>';
        $('body').modalmanager('loading');
        bootbox.confirm(title, message, label1, label2, function(r) {
            if( r ) {
                $('body').modalmanager('loading');
                if( r ) {
                    $.ajax({
                        type: "GET",
                        url:  url,
                        success: function(html){
                            $(formName).submit();
                        }
                    });
                }
            }
        });
    }
    function doActiveItemByAjaxCall(url, title, firstName, lastName, label1, label2, formName) {
        var message = '<fmt:message key="admin.confirm.active.user.message"><fmt:param>' + firstName +
            '</fmt:param><fmt:param>' + lastName + '</fmt:param></fmt:message>';
        $('body').modalmanager('loading');
        bootbox.confirm(title, message, label1, label2, function(r) {
            if( r ) {
                $.ajax({
                    type: "GET",
                    url:  url,
                    success: function(html){
                        $(formName).submit();
                    }
                });
            }
        });
    }

</script>