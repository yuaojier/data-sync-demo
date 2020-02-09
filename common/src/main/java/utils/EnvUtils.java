package utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;

public class EnvUtils {
    public static boolean isLocalEnv() {
        return StrUtil.equalsIgnoreCase(SystemUtil.get("env"), "local") || hasProperty("idea.version") || hasProperty("idea.launcher.bin.path");
    }

    public static boolean hasProperty(String key) {
        return StrUtil.isNotEmpty(SystemUtil.get(key));
    }
}
