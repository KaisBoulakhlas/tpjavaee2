package montp.api;

import java.io.Serializable;

/**
 * Hold the quoted value of a company.
 */
public class Quote implements Serializable {
    
    private Company company;
    private Double quote;

    public Quote() {
    }

    public Quote(Company company, Double quote) {
        this.company = company;
        this.quote = quote;
    }
    
    public Company getCompany() {
        return company;
    }

    public Double getQuote() {
        return quote;
    }

    public void setQuote(Double quote) {
        this.quote = quote;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
