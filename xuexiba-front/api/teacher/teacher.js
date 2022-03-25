import request from '@/utils/request'

export default{
    // 首页显示
    getTeacherList(){
        return request({
            url:`/eduservice/teacher/getTeacherLimit`,
            method:'get'
        })
    },
    // 教师页面
    getTeacherAll(pageNum,pageSize,teacherQuery){
        return request({
            url:`/eduservice/teacher/getTeacher/${pageNum}/${pageSize}`,
            method:'post',
            data:teacherQuery
        })
    },
    // 教师详情页面
    getTeacherData(id){
        return request({
            url:`/eduservice/teacher/getTeacherDate/${id}`,
            method:'post'
        })
    }
}