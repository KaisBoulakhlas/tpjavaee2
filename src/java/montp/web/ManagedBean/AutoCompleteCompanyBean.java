package montp.web.ManagedBean;

import montp.api.Company;
import montp.api.StockMarket;

import java.util.Collection;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AutoCompleteCompanyBean {

    private String companyName;
    private List<Company> companyList;

    @Inject
    private StockMarket stockMarket;

    public Collection<Company> companies(String query){
        return stockMarket.getCompanies(query);
    }

    // Method To Display The Country List On The JSF Page
    public List<Company> getCompanyList() {
        return companyList;
    }
}
