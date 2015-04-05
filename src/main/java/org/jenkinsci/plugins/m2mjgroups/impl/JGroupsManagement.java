package org.jenkinsci.plugins.m2mjgroups.impl;

import hudson.Extension;
import hudson.init.InitMilestone;
import hudson.init.Initializer;
import hudson.model.ManagementLink;
import hudson.util.HttpResponses;

import java.util.logging.Logger;

import org.jgroups.Message;
import org.kohsuke.args4j.Argument;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.interceptor.RequirePOST;

/**
 * Taken from https://github.com/jenkinsci/master-to-master-api-plugin/blob/master/src/main/java/org/jenkinsci/plugins/mastertomasterapi/simple/SIMManagement.java
 */
@Extension
public class JGroupsManagement extends ManagementLink {
    
    private static transient JGroupsBasedMasterImpl jmaster;

    @Override
    public String getIconFileName() {
        return "computer.png";
    }

    @Override
    public String getUrlName() {
        return "m2m-jgroups";
    }

    public String getDisplayName() {
        return "JGroups master to master PoC";
    }


    @RequirePOST
    public void doSendMsgToAll(StaplerRequest req, StaplerResponse res) throws Exception {
        jmaster.sendMsg(new Message(null, req.getParameter("msg")));
        req.getView(this, "index.jelly").forward(req, res);
    }

    public static JGroupsManagement get() {
        return all().get(JGroupsManagement.class);
    }

    @Initializer(after=InitMilestone.JOB_LOADED, fatal=false)
    public static void init() throws Exception {
        jmaster = new JGroupsBasedMasterImpl();
        jmaster.connect("test-cluster");
    }

}
