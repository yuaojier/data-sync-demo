package utils;

import cn.hutool.core.lang.Assert;
import lombok.NonNull;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author maxh
 * @since 1.0 , 2017/11/29 12:39.
 */
public class URLUtils {
    /**
     * url path分隔符
     */
    public static final String PATH_SEPARATOR = "/";

    /**
     * uri与参数连接符
     */
    public static final String URL_PARAM_SEPARATOR = "?";

    /**
     * 多个参数间的连接符
     */
    public static final String URL_PARAMS_SEPARATOR = "&";

    public static final String URL_PARAM_VALUE_SEPARATOR = "=";

    public static WrapperURL create(String basePath, String... paths) {
        return WrapperURL.create(basePath, paths);
    }

    /**
     * URL后添加path, 不支持带有queryParam的basePath
     */
    public static String appendPath(String basePath, String... paths) {
        return WrapperURL.create(basePath, paths).getUrl();
    }

    /**
     * url中添加queryParam
     *
     * @param url   url
     * @param name  参数name
     * @param value 参数值
     */
    public static String appendParam(@NonNull String url, @NonNull String name, String value) {
        return WrapperURL.create(url).appendParam(name, value).getUrl();
    }

    /**
     * 移除URL中指定的参数
     */
    public static String removeParams(@NonNull String url, @NonNull String... removeParams) {
        return WrapperURL.create(url).removeParams(removeParams).getUrl();
    }

    public static class WrapperURL {
        private StringBuilder url;

        public static WrapperURL create(String basePath, String... paths) {
            WrapperURL wrapperURL = new WrapperURL();

            Assert.notNull(paths);
            StringBuilder tempURL = new StringBuilder(basePath);
            String prePath = basePath;
            for (String path : paths) {
                if (StringUtils.isNotEmpty(path)) {
                    if (!(prePath.endsWith(PATH_SEPARATOR) || path.startsWith(PATH_SEPARATOR))) {
                        tempURL.append(PATH_SEPARATOR);
                    }
                    tempURL.append(path);
                    prePath = path;
                }
            }

            wrapperURL.url = tempURL;

            return wrapperURL;
        }

        public String getUrl() {
            return url.toString();
        }

        public WrapperURL appendParam(@NonNull String name, String value) {
            url.append(url.indexOf(URL_PARAM_SEPARATOR) > 0 ? URL_PARAMS_SEPARATOR : URL_PARAM_SEPARATOR);
            url.append(name)
                    .append(URL_PARAM_VALUE_SEPARATOR)
                    .append(StringUtils.defaultString(value));
            return this;
        }

        public WrapperURL removeParams(@NonNull String... removeParams) {
            if (StringUtils.isEmpty(url)) {
                return this;
            }
            int subIndex = url.indexOf(URL_PARAM_SEPARATOR);
            if (subIndex > 0) {
                StringBuilder urlBuilder = new StringBuilder(url.substring(0, subIndex));
                String queryParams = url.substring(subIndex + 1);
                if (StringUtils.isNotEmpty(queryParams)) {
                    String[] params = queryParams.split(URL_PARAMS_SEPARATOR);
                    boolean isFirst = true;
                    for (String param : params) {
                        int nameIndex = param.indexOf(URL_PARAM_VALUE_SEPARATOR);
                        String name = param.substring(0, nameIndex);
                        if (nameIndex > 0 && param.length() > nameIndex + 1 && !ArrayUtils.contains(removeParams, name)) {
                            if (isFirst) {
                                isFirst = false;
                                urlBuilder.append(URL_PARAM_SEPARATOR);
                            } else {
                                urlBuilder.append(URL_PARAMS_SEPARATOR);
                            }
                            urlBuilder.append(name);
                            urlBuilder.append(param.substring(nameIndex));
                        }
                    }
                }
                url = urlBuilder;
            }
            return this;
        }
    }
}
