<%@ include file="/common/taglibs.jsp" %>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i> General Forms</h2>

</div>

<div class="contentpanel">

    <div class="panel panel-default">
        <div class="panel-heading">
            <div class="panel-btns">
                <a href="" class="panel-close">&times;</a>
                <a href="" class="minimize">&minus;</a>
            </div>
            <h4 class="panel-title">Input Fields</h4>
        </div>
        <div class="panel-body panel-body-nopadding">

            <form class="form-horizontal form-bordered">

                <div class="form-group">
                    <label class="col-sm-3 control-label">Default Input</label>
                    <div class="col-sm-6">
                        <input type="text" placeholder="Default Input" class="form-control" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label" for="disabledinput">Disabled Input</label>
                    <div class="col-sm-6">
                        <input type="text" placeholder="Disabled Input" id="disabledinput" class="form-control" disabled="" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label" for="readonlyinput">Read-Only Input</label>
                    <div class="col-sm-6">
                        <input type="text" value="Read Only Input" id="readonlyinput" class="form-control" readonly="readonly" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">Help Text</label>
                    <div class="col-sm-6">
                        <input type="text" placeholder="Help Text" class="form-control">
                        <span class="help-block">A block of help text that breaks onto a new line and may extend beyond one line.</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">Input w/ Tooltip</label>
                    <div class="col-sm-6">
                        <input type="text" placeholder="Hover me" title="Tooltip goes here" data-toggle="tooltip" data-trigger="hover" class="form-control tooltips" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">Input w/ Popover</label>
                    <div class="col-sm-6">
                        <input type="text" placeholder="Click Me" class="form-control popovers" data-toggle="popover" data-placement="top" data-original-title="The Title" data-content="Content goes here..." data-trigger="click" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">Placeholder</label>
                    <div class="col-sm-6">
                        <input type="text" placeholder="This is a placeholder" class="form-control">
                    </div>
                </div>

                <div class="form-group has-success">
                    <label class="col-sm-3 control-label">Input with success</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control">
                    </div>
                </div>

                <div class="form-group has-warning">
                    <label class="col-sm-3 control-label">Input with warning</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control">
                    </div>
                </div>

                <div class="form-group has-error">
                    <label class="col-sm-3 control-label">Input with error</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">Column sizing</label>
                    <div class="col-sm-4">
                        <input type="text" placeholder=".col-sm-4" class="form-control">
                    </div>
                    <div class="col-sm-3">
                        <input type="text" placeholder=".col-sm-3" class="form-control">
                    </div>
                    <div class="col-sm-2">
                        <input type="text" placeholder=".col-sm-2" class="form-control">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">Input Sizing</label>
                    <div class="col-sm-5">
                        <input type="text" placeholder=".input-sm" class="form-control input-sm mb15">
                        <input type="text" placeholder="default" class="form-control mb15">
                        <input type="text" placeholder=".input-lg" class="form-control input-lg">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">Input Tags</label>
                    <div class="col-sm-7">
                        <input name="tags" id="tags" class="form-control" value="foo,bar,baz" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">Textarea</label>
                    <div class="col-sm-7">
                        <textarea class="form-control" rows="5"></textarea>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">Autogrow Textarea</label>
                    <div class="col-sm-7">
                        <textarea id="autoResizeTA" class="form-control" rows="5"></textarea>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">Radio</label>
                    <div class="col-sm-6">
                        <div class="radio"><label><input type="radio"> Unchecked</label></div>
                        <div class="radio"><label><input type="radio" checked=""> Checked</label></div>
                        <div class="radio"><label><input type="radio" disabled=""> Disabled Unchecked</label></div>
                        <div class="radio"><label><input type="radio" checked="" disabled=""> Disabled Checked</label></div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label" for="checkbox">Checkbox</label>
                    <div class="col-sm-6">
                        <div class="checkbox block"><label><input type="checkbox"> Unchecked</label></div>
                        <div class="checkbox block"><label><input type="checkbox" checked=""> Checked</label></div>
                        <div class="checkbox block"><label><input type="checkbox" disabled=""> Disabled Unchecked</label></div>
                        <div class="checkbox block"><label><input type="checkbox" checked="" disabled=""> Disabled Checked</label></div>
                    </div>
                </div>


                <div class="form-group">
                    <label class="col-sm-3 control-label">Spinner</label>
                    <div class="col-sm-9">
                        <input type="text" id="spinner" />
                        <span class="help-block">Enhance a text input for entering numeric values, with up/down buttons and arrow key handling.</span>
                    </div>
                </div>

            </form>

        </div><!-- panel-body -->

        <div class="panel-footer">
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3">
                    <button class="btn btn-primary">Submit</button>&nbsp;
                    <button class="btn btn-info">Cancel</button>
                </div>
            </div>
        </div><!-- panel-footer -->

    </div><!-- panel -->



</div><!-- contentpanel -->