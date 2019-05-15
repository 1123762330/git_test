package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import javax.persistence.*;

public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "is_anonymous")
    private Boolean isAnonymous;

    @Column(name = "no_fees")
    private Boolean noFees;

    private String username;

    private String pass;

    /**
     * Assocaited email: used for validating users, and re-setting passwords
     */
    private String email;

    private String timezone;

    @Column(name = "notify_email")
    private String notifyEmail;

    @Column(name = "loggedIp")
    private String loggedip;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "failed_logins")
    private Integer failedLogins;

    @Column(name = "failed_pins")
    private Integer failedPins;

    @Column(name = "signup_timestamp")
    private Integer signupTimestamp;

    @Column(name = "last_login")
    private Integer lastLogin;

    /**
     * four digit pin to allow account changes
     */
    private String pin;

    @Column(name = "api_key")
    private String apiKey;

    private String token;

    @Column(name = "donate_percent")
    private Float donatePercent;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return is_admin
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * @param isAdmin
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * @return is_anonymous
     */
    public Boolean getIsAnonymous() {
        return isAnonymous;
    }

    /**
     * @param isAnonymous
     */
    public void setIsAnonymous(Boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    /**
     * @return no_fees
     */
    public Boolean getNoFees() {
        return noFees;
    }

    /**
     * @param noFees
     */
    public void setNoFees(Boolean noFees) {
        this.noFees = noFees;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * 获取Assocaited email: used for validating users, and re-setting passwords
     *
     * @return email - Assocaited email: used for validating users, and re-setting passwords
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置Assocaited email: used for validating users, and re-setting passwords
     *
     * @param email Assocaited email: used for validating users, and re-setting passwords
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return timezone
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * @param timezone
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * @return notify_email
     */
    public String getNotifyEmail() {
        return notifyEmail;
    }

    /**
     * @param notifyEmail
     */
    public void setNotifyEmail(String notifyEmail) {
        this.notifyEmail = notifyEmail;
    }

    /**
     * @return loggedIp
     */
    public String getLoggedip() {
        return loggedip;
    }

    /**
     * @param loggedip
     */
    public void setLoggedip(String loggedip) {
        this.loggedip = loggedip;
    }

    /**
     * @return is_locked
     */
    public Boolean getIsLocked() {
        return isLocked;
    }

    /**
     * @param isLocked
     */
    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    /**
     * @return failed_logins
     */
    public Integer getFailedLogins() {
        return failedLogins;
    }

    /**
     * @param failedLogins
     */
    public void setFailedLogins(Integer failedLogins) {
        this.failedLogins = failedLogins;
    }

    /**
     * @return failed_pins
     */
    public Integer getFailedPins() {
        return failedPins;
    }

    /**
     * @param failedPins
     */
    public void setFailedPins(Integer failedPins) {
        this.failedPins = failedPins;
    }

    /**
     * @return signup_timestamp
     */
    public Integer getSignupTimestamp() {
        return signupTimestamp;
    }

    /**
     * @param signupTimestamp
     */
    public void setSignupTimestamp(Integer signupTimestamp) {
        this.signupTimestamp = signupTimestamp;
    }

    /**
     * @return last_login
     */
    public Integer getLastLogin() {
        return lastLogin;
    }

    /**
     * @param lastLogin
     */
    public void setLastLogin(Integer lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * 获取four digit pin to allow account changes
     *
     * @return pin - four digit pin to allow account changes
     */
    public String getPin() {
        return pin;
    }

    /**
     * 设置four digit pin to allow account changes
     *
     * @param pin four digit pin to allow account changes
     */
    public void setPin(String pin) {
        this.pin = pin;
    }

    /**
     * @return api_key
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * @param apiKey
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return donate_percent
     */
    public Float getDonatePercent() {
        return donatePercent;
    }

    /**
     * @param donatePercent
     */
    public void setDonatePercent(Float donatePercent) {
        this.donatePercent = donatePercent;
    }
}