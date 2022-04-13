package com.bekjon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Bekjon Bakhromov
 * @created 13.04.2022-12:25 PM
 */
@Getter
@Setter
public class MessageDto implements Serializable {
    @JsonProperty("name")
    String name;

    @JsonProperty("message")
    String message;


}
