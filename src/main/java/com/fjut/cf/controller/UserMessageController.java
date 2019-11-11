package com.fjut.cf.controller;

import com.fjut.cf.component.interceptor.PrivateRequired;
import com.fjut.cf.pojo.enums.ResultJsonCode;
import com.fjut.cf.pojo.po.UserMessagePO;
import com.fjut.cf.pojo.vo.ResultJsonVO;
import com.fjut.cf.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author axiang [2019/11/11]
 */
@RestController
@CrossOrigin
@RequestMapping("/user_message")
public class UserMessageController {
    @Autowired
    UserMessageService userMessageService;

    @PrivateRequired
    @GetMapping("/all/get")
    public ResultJsonVO getUserMessageByUsername(@RequestParam("username") String username,
                                                 @RequestParam("pageNum") Integer pageNum,
                                                 @RequestParam("pageSize") Integer pageSize) {
        ResultJsonVO resultJsonVO = new ResultJsonVO(ResultJsonCode.REQUIRED_SUCCESS);
        Integer startIndex = (pageNum - 1) * pageSize;
        List<UserMessagePO> userMessagePOS = userMessageService.queryUserMessageByUsernameDescLimit(username, startIndex, pageSize);
        Integer count = userMessageService.queryUserMessageCountByUsername(username);
        resultJsonVO.addInfo(userMessagePOS);
        resultJsonVO.addInfo(count);
        return resultJsonVO;
    }

    @PrivateRequired
    @GetMapping("/unread/get")
    public ResultJsonVO getUserUnreadMessageByUsername(@RequestParam("username") String username,
                                                       @RequestParam("pageNum") Integer pageNum,
                                                       @RequestParam("pageSize") Integer pageSize) {
        ResultJsonVO resultJsonVO = new ResultJsonVO(ResultJsonCode.REQUIRED_SUCCESS);
        Integer startIndex = (pageNum - 1) * pageSize;
        List<UserMessagePO> userMessagePOS = userMessageService.queryUserMessageUnreadByUsernameDescLimit(username, startIndex, pageSize);
        Integer count = userMessageService.queryUserMessageUnreadCountByUsername(username);
        resultJsonVO.addInfo(userMessagePOS);
        resultJsonVO.addInfo(count);
        return resultJsonVO;
    }

    @PrivateRequired
    @GetMapping("/unread/count/get")
    public ResultJsonVO getUserUnreadMessageCountByUsername(@RequestParam("username") String username) {
        ResultJsonVO resultJsonVO = new ResultJsonVO(ResultJsonCode.REQUIRED_SUCCESS);
        Integer count = userMessageService.queryUserMessageUnreadCountByUsername(username);
        resultJsonVO.addInfo(count);
        return resultJsonVO;
    }

    @PrivateRequired
    @PostMapping("/unread/set_read/post")
    public ResultJsonVO setUserMessageRead(@RequestParam("username") String username,
                                           @RequestParam("id") Integer id) {
        ResultJsonVO resultJsonVO = new ResultJsonVO();
        Integer integer = userMessageService.updateUserMessageStatusSetReadByUsernameAndId(username, id);
        if (integer == 1) {
            resultJsonVO.setStatus(ResultJsonCode.REQUIRED_SUCCESS);
        } else {
            resultJsonVO.setStatus(ResultJsonCode.BUSINESS_FAIL, "设置未读消息失败");
        }
        return resultJsonVO;
    }

    @PrivateRequired
    @PostMapping("/unread/set_all_read/post")
    public ResultJsonVO setUserMessageAllRead(@RequestParam("username") String username)
    {
        ResultJsonVO resultJsonVO = new ResultJsonVO();
        Integer integer = userMessageService.updateUserMessageStatusSetReadByUsername(username);
        if (integer >= 1) {
            resultJsonVO.addInfo(integer);
            resultJsonVO.setStatus(ResultJsonCode.REQUIRED_SUCCESS);
        } else {
            resultJsonVO.setStatus(ResultJsonCode.BUSINESS_FAIL, "设置未读消息失败");
        }
        return resultJsonVO;
    }


}
