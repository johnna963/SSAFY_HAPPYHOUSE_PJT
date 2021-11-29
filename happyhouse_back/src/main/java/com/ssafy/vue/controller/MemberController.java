package com.ssafy.vue.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.service.JwtService;
import com.ssafy.vue.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
@Api("사용자 컨트롤러  API V1")
public class MemberController {

	private final MemberService memberService;
    private final JwtService jwtService;
    private final String SUCCESS = "success";
    private final String FAIL ="fail";
    public static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    
    @Autowired
    public MemberController(MemberService memberService, JwtService jwtService) {
		this.memberService = memberService;
		this.jwtService = jwtService;
	}

	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) MemberDto memberDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			MemberDto loginUser = memberService.login(memberDto);
			if (loginUser != null) {
				String token = jwtService.create("userid", loginUser.getUserid(), "access-token");// key, data, subject
				logger.debug("로그인 토큰정보 : {}", token);
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userid") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userid,
			HttpServletRequest request) {
//		logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(userid);
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/lookup")
    public ResponseEntity<List<MemberDto>> lookup(@RequestBody String userId) throws Exception{
        return new ResponseEntity<List<MemberDto>>(memberService.findAll(userId), HttpStatus.OK);
    }
	
	@GetMapping("/findPassword/{userid}")
    public ResponseEntity<String> findPw(@PathVariable("userid") String userid) throws Exception {
    	String foundPw = memberService.findPw(userid);
    	
    	if(foundPw != null) {
    		return new ResponseEntity<String>(foundPw, HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/modify")
    public ResponseEntity<String> modify(@RequestBody MemberDto requestDto) throws Exception {
        if(memberService.setMember(requestDto))
        	return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        
        return new ResponseEntity<String>(FAIL, HttpStatus.EXPECTATION_FAILED);
    }
    
    @ApiOperation(value = "회원 정보 삭제", notes = "회원 아이디에 해당하는 회원 정보를 삭제한다.", response = String.class)
    @DeleteMapping("/delete/{userid}")
    public ResponseEntity<String> delete(@PathVariable("userid") String userid) throws Exception {
        if(memberService.delete(userid))
        	return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        return new ResponseEntity<String>(FAIL, HttpStatus.EXPECTATION_FAILED);
    }
    
    @ApiOperation(value = "회원가입", notes = "회원가입 기능. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PostMapping("/signup")
    public ResponseEntity<String> join(@RequestBody @ApiParam(value = "회원 정보.") MemberDto memberDto) throws Exception {
		if(memberService.join(memberDto))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }
    
    @ApiOperation(value = "회원 목록", notes = "모든 회원의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<MemberDto>> listMember() throws Exception {
		logger.info("listMember - 호출");
		return new ResponseEntity<List<MemberDto>>(memberService.listMember(), HttpStatus.OK);
	}

}
