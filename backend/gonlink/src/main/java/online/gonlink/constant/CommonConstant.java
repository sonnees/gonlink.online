/*******************************************************************************
 * Class        : CommonConstant
 * Created date : 2024/09/06
 * Lasted date  : 2024/09/06
 * Author       : sonnees
 * Change log   : 2024/09/06: 01-00 sonnees create a new
 ******************************************************************************/
package online.gonlink.constant;


/**
 * CommonConstant
 *
 * @version 01-00
 * @since 01-00
 * @author sonnees
 */
public final class CommonConstant {
    private CommonConstant() {
        throw new IllegalStateException("CommonConstant class");
    }

    /** Config constant */
    public static final String STRING_PNG = "png";
    public static final int COLOR_0 = 0xFF000000;
    public static final int COLOR_1 = 0xFFFFFFFF;
    public static final String TIME_ZONE = "UTC";
    public static final String SIMPLE_DATE_FORMAT_YM = "yyyy-MM";
    public static final String QUALIFIER_SIMPLE_DATE_FORMAT_YM = "SIMPLE_DATE_FORMAT_YM";
    public static final String SIMPLE_DATE_FORMAT_YMD = "yyyy-MM-dd";
    public static final String QUALIFIER_SIMPLE_DATE_FORMAT_YMD = "SIMPLE_DATE_FORMAT_YMD";
    public static final String SIMPLE_DATE_FORMAT_YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String QUALIFIER_SIMPLE_DATE_FORMAT_YMD_HMS = "SIMPLE_DATE_FORMAT_YMD_HMS";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";


    /** Config constant */
    public static final String COLLECTION_SHORT_URL = "short_urls";


    /** Grpc handle constant */
    public static final String GRPC_SUCCESS = "Thành công";

    public static final String GRPC_FAIL = "Thất bại";



}
