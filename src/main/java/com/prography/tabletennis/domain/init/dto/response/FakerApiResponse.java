package com.prography.tabletennis.domain.init.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class FakerApiResponse {
    private String status;
    private int code;
    private String locale;
    private String seed;
    private int total;

    @JsonProperty("data")
    private List<UserData> userDataList; // data 배열을 담을 리스트
}
