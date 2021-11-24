package montp.web.converters;


import montp.api.Company;
import montp.api.StockMarketClient;
import montp.tools.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

@FacesConverter("company")
public class CompanyConverter implements GenericCompanyConverter {

    private StockMarketClient client;

    public CompanyConverter(){
        client = new StockMarketClient();
    }

    @Override
    public Company getAsObject(FacesContext context, UIComponent component, String value) {
        Logger.info("value : "+value);
        return client.getCompany(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Company value) {
        if (value == null) return "";
        if (value.getSymbol() == null) return "";
        return value.getSymbol();
    }
}