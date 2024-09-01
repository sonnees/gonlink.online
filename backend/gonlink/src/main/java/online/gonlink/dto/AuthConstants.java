package online.gonlink.dto;

import io.grpc.Context;

public class AuthConstants {
    public static final Context.Key<String> USER_EMAIL = Context.key("user_email");
    public static final Context.Key<String> USER_NAME = Context.key("user_name");
    public static final Context.Key<String> USER_AVATAR = Context.key("user_avatar");
    public static final Context.Key<String> USER_ROLE = Context.key("user_role");
}
