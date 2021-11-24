package montp.data.dao;


import montp.api.Company;


import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CompanyDAO extends GenericDAO<Company> {

    public CompanyDAO() {
        super(Company.class);
    }

}
