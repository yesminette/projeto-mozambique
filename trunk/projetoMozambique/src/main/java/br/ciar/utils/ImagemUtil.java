/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ciar.utils;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

/**
 *
 * @author Lauro
 */
public class ImagemUtil {

    public static float IMAGE_QUALITY = 1.0f;

    /**
     * Redimensiona uma imagem.
     *
     * @param imgName A imagem a ser redimensionada. Deve ser o caminho completo para a imagem.
     * @param maxWidth A largura máxima permitida para a imagem.
     * @param maxHeight A altura máxima permitida para a imagem.
     * @return Uma imagem tipo <code>BufferedImage</code> redimensionada.
     * @throws IOException Se o arquivo não for encontrado
     */
    public static BufferedImage resizeImage(String imgName, TiposImagens type, int maxWidth, int maxHeight) throws IOException
    {
        return resizeImage(ImageIO.read(new File(imgName)), type, maxWidth, maxHeight);
    }

    /**
     * Redimensiona uma imagem.
     *
     * @param image A imagem a ser redimensionada.
     * @param maxWidth A largura máxima permitida para a imagem.
     * @param maxHeight A altura máxima permitida para a imagem.
     * @return Uma imagem tipo <code>BufferedImage</code> redimensionada.
     */
    public static BufferedImage resizeImage(Image image, TiposImagens type, int maxWidth, int maxHeight)
    {
        float zoom = 1.0F;
        Dimension largestDimension = new Dimension(maxWidth, maxHeight);

        // Tamanho Original
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);

        float aspectRation = (float)imageWidth / imageHeight;

        if (imageWidth > maxWidth || imageHeight > maxHeight) {
            int scaledW = Math.round(imageWidth * zoom);
            int scaledH = Math.round(imageHeight * zoom);

            Dimension preferedSize = new Dimension(scaledW, scaledH);

            if ((float)largestDimension.width / largestDimension.height > aspectRation) {
                largestDimension.width = (int)Math.ceil(largestDimension.height * aspectRation);
            }
            else {
                largestDimension.height = (int)Math.ceil((float)largestDimension.width / aspectRation);
            }

            imageWidth = largestDimension.width;
            imageHeight = largestDimension.height;
        }

        return createBufferedImage(image, type, imageWidth, imageHeight);
    }

    /**
     * Salva uma imagem no disco.
     *
     * @param image A imagem a ser salva.
     * @param toFileName O nome do arquivo a ser usado.
     * @param type O tipo da imagem. <code>TiposImagens.IMAGE_JPEG</code> salvará como JPEG, 
     *  ou <code>TiposImagens.IMAGE_PNG</code> salvará como PNG.
     * @return <code>false</code> se não for possível gravar a imagem.
     * @throws IOException
     */
    public static boolean saveImage(BufferedImage image, String toFileName, TiposImagens type) throws IOException
    {
        File file = new File(toFileName);
        file.setExecutable(true);
        file.setReadable(true);
        file.setWritable(true,true);
        return ImageIO.write(image, type == TiposImagens.IMAGE_JPEG ? "jpg" : "png", file);
    }

    /**
     * Comprime a imagem e salva no disco.
     * Esse método só suporta imagens JPEG.
     *
     * @param image A imagem a ser salva.
     * @param toFileName O nome do arquivo a ser usado.
     * @param type O tipo da imagem. <code>TiposImagens.IMAGE_JPEG</code> salvará como JPEG,
     *  ou <code>TiposImagens.IMAGE_PNG</code> salvará como PNG.
     * @return <code>false</code> se não for possível gravar a imagem.
     * @throws IOException
     */
    public static void saveCompressedImage(BufferedImage image, String toFileName, TiposImagens type) throws IOException
    {
        if (type == TiposImagens.IMAGE_PNG) {
            throw new UnsupportedOperationException("PNG compression not implemented");
        }

        ImageWriter writer = null;

        Iterator iter = ImageIO.getImageWritersByFormatName("jpg");
        writer = (ImageWriter)iter.next();

        File file = new File(toFileName);
        file.setExecutable(true);
        file.setReadable(true);
        file.setWritable(true,true);

        ImageOutputStream ios = ImageIO.createImageOutputStream(file);
        writer.setOutput(ios);

        ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());

        iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        iwparam.setCompressionQuality(IMAGE_QUALITY);

        writer.write(null, new IIOImage(image, null, null), iwparam);

        ios.flush();
        writer.dispose();
        ios.close();
    }

    /**
     * Cria uma <code>BufferedImage</code> dada uma <code>Image</code>.
     *
     * @param image A imagem a ser convertida.
     * @param largura A largura desejada para a imagem.
     * @param altura A altura desejada para a imagem.
     * @return A imagem convertida.
     */
    public static BufferedImage createBufferedImage(Image image, TiposImagens imageType, int largura, int altura)
    {
        int type;
        if (imageType == TiposImagens.IMAGE_PNG && hasAlpha(image)) {
            type = BufferedImage.TYPE_INT_ARGB;
        }
        else {
            type = BufferedImage.TYPE_INT_RGB;
        }

        BufferedImage bi = new BufferedImage(largura, altura, type);

        Graphics g = bi.createGraphics();
        g.drawImage(image, 0, 0, largura, altura, null);
        g.dispose();

        return bi;
    }

    /**
     * Determina se a imagem possui canais alfa (pixels trasnparentes).
     *
     * @param image A imagem a ser verificada se possui pixels transparentes.
     * @return <code>true</code> ou <code>false</code>, dependendo do resultado.
     * @throws InterruptedException
     */
    public static boolean hasAlpha(Image image)
    {
        try {
            PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
            pg.grabPixels();

            return pg.getColorModel().hasAlpha();
        }
        catch (InterruptedException e) {
            return false;
        }
    }
}
