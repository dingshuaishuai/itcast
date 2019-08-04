package demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service 
public class HelloService {

	@Autowired
	private Dao dao;
	
	//保存url
	public  void saveUrl(String name,String url,String tagname, String uid){
		dao.saveUrl(name, url,tagname,uid);
	}
	
	//保存tag
	public  void saveTag(String name, String uid){
		dao.saveTag(name,uid);
	}
	
	//读方法
	public List readUrl(){
		return dao.readUrl();
	}

	public List readTag() {
		// TODO Auto-generated method stub
		
		return dao.readTag();
	}

	public Boolean checktag(String name, String uid) {
		return dao.checktag(name,uid);
		
	}

	public Boolean checkphone(String phone) {
		// TODO Auto-generated method stub
		return dao.checkphone(phone);
		
	}

	public void zhuce(String phone, String password) {
		// TODO Auto-generated method stub
		
		 dao.zhuce(phone,password);
		
	}

	public Boolean denglu(String phone, String password) {
		// TODO Auto-generated method stub
		return dao.denglu(phone,password);
	}
     //登录后的读取
	public List readUrl(String uid) {
		// TODO Auto-generated method stub
		
		return dao.readUrl(uid);
	}
	//登录后的读取
	public List readTag(String uid) {
		// TODO Auto-generated method stub
		return dao.readTag(uid);
	}

	//更新tag
	public void updatetag(String uid, String tagname, String retagname) {
		// TODO Auto-generated method stub
		dao.updatetag(uid,tagname,retagname);
	}

	public void updatetagForURL(String uid, String tagname, String name) {
		// TODO Auto-generated method stub
		dao.updatetagForURL(uid,tagname,name);
		
	}

	//删除tag
	public void delTag(String uid, String tagname) {
		// TODO Auto-generated method stub
		dao.delTag(uid,tagname);
		
	}

	//删除url
	public void delurl(String uid, String urlname) {
		// TODO Auto-generated method stub
		dao.delurl(uid,urlname);
		
	}

	public void upurl(String uid, String urlname, String newname, String newurl){
		// TODO Auto-generated method stub
		dao.upurl(uid,urlname,newname,newurl);
		
	}

	public String checkurlcount(String uid) {
		// TODO Auto-generated method stub
		return dao.checkurlcount(uid);
		
	}

	public Boolean checkhoutai(String vipid) {
		// TODO Auto-generated method stub
		return dao.checkhoutai(vipid);
	}

	public void houtaisave(String vipid) {
		// TODO Auto-generated method stub
		dao.houtaisave(vipid);
		
	}

	
	public Boolean checkvip(String phone) {
		// TODO Auto-generated method stub
		return dao.checkvip(phone);
	}

	public String checktagcount(String uid) {
		// TODO Auto-generated method stub
		return dao.checktagcount(uid);
	}

	public Boolean checkdeltag(String tagname,String uid) {
		// TODO Auto-generated method stub
		return dao.checkdeltag(tagname,uid);
	}

	public void houtaidelvip(String vipid) {
		// TODO Auto-generated method stub
		dao.houtaidelvip(vipid);
	}

	public String checkvipend(String phone) {
		// TODO Auto-generated method stub
		return dao.checkvipend(phone);
	}

	
}
