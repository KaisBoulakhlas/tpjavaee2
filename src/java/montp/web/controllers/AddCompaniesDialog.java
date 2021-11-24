package montp.web.controllers;

import montp.api.Company;
import montp.api.StockMarket;
import montp.api.StockMarketClient;
import montp.data.dao.CotationDAO;
import montp.data.model.Cotation;
import montp.services.CotationService;
import montp.tools.Logger;
import montp.web.FacesTools;
import montp.web.UserSession;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@ViewScoped
@Named
public class AddCompaniesDialog extends AbstractDialog<Cotation, CotationDAO, CotationService> {

    @Override
    public void instanciate() {
        instance = new Cotation();
    }

    @Inject
    private UserSession session;

    @Inject private StockMarketClient client;

    @Inject private CotationService service;

    private String companyName;
    private List<Company> companyList;

    @Inject
    private StockMarket stockMarket;

    private Company company;
    private final List<Company> companies = new LinkedList<Company>();

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Company> getCompanies(){
        return companies;
    }

    public List<Company> companiesyByQuery(String query){
        return (List<Company>) stockMarket.getCompanies(query);
    }

    // Method To Display The Country List On The JSF Page
    public List<Company> getCompanyList() {
        return companyList;
    }

    public void save(){
        for(Company company : companies){
            Cotation cotation = new Cotation(company.getName(),company.getSymbol(),session.getUser(),getQuote(company));
            service.insert(cotation);
        }
        FacesTools.redirect("index");
    }

    public void add(){
        if(this.company != null)
            this.companies.add(this.company);
    }

    private Double getQuote(Company company){
        return client.getQuote(company);
    }
}
