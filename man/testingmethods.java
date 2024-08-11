package man;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class testingmethods {

	public static void main(String[] args) {
		
		File dbfile=new File("ComsciIA.db");
		String a = dbfile.getAbsolutePath();
		System.out.println(a);
		Path b = Paths.get("ComsciIA.db");
		String c = b.toString();
		System.out.println(c);
		
		
			

}
}

