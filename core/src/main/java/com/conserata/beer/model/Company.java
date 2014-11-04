package com.conserata.beer.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * (c) conserata IT-Consulting
 * @author tspaeth
 */
@Entity
@Table(name = "Company")
public class Company extends BaseEntity {

    private String companyName;

    private String companyAddress;

    private Country country;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (companyAddress != null ? !companyAddress.equals(company.companyAddress) : company.companyAddress != null)
            return false;
        if (companyName != null ? !companyName.equals(company.companyName) : company.companyName != null) return false;
        if (country != company.country) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = companyName != null ? companyName.hashCode() : 0;
        result = 31 * result + (companyAddress != null ? companyAddress.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyName='" + companyName + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", country=" + country +
                '}';
    }
}
