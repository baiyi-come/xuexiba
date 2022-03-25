import request from '@/utils/request'
export default{
    getCourseList(){
        return request({
            url:`/eduservice/course/getCourseLimit`,
            method:'get'
        })
    },

    // 课程页面
    getCourseAll(pageNum,pageSize,courseInfo){
        return request({
            url:`/eduservice/course/getCourseAll/${pageNum}/${pageSize}`,
            method:'post',
            data:courseInfo
        })
    },
    // 获取课程分类
    getSubject(){
        return request({
            url:'/eduservice/subject/getAllSubject',
            method:'get'
        })
    },


    // 获取单个课程的详情
    getCourseData(id){
        return request({
            url:`/eduservice/course/getCourseDataById/${id}`,
            method:'post'
        })
    },

    // 视频播放
    getPlayAuth(vid) {
        return request({
          url: `/serviceVod/getPlayAuth/${vid}`,
          method: 'get'
        })
      },

      getBuy(id){
          return request({
              url:`/eduservice/course/isBuyByCourseId/${id}`,
              method:'get'

          })
      },


      // 保存评论
      saveComment(teacherId,courseId,comment){
        return request({
            url:`/eduservice/comment/saveComment/${teacherId}/${courseId}/${comment}`,
            method:'post'
        })
      },

      getComment(pageNum,pageSize,courseId){
          return request({
              url:`/eduservice/comment/getComment/${pageNum}/${pageSize}/${courseId}`,
              method:'post'
          })
      },
      getUserId(){
          return request({
              url:'/larservice/ucenter/getUserId',
              method:'post'
          })
      },
      removeComment(id){
         return request({
             url:`/eduservice/comment/removeComment/${id}`,
             method:'post'
         })
      }
}