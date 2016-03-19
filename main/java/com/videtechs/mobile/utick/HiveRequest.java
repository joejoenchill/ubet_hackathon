package com.videtechs.mobile.utick;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HiveRequest {

    private static String urlUser = "http://www.videtechs.com/utick/api/putUser.php";
    private static String urlPay = "http://www.videtechs.com/utick/api/payTick.php";
    private static String urlEvent = "https://gh-disaster-relief.appspot.com/_ah/api/ticketApi/v1/ticket";
    private static String urlForu = "http://www.videtechs.com/utick/api/putUser.php";
    private static String urlChats = "http://www.videtechs.com/utick/api/putUser.php";
    private static String urlChatp = "http://www.videtechs.com/utick/api/putUser.php";
    private static String urlNews = "http://www.videtechs.com/utick/api/putUser.php";
    private static String urlTriviz = "http://www.videtechs.com/utick/api/putUser.php";

    private JSONParser jsonParser;

    public HiveRequest(){jsonParser = new JSONParser();}

    public JSONObject putUser(String name, String email, String phone){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("utName", name));
        params.add(new BasicNameValuePair("utEmail", email));
        params.add(new BasicNameValuePair("utPhone", phone));
        JSONObject json = jsonParser.makeHttpRequest(urlUser, "POST", params);
        return json;
    }


    //GET CONTESTS
    public JSONObject getEvents(){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("utPhone", ""));
        params.add(new BasicNameValuePair("utAmount", ""));
        JSONObject json = jsonParser.makeHttpRequest(urlEvent, "GET", params);

        return json;
    }


    //GET CANDIDATES
    public JSONObject payUTick(String amount){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("utPhone", ""));
        params.add(new BasicNameValuePair("utAmount", amount));
        JSONObject json = jsonParser.makeHttpRequest(urlPay, "POST", params);
        return json;
    }


    //GET FORUMS
    public JSONObject getForums(String lastId){

        //create user account on server
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("lastId", lastId));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(urlForu, "POST", params);

        // return json
        return json;
    }

    //GET CHATS
    public JSONObject getChats(String forId, String chaId){

        //create user account on server
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("forId", forId));
        params.add(new BasicNameValuePair("chaId", chaId));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(urlChats, "POST", params);

        // return json
        return json;
    }

    //PUT CHATS
    public JSONObject putChats(String forum, String user, String msg){

        //create user account on server
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("forum", forum));
        params.add(new BasicNameValuePair("user", user));
        params.add(new BasicNameValuePair("msg", msg));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(urlChatp, "POST", params);

        // return json
        return json;
    }

    //GET NEWS
    public JSONObject getNews(String lastId){

        //create user account on server
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("lastId", lastId));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(urlNews, "POST", params);

        // return json
        return json;
    }

    //GET TRIVIALS
    public JSONObject getTriviz(String lastId){

        //create user account on server
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("lastId", lastId));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(urlTriviz, "POST", params);

        // return json
        return json;
    }
}
