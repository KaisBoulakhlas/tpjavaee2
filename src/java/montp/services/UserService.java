package montp.services;

import montp.data.dao.UserDAO;
import montp.data.model.security.Group;
import montp.data.model.security.User;
import montp.tools.Tools;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class UserService extends GenericService<User, UserDAO> {

    public List<User> getUsers() {
        return dao.getUsers();
    }

    public User getFromUsername(String username) {
        return dao.getFromUsername(username.toLowerCase().trim());
    }

    public Group getGroup(String groupname) {
        return dao.getGroup(groupname.toUpperCase().trim());
    }

    public boolean isActive(User user) {
        User u = dao.get(user.getId());
        if (u == null) return false;
        return u.getPassword() != null;
    }

    @Transactional
    public void disable(User user) {
        String password = user.getPassword();
        user.setOldPassword(password);
        user.setPassword(null);
        super.update(user);
    }

    @Transactional
    public void enable(User user) {
        String password = user.getOldPassword();
        user.setPassword(password);
        user.setOldPassword(null);
        super.update(user);
    }

    @Transactional
    public void insert(User user) {
        if (user.getGroups() == null) {
            List<Group> groupes = new LinkedList<>();
            groupes.add(getGroup("USER"));
            user.setGroups(groupes);
        }
        user.setPassword(Tools.digestSHA256Hex(user.getPassword().trim()));
        user.setUserName(user.getUserName().toLowerCase().trim());
        super.insert(user);
    }

    public boolean checkIfExists(String username) {
        return getFromUsername(username) != null;
    }
}
