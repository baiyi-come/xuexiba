package com.tzf.eduservice.service;

import com.tzf.eduservice.entity.EduChapter;
import com.tzf.eduservice.entity.vo.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getEduChapterVOById(String id);

    void removeChapterById(String id);
}
