package mapfishapp.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AbstractController {

    static final String SESSION_POST_DATA_STORE = "SESSION_POST_DATA_STORE";

    public AbstractController() {
        super();
    }

    protected Map<String, Object> createModelFromStringOrSession(HttpServletRequest request, String str) {
        HttpSession session = request.getSession();
        if (str == null) {
            str = (String) session.getAttribute(SESSION_POST_DATA_STORE);
        } else {
            session.setAttribute(SESSION_POST_DATA_STORE, str);
        }
    
        if (str == null || str.trim() == "") {
            session.setAttribute(SESSION_POST_DATA_STORE, null);
            str = null;
        }
    
        Map<String, Object> model;
        try {
            if (str != null) {
                model = createModelFromString(request, str);
            } else {
                model = defaultModel(request);
            }
        } catch (Throwable e) {
            model = defaultModel(request);
    
        }
        return model;
    }

    Map<String, Object> defaultModel(HttpServletRequest request) {
        Map<String, Object> model;
        model = new HashMap<String, Object>();
        model.put("data", "null");
        model.put("debug", Boolean.parseBoolean(request.getParameter("debug")));
    
        return model;
    }

    Map<String, Object> createModelFromString(HttpServletRequest request, String str) {
        JSONObject jsonData;
        String data;
        try {
            jsonData = new JSONObject(str);
        } catch (JSONException e) {
            throw new RuntimeException("Cannot parse the json post data", e);
        }
    
        boolean debug;
        if (request.getParameter("debug") == null) {
            try {
                debug = jsonData.getBoolean("debug");
            } catch (JSONException e) {
                debug = false;
            }
        } else {
            debug = Boolean.parseBoolean(request.getParameter("debug"));
        }
    
        try {
            JSONArray jsonLayers, jsonServices;
    
            jsonLayers = jsonData.optJSONArray("layers");
            
            jsonServices = jsonData.optJSONArray("services");
            if (jsonLayers == null) {
                jsonLayers = jsonServices;
            } else {
                jsonLayers = new JSONArray(jsonLayers.toString(1).replaceAll("layername", "name").replaceAll("WMS", "WMSLayer"));
                
                if(jsonServices!=null) {
                    jsonServices = new JSONArray(jsonServices.toString(1).replaceAll("text", "name"));
                    for (int i = 0; i < jsonServices.length(); i++) {
                        jsonLayers.put(jsonServices.get(i));
                    }
                }
            }
            if (jsonLayers != null) {
                data = jsonLayers.toString(1).replaceAll("owstype", "type").replaceAll("owsurl", "url");
            } else {
                data = "[]";
            }
        } catch (JSONException e) {
            data = "[]";
        }
    
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("debug", debug);
        model.put("data", data);
    
        return model;
    }

    protected String getPostData(HttpServletRequest request) throws IOException {
        String str = request.getParameter("data");
    
        if (str == null) {
            // there is no "data" param: we should parse raw post data
            BufferedReader postData = request.getReader();
            StringBuilder stringBuilder = new StringBuilder();
            String cur;
            while ((cur = postData.readLine()) != null) {
                stringBuilder.append(cur).append("\n");
            }
            if (stringBuilder.length() > 0) {
                str = stringBuilder.toString();
            }
        }
        return str;
    }

}