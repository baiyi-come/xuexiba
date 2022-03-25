import request from '@/utils/request'
export default {

    // 保存社区
    saveComment(comment) {
        return request({
            url: '/comservice/community/saveCommunity',
            method: 'post',
            data: comment
        })
    },
    // 获取社区
    getCommunity(pageNum, pageSize) {
        return request({
            url: `/comservice/community/getCommunity/${pageNum}/${pageSize}`,
            method: 'post'
        })
    },
    // 根据社区名查询社区
    getCommunityByName(communityName) {
        return request({
            url: `/comservice/community/getCommunityByName/${communityName}`,
            method: 'post'
        })
    },
    // 保存发表内容
    saveCommunityText(communityText) {
        return request({
            url: '/comservice/communitytext/saveCommunityText',
            method: 'post',
            data: communityText

        })
    },
    // 查询某个社区里的文章

    getCommunityTextByCommunityId(communityId) {
        return request({
            url: `/comservice/communitytext/getCommunityTextByCommunityId/${communityId}`,
            method: 'post'
        })
    },

    // 获取当前用户id
    getUserId() {
        return request({
            url: '/larservice/ucenter/getUserId',
            method: 'post'
        })


    },
    // 删除某个文章
    removeCommunityText(id) {
        return request({
            url: `/comservice/communitytext/removeCommunityText/${id}`,
            method: 'post'
        })
    },
    getCommunityLimitTwo() {
        return request({
            url: `/comservice/communitytext/getCommunityLimitTwo`,
            method: 'post'
        })
    },
    // 获取当前用户信息
    getUser(id) {
        return request({
            url: `/larservice/ucenter/getUser/${id}`,
            method: 'post'
        })
    },
    // 保存评论
    saveCommunityTextComment(communityTextComment){
        return request({
            url:'/comservice/comment/saveCommunityTextComment',
            method:'post',
            data:communityTextComment

        })
    },
    // 获取某一个文章下的评论
    getCommunityCommentByTextId(id){
        return request({
             url:`/comservice/comment/getCommunityCommentByTextId/${id}`,
             method:'post'
        })
       
    },
    // 删除一级评论（其下二级评论一并删除）

    removeCommunityTextOneComment(id){
        return request({
            url:`/comservice/comment/removeCommunityTextOneComment/${id}`,
            method:'get'
        })
    },
    // 删除二级评论
    removeCommunityTextTwoComment(id){
        return request({
            url:`/comservice/comment/removeCommunityTextTwoComment/${id}`,
            method:'get'
        })
    },
    // 删除社区
    removeCommunity(id){
        return request({
            url:`/comservice/community/removeCommunity/${id}`,
            method:'get'
        })
    }
}