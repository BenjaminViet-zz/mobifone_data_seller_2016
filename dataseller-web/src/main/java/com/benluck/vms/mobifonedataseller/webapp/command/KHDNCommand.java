package com.benluck.vms.mobifonedataseller.webapp.command;

import com.benluck.vms.mobifonedataseller.core.dto.KHDNDTO;

/**
 * Created by thaihoang on 11/5/2016.
 */
public class KHDNCommand extends AbstractCommand<KHDNDTO> {
    public KHDNCommand(){
        this.pojo = new KHDNDTO();
    }
}
