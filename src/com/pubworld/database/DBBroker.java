/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pubworld.database;

import com.pubworld.data.PubWorldData;
import com.pubworld.domain.Conference;
import com.pubworld.domain.Paper;
import com.pubworld.domain.Participant;
import com.pubworld.domain.ProgramCommitee;
import static java.nio.file.attribute.AclEntryPermission.DELETE;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Djordje
 */
public class DBBroker {

    private static DBBroker instance;
    private static String db_userid;
    private static String db_password;
    private static String db_connect_string = "jdbc:sqlserver://DJORDJE;databaseName=pubworld";

    private Connection conn;

    private DBBroker() {
        db_userid = "djordje";
        db_password = "djordje";
    }

    public static synchronized DBBroker getInstance() {
        if (instance == null) {
            instance = new DBBroker();
        }
        return instance;
    }

    public void connect() {
        try {
//            String url = "jdbc:sqlserver://DJORDJE;databaseName=pubworld;integratedSecurity=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            try {
                conn = DriverManager.getConnection(db_connect_string, db_userid, db_password);
                System.out.println("Connection to the database has been made.");
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }

    public void clearDatabase() {
        List<String> queries = new ArrayList<>();

        queries.add("DELETE FROM [dbo].[Author]");
        queries.add("DELETE FROM [dbo].[ProgramComitee]");
        queries.add("DELETE FROM [dbo].[Paper]");
        queries.add("DELETE FROM [dbo].[Conference]");
        queries.add("DELETE FROM [dbo].[Participant]");
        Statement statement = null;
        try {
            statement = (Statement) conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (String string : queries) {
            try {
                statement.execute(string);
            } catch (SQLException ex) {
                Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void insertAll() {
        Statement statement = null;
        try {
            statement = (Statement) conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            for (Conference conference : PubWorldData.getInstance().getListOfAllConferences()) {

                String SQL = "SELECT conferenceId FROM Conference WHERE conferenceId='" + conference.getConferenceName().replaceAll("\\s+", "") + "'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);

                if (!rs.next()) {
                    statement.executeUpdate("INSERT INTO Conference (conferenceId, conferenceName, conferenceYear, conferenceLocation)"
                            + "VALUES ('" + conference.getConferenceName().replaceAll("\\s+", "")
                            + "', '" + conference.getConferenceName()
                            + "', '" + conference.getConferenceYear()
                            + "', '" + conference.getConferenceLocation()
                            + "')");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Participant participant : PubWorldData.getInstance().getListOfAllParticipants()) {

            try {

                String participantId = participant.getParticipantName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "" + participant.getParticipantAffiliation().replaceAll("\\s", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "");

//                System.out.println("SELECT participantId FROM Participant WHERE participantId='"
//                        + participant.getParticipantName().replaceAll("\\s+", "").replace("\'", "") + "" + participant.getParticipantAffiliation().replaceAll("\\s", "").replace("\'", "") + "'");
                String SQL = "SELECT participantId FROM Participant WHERE participantId='"
                        + participant.getParticipantName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "" + participant.getParticipantAffiliation().replaceAll("\\s", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);

                if (!rs.next()) {
                    statement.executeUpdate("INSERT INTO Participant (participantId, participantName, participantRole, participantAffiliation)"
                            + " VALUES ('" + participant.getParticipantName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "" + participant.getParticipantAffiliation().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
                            + "','" + participant.getParticipantName().replaceAll("\\-", " ").replaceAll("\\'", " ").replaceAll("\\,", " ") + "',null, '" + participant.getParticipantAffiliation().replaceAll("\\-", " ").replaceAll("\\'", " ").replaceAll("\\,", " ")
                            + "')");
                    System.out.println("UBACIO");
                }
            } catch (SQLException ex) {
                Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("INSERT INTO Participant (participantId, participantName, participantRole, participantAffiliation)"
                        + " VALUES ('" + participant.getParticipantName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "" + participant.getParticipantAffiliation().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
                        + "','" + participant.getParticipantName().replaceAll("\\-", " ").replaceAll("\\'", " ").replaceAll("\\,", " ") + "',null, '" + participant.getParticipantAffiliation().replaceAll("\\-", " ").replaceAll("\\'", " ").replaceAll("\\,", " ")
                        + "')");
            }
        }

        for (ProgramCommitee pc : PubWorldData.getInstance().getListOfAllProgramCommitees()) {
            try {

                String SQL = "SELECT conferenceid, participantId FROM ProgramComitee WHERE participantId='"
                        + pc.getProgramCommiteeName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "" + pc.getProgramCommiteeAffiliation().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
                        + "' AND conferenceid='" + pc.getProgramCommiteeConference().getConferenceName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);

                if (!rs.next()) {

                    String SQL2 = "SELECT * FROM Participant WHERE participantId='"
                            + pc.getProgramCommiteeName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "" + pc.getProgramCommiteeAffiliation().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
                            + "'";
                    Statement stmt2 = conn.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(SQL2);

                    if (!rs2.next()) {
                        statement.executeUpdate("INSERT INTO Participant (participantId, participantName, participantRole, participantAffiliation)"
                                + " VALUES ('" + pc.getProgramCommiteeName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "" + pc.getProgramCommiteeAffiliation().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
                                + "','" + pc.getProgramCommiteeName().replaceAll("\\s+", " ").replaceAll("\\-", " ").replaceAll("\\'", " ").replaceAll("\\,", " ")
                                + "', null, '" + pc.getProgramCommiteeAffiliation().replaceAll("\\-", " ").replaceAll("\\'", " ").replaceAll("\\,", " ")
                                + "')");
                        System.out.println("UBACIO");
                    }

                    statement.executeUpdate("INSERT INTO ProgramComitee (role, conferenceid, participantId, affiliation)"
                            + " VALUES (null, '" + pc.getProgramCommiteeConference().getConferenceName().replaceAll("\\s+", "").replace("\'-,", "")
                            + "','" + pc.getProgramCommiteeName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "" + pc.getProgramCommiteeAffiliation().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
                            + "', '" + pc.getProgramCommiteeAffiliation().replaceAll("\\-", " ").replaceAll("\\'", " ").replaceAll("\\,", " ")
                            + "')");
                    System.out.println("UBACIO 2");
                }
            } catch (SQLException ex) {
                Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("INSERT INTO ProgramComitee (role, conferenceid, participantId, affiliation)"
                        + " VALUES (null, '" + pc.getProgramCommiteeConference().getConferenceName().replaceAll("\\s+", "").replace("\'-,", "")
                        + "','" + pc.getProgramCommiteeName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "" + pc.getProgramCommiteeAffiliation().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
                        + "', '" + pc.getProgramCommiteeAffiliation().replaceAll("\\-", " ").replaceAll("\\'", " ").replaceAll("\\,", " ")
                        + "')");
            }
        }

        for (Paper paper : PubWorldData.getInstance().getListOfAllPapers()) {
            try {

                String SQL = "SELECT paperId FROM Paper WHERE paperId='" + paper.getPaperName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);

                if (!rs.next()) {
                    statement.executeUpdate("INSERT INTO Paper (paperId, paperName, conferenceId)"
                            + " VALUES ('" + paper.getPaperName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
                            + "','" + paper.getPaperName().replaceAll("\\-", " ").replaceAll("\\'", " ").replaceAll("\\,", " ")
                            + "', '" + paper.getPaperConference().getConferenceName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
                            + "')");
                    System.out.println("UBACIO 3");
                }

                for (Participant author : paper.getPaperAuthors()) {
                    try {
                        String SQL2 = "SELECT participantId, paperId FROM Author WHERE paperId='"
                                + paper.getPaperName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
                                + "' AND participantId='" + author.getParticipantName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "" + author.getParticipantAffiliation().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "'";
                        Statement stmt2 = conn.createStatement();
                        ResultSet rs2 = stmt2.executeQuery(SQL2);

                        if (!rs2.next()) {
                            int executeUpdate = statement.executeUpdate("INSERT INTO Author (participantId, paperId, position, affiliation)"
                                    + " VALUES ('" + author.getParticipantName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "" + author.getParticipantAffiliation().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
                                    + "','" + paper.getPaperName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
                                    + "'," + (paper.getPaperAuthors().indexOf(author) + 1)
                                    + ",'" + paper.getPaperConference().getConferenceName().replaceAll("\\-", " ").replaceAll("\\'", " ").replaceAll("\\,", " ")
                                    + "')");
                            System.out.println("UBACIO 4");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
                        System.err.println("UBACIVANJE AUTORA RADA NIJE PROSLO" + "INSERT INTO Author (participantId, paperId, position, affiliation)"
                                + " VALUES ('" + author.getParticipantName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "" + author.getParticipantAffiliation().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
                                + "','" + paper.getPaperName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
                                + "'," + (paper.getPaperAuthors().indexOf(author) + 1)
                                + ",'" + paper.getPaperConference().getConferenceName().replaceAll("\\-", " ").replaceAll("\\'", " ").replaceAll("\\,", " ")
                                + "')");
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("UBACIVANJE RADA NIJE PROSLO " + "INSERT INTO Paper (paperId, paperName, conferenceId)"
                        + " VALUES ('" + paper.getPaperName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
                        + "','" + paper.getPaperName().replaceAll("\\-", " ").replaceAll("\\'", " ").replaceAll("\\,", " ")
                        + "', '" + paper.getPaperConference().getConferenceName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
                        + "')");
            }
        }

//        for (Paper paper : PubWorldData.getInstance().getListOfAllPapers()) {
//            for (Participant author : paper.getPaperAuthors()) {
//                try {
//                    String SQL = "SELECT participantId, paperId FROM Author WHERE paperId='"
//                            + paper.getPaperName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
//                            + "' AND participantId='" + author.getParticipantName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "" + author.getParticipantAffiliation().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "'";
//                    Statement stmt = conn.createStatement();
//                    ResultSet rs = stmt.executeQuery(SQL);
//
//                    if (!rs.next()) {
//                        int executeUpdate = statement.executeUpdate("INSERT INTO Author (participantId, paperId, position, affiliation)"
//                                + " VALUES ('" + author.getParticipantName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "" + author.getParticipantAffiliation().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
//                                + "','" + paper.getPaperName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
//                                + "'," + (paper.getPaperAuthors().indexOf(author) + 1)
//                                + ",'" + paper.getPaperConference().getConferenceName().replaceAll("\\-", " ").replaceAll("\\'", " ").replaceAll("\\,", " ")
//                                + "')");
//                        System.out.println("UBACIO 4");
//                    }
//                } catch (SQLException ex) {
//                    Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
//                    System.err.println("UBACIVANJE AUTORA RADA NIJE PROSLO" + "INSERT INTO Author (participantId, paperId, position, affiliation)"
//                            + " VALUES ('" + author.getParticipantName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "") + "" + author.getParticipantAffiliation().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
//                            + "','" + paper.getPaperName().replaceAll("\\s+", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\,", "")
//                            + "'," + (paper.getPaperAuthors().indexOf(author) + 1)
//                            + ",'" + paper.getPaperConference().getConferenceName().replaceAll("\\-", " ").replaceAll("\\'", " ").replaceAll("\\,", " ")
//                            + "')");
//                }
//            }
//        }
    }

}
