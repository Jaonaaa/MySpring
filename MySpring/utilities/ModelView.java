package utilities;

import java.util.HashMap;

public class ModelView {

    String url;
    HashMap<String, Object> datas = new HashMap<String, Object>();
    HashMap<String, Object> sessions = new HashMap<String, Object>();
    Boolean isJson = false;

    public ModelView() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addItem(String key, Object value) {
        this.datas.put(key, value);
    }

    public void addSession(String key, Object value) {
        this.sessions.put(key, value);
    }

    public HashMap<String, Object> getDatas() {
        return datas;
    }

    public HashMap<String, Object> getSessions() {
        return sessions;
    }

    public void setSessions(HashMap<String, Object> sessions) {
        this.sessions = sessions;
    }

    public void setDatas(HashMap<String, Object> datas) {
        this.datas = datas;
    }

    public Boolean getIsJson() {
        return isJson;
    }

    public void setIsJson(Boolean isJson) {
        this.isJson = isJson;
    }

}
