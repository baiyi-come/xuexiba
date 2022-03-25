package com.tzf.eduservice.service.impl;

import com.tzf.eduservice.entity.EduChapter;
import com.tzf.eduservice.entity.EduVideo;
import com.tzf.eduservice.entity.vo.ChapterVo;
import com.tzf.eduservice.entity.vo.VideoVo;
import com.tzf.eduservice.mapper.EduChapterMapper;
import com.tzf.eduservice.service.EduChapterService;
import com.tzf.eduservice.service.EduVideoService;
import com.tzf.servicebase.exceptionHandler.GlobalCommonExceptionHandler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Resource
    private EduVideoService eduVideoService;
    /**
     * 课程大纲查询
     *  此种方法会多次查询数据库，给数据库带来压力
     *  还有一种方法是用课程id分别去访问章节表和小节表，只需访问两次数据库，减小数据库压力
     *  可参考课程管理的课程分类列表进行设计
     * @param id
     * @return
     */
    @Override
    public List<ChapterVo> getEduChapterVOById(String id) {
        // 获取课程章节
        // 添加查询条件
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        // 根据条件进行查询
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapper);
        // 获取课程小结
            // 根据章节id获取章节内的小节
            // 创建返回list
        List<ChapterVo> list = new ArrayList<>();
        for (EduChapter eduChapter : eduChapterList) {
            // 创建ChapterVo
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
//            System.out.println(chapterVo);
            // 获取eduChapter的id
            String eduChapterId = eduChapter.getId();
//            System.out.println(eduChapterId);
            // 根据eduChapterId查询到相应的章节
            QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
            videoQueryWrapper.eq("chapter_id", eduChapterId);
            List<EduVideo> eduVideoList = eduVideoService.list(videoQueryWrapper);
//            System.out.println(eduVideoList);
            // 用于存储分类好了的videoVo对象
            List<VideoVo> videoVo = new ArrayList<>();

            // 遍历转换成videoVo对象
            for (EduVideo eduVideo : eduVideoList) {
                VideoVo videoVo1 = new VideoVo();
                BeanUtils.copyProperties(eduVideo, videoVo1);
                videoVo.add(videoVo1);
            }
            chapterVo.setChildren(videoVo);

            list.add(chapterVo);
        }
        return list;
    }

    @Override
    public void removeChapterById(String id) {
        // 判断此章节下是否有小节，有的话，不能删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", id);
        int count = eduVideoService.count(wrapper);
        if (count > 0) {
            throw new GlobalCommonExceptionHandler(20001, "不能删除此章节，因为下面有小节");
        }else {
            int i = baseMapper.deleteById(id);
            if (i <= 0) {
                throw new GlobalCommonExceptionHandler(20001, "删除失败");
            }
        }
    }
}
