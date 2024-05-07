package cn.weedien.countdown.service;

import cn.weedien.countdown.model.req.CountdownCommand;
import cn.weedien.countdown.model.resp.CountdownQueryRespDTO;
import cn.weedien.countdown.model.resp.CountdownRespDTO;

import java.util.List;

public interface CountdownService {
    CountdownRespDTO insert(CountdownCommand command);

    void deleteById(long id);

    CountdownQueryRespDTO getById(long id);

    List<CountdownQueryRespDTO> list();

    CountdownQueryRespDTO getByQueryCode(String queryCode);
}