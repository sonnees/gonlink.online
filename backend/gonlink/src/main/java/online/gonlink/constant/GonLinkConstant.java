/*******************************************************************************
 * Class        : GonLinkConstant
 * Created date : 2024/09/06
 * Lasted date  : 2024/09/06
 * Author       : sonnees
 * Change log   : 2024/09/06: 01-00 sonnees create a new
 ******************************************************************************/
package online.gonlink.constant;


/**
 * GonLinkConstant
 *
 * @version 01-00
 * @since 01-00
 * @author sonnees
 */
public final class GonLinkConstant {
    private GonLinkConstant() {
        throw new IllegalStateException("GonLinkConstant class");
    }

    /** Config constant */
    public static final String TIME_ZONE = "UTC";
    public static final String SIMPLE_DATE_FORMAT_YM = "yyyy-MM";
    public static final String QUALIFIER_SIMPLE_DATE_FORMAT_YM = "SIMPLE_DATE_FORMAT_YM";
    public static final String SIMPLE_DATE_FORMAT_YMD = "yyyy-MM-dd";
    public static final String QUALIFIER_SIMPLE_DATE_FORMAT_YMD = "SIMPLE_DATE_FORMAT_YMD";
    public static final String SIMPLE_DATE_FORMAT_YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String QUALIFIER_SIMPLE_DATE_FORMAT_YMD_HMS = "SIMPLE_DATE_FORMAT_YMD_HMS";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";


}
