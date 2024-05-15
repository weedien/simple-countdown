package cn.weedien.countdown.service.impl;

import cn.weedien.countdown.dao.CountdownDao;
import cn.weedien.countdown.common.exception.BusinessException;
import cn.weedien.countdown.model.entity.Countdown;
import cn.weedien.countdown.model.req.CountdownCommand;
import cn.weedien.countdown.model.resp.CountdownQueryRespDTO;
import cn.weedien.countdown.model.resp.CountdownRespDTO;
import cn.weedien.countdown.service.CountdownService;
import cn.weedien.countdown.service.QueryCodeService;
import cn.weedien.countdown.util.CustomIdGenerator;
import cn.weedien.countdown.util.DurationUtil;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountdownServiceImpl implements CountdownService {

    @Resource
    private CountdownDao countdownDao;

    @Resource
    private QueryCodeService queryCodeService;

    @Resource
    private CustomIdGenerator idGenerator;

    @Override
    public CountdownRespDTO insert(CountdownCommand command) {
        long duration = DurationUtil.parseToMillis(command.getDuration());

        if (command.getMessage() == null || command.getMessage().isEmpty()) {
            command.setMessage("Time's up!");
        }

        Date expireTime = new Date(System.currentTimeMillis() + duration);

        Countdown countdown = new Countdown();
        countdown.setId(idGenerator.nextId());
        countdown.setExpireAt(expireTime);
        countdown.setQueryCode(queryCodeService.genQueryCode());
        countdown.setRemark(command.getRemark());
        countdown.setMessage(command.getMessage());
        countdown.setCreatedAt(new Date());
        countdown.setUpdatedAt(new Date());
        countdown.setDeleted(false);

        countdownDao.insert(countdown);

        CountdownRespDTO resp = new CountdownRespDTO();
        resp.setQueryCode(countdown.getQueryCode());
        resp.setExpireAt(countdown.getExpireAt());
        resp.setCreatedAt(countdown.getCreatedAt());
        return resp;
    }

    @Override
    public void deleteById(long id) {
        countdownDao.logicalDelete(id);
    }

    @Override
    public CountdownQueryRespDTO getById(long id) {
        Countdown countdown = countdownDao.selectById(id);
        if (countdown == null) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "Countdown not found");
        }

        CountdownQueryRespDTO resp = new CountdownQueryRespDTO();
        resp.setId(countdown.getId());
        resp.setQueryCode(countdown.getQueryCode());
        resp.setExpireAt(countdown.getExpireAt());
        resp.setCreatedAt(countdown.getCreatedAt());
        resp.setRemark(countdown.getRemark());
        resp.setMessage(countdown.getMessage());
        return resp;
    }

    @Override
    public List<CountdownQueryRespDTO> list() {
        List<Countdown> countdowns = countdownDao.list();
        return countdowns.stream().map(countdown -> {
            CountdownQueryRespDTO dto = new CountdownQueryRespDTO();
            dto.setId(countdown.getId());
            dto.setQueryCode(countdown.getQueryCode());
            dto.setCreatedAt(countdown.getCreatedAt());
            dto.setExpireAt(countdown.getExpireAt());
            dto.setRemark(countdown.getRemark());
            dto.setMessage(countdown.getMessage());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public CountdownQueryRespDTO getByQueryCode(String queryCode) {
        if (!queryCodeService.isValidQueryCode(queryCode)) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "Query code not exists");
        }
        Countdown countdown = countdownDao.selectByQueryCode(queryCode);
        if (countdown == null) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "Countdown not found");
        }

        CountdownQueryRespDTO resp = new CountdownQueryRespDTO();
        resp.setRemaining(DurationUtil.intervalFromNow(countdown.getExpireAt()));
        resp.setPassed(DurationUtil.intervalBeforeNow(countdown.getCreatedAt()));
        resp.setCreatedAt(countdown.getCreatedAt());
        resp.setExpireAt(countdown.getExpireAt());
        resp.setRemark(countdown.getRemark());

        if (countdown.getExpireAt().compareTo(new Date()) < 0 && StringUtils.hasLength(countdown.getMessage())) {
            resp.setMessage(countdown.getMessage());
        }

        return resp;
    }
}
