package util;

public final class ServiceUrlConfig {

    private static final String DEFAULT_HOA_DON_URL = "http://localhost:8080/QuanLyBanHang/rest";
    private static final String DEFAULT_KHACH_HANG_URL = "http://localhost:8080/QuanLyBanHang2/rest";
    private static final String DEFAULT_HANG_HOA_URL = "http://localhost:8080/QuanLyBanHang3/rest";

    private ServiceUrlConfig() {
    }

    public static String hoaDonBaseUrl() {
        return getValue("HOA_DON_BASE_URL", DEFAULT_HOA_DON_URL);
    }

    public static String khachHangBaseUrl() {
        return getValue("KHACH_HANG_BASE_URL", DEFAULT_KHACH_HANG_URL);
    }

    public static String hangHoaBaseUrl() {
        return getValue("HANG_HOA_BASE_URL", DEFAULT_HANG_HOA_URL);
    }

    private static String getValue(String key, String defaultValue) {
        String propertyValue = System.getProperty(key);
        if (propertyValue != null && !propertyValue.trim().isEmpty()) {
            return propertyValue.trim();
        }

        String envValue = System.getenv(key);
        if (envValue != null && !envValue.trim().isEmpty()) {
            return envValue.trim();
        }

        return defaultValue;
    }
}