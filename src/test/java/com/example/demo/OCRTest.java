package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

import com.example.demo.util.VerifyCodeUtils;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCRTest {
	
	@Test
	public void simpleTest() throws TesseractException {
		Tesseract tesseract = new Tesseract();
		URL fileURL = OCRTest.class.getResource("/file/eng.jpg");
		File jpgFile = new File(fileURL.getPath());
		// data目录是testdata的父目录，即file目录的父目录
		tesseract.setDatapath(jpgFile.getParentFile().getParent());
		tesseract.setLanguage("eng");
		String string = tesseract.doOCR(jpgFile);
		System.out.println(string);
	}
	
	@Test
	public void testNumberCode() throws IOException, TesseractException {
		Object[] arr = generateVerifyCode();
		File file = (File) arr[0];
		String code = (String) arr[1];
		System.out.println(code);
		Assert.assertTrue(file.exists());
		Tesseract tesseract = new Tesseract();
		URL dataURL = OCRTest.class.getResource("/tessdata/chi_sim.traineddata");
		File dataFile = new File(dataURL.getPath());
		tesseract.setDatapath(dataFile.getParentFile().getParent());
		tesseract.setLanguage("chi_sim");
		String string = tesseract.doOCR(file);
		System.out.println(string);
		// always fail
		Assert.assertEquals(code, string);
	}
	
	@Test
	public void testResource() {
		URL url = OCRTest.class.getResource("/file/chi.jpg");
		File jpg = new File(url.getFile());
		Assert.assertTrue(jpg.exists());
	}
	
	public Object[] generateVerifyCode() throws IOException {
		URL dirURL = OCRTest.class.getResource("/file");
        File dir = new File(dirURL.getPath());
        int w = 200, h = 80;
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        File file = new File(dir, verifyCode + ".jpg");
        VerifyCodeUtils.outputImage(w, h, file, verifyCode);
        Object[] arr = new Object[2];
        arr[0] = file;
        arr[1] = verifyCode;
        return arr;
	}
}
