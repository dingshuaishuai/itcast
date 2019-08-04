package demo;

import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
/**
 * 短信验证
 * @author Administrator
 *
 */
@Component
public class SendSms {
	
	public String  send(String uid){
		 DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIMTcNLMRt4UHT", "hq2QXlULD31drLoMFyx6olhVowaLHn");
	      IAcsClient client = new DefaultAcsClient(profile);

	     /* Date date = new Date();
	      long time = date.getTime();
	      String stime = ""+String.valueOf(time).substring(8);
	      String code = "{code:"+stime+"}";*/
	      StringBuilder str = new StringBuilder();
	        Random random = new Random();
	        for (int i = 0; i < 6; i++) {
	            str.append(random.nextInt(10));
	        }
	        String co = str.toString();
	       String code = "{code:"+co+"}";
	       System.out.println("验证码为:"+code);

	      
	      CommonRequest request = new CommonRequest();
	      request.setMethod(MethodType.POST);
	      request.setDomain("dysmsapi.aliyuncs.com");
	      request.setVersion("2017-05-25");
	      request.setAction("SendSms");
	      request.putQueryParameter("PhoneNumbers", uid);
	      request.putQueryParameter("SignName", "只是链接");
	      request.putQueryParameter("TemplateCode", "SMS_169113191");
	      request.putQueryParameter("TemplateParam",code);
	      try {
	          CommonResponse response = client.getCommonResponse(request);
	          //return code;
	          System.out.println(response.getData());
	      } catch (ServerException e) {
	          e.printStackTrace();
	      } catch (ClientException e) {
	          e.printStackTrace();
	      }
	      return co;
		
	}

	 
}
