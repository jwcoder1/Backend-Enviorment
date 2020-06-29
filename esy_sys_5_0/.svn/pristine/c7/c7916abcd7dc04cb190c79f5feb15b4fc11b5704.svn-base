package org.esy.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * 
 * 配置文件值读取工具
 * @author Victor 2014/11/12
 *
 */
public class FileUtils {

	/**
	 * 取得文本文件内容
	 * @param filePath 文件路径
	 * @param encoding 文件编码
	 * @return 文件行记录
	 * @throws Exception 
	 */
	public static List<String> readTxtFile(String filePath, String encoding) throws Exception {

		List<String> result;
		File file = new File(filePath);
		if (file.isFile() && file.exists()) { //判断文件是否存在
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);//考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			result = getTextList(bufferedReader);
			read.close();
		} else {
			System.out.println("找不到指定的文件：" + filePath);
			result = new ArrayList<String>();
		}
		return result;

	}

	/**
	 * 
	 * @param bufferedReader
	 * @return 读取 List<String>
	 * @throws IOException
	 */
	public static List<String> getTextList(BufferedReader bufferedReader) throws IOException {

		List<String> result = new ArrayList<String>();
		String lineTxt = null;
		while ((lineTxt = bufferedReader.readLine()) != null) {
			result.add(lineTxt);
		}
		return result;

	}

	/**
	 * 
	 * @return 项目 WEB-INF 的系统绝对路径
	 */
	public static String getWebPath() {
		String path = FileUtils.class.getResource("/").getPath();
		String filePath = path.substring(0, path.length() - "classes/".length()) + "yes/";
		return filePath;
	}

	/** 
	 *  
	 * @Title: getTextFromPdf 
	 * @Description: 读取pdf文件内容 
	 * @param filePath 
	 * @return: 读出的pdf的内容 
	 */
	public static String getTextFromPdf(String filePath) {
		String result = null;
		FileInputStream is = null;
		PDDocument document = null;
		try {
			is = new FileInputStream(filePath);
			PDFParser parser = new PDFParser(is);
			parser.parse();
			document = parser.getPDDocument();
			PDFTextStripper stripper = new PDFTextStripper();
			result = stripper.getText(document);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (document != null) {
				try {
					document.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/** 
	 *  
	 * @Title: getTextFromWord 
	 * @Description: 读取word 
	 * @param filePath 
	 *            文件路径 
	 * @return: String 读出的Word的内容 
	 */
	public static String getTextFromWord(String filePath) {
		String result = null;
		File file = new File(filePath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			HWPFDocument wordExtractor = new HWPFDocument(fis);
			result = wordExtractor.getDocumentText();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/** 
	 *  
	 * @Title: getTextFromWord 
	 * @Description: 读取word 
	 * @param filePath 
	 *            文件路径 
	 * @return: String 读出的Word的内容 
	 */
	@SuppressWarnings("resource")
	public static String getTextFromWord2007(String filePath) {
		String result = null;
		File file = new File(filePath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			XWPFDocument wordExtractor = new XWPFDocument(fis);
			XWPFWordExtractor extractor = new XWPFWordExtractor(wordExtractor);
			result = extractor.getText();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/** 
	 * @param filePath 
	 *            文件路径 
	 * @return 读出的Excel的内容 
	 */
	@SuppressWarnings({ "resource", "deprecation" })
	public static String getTextFromExcel(String filePath) {
		StringBuffer buff = new StringBuffer();
		try {
			// 创建对Excel工作簿文件的引用  
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath));
			// 创建对工作表的引用。  
			for (int numSheets = 0; numSheets < wb.getNumberOfSheets(); numSheets++) {
				if (null != wb.getSheetAt(numSheets)) {
					HSSFSheet aSheet = wb.getSheetAt(numSheets);// 获得一个sheet  
					for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
						if (null != aSheet.getRow(rowNumOfSheet)) {
							HSSFRow aRow = aSheet.getRow(rowNumOfSheet); // 获得一个行  
							for (int cellNumOfRow = 0; cellNumOfRow <= aRow.getLastCellNum(); cellNumOfRow++) {
								if (null != aRow.getCell(cellNumOfRow)) {
									HSSFCell aCell = aRow.getCell(cellNumOfRow);// 获得列值  
									switch (aCell.getCellType()) {
									case HSSFCell.CELL_TYPE_FORMULA:
										break;
									case HSSFCell.CELL_TYPE_NUMERIC:
										buff.append(aCell.getNumericCellValue()).append('\t');
										break;
									case HSSFCell.CELL_TYPE_STRING:
										buff.append(aCell.getStringCellValue()).append('\t');
										break;
									}
								}
							}
							buff.append('\n');
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buff.toString();
	}

	@SuppressWarnings("deprecation")
	public static String getTextFromExcel2007(String filePath) {
		StringBuffer buff = new StringBuffer();
		try {
			// 创建对Excel工作簿文件的引用  
			@SuppressWarnings("resource")
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(filePath));
			// 创建对工作表的引用。  
			for (int numSheets = 0; numSheets < wb.getNumberOfSheets(); numSheets++) {
				if (null != wb.getSheetAt(numSheets)) {
					XSSFSheet aSheet = wb.getSheetAt(numSheets);// 获得一个sheet  
					for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
						if (null != aSheet.getRow(rowNumOfSheet)) {
							XSSFRow aRow = aSheet.getRow(rowNumOfSheet); // 获得一个行  
							for (int cellNumOfRow = 0; cellNumOfRow <= aRow.getLastCellNum(); cellNumOfRow++) {
								if (null != aRow.getCell(cellNumOfRow)) {
									XSSFCell aCell = aRow.getCell(cellNumOfRow);// 获得列值  
									switch (aCell.getCellType()) {
									case HSSFCell.CELL_TYPE_FORMULA:
										break;
									case HSSFCell.CELL_TYPE_NUMERIC:
										buff.append(aCell.getNumericCellValue()).append('\t');
										break;
									case HSSFCell.CELL_TYPE_STRING:
										buff.append(aCell.getStringCellValue()).append('\t');
										break;
									}
								}
							}
							buff.append('\n');
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buff.toString();
	}

	/**  
	* @param filePath 文件路径  
	* @return 获得的html文本内容  
	*/
	public static String getTextFromHtml(String filePath) {
		//得到body标签中的内容   
		String str = readHtml(filePath);
		StringBuffer buff = new StringBuffer();
		int maxindex = str.length() - 1;
		int begin = 0;
		int end;
		//截取>和<之间的内容   
		while ((begin = str.indexOf('>', begin)) < maxindex) {
			end = str.indexOf('<', begin);
			if (end - begin > 1) {
				buff.append(str.substring(++begin, end));
			}
			begin = end + 1;
		}
		;
		return buff.toString();
	}

	/**  
	 * @param filePath 文件路径  
	 * @return 获得html的全部内容  
	 */
	public static String readHtml(String filePath) {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GB2312"));
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
