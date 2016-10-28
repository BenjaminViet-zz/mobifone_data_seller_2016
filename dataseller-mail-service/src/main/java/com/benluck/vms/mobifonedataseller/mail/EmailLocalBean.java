package com.benluck.vms.mobifonedataseller.mail;

import com.benluck.vms.mobifonedataseller.mail.exception.SendEmailException;

import javax.ejb.Asynchronous;
import javax.ejb.Local;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hieu
 * Date: 12/5/13
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface EmailLocalBean {
    @Asynchronous
    public void sendMessage(List<String> toAddresses,
                            List<String> ccAddresses,
                            List<String> bccAddresses,
                            String subject,
                            String templateName,
                            Map<String,Object> model);

    @Asynchronous
    public void sendMessage(final java.util.List<String> toAddresses,
                            final java.util.List<String> ccAddresses,
                            final java.util.List<String> bccAddresses,
                            final String sender,
                            final String subject,
                            final String templateName,
                            final  Map<String,Object> model);
    @Asynchronous
    public void sendMessage(final java.util.List<String> toAddresses,
                            final java.util.List<String> ccAddresses,
                            final java.util.List<String> bccAddresses,
                            final String sender,
                            final String subject,
                            final String content);
}
