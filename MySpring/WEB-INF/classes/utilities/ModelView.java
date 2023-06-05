package utilities;

import java.util.HashMap;

public class ModelView {

    String url;
    HashMap<String, Object> datas = new HashMap<String, Object>();

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

    public HashMap<String, Object> getDatas() {
        return datas;
    }

    public void setDatas(HashMap<String, Object> datas) {
        this.datas = datas;
    }

}
