package org.jenkinsci.plugins.m2mjgroups.api;

import hudson.ExtensionPoint;

public interface Cluster extends ExtensionPoint {
    
    public String getClusterName();

}
