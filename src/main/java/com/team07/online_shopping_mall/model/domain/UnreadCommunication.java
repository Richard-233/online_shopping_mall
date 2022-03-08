package com.team07.online_shopping_mall.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mall_unreadcommunication")
@ApiModel(value="UnreadCommunication对象", description="")
public class UnreadCommunication extends Model<UnreadCommunication> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @ApiModelProperty(value = "发送方id")
    private Long senderId;

    @ApiModelProperty(value = "发送方name")
    private String senderName;

    @ApiModelProperty(value = "接收方id")
    private Long receiverId;

    @ApiModelProperty(value = "接收方name")
    private String receiverName;

    @ApiModelProperty(value = "聊天消息")
    private String messages;

    @ApiModelProperty(value = "是否已读")
    private Integer isRead;

    @ApiModelProperty(value = "未读消息数量")
    private Integer unReadNumber;

    @ApiModelProperty(value = "时间")
    private LocalDateTime createTime;


    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public String toString(){
        return this.getSenderId()+"对"+this.getReceiverId()
                +"说："+this.getMessages()+",时间是："+this.getCreateTime()+",多少条未读消息"+this.getUnReadNumber();
    }

    public void newUpdate(UnreadCommunication unreadCommunication) {
        this.setId(unreadCommunication.getId());
        this.setSenderId(unreadCommunication.getSenderId());
        this.setSenderName(unreadCommunication.getSenderName());
        this.setUnReadNumber(unreadCommunication.getUnReadNumber());
    }
}

