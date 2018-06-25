package it.dan.entities;

public class Opinion {
    public enum PersonLike {
        Like, Dislike, Noinfo
    }

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

    private String who;
    private String whom;
    private  PersonLike like;



    public PersonLike getLike() {
        return like;
    }

    public void setOpinionLike() {
        this.like = PersonLike.Like;
    }

    public void setOpinionDisLike() {
        this.like = PersonLike.Dislike;
    }

    public Opinion(String who, String whom) {
        this.who = who;
        this.whom = whom;
        this.like = PersonLike.Noinfo;
    }
    public Opinion() {
    }
}
