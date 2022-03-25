package com.tzf.eduservice.controller;


import com.tzf.commonutil.Resouce;
import com.tzf.eduservice.entity.EduChapter;
import com.tzf.eduservice.entity.vo.ChapterVo;
import com.tzf.eduservice.service.EduChapterService;
import com.tzf.servicebase.config.DisableAuth;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Resource
    private EduChapterService eduChapterService;

    /**
     * 根据id获取资源
     * @param id
     * @return
     */
    @DisableAuth
    @GetMapping("{id}")
    public Resouce getEduChapter(@PathVariable("id") String id) {
        List<ChapterVo> list=eduChapterService.getEduChapterVOById(id);
        return Resouce.ok().data("list",list);
    }

    /**
     * 保存章节
     * @param eduChapter
     * @return
     */
    @DisableAuth
    @PostMapping("saveChapter")
    public Resouce saveChapter(@RequestBody EduChapter eduChapter) {
        try {
            eduChapterService.save(eduChapter);

            return Resouce.ok();

        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error();
        }
    }

    /**
     * 根据章节id查询
     * @param id
     * @return
     */
    @DisableAuth
    @GetMapping("getChapterById/{id}")
    public Resouce getChapterById(@PathVariable String id) {
        try {
            EduChapter chapter = eduChapterService.getById(id);
            return Resouce.ok().data("chapter",chapter);
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error();
        }
    }

    /**
     * 更新
     * @param eduChapter
     * @return
     */
    @DisableAuth
    @PostMapping("updateChapter")
    public Resouce updateChapter(@RequestBody EduChapter eduChapter) {
        try {
            eduChapterService.updateById(eduChapter);
            return Resouce.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error();
        }

    }

    /**
     * 删除章节
     * @param id
     * @return
     */
    @DisableAuth
    @DeleteMapping("{id}")
    public Resouce removeChapterById(@PathVariable String id) {
        eduChapterService.removeChapterById(id);
        return Resouce.ok();
    }
}

