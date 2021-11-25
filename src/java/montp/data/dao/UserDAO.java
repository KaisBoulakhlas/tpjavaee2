package montp.data.dao;

import montp.tools.Tools;
import montp.data.model.security.Group;
import montp.data.model.security.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UserDAO extends GenericDAO<User> {

    public UserDAO() {
        super(User.class);
    }

    @SuppressWarnings("unchecked")
    public List<User> getUsers() {
        return em.createQuery("SELECT u FROM User u ORDER BY u.email")
                .getResultList();
    }

    public User getFromEmail(String email) {
        return (User) em.createQuery("SELECT u FROM User u WHERE u.email=:email")
                .setParameter("email", email)
                .getSingleResult();
    }

    public Group getGroup(String groupname) {
        return em.find(Group.class, groupname);
    }

    @Transactional
    public void update(User user) {
        User u = em.find(User.class, user.getId());
        em.createNativeQuery("DELETE FROM SECURITY_USER_GROUP WHERE email=?1")
                .setParameter(1, u.getEmail())
                .executeUpdate();
        u.setEmail(user.getEmail().toLowerCase().trim());
        if ((user.getPassword() != null)
                && (!user.getPassword().trim().isEmpty())) {
            u.setPassword(Tools.digestSHA256Hex(user.getPassword().trim()));
        }
        super.update(u);
        em.createNativeQuery("INSERT INTO SECURITY_USER_GROUP(email,groupname) VALUES(?1,?2)")
                .setParameter(1, u.getEmail())
                .setParameter(2, "USER")
                .executeUpdate();
    }

    @Transactional
    public void changeStatus(User user){
        super.update(user);
    }

}
