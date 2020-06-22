package arguments;

import com.beust.jcommander.JCommander;

public class CommandLineParser {
    private CommandLineArgs commandLineArgs;

    public CommandLineParser(String[] args) {
        commandLineArgs = new CommandLineArgs();
        JCommander.newBuilder()
                .addObject(commandLineArgs)
                .build()
                .parse(args);
    }

    public String getYesterdaySitesStateFileName() {
        return commandLineArgs.getYesterdaySitesStateFileName();
    }

    public String getTodaySitesStateFileName() {
        return commandLineArgs.getTodaySitesStateFileName();
    }

    public String getEmailFromUsername() {
        return commandLineArgs.getEmailFromUsername();
    }

    public String getEmailFromPassword() {
        return commandLineArgs.getEmailFromPassword();
    }

    public String getEmailToSend() {
        return commandLineArgs.getEmailToSend();
    }

    public boolean getHelp() {
        return commandLineArgs.getHelpOption();
    }

    public void showHelp() {
        JCommander.newBuilder().addObject(commandLineArgs).build().usage();
    }
}
