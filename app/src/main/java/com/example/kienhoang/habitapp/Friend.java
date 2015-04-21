package com.example.kienhoang.habitapp;

/**
 * Created by kienhoang on 4/21/15.
 */
public class Friend {
    private int profilePic;
    private String name;
    private int numBuildHabits;
    private int numBreakHabits;

    public Friend(int profilePic, String name, int numBuildHabits, int numBreakHabits) {
        this.profilePic = profilePic;
        this.name = name;
        this.numBuildHabits = numBuildHabits;
        this.numBreakHabits = numBreakHabits;
    }

    public int getProfilePic() {
        return this.profilePic;
    }

    public String getName() {
        return this.name;
    }

    public int getNumBuildHabits() {
        return this.numBuildHabits;
    }

    public int getNumBreakHabits() {
        return this.numBreakHabits;
    }
}
