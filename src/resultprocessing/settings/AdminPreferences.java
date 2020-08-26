/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.settings;
import alertmessage.AlertMaker;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.digest.DigestUtils;
/**
 *
 * @author milton
 */
public class AdminPreferences {
    private static final String adminfile_config = "adminConfig.txt";
    private String usename;
    private String password;

    public AdminPreferences() {
        this.usename = "admin";
        this.password = "admin";
        setPassword(this.password);
    }

    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = DigestUtils.shaHex(password);
    }
    
    public static void initConfig(){
        Writer writer = null;
        
        try {
        AdminPreferences admin = new AdminPreferences();
        Gson gson = new Gson();
        writer = new FileWriter(adminfile_config);
        gson.toJson(admin, writer);
        
        } catch (IOException ex) {
            Logger.getLogger(AdminPreferences.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(AdminPreferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void updateAdminConfig(AdminPreferences admin){
        Writer writer = null;
        
        try {
            Gson gson = new Gson();
            writer = new FileWriter(adminfile_config);
            gson.toJson(admin, writer);
            AlertMaker.showConfirmation("Success");
        } catch (IOException ex) {
            Logger.getLogger(AdminPreferences.class.getName()).log(Level.SEVERE, null, ex);
            AlertMaker.showError("Fail");
        }finally{
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(AdminPreferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public static AdminPreferences getPreferences(){
        Gson gson = new Gson();
        AdminPreferences preferences = new AdminPreferences();
        
        try {
            preferences = gson.fromJson(new FileReader(adminfile_config), AdminPreferences.class);
                    } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminPreferences.class.getName()).log(Level.SEVERE, null, ex);
            initConfig();
        }
        return preferences;
    }
}
