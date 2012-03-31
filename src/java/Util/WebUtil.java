/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import com.google.gson.Gson;

/**
 *
 * @author Admin
 */
public class WebUtil {
    
    public static Gson gson = new Gson();
    
    public static String js_var(String var_name, Object obj) {
        
        String json = gson.toJson(obj, obj.getClass());
        String js_var = String.format("<script type='text/javascript'> "
                + "var %s=%s;</script>", var_name, json);
        return js_var;
    }

}
