package org.example.common.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@XStreamAlias("album")
public class Album {

    @NotNull
    @XStreamAlias("album_id")
    @JsonProperty("album_id")
    private String albumId;

    @NotEmpty
    @XStreamAlias("album_title")
    @JsonProperty("album_title")
    private String albumTitle;

}
