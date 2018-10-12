package br.com.uece.frameworks.stockfy.util;

import br.com.uece.frameworks.stockfy.model.Imagem;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Create by Bruno Barbosa - 11/10/2018
 */
public class ImageUtil {

    /*
     * Para salvar como Blob usar: BlobProxy.generateProxy(ImageUtil.getImage(...))
     * */

    public static byte[] getImage(Imagem imagem) {
        try {
            return IOUtils.toByteArray(imagem.getImage().getBinaryStream());
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getImage(MultipartFile file) {
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteOutStream);
            return byteOutStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}