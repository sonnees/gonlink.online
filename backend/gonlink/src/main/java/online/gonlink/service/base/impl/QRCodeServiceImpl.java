package online.gonlink.service.base.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import online.gonlink.config.GlobalValue;
import online.gonlink.exception.enumdef.ExceptionEnum;
import online.gonlink.exception.ResourceException;
import online.gonlink.service.base.QRCodeService;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QRCodeServiceImpl implements QRCodeService {
    GlobalValue config;

    @Override
    public String getStringBase64Image(String content, int width, int height) {
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height);

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++)
                for (int y = 0; y < height; y++)
                    image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);

            BufferedImage scaledImage = Scalr.crop(image, 30, 30, width-60, height-60);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(scaledImage, "png", outputStream);
            byte[] imageBytes = outputStream.toByteArray();

            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e){
            throw new ResourceException(ExceptionEnum.INTERNAL.name(), e);
        }
    }

    @Override
    public String getStringBase64Image(String content) {
        return getStringBase64Image(content, config.getWIDTH(), config.getHEIGHT());
    }
}
