package marvin.apoteket16.sql;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import marvin.apoteket16.locale.AsqlLocale.Translation;
import marvin.apoteket16.utilities.GuiPopUpManager;

public class AsqlXlsPrinterRM implements InterfaceAsqlResultManager {
	
	private static final long serialVersionUID = -7261456181624550378L;
	private final String targetPath;
	private final String outputName;
	private static final int ROW_COUNT_PER_SHEET = 60000;
	
	public AsqlXlsPrinterRM(String targetPath, String outputName){
		this.targetPath = targetPath;
		this.outputName = outputName;
	}

	@Override
	public void postResult(ResultSet result) throws SQLException {
		int rowNo = 0;
		int sheetNo = 1;
		Workbook wb = new HSSFWorkbook();
		ResultSetMetaData md = null;
		Sheet sheet = null;

		//Populate data
		while (result.next()){
			if (rowNo%ROW_COUNT_PER_SHEET == 0){
				//Create sheet
				sheet = wb.createSheet("Output"+sheetNo);
				sheetNo++;
				rowNo = 0;
				//Create headers
				Row row = sheet.createRow(rowNo);
				rowNo++;
				md = result.getMetaData();
				for (int i = 0; i<md.getColumnCount();i++){
					Cell cell =row.createCell(i);
					cell.setCellValue(md.getColumnLabel(i+1));
				}
			}
			Row lr = sheet.createRow(rowNo);
			rowNo++;
			for (int i = 0; i<md.getColumnCount();i++){
				Cell lc = lr.createCell(i);
				lc.setCellValue(result.getString(i+1));
			}
		}
		//Close results
		result.close();
		//Create file
		FileOutputStream fileOut = null;
		try {
			String timeStamp = createTimestamp();
			String savePath = targetPath;
			if (!targetPath.equals("")) {
				savePath = savePath+"/";
			}
			fileOut = new FileOutputStream(savePath + outputName+" " + timeStamp + ".xls");
			//Write workbook to file
			wb.write(fileOut);
		} catch (FileNotFoundException e) {
			GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_UNABLE_TO_OPEN_FILE.toString(), Translation.ERROR_MESSAGE_UNABLE_TO_SAVE_TITLE.toString());
			e.printStackTrace();
		} catch (IOException e) {
			GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_UNABLE_TO_WRITE_FILE.toString(), Translation.ERROR_MESSAGE_UNABLE_TO_SAVE_TITLE.toString());
			e.printStackTrace();
		} finally{
			try {
				//Close the file
				wb.close();
				fileOut.close();
			} catch (IOException e) {
				GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_UNABLE_TO_CLOSE_FILE.toString(), Translation.ERROR_MESSAGE_UNABLE_TO_SAVE_TITLE.toString());
				e.printStackTrace();
			}
		}
		

	}

	private String createTimestamp() {
		Calendar now = Calendar.getInstance();
		String timeStamp = now.get(Calendar.YEAR) +""+ 
				zeroPad(now.get(Calendar.MONTH))+""+
				zeroPad(now.get(Calendar.DAY_OF_MONTH))+"-"+
				zeroPad(now.get(Calendar.HOUR_OF_DAY))+""+
				zeroPad(now.get(Calendar.MINUTE));
		return timeStamp;
	}
	
	private String zeroPad(int value){
		return String.format("%02d", value);
	}

	@Override
	public String getName() {
		return Translation.EXCEL_OUTPUT.toString();
	}

}
