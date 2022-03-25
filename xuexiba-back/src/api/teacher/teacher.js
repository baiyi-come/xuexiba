import request from '@/utils/request'

export default {
    // 获取分页数据
    getTeacherPage(current,limit,teacherQuery){
        return request({
            url: `/eduservice/teacher/get/${current}/${limit}`,
            method: 'post',
            // teacherQuery条件对象，后端使用RequestBody获取数据
            // data表示把对象转换json进行传递到接口里面
            data: teacherQuery
        })
    },
    // 删除讲师操作
    removeTeacherById(id){
       return request({
        url:`/eduservice/teacher/${id}`,
        method:'delete'
       })
    },
    
    // 添加讲师操作
    saveTeacher(teacher){
        return request({
            url:`/eduservice/teacher/addTeacher`,
            method: 'post',
            data: teacher
        })
    },
    // 查询教师
    getTeacherById(id){
        return request({
            url:`/eduservice/teacher/${id}`,
            method:'get'
        })
    },
    // 更新教师数据
    updateTeacher(teacher){
        return request({
            url:`/eduservice/teacher/updateTeacher`,
            method:"post",
            data:teacher
        })
    }

}