package com.fengwuj.main;

import java.io.File;
import java.io.IOException;

public class main {
	public static void main(String[] args) {
		 String pathAndFileName = "demo.png"; 
         String content = "Hello，二维码";
         try {
			QrCodeUtil.writeStr2Pic(content, pathAndFileName);
			System.out.println("生成成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         String respTextString = QrCodeUtil.getInfoFromQrImg(pathAndFileName);
         System.out.println(respTextString);
	}
}
