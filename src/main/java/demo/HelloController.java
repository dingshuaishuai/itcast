package demo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@Controller
public class HelloController {
	@Autowired
	private SendSms sms;
	
	@Autowired
	private HelloService helloService;
	//删除url,可以返回json,让前台解析,重定向到/react2
	@RequestMapping("/delurl")
	public String delurl(String uid,String urlname){
		helloService.delurl(uid,urlname);
		
		return "redirect:/";
	}
	
	
	//删除tag
	@RequestMapping("/delTag")
	public String delTag(String uid,String tagname){
		
		
		helloService.delTag(uid,tagname);
		
		return "redirect:/";
	}
	
	//updateTag更改功能,根据主键来修改
	@RequestMapping("/updateTag")
	public String updatetag(String uid,String tagname,String name){
		helloService.updatetag(uid,tagname,name.replaceAll("\\s*", ""));
		//修改标签页名,也要修改关联的url对应的绑定
		helloService.updatetagForURL(uid,tagname,name.replaceAll("\\s*", ""));
			
		
		
		
		
		return "redirect:/";
	}
	//登录,要写登录信息到cookie
	@RequestMapping("/denglu")
	
	public String denglu(){
		
		return "redirect:/";
	}
	
	//登录校验手机密码,把phone传回页面显示,并写入cookie?,封装在RT里
	@RequestMapping("/checkdenglu")
	@ResponseBody
	public RT checkdenglu(String phone,String password,HttpServletResponse res){
		
		RT rt = new RT();
		Boolean checkdenglu = helloService.denglu(phone,password);
		Boolean checkvip = helloService.checkvip(phone);
		String vipend = helloService.checkvipend(phone);
		if(checkdenglu==true){
			rt.setPhone(phone);
			//登录成功后,向客户端写cookie,保存手机号
			Cookie cookie1 = new Cookie("phone", phone);
			
			//缓存416天
			cookie1.setMaxAge(36000000);
			res.addCookie(cookie1);
		}
		if(checkvip==true){
			//如果是会员,向客户端写cookie,保存会员信息
			Cookie cookie2 = new Cookie("huiyuan","y");
			//缓存416天
			cookie2.setMaxAge(36000000);
			//写过期时间
			Cookie cookie4 = new Cookie("vipend",vipend);
			cookie4.setMaxAge(36000000);
			res.addCookie(cookie2);
			res.addCookie(cookie4);
		}
		
		if(checkvip==false){
			//如果不是会员,向客户端写cookie,保存非会员信息
			Cookie cookie3 = new Cookie("huiyuan","n");
			//缓存416天
			cookie3.setMaxAge(36000000);
			res.addCookie(cookie3);
		}
		rt.setState(checkdenglu);
		return rt;
	}
	//发验证码前的验证手机号
	@RequestMapping("/yanzhengma")
	@ResponseBody
	public RT zhuce3(String phone){
		RT rt = new RT();
		Boolean checkphone = helloService.checkphone(phone.replaceAll("\\s*", ""));
		if(checkphone){
			//手机号能用,调用短信接口
			String back = sms.send(phone);
			rt.setYanzhengma(back);
			rt.setState(checkphone);
		}else{
			rt.setState(false);
		}
		
		return rt;
	}
	
	//注册前验证手机号,和验证码
	@RequestMapping("/checkzhuce")
	@ResponseBody
	public RT zhuce2(String phone,String yanzhengma){
		RT rt = new RT();
		Boolean checkphone = helloService.checkphone(phone.replaceAll("\\s*", ""));
		String back = sms.send(phone);
		
		if(yanzhengma.equals(back)){
			//验证码正确
		}else{
			//验证码错误
			
		}
		rt.setState(checkphone);
		return rt;
	}
	
	//注册
	@RequestMapping("/zhuce")
	public String zhuce(String phone,String password){
		helloService.zhuce(phone.replaceAll("\\s*", ""),password.replaceAll("\\s*", ""));
		return "redirect:/";
	}
	
	//checktag是否已经存在,取cookie,非会员要查已存数量,会员过期不能存
	@RequestMapping("/checktag")
	@ResponseBody
	public RT checktag(String name,String uid,HttpServletRequest request, HttpServletResponse response){
		RT rt = new RT();
		Boolean checktag = helloService.checktag(name.replaceAll("\\s*", ""),uid.replaceAll("\\s*", ""));
		Cookie[] cookies = request.getCookies();
		String vipend = null;
		 SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		 Date vipdate = null;
		 Date now = new Date();
		 String vip =null;
		for(Cookie cookie:cookies){
			if(cookie.getName().equals("vipend")){
    			vipend = cookie.getValue();
    			try {
    				 vipdate = sd.parse(vipend);
    				 if(now.before(vipdate)){
    					 rt.setViptime(true);
    				 }else{
    					 rt.setViptime(false);
    					 //会员过期了,更改数据库信息
    					 helloService.houtaidelvip(uid);
    					 //清除客户端cookie
    					 Cookie cookie2 = new Cookie("huiyuan", "n");
    					 Cookie cookie3 = new Cookie("vipend", null);
    					 cookie2.setMaxAge(36000000);
    					 cookie3.setMaxAge(0);
    					 cookie2.setPath("/");
    					 cookie3.setPath("/");
    					 response.addCookie(cookie2);
    					 response.addCookie(cookie3);
    				 }
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
    		}
			if(cookie.getName().equals("huiyuan")){
				if(cookie.getValue().equals("y")){
					rt.setTag(true);
				}
				if(cookie.getValue().equals("n")){
				    vip = helloService.checktagcount(uid);
				    if("y".equals(vip)){
						 rt.setTag(true);
						// return rt;
					 }else{
						 rt.setTag(false);
					 }
				}
				
			}
			
		}
		
		 //没过期的情况
		 
		rt.setState(checktag);
		return rt;
	}
	
	
	//默认请求
	@RequestMapping("/")
	public String read(Model model,HttpServletRequest request){
		List listUrl = null;
		List listTag = null;
		
		String uid = null;
		Cookie[] cookies = request.getCookies();
		
	    if(cookies !=null){
	    	
	    	//登录或未登录但有cookie
	    	for(Cookie cookie:cookies){
	    		
	    		//已经登录
	    		if(cookie.getName().equals("phone")){
	    			uid = cookie.getValue();
	    			listUrl = helloService.readUrl(uid);
	    	    	listTag = helloService.readTag(uid);
	    	    	model.addAttribute("listUrl",listUrl);
	    			model.addAttribute("listTag",listTag);
	    			return "index";
	    		}
	    		//没登录,但有cookie
	    		
//	    		if(!cookie.getName().equals("phone")||cookie.getName().equals("huiyuan")||cookie.getName().equals("vipend")){
//		    		map.put(cookie.getName(), cookie.getValue());
//		    	}
	    	}                  
	    	model.addAttribute("listUrl",listUrl);
			model.addAttribute("listTag",listTag);
			return "index";
	    	
	    	
	    }else{
	    	//没登录的情况,{ }
	    	 listUrl = helloService.readUrl();
	    	 listTag = helloService.readTag();
	    }
		
		
		
		model.addAttribute("listUrl",listUrl);
		model.addAttribute("listTag",listTag);
		return "index";
	}
    //保存url,添加一种情况,游客保存
	@RequestMapping("/saveUrl")
	public String saveUrl(String name,String url,String tagname,String uid,HttpServletRequest request,HttpServletResponse response){
		
		helloService.saveUrl(name.replaceAll("\\s*",""), url.replaceAll("\\s*",""),tagname.replaceAll("\\s*",""),uid.replaceAll("\\s*",""));
		//如果未登录,写回cookie,如果已登录,写到数据库
		
		return "redirect:/";
	}
	//保存tag,去前后空格
	@RequestMapping("/saveTag")
	
	public String saveTag(String name,String uid,HttpServletRequest request){
		
		helloService.saveTag(name.replaceAll("\\s*", ""),uid.replaceAll("\\s*", ""));
		
		//返回json
		
		return "redirect:/";
	}
	  
	@Autowired
	private Environment env;
	
	@RequestMapping("/s")
	public String eat(String name,String url,ModelMap map){
		map.addAttribute("name", name);
		//已经接收到页面的name,url,下一步存到数据库,service+dao做生产者
		
		
		
		
		return "a";
	}
	
	@RequestMapping("/g")
	public String sleep(ModelMap map){
		map.addAttribute("name", "百度");
		map.addAttribute("url", "https://www.jd.com/");
		
		return "home";
	}
	
	@GetMapping(value = "/h")
    public String hello(Model model) {
        String name = "jiangbei";
        model.addAttribute("name", name);
        return "a";
    }
	
	//测试react
	/*@RequestMapping("/react")
	public String index(HttpServletRequest request,Model model){
		List listUrl;
		List listTag;
		String uid = null;
		Cookie[] cookies = request.getCookies();
		System.out.println("Cookie[] cookies的长度="+cookies);
	    if(cookies !=null){
	    	//登录的情况
	    	for(Cookie cookie:cookies){
	    		 uid = cookie.getValue();
	    	}
	    	listUrl = helloService.readUrl(uid);
	    	listTag = helloService.readTag(uid);
	    }else{
	    	//没登录的情况
	    	 listUrl = helloService.readUrl();
	    	 listTag = helloService.readTag();
	    }
		
	    model.addAttribute("listUrl",listUrl);
		model.addAttribute("listTag",listTag);
		
		return "ajax2";
//		return "redirect:/read";
	}*/
	/**
	 * 增删改后用这个方法?
	 * 这个方法返回json,由前台解析展示
	 * @param request
	 * @return
	 */
	/*@RequestMapping("/react2")
	@ResponseBody
	public List link(HttpServletRequest request){
		List all = new ArrayList<>();
		List listUrl;
		List listTag;
		String uid = null;
		Cookie[] cookies = request.getCookies();
	
	    if(cookies !=null){
	    	//登录的情况
	    	for(Cookie cookie:cookies){
	    		 uid = cookie.getValue();
	    	}
	    	listUrl = helloService.readUrl(uid);
	    	listTag = helloService.readTag(uid);
	    }else{
	    	//没登录的情况
	    	 listUrl = helloService.readUrl();
	    	 listTag = helloService.readTag();
	    }
		
	    all.add(listTag);
		all.add(listUrl);
		
		
		
		return all;
		
	}*/
	
	//更改url信息 /upurl
	@RequestMapping("/upurl")	
	public String upurl(String uid,String urlname,String newname,String newurl){
		helloService.upurl(uid,urlname,newname.replaceAll("\\s*", ""),newurl.replaceAll(" ", ""));
		
		
		return "redirect:/";
	}
	
	//检查非会员存储上限,会员则没有限制 .并且检查会员是否过期,大于1000的处理方法
	@RequestMapping("/checkurlcount")
	@ResponseBody
	public RT checkurlcount(String uid,HttpServletRequest request,HttpServletResponse response){
		 Cookie[] cookies = request.getCookies();
		RT rt = new RT();
		if(cookies != null){
			
			String vip = helloService.checkurlcount(uid);
			if("y".equals(vip)){
				rt.setState(true);
				// return rt;
			}else{
				rt.setState(false);
			}
			String vipend = null ;
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			Date vipdate = null;
			Date now = new Date();
			for(Cookie cookie:cookies){
				if(cookie.getName().equals("vipend")){
					vipend = cookie.getValue();
					try {
						vipdate = sd.parse(vipend);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(now.before(vipdate)){
						rt.setViptime(true);
					}else{
						//会员过期
						rt.setViptime(false);
						helloService.houtaidelvip(uid);
						//清除客户端cookie
						Cookie cookie2 = new Cookie("huiyuan", "n");
						Cookie cookie3 = new Cookie("vipend", null);
						cookie2.setMaxAge(36000000);
						cookie3.setMaxAge(0);
						cookie2.setPath("/");
						cookie3.setPath("/");
						response.addCookie(cookie2);
						response.addCookie(cookie3);
					}
				}
			}    
		}
		
		 
		
		return rt;
	}
	
	//网站后台,可以开通会员
	//限制13584071815可以操作后台,检查cookie
	@RequestMapping("/houtai")
	public String houtai(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
	    if(cookies !=null){
	    	//登录的情况
	    	for(Cookie cookie:cookies){
	    		if(cookie.getName().equals("phone")){
	    			if(cookie.getValue().equals("13584071815")){
	    				return "houtai";
	    			}
	    			
	    		}
	    	}                                           
	    	
	    }else{
	    	//不是管理员的情况
	    	return "redirect:/";
	    	 
	    }
		
		return "redirect:/";
	}
	
	
	//网站后台检查账号是否存在,可以开通会员
		@RequestMapping("/checkhoutai")
		@ResponseBody
		public RT checkhoutai(String vipid){
			RT rt = new RT();
			Boolean bool =  helloService.checkhoutai(vipid.replaceAll("\\s*", ""));
			rt.setState(bool);
			
			return rt;
		}
		
		//保存为会员,并写cookie?在登录模块写,
		@RequestMapping("/houtaisave")
		public String houtaisave(String vipid){
			helloService.houtaisave(vipid.replaceAll("\\s*", ""));
			
			
			
			return "redirect:/houtai";
		}
		
		//删除标签页前检查有没有url/checkdeltag
		@RequestMapping("/checkdeltag")
		@ResponseBody
		public RT checkdeltag(String tagname,String uid){
			RT rt = new RT();
			Boolean bool = helloService.checkdeltag(tagname,uid);
			rt.setState(bool);
			return rt;
		}
		
		//后台取消会员资格/houtaidelvip
		@RequestMapping("/houtaidelvip")
		public String houtaidelvip(String vipid){
			helloService.houtaidelvip(vipid.replaceAll("\\s*", ""));
			return "redirect:/houtai";
		}
		
	
	
	
	
	
	
	

}
