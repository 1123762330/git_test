package com.xn.find_xn_user.domain;

public class Accounts {
    private Integer id;
    private byte is_admin;
    private byte is_anonymous;
    private byte no_fees;
    private String username;
    private String pass;
    private String email;
    private String timezone;
    private String notify_email;
    private String loggedIp;
    private byte is_locked;
    private Integer failed_logins;
    private Integer failed_pins;
    private Integer signup_timestamp;
    private Integer last_login;
    private String pin;
    private String api_key;
    private String token;
    private float donate_percent;

    public Accounts() {
    }

    public Accounts(Integer id, byte is_admin, byte is_anonymous, byte no_fees, String username, String pass, String email, String timezone, String notify_email, String loggedIp, byte is_locked, Integer failed_logins, Integer failed_pins, Integer signup_timestamp, Integer last_login, String pin, String api_key, String token, float donate_percent) {
        this.id = id;
        this.is_admin = is_admin;
        this.is_anonymous = is_anonymous;
        this.no_fees = no_fees;
        this.username = username;
        this.pass = pass;
        this.email = email;
        this.timezone = timezone;
        this.notify_email = notify_email;
        this.loggedIp = loggedIp;
        this.is_locked = is_locked;
        this.failed_logins = failed_logins;
        this.failed_pins = failed_pins;
        this.signup_timestamp = signup_timestamp;
        this.last_login = last_login;
        this.pin = pin;
        this.api_key = api_key;
        this.token = token;
        this.donate_percent = donate_percent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(byte is_admin) {
        this.is_admin = is_admin;
    }

    public byte getIs_anonymous() {
        return is_anonymous;
    }

    public void setIs_anonymous(byte is_anonymous) {
        this.is_anonymous = is_anonymous;
    }

    public byte getNo_fees() {
        return no_fees;
    }

    public void setNo_fees(byte no_fees) {
        this.no_fees = no_fees;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getNotify_email() {
        return notify_email;
    }

    public void setNotify_email(String notify_email) {
        this.notify_email = notify_email;
    }

    public String getLoggedIp() {
        return loggedIp;
    }

    public void setLoggedIp(String loggedIp) {
        this.loggedIp = loggedIp;
    }

    public byte getIs_locked() {
        return is_locked;
    }

    public void setIs_locked(byte is_locked) {
        this.is_locked = is_locked;
    }

    public Integer getFailed_logins() {
        return failed_logins;
    }

    public void setFailed_logins(Integer failed_logins) {
        this.failed_logins = failed_logins;
    }

    public Integer getFailed_pins() {
        return failed_pins;
    }

    public void setFailed_pins(Integer failed_pins) {
        this.failed_pins = failed_pins;
    }

    public Integer getSignup_timestamp() {
        return signup_timestamp;
    }

    public void setSignup_timestamp(Integer signup_timestamp) {
        this.signup_timestamp = signup_timestamp;
    }

    public Integer getLast_login() {
        return last_login;
    }

    public void setLast_login(Integer last_login) {
        this.last_login = last_login;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public float getDonate_percent() {
        return donate_percent;
    }

    public void setDonate_percent(float donate_percent) {
        this.donate_percent = donate_percent;
    }

    @Override
    public String toString() {
        return "Accounts{" +
                "id=" + id +
                ", is_admin=" + is_admin +
                ", is_anonymous=" + is_anonymous +
                ", no_fees=" + no_fees +
                ", username='" + username + '\'' +
                ", pass='" + pass + '\'' +
                ", email='" + email + '\'' +
                ", timezone='" + timezone + '\'' +
                ", notify_email='" + notify_email + '\'' +
                ", loggedIp='" + loggedIp + '\'' +
                ", is_locked=" + is_locked +
                ", failed_logins=" + failed_logins +
                ", failed_pins=" + failed_pins +
                ", signup_timestamp=" + signup_timestamp +
                ", last_login=" + last_login +
                ", pin='" + pin + '\'' +
                ", api_key='" + api_key + '\'' +
                ", token='" + token + '\'' +
                ", donate_percent=" + donate_percent +
                '}';
    }
}
