package com.team07.online_shopping_mall.service.impl;

import com.team07.online_shopping_mall.mapper.CommunicationMapper;
import com.team07.online_shopping_mall.model.domain.Communication;
import com.team07.online_shopping_mall.service.CommunicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Service
public class CommunicationServiceImpl extends ServiceImpl<CommunicationMapper, Communication> implements CommunicationService {
    @Autowired
    private CommunicationMapper communicationMapper;
//    public boolean identifyUser(Long id, Long userId){
//        return userId.equals(id);
//    }
}
