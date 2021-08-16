package services;

import dataaccess.UserDB;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;

public class AccountService {
    
    public User login(String email, String password, String path) {
        UserDB userDB = new UserDB();
        
        try {
            User user = userDB.get(email);
            if (password.equals(user.getPassword())) {
                Logger.getLogger(AccountService.class.getName()).log(Level.INFO, "Successful login by {0}", email);
                
 
                
                
                
                /*
                String body = "Successful login by " + user.getFirstName() + " on " + (new java.util.Date()).toString();
                GmailService.sendMail(email, "Successful Login", body, false);
                */
                
                
                String to = user.getEmail();
                String subject = "Email Confirmation";
                String template = path + "/emailtemplates/emailConfirmation.html";
                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("date", (new java.util.Date()).toString());
                
                GmailService.sendMail(to, subject, template, tags);
                
                return user;
            }
        } catch (Exception e) {
        }
        
        return null;
    }
    public void resetPassword(String email, String path, String url) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        String uuid = UUID.randomUUID().toString();
        String link = url + "?uuidPassword=" + uuid;
        user.setResetPasswordUUID(uuid);
        userDB.update(user);
        
        String to = user.getEmail();
                String subject = "Reset Password";
                String template = path + "/emailtemplates/resetpassword.html";
                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("link", link);
                
                GmailService.sendMail(to, subject, template, tags);
    }
    public void newResetPassword(String email, String password) throws Exception{
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        user.setPassword(password);
        user.setResetPasswordUUID(null);
        userDB.update(user);
        
    }
    public User uuidPassword(String uuid) throws Exception{
        UserDB userDB = new UserDB();
        List<User> userList =  userDB.getAll();

        
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getResetPasswordUUID() != null) {
                if (userList.get(i).getResetPasswordUUID().equals(uuid)) {
                    return userList.get(i);
                }
            }    
        }
        return null;
    }
    
}
