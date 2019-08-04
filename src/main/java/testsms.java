import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
public class testsms {
	 public static void main(String[] args) {
	        DefaultProfile profile = DefaultProfile.getProfile("default", "<accessKeyId>", "<accessSecret>");
	        IAcsClient client = new DefaultAcsClient(profile);

	        CommonRequest request = new CommonRequest();
	        request.setMethod(MethodType.POST);
	        request.setDomain("dysmsapi.aliyuncs.com");
	        request.setVersion("2017-05-25");
	        request.setAction("SendSms");
	        request.putQueryParameter("PhoneNumbers", "13584071815");
	        request.putQueryParameter("SignName", "只是链接");
	        request.putQueryParameter("TemplateCode", "SMS_169113191");
	        request.putQueryParameter("TemplateParam", "{code:123456}");
	        try {
	            CommonResponse response = client.getCommonResponse(request);
	            System.out.println(response.getData());
	        } catch (ServerException e) {
	            e.printStackTrace();
	        } catch (ClientException e) {
	            e.printStackTrace();
	        }
	    }

}
