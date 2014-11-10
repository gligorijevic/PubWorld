/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pubworld.data;

import com.pubworld.domain.Conference;
import com.pubworld.domain.Paper;
import com.pubworld.domain.Participant;
import com.pubworld.domain.ProgramCommitee;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Djordje
 */
public class PubWorldData {

    private List<Participant> listOfAllParticipants;
    private List<ProgramCommitee> listOfAllProgramCommitees;
    private List<Paper> listOfAllPapers;
    private List<Conference> listOfAllConferences;

    private PubWorldData() {
        listOfAllParticipants = new ArrayList<>();
        listOfAllProgramCommitees = new ArrayList<>();
        listOfAllPapers = new ArrayList<>();
    }

    public static PubWorldData getInstance() {
        return PubWorldDataHolder.INSTANCE;
    }

    /**
     * @return the listOfAllParticipants
     */
    public List<Participant> getListOfAllParticipants() {
        return listOfAllParticipants;
    }

    /**
     * @param listOfAllParticipants the listOfAllParticipants to set
     */
    public void setListOfAllParticipants(List<Participant> listOfAllParticipants) {
        this.listOfAllParticipants = listOfAllParticipants;
    }

    /**
     * @return the listOfAllProgramCommitees
     */
    public List<ProgramCommitee> getListOfAllProgramCommitees() {
        return listOfAllProgramCommitees;
    }

    /**
     * @param listOfAllProgramCommitees the listOfAllProgramCommitees to set
     */
    public void setListOfAllProgramCommitees(List<ProgramCommitee> listOfAllProgramCommitees) {
        this.listOfAllProgramCommitees = listOfAllProgramCommitees;
    }

    /**
     * @return the listOfAllPapers
     */
    public List<Paper> getListOfAllPapers() {
        return listOfAllPapers;
    }

    /**
     * @param listOfAllPapers the listOfAllPapers to set
     */
    public void setListOfAllPapers(List<Paper> listOfAllPapers) {
        this.listOfAllPapers = listOfAllPapers;
    }

    /**
     * @return the listOfAllConferences
     */
    public List<Conference> getListOfAllConferences() {
        return listOfAllConferences;
    }

    /**
     * @param listOfAllConferences the listOfAllConferences to set
     */
    public void setListOfAllConferences(List<Conference> listOfAllConferences) {
        this.listOfAllConferences = listOfAllConferences;
    }

    private static class PubWorldDataHolder {

        private static final PubWorldData INSTANCE = new PubWorldData();
    }

    public void insertPaper(Paper paper) {
        if (paper.getPaperName() != null && paper.getPaperAuthors().size() > 0) {
            if (!listOfAllPapers.contains(paper)) {
                getListOfAllPapers().add(paper);
                System.err.println("Paper " + paper.getPaperName() + " has been inserted into list.");
            }
        }
    }

    public void insertParticipant(Participant participant) {
        if (participant.getParticipantName() != null) {
            if (!listOfAllParticipants.contains(participant)) {
                getListOfAllParticipants().add(participant);
                System.err.println("Participant " + participant.getParticipantName() + " has been inserted into list.");
            }
        }
    }

    public void insertProgramCommitee(ProgramCommitee pc) {
        if (pc.getProgramCommiteeName() != null) {
            if (!listOfAllProgramCommitees.contains(pc)) {
                getListOfAllProgramCommitees().add(pc);
                System.err.println("Program Commitee " + pc.getProgramCommiteeName() + " has been inserted into list.");
            }
        }
    }
      
}
