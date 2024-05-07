package cn.weedien.countdown.controller;

import cn.weedien.countdown.common.auth.Auth;
import cn.weedien.countdown.model.Result;
import cn.weedien.countdown.model.req.CountdownCommand;
import cn.weedien.countdown.model.resp.CountdownQueryRespDTO;
import cn.weedien.countdown.model.resp.CountdownRespDTO;
import cn.weedien.countdown.service.CountdownService;
import cn.weedien.countdown.uitl.Results;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countdowns")
public class CountdownController {

    @Resource
    private CountdownService countdownService;

    /**
     * 创建倒计时
     *
     * @param duration 倒计时时长
     * @param remark   备注
     * @param message  消息
     * @param command  倒计时创建命令
     * @return 倒计时创建信息
     * @apiNote 支持以请求参数或请求体的形式传递数据，便于手动发起请求
     */
    @PostMapping
    public Result<CountdownRespDTO> createCountdown(
            @RequestParam(required = false) String duration,
            @RequestParam(required = false) String remark,
            @RequestParam(required = false) String message,
            @RequestBody(required = false) CountdownCommand command) {
        if (command == null) {
            command = new CountdownCommand();
            command.setDuration(duration);
            command.setRemark(remark);
            command.setMessage(message);
        }
        return Results.success(countdownService.insert(command));
    }

    /**
     * 删除倒计时
     *
     * @param id 倒计时ID
     * @return 空值
     * @apiNote 删除操作需要提供认证信息
     */
    @Auth
    @DeleteMapping("/{id}")
    public Result<Void> deleteCountdown(@PathVariable long id) {
        countdownService.deleteById(id);
        return Results.success();
    }

    /**
     * 获取倒计时
     *
     * @param id 倒计时ID
     * @return 倒计时信息
     * @apiNote 通过id获取倒计时信息，需要提供认证信息
     */
    @Auth
    @GetMapping("/{id}")
    public Result<CountdownQueryRespDTO> getCountdown(@PathVariable long id) {
        return Results.success(countdownService.getById(id));
    }

    /**
     * 获取所有倒计时
     *
     * @return 倒计时信息列表
     * @apiNote 需要提供认证信息
     */
    @Auth
    @GetMapping
    public Result<List<CountdownQueryRespDTO>> getCountdowns() {
        return Results.success(countdownService.list());
    }

    /**
     * 查询倒计时
     *
     * @param queryCode 查询码
     * @return 倒计时剩余时间
     * @apiNote 通过查询码查询倒计时信息，属于公开接口
     */
    @GetMapping("/query/{queryCode}")
    public Result<CountdownQueryRespDTO> queryCountdown(@PathVariable String queryCode) {
        return Results.success(countdownService.getByQueryCode(queryCode));
    }

}
