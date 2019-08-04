package demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

@Component
public class Testdruid {
	@Autowired
	private static Testdruid td;
	@Autowired
	private static DruidDataSource dds;
	static{
		Properties properties = new Properties();
		InputStream in = Testdruid.class.getResourceAsStream("druid.properties");
		try {
			properties.load(in);
			System.out.println("德鲁伊的static代码块运行");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			dds = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private Testdruid(){}
	
	public static Testdruid getInstance(){
		if(null==td){
			td = new Testdruid();
		}
		return td;
	}
	
	public DruidPooledConnection getConnection() throws Exception{
		return dds.getConnection();
	}

}
