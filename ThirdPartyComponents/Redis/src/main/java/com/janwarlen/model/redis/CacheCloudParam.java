package com.janwarlen.model.redis;

/**
 * @ClassName: CacheCloudParam
 * @author: janwarlen
 * @Date: 2019/12/23 19:12
 * @Description:
 */
public class CacheCloudParam {

    private String cachecloudUrl;

    private Integer maxRedirections;

    public String getCachecloudUrl() {
        return cachecloudUrl;
    }

    public void setCachecloudUrl(String cachecloudUrl) {
        this.cachecloudUrl = cachecloudUrl;
    }

    public Integer getMaxRedirections() {
        return maxRedirections;
    }

    public void setMaxRedirections(Integer maxRedirections) {
        this.maxRedirections = maxRedirections;
    }
}
