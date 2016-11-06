package com.intive.model;

public class CaptchaData {

    private String captchaCode;
    private String captchaCorrect;
    private String captchaIncorrect;

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String userCaptchaCode) {
        this.captchaCode = userCaptchaCode;
    }

    public String getCaptchaCorrect() {
        return captchaCorrect;
    }

    public void setCaptchaCorrect(String captchaCorrect) {
        this.captchaCorrect = captchaCorrect;
    }

    public String getCaptchaIncorrect() {
        return captchaIncorrect;
    }

    public void setCaptchaIncorrect(String captchaIncorrect) {
        this.captchaIncorrect = captchaIncorrect;
    }

}
