package controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
//测试druid的用法
//@Component
public class testdruid {
	@Autowired
	private static testdruid td;
	@Autowired
	private static DruidDataSource dds;
	static{
		Properties properties = new Properties();
		InputStream in = testdruid.class.getResourceAsStream("druid.properties");
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
	private testdruid(){}
	
	public static testdruid getInstance(){
		if(null==td){
			td = new testdruid();
		}
		return td;
	}
	
	public DruidPooledConnection getConnection() throws Exception{
		return dds.getConnection();
	}
	
	
	
	

}
