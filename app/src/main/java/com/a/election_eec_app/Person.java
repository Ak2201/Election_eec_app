package com.a.election_eec_app;

import java.io.Serializable;

public class Person implements Serializable {
    String id;
    private String registernumber;
    private Boolean hasVotedPresident1;
    private Boolean hasVotedVicePresident1;
    private Boolean hasVotedSecretary1;
    private Boolean hasVotedTreasurer1;
    private Boolean hasVotedJointSecretary1;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegisternumber() {
        return registernumber;
    }

    public void setRegisternumber(String registernumber) {
        this.registernumber = registernumber;
    }

    public Boolean getHasVotedPresident1() {
        return hasVotedPresident1;
    }

    public void setHasVotedPresident1(Boolean hasVotedPresident1) {
        this.hasVotedPresident1 = hasVotedPresident1;
    }

    public Boolean getHasVotedVicePresident1() {
        return hasVotedVicePresident1;
    }

    public void setHasVotedVicePresident1(Boolean hasVotedVicePresident1) {
        this.hasVotedVicePresident1 = hasVotedVicePresident1;
    }

    public Boolean getHasVotedSecretary1() {
        return hasVotedSecretary1;
    }

    public void setHasVotedSecretary1(Boolean hasVotedSecretary1) {
        this.hasVotedSecretary1 = hasVotedSecretary1;
    }

    public Boolean getHasVotedTreasurer1() {
        return hasVotedTreasurer1;
    }

    public void setHasVotedTreasurer1(Boolean hasVotedTreasurer1) {
        this.hasVotedTreasurer1 = hasVotedTreasurer1;
    }

    public Boolean getHasVotedJointSecretary1() {
        return hasVotedJointSecretary1;
    }

    public void setHasVotedJointSecretary1(Boolean hasVotedJointSecretary1) {
        this.hasVotedJointSecretary1 = hasVotedJointSecretary1;
    }
}
