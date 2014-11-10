/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pubworld.crawler;

import com.pubworld.data.PubWorldData;
import com.pubworld.domain.Conference;
import com.pubworld.domain.Paper;
import com.pubworld.domain.Participant;
import com.pubworld.domain.ProgramCommitee;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Djordje
 */
public class ICDE14Crawler {

    public static void getPapersAndAutors(Document doc, Conference conference) {
        List<Participant> participants = new ArrayList<>();
        Elements radovi = doc.select("h1");
        for (Element element : radovi) {
//            System.out.println(element.tag());
            //find healdine with Papers
            int brojac = 0;
            if (element.text().contains("Research Track Papers") && element.tagName().equals("h1")) {
                Element naslovi = element.nextElementSibling().nextElementSibling();

                Paper paper = new Paper(); // = new Paper();

                while (naslovi != null) {

                    if (naslovi.tagName().equals("h1") || naslovi.tagName().equals("b") || naslovi.tagName().equals("ul")) {
                        String text = naslovi.text().trim();

                        //if paper name
                        if (Character.isDigit(text.charAt(0))) {
                            
                            brojac++;
                            paper.setPaperAuthors(participants);
                            PubWorldData.getInstance().insertPaper(paper);
                            System.out.println(brojac);
                            
                            participants = new ArrayList<>();
                            paper = new Paper();
                            paper.setPaperConference(conference);

                            String[] reci = text.split(" ");
                            int skrati = reci[0].length();
                            System.out.println("PAPER: " + text.substring(skrati).trim());
                            paper.setPaperName(text.substring(skrati).trim());
                        } else if (naslovi.tagName().equals("ul")) {
                            Elements e = naslovi.getAllElements();
                            int index = 1;
                            for (Element element1 : e) {
                                index++;
                                if (index > 2) {
                                    String linija = element1.text().trim();

                                    Participant participant;
                                    if (linija.contains("(")) {
                                        System.out.println("AUTOR NAME: " + linija.substring(0, linija.indexOf("(") - 1).trim());
                                        System.out.println("AUTOR AFFILIATION: " + linija.substring(linija.indexOf("(") + 1, linija.length() - 1).trim());
                                        participant = new Participant(linija.substring(0, linija.indexOf("(") - 1), null, linija.substring(linija.indexOf("(") + 1, linija.length() - 1).trim());
                                        participants.add(participant);
                                    } else if (linija.contains(",")) {
                                        System.out.println("AUTOR NAME: " + linija.substring(0, linija.indexOf(",")).trim());
                                        System.out.println("AUTOR AFFILIATION: " + linija.substring(linija.indexOf(",") + 1, linija.length()).trim());
                                        participant = new Participant(linija.substring(0, linija.indexOf(",")).trim(), null, linija.substring(linija.indexOf(",") + 1, linija.length()).trim());
                                        participants.add(participant);
                                    } else {
                                        System.out.println("AUTOR NAME: " + linija);
                                        participant = new Participant(linija, null, "null");
                                        participants.add(participant);
                                    }
                                    PubWorldData.getInstance().insertParticipant(participant);
                                }
                            }

//                            int pocetak = 0;
//                            int sredina = 0;
//                            int kraj = 0;
//
//                            while (text.length() > 3) {
//                                sredina = text.indexOf("(");
//                                kraj = text.indexOf(")");
//                                String ime = text.substring(pocetak, sredina).trim();
//                                pocetak = kraj + 2;
//                                String afilijacija = text.substring(sredina + 1, kraj).trim();
//
//                                System.out.println("AUTOR NAME: " + ime.trim());
//                                System.out.println("AUTOR AFFILIATION: " + afilijacija.trim());
//
//                                if (text.length() < pocetak) {
//                                    break;
//                                }
//                                text = text.substring(pocetak);
//                                pocetak = 0;
//                                sredina = 0;
//                                kraj = 0;
//                            }
                            System.out.println("#########################################");
                        }

                        
                    }
                    naslovi = naslovi.nextElementSibling();

                }
            }
        }
    }

    public static void getProgramCommitee(Document doc, Conference conference) {
        Elements radovi = doc.select("li");
        for (Element element : radovi) {

            String text = element.text().trim();
//            System.out.println(text);
            if (text.contains("(") && text.contains(")")) {
                int sredina = text.indexOf("(");
                int kraj = text.indexOf(")");
                int pocetak = 0;
                String ime = text.substring(pocetak, sredina).trim();
                pocetak = kraj + 2;
                String afilijacija = text.substring(sredina + 1, kraj).trim();

                System.out.println("AUTOR NAME: " + ime.trim());
                System.out.println("AUTOR AFFILIATION: " + afilijacija.trim());
                ProgramCommitee programCommitee = new ProgramCommitee(ime.trim(), null, afilijacija.trim(), conference);
                PubWorldData.getInstance().insertProgramCommitee(programCommitee);
                Participant participant = new Participant(ime.trim(), null, afilijacija.trim());
                PubWorldData.getInstance().insertParticipant(participant);
            }
        }
    }
}
