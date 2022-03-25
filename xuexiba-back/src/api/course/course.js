import request from '@/utils/request'
export default {
    //1 添加课程信息
    addCourseInfo(courseInfo) {
        return request({
            url: '/eduservice/course/saveCourse',
            method: 'post',
            data: courseInfo
        })
    },
    //2 查询所有讲师
    getListTeacher() {
        return request({
            url: '/eduservice/teacher/getEduTeacherAll',
            method: 'get'
        })
    },
    //根据课程id查询课程基本信息
    getCourseInfoId(id) {
        return request({
            url: `/eduservice/course/getCourseById/${id}`,
            method: 'get'
        })
    },
    //修改课程信息
    updateCourseInfo(courseInfo) {
        return request({
            url: '/eduservice/course/updateCourse',
            method: 'post',
            data: courseInfo
        })
    },

    //课程确认信息显示
    getPublihCourseInfo(id) {
        return request({
            url: '/eduservice/course/' + id,
            method: 'get'
        })
    },
    //课程最终发布
    publihCourse(id) {
        return request({
            url: '/eduservice/course/publishCourse/' + id,
            method: 'post'
        })
    },
    //课程最终发布
    getListCourse() {
        return request({
            url: '/eduservice/course',
            method: 'get'
        })
    },

    // 课程列表
    getListCourse(current,limit,courseQuery){
        return request({
            url:`/eduservice/course/${current}/${limit}`,
            method:'post',
          data:courseQuery
        })
    },
    // 课程删除
    removeCourse(courseId){
        return request({
            url:`/eduservice/course/removeCourse/${courseId}`,
            method:"delete"
        })
    }
}
