package com.tzf.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.tzf.eduservice.entity.EduSubject;
import com.tzf.eduservice.entity.excel.SubjectExcel;
import com.tzf.eduservice.service.EduSubjectService;
import com.tzf.servicebase.exceptionHandler.FileIsNullHandler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<SubjectExcel> {
    // 因为SubjectExcelListener不能交给spring进行管理，需要自己进行new，不能注入其他对象
    // 不能实现数据库1操作
    public EduSubjectService eduSubjectService;

    public SubjectExcelListener(){}

    public SubjectExcelListener(EduSubjectService eduSubjectService) {

        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectExcel subjectExcel, AnalysisContext analysisContext) {
        // 判断文件是否为空
        if (subjectExcel == null) {
            throw new FileIsNullHandler(20001, "文件为空");
        }

        // 判断第一分级是否唯一
        EduSubject OneSubject = this.justOneSubject(eduSubjectService, subjectExcel.getOneSubjectName());

        if (OneSubject == null) {
             OneSubject = new EduSubject();
            OneSubject.setParentId("0");
            OneSubject.setTitle(subjectExcel.getOneSubjectName());
            eduSubjectService.save(OneSubject);
        }

        String pid = OneSubject.getId();
        // 判断第二分级是否唯一
        EduSubject TwoSubject = this.justTwoSubject(eduSubjectService, subjectExcel.getTwoSubjectName(), pid);

        if (TwoSubject == null) {
            EduSubject eduSubject = new EduSubject();
            eduSubject.setParentId(pid);
            eduSubject.setTitle(subjectExcel.getTwoSubjectName());
            eduSubjectService.save(eduSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    private EduSubject justOneSubject(EduSubjectService SubjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", 0);
        EduSubject eduSubject = SubjectService.getOne(wrapper);
        return eduSubject;
    }

    private EduSubject justTwoSubject(EduSubjectService SubjectService,String name,String pId) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pId);
        EduSubject eduSubject = SubjectService.getOne(wrapper);
        return eduSubject;
    }
}
