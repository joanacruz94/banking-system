package com.server.dto;

import com.server.model.Address;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountHolderDTO {
    private Long id;
    private String name;
    private String email;
    private String dateOfBirth;
    private Address address;
    private List<Long> accountsID;
}
