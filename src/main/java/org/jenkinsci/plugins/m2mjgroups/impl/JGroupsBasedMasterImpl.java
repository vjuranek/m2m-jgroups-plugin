package org.jenkinsci.plugins.m2mjgroups.impl;

import org.jenkinsci.plugins.m2mjgroups.JGroupsBasedMaster;
import org.jgroups.Message;

public class JGroupsBasedMasterImpl extends JGroupsBasedMaster {

    @Override
    public void receiveMsg(Message message) {
        System.out.println("Received msg: " + message.getObject());
    }

}
