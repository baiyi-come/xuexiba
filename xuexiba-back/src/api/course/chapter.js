import request from '@/utils/request'
export default {
    //1 根据课程id获取章节和小节数据列表
    
    getAllChapterVideo(courseId) {
        console.log(courseId)
        return request({
            url: `/eduservice/chapter/${courseId}`,
            method: 'get'
          })
    },
    //添加章节
    addChapter(chapter) {
        return request({
            url: '/eduservice/chapter/saveChapter',
            method: 'post',
            data: chapter
          })
    },
    //根据id查询章节
    getChapter(chapterId) {
        return request({
            url: `/eduservice/chapter/getChapterById/${chapterId} `,
            method: 'get'
          })
    },
    //修改章节
    updateChapter(chapter) {
        return request({
            url: '/eduservice/chapter/updateChapter',
            method: 'post',
            data: chapter
          })
    },
    //删除章节
    deleteChapter(chapterId) {
        return request({
            url: `/eduservice/chapter/${chapterId}`,
            method: 'delete'
          })
    },
}