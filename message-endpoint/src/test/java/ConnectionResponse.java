class ConnectionResponse{
    private String response;
    private int responseCode;

    ConnectionResponse(String response, int responseCode){
        this.response = response;
        this.responseCode = responseCode;
    }

    public String getResponse(){
        return response;
    }

    public int getResponseCode(){
        return responseCode;
    }
}