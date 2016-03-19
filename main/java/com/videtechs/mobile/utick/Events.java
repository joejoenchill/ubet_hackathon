package com.videtechs.mobile.utick;

public class Events {

    public String voId;
    public String voTitle;
    public String voDate;
    public String voPrice;
    public String voVenue;
    public String voImage;

    public Events(){
        super();
    }

    public Events(String voId,String voTitle,  String voDate, String voPrice, String voVenue, String voImage){
        super();
        this.voId = voId;
        this.voTitle = voTitle;
        this.voDate = voDate;
        this.voPrice = voPrice;
        this.voVenue = voVenue;
        this.voImage = voImage;
    }
}
