package marvin.apoteket16.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class TestXls {

	public static void main(String[] args) {
		String outputName = "test";
		String targetPath = "C:/Users/Anton/Desktop";
		int rowCount = 0;
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("Output");
		Row row = sheet.createRow(rowCount);
		rowCount++;
		int md = 10;
		for (int i = 0; i<=md;i++){
			Cell cell =row.createCell(i);
			cell.setCellValue(i);
		}
		for (int j = 0;j < 15;j++){
			Row lr = sheet.createRow(rowCount);
			rowCount++;
			for (int i = 0; i<=md;i++){
				Cell lc = lr.createCell(i);
				lc.setCellValue(j+":"+i);
			}
		}
		FileOutputStream fileOut = null;
		try {
			Calendar now = Calendar.getInstance();
			String timeStamp = now.get(Calendar.YEAR) +""+ zeroPad(now.get(Calendar.MONTH))+""+zeroPad(now.get(Calendar.DAY_OF_MONTH))+"-"+zeroPad(now.get(Calendar.HOUR_OF_DAY))+""+zeroPad(now.get(Calendar.MINUTE));
			fileOut = new FileOutputStream(targetPath + "/" +outputName+ " " + timeStamp+".xls");
			wb.write(fileOut);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				wb.close();
				fileOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private static String zeroPad(int value){
		return String.format("%02d", value);
	}

}
