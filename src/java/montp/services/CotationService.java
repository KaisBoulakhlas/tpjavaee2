package montp.services;

import montp.api.Company;
import montp.api.StockMarketClient;
import montp.data.dao.CotationDAO;
import montp.data.model.Cotation;
import montp.data.model.security.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

@ApplicationScoped
public class CotationService extends GenericService<Cotation, CotationDAO> {

    @Inject
    private StockMarketClient stockMarketClient;

    public List<Cotation> getCotationsOfUser(User user) {
        return dao.getCotationsOfUser(user);
    }

    public Collection<Company> getCompanies(){ return stockMarketClient.getCompanies();}

}