package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/label")
@CrossOrigin
public class LabelController {
    @Autowired
    private LabelService labelService;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        try {
            List<Label> labelList = labelService.findAll();
            return new Result(true, StatusCode.OK, "查询成功", labelList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "查询失败");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) {
        System.out.println("No1根据id查询标签");
        return new Result(true, StatusCode.OK, "查询成功", labelService.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result saveLabel(@RequestBody Label label) {
        String id = labelService.saveLabel(label);
        return new Result(true, StatusCode.OK, "保存成功", id);
    }

    @RequestMapping(value = "/{labelId}", method = RequestMethod.PUT)
    public Result updateLabel(@RequestBody Label label, @PathVariable(value = "labelId") String labelId) {
        label.setId(labelId);
        labelService.updateLabel(label);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteLabel(@PathVariable("id") String id) {
        labelService.deleteLabel(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findByCondition(@RequestBody Label label) {
        List<Label> conditions = labelService.findByCondition(label);
        return new Result(true, StatusCode.OK, "条件查询成功", conditions);
    }

    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findPageByCondition(@RequestBody Label label, @PathVariable int page, @PathVariable int size) {
        PageResult<Label> pageResult = labelService.findPageByCondition(label, page, size);
        return new Result(true, StatusCode.OK, "分页查询成功", pageResult);
    }

}
