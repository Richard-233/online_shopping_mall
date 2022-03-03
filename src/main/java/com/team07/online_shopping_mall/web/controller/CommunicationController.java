package com.team07.online_shopping_mall.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.team07.online_shopping_mall.common.ApiRestResponse;
import com.team07.online_shopping_mall.common.Constant;
import com.team07.online_shopping_mall.exception.MallExceptionEnum;
import com.team07.online_shopping_mall.mapper.CommunicationMapper;
import com.team07.online_shopping_mall.mapper.ShopMapper;
import com.team07.online_shopping_mall.model.domain.*;
import com.team07.online_shopping_mall.service.CommunicationService;
import com.team07.online_shopping_mall.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.team07.online_shopping_mall.common.JsonResponse;
import com.team07.online_shopping_mall.service.ShopService;

import javax.servlet.http.HttpSession;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;


/**
 *
 *  前端控制器
 *
 *
 * @author team07
 * @since 2022-02-24
 * @version v1.0
 */
@Controller
@RequestMapping("/api/communication")
public class CommunicationController {

    private final Logger logger = LoggerFactory.getLogger( CommunicationController.class );

    @Autowired
    private CommunicationService communicationService;
    @Autowired
    private CommunicationMapper communicationMapper;
    @Autowired
    private UserService userService;

    /**
     * 描述：查询二者间所有消息
     *
     * 当A登录之后，点开与B的聊天窗口（或者是输入B的userId），就会调用/api/communication/communicate
     * communicate这个接口对应的communicate方法的内容是：定期调用/selectmessages对应的getMessages方法，
     * 使聊天记录可以更新，并一直存在一个可以写入的方法/add
     * /selectall对应的getMessages方法:显示出当前所有消息
     * /add对应的create方法：有一个输入消息的框和按钮，一旦点击，那么就会向数据库里写入一条消息
     * 并且，调用create方法或者是getMessages方法之后，在这两个方法末尾，都会调用/communicate对应的communicate方法
     *
     */
    @RequestMapping(value = "/selectmessages", method = RequestMethod.GET)
    @ResponseBody
    public List<Communication> getMessages(@RequestParam Long receiverId, HttpSession session)throws Exception {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        Long senderId=currentUser.getId();
        QueryWrapper<Communication> wrapper=new QueryWrapper<Communication>().eq("sender_id"
            ,senderId).eq("receiver_id",receiverId).or(i//总感觉这个i有问题，如果报错就改成wrapper试试
            ->i.eq("sender_id",receiverId).eq("receiver_id",senderId)
        ).orderByAsc("create_time");
        List<Communication> communications=communicationService.list(wrapper);
        if(communications.size()>0)
            return communications;
        //否则返回什么呢
        else return communications;
    }



    /**
     * 描述:创建新的消息
     *
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public void create(@RequestParam(value="messages",required = false, defaultValue = "") String messages,
                       @RequestParam(value="receiverId",required = false, defaultValue = "0") Long receiverId,
                       HttpSession session) throws Exception {
        if(!messages.isEmpty()&&receiverId!=0){
            User currentUser = (User) session.getAttribute(Constant.MALL_USER);
            Long senderId=currentUser.getId();
            Communication communication=new Communication();
            communication.setMessages(messages);
            communication.setSenderId(senderId);
            communication.setSenderName(userService.getById(senderId).getUsername());
            communication.setReceiverId(receiverId);
            communication.setReceiverName(userService.getById(receiverId).getUsername());
            communicationService.save(communication);
        }
    }



    /**
     * 描述:不断循环，直到用户关闭窗口
     *
     */

    @RequestMapping(value = "/communicate", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse communicate(@RequestParam(value="messages",required = false, defaultValue = "") String messages,
                              @RequestParam(value="receiverId",required = false, defaultValue = "0") Long receiverId,
                              HttpSession session)throws Exception {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        Long senderId=currentUser.getId();
//        //currentUser.setIntegral()
//        QueryWrapper<Communication> wrapper=new QueryWrapper<Communication>().555eq("offline",0).orderByDesc("score");
//        List<Communication> communications=communicationService.list(wrapper);
//        if(communications.size()>0)
//            return ApiRestResponse.success(communications);
//        else return ApiRestResponse.error(MallExceptionEnum.SELECT_FAILED);


        //while(true){
            create(messages,receiverId,session);
            List<Communication> list=getMessages(receiverId, session);
//            CommunicationMessages cm=new CommunicationMessages();
//            List<CommunicationMessages> cmlist=new ArrayList<>();
//        for (int i=0;i<list.size();i++) {
//            cm.setId(list.get(i).getId());
//            cm.setSenderId(list.get(i).getSenderId());
//            cm.setSenderName(userService.getById(list.get(i).getSenderId()).getUsername());
//            cm.setReceiverId(list.get(i).getReceiverId());
//            cm.setReceiverName(userService.getById(list.get(i).getReceiverId()).getUsername());
//            cm.setMessages(list.get(i).getMessages());
//            cm.setCreateTime(list.get(i).getCreateTime());
//            cmlist.add(cm);
//            System.out.println(cmlist.get(i));
//        }
            //如何输出list？
            //暂且先sout吧
            System.out.println(list);
            //最后，不能太勤，要隔2s循环一次
            //Thread.sleep(2000);
            return ApiRestResponse.success(list);
        //}

    }

}

