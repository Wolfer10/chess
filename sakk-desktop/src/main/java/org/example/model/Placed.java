package org.example.model;

public class Placed implements Comparable {
   String from;
   private int time = 0;

    public Placed(String from) {
        this.from = from;
        this.time++;
    }

    public String getFrom() {
        return from;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public int compareTo(Object other) {
        if (time > ((Placed) other).time){
            return -1;
        } else {
            return 1;
        }


    }
}
