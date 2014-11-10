/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pubworld.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Djordje
 */
public class Conference {

    private int conferenceId;
    private String conferenceName;
    private String conferenceYear;
    private String conferenceLocation;
    private List<Participant> programCommitee;

    public Conference() {
    }

    public Conference(int conferenceId, String conferenceName, String conferenceYear, String conferenceLocation, List<Participant> programCommitee) {
        this.conferenceId = conferenceId;
        this.conferenceName = conferenceName;
        this.conferenceYear = conferenceYear;
        this.conferenceLocation = conferenceLocation;

        if (programCommitee.isEmpty()) {
            this.programCommitee = new ArrayList<>();
        } else {
            this.programCommitee = programCommitee;
        }

    }

    public Conference(String conferenceName, String conferenceYear, String conferenceLocation) {
        this.conferenceName = conferenceName;
        this.conferenceYear = conferenceYear;
        this.conferenceLocation = conferenceLocation;
        this.programCommitee = new ArrayList<>();
    }

    /**
     * @return the conferenceId
     */
    public int getConferenceId() {
        return conferenceId;
    }

    /**
     * @param conferenceId the conferenceId to set
     */
    public void setConferenceId(int conferenceId) {
        this.conferenceId = conferenceId;
    }

    /**
     * @return the conferenceName
     */
    public String getConferenceName() {
        return conferenceName;
    }

    /**
     * @param conferenceName the conferenceName to set
     */
    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    /**
     * @return the conferenceYear
     */
    public String getConferenceYear() {
        return conferenceYear;
    }

    /**
     * @param conferenceYear the conferenceYear to set
     */
    public void setConferenceYear(String conferenceYear) {
        this.conferenceYear = conferenceYear;
    }

    /**
     * @return the conferenceLocation
     */
    public String getConferenceLocation() {
        return conferenceLocation;
    }

    /**
     * @param conferenceLocation the conferenceLocation to set
     */
    public void setConferenceLocation(String conferenceLocation) {
        this.conferenceLocation = conferenceLocation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Conference) {
            if (((Conference) obj).conferenceLocation.equals(this.conferenceLocation)
                    && ((Conference) obj).conferenceName.equals(this.conferenceName)) {
                return true;
            }
        }
        return false;
    }

}
