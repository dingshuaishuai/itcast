package demo;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

import org.springframework.stereotype.Component;

import com.mysql.jdbc.Connection;
/**
 * 连接池
 * @author Administrator
 *
 */
//@Component
public class lianjiechi {
//	private static LinkedList<Connection> list = new LinkedList();
//	static{
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//		} catch (ClassNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//				Connection connection1 = null;
//				Connection connection2 = null;
//				try {
//					connection1 = (Connection) DriverManager.getConnection("jdbc:mysql://47.111.20.125:3306/zz?characterEncoding=utf-8","root", "root");
//					connection2 = (Connection) DriverManager.getConnection("jdbc:mysql://47.111.20.125:3306/zz?characterEncoding=utf-8","root", "root");
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				list.add(connection1);
//				list.add(connection2);
//			
//		System.out.println("初始创建的连接池内的数量="+list.size());
//		
//		
//	}
//	
//	//获得1个链接
//	public Connection getConnection(){
//		System.out.println("list.removeFirst()前的数量"+list.size());
//		final Connection connection = list.removeFirst();
//		System.out.println(connection);
//		System.out.println("list.removeFirst()后的数量"+list.size());
//		return connection;
//	}
//	//把链接放回连接池
//	public void backConnection(Connection connection){
//		System.out.println("list.add(connection)前"+list.size());
//		list.add(connection);
//		System.out.println("list.add(connection)后"+list.size());
//	}

}
