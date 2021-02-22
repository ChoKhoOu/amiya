package top.chokhoou.amiya.utils.amiya.config;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ChoKhoOu
 */
public class JsonUtil implements AmiyaConfig {

    /*************** group id start **************/
    private static final String JSON_PATH_GROUP_AMIYA = "amiya.json";
    /*************** group id end **************/

    private final Map<String, ConfigHandler> handlerMap = new ConcurrentHashMap<>();
    private final Map<String, JSONObject> jsonObjectMap = new ConcurrentHashMap<>();

    @Override
    public ConfigHandler amiya() {
        return handlerMap.computeIfAbsent(JSON_PATH_GROUP_AMIYA, k -> new JsonGroup(JSON_PATH_GROUP_AMIYA));
    }

    private String getConfig(String groupId, String dataId) {
        JSONObject jsonObject = jsonObjectMap.computeIfAbsent(groupId, k -> {
            File file = new File(JSON_PATH_GROUP_AMIYA);
            return JSONUtil.readJSONObject(file, StandardCharsets.UTF_8);
        });

        return jsonObject.getStr(dataId);
    }

    private class JsonGroup extends AbstractGroup {
        private final String path;

        public JsonGroup(String path) {
            this.path = path;
        }

        @Override
        public String getConfigString(String dataId) {
            return getConfig(path, dataId);
        }
    }
}
