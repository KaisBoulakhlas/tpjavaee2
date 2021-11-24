package montp.web.controllers;

import montp.api.StockMarketClient;
import montp.data.model.Cotation;
import montp.services.CotationService;
import montp.web.UserSession;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Named("dtColumnsView")
@ViewScoped
public class ColumnsView implements Serializable {

    private String columnTemplate = "symbol company";
    private List<ColumnModel> columns;
    private List<Cotation> cotations;
    private List<Cotation> filteredCotations;
    private Map<String, Class> validColumns;
    @Inject private StockMarketClient stockMarketClient;

    @Inject
    private CotationService service;

    @Inject private UserSession session;

    @PostConstruct
    public void init() {
        cotations = service.getCotationsOfUser(session.getUser());

        validColumns = Stream.of(Cotation.class.getDeclaredFields()).collect(Collectors.toMap(Field::getName, Field::getType));
        createDynamicColumns();
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    public List<Cotation> getCotations() {
        return cotations;
    }

    public String getCompanyName(String symbol){
        return stockMarketClient.getCompany(symbol).getName();
    }


    public void setCotations(List<Cotation> cotations) {
        this.cotations = cotations;
    }

    public List<Cotation> getFilteredCotations() {
        return filteredCotations;
    }

    public void setFilteredCotations(List<Cotation> filteredCotations) {
        this.filteredCotations = filteredCotations;
    }

    public CotationService getService() {
        return service;
    }

    public void setService(CotationService service) {
        this.service = service;
    }

    public String getColumnTemplate() {
        return columnTemplate;
    }

    public void setColumnTemplate(String columnTemplate) {
        this.columnTemplate = columnTemplate;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    private void createDynamicColumns() {
        String[] columnKeys = columnTemplate.split(" ");
        columns = new ArrayList<>();

        for (String columnKey : columnKeys) {
            String key = columnKey.trim();

            if (validColumns.containsKey(key)) {
                columns.add(new ColumnModel(columnKey.toUpperCase(), columnKey, validColumns.get(key)));
            }
        }
    }

    public void updateColumns() {
        //reset table state
        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":formDataTable:dataTable");
        table.setValueExpression("sortBy", null);

        //update columns
        createDynamicColumns();
    }

    public static class ColumnModel implements Serializable {

        private String header;
        private String property;
        private String type;
        private Class<?> klazz;

        public ColumnModel(String header, String property, Class klazz) {
            this.header = header;
            this.property = property;
            this.klazz = klazz;
            initType();
        }

        public String getHeader() {
            return header;
        }

        public String getProperty() {
            return property;
        }

        public String getType() {
            return type;
        }

        public Class<?> getKlazz() {
            return klazz;
        }

        private void initType() {
            if (Temporal.class.isAssignableFrom(klazz)) {
                type = "date";
            }
            else if (klazz.isEnum()) {
                type = "enum";
            }
        }
    }
}