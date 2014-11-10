/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pubworld.run;

import com.pubworld.crawler.ICDE06Crawler;
import com.pubworld.crawler.ICDE14Crawler;
import com.pubworld.data.PubWorldData;
import com.pubworld.database.DBBroker;
import com.pubworld.domain.Conference;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Djordje
 */
public class PubWorld {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        List<Conference> conferences = new ArrayList<>();
        conferences.add(new Conference("ICDE2014", "2014", "Chicago, IL, USA"));
        conferences.add(new Conference("ICDE2013", "2013", "Brisbane, Australia"));
        conferences.add(new Conference("ICDE2006", "2006", "Atlanta, GA, USA"));

        PubWorldData.getInstance().setListOfAllConferences(conferences);

        List<String> conferencesPapers = new ArrayList<>();
        conferencesPapers.add("http://ieee-icde2014.eecs.northwestern.edu/accepted.html");
        conferencesPapers.add("http://www.icde2013.org/papers.html");
        conferencesPapers.add("http://icde06.ewi.utwente.nl/acceptedpapers.html");

        List<String> conferencesProgramCommitee = new ArrayList<>();
        conferencesProgramCommitee.add("http://ieee-icde2014.eecs.northwestern.edu/pc.html");
        conferencesProgramCommitee.add("http://www.icde2013.org/pc.html");
        conferencesProgramCommitee.add("http://icde06.ewi.utwente.nl/committee_prog.html");

//        Document doc1 = Jsoup.connect("http://ieee-icde2014.eecs.northwestern.edu/accepted.html").get();
//        Document doc2 = Jsoup.connect("http://ieee-icde2014.eecs.northwestern.edu/pc.html").get();
//        Document doc3 = Jsoup.connect("http://www.icde2013.org/papers.html").get();
//        Document doc4 = Jsoup.connect("http://www.icde2013.org/pc.html").get();
//        Document doc5 = Jsoup.connect("http://icde06.ewi.utwente.nl/acceptedpapers.html").get();
//        Document doc6 = Jsoup.connect("http://icde06.ewi.utwente.nl/committee_prog.html").get();
        for (int i = 0; i < 3; i++) {
            Document docPapers = Jsoup.connect(conferencesPapers.get(i)).get();
            Document docPc = Jsoup.connect(conferencesProgramCommitee.get(i)).get();

            if (conferencesPapers.get(i).contains("06")) {
                ICDE06Crawler.getPapersAndAutors(docPapers, conferences.get(i));
                ICDE06Crawler.getProgramCommitee(ICDE06Crawler.br2nl(docPc.html()), conferences.get(i));
            } else {
                ICDE14Crawler.getPapersAndAutors(docPapers, conferences.get(i));
                ICDE14Crawler.getProgramCommitee(docPc, conferences.get(i));
            }
        }

        int broj = 0;
        for (int i = 0; i < PubWorldData.getInstance().getListOfAllPapers().size(); i++) {
            if (PubWorldData.getInstance().getListOfAllPapers().get(i).getPaperConference().getConferenceName().equals("ICDE2014")) {
                broj++;
            }
        }
        System.out.println(broj);

//        System.out.println(ICDE06Crawler.br2nl(doc6.html()));
//        System.out.println(6doc.toString());
//        ICDE14Crawler.getPapersAndAutors(doc1);
//        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//        ICDE14Crawler.getProgramCommitee(doc2);
//                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//        ICDE14Crawler.getPapersAndAutors(doc3);
//        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//        ICDE14Crawler.getProgramCommitee(doc4);
//                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//        ICDE06Crawler.getPapersAndAutors(doc5);
//        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//        ICDE06Crawler.getProgramCommitee(ICDE06Crawler.br2nl(doc6.html()));
//        DBBroker.getInstance().connect();
        System.out.println("Number of papers crawled: " + PubWorldData.getInstance().getListOfAllPapers().size());
        System.out.println("Number of authors crawled: " + PubWorldData.getInstance().getListOfAllParticipants().size());
        System.out.println("Number of program commitee crawled: " + PubWorldData.getInstance().getListOfAllProgramCommitees().size());
        try {
            DBBroker.getInstance().connect();
            DBBroker.getInstance().clearDatabase();
            DBBroker.getInstance().insertAll();
            DBBroker.getInstance().closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(PubWorld.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
