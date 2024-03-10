package com.giimall.common.poi;/*
package com.giimall.common.poi;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.export.styler.AbstractExcelExportStyler;
import org.apache.poi.ss.usermodel.*;

*/
/**
 * 默认的Excel样式
 *
 * @author wangLiuJing
 * Created on 2021/5/25
 *//*

public class DefaultExcelExportStyler extends AbstractExcelExportStyler {

	public DefaultExcelExportStyler(Workbook workbook) {
		super.createStyles(workbook);
	}

	@Override
	public CellStyle getHeaderStyle(short headerColor) {
		CellStyle titleStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 15);
		font.setFontName("华文楷体");
		font.setBold(true);
		titleStyle.setFont(font);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		return titleStyle;
	}

	@Override
	public CellStyle getTitleStyle(short color) {
		CellStyle titleStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("华文楷体");
		font.setBold(true);
		titleStyle.setFont(font);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleStyle.setWrapText(false);
		return titleStyle;
	}

	@Override
	public CellStyle getStyles(boolean parity, ExcelExportEntity entity){
		CellStyle titleStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("华文楷体");
		titleStyle.setFont(font);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleStyle.setWrapText(false);
		return titleStyle;
	}
}
*/
