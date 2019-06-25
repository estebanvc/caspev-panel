package com.caspev.panel.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class NfcCardModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String uuid;

    @NotEmpty
    @NotNull
    @Size(min = 11, max = 11)
    private String code;
}
