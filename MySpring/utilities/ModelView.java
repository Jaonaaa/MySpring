package utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class ModelView {

    String url;
    HashMap<String, Object> datas = new HashMap<String, Object>();
    HashMap<String, Object> sessions = new HashMap<String, Object>();
    Boolean isJson = false;
    Boolean invalidate_session = false;
    Vector<String> remove_session = new Vector<String>();

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

    public void removeSession(String key) {
        this.remove_session.add(key);
    }

    public void removeAllSession() {
        this.invalidate_session = true;
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

    public Boolean getInvalidate_session() {
        return invalidate_session;
    }

    public void setInvalidate_session(Boolean invalidate_session) {
        this.invalidate_session = invalidate_session;
    }

    public Vector<String> getRemove_session() {
        return remove_session;
    }

    public void setRemove_session(Vector<String> remove_session) {
        this.remove_session = remove_session;
    }

}
