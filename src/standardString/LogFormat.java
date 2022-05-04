package standardString;

import java.nio.charset.StandardCharsets;

public class LogFormat {

    private final String logout = "logged out";
    private final String login = "logged in";

    public String startLog(String id){
        return "=== "+id+" log start ===" + System.lineSeparator();
    }
    public String endLog(String id){
        return "=== "+id+" log end ===" + System.lineSeparator();
    }

    public String logoutLog() {
        return logout + System.lineSeparator();
    }

    public String loginLog() {
        return login + System.lineSeparator();
    }

    public String request(String req){
        return "Request: " + req + System.lineSeparator();
    }

    public String reply(String reply){
        return "Result:" + System.lineSeparator() + reply + System.lineSeparator();
    }
}
