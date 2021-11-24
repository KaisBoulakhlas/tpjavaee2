package montp.data;

import montp.data.model.Cotation;
import montp.data.model.security.Group;
import montp.data.model.security.User;
import montp.services.CotationService;
import montp.services.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Startup
public class Seeder {
    
    @Inject
    private UserService userService;

    @Inject
    private CotationService stockMarketClientService;


    @PersistenceContext
    private EntityManager em;
    
    @PostConstruct
    public void init() {
        if (userService.getGroup("USER") == null) {
            Group groupUser = new Group("USER");
            em.persist(groupUser);
            Group groupAdmin = new Group("ADMIN");
            em.persist(groupAdmin);
            User userUser1 = new User("user1@gmail.com", "user1");
            List<Group> groupes = new ArrayList<>();
            groupes.add(groupUser);
            userUser1.setGroups(groupes);
            userService.insert(userUser1);
            User userAdmin = new User("admin@gmail.com", "admin");
            groupes.add(groupAdmin);
            userAdmin.setGroups(groupes);
            userService.insert(userAdmin);
        }
    }

}
