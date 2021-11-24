package montp.data.dao;


import montp.data.model.Cotation;
import montp.data.model.security.User;


import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CotationDAO extends GenericDAO<Cotation> {

    public CotationDAO() {
        super(Cotation.class);
    }

    @SuppressWarnings("unchecked")
    public List<Cotation> getCotationsOfUser(User user) {
        return em.createQuery("SELECT s FROM Cotation s WHERE s.user = :user")
                .setParameter("user", user)
                .getResultList();
    }

}
