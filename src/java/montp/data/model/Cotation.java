package montp.data.model;

import montp.data.model.security.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Cotation extends GenericEntity {

    @ManyToOne
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT NOT NULL")
    private String symbol;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private Double priceQuote;

    public Cotation() {

    }

    public Cotation(String company,String symbol, User user, Double priceQuote) {
        this.symbol = symbol;
        this.user = user;
        this.company = company;
        this.priceQuote = priceQuote;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getPriceQuote() {
        return priceQuote;
    }

    public void setPriceQuote(Double priceQuote) {
        this.priceQuote = priceQuote;
    }
}
