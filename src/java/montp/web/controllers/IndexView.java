package montp.web.controllers;

import montp.api.StockMarketClient;
import montp.data.dao.CotationDAO;
import montp.data.model.Cotation;
import montp.data.model.security.User;
import montp.services.CotationService;
import montp.tools.Logger;
import montp.web.UserSession;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named("index")
public class IndexView extends AbstractDataTableView<Cotation, CotationDAO, CotationService> implements Serializable {

    @Inject private UserSession session;

    @Inject private StockMarketClient stockMarketClient;

    @Inject private CotationService cotationService;

    @PostConstruct
    public void init() {
        Logger.log(Logger.LogLevel.INFO, IndexView.class.getSimpleName(), "initializing view controller");
    }

    public User getHello() {
        return session.getUser();
    }

    public String getCurrentQuote(String symbol){
        return String.format("%.2f",stockMarketClient.getQuote(symbol));
    }

    public Double getCotationAverage(){
        List<Cotation> cotations = cotationService.getCotationsOfUser(session.getUser());
        int count = cotations.size();
        Double moyenne  = 0.00;
        for(Cotation cotation : cotations){
            moyenne += cotation.getPriceQuote();
        }
        return moyenne/count;
    }

    public String getVariation(long id){
        Cotation cotation = service.get(id);
        Double quoteOriginal = cotation.getPriceQuote();

        Double variation = ((stockMarketClient.getQuote(cotation.getSymbol())-quoteOriginal)/quoteOriginal * 100);
        return String.format("%.2f",variation);
    }

    public Double getVariationAverage(){
        List<Cotation> cotations = cotationService.getCotationsOfUser(session.getUser());
        double moyenne  = 0.00;
        for(Cotation cotation : cotations){
            Double quoteOriginal = cotation.getPriceQuote();
            double variation = ((stockMarketClient.getQuote(cotation.getSymbol())-quoteOriginal)/quoteOriginal * 100);
            moyenne += variation;
        }
        return moyenne;
    }
}
