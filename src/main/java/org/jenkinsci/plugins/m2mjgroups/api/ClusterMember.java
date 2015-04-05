package org.jenkinsci.plugins.m2mjgroups.api;

import hudson.ExtensionPoint;

public interface ClusterMember extends ExtensionPoint {
    
    public void connect(Cluster cluster) throws Exception;
    
    public void connect(String clusterName) throws Exception;

}
