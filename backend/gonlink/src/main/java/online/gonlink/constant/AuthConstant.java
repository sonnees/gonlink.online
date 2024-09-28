package online.gonlink.constant;

import io.grpc.Context;

public class AuthConstant {
    public static final Context.Key<String> USER_EMAIL = Context.key("user_email");
    public static final Context.Key<String> USER_NAME = Context.key("user_name");
    public static final Context.Key<String> USER_AVATAR = Context.key("user_avatar");
    public static final Context.Key<String> USER_ROLE = Context.key("user_role");

    public static final Context.Key<String> IP = Context.key("ip");
    public static final Context.Key<String> HOST_NAME = Context.key("hostname");
    public static final Context.Key<String> CITY = Context.key("city");
    public static final Context.Key<String> REGION = Context.key("region");
    public static final Context.Key<String> COUNTRY = Context.key("country");
    public static final Context.Key<String> LOC = Context.key("loc");
    public static final Context.Key<String> ORG = Context.key("org");
    public static final Context.Key<String> POSTAL = Context.key("postal");
    public static final Context.Key<String> TIMEZONE = Context.key("timezone");

}
