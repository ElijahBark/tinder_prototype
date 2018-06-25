package it.dan.entities;

public class Message implements Comparable<Message> {
    private String text;
    private String who;
    private  String whom;
    private long date;

    public Message() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Message(String text, String who, String whom) {
        this.text = text;
        this.who = who;
        this.whom = whom;
        this.date = System.currentTimeMillis();
    }

    @Override
    public int compareTo(Message o) {
        return (int)(date - o.date);
    }
}
