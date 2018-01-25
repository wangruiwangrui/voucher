package com.voucher.weixin.insweptcontroller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.model.Sellers;
import com.voucher.manage.model.User_Asset;
import com.voucher.manage.model.Users;

import com.voucher.manage.service.SellerService;
import com.voucher.manage.service.UserService;
import com.voucher.manage.tools.Constants;
import com.voucher.manage.tools.IdcardUtil;
import com.voucher.manage.tools.Md5;
import com.voucher.manage.tools.verifycode.Captcha;
import com.voucher.manage.tools.verifycode.SpecCaptcha;
import com.voucher.sqlserver.context.Connect;

@Controller
@RequestMapping("/mobile/assetRegister")
public class AssetUserRegisterController {

	private String verifyCode;
	
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	ApplicationContext applicationContext=new Connect().get();
	
	AssetsDAO assetsDAO=(AssetsDAO) applicationContext.getBean("assetsdao");
	
	/*
	 * 生成验证码类
	 */
	@RequestMapping(value="getYzm",method=RequestMethod.GET)
	public void getYzm(HttpServletResponse response,HttpServletRequest request){
		try {
			response.setHeader("Pragma", "No-cache");  
	        response.setHeader("Cache-Control", "no-cache");  
	        response.setDateHeader("Expires", 0);  
	        response.setContentType("image/jpeg");  
	          
	        //生成随机字串  
	        Captcha captcha = new SpecCaptcha(120,25,4); 

	        //生成图片  
	        captcha.out(response.getOutputStream());
            verifyCode=captcha.text().toLowerCase();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@RequestMapping("testName")
	public @ResponseBody Map<String, Object>
	testName(@RequestParam String name){
		Map<String, Object> map=new HashMap<>();
		
		int repeat=userService.selectRepeatUser(name);
		
		if(name.equals("")){
			map.put("data", "用户名不能空");
			return map;
		}
		
		
		String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";  
		Pattern   p   =   Pattern.compile(regEx);     
	    Matcher   m   =   p.matcher(name); 
	      
	    if(m.find()){
	    	map.put("data", "用户名含有非法字符");
			return map;
	    }
	    
			map.put("data", "succeed");
			return map;
		
	}
	
	@RequestMapping("testIDNo")
	public @ResponseBody Map<String, Object>
	testIDNo(@RequestParam String IDNo){
		Map<String, Object> map=new HashMap<>();
		
		if(IDNo.equals("")){
			map.put("data", "用户名不能空");
			return map;
		}
		
		
		if(IdcardUtil.isIdcard(IDNo)) {	    
			map.put("data", "succeed");
			return map;
		}else {
			map.put("data", "false");
			return map;
		}
		
	}

	/**
     * 电话号码验证
     * 
     * @param  str
     * @return 验证通过返回true
     */
	 public static boolean isPhone(String str) { 
        Pattern p1 = null,p2 = null;
        Matcher m = null;
        boolean b = false;  
        p1 = Pattern.compile("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$");

         m = p1.matcher(str);
         b = m.matches();  
      //  return b;
         return true;
     }
	
	@RequestMapping("/testPhone")
	public @ResponseBody Map<String, Object>
	testPhone(@RequestParam String telephone){
	    Map<String, Object> map=new HashMap<>();
		
		if(telephone.equals("")){
			map.put("data", "手机号码不能空");
			return map;
		}
		
		if(!isPhone(telephone)){
			map.put("data", "请输入正确的手机号码");
			return map;
		}
		
        map.put("data", "succeed");
		
		return map;
	}
	
	
   @RequestMapping("insert")
   public @ResponseBody Integer
   insert(HttpServletRequest request,@RequestParam String name,
		   @RequestParam String Charter,@RequestParam String IDNo,
		   @RequestParam String regtlx){
	   
	   HttpSession session = request.getSession();
       String openId=null;

       try{
         openId=session.getAttribute("openId").toString();
         }catch (Exception e) {
			// TODO: handle exception
        	 e.printStackTrace();
		  }
	  
	   if(regtlx.equals("")){
		   return 2;
	   }
	   
	   if(!regtlx.equals(verifyCode)){
		   verifyCode=null;
		   return 2;
	   }
	
	   Date upTime=new Date();
	   
	   try {		
               User_Asset user_asset=new User_Asset();
               
               user_asset.setOpenId(openId);
               user_asset.setCharter(Charter);
               user_asset.setIdno(IDNo);
				/*
				int testRepeat=
				
				int type;
				
				if(testRepeat==1){
					type=userService.updateUsersInfo(users);
				}else{
					type=userService.insertUsersInfo(users);
				}
				
				return type;*/
               return 0;
			
			}
		    catch (Exception e) {
		    	e.printStackTrace();

	            return 3;
		    }
   }
   
   @RequestMapping("/userinfoByopenId")
   public @ResponseBody Users 
   userinfoByopenId(HttpServletRequest request,@RequestParam Integer campusId){
	   HttpSession session = request.getSession();
       String openId=null;
       try{
           openId=session.getAttribute("openId").toString();
           }catch (Exception e) {
  			// TODO: handle exception
          	 e.printStackTrace();
  		  }
       
       return userService.getUserInfoById(campusId, openId);
   }
}