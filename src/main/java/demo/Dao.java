package demo;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.pool.DruidPooledPreparedStatement;

@Repository
public class Dao {
//	@Autowired
//	private lianjiechi ljc;
	@Autowired
    private Testdruid td;
	@Autowired
	private ConfigBean conf;
	
	
	//Connection connection =null;
	//PreparedStatement preparedStatement =null;
	//保存url
	public  void saveUrl(String name,String url,String tagname, String uid) {
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		
		//Connection connection =null;
		DruidPooledPreparedStatement preparedStatement =null;
		
		
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			//connection = (Connection) DriverManager.getConnection("jdbc:mysql://47.111.20.125:3306/zz?characterEncoding=utf-8","root", "root");
			 connection = td.getConnection();
			//String sql = "insert into link (name,url,tagname) values(\""+name+"\",\""+url+"\",\""+tagname+"\")";
			//这种写法比拼接字符串安全
			String sql = "insert into link (name,url,tagname,uid) values(?,?,?,?)";
			
			preparedStatement =  (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, url);
			preparedStatement.setString(3, tagname);
			preparedStatement.setString(4, uid);
//			preparedStatement.execute(sql);
			 preparedStatement.execute();
			 
			 preparedStatement.close();
			connection.close();

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List readUrl(){
		
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		
		ResultSet result = null;
		List list =null;
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			//connection = (Connection) DriverManager.getConnection("jdbc:mysql://47.111.20.125:3306/zz?characterEncoding=utf-8","root", "root");
			connection = td.getConnection();
			String sql = "select * from link where uid = ?";
			preparedStatement = (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, conf.getPhone());
			result = preparedStatement.executeQuery();
			//把resultset变成list集合返回
			list = convertList(result);
			
			result.close();
			preparedStatement.close();
			connection.close();
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
		
		
	}
	
	/**
	 * 转换数据库结果方法
	 * @param result
	 * @return
	 */
	private static List convertList(ResultSet rs) throws SQLException{
		List list = new ArrayList();
		java.sql.ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		while (rs.next()) {
			Map rowData = new HashMap();//声明Map
			for (int i = 1; i <= columnCount; i++) {
			rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
			}
			list.add(rowData);
			}
			return list;
			
		
		
		
	}
    
	
	//保存标签
	public void saveTag(String name, String uid) {
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		// TODO Auto-generated method stub
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			//connection = (Connection) DriverManager.getConnection("jdbc:mysql://47.111.20.125:3306/zz?characterEncoding=utf-8","root", "root");
			connection = td.getConnection();
			
//            String sql = "insert into link (name,url,tagname,uid) values(?,?,?,?)";
//			preparedStatement =  (DruidPooledPreparedStatement) connection.prepareStatement(sql);
//			preparedStatement.setString(1, name);
//			preparedStatement.setString(2, url);
			
			
			String sql = "insert into user (name,uid) values(?,?)";
			preparedStatement = (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, uid);
			preparedStatement.execute();
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	//读标签
	public List readTag() {
		// TODO Auto-generated method stub
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		ResultSet result = null;
		List list =null;
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			//connection = (Connection) DriverManager.getConnection("jdbc:mysql://47.111.20.125:3306/zz?characterEncoding=utf-8","root", "root");
			connection = td.getConnection();
			String sql = "select * from user where uid = ?";
			preparedStatement = (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1,conf.getPhone());
			result = preparedStatement.executeQuery();
			//把resultset变成list集合返回
			list = convertList(result);
			result.close();
			preparedStatement.close();
			connection.close();
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
		
		
	}

	public Boolean checktag(String name, String uid) {
		// TODO Auto-generated method stub
		
		List list =null;
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		
		try {
			connection = td.getConnection();
			String sql = "select * from user where binary name = ? and binary uid=?";
			preparedStatement =  (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, uid);
			 ResultSet result = preparedStatement.executeQuery();
			 list = convertList(result);
			 
			 result.close();
			 preparedStatement.close();
			 connection.close();
			 
			 if(list.size()==0){
				 return true;
			 }
			 return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
    
	//检查注册的手机号是否可用
	public Boolean checkphone(String phone) {
		// TODO Auto-generated method stub
		List list =null;
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		
		try {
			connection = td.getConnection();
			String sql = "select * from login where binary name = ?";
			preparedStatement =  (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, phone);
			 ResultSet result = preparedStatement.executeQuery();
			 list = convertList(result);
			
			 result.close();
			 preparedStatement.close();
			 connection.close();
			 
			 if(list.size()==0){
				 return true;
			 }
			 return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	//保存注册信息,返回,注册时给一个默认标签页,新建login表,把信息插入,vip初始为n
	public void zhuce(String phone, String password) {
		// TODO Auto-generated method stub
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		 try {
			connection = td.getConnection();
            //String sql = "insert into link (name,url,tagname) values(?,?,?)";
			String sql = "insert into user (uid,name) values(?,?)";
			preparedStatement =  (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, phone);
			preparedStatement.setString(2, "默认");
			preparedStatement.execute();
			//保存一个百度网址
			String sql2 = "insert into link (name,url,tagname,uid) values(?,?,?,?)";
			preparedStatement =  (DruidPooledPreparedStatement) connection.prepareStatement(sql2);
			preparedStatement.setString(1, "百度");
			preparedStatement.setString(2, "https://www.baidu.com/");
			preparedStatement.setString(3, "默认");
			preparedStatement.setString(4,phone );
			preparedStatement.execute();
			//login表插入登录信息
			String sql3 = "insert into login (name,password,vip) values(?,?,?)";
			preparedStatement =  (DruidPooledPreparedStatement) connection.prepareStatement(sql3);
			preparedStatement.setString(1,phone );
			preparedStatement.setString(2,password);
			preparedStatement.setString(3,"n");
			preparedStatement.execute();
			preparedStatement.close();
			connection.close();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	//登录验证
	public Boolean denglu(String phone, String password) {
		// TODO Auto-generated method stub
		List list =null;
		td=Testdruid.getInstance();
		DruidPooledConnection connection = null ;
		DruidPooledPreparedStatement preparedStatement =null;
		
		try {
			connection = td.getConnection();
			String sql = "select * from login where binary name = ? and binary password = ?";
			preparedStatement =  (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, phone);
			preparedStatement.setString(2, password);
			 ResultSet result = preparedStatement.executeQuery();
			 list = convertList(result);
			 result.close();
			 preparedStatement.close();
			 connection.close();
			 
			 if(list.size()==0){
				 return false;
			 }
			 
			 return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
		
	}
    //登陆后的读取
	public List readUrl(String uid) {
		// TODO Auto-generated method stub
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		
		ResultSet result = null;
		List list =null;
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			//connection = (Connection) DriverManager.getConnection("jdbc:mysql://47.111.20.125:3306/zz?characterEncoding=utf-8","root", "root");
			connection = td.getConnection();
			String sql = "select * from link where binary uid = ? ";
			preparedStatement = (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, uid);
			result = preparedStatement.executeQuery();
			//把resultset变成list集合返回
			list = convertList(result);
			result.close();
			preparedStatement.close();
			connection.close();
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//登陆后的读取
	public List readTag(String uid) {
		// TODO Auto-generated method stub
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		ResultSet result = null;
		List list =null;
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			//connection = (Connection) DriverManager.getConnection("jdbc:mysql://47.111.20.125:3306/zz?characterEncoding=utf-8","root", "root");
			connection = td.getConnection();
			String sql = "select * from user where binary uid = ?";
			preparedStatement = (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, uid);
			result = preparedStatement.executeQuery();
			//把resultset变成list集合返回
			list = convertList(result);
			result.close();
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//修改tag
	public void updatetag(String uid, String tagname, String retagname) {
		// TODO Auto-generated method stub
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		try {
			connection = td.getConnection();
			String sql = "update user set name = ? where binary uid = ? and binary name = ? ";
			preparedStatement = (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, retagname);
			preparedStatement.setString(2, uid);
			preparedStatement.setString(3, tagname);
			 preparedStatement.executeUpdate();
			 
			 preparedStatement.close();
			 connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	//更新tag名称,修改关联的url相应字段
	public void updatetagForURL(String uid, String tagname, String name) {
		// TODO Auto-generated method stub
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		try {
			connection = td.getConnection();
			String sql = "update link set tagname = ? where binary uid = ? and binary tagname = ? ";
			preparedStatement = (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, uid);
			preparedStatement.setString(3, tagname);
			 preparedStatement.executeUpdate();
			 
			 preparedStatement.close();
			 connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//删除tag
	public void delTag(String uid, String tagname) {
		// TODO Auto-generated method stub
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		try {
			connection = td.getConnection();
			String sql = "delete from user where binary uid = ? and binary name = ? ";
			preparedStatement = (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, uid);
			preparedStatement.setString(2, tagname);
			preparedStatement.execute();
			preparedStatement.close();
			 connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	//删除url
	public void delurl(String uid, String urlname) {
		// TODO Auto-generated method stub
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		try {
			connection = td.getConnection();
			String sql = "delete from link where binary uid = ? and binary name = ? ";
			preparedStatement = (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, uid);
			preparedStatement.setString(2, urlname);
			preparedStatement.execute();
			preparedStatement.close();
			 connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//更改url
	public void upurl(String uid, String urlname, String newname, String newurl) {
		// TODO Auto-generated method stub
		
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		try {
			connection = td.getConnection();
			String sql = "update link set name = ?, url=? where binary uid = ? and binary name = ? ";
			preparedStatement = (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, newname);
			preparedStatement.setString(2, newurl);
			preparedStatement.setString(3, uid);
			preparedStatement.setString(4, urlname);
			 preparedStatement.executeUpdate();
			 
			 preparedStatement.close();
			 connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	//保存url前的检查
	public String checkurlcount(String uid) {
		// TODO Auto-generated method stub
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		
		ResultSet result = null;
		String vip ="";
		try {
			connection = td.getConnection();
			//先查是不是会员
			String sql = "select * from login where binary name = ? ";
			preparedStatement = (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, uid);
			result = preparedStatement.executeQuery();
			while(result.next()){
				 vip = result.getString(4);
				 
			}
			if("y".equals(vip)){ 
				
				result.close();
				preparedStatement.close();
				connection.close();
				
				
				return vip;
				
			}else{
				String sql2 = "select count(*) as urlcount from link where uid = ? ";
				preparedStatement = (DruidPooledPreparedStatement) connection.prepareStatement(sql2);
				
				preparedStatement.setString(1, uid);
				result = preparedStatement.executeQuery();
				int count=0;
				while(result.next()){
					count = result.getInt("urlcount");
				}
				
				result.close();
				preparedStatement.close();
				connection.close();
				int checkURLcount = Integer.parseInt(conf.getUrl());//544行用
				if(count>=checkURLcount){
					vip="n";
					
					return vip;
				}else{
					
					vip="y";
					return vip;
				}
				
				
				
			}
			
			
			
			

			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vip;
		
	}

	//开通会员前检查账号的存在性
	public Boolean checkhoutai(String vipid) {
		// TODO Auto-generated method stub
		List list =null;
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		
		try {
			connection = td.getConnection();
			String sql = "select * from login where binary name = ? ";
			preparedStatement =  (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, vipid);
			ResultSet result = preparedStatement.executeQuery();
			list = convertList(result);
			 
			 result.close();
			 preparedStatement.close();
			 connection.close();
			 
			 if(list.size()==0){
				 return false;
			 }
			 
			 return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	//后台保存vip,要添加日期
	public void houtaisave(String vipid) {
		// TODO Auto-generated method stub
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		//添加日期
		Date date = new Date();
		System.out.println(date.getTime());
		java.sql.Date sqldate = new java.sql.Date(date.getTime());
		java.sql.Date sqldate2 = new java.sql.Date(date.getTime()+31708800000l);
		try {
			connection = td.getConnection();
			String sql = "update login set vip = ?,viptimestart = ?,viptimeend = ? where binary  name = ?  ";
			preparedStatement = (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1,"y");
			preparedStatement.setDate(2,sqldate);
			preparedStatement.setDate(3, sqldate2);
			preparedStatement.setString(4, vipid);
			 preparedStatement.executeUpdate();
			 preparedStatement.close();
			 connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//写cookie前检查是不是vip
	public Boolean checkvip(String phone) {
		// TODO Auto-generated method stub
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		ResultSet result ;
		String vip ="";
		try {
			connection = td.getConnection();
			//先查是不是会员
			String sql = "select * from login where binary name = ? ";
			preparedStatement = (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, phone);
			result = preparedStatement.executeQuery();
			while(result.next()){
				 vip = result.getString(4);
			}
			result.close();
			preparedStatement.close();
			connection.close();
			if("y".equals(vip)){ 
				return true;
			}else{
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//检查非会员存储的tag数量
	public String checktagcount(String uid) {
		// TODO Auto-generated method stub
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		ResultSet result = null;
		String vip ="";
		try {
			connection = td.getConnection();
			//先查是不是会员
				String sql2 = "select count(*) as tagcount from user where binary uid = ? ";
				preparedStatement = (DruidPooledPreparedStatement) connection.prepareStatement(sql2);
				
				preparedStatement.setString(1, uid);
				result = preparedStatement.executeQuery();
				int count = 0;
				while(result.next()){
					count = result.getInt("tagcount");
				}
				
				result.close();
				preparedStatement.close();
				connection.close();
				int checkTAGcount = Integer.parseInt(conf.getTag());//690行
				if(count>=checkTAGcount){
					vip="n";
					
					return vip;
				}else{
					
					vip="y";
					return vip;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vip;
	}

	//删除标签页前检查有没有url,有就不能删除
	public Boolean checkdeltag(String tagname,String uid) {
		// TODO Auto-generated method stub
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		ResultSet result = null;
		
		try {
			connection = td.getConnection();
				String sql2 = "select count(*) as urlcount from link where binary uid = ? and binary tagname = ?";
				preparedStatement = (DruidPooledPreparedStatement) connection.prepareStatement(sql2);
				
				preparedStatement.setString(1, uid);
				preparedStatement.setString(2, tagname);
				result = preparedStatement.executeQuery();
				int count = 0;
				while(result.next()){
					count = result.getInt("urlcount");
				}
				
				result.close();
				preparedStatement.close();
				connection.close();
				
				if(count>0){
					
					
					return false;
				}else{
					
					
					return true;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	//后台取消会员资格
	public void houtaidelvip(String vipid) {
		// TODO Auto-generated method stub
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		try {
			connection = td.getConnection();
			String sql = "update login set vip = ? where binary name = ?  ";
			preparedStatement = (DruidPooledPreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1,"n");
			preparedStatement.setString(2, vipid);
			
			 preparedStatement.executeUpdate();
			 
			 preparedStatement.close();
			 connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	//从数据库取vip过期时间,写回客户端
	public String checkvipend(String phone) {
		// TODO Auto-generated method stub
		td=Testdruid.getInstance();
		DruidPooledConnection connection ;
		DruidPooledPreparedStatement preparedStatement =null;
		ResultSet result = null;
		String viptime ="";
		try {
			connection = td.getConnection();
			//先查是不是会员
				String sql2 = "select * from login where binary name = ? ";
				preparedStatement = (DruidPooledPreparedStatement) connection.prepareStatement(sql2);
				preparedStatement.setString(1, phone);
				result = preparedStatement.executeQuery();
				while(result.next()){
					 viptime = result.getString(6);
				}
				
				result.close();
				preparedStatement.close();
				connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return viptime;
		
		
	}

	
	
	
}
