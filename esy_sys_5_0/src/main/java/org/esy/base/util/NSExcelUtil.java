/*
 * Copyright (c) , Yes-soft Ltd. All rights reserved.
 * Use is subject to license terms.
 */
package org.esy.base.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.esy.base.common.BaseUtil;
import org.esy.base.core.FormatSettings;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * <pre>
 * ClssName: NSExcelUtil 
 * Description: Excel 处理类 支持 2007-2010
 * </pre>
 *
 * @author huiqiang.yan
 * @since 1.8
 * @version $ Id:NSExcelUtil.java 1.0 2017年5月11日上午9:44:13 $
 */
public class NSExcelUtil {
	public List<List<String>> ll = new ArrayList<List<String>>();

	public NSExcelUtil() {

	}

	/**
	 * Description:生成excel
	 * 
	 * @author huiqiang.yan 2017年5月11日上午9:45:46
	 * @param sheetName
	 * @param header
	 * @param lmap
	 * @param isxlsx
	 * @param formatSetting
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public ByteArrayOutputStream writeExcel(String sheetName, Map<String, Object> header, List<?> lmap, boolean isxlsx,
			FormatSettings formatSetting) throws IOException {
		Workbook wb = null;
		wb = isxlsx ? new SXSSFWorkbook(200) : new HSSFWorkbook();
		Sheet sheet = wb.createSheet(sheetName);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Row row = sheet.createRow(0);
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		int i = 0, j, col = 0;
		for (Entry<String, Object> h : header.entrySet()) {
			Map<String, Object> hv = (Map<String, Object>) h.getValue();
			Cell cell = row.createCell(i++);
			cell.setCellValue(BaseUtil.toString(hv.get("name")));
			cell.setCellStyle(style);
		}
		SimpleDateFormat sim = null;
		if (formatSetting != null) {
			if (BaseUtil.isNotEmpty(formatSetting.getDateFormat())) {
				sim = new SimpleDateFormat(formatSetting.getDateFormat());
			}
		}

		Map<Integer, Row> _maprows = new HashMap<Integer, Row>();
		// _maprows.put(1, sheet.createRow(i++));
		i = 1;
		Map<Object, Object> _map;
		Map<String, Object> hv, valmap;
		String h;
		Object val;
		Object celval;
		Double temp;
		String[] temps, celvals;
		for (Object obj : lmap) {
			_map = new HashMap<Object, Object>();
			if (obj instanceof Map) {
				_map = (Map<Object, Object>) obj;
			} else {
				_map = new BeanMap(obj);
			}
			Row _row = sheet.createRow(i++);
			_maprows = new HashMap<Integer, Row>();
			j = 0;
			for (Entry<String, Object> _h : header.entrySet()) {
				col = j++;
				hv = (Map<String, Object>) _h.getValue();
				h = _h.getKey();
				val = _map.get(NSUtil.converStrFirstLowerCase(h));
				if (BaseUtil.isEmpty(hv.get("newrow"))) {
					// 为单行数据
					if (BaseUtil.isNotEmpty(hv.get("titleMap"))) {
						valmap = (Map<String, Object>) hv.get("titleMap");
						val = valmap.get(val);
					}
					if (val == null) {
						val = "";
					}
					if (formatSetting != null) {// 处理格式化数据
						if (sim != null && val instanceof Date) {// date处理
							val = NSUtil.getDateString((Date) val, sim);
						} else if (formatSetting.getDecimalCount() != null
								&& (val instanceof Double || val instanceof Float)) {// double/float处理
							temp = null;
							if (val instanceof Double) {
								temp = (Double) val;
							} else {
								temp = Double.parseDouble(String.valueOf(val));
							}
							val = BaseUtil.doubleRound(temp, formatSetting.getDecimalCount());
						} else if (formatSetting.getBooleanflag() != null && val instanceof Boolean) {
							temps = formatSetting.getBooleanflag().split(",");
							if ((Boolean) val) {
								val = temps[0];
							} else {
								val = temps[1];
							}
						}
					}
					_row.createCell(col).setCellValue(val.toString());
				} else {
					// 为多行数据
					if (BaseUtil.isEmpty(val)) {
						continue;
					}
					celvals = BaseUtil.toString(val).split(",");
					for (int k = 0; k < celvals.length; k++) {

						if (BaseUtil.isEmpty(_maprows.get(k))) {
							if (k == 0) {
								_maprows.put(k, _row);
							} else {
								_maprows.put(k, sheet.createRow(i++));
							}
						}
						celval = celvals[k];
						if (BaseUtil.isNotEmpty(hv.get("titleMap"))) {
							valmap = (Map<String, Object>) hv.get("titleMap");
							celval = valmap.get(celval);
						}
						if (celval == null) {
							celval = "";
						}
						if (formatSetting != null) {// 处理格式化数据
							if (sim != null && celval instanceof Date) {// date处理
								celval = NSUtil.getDateString((Date) celval, sim);
							} else if (formatSetting.getDecimalCount() != null
									&& (celval instanceof Double || celval instanceof Float)) {// double/float处理
								temp = null;
								if (celval instanceof Double) {
									temp = (Double) celval;
								} else {
									temp = Double.parseDouble(String.valueOf(celval));
								}
								celval = BaseUtil.doubleRound(temp, formatSetting.getDecimalCount());
							} else if (formatSetting.getBooleanflag() != null && celval instanceof Boolean) {
								temps = formatSetting.getBooleanflag().split(",");
								if ((Boolean) celval) {
									celval = temps[0];
								} else {
									celval = temps[1];
								}
							}
						}
						_maprows.get(k).createCell(col).setCellValue(celval.toString());
					}

				}

			}
		}
		System.out.print(i);
		wb.write(bos);
		bos.close();

		return bos;
	}

	/**
	 * Description:生成excel
	 * 
	 * @author huiqiang.yan 2017年5月11日上午9:45:46
	 * @param sheetName
	 * @param header
	 * @param lmap
	 * @param isxlsx
	 * @param formatSetting
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public ByteArrayOutputStream writeGExcel(String sheetName, LinkedHashMap<String, Object> header, List<?> lmap,
			boolean isxlsx, FormatSettings formatSetting) throws IOException {
		Workbook wb = null;
		wb = isxlsx ? new SXSSFWorkbook(200) : new HSSFWorkbook();
		Sheet sheet = wb.createSheet(sheetName);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Row row = sheet.createRow(0);
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		int i = 0, j;
		for (Entry<String, Object> h : header.entrySet()) {
			Cell cell = row.createCell(i++);
			cell.setCellValue(BaseUtil.toString(h.getValue()));
			cell.setCellStyle(style);
		}
		SimpleDateFormat sim = null;
		if (formatSetting != null) {
			if (BaseUtil.isNotEmpty(formatSetting.getDateFormat())) {
				sim = new SimpleDateFormat(formatSetting.getDateFormat());
			}
		}
		i = 1;
		for (Object obj : lmap) {
			Map<Object, Object> _map = new HashMap<Object, Object>();
			if (obj instanceof Map) {
				_map = (Map<Object, Object>) obj;
			} else {
				_map = new BeanMap(obj);
			}
			Row _row = sheet.createRow(i++);
			j = 0;
			for (Entry<String, Object> _h : header.entrySet()) {
				String h = _h.getKey();
				Object val = _map.get(NSUtil.converStrFirstLowerCase(h));
				if (val == null) {
					val = "";
				}
				if (formatSetting != null) {// 处理格式化数据
					if (sim != null && val instanceof Date) {// date处理
						val = NSUtil.getDateString((Date) val, sim);
					} else if (formatSetting.getDecimalCount() != null
							&& (val instanceof Double || val instanceof Float)) {// double/float处理
						Double temp = null;
						if (val instanceof Double) {
							temp = (Double) val;
						} else {
							temp = Double.parseDouble(String.valueOf(val));
						}
						val = BaseUtil.doubleRound(temp, formatSetting.getDecimalCount());
					}
				}
				_row.createCell(j++).setCellValue(val.toString());
			}
		}
		wb.write(bos);
		bos.close();
		return bos;
	}

	// 读取Excel 转换成 集合
	public Map<String, List<Map<String, Object>>> readListExcel(InputStream excel, Boolean isxlsx) throws Exception {
		Workbook wb = null;
		wb = isxlsx ? new XSSFWorkbook(excel) : new HSSFWorkbook(excel);
		// return readToListMap(excel);
		Map<String, List<Map<String, Object>>> resultmap = new HashMap<String, List<Map<String, Object>>>();
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			List<Map<String, Object>> lmap = new ArrayList<Map<String, Object>>();
			Sheet sheet = wb.getSheetAt(i);
			// sheet.getSheetName();
			Row firstRow = sheet.getRow(0);
			for (Row row : sheet) {
				if (row.getRowNum() < 1) {
					continue;
				}
				Map<String, Object> map = new HashMap<String, Object>();
				for (Cell cell : row) {
					Object o = null;
					cell.setCellType(Cell.CELL_TYPE_STRING);
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BLANK:
					case Cell.CELL_TYPE_STRING:
						o = cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						o = cell.getBooleanCellValue();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						o = cell.getNumericCellValue();
						break;
					default:
						o = cell.getErrorCellValue();
						break;
					}
					if (firstRow.getCell(cell.getColumnIndex()) != null) {
						map.put(firstRow.getCell(cell.getColumnIndex()).getStringCellValue(), o);
					} else {
						break;
					}
				}
				lmap.add(map);
			}
			resultmap.put(sheet.getSheetName(), lmap);
		}
		return resultmap;
	}

	// 读取Excel 转换成 集合
	public List<Map<String, Object>> readExcel(InputStream excel, Boolean isxlsx) throws Exception {
		Workbook wb = null;
		wb = isxlsx ? new XSSFWorkbook(excel) : new HSSFWorkbook(excel);
		// return readToListMap(excel);
		// Sheet sheet = wb.getSheetAt(wb.getNumberOfSheets()>1?1:0);
		Sheet sheet = wb.getSheetAt(0);
		Row firstRow = sheet.getRow(0);
		List<Map<String, Object>> lmap = new ArrayList<Map<String, Object>>();
		for (Row row : sheet) {
			if (row.getRowNum() < 1) {
				continue;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			for (Cell cell : row) {
				Object o = null;
				cell.setCellType(Cell.CELL_TYPE_STRING);
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_BLANK:
				case Cell.CELL_TYPE_STRING:
					o = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					o = cell.getBooleanCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					o = cell.getNumericCellValue();
					break;
				default:
					o = cell.getErrorCellValue();
					break;
				}
				if (firstRow.getCell(cell.getColumnIndex()) != null) {
					map.put(firstRow.getCell(cell.getColumnIndex()).getStringCellValue(), o);
				} else {
					break;
				}
			}
			lmap.add(map);
		}
		return lmap;
	}

	public void processOneSheet(InputStream is) throws Exception {
		OPCPackage pkg = OPCPackage.open("D:\\d\\Pd.xlsx");
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();
		XMLReader parser = fetchSheetParser(sst);
		InputStream sheet2 = r.getSheet("rId2");
		InputSource sheetSource = new InputSource(sheet2);
		parser.parse(sheetSource);
		sheet2.close();
	}

	public void processAllSheets(String filename) throws Exception {
		OPCPackage pkg = OPCPackage.open(filename);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();

		XMLReader parser = fetchSheetParser(sst);

		Iterator<InputStream> sheets = r.getSheetsData();
		while (sheets.hasNext()) {
			InputStream sheet = sheets.next();
			InputSource sheetSource = new InputSource(sheet);
			parser.parse(sheetSource);
			sheet.close();
		}
	}

	public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
		XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		ContentHandler handler = new SheetHandler(sst, ll);
		parser.setContentHandler(handler);
		return parser;
	}

	private static class SheetHandler extends DefaultHandler {
		private SharedStringsTable sst;

		private String lastContents;

		private boolean nextIsString;

		private List<String> list = null;

		private List<List<String>> ll = null;

		private SheetHandler(SharedStringsTable sst, List<List<String>> ll) {
			this.sst = sst;
			this.ll = ll;
		}

		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
			if (name.equals("c")) {
				String cellType = attributes.getValue("t");
				if (cellType != null && cellType.equals("s")) {
					nextIsString = true;
				} else {
					nextIsString = false;
				}
			} else if (name.equals("row")) {
				list = new ArrayList<String>();
			}
			lastContents = "";
		}

		public void endElement(String uri, String localName, String name) throws SAXException {
			if (nextIsString) {
				int idx = Integer.parseInt(lastContents);
				lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
				nextIsString = false;
			}
			if (name.equals("v")) {
				list.add(lastContents);
			} else if (name.equals("row")) {
				ll.add(list);
			}
		}

		public void characters(char[] ch, int start, int length) throws SAXException {
			lastContents += new String(ch, start, length);
		}
	}
}
