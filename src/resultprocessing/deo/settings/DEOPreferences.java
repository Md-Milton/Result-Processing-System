/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultprocessing.deo.settings;

import alertmessage.AlertMaker;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.digest.DigestUtils;
import resultprocessing.utility.ResultProcessingUtil;

/**
 *
 * @author milton
 */
public class DEOPreferences {

    private static final String deoConfig = "deo_config.txt";
    private String username;
    private String passs;

    public DEOPreferences() {
        this.username = "deo";
        this.passs = "pass";
        setPasss(passs);
    }

    
    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasss() {
        return passs;
    }

    public void setPasss(String passs) {
        this.passs = DigestUtils.shaHex(passs);
   }
    
    
//    public void init(){
//        DEOPreferences preferences = new DEOPreferences();
//        ResultProcessingUtil.initializeConfig(preferences, deoConfig);
//    }
    
public static void initConfig(){
    Writer writer = null;
    
        try {
            DEOPreferences preferences = new DEOPreferences();
            Gson gson = new Gson();
            writer = new FileWriter(deoConfig);
            gson.toJson(preferences, writer);
        
        } catch (IOException ex) {
            Logger.getLogger(DEOPreferences.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(DEOPreferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
}
    
public static DEOPreferences getPreferences( ){
            Gson gson = new Gson();
    DEOPreferences preferences = new DEOPreferences();
        try {
            
            preferences = gson.fromJson(new FileReader(deoConfig),DEOPreferences.class);
            
        } catch (IOException ex) {
            Logger.getLogger(DEOPreferences.class.getName()).log(Level.SEVERE, null, ex);
            initConfig();
        }
    
    return preferences;
}

public static void updateConfig(DEOPreferences preferences){
    Writer writer = null;
    
        try {
            Gson gson = new Gson();
            writer = new FileWriter(deoConfig);
            gson.toJson(preferences, writer);
            AlertMaker.showConfirmation("success");
        } catch (IOException ex) {
            Logger.getLogger(DEOPreferences.class.getName()).log(Level.SEVERE, null, ex);
            AlertMaker.showError("Not Updated");
        }finally{
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(DEOPreferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
}


}
