import arguments.CommandLineParser;
import emailservice.EmailService;
import model.SiteState;
import model.SitesStateTables;

import javax.mail.MessagingException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SiteCheckingService {
    private SitesStateTables siteStateTable;

    private String yesterdaySitesStateFileName;
    private String todaySitesStateFileName;

    private String emailFromUsername;
    private String emailFromPassword;

    private String emailToSend;

    private boolean setOptions(String[] args) {
        CommandLineParser commandLineParser = new CommandLineParser(args);

        if (commandLineParser.getHelp()) {
            commandLineParser.showHelp();
            return false;
        }

        yesterdaySitesStateFileName = commandLineParser.getYesterdaySitesStateFileName();
        todaySitesStateFileName = commandLineParser.getTodaySitesStateFileName();

        emailFromUsername = commandLineParser.getEmailFromUsername();
        emailFromPassword = commandLineParser.getEmailFromPassword();

        emailToSend = commandLineParser.getEmailToSend();

        return true;
    }

    private void initStateTables() throws IOException {
        siteStateTable = new SitesStateTables();

        String yesterdaySitesDirectory = yesterdaySitesStateFileName.split(".txt")[0];
        String todaySitesDirectory = todaySitesStateFileName.split(".txt")[0];

        try (BufferedReader yesterdaySitesReader = new BufferedReader(new FileReader(yesterdaySitesStateFileName));
             BufferedReader todaySitesReader = new BufferedReader(new FileReader(todaySitesStateFileName))) {

            String line;
            while ((line = yesterdaySitesReader.readLine()) != null) {
                String[] urlHtmlPair = line.split(" ");

                siteStateTable.addSites(urlHtmlPair[0], yesterdaySitesDirectory + "/" + urlHtmlPair[1], false);
            }
            while ((line = todaySitesReader.readLine()) != null) {
                String[] urlHtmlPair = line.split(" ");

                siteStateTable.addSites(urlHtmlPair[0], todaySitesDirectory + "/" + urlHtmlPair[1], true);
            }
        }
    }

    private void checkSiteChanges() {
        siteStateTable.initStates();

        for (Map.Entry<String, List<String>> todayPair : siteStateTable.getTodaySitesTable().entrySet()) {
            boolean found = false;
            for (Map.Entry<String, List<String>> yesterdayPair : siteStateTable.getYesterdaySitesTable().entrySet()) {
                if (todayPair.getKey().equals(yesterdayPair.getKey())) {
                    found = true;
                    if (todayPair.getValue().equals(yesterdayPair.getValue())) {
                        siteStateTable.getStates().put(todayPair.getKey(), SiteState.NO_CHANGES);
                    } else {
                        siteStateTable.getStates().put(todayPair.getKey(), SiteState.CHANGED);
                    }
                }
            }
            if (!found) {
                siteStateTable.getStates().put(todayPair.getKey(), SiteState.NEW);
            }
        }
    }

    private void sendSiteChanges() throws MessagingException {

        String greeting = "Hello, " + emailToSend + "!  \n" +
                "Over the past 24 hours, the following changes have occurred in the sites entrusted to you:\n";

        StringBuilder deletedSites = new StringBuilder("The following pages have disappeared:\n");
        StringBuilder newSites =  new StringBuilder("The following new pages have appeared:\n");
        StringBuilder changedSites =  new StringBuilder("The following pages have changed:\n");
        StringBuilder noChangedSites =  new StringBuilder("The following pages without changes:\n");

        List<String> deletedSitesList = new ArrayList<>();
        List<String> newSitesList = new ArrayList<>();
        List<String> changedSitesList = new ArrayList<>();
        List<String> noChangedSitesList = new ArrayList<>();


        for (Map.Entry<String, SiteState> entry : siteStateTable.getStates().entrySet()) {
            if (entry.getValue() == SiteState.DELETED) {
                deletedSitesList.add(entry.getKey());
            } else if (entry.getValue() == SiteState.NEW) {
                newSitesList.add(entry.getKey());
            } else if (entry.getValue() == SiteState.CHANGED) {
                changedSitesList.add(entry.getKey());
            } else if (entry.getValue() == SiteState.NO_CHANGES) {
                noChangedSitesList.add(entry.getKey());
            }
        }

        for (String s : deletedSitesList) {
            deletedSites.append(s).append("\n");
        }

        for (String s : newSitesList) {
            newSites.append(s).append("\n");
        }

        for (String s :changedSitesList) {
            changedSites.append(s).append("\n");
        }

        for (String s : noChangedSitesList) {
            noChangedSites.append(s).append("\n");
        }

        String body = greeting + deletedSites + newSites + changedSites + noChangedSites;

        EmailService emailService = new EmailService(emailFromUsername, emailFromPassword);
        emailService.sendMessage(emailToSend, "Site changes info", body);
    }

    public void start(String[] args) throws IOException, MessagingException {
        if (setOptions(args)) {
            initStateTables();
            checkSiteChanges();
            sendSiteChanges();
        }
    }
}
