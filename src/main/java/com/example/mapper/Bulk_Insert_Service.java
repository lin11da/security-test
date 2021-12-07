package com.example.mapper;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojp.entity.UserO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

//批量插入数据
@Repository
@Mapper
public interface Bulk_Insert_Service extends IService<UserO> {
}
