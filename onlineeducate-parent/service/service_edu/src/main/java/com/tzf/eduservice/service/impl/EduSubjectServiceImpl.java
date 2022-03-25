package com.tzf.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.tzf.eduservice.entity.EduSubject;
import com.tzf.eduservice.entity.excel.SubjectExcel;
import com.tzf.eduservice.entity.subject.OneSubject;
import com.tzf.eduservice.entity.subject.TwoSubject;
import com.tzf.eduservice.listener.SubjectExcelListener;
import com.tzf.eduservice.mapper.EduSubjectMapper;
import com.tzf.eduservice.service.EduSubjectService;
import com.tzf.servicebase.exceptionHandler.FileTypeHandler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {

        String contentType = file.getContentType();

        if (!contentType.equals("application/vnd.ms-excel") && !contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            throw new FileTypeHandler(20001, "文件类型只能为xsl或xslx");
        }
        // 转成文件输入流
        try {
            InputStream fileInputStream = file.getInputStream();
            EasyExcel.read(fileInputStream, SubjectExcel.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getSubjectList() {

        // 获取一级列表
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(oneWrapper);
        // 获取二级列表
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(twoWrapper);
        // 创建一个集合用于存合并后的对象
        ArrayList<OneSubject> oneSubjects = new ArrayList<>();
        // 组合以及列表和二级列表
            // 遍历一级目录
        for (EduSubject eduSubject : oneSubjectList) {
            // new 一级列表对象
            OneSubject oneSubject = new OneSubject();
            // 赋值
            oneSubject.setId(eduSubject.getId());
            oneSubject.setTitle(eduSubject.getTitle());

            // 根据一级目录的id遍历二级目录

            // 创建二级目录集合，用于存储合适的二级对象
            List<TwoSubject> twoSubjectArrayList = new ArrayList<>();

            for (int i = 0; i < twoSubjectList.size(); i++) {
                // 获取对象
                EduSubject eduSubject1 = twoSubjectList.get(i);

//                System.out.println(eduSubject1.getParentId());
//                System.out.println(oneSubject.getId());
                //  如果相等则符合条件，加入到集合中
                if (eduSubject1.getParentId().equals(oneSubject.getId())) {

                    TwoSubject twoSubject = new TwoSubject();
                    // 拷贝
                    BeanUtils.copyProperties(eduSubject1, twoSubject);

                    // 加入二级集合
                    twoSubjectArrayList.add(twoSubject);
                }
            }
             // 加入一级对象，此时一个一级对象遍历完毕
            oneSubject.setTwoSubjectList(twoSubjectArrayList);

            // 添加到总集合中，用于返回
            oneSubjects.add(oneSubject);
        }

        // 返回组合后的列表
        return oneSubjects;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = null;
        try {
            fileName = URLEncoder.encode("课程分类", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        List<EduSubject> list = baseMapper.selectList(null);

        try {
            EasyExcel.write(response.getOutputStream()).sheet("模板").doWrite(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
