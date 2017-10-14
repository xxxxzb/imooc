package org.imooc.controller.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.imooc.bean.Comment;
import org.imooc.bean.Orders;
import org.imooc.bean.Page;
import org.imooc.constant.ApiCodeEnum;
import org.imooc.dto.AdDto;
import org.imooc.dto.ApiCodeDto;
import org.imooc.dto.BusinessDto;
import org.imooc.dto.BusinessListDto;
import org.imooc.dto.CommentForSubmitDto;
import org.imooc.dto.CommentListDto;
import org.imooc.dto.OrderForBuyDto;
import org.imooc.dto.OrdersDto;
import org.imooc.service.content.AdService;
import org.imooc.service.content.BusinessService;
import org.imooc.service.content.CommentService;
import org.imooc.service.content.MemberService;
import org.imooc.service.content.OrdersService;
import org.imooc.task.BusinessTask;
import org.imooc.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	private AdService adservice;
	
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private CommentService commentService;
	
	@Value("${ad.number}")
	private int adNumber;
	
	@Value("${businessHome.number}")
	private int businessHomeNumber;
	
	@Value("${businessSearch.number}")
	private int businessSearchNumber;
	
	/**
	 * 首页 —— 广告（超值特惠）
	 */
	@RequestMapping(value="/homead",method=RequestMethod.GET)
	public List<AdDto> homead(){
		AdDto adDto = new AdDto();
		adDto.getPage().setPageNumber(adNumber);
		return adservice.searchByPage(adDto);
	}
	
	/**
	 * 首页 —— 推荐列表（猜你喜欢）
	 */
	@RequestMapping(value="/homelist/{city}/{page}",method=RequestMethod.GET)
	public BusinessListDto homelist() {
		BusinessDto businessDto = new BusinessDto();
		businessDto.getPage().setPageNumber(businessHomeNumber);
		return businessService.seachByPageForApi(businessDto);
	}
	
	/**
	 * 搜索结果页 - 搜索结果 - 三个参数
	 */
	@RequestMapping(value="/search/{page}/{city}/{category}/{keyword}",method=RequestMethod.GET)
	public BusinessListDto searchByThree(BusinessDto businessDto){
		businessDto.getPage().setPageNumber(businessSearchNumber);
		return businessService.seachByPageForApi(businessDto);
	}
	
	/**
	 * 搜索结果页 - 搜索结果 - 两个参数
	 */
	@RequestMapping(value="/search/{page}/{city}/{category}",method=RequestMethod.GET)
	public BusinessListDto searchByTwo(BusinessDto businessDto){
		businessDto.getPage().setPageNumber(businessSearchNumber);
		return businessService.seachByPageForApi(businessDto);
	}
	
	/**
	 * 详情页 - 商户信息
	 */
	@RequestMapping(value="/detail/info/{id}",method=RequestMethod.GET)
	public BusinessDto detailInfo(@PathVariable("id")Long id){
		return businessService.seachById(id);
	}
	
	/**
	 * 根据手机号 下发短信验证码
	 */
	@RequestMapping(value="/sms",method=RequestMethod.POST)
	public ApiCodeDto sms(@RequestParam("username")Long username){
		ApiCodeDto dto;
		//1、验证手机号是否被注册过（是否存在）
		if(memberService.exists(username)){
			//2、生成6位随机数
			String code = String.valueOf(CommonUtil.random(6));
			
			//3、保存手机号与对应的md5（6位随机数）（一般保存1分钟，之后就失效）
			if(memberService.saveCode(username, code)) {
				//4、调用短信通道，发送明文6位数到对应手机上
				if(memberService.sendCode(username, code)){
					dto = new ApiCodeDto(ApiCodeEnum.SUCCESS.getErrno(),code);					
				}else {
					dto = new ApiCodeDto(ApiCodeEnum.SEND_FAIL);
				}
			}else {
				dto = new ApiCodeDto(ApiCodeEnum.REPEAT_REQUEST);
			}
		}else {
			dto = new ApiCodeDto(ApiCodeEnum.USER_NO_EXISTS);
		}
		return dto;
	}
	
	/**
	 * 会员登录
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ApiCodeDto login(@RequestParam("username")Long username,@RequestParam("code")String code){
		ApiCodeDto dto;
		// 1、用手机号取出保存的md5(6位随机数)，能取到，并且与提交上来的code值相同为校验通过
		if(memberService.getCode(username)!=null){
			if(memberService.getCode(username).equals(code)){
		// 2、如果校验通过，生成一个32位的token
				String token = CommonUtil.getUUID();
		// 3、保存手机号与对应的token（一般这个手机号中途没有与服务端交互的情况下，保持10分钟）
				memberService.saveToken(token, username);
		// 4、将这个token返回给前端
				dto = new ApiCodeDto(ApiCodeEnum.SUCCESS);
				dto.setToken(token);
			}else{
					dto = new ApiCodeDto(ApiCodeEnum.CODE_ERROR);
			}
		}else{
			dto = new ApiCodeDto(ApiCodeEnum.CODE_INVALID);
		}
		
		return dto;
		
	}
	
	/**
	 * 买单
	 */
	@RequestMapping(value="/order",method=RequestMethod.POST)
	public ApiCodeDto order(OrderForBuyDto orderForBuyDto) {
		ApiCodeDto dto;
		//1、校验token是否有效(缓存中是否存在这样一个token,并且对应存放的会员信息(这里指手机号)与提交上来的信息一致)
		Long phone = memberService.getPhone(orderForBuyDto.getToken());
		if(phone!=null && phone.equals(orderForBuyDto.getUsername())) {
		//2、根据手机号获取会员主键
			Long memberId = memberService.getIdByPhone(phone);
		//3、保存订单
			OrdersDto ordersDto = new OrdersDto();
			ordersDto.setNum(orderForBuyDto.getNum());
			ordersDto.setPrice(orderForBuyDto.getPrice());
			ordersDto.setBusinessId(orderForBuyDto.getId());
			ordersDto.setMemberId(memberId);
			ordersService.add(ordersDto);
//			BusinessTask businessTask= new BusinessTask();
//			businessTask.synNumber();
			dto = new ApiCodeDto(ApiCodeEnum.SUCCESS);
		}else{
			dto = new ApiCodeDto(ApiCodeEnum.NOT_LOGGED);
		}
		return dto;
	}
	
	/**
	 * 订单列表 
	 */
	@RequestMapping(value="/orderlist/{username}",method=RequestMethod.GET)
	public List<OrdersDto> orderlist(@PathVariable("username")Long username) {
		Long memberId = memberService.getIdByPhone(username);
		return ordersService.getListByMemberId(memberId);
	}
	
	/**
	 * 提交评论
	 */
	@RequestMapping(value="/submitComment",method=RequestMethod.POST)
	public ApiCodeDto submitComment(CommentForSubmitDto dto){
		ApiCodeDto apiCodeDto;
		//1、校验登录信息：token、手机号
		Long phone = memberService.getPhone(dto.getToken());
		if(phone !=null && dto.getUsername().equals(phone)){			
		//2、根据手机号获取会员id
			Long memberId = memberService.getIdByPhone(phone);
		// 3、根据提交上来的订单ID获取对应的会员ID，校验与当前登录的会员是否一致
			OrdersDto ordersDto = ordersService.getbyId(dto.getId());
			if(ordersDto.getMemberId().equals(memberId)){
				//4、保存评论
				commentService.add(dto);
				//TODO 5、非实时同步星星数
				apiCodeDto = new ApiCodeDto(ApiCodeEnum.SUCCESS);
			}else{
				apiCodeDto = new ApiCodeDto(ApiCodeEnum.NO_AUTH);
			}
		}else{
			apiCodeDto = new ApiCodeDto(ApiCodeEnum.NOT_LOGGED);
		}
		return apiCodeDto;
	}
	
	/**
	 * TODO
	 * 详情页 - 用户评论
	 */
	@RequestMapping(value = "/detail/comment/{currentPage}/{businessId}", method = RequestMethod.GET)
	public CommentListDto detail(@PathVariable("businessId") Long businessId,Page page) {
		return commentService.getListByBusinessId(businessId,page);
	}
}
