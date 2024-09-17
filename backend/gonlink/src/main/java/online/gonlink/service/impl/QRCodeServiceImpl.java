package online.gonlink.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import lombok.AllArgsConstructor;
import online.gonlink.GetStringBase64ImageRequest;
import online.gonlink.GetStringBase64ImageResponse;
import online.gonlink.config.GlobalValue;
import online.gonlink.constant.CommonConstant;
import online.gonlink.exception.enumdef.ExceptionEnum;
import online.gonlink.exception.ResourceException;
import online.gonlink.service.QRCodeService;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
@AllArgsConstructor
public class QRCodeServiceImpl implements QRCodeService {
    private GlobalValue config;

    @Override
    public GetStringBase64ImageResponse getStringBase64Image(GetStringBase64ImageRequest request) {
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(request.getContent(), BarcodeFormat.QR_CODE, request.getWidth(), request.getHeight());

            BufferedImage image = new BufferedImage(request.getWidth(), request.getHeight(), BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < request.getWidth(); x++)
                for (int y = 0; y < request.getHeight(); y++)
                    image.setRGB(x, y, matrix.get(x, y) ? CommonConstant.COLOR_0 : CommonConstant.COLOR_1);

            BufferedImage scaledImage = Scalr.crop(image, config.getSCALR_X(), config.getSCALR_Y(), request.getWidth()-config.getSCALR_WIDTH(), request.getHeight()-config.getSCALR_HEIGHT());

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(scaledImage, CommonConstant.STRING_PNG, outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            return GetStringBase64ImageResponse.newBuilder()
                    .setBase64Image(Base64.getEncoder().encodeToString(imageBytes))
                    .build();

        } catch (Exception e){
            throw new ResourceException(ExceptionEnum.INTERNAL.name(), e);
        }
    }

    @Override
    public String getStringBase64Image(String content) {
        return getStringBase64Image(
                GetStringBase64ImageRequest.newBuilder()
                        .setContent(content)
                        .setHeight(config.getHEIGHT())
                        .setWidth(config.getWIDTH())
                        .build()
                ).getBase64Image();
    }
}
