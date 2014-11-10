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
public class Participant {

    private int participantId;
    private String participantName;
    private String participantRole;
    private String participantAffiliation;

    public Participant() {
    }

    public Participant(int participantId, String participantName, String participantRole, String participantAffiliation) {
        this.participantId = participantId;
        this.participantName = participantName;
        this.participantRole = participantRole;
        this.participantAffiliation = participantAffiliation;
    }

    public Participant(String participantName, String participantRole, String participantAffiliation) {
        this.participantName = participantName;
        this.participantRole = participantRole;
        this.participantAffiliation = participantAffiliation;
    }

    /**
     * @return the participantId
     */
    public int getParticipantId() {
        return participantId;
    }

    /**
     * @param participantId the participantId to set
     */
    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    /**
     * @return the participantName
     */
    public String getParticipantName() {
        return participantName;
    }

    /**
     * @param participantName the participantName to set
     */
    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    /**
     * @return the participantRole
     */
    public String getParticipantRole() {
        return participantRole;
    }

    /**
     * @param participantRole the participantRole to set
     */
    public void setParticipantRole(String participantRole) {
        this.participantRole = participantRole;
    }

    /**
     * @return the participantAffiliation
     */
    public String getParticipantAffiliation() {
        return participantAffiliation;
    }

    /**
     * @param participantAffiliation the participantAffiliation to set
     */
    public void setParticipantAffiliation(String participantAffiliation) {
        this.participantAffiliation = participantAffiliation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Participant) {
            if (((Participant) obj).participantName.equals(this.participantName)) {
                if (((Participant) obj).participantAffiliation != null) {
                    if (((Participant) obj).participantAffiliation.equals(this.participantAffiliation)) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

}
