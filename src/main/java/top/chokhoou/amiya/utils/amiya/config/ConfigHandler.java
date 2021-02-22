package top.chokhoou.amiya.utils.amiya.config;

import cn.hutool.json.JSONUtil;

import java.util.List;

public interface ConfigHandler {



    /**
     * 获取配置
     *
     * @param dataId key
     * @return string
     */
    String getConfigString(String dataId);

    /**
     * 获取配置，若获取不到，则返回预先给定的默认值
     *
     * @param dataId       key
     * @param clazz        目标类型
     * @param defaultValue 默认值
     * @return object
     */
    default <T> T getOrDefault(String dataId, Class<T> clazz, T defaultValue) {
        String configString = getConfigString(dataId);
        if (configString == null) {
            return defaultValue;
        }
        return JSONUtil.toBean(getConfigString(dataId), clazz);
    }

    /**
     * 获取配置String，若获取不到，则返回预先给定的默认值
     *
     * @param dataId        key
     * @param defaultString 默认值
     * @return string
     */
    default String getStringOrDefault(String dataId, String defaultString) {
        return getOrDefault(dataId, String.class, defaultString);
    }

    /**
     * 获取配置,将其转换为object
     *
     * @param dataId key
     * @param clazz  目标类型
     * @return <T>
     */
    default <T> T getConfigObject(String dataId, Class<T> clazz) {
        return JSONUtil.toBean(getConfigString(dataId), clazz);
    }

    /**
     * 获取配置,将其转换为List
     *
     * @param dataId key
     * @param clazz  目标类型
     * @return List<T>
     */
    default <T> List<T> getConfigObjectList(String dataId, Class<T> clazz) {
        return JSONUtil.toList(getConfigString(dataId), clazz);
    }
}