import request from '@/utils/request'

export default {
    
    getRegisterCount(day) {
        return request({
            url: `/staservice/daily/getRegisterCount/${day}`,
            method: 'get'
        })
    },

    // 根据条件查询访问信息
    getCountByDay(searchObj){
        return request({
            url:`/staservice/daily/getCountByDay/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
            method:`post`
        })
    },

    // 默认加载图
    getDefault(){
        return request({
            url:'/staservice/daily/getDefaultDate',
            method:'post'
        })
    }

}