package com.benluck.vms.mobifonedataseller.editor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author benluck
 * 
 */
public class CustomDateEditor extends PropertyEditorSupport {
	private transient final Log log = LogFactory.getLog(CustomDateEditor.class);
	private String dateFormat = "yyyy-MM-dd";
	public CustomDateEditor(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public CustomDateEditor(){}

    /** Reformat date value to predefined format.
     *
     * @param text
     * @throws IllegalArgumentException
     */
	public void setAsText(String text) throws IllegalArgumentException {
		if (text != null && text.trim().length() > 1) {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			try {
				Date d = (Date) format.parse(text);
				setValue(new Date(d.getTime()));
			} catch (Exception e) {
				log.error("Invalid date format [" + dateFormat + "]" + e.getMessage());
			}
		}

	}
}
