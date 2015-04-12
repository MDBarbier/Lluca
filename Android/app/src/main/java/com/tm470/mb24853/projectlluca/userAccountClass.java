package com.tm470.mb24853.projectlluca;

/**
 * Created by Admin on 03/04/2015.
 */
public class userAccountClass
{
    private String username;
    private String password;
    private String emailAddress;
    private int logged_in;
    private int user_id;
    private String[] ownedDecks;

    public userAccountClass()
    {
        //empty constructor
    }

    public userAccountClass(String username, String password, String emailAddress)
    {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.logged_in = 0;

    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogged_in(int logged_in) {
        this.logged_in = logged_in;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setOwnedDecks(String[] ownedDecks) {
        this.ownedDecks = ownedDecks;
    }

    public String getUsername()
    {
        return username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public int getLogged_in() { return logged_in;  }

    public int getUserID() {return user_id; }

    public String[] getOwnedDecks() {return ownedDecks;}
}
