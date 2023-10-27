package br.com.rafaelcavalcante.biritashop.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BreadcrumbItem {
    private String queryString;
    private String label;
    private boolean current;
}