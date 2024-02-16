package com.ibsplc.neoicargo.devops.utils.ecrsync;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			June 03, 2021 	  Binu K			First draft
 */
public class CLIArgs {

    private String source;
    private String target;
    private String sourcePrefix;
    private String targetPrefix;
    private String targetImagePrefix;
    private String bomFile;
    private boolean simulation;
    private boolean doNotDelete;

    static boolean hasText(String string){
        return string!=null && string.trim().length() >0;
    }

    public boolean isDoNotDelete() {
        return doNotDelete;
    }

    public void setDoNotDelete(boolean doNotDelete) {
        this.doNotDelete = doNotDelete;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSourcePrefix() {
        return sourcePrefix;
    }

    public void setSourcePrefix(String sourcePrefix) {
        this.sourcePrefix = sourcePrefix;
    }

    public String getTargetPrefix() {
        return targetPrefix;
    }

    public void setTargetPrefix(String targetPrefix) {
        this.targetPrefix = targetPrefix;
    }

    public String getBomFile() {
        return bomFile;
    }

    public void setBomFile(String bomFile) {
        this.bomFile = bomFile;
    }


    public boolean isSimulation() {
        return simulation;
    }

    public void setSimulation(boolean simulation) {
        this.simulation = simulation;
    }

    public String getTargetImagePrefix() {
        return targetImagePrefix;
    }

    public void setTargetImagePrefix(String targetImagePrefix) {
        this.targetImagePrefix = targetImagePrefix;
    }
}
