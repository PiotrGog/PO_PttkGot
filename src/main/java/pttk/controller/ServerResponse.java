package pttk.controller;


public class ServerResponse {
    private static final String SERVER_ERROR_MSG = "Server Error";

    private String responseMsg;

    public ServerResponse() {}

    public ServerResponse(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public static String getServerErrorMsg() {
        return SERVER_ERROR_MSG;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "responseMsg='" + responseMsg + '\'' +
                '}';
    }
}
