/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pubworld.domain;

/**
 *
 * @author Djordje
 */
public class ProgramCommitee {

    private int programCommiteeId;
    private String programCommiteeName;
    private String programCommiteeRole;
    private String programCommiteeAffiliation;
    private Conference programCommiteeConference;

    public ProgramCommitee() {
    }

    public ProgramCommitee(int programCommiteeId, String programCommiteeName, String programCommiteeRole, String programCommiteeAffiliation, Conference programCommiteeConference) {
        this.programCommiteeId = programCommiteeId;
        this.programCommiteeName = programCommiteeName;
        this.programCommiteeRole = programCommiteeRole;
        this.programCommiteeAffiliation = programCommiteeAffiliation;
        this.programCommiteeConference = programCommiteeConference;
    }

    public ProgramCommitee(String programCommiteeName, String programCommiteeRole, String programCommiteeAffiliation, Conference programCommiteeConference) {
        this.programCommiteeName = programCommiteeName;
        this.programCommiteeRole = programCommiteeRole;
        this.programCommiteeAffiliation = programCommiteeAffiliation;
        this.programCommiteeConference = programCommiteeConference;
    }

    /**
     * @return the programCommiteeId
     */
    public int getProgramCommiteeId() {
        return programCommiteeId;
    }

    /**
     * @param programCommiteeId the programCommiteeId to set
     */
    public void setProgramCommiteeId(int programCommiteeId) {
        this.programCommiteeId = programCommiteeId;
    }

    /**
     * @return the programCommiteeName
     */
    public String getProgramCommiteeName() {
        return programCommiteeName;
    }

    /**
     * @param programCommiteeName the programCommiteeName to set
     */
    public void setProgramCommiteeName(String programCommiteeName) {
        this.programCommiteeName = programCommiteeName;
    }

    /**
     * @return the programCommiteeRole
     */
    public String getProgramCommiteeRole() {
        return programCommiteeRole;
    }

    /**
     * @param programCommiteeRole the programCommiteeRole to set
     */
    public void setProgramCommiteeRole(String programCommiteeRole) {
        this.programCommiteeRole = programCommiteeRole;
    }

    /**
     * @return the programCommiteeAffiliation
     */
    public String getProgramCommiteeAffiliation() {
        return programCommiteeAffiliation;
    }

    /**
     * @param programCommiteeAffiliation the programCommiteeAffiliation to set
     */
    public void setProgramCommiteeAffiliation(String programCommiteeAffiliation) {
        this.programCommiteeAffiliation = programCommiteeAffiliation;
    }

    /**
     * @return the programCommiteeConference
     */
    public Conference getProgramCommiteeConference() {
        return programCommiteeConference;
    }

    /**
     * @param programCommiteeConference the programCommiteeConference to set
     */
    public void setProgramCommiteeConference(Conference programCommiteeConference) {
        this.programCommiteeConference = programCommiteeConference;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProgramCommitee) {
            if (((ProgramCommitee) obj).programCommiteeName.equals(this.programCommiteeName)
                    && ((ProgramCommitee) obj).programCommiteeConference.equals(programCommiteeConference)) {
                if(((ProgramCommitee) obj).programCommiteeAffiliation!=null){
                    if(((ProgramCommitee) obj).programCommiteeAffiliation.equals(this.programCommiteeAffiliation)){
                        return true;
                    }
                }else{
                    return true;
                }
                return true;
            }
        }
        return false;
    }

}
