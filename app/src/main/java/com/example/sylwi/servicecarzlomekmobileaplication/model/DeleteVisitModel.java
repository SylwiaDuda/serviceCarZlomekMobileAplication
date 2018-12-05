package com.example.sylwi.servicecarzlomekmobileaplication.model;

/**
 * Created by sylwi on 04.12.2018.
 */

public class DeleteVisitModel extends TokenModel{
    private String visitId;

    public DeleteVisitModel(String token, String visitId) {
        super(token);
        this.visitId = visitId;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }
}
