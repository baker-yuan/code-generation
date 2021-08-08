package cn.baker.tool.config;

/**
 * 动态数据源上下文
 *
 * @author yuanyu
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setDataSourceName(String name) {
        CONTEXT_HOLDER.set(name);
    }

    public static String getDataSourceName() {
        return CONTEXT_HOLDER.get();
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
    }
}
