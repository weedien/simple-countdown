package cn.weedien.countdown.dao;

import cn.weedien.countdown.model.entity.Countdown;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface CountdownDao {
    Countdown selectById(@Param("id") long id);

    int insert(Countdown countdown);

    int update(Countdown countdown);

    int logicalDelete(@Param("id") long id);

    Countdown selectByQueryCode(@Param("queryCode") String queryCode);

    String selectQueryCodeById(@Param("id") long id);

    List<Countdown> list();

    List<Countdown> listUnexpired();

    List<String> listExpiredQueryCodes(Date expireTime);

    List<String> listValidQueryCodes();
}