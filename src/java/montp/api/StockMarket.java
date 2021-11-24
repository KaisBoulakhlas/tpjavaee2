package montp.api;

import java.util.Collection;
import java.util.Set;

public interface StockMarket {
    
    /**
     * Returns the list of the quoted companies
     * @return collection of companies
     */
    public Collection<Company> getCompanies();
    
    /**
     * Returns the company corresponding to the unique symbol
     * @param symbol the unique symbol to search for
     * @return a company or null if the symbol is not found
     */
    public Company getCompany(String symbol);
    
    /**
     * Return a list of companies which name contains the given string
     * @param search string to search for
     * @return a collection of companies, can be empty (but not null!)
     */
    public Collection<Company> getCompanies(String search);

    /**
     * Call this method at regular intervals to get the current quotes.
     * @param companies collection of companies.
     * @return the current quotes of the companies.
     */
    public Collection<Quote> getQuotes(Set<String> companiesSymbols);

}
