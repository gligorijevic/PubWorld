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
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

/**
 *
 * @author Djordje
 */
public class ICDE06Crawler {

    public static void getPapersAndAutors(Document doc, Conference conference) {
        Elements sekcije = doc.select("pre");

        for (Element element : sekcije) {
            String radovi = element.text();
            String[] pojedinacniRadovi;

            if (radovi.contains("#")) {
                pojedinacniRadovi = radovi.split("#");
            } else {
                pojedinacniRadovi = radovi.split("\n\n");
            }

            for (String pojedinacniRad : pojedinacniRadovi) {

                Paper paper = new Paper();
                paper.setPaperConference(conference);
                List<Participant> participants = new LinkedList<>();

                if (pojedinacniRad.length() > 1) {
                    String[] radPar = pojedinacniRad.split("\n");
//                    if (radPar.length == 2) {
//                        int skrati = 0;
//                        if (Character.isDigit(radPar[0].charAt(0))) {
//                            String[] reci = radPar[0].trim().split(" ");
//                            skrati = reci[0].length();
//                        }
//                        System.out.println("PAPER NAME: " + radPar[0].substring(skrati).trim());
//
//                        String[] autori = radPar[1].split(",");
//                        for (String autor : autori) {
//                            System.out.println("AUTHOR: " + autor.trim());
//                        }
//                    } else {
                    String paperName = "";
                    String authors = "";

                    paperName = radPar[0].trim();
                    authors = radPar[radPar.length - 1].trim();

                    for (int i = 1; i < radPar.length - 1; i++) {
                        if (!radPar[i].contains(",")) {
                            if (!radPar[i].equals("")) {
                                paperName = paperName + " " + radPar[i].trim();
                            }

                        } else {
                            authors = radPar[i] + " " + authors.trim();
                        }
                    }

                    int skrati = 0;
                    if (Character.isDigit(paperName.charAt(0))) {
                        String[] reci = paperName.split(" ");
                        skrati = reci[0].length();
                    }
                    System.out.println("PAPER NAME: " + paperName.substring(skrati).trim());
                    paper.setPaperName(paperName.substring(skrati).trim());

                    String[] autori = authors.split(",");
                    for (String autor : autori) {
                        System.out.println("AUTHOR: " + autor.trim());
                        Participant participant = new Participant(autor.trim(), null, "null");
                        participants.add(participant);
                        PubWorldData.getInstance().insertParticipant(participant);
                    }
                }

                paper.setPaperAuthors(participants);
                //sacuvaj
                PubWorldData.getInstance().insertPaper(paper);
            }
        }
    }

    public static void getProgramCommitee(String html, Conference conference) {
        String[] parts = html.split("\n");
        for (int i = 0; i < parts.length; i++) {
            if (!parts[i].trim().equals("") && !parts[i].contains("Copyright ICDE 2006")) {
                if (parts[i].contains("Dr.")) {
                    System.out.println("NAME: " + parts[i].substring(4));
                    System.out.println("AFFILIATION: " + parts[i + 1] + " " + parts[i + 2]);

                    ProgramCommitee pc = new ProgramCommitee(parts[i].substring(4).trim(), null, parts[i + 1] + " " + parts[i + 2], conference);
                    PubWorldData.getInstance().insertProgramCommitee(pc);

                } else if (parts[i].contains("Technical Program Committee")) {
                    for (int j = i + 1; j < parts.length; j++) {

                        String[] parts2 = parts[j].split(",");
                        if (parts2.length <= 3 && !parts2[0].trim().equals("") && !parts[j].trim().contains("Copyright ICDE 2006")) {
                            System.out.println("AUTHOR: " + parts2[0]);
                            System.out.println("AFFILIATION: " + parts2[1]);
                            ProgramCommitee pc = new ProgramCommitee(parts2[0].trim(), null, parts2[1], conference);
                            PubWorldData.getInstance().insertProgramCommitee(pc);
                            Participant participant = new Participant(parts2[0].trim(), null, parts2[1]);
                            PubWorldData.getInstance().insertParticipant(participant);
                        }
                    }
                }
            }
        }

    }

    public static String br2nl(String html) {
        if (html == null) {
            return html;
        }
        Document document = Jsoup.parse(html);
        document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
        document.select("br").append("\\n");
        document.select("p").prepend("\\n\\n");
        String s = document.html().replaceAll("\\\\n", "\n");
        return Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
    }

}
