import request from '@/utils/request'
export default {

    //添加小节
    addVideo(video) {
        return request({
            url: '/eduservice/video/saveEduVideo',
            method: 'post',
            data: video
        })
    },

    // 获取小节信息
    getVideoById(id) {
        return request({
            url: `/eduservice/video/getVideoById/${id}`,
            method: 'get'
        })
    },

    // 更新
    updateVideo(video) {
        return request({
            url: '/eduservice/video/updateVideo',
            method: 'post',
            data: video
        })

    },

    //删除小节
    deleteVideo(id) {
        return request({
            url: `/eduservice/video/${id}`,
            method: 'delete'
        })
    },
    deleteAliyunvod(id){
        return request({
            url:`/serviceVod/removeVideo/${id}`,
            method: 'delete'
        })
    }
}