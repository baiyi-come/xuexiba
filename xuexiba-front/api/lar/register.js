import request from '@/utils/request'

export default {
    //根据手机号发验证码
  sendCode(phone) {
    return request({
      url: `/serviceSms/sendCode/${phone}`,
      method: 'post'
    })
  },

  //注册的方法
  registerMember(formItem) {
    return request({
      url: `/larservice/ucenter/register`,
      method: 'post',
      data: formItem
    })
  }

}