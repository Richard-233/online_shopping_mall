package com.team07.online_shopping_mall.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.team07.online_shopping_mall.common.ApiRestResponse;
import com.team07.online_shopping_mall.common.Constant;
import com.team07.online_shopping_mall.common.utls.SecurityUtils;
import com.team07.online_shopping_mall.exception.MallExceptionEnum;
import com.team07.online_shopping_mall.mapper.CommunicationMapper;
import com.team07.online_shopping_mall.mapper.ShopMapper;
import com.team07.online_shopping_mall.model.domain.*;
import com.team07.online_shopping_mall.model.dto.UserInfoDTO;
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
     * 描述：查询自己和receiverId二者间所有消息
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
    public ApiRestResponse getMessages(Long receiverId, HttpSession session)throws Exception {
        //System.out.println("smmm");
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        Long senderId=currentUser.getId();
//        //调试用。。。
//        SecurityUtils securityUtils = new SecurityUtils();
//        UserInfoDTO userInfo = securityUtils.getUserInfo();
//        Long senderId=userInfo.getId();
//        //。。。调试用
        QueryWrapper<Communication> wrapper=new QueryWrapper<Communication>().eq("sender_id"
            ,senderId).eq("receiver_id",receiverId).or(i//总感觉这个i有问题，如果报错就改成wrapper试试
            ->i.eq("sender_id",receiverId).eq("receiver_id",senderId)
        ).orderByDesc("create_time");
        List<Communication> communications=communicationService.list(wrapper);
        //if(communications.size()>0)
            return ApiRestResponse.success(communications);
        //否则返回什么呢
        //else return ApiRestResponse.error(MallExceptionEnum.SELECT_FAILED);
    }


    /**
    * 查询关于自己的所有消息
    *这个函数已经用不上了
    * */
    @RequestMapping(value = "/selectall", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse getAll(HttpSession session)throws Exception {
//        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
//        Long senderId=currentUser.getId();
        //调试用。。。
        SecurityUtils securityUtils = new SecurityUtils();
        UserInfoDTO userInfo = securityUtils.getUserInfo();
        Long senderId=userInfo.getId();
        //。。。调试用
        QueryWrapper<Communication> wrapper=new QueryWrapper<Communication>().eq("sender_id"
                ,senderId).or(i//总感觉这个i有问题，如果报错就改成wrapper试试
                ->i.eq("receiver_id",senderId)
        ).orderByDesc("create_time");
        List<Communication> communications=communicationService.list(wrapper);
        //System.out.println(communications);
        if(communications.size()>0)
            return ApiRestResponse.success(communications);
            //否则返回什么呢
        else return ApiRestResponse.error(MallExceptionEnum.SELECT_FAILED);
    }



    /**
     * 一键确认已读
     *
     * */
    @RequestMapping(value = "/beread", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse<Object> beread(Long receiverId, HttpSession session)throws Exception {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        Long senderId=currentUser.getId();
//        //调试用。。。
//        SecurityUtils securityUtils = new SecurityUtils();
//        UserInfoDTO userInfo = securityUtils.getUserInfo();
//        Long senderId=userInfo.getId();
//        //。。。调试用
        QueryWrapper<Communication> wrapper=new QueryWrapper<Communication>();
        wrapper.eq("sender_id",receiverId).eq("receiver_id",senderId).eq("is_read",0);
        List<Communication> lists=new ArrayList<Communication>();
        lists=communicationService.list(wrapper);
        for(int i=0;i<lists.size();i++){
            lists.get(i).setIsRead(1);
        }
        communicationService.updateBatchById(lists);
        return ApiRestResponse.success();

    }




    /**
     * 查询关于自己的所有聊天框
     * 还需要显示有多少条未读数据
     *
     * */
    @RequestMapping(value = "/windows", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse communicationWindows(HttpSession session)throws Exception {
        //System.out.println(1111);
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        Long userId=currentUser.getId();
        //System.out.println(userId);
//        //调试用。。。
//        SecurityUtils securityUtils = new SecurityUtils();
//        UserInfoDTO userInfo = securityUtils.getUserInfo();
//        Long userId=userInfo.getId();
//        //。。。调试用
        QueryWrapper<Communication> wrapper=new QueryWrapper<Communication>().
                select("distinct sender_id,receiver_id,sender_name,receiver_name,is_read")
                .eq("sender_id",userId).or(i
                ->i.eq("receiver_id",userId)
        );
                //.orderByDesc("create_time");
        List<Communication> communications=communicationService.list(wrapper);
        //int sizeofcom=communications.size();
        //获取好友的id和name，如果上面加了isread的话，可能一个好友会获取到多次
        for(int i=0;i<communications.size();i++){
            if(communications.get(i).getSenderId().equals(userId)){
                communications.get(i).setSenderId(communications.get(i).getReceiverId());
                communications.get(i).setSenderName(communications.get(i).getReceiverName());
            }
        }
//        System.out.println(communications);
//        System.out.println(7777);
        //去掉重复的好友id，就算加了isread，一个好友获取了多次，此处也会清重
        for(int i=0;i<communications.size();i++){
            Long otherId=communications.get(i).getSenderId();
            for(int j=i+1;j<communications.size();j++){
                if(otherId.equals(communications.get(j).getSenderId())){
                    Communication cm1=communications.get(j);
                    communications.remove(cm1);
                    j--;
                    //System.out.println(i+","+j);
                }
            }
        }
//        System.out.println(communications);
//        System.out.println(99999);
        //得到每一个好友给自己的未读消息数量
        List<UnreadCommunication> uclist=new ArrayList<>();
        for (int j=0;j<communications.size();j++) {
            UnreadCommunication uc=new UnreadCommunication();
            QueryWrapper<Communication> wrapper2=new QueryWrapper<Communication>()
                    .eq("receiver_id",userId).eq("sender_id",communications.get(j).getSenderId())
                    .eq("is_read",0);
            int numberOfUnread=communicationService.count(wrapper2);
            uc.setSenderId(communications.get(j).getSenderId());
            uc.setSenderName(communications.get(j).getSenderName());
            uc.setUnReadNumber(numberOfUnread);
            uclist.add(j,uc);
        }
//        System.out.println(uclist);
//        System.out.println(8888);
        //对未读消息数进行排序
        int un=0;
        for(int i=0;i<uclist.size();i++){
            for(int j=i+1;j<uclist.size();j++){
                un=uclist.get(j).getUnReadNumber();
                UnreadCommunication uc2=uclist.get(j);
                if(un>uclist.get(i).getUnReadNumber()){
                    uclist.get(j).newUpdate(uclist.get(i));
                    uclist.get(i).newUpdate(uc2);
                }
            }
        }
        //System.out.println(uclist);
        if(uclist.size()>0)
            return ApiRestResponse.success(uclist);
            //否则返回什么呢
        else return ApiRestResponse.error(MallExceptionEnum.SELECT_FAILED);
    }


    /**
     * 描述:创建新的消息
     *
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse create(String messages,
                       Long receiverId,
                       HttpSession session) throws Exception {
//        //调试用。。。
//        SecurityUtils securityUtils = new SecurityUtils();
//        UserInfoDTO userInfo = securityUtils.getUserInfo();
//        Long senderId=userInfo.getId();
//        //。。。调试用
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        Long senderId=currentUser.getId();
        if((!messages.isEmpty())&&(receiverId!=0)&&(!senderId.equals(receiverId))){
//            User currentUser = (User) session.getAttribute(Constant.MALL_USER);
//            Long senderId=currentUser.getId();
            Communication communication=new Communication();
            communication.setMessages(messages);
            communication.setSenderId(senderId);
            communication.setSenderName(userService.getById(senderId).getUsername());
            communication.setReceiverId(receiverId);
            communication.setReceiverName(userService.getById(receiverId).getUsername());
            //已读这个字段，是为了判断对方是否已读，因为自己发出去的消息，自己肯定是已读的
            //0是未读，1是已读
            communication.setIsRead(0);
            communicationService.save(communication);
            //System.out.println(communication);
        }
        return ApiRestResponse.success();
    }



    /**
     * 描述:不断循环，直到用户关闭窗口
     *这个函数已经用不上了
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
           // List<Communication> list=getMessages(receiverId, session);
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
           // System.out.println(list);
            //最后，不能太勤，要隔2s循环一次
            //Thread.sleep(2000);
            return ApiRestResponse.success(1);
        //}

    }

}

