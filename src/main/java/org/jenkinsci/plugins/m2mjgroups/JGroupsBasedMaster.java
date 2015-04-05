package org.jenkinsci.plugins.m2mjgroups;

import hudson.ExtensionList;
import jenkins.model.Jenkins;

import org.jenkinsci.plugins.m2mjgroups.api.Cluster;
import org.jenkinsci.plugins.m2mjgroups.api.ClusterMember;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;

public abstract class JGroupsBasedMaster implements ClusterMember {

    private final JChannel jchannel;

    public JGroupsBasedMaster() {
        JChannel jchannel = null;
        try {
            jchannel = new JChannel();
        } catch (Exception e) {
            throw new IllegalStateException("Cannot create JChannel", e.getCause());
        }
        
        this.jchannel = jchannel;
        this.jchannel.receiver(new JGroupsReciever());
        this.jchannel.setDiscardOwnMessages(true); // don't receive own messages
    }

    public void connect(Cluster cluster) throws Exception {
        jchannel.connect(cluster.getClusterName());
    }
    
    public void connect(String clusterName) throws Exception {
        jchannel.connect(clusterName);
    }

    public abstract void receiveMsg(Message message);

    public void sendMsg(Message message) throws Exception {
        jchannel.send(message);
    }

    public static ExtensionList<JGroupsBasedMaster> all() {
        return Jenkins.getInstance().getExtensionList(JGroupsBasedMaster.class);
    }

    public class JGroupsReciever extends ReceiverAdapter {
        @Override
        public void receive(Message m) {
            receiveMsg(m);
        }
    }
}
