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
public class Paper {

    private int paperId;
    private String paperName;
    private Conference paperConference;
    private List<Participant> paperAuthors;

    public Paper() {
        paperAuthors = new ArrayList<>();
    }

    public Paper(int paperId, String paperName, Conference paperConference, List<Participant> paperAuthors) {
        this.paperId = paperId;
        this.paperName = paperName;
        this.paperConference = paperConference;
        if (paperAuthors.isEmpty()) {
            this.paperAuthors = new ArrayList<>();
        } else {
            this.paperAuthors = paperAuthors;
        }
    }

    /**
     * @return the paperId
     */
    public int getPaperId() {
        return paperId;
    }

    /**
     * @param paperId the paperId to set
     */
    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    /**
     * @return the paperName
     */
    public String getPaperName() {
        return paperName;
    }

    /**
     * @param paperName the paperName to set
     */
    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    /**
     * @return the paperConference
     */
    public Conference getPaperConference() {
        return paperConference;
    }

    /**
     * @param paperConference the paperConference to set
     */
    public void setPaperConference(Conference paperConference) {
        this.paperConference = paperConference;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Paper){
            if(((Paper)obj).paperName.equals(this.paperName)){
                return true;
            }
        }
        return false;
    }

    /**
     * @return the paperAuthors
     */
    public List<Participant> getPaperAuthors() {
        return paperAuthors;
    }

    /**
     * @param paperAuthors the paperAuthors to set
     */
    public void setPaperAuthors(List<Participant> paperAuthors) {
        this.paperAuthors = paperAuthors;
    }
    
    

}
