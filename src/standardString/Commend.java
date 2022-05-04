package standardString;

import zad1.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Commend {

    private final String loginReqForms = "login %s";
    private final String loginReplyForms = "logged in";
    private final String byeReplyForms = "logged out";
    private final String byeAndLogForms = "bye and log transfer";
    private final String bye = "bye";
    public final String request = "request";

    // login
    public String loginReq(String id){
        return String.format(loginReqForms, id);
    }
    public String loginReply(){
        return loginReplyForms;
    }

    public boolean isLoginReq(String lien){
        String loginReqRegex = loginReqForms.replace("%s", "[a-zA-Z]+");
        return lien.matches(loginReqRegex);
    }

    public boolean isLoginReplay(String lien){
        return loginReplyForms.equals(lien);
    }

    // date
    public boolean isDate(String lien){
        String[] dates = lien.split(" ");
        return isValidDate(dates[0]) && isValidDate(dates[1]);
    }

    public String dateReply(String lien){
        String[] arr = lien.split(" ");
        String fromDate = arr[0];
        String toDate = arr[1];
        return Time.passed(fromDate, toDate);
    }

    private boolean isValidDate(String input) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(input);
            return true;
        }
        catch(ParseException e){
            return false;
        }
    }

    // bye and log
    public boolean isByeAndLog(String lien){
        return byeAndLogForms.equals(lien);
    }

    // bye
    public String byeReply(){
        return byeReplyForms;
    }

    public boolean isBye(String lien){
        return lien.equals(lien);
    }

}
