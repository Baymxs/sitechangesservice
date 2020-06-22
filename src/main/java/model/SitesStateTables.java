package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SitesStateTables {
    private Map<String, List<String>> yesterdaySitesTable;
    private Map<String, List<String>> todaySitesTable;

    private Map<String, SiteState> states;

    public SitesStateTables() {
        yesterdaySitesTable = new HashMap<>();
        todaySitesTable = new HashMap<>();
        states = new HashMap<>();
    }

    public void initStates() {
        for (Map.Entry<String, List<String>> entry : yesterdaySitesTable.entrySet()) {
            states.put(entry.getKey(), SiteState.DELETED);
        }
        for (Map.Entry<String, List<String>> entry : todaySitesTable.entrySet()) {
            states.put(entry.getKey(), SiteState.DELETED);
        }
    }

    public Map<String, List<String>> getYesterdaySitesTable() {
        return yesterdaySitesTable;
    }

    public Map<String, List<String>> getTodaySitesTable() {
        return todaySitesTable;
    }

    public Map<String, SiteState> getStates() {
        return states;
    }

    public void addSites(String url, String htmlFileName, boolean day) throws IOException {
        try(BufferedReader htmlFileReader = new BufferedReader(new FileReader(htmlFileName))) {
            List<String> htmlFileLines = new ArrayList<>();

            String line;
            while ((line = htmlFileReader.readLine()) != null) {
                htmlFileLines.add(line);
            }

            if (day) {
                todaySitesTable.put(url, htmlFileLines);
            } else {
                yesterdaySitesTable.put(url, htmlFileLines);
            }
        }
    }
}
