package com.bjfu.wordlates.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private Long id;
    private String foodName;
    private String inspectionInstituteName;
    private String companyName;
    private String testingInstituteName;
}
