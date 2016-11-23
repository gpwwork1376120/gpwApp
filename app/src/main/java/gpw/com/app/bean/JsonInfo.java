package gpw.com.app.bean;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.Map;

public class JsonInfo implements Serializable {

    private JsonObject jsonObject;

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}