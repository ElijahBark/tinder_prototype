package it.dan.entities;

public class Opinion {
    private String who;
    private String whom;
    private Boolean like;

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWhom() {
        return whom;
    }

    public void setWhom(String whom) {
        this.whom = whom;
    }


    public Boolean getLike() {
        return like;
    }

    public void setOpinionLike(boolean like) {
        this.like = like;
    }

    public Opinion(String who, String whom) {
        this.who = who;
        this.whom = whom;
    }
    public Opinion() {
    }
}
