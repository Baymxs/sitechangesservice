package arguments;

import com.beust.jcommander.Parameter;

public class CommandLineArgs {
    @Parameter(names = {"--yesterday_state", "--y_state"}, description = "Input file name with the state of the sites for yesterday", help = true)
    private String yesterdaySitesStateFileName;

    @Parameter(names = {"--today_state", "--t_state"}, description = "Input file name with the state of the sites for today", help = true)
    private String todaySitesStateFileName;

    @Parameter(names = {"--from_send"}, description = "Email from which the message is sent", help = true)
    private String emailFromUsername;

    @Parameter(names = {"--from_send_password"}, description = "Password email from which the message is sent", help = true)
    private String emailFromPassword;

    @Parameter(names = {"--to_send"}, description = "Email to which the message is sent", help = true)
    private String emailToSend;

    @Parameter(names = {"--help", "-h"}, description = "Shows information about program options", help = true)
    private boolean help;

    public String getYesterdaySitesStateFileName() {
        return yesterdaySitesStateFileName;
    }

    public String getTodaySitesStateFileName() {
        return todaySitesStateFileName;
    }

    public String getEmailFromUsername() {
        return emailFromUsername;
    }

    public String getEmailFromPassword() {
        return emailFromPassword;
    }

    public String getEmailToSend() {
        return emailToSend;
    }

    public boolean getHelpOption() {
        return help;
    }
}
