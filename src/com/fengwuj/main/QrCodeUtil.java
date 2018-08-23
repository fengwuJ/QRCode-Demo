package com.fengwuj.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;

public class QrCodeUtil {
	//
	public static void writeStr2Pic(String content,String pathAndFileName) throws IOException{
		File file = new File(pathAndFileName);
		Qrcode qrcode = new Qrcode();
		qrcode.setQrcodeErrorCorrect('M');	//设置纠错级别
		qrcode.setQrcodeEncodeMode('B');
		qrcode.setQrcodeVersion(7);		//设置Qrcode包版本
		byte[] b = content.getBytes("utf-8");
		BufferedImage bImage = new BufferedImage(139, 139, BufferedImage.TYPE_INT_RGB);
		
		//创建图册
		Graphics2D graphics2d = bImage.createGraphics();
		graphics2d.setBackground(Color.WHITE);   // 设置背景颜色（白色） 
        graphics2d.clearRect(0, 0, 139, 139);    // 矩形 X、Y、width、height 
        graphics2d.setColor(Color.BLACK);    // 设置图像颜色（黑色）
        
        if (b.length > 0 && b.length < 123) { 
            boolean[][] d = qrcode.calQrcode(b); 
            for (int i = 0; i < d.length; i++) { 
                for (int j = 0; j < d.length; j++) { 
                    if (d[j][i]) { 
                        graphics2d.fillRect(j * 3 + 2, i * 3 + 2, 3, 3); 
                    } 
                } 
            } 
        } 
		
     // 释放此图形的上下文以及它使用的所有系统资源。调用 dispose 之后，就不能再使用 Graphics 对象 
        graphics2d.dispose(); 
     // 刷新此 Image 对象正在使用的所有可重构的资源 
        bImage.flush(); 
        ImageIO.write(bImage, "png", file); 
	}
	
	public static String getInfoFromQrImg(String pathAndFileName) {
		File imaFile = null;
		if ("".equals(pathAndFileName)) {
			return "请输入文件路径";
		}else {
			imaFile = new File(pathAndFileName);
		}
		class MyCodeImage implements QRCodeImage{
        	BufferedImage image = null;
        	
        	public MyCodeImage(BufferedImage image){
        		this.image = image;
        	}
        	
			@Override
			public int getHeight() {
				// TODO Auto-generated method stub
				return image.getHeight();
			}

			@Override
			public int getPixel(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return image.getRGB(arg0, arg1);
			}

			@Override
			public int getWidth() {
				// TODO Auto-generated method stub
				return image.getWidth();
			}
        	
        }
		String decodedData = null; 
        QRCodeDecoder decoder = new QRCodeDecoder(); 
        BufferedImage image = null; 
        try {
			image = ImageIO.read(imaFile);
		} catch (IOException e) {
			System.out.println("错误信息："+e.getMessage());
		}
        decodedData = new String(decoder.decode(new MyCodeImage(image)));
        return decodedData;
        		
        
	}
	
}
