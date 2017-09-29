package org.imooc.controller.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.imooc.bean.Ad;
import org.imooc.bean.BusinessList;
import org.imooc.bean.Comment;
import org.imooc.bean.Orders;
import org.imooc.bean.Page;
import org.imooc.controller.content.BusinessesController;
import org.imooc.dto.AdDto;
import org.imooc.dto.ApiCodeDto;
import org.imooc.dto.BusinessDto;
import org.imooc.dto.BusinessListDto;
import org.imooc.service.content.AdService;
import org.imooc.service.content.BusinessService;
import org.imooc.service.content.MemberService;
import org.imooc.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	private AdService adservice;
	
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private MemberService memberService;
	
	@Value("${ad.number}")
	private int adNumber;
	
	@Value("${businessHome.number}")
	private int businessHomeNumber;
	
	@Value("${businessSearch.number}")
	private int businessSearchNumber;
	
	/**
	 * 首页 —— 广告（超值特惠）
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@ResponseBody
	@RequestMapping(value="/homead",method=RequestMethod.GET)
	public List<AdDto> homead(){
		AdDto adDto = new AdDto();
		adDto.getPage().setPageNumber(adNumber);
		return adservice.searchByPage(adDto);
	}
	
	/**
	 * 首页 —— 推荐列表（猜你喜欢）
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@ResponseBody
	@RequestMapping(value="/homelist/{city}/{page}",method=RequestMethod.GET)
	public BusinessListDto homelist() {
		BusinessDto businessDto = new BusinessDto();
		businessDto.getPage().setPageNumber(businessHomeNumber);
		return businessService.seachByPageForApi(businessDto);
	}
	
	/**
	 * 搜索结果页 - 搜索结果 - 三个参数
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@ResponseBody
	@RequestMapping(value="/search/{page}/{city}/{category}/{keyword}",method=RequestMethod.GET)
	public BusinessListDto searchByThree(BusinessDto businessDto){
		businessDto.getPage().setPageNumber(businessSearchNumber);
		return businessService.seachByPageForApi(businessDto);
	}
	
	/**
	 * 搜索结果页 - 搜索结果 - 两个参数
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@ResponseBody
	@RequestMapping(value="/search/{page}/{city}/{category}",method=RequestMethod.GET)
	public BusinessListDto searchByTwo(BusinessDto businessDto){
		businessDto.getPage().setPageNumber(businessSearchNumber);
		return businessService.seachByPageForApi(businessDto);
	}
	
	/**
	 * 详情页 - 商户信息
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@ResponseBody
	@RequestMapping(value="/detail/info/{id}",method=RequestMethod.GET)
	public BusinessDto detailInfo(@PathVariable("id")Long id){
		return businessService.seachById(id);
	}
	
	/**
	 * 详情页 - 用户评论
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@ResponseBody
	@RequestMapping(value="/detail/comment/{page}/{id}",method=RequestMethod.GET)
	public Comment detailComment(@PathVariable("id")Long id,Page page){
		return null;
	}
	
	
	/**
	 * 获取提交评论
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@ResponseBody
	@RequestMapping(value="/submitComment",method=RequestMethod.POST)
	public Map<String,Object> submitComment() {
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("errno", 0);
		result.put("msg", "ok");
		return result;
	}
	
	/**
	 * 订单列表
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@ResponseBody
	@RequestMapping(value="/orderlist/{username}",method=RequestMethod.GET)
	public Orders orderlist() throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper=new ObjectMapper();
		String s="";
		return mapper.readValue(s, new TypeReference <Orders>(){});
	}
	
	/**
	 * 购买
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@ResponseBody
	@RequestMapping(value="/order",method=RequestMethod.POST)
	public Map<String,Object> order() {
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("errno", 0);
		result.put("msg", "buy ok");
		return result;
	}
	
	/**
	 * 登录
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@ResponseBody
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public Map<String,Object> login() {
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("errno", 0);
		result.put("msg", "loing ok");
		result.put("token", "aaaaaaaaaaaa");
		return result;
	}
	
	
	
	/**
	 * 获取短信验证码
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 *//*
	@ResponseBody
	@RequestMapping(value="/sms",method=RequestMethod.POST)
	public Map<String,Object> sms() {
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("errno", 0);
		result.put("msg", "loing ok");
		result.put("code", "md5(123456)");
		return result;
	}
*/
	
	/**
	 * 根据手机号 下发短信验证码
	 */
	@RequestMapping(value="/sms",method=RequestMethod.POST)
	public ApiCodeDto sms(@RequestParam("username")Long username){
		
		//1、验证手机号是否被注册过（是否存在）
		if(memberService.exists(username)){
			//2、生成6位随机数
			String code = String.valueOf(CommonUtil.random(6));
			
			//3、保存手机号与对应的md5（6位随机数）（一般保存1分钟，之后就失效）
			memberService
		}
		return null;
	}
}
