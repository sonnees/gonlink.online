package online.gonlink.util;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

public class IPUtils {
    public static String validateAndGetIP(String ip, String defaultIP) {
        // Nếu IP null hoặc rỗng
        if (ip == null || ip.trim().isEmpty()) {
            return defaultIP;
        }

        // Danh sách các IP localhost
        List<String> localIPs = Arrays.asList(
                "::1",                    // IPv6 localhost
                "127.0.0.1",             // IPv4 localhost
                "0:0:0:0:0:0:0:1",       // IPv6 localhost đầy đủ
                "localhost"
        );

        // Nếu là địa chỉ localhost
        if (localIPs.contains(ip)) {
            return defaultIP;
        }

        // Kiểm tra IP private
        if (isPrivateIP(ip)) {
            return defaultIP;
        }

        // Kiểm tra format IP hợp lệ
        if (!isValidPublicIP(ip)) {
            return defaultIP;
        }

        return ip;
    }

    private static boolean isPrivateIP(String ip) {
        try {
            if (ip.contains(":")) {  // IPv6
                return false;  // Tạm thời bỏ qua kiểm tra IPv6 private
            }

            InetAddress addr = InetAddress.getByName(ip);

            // Kiểm tra các dải IP private
            return addr.isSiteLocalAddress() ||
                    addr.isLinkLocalAddress() ||
                    addr.isLoopbackAddress();
        } catch (Exception e) {
            return true;  // Nếu có lỗi, coi như IP không hợp lệ
        }
    }

    private static boolean isValidPublicIP(String ip) {
        try {
            if (ip.contains(":")) {  // IPv6
                return InetAddress.getByName(ip).isReachable(1000);
            }

            // Validate format IPv4
            String[] parts = ip.split("\\.");
            if (parts.length != 4) {
                return false;
            }

            for (String part : parts) {
                int value = Integer.parseInt(part);
                if (value < 0 || value > 255) {
                    return false;
                }
            }

            // Kiểm tra một số dải IP đặc biệt
            if (ip.startsWith("0.") ||
                    ip.startsWith("10.") ||
                    ip.startsWith("192.168.") ||
                    ip.startsWith("172.16.") ||
                    ip.startsWith("169.254.")) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}